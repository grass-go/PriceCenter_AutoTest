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


async function getStorageAt(input1, input2) {
    const code = await provider.getStorageAt(
        input1,
        input2
    );
    console.log(code);
}

async function getBlock(input) {
    let num;
    if (input != null) {
        if (!input.startsWith('0x')) {
            num = Number(input)
        } else {
            num = input
        }
    }
    const block = await provider.getBlock(num);
    console.log(block);
}


async function getBlockNumber(input) {
    const block = await provider.getBlockNumber(input);
    console.log(block);
}


async function getBlockWithTransactions(input) {
    let num;
    if (input != null) {
        if (!input.startsWith('0x')) {
            num = Number(input)
        } else {
            num = input
        }
    }
    const block = await provider.getBlockWithTransactions(num);
    console.log(block);
}


async function estimateGas(input1, input2, input3) {
    const t = await provider.estimateGas({
        // Wrapped ETH address
        to: input1,

        // `function deposit() payable`
        data: input2,

        // 1 ether
        value: input3,
    });
    console.log("====> estimateGas");
    console.log(t);
}

async function getGasPrice() {
    const price = await provider.getGasPrice();
    console.log("====> getGasPrice");
    console.log(price);
}


async function getNetworkWithEmpty() {
    const network = await provider.getNetwork();
    console.log(network);
}

async function getNetwork(input) {
    const network = await provider.getNetwork(input);
    console.log(network);
}


async function getTransactionReceipt(input) {
    const receipt = await provider.getTransactionReceipt(input);
    console.log(receipt);
}


switch (arguments[0]) {
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
        getStorageAt(arguments[1], arguments[2])
        break;
    case "getBlock":
        getBlock(arguments[1])
        break;
    case "getBlockNumber":
        getBlockNumber(arguments[1])
        break;
    case "getBlockWithTransactions":
        getBlockWithTransactions(arguments[1])
        break;
    case "estimateGas":
        estimateGas(arguments[1], arguments[2])
        break;
    case "getGasPrice":
        getGasPrice()
        break;
    case "getNetworkWithEmpty":
        getNetworkWithEmpty()
        break;
    case "getNetwork":
        getNetwork(arguments[1])
        break;
    case "getTransactionReceipt":
        getTransactionReceipt(arguments[1])
        break;
    default:
        break;
}