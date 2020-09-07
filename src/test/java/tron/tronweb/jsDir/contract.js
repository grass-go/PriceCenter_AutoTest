const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);



function getContractFromAbi() {
console.log(tronWeb.contract([{"constant":false,"inputs":[{"name":"number","type":"uint256"}],"name":"constructor","outputs":[{"name":"result","type":"uint256"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"number","type":"uint256"}],"name":"calculateValue","outputs":[{"name":"result","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"anonymous":false,"inputs":[{"indexed":false,"name":"input","type":"uint256"},{"indexed":false,"name":"result","type":"uint256"}],"name":"Notify","type":"event"}]))
}

function getContractFromAddress(input) {
console.log(tronWeb.contract([input]))
}

function getEventByTransactionID(input) {
    //console.log("input:"+input)
    tronWeb.getEventByTransactionID(input).then(result => {console.log(result)})
}

function getEventResult(contractAddress, eventname, size_input) {
    tronWeb.getEventResult(contractAddress,{eventName:eventname,size:size_input}).then(result => {console.log(result)})

}

function getContract(input) {
    tronWeb.trx.getContract(input).then(result => console.log(JSON.stringify(result)))
}


switch(arguments[0]) {
     case "getContractFromAbi":
        getContractFromAbi()
        break;
     case "getContractFromAddress":
        getContractFromAddress(arguments[1])
        break;
     case "getEventByTransactionID":
        getEventByTransactionID(arguments[1])
        break;
     case "getEventResult":
        getEventResult(arguments[1],arguments[2],arguments[3])
        break;
    case "getContract":
        getContract(arguments[1])
        break;
     default:
        break;
}