const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);



function fromAscii(input) {
console.log(tronWeb.fromAscii(input))
}

function fromDecimal(input) {
console.log(tronWeb.fromDecimal(input))
}

function fromSun(input) {
console.log(tronWeb.fromSun(input))
}

function fromUtf8(input) {
console.log(tronWeb.fromUtf8(input))
}

function isConnected() {
tronWeb.isConnected().then(result => {console.log(result)})
}

function setDefaultBlock(input) {
console.log(tronWeb.setDefaultBlock(input))
}


switch(arguments[0]) {
     case "fromAscii":
        fromAscii(arguments[1])
        break;
     case "fromDecimal":
        fromDecimal(arguments[1])
        break;
     case "fromSun":
        fromSun(arguments[1])
        break;
     case "fromUtf8":
        fromUtf8(arguments[1])
        break;
     case "isConnected":
        isConnected()
        break;
     case "setDefaultBlock":
        setDefaultBlock(arguments[1])
        break;
     default:
        break;
}