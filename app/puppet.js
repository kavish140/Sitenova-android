const puppeteer = require('puppeteer');

(async () => {
  const browser = await puppeteer.launch({
    args: ['--no-sandbox', '--disable-setuid-sandbox']
  });
  const page = await browser.newPage();
  await page.goto('https://sitenova.dev', { waitUntil: 'networkidle0' });
  const links = await page.evaluate(() => {
    return Array.from(document.querySelectorAll('a')).map(a => a.href);
  });
  const texts = await page.evaluate(() => document.body.innerText);
  console.log("TEXT:");
  console.log(texts);
  console.log("LINKS:");
  console.log([...new Set(links)]);
  await browser.close();
})();
