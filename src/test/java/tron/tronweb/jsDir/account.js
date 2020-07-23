const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);



function createAccount() {
console.log(tronWeb.createAccount())
}

function isAddress(input) {
console.log(tronWeb.isAddress(input))
}

function setPrivateKey(input) {
console.log(tronWeb.setPrivateKey(input))
}

function defaultPrivateKey() {
console.log(tronWeb.defaultPrivateKey)
}

function setAddress(input) {
console.log(tronWeb.setAddress(input))
}

function defaultAddress() {
console.log(tronWeb.defaultAddress)
}

function getAccount(input) {
    tronWeb.trx.getAccount(input).then(result => console.log(JSON.stringify(result)))
}

switch(arguments[0]) {
     case "createAccount":
        createAccount()
        break;
     case "isAddress":
        isAddress(arguments[1])
        break;
     case "setPrivateKey":
        setPrivateKey(arguments[1])
        defaultPrivateKey()
        break;
     case "setAddress":
        setAddress(arguments[1])
        defaultAddress()
        break;
    case "getAccount":
        getAccount(arguments[1])
        break;
     default:
        break;
}