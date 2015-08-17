'use strict';

var gulp = require('gulp');

var paths = gulp.paths;

var $ = require('gulp-load-plugins')();

var urlAdjuster = require('gulp-css-url-adjuster');

var wiredep = require('wiredep').stream;

// Injects all js files in src/app and all bower dependencies into index.html
// Injects all CSS files in .tmp/srv into index.html
// Places temp serve index.html without concat scripts into srv/
gulp.task('inject', ['styles'], function () {
  // Gets the fonts css file and concats with the index.css file
  // and adjusts the urls of the fontello fonts.
  gulp.src([
    paths.src + '/assets/fonts/**/trash.css',
    paths.tmp + '/serve/app/index.css'
  ])
  .pipe($.concatCss('index.css', {
    rebaseUrls: true,
  }))
  .pipe(urlAdjuster({
    prepend: '/assets/fonts/'
  }))
  .pipe(gulp.dest(paths.tmp + '/serve/app'));

  var injectStyles = gulp.src([
    paths.tmp + '/serve/{app,components}/**/*.css',
    '!' + paths.tmp + '/serve/app/vendor.css'
  ], { read: false });

  var injectScripts = gulp.src([
    paths.src + '/{app,components}/**/*.js',
    '!' + paths.src + '/{app,components}/**/*.spec.js',
    '!' + paths.src + '/{app,components}/**/*.mock.js'
  ]).pipe($.angularFilesort());

  var injectOptions = {
    ignorePath: [paths.src, paths.tmp + '/serve'],
    addRootSlash: false
  };

  var wiredepOptions = {
    directory: 'bower_components',
    exclude: [/bootstrap-sass-official/, /bootstrap\.css/, /bootstrap\.css/, /foundation\.css/, /jquery/]
  };

  return gulp.src(paths.src + '/*.html')
    .pipe($.inject(injectStyles, injectOptions))
    .pipe($.inject(injectScripts, injectOptions))
    .pipe(wiredep(wiredepOptions))
    .pipe(gulp.dest(paths.tmp + '/serve'));

});
