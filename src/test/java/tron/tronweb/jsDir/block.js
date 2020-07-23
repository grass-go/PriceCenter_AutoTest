const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);


function getBlock() {
    tronWeb.trx.getBlock('').then(result => console.log(JSON.stringify(result)))
}

function getCurrentBlock() {
    tronWeb.trx.getCurrentBlock().then(result => console.log(JSON.stringify(result)))
}

function getBlockByNumber(input) {
    tronWeb.trx.getBlockByNumber(input).then(result => console.log(JSON.stringify(result)))
}

function getBlockByHash(input) {
    tronWeb.trx.getBlockByHash(input).then(result => console.log(JSON.stringify(result)))
}

function getBlockRange(start,end) {
    tronWeb.trx.getBlockRange(start,end).then(result => console.log(JSON.stringify(result)))
}

function getBlockTransactionCount(input) {
    tronWeb.trx.getBlockTransactionCount(input).then(result => console.log(result))
}

switch(arguments[0]) {
    case "getBlock":
        getBlock()
        break;
    case "getBlockByHash":
        getBlockByHash(arguments[1])
        break;
    case "getBlockByNumber":
        getBlockByNumber(arguments[1])
        break;
    case "getBlockRange":
        getBlockRange(arguments[1],arguments[2])
        break;
    case "getCurrentBlock":
        getCurrentBlock()
        break;
    case "getBlockTransactionCount":
        getBlockTransactionCount(arguments[1])
        break;
     default:
        break;
}