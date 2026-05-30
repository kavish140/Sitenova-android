const jsdom = require('jsdom');
const { JSDOM } = jsdom;
JSDOM.fromURL('https://sitenova.dev/', { runScripts: 'dangerously', resources: 'usable' })
  .then(dom => {
     setTimeout(() => {
        const text = dom.window.document.body.innerText || dom.window.document.body.textContent;
        console.log("TEXT:");
        console.log(text);
        const links = Array.from(dom.window.document.querySelectorAll('a')).map(a => a.href);
        console.log("LINKS:");
        console.log([...new Set(links)]);
     }, 5000);
  }).catch(e => console.error(e));
