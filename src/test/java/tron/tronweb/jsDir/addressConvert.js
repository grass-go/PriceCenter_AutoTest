const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);

function base58ToBase64(input) {
//console.log(input)
var value = tronWeb.address.toHex(input)
console.log(value)
return value
}

function base64ToBase58(input) {
var value = tronWeb.address.fromHex(input)
console.log(value)
return value
}

function privateKeyToBase58(input) {
var value = tronWeb.address.fromPrivateKey(input)
console.log(value)
return value
}

switch(arguments[0]) {
     case "base58ToBase64":
        base58ToBase64(arguments[1])
        break;
     case "base64ToBase58":
        base64ToBase58(arguments[1])
        break;
      case "privateKeyToBase58":
        privateKeyToBase58(arguments[1])
     default:
        break;
}