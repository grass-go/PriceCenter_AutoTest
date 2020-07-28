const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);


function getTransaction(input) {
    tronWeb.trx.getTransaction(input).then(result => console.log(JSON.stringify(result)))
}

switch(arguments[0]) {
    case "getTransaction":
        getTransaction(arguments[1])
        break;

     default:
        break;
}