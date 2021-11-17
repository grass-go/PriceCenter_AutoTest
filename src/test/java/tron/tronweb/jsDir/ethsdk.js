const arguments = process.argv.splice(2);

/*const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,efventServer,privateKey);*/

const ethers = require('tronethers')
//010
const providerMainnet = new ethers.providers.JsonRpcProvider({
    url: 'http://101.200.46.37:50545',
    fullHost: 'http://101.200.46.37:50091'
});

//021
//const providerMainnet = new ethers.providers.JsonRpcProvider({url:'http://47.95.206.44:50545',fullHost: 'http://47.95.206.44:50090'});


//nile
/*const providerMainnet = new ethers.providers.JsonRpcProvider({ url: 'http://47.252.3.238:50546',
    fullHost: 'http://47.252.3.238:8090',});*/



async function ethGetBalanceUseProvider(input) {
    const balance = await providerMainnet.getBalance(input);
    console.log(balance);
}

async function ethGetBalanceUseSigner(privateKey, input) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const balance = await signer.getBalance(input);
    console.log(balance);
}

async function getChainId(privateKey) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const chainId = await signer.getChainId();
    console.log(chainId);
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


async function estimateGas01WithProvider(input1, input2, input3, input4) {
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

async function estimateGas01WithSigner(privateKey, input1, input2, input3, input4) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const t = await signer.estimateGas({
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


async function estimateGas02WithProvider(input1, input2, input3) {
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


async function estimateGas02WithSigner(privateKey, input1, input2, input3) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const t = await signer.estimateGas({
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


async function call(input1, input2, input3) {
    const t = await providerMainnet.call({
        // Wrapped ETH address
        from: input1,
        // `function deposit() payable`
        to: input2,
        // 1 ether
        data: input3,
    });
    console.log(t);
}

async function fromMnemonic(toAccount) {

    const wallet = await new ethers.Wallet.fromMnemonic(
        'man truth minor fancy safe they lake parent appear pull rebel bomb'
    );

    const signer = wallet.connect(providerMainnet)

    const txResp = await signer.sendTransaction({
        to: toAccount,
        value: BigInt(5678),
    });
    console.log(txResp);
}


async function fromMnemonic1(toAccount) {


    console.log(ethers.utils.getAddress(ethers.Wallet.fromMnemonic( 'office vicious language order rival physical custom anger receive youth crystal wish',"m/44'/195'/0'/0/0" ).address,3))
    console.log(ethers.utils.getAddress(ethers.Wallet.fromMnemonic( 'office vicious language order rival physical custom anger receive youth crystal wish',"m/44'/195'/1'/0/0" ).address,3))
    console.log(ethers.utils.getAddress(ethers.Wallet.fromMnemonic( 'office vicious language order rival physical custom anger receive youth crystal wish',"m/44'/195'/2'/0/0" ).address,3))
    console.log(ethers.utils.getAddress(ethers.Wallet.fromMnemonic( 'office vicious language order rival physical custom anger receive youth crystal wish',"m/44'/195'/3'/0/0" ).address,3))
    console.log(ethers.utils.getAddress(ethers.Wallet.fromMnemonic( 'office vicious language order rival physical custom anger receive youth crystal wish',"m/44'/195'/4'/0/0" ).address,3))

}


async function createRandom(toAccount) {

    const wallet = await new ethers.Wallet.createRandom();
//    console.log(wallet);
    console.log( wallet.address)
    const signer = wallet.connect(providerMainnet)
    console.log(signer.provider)


}


async function mnemonic() {

    const wallet = await new ethers.Wallet.createRandom();
    const mnemonic = wallet._mnemonic();
    console.log(mnemonic.phrase);
}

async function createWalletByPrivate(privateKey,toAccount) {

    const wallet =  await new ethers.Wallet(privateKey, providerMainnet);

    const signer = wallet.connect(providerMainnet)

    const txResp = await signer.sendTransaction({
        to: toAccount,
        value: BigInt(5678),
    });
    console.log(txResp);

}

async function fromEncryptedJson(toAccount) {
    const jsonString = `{"address":"419f2e05d49b5fe66dce55598984aace7b3dc45fb0","id":"19d3b9dd-36f4-4a0e-b2f7-fa4fcd7df1f5","version":3,"crypto":{"cipher":"aes-128-ctr","cipherparams":{"iv":"a222160e1e1d81e47efaaab813e5e017"},"ciphertext":"051546605fa643d905517b30ad52cf18ea1cad9a14bdcbad5a8572f978557700","kdf":"scrypt","kdfparams":{"salt":"9bcde9d303bb95a027039471747167c6feb4a83cbdbfe2eca6e4f1ea15a0d72d","n":131072,"dklen":32,"p":1,"r":8},"mac":"6252ff1956eb930e929462474204974dc911cea0237844b607e6803b0b4abc28"}}`;
    const wallet =  await new ethers.Wallet.fromEncryptedJson(jsonString, '123123123');

    const signer = wallet.connect(providerMainnet)

    const txResp = await signer.sendTransaction({
        to: toAccount,
        value: BigInt(5678),
    });
    console.log(txResp);
}

async function fromEncryptedJsonSync(toAccount) {
    const jsonString = `{"address":"419f2e05d49b5fe66dce55598984aace7b3dc45fb0","id":"19d3b9dd-36f4-4a0e-b2f7-fa4fcd7df1f5","version":3,"crypto":{"cipher":"aes-128-ctr","cipherparams":{"iv":"a222160e1e1d81e47efaaab813e5e017"},"ciphertext":"051546605fa643d905517b30ad52cf18ea1cad9a14bdcbad5a8572f978557700","kdf":"scrypt","kdfparams":{"salt":"9bcde9d303bb95a027039471747167c6feb4a83cbdbfe2eca6e4f1ea15a0d72d","n":131072,"dklen":32,"p":1,"r":8},"mac":"6252ff1956eb930e929462474204974dc911cea0237844b607e6803b0b4abc28"}}`;
    const wallet =  await new ethers.Wallet.fromEncryptedJson(jsonString, '123123123');
    const signer = wallet.connect(providerMainnet)

    const txResp = await signer.sendTransaction({
        to: toAccount,
        value: BigInt(5678),
    });
    console.log(txResp);
}



async function getGasPriceWithProvider() {
    const price = await providerMainnet.getGasPrice();
    console.log(price);
}

async function getGasPriceWithSigner(privateKey) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const price = await signer.getGasPrice();
    console.log(price);
}


async function getAddress(privateKey) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const address = await signer.getAddress();
    console.log(address);
}

async function getNetwork() {
    const network = await providerMainnet.getNetwork();
    console.log(network);
}

async function getTransactionReceipt(input) {
    const receipt = await providerMainnet.getTransactionReceipt(input);
    console.log(receipt);
}

async function sendTrx(privateKey, toAccount) {
    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const txResp = await signer.sendTransaction({
        to: toAccount,
        value: BigInt(5678),
    });
    console.log(txResp);

}



async function triggerWithSendTransaction() {

    const signer = new ethers.Wallet("b32e71eab2f7b3057cde9ea6a489a404b7e0f09320a86671f3addcf1581979f5", providerMainnet);

    const txResp = await signer.sendTransaction({

        "to": "419F2E05D49B5FE66DCE55598984AACE7B3DC45FB1",
        "data": "a9059cbb00000000000000000000000069319eA845B1c35A1f7b0E1429f4F303e8F791330000000000000000000000000000000000000000000000000000000000000001",
        "gas": "0x245498",
        "value": "0xA"
    });
    console.log(txResp);


}


async function deployWithSendTransaction() {
    const signer = new ethers.Wallet("b32e71eab2f7b3057cde9ea6a489a404b7e0f09320a86671f3addcf1581979f5", providerMainnet);
    //console.log(signer);
    //  console.log(111111);
    const txResp = await signer.sendTransaction({

        "name": "transferTokenContract",
        "gas": "0x245498",
        "abi": "[{\"constant\":false,\"inputs\":[],\"name\":\"getResultInCon\",\"outputs\":[{\"name\":\"\",\"type\":\"trcToken\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"toAddress\",\"type\":\"address\"},{\"name\":\"id\",\"type\":\"trcToken\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"TransferTokenTo\",\"outputs\":[],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"msgTokenValueAndTokenIdTest\",\"outputs\":[{\"name\":\"\",\"type\":\"trcToken\"},{\"name\":\"\",\"type\":\"uint256\"},{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":true,\"stateMutability\":\"payable\",\"type\":\"constructor\"}]\n",
        "data": "6080604052d3600055d2600155346002556101418061001f6000396000f3006080604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166305c24200811461005b5780633be9ece71461008157806371dc08ce146100aa575b600080fd5b6100636100b2565b60408051938452602084019290925282820152519081900360600190f35b6100a873ffffffffffffffffffffffffffffffffffffffff600435166024356044356100c0565b005b61006361010d565b600054600154600254909192565b60405173ffffffffffffffffffffffffffffffffffffffff84169082156108fc029083908590600081818185878a8ad0945050505050158015610107573d6000803e3d6000fd5b50505050565bd3d2349091925600a165627a7a72305820a2fb39541e90eda9a2f5f9e7905ef98e66e60dd4b38e00b05de418da3154e7570029",
        "value": "0x1f4",
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
    const contract = await factory.deploy({gas: 1000000});
    console.log(111111)
    console.log(contract);
    console.log(contract.address)
    console.log(2222222)

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





async function triggerContract1(privateKey) {

    const abi1 = [{
        "outputs": [{
            "type": "uint256"
        }],
        "name": "getUint",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "bytes"
        }],
        "name": "getBytes",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "bool"
        }],
        "name": "getBool",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int256[2][2]"
        }],
        "inputs": [{
            "name": "param",
            "type": "int256[2][2]"
        }],
        "name": "changeInt256Array",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "bytes32"
        }],
        "name": "getBytes32",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "uint8"
        }],
        "name": "getActionChoices",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "address"
        }],
        "name": "getAddress",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "bytes32"
        }],
        "inputs": [{
            "name": "param",
            "type": "bytes32"
        }],
        "name": "changeBytes32",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int64[]"
        }],
        "name": "getInt64NegativeArray",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int256"
        }],
        "name": "getNegativeInt",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "string"
        }],
        "inputs": [{
            "name": "param",
            "type": "string"
        }],
        "name": "changeString",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int256"
        }],
        "name": "getInt",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "bool"
        }],
        "inputs": [{
            "name": "param",
            "type": "bool"
        }],
        "name": "changeBool",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int256[2][2]"
        }],
        "name": "getInt256Array",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "string"
        }],
        "name": "getString",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "uint8"
        }],
        "inputs": [{
            "name": "param",
            "type": "uint8"
        }],
        "name": "changeActionChoices",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "uint256"
        }],
        "inputs": [{
            "name": "param",
            "type": "uint256"
        }],
        "name": "changeUint",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int32[2][]"
        }],
        "inputs": [{
            "name": "param",
            "type": "int32[2][]"
        }],
        "name": "changeInt32Array",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int64[]"
        }],
        "inputs": [{
            "name": "param",
            "type": "int64[]"
        }],
        "name": "changeInt64NegativeArray",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "uint256"
        }],
        "constant": true,
        "inputs": [{
            "type": "address"
        }],
        "name": "mapa",
        "stateMutability": "view",
        "type": "function"
    }, {
        "outputs": [{
            "type": "uint256"
        }],
        "inputs": [{
            "name": "param",
            "type": "uint256"
        }],
        "name": "setMapping",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int256"
        }],
        "inputs": [{
            "name": "param",
            "type": "int256"
        }],
        "name": "changeNegativeInt",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int32[2][]"
        }],
        "name": "getInt32Array",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "int256"
        }],
        "inputs": [{
            "name": "param",
            "type": "int256"
        }],
        "name": "changeInt",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "bytes"
        }],
        "inputs": [{
            "name": "param",
            "type": "bytes"
        }],
        "name": "changeBytes",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "outputs": [{
            "type": "address"
        }],
        "inputs": [{
            "name": "param",
            "type": "address"
        }],
        "name": "changeAddress",
        "stateMutability": "nonpayable",
        "type": "function"
    }, {
        "payable": true,
        "stateMutability": "payable",
        "type": "constructor"
    }, {
        "inputs": [{
            "type": "int256"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "uint256"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "bool"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "address"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "bytes32"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "bytes"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "string"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "uint8"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "int64[]"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "int32[2][]"
        }],
        "name": "log",
        "type": "event"
    }, {
        "inputs": [{
            "type": "int256[2][2]"
        }],
        "name": "log",
        "type": "event"
    }];


    const signer = new ethers.Wallet(privateKey, providerMainnet);
    const contractAddress = '0xC0B18081916B857FBDC9F2B330FD2711BF9FD729';


    const contract = new ethers.Contract(contractAddress, abi1, signer);

    const txResp = await contract.changeInt32Array([[3,3],[3,4],[5,6]],{ gas: 1000000 });
    console.log(txResp);

}


async function connect(privateKey,toAccount){

    const wallet = new ethers.Wallet(privateKey);
    const signer = wallet.connect(providerMainnet)

    const txResp = await signer.sendTransaction({
        to: toAccount,
        value: BigInt(5678),
    });
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


switch (arguments[0]) {
    case "ethGetBalanceUseSigner":
        ethGetBalanceUseSigner(arguments[1])
        break;
    case "ethGetBalanceUseProvider":
        ethGetBalanceUseProvider(arguments[1])
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


    case "estimateGas01WithProvider":
        estimateGas01WithProvider(arguments[1], arguments[2], arguments[3], arguments[4])
        break;
    case "estimateGas01WithSigner":
        estimateGas01WithSigner(arguments[1], arguments[2], arguments[3], arguments[4])
        break;
    case "estimateGas02WithProvider":
        estimateGas02WithProvider(arguments[1], arguments[2], arguments[3], arguments[4])
        break;

    case "estimateGas02WithSigner":
        estimateGas02WithSigner(arguments[1], arguments[2], arguments[3], arguments[4])
        break;
    case "getGasPriceWithProvider":
        getGasPriceWithProvider()
        break;
    case "getGasPriceWithSigner":
        getGasPriceWithSigner(arguments[1])
        break;
    case "getNetwork":
        getNetwork()
        break;
    case "getTransactionReceipt":
        getTransactionReceipt(arguments[1])
        break;
    case "sendTrx":
        sendTrx(arguments[1], arguments[2])
        break;
    case "deployContract":
        deployContract(arguments[1])
        break;
    case "triggerContract":
        triggerContract(arguments[1])
        break;
    case "triggerContract1":
        triggerContract1(arguments[1])
        break;

    case "callContract":
        callContract(arguments[1])
        break;
    case "getTransaction":
        getTransaction(arguments[1])
        break;
    case "call":
        call(arguments[1], arguments[2], arguments[3])
        break;
    case "getChainId":
        getChainId(arguments[1])
        break;
    case "getAddress":
        getAddress(arguments[1])
        break;
    case "triggerWithSendTransaction":
        triggerWithSendTransaction()
        break;
    case "deployWithSendTransaction":
        deployWithSendTransaction()
        break;
    case "fromMnemonic":
        fromMnemonic(arguments[1])
        break;

    case "fromMnemonic1":
        fromMnemonic1(arguments[1])
        break;
    case "createRandom":
        createRandom(arguments[1])
        break;
    case "mnemonic":
        mnemonic()
        break;
    case "createWalletByPrivate":
        createWalletByPrivate(arguments[1],arguments[2])
        break;
    case "fromEncryptedJson":
        fromEncryptedJson(arguments[1])
        break;
    case "fromEncryptedJsonSync":
        fromEncryptedJsonSync(arguments[1])
        break;
    case "connect":
        connect(arguments[1],arguments[2])
        break;
    default:
        break;
}

