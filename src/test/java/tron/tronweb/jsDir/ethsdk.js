const arguments = process.argv.splice(2);

/*const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);*/

const ethers = require('tronethers')
const providerMainnet = new ethers.providers.JsonRpcProvider('http://47.95.206.44:50545');
const providerNilenet = new ethers.providers.JsonRpcProvider('http://123.56.166.152:30080');

async function ethGetBalance(input) {
  const balance = await providerMainnet.getBalance(input);
  console.log(balance);
}


async function getCode(input) {
  const code = await providerMainnet.getCode(input);
  console.log(code);
}



async function getStorageAt(input1,input2) {
  const code = await providerMainnet.getStorageAt(
    input1,
    input2
  );
  console.log(code);
}

async function getBlock() {
  const block = await providerMainnet.getBlock();
  console.log(block);
}




async function getBlockNumber() {
  const block = await providerMainnet.getBlockNumber();
  console.log(block);
}


async function getBlockWithTransactions(input) {
  const block = await providerMainnet.getBlockWithTransactions(input);
  console.log(block);
}


async function estimateGas(input1,input2) {
  const t = await providerMainnet.estimateGas({
    // Wrapped ETH address
    to: input1,

    // `function deposit() payable`
    data: input2,

    // 1 ether
    value: 0,
  });
  console.log("====> estimateGas");
  console.log(t);
}


async function getGasPrice() {
  const price = await providerMainnet.getGasPrice();
  console.log(price);
}

async function getNetwork() {
  const network = await providerMainnet.getNetwork();
  console.log(network);
}

async function getTransactionReceipt(input) {
  const receipt = await providerMainnet.getTransactionReceipt(input);
  console.log(receipt);
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
    case "getBlockNumber":
        getBlockNumber()
        break;
    case "getBlockWithTransactions":
        getBlockWithTransactions(arguments[1])
        break;
    case "estimateGas":
        estimateGas(arguments[1],arguments[2])
        break;
    case "getGasPrice":
        getGasPrice()
        break;
    case "getNetwork":
        getNetwork()
        break;
    case "getTransactionReceipt":
        getTransactionReceipt(arguments[1])
        break;
     default:
        break;
}