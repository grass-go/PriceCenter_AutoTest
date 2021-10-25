const arguments = process.argv.splice(2);

/*const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);*/

const ethers = require('tronethers')
//const providerMainnet = new ethers.providers.JsonRpcProvider('http://101.200.46.37:50545');

const providerMainnet = new ethers.providers.JsonRpcProvider('http://47.95.206.44:50545');
/*const providerMainnet = new ethers.providers.JsonRpcProvider({ url: 'http://47.252.3.238:50546',
    fullHost: 'http://47.252.3.238:8090',});*/
//const providerNilenet = new ethers.providers.JsonRpcProvider('http://123.56.166.152:30080');

async function ethGetBalance(input) {
    const balance = await providerMainnet.getBalance(input);
    console.log(balance);
}


async function getCode(input) {
    const code = await providerMainnet.getCode(input);
    console.log(code);
}


async function getStorageAt(input1, input2) {
    const code = await providerMainnet.getStorageAt(
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

    const block = await providerMainnet.getBlock(num);
    console.log(block);
}


async function getBlockNumber() {
    const block = await providerMainnet.getBlockNumber();
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
    const block = await providerMainnet.getBlockWithTransactions(num);
    console.log(block);
}


async function estimateGas01(input1, input2, input3, input4) {
    const t = await providerMainnet.estimateGas({
        // Wrapped ETH address
        from: input1,
        to: input2,
        // `function deposit() payable`
        data: input3,
        // 1 ether
        value: input4,
    });
    console.log("====> estimateGas");
    console.log(t);
}


async function estimateGas02(input1, input2,input3) {
    const t = await providerMainnet.estimateGas({
        // Wrapped ETH address
        from: input1,
        // `function deposit() payable`
        data: input2,
        // 1 ether
        value: input3,
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

async function sendTrx(privateKey,toAccount) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
  //  console.log(signer);
    const txResp = await signer.sendTransaction({
        to: toAccount,
        value: BigInt(5678),
    });
    console.log(txResp);

}

async function getTransaction(blockHash) {

    const result = await providerMainnet.getTransaction(blockHash);
     console.log(result);

}



async function deployContract(privateKey) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const abi = [{
        "inputs": [],
        "stateMutability": "payable",
        "type": "constructor"
    }, {
        "inputs": [],
        "name": "Storage",
        "outputs": [],
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "inputs": [{
            "internalType": "address payable",
            "name": "toAddress",
            "type": "address"
        }, {
            "internalType": "trcToken",
            "name": "id",
            "type": "trcToken"
        }, {
            "internalType": "uint256",
            "name": "amount",
            "type": "uint256"
        }],
        "name": "TransferTokenTo",
        "outputs": [],
        "stateMutability": "payable",
        "type": "function"
    }, {
        "inputs": [],
        "name": "getResultInCon",
        "outputs": [{
            "internalType": "trcToken",
            "name": "",
            "type": "trcToken"
        }, {
            "internalType": "uint256",
            "name": "",
            "type": "uint256"
        }, {
            "internalType": "uint256",
            "name": "",
            "type": "uint256"
        }],
        "stateMutability": "payable",
        "type": "function"
    }, {
        "inputs": [],
        "name": "msgTokenValueAndTokenIdTest",
        "outputs": [{
            "internalType": "trcToken",
            "name": "",
            "type": "trcToken"
        }, {
            "internalType": "uint256",
            "name": "",
            "type": "uint256"
        }, {
            "internalType": "uint256",
            "name": "",
            "type": "uint256"
        }],
        "stateMutability": "payable",
        "type": "function"
    }];



    const bytecode = '0x6080604052d3600255d26003553460045561001861001d565b610038565b6104d2600090815533815260016020526040902061162e9055565b6101ea806100476000396000f3fe60806040526004361061003f5760003560e01c806305c24200146100445780633be9ece71461006457806371dc08ce14610079578063ee3711be14610081575b600080fd5b61004c6100b0565b60405161005b9392919061019e565b60405180910390f35b610077610072366004610156565b6100be565b005b61004c610133565b34801561008d57600080fd5b50d3801561009a57600080fd5b50d280156100a757600080fd5b5061007761013b565b600254600354600454909192565b6001600160a01b03831681156108fc028284801580156100dd57600080fd5b5080678000000000000000111580156100f557600080fd5b5080620f42401015801561010857600080fd5b50604051600081818185878a8ad094505050505015801561012d573d6000803e3d6000fd5b50505050565bd3d234909192565b6104d2600090815533815260016020526040902061162e9055565b60008060006060848603121561016a578283fd5b83356001600160a81b0381168114610180578384fd5b6001600160a01b031695602085013595506040909401359392505050565b928352602083019190915260408201526060019056fea2646970667358221220273f33489ace19b28597ec7d4cbd832887b1f5b3936496aa1f65a65dd2e30a9b64736f6c63430008000033';
    const factory = new ethers.ContractFactory(abi, bytecode, signer);
    const contract = await factory.deploy();
    console.log(contract);
    console.log(contract.address)

}


async function triggerContract(privateKey) {

    const abi1 = [
        {
            anonymous: false,
            inputs: [
                {
                    indexed: true,
                    internalType: 'uint256',
                    name: 'name',
                    type: 'uint256',
                },
                {
                    indexed: false,
                    internalType: 'address',
                    name: 'to',
                    type: 'address',
                },
            ],
            name: 'ChangeName',
            type: 'event',
        },
        {
            inputs: [],
            name: 'name',
            outputs: [
                {
                    internalType: 'uint256',
                    name: '',
                    type: 'uint256',
                },
            ],
            stateMutability: 'view',
            type: 'function',
        },
        {
            inputs: [
                {
                    internalType: 'uint256',
                    name: '_name',
                    type: 'uint256',
                },
            ],
            name: 'setName',
            outputs: [],
            stateMutability: 'nonpayable',
            type: 'function',
        },
    ];



    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const contractAddress = '0x92b9eA8Aac481b979D40b97dCa0f1c789D749435';



    const contract = new ethers.Contract(contractAddress, abi1, signer);
    const txResp = await contract.setName(1000, {gas: 1000000});
    console.log(txResp);

}


async function callContract(privateKey) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const contractAddress = '0x92b9eA8Aac481b979D40b97dCa0f1c789D749435';

    const abi2 = [
        {
            anonymous: false,
            inputs: [
                {
                    indexed: true,
                    internalType: 'uint256',
                    name: 'name',
                    type: 'uint256',
                },
                {
                    indexed: false,
                    internalType: 'address',
                    name: 'to',
                    type: 'address',
                },
            ],
            name: 'ChangeName',
            type: 'event',
        },
        {
            inputs: [],
            name: 'name',
            outputs: [
                {
                    internalType: 'uint256',
                    name: '',
                    type: 'uint256',
                },
            ],
            stateMutability: 'view',
            type: 'function',
        },
        {
            inputs: [
                {
                    internalType: 'uint256',
                    name: '_name',
                    type: 'uint256',
                },
            ],
            name: 'setName',
            outputs: [],
            stateMutability: 'nonpayable',
            type: 'function',
        },
    ];




    const contract = new ethers.Contract(contractAddress, abi2, signer);

    const txResp = await contract.name();
    console.log(txResp);

}




switch  (arguments[0]) {
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
        getBlockNumber()
        break;
    case "getBlockWithTransactions":
        getBlockWithTransactions(arguments[1])
        break;
    case "estimateGas01":
        estimateGas01(arguments[1], arguments[2], arguments[3], arguments[4])
        break;
    case "estimateGas02":
        estimateGas02(arguments[1], arguments[2], arguments[3], arguments[4])
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
    case "sendTrx":
        sendTrx(arguments[1],arguments[2])
        break;
    case "deployContract":
        deployContract(arguments[1])
        break;
    case "triggerContract":
        triggerContract(arguments[1])
        break;

    case "callContract":
        callContract(arguments[1])
        break;
    case "getTransaction":
        getTransaction(arguments[1])
        break;

    default:
        break;
}