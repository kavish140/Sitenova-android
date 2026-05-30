const https = require('https');

https.get('https://sitenova.dev', res => {
  let body = '';
  res.on('data', d => body += d);
  res.on('end', () => {
    // Find all JS files
    const scripts = [...body.matchAll(/src=\"(\/assets\/[^\"]+\.js)\"/g)].map(m => m[1]);
    
    // Also check preloads
    const preloads = [...body.matchAll(/href=\"(\/assets\/[^\"]+\.js)\"/g)].map(m => m[1]);
    
    const allJs = [...new Set([...scripts, ...preloads])];
    
    allJs.forEach(jsPath => {
      https.get('https://sitenova.dev' + jsPath, sres => {
         let sbody = '';
         sres.on('data', d => sbody += d);
         sres.on('end', () => {
            // Find words near "site" or "url" or "href"
            const matches = sbody.match(/.{0,50}(?:\.com|\.dev|\.in|\.org|\.net).{0,50}/gi);
            if (matches) {
               console.log("Found in " + jsPath + ":");
               console.log([...new Set(matches)]);
            }
         });
      });
    });
  });
});
