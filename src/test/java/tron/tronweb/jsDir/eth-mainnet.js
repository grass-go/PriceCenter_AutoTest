const arguments = process.argv.splice(2);

/*const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);*/

const ethers = require('ethers')
const provider = new ethers.providers.JsonRpcProvider('https://mainnet.infura.io/v3/dfb752dd45204b8daae74249f4653584');


async function ethGetBalance(input) {
  const balance = await provider.getBalance(input);
  console.log(balance);
}


async function getCode(input) {
  const code = await provider.getCode(input);
  console.log(code);
}



async function getStorageAt(input1,input2) {
  const code = await provider.getStorageAt(
    input1,
    input2
  );
  console.log(code);
}

async function getBlock() {
  const block = await provider.getBlock();
  console.log(block);
}







switch(arguments[0]) {
    case "ethGetBalance":
        ethGetBalance(arguments[1])
        break;
    case "listExchangesPaginated":
        listExchangesPaginated()
        break;
    case "getBlockNumber":
        getBlockNumber()
        break;
    case "getCode":
        getCode(arguments[1])
        break;
    case "getStorageAt":
        getStorageAt(arguments[1],arguments[2])
        break;
    case "getBlock":
        getBlock()
        break;
     default:
        break;
}