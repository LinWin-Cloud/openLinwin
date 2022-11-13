/**
 * auther: linwincloud
 * https://github.com/LinWin-Cloud
 */

var http=require('http')
var server=http.createServer(function(req,res){
    //console.log(req.require)

    res.setHeader('Content-Type','text/html;charset=utf-8');
    res.writeHead(200,'OK')
    res.write(`<html><head></head><body><h1>hello world<h1><h2>你好</h2></body></html>`)
    res.end()
})
console.log('open http://localhost:8080')

try
{
    server.listen(8888) // crete http listener
}
catch (err)
{
    console.log(err)
}