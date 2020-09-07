const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);




function getChainParameters() {
    tronWeb.trx.getChainParameters().then(result => console.log(JSON.stringify(result)))
}

function getNodeInfo() {
    tronWeb.trx.getNodeInfo().then(result => console.log(JSON.stringify(result)))
}

function listExchanges() {
    tronWeb.trx.listExchanges().then(result => console.log(JSON.stringify(result)))
}

async function listExchangesPaginated(Limit,Offset) {
    let list = await tronWeb.trx.listExchangesPaginated(2, 0);
    console.log(JSON.stringify(list))
}

function listNodes() {
    tronWeb.trx.listNodes().then(result => console.log(result))
}

function listProposals() {
    tronWeb.trx.listProposals().then(result => console.log(JSON.stringify(result)))
}

function listSuperRepresentatives() {
    tronWeb.trx.listSuperRepresentatives().then(result => console.log(JSON.stringify(result)))
}

function listTokens() {
    tronWeb.trx.listTokens().then(result => console.log(JSON.stringify(result)))
}

function getExchangeByID(input) {
    tronWeb.trx.getExchangeByID(input).then(result => console.log(JSON.stringify(result)))
}

function timeUntilNextVoteCycle() {
    tronWeb.trx.timeUntilNextVoteCycle().then(result => console.log(result))
}


switch(arguments[0]) {
    case "getChainParameters":
        getChainParameters()
        break;
    case "getNodeInfo":
        getNodeInfo()
        break;
    case "listExchanges":
        listExchanges()
        break;
    case "listExchangesPaginated":
        listExchangesPaginated(arguments[1],arguments[2])
        break;
    case "listNodes":
        listNodes()
        break;
    case "listProposals":
        listProposals()
        break;
    case "listSuperRepresentatives":
        listSuperRepresentatives()
        break;
    case "listTokens":
        listTokens()
        break;
    case "getExchangeByID":
        getExchangeByID(arguments[1])
        break;
    case "timeUntilNextVoteCycle":
        timeUntilNextVoteCycle()
        break;
     default:
        break;
}