function printAsync(s, cb) {
    var delay = Math.floor((Math.random() * 100) + 50);
    setTimeout(function () {
      console.log(s);
      if (cb) cb();
    }, delay);
  }
  
async function task(n) {
    return new Promise((res) => {
      printAsync(n, res);
    });
  }
  
  async function loop(times){
      for(let i=0; i<times; i++){
        await task(1);
        await task(2);
        await task(3);
    }
    console.log("Done")
  }
  
  loop(4);
  