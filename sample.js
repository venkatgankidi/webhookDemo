let express = require('express');
let app = express();
let bodyparser = require('body-parser');

let urlencoded = bodyparser.urlencoded({extended:false})

app.get('/', (req, res) =>{
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/Json');
    res.end(req.body);
})


let server = app.listen(8080, () => {
   const host = server.address().address
   const port = server.address().port
   
   console.log(`Example app listening at  ${host}, ${port}`)
})