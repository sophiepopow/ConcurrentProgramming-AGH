const waterfall = require('async/waterfall')

function printAsync(s, cb) {
    var delay = Math.floor((Math.random() * 100) + 50);
    setTimeout(function () {
      console.log(s);
      if (cb) cb();
    }, delay);
  }

function loop(n) {
    _123 = [
        (next) => printAsync("1", next),
        (next) => printAsync("2", next),
        (next) => printAsync("3", next),
    ];

    let waterfallFunctions = [];

    for(let i=0; i<n; i+=1) {
        waterfallFunctions = [...waterfallFunctions, ..._123];
    }

    waterfall(waterfallFunctions, () => console.log("Done"));
}

loop(4);