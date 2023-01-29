const express = require("express");

const app = express();

const PORT = 5000;
const HOST = '0.0.0.0';

app.get('/', function(req, res) {
  res.send("Hola mundo!")
})

app.get('/sumar/:n1/:n2', function(req, res) {
  const {params} = req;
  const {n1, n2} = params;
  res.send("La suma es :" + (parseInt(n1) + parseInt(n2)));
})

app.listen(PORT, HOST, () => {
  console.log(`Running on http://${HOST}:${PORT}`);
})