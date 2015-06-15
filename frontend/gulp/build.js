'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var urlAdjuster = require('gulp-css-url-adjuster');

var $ = require('gulp-load-plugins')({
  pattern: ['gulp-*', 'main-bower-files', 'uglify-save-license', 'del']
});

gulp.task('partials', function () {
  return gulp.src([
    paths.src + '/{app,components}/**/*.html',
    paths.tmp + '/{app,components}/**/*.html'
  ])
    // .pipe($.minifyHtml({
      // empty: true,
      // spare: true,
      // quotes: true
    // }))
    .pipe($.angularTemplatecache('templateCacheHtml.js', {
      module: 'vaccinations',
    }))
    .pipe(gulp.dest(paths.tmp + '/partials/'));
});

gulp.task('html', ['inject', 'partials'], function () {
  var partialsInjectFile = gulp.src(paths.tmp + '/partials/templateCacheHtml.js', { read: false });
  var partialsInjectOptions = {
    starttag: '<!-- inject:partials -->',
    ignorePath: paths.tmp + '/partials',
    addRootSlash: false
  };

  var htmlFilter = $.filter('**/*.html');
  var jsFilter = $.filter('**/*.js');
  var cssFilter = $.filter('**/*.css');
  var indexCssFilter = $.filter('**/index.css');
  var jspFilter = $.filter('**/*.jsp');
  var assets;

  return gulp.src(paths.tmp + '/serve/*.html')
    // HTML partials js injection.
    .pipe($.inject(partialsInjectFile, partialsInjectOptions))

    // Collect js/css files into assets variable and filters out index.html
    .pipe(assets = $.useref.assets())

    // Add hash to filenames
    .pipe($.rev())

    .pipe(jsFilter)
    // Annotate angular components which aren't array prefixed.
    .pipe($.ngAnnotate())
    //.pipe($.uglify({preserveComments: $.uglifySaveLicense}))
    .pipe(jsFilter.restore())

    .pipe(cssFilter)
    .pipe($.replace('../bootstrap-sass-official/assets/fonts/bootstrap', 'fonts'))

    // Adjust the css url links for the fontello fonts.
    .pipe(urlAdjuster ({
      replace: ['/assets/fonts/fontello-97618726/font/', '/openmrs/moduleResources/vaccinations/fonts/']
    }))
    // Optimize css
    .pipe($.csso())
    .pipe(cssFilter.restore())
    .pipe(assets.restore())

    // Remove references to all other scripts and stylesheets.
    .pipe($.useref())

    // Rewrite references to app and vender files with appropriate hash postfixes.
    .pipe($.revReplace())

    .pipe(htmlFilter)
    // .pipe($.minifyHtml({
      // empty: true,
      // spare: true,
      // quotes: true
    // }))
    // Inject Spring headers
    .pipe($.headerfooter.header('<%@ include file="template/localHeader.jsp"%>\n\n'))
    .pipe($.headerfooter.header('<%@ include file="/WEB-INF/template/header.jsp"%>\n'))
    .pipe($.headerfooter.header('<%@ include file="/WEB-INF/template/include.jsp"%>\n'))
    // Inject Spring footers
    .pipe($.headerfooter.footer('\n\n'))
    .pipe($.headerfooter.footer('<%@ include file="/WEB-INF/template/footer.jsp"%>\n'))
    // Restore all files in stream.
    .pipe(htmlFilter.restore())

    // Prefix scripts and style files with '/resources/'
    // so they are placed appropriately in the omod file strucure.
    .pipe($.rename(function (path) {
      // console.log(path);
      if (path.dirname === 'styles' || path.dirname === 'scripts') {
        path.dirname = '/resources/' + path.dirname;
      }
    }))

    // Output files
    .pipe(gulp.dest(paths.omod + '/'))

    // Print files sizes to build window.
    .pipe($.size({ title: paths.omod + '/', showFiles: true }));
});

gulp.task('images', function () {
  return gulp.src(paths.src + '/assets/images/**/*')
    .pipe(gulp.dest(paths.dist + '/assets/images/'));
});

gulp.task('fonts', function () {
  return gulp.src(paths.src + '/assets/fonts/**/*.{eot,svg,ttf,woff}')
    // Prefix scripts and style files with '/resources/'
    // so they are placed appropriately in the omod file strucure.
    // .pipe($.rename(function (path) {
    //     path.dirname = '/fonts/';
    // }))
    // .pipe($.rename(function (path) {
    //     console.log(path);
    //     // path.dirname = '/fonts/';
    // }))
    .pipe($.flatten())
    .pipe(gulp.dest(paths.omod + '/resources/fonts/'));
});

gulp.task('clean', function (done) {
  $.del([paths.omod + '/index.html', paths.omod + '/manage.jsp', paths.omod + '/resources/', paths.tmp + '/'], {force: true}, done);
});

gulp.task('build', ['html', 'images', 'fonts'], function () {
  var jspUrlPrefix = '${pageContext.request.contextPath}/moduleResources/vaccinations/';

  // After all build procedures are complete, prefix any href/src urls with the
  // spring prefix.
  gulp.src(paths.omod + '/index.html')
    .pipe($.prefix(jspUrlPrefix, null, '{{'))
    .pipe($.rename('manage.jsp'))
    .pipe(gulp.dest(paths.omod + '/'));

  return gulp.src(paths.omod + '/index.html')
    .pipe($.rimraf({force: true}));

});
