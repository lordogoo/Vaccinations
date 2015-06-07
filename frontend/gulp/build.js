'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

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

  var htmlFilter = $.filter('*.html');
  var jsFilter = $.filter('**/*.js');
  var cssFilter = $.filter('**/*.css');
  var jspFilter = $.filter('**/*.jsp');
  var assets;

  return gulp.src(paths.tmp + '/serve/*.html')
    // HTML partials js injection.
    .pipe($.inject(partialsInjectFile, partialsInjectOptions))

    // Collect html files into assets variable.
    .pipe(assets = $.useref.assets())
    // Add hash to filenames
    .pipe($.rev())

    // Filter on JS files.
    .pipe(jsFilter)
    .pipe($.ngAnnotate())
    //.pipe($.uglify({preserveComments: $.uglifySaveLicense}))
    .pipe(jsFilter.restore())

    // Filter only CSS files.
    .pipe(cssFilter)
    .pipe($.replace('../bootstrap-sass-official/assets/fonts/bootstrap', 'fonts'))
    .pipe($.csso())
    .pipe(cssFilter.restore())
    .pipe(assets.restore())

    // Remove references to all other scripts and stylesheets,
    // other than app/vender files
    .pipe($.useref())

    // Rewrite references to app and vender files with appropriate hashs.
    .pipe($.revReplace())

    // Filter on HTML file
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

    // Rename index.html to manage.jsp.
    .pipe($.rename('manage.jsp'))

    // Restore all files in stream.
    .pipe(htmlFilter.restore())

    // Place JS files in omod/../resources/scripts dir.
    .pipe(jsFilter)
    .pipe(gulp.dest(paths.omod + '/resources' + '/'))
    .pipe(jsFilter.restore())

    // Place CSS files in omod/../resources/styles dir.
    .pipe(cssFilter)
    .pipe(gulp.dest(paths.omod + '/resources' + '/'))
    .pipe(cssFilter.restore())

    // Place html/jsp file into omod directory.
    .pipe(jspFilter)
    .pipe(gulp.dest(paths.omod + '/'))
    .pipe(jspFilter.restore())

    // Print files sizes to build window.
    .pipe($.size({ title: paths.omod + '/', showFiles: true }));
});

gulp.task('images', function () {
  return gulp.src(paths.src + '/assets/images/**/*')
    .pipe(gulp.dest(paths.dist + '/assets/images/'));
});

gulp.task('fonts', function () {
  return gulp.src($.mainBowerFiles())
    .pipe($.filter('**/*.{eot,svg,ttf,woff}'))
    .pipe($.flatten())
    .pipe(gulp.dest(paths.dist + '/fonts/'));
});

gulp.task('clean', function (done) {
  $.del([paths.dist + '/', paths.tmp + '/'], done);
});

gulp.task('build', ['html', 'images', 'fonts']);
