const arguments = process.argv.splice(2);

const TronWeb = require('tronweb')
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider("https://api.trongrid.io");
const solidityNode = new HttpProvider("https://api.trongrid.io");
const eventServer = new HttpProvider("https://api.trongrid.io");
const privateKey = "3481E79956D4BD95F358AC96D151C976392FC4E3FC132F78A847906DE588C145";
const tronWeb = new TronWeb(fullNode,solidityNode,eventServer,privateKey);


function getTransaction(input) {
    tronWeb.trx.getTransaction(input).then(result => console.log(JSON.stringify(result)))
}

function getTransactionFromBlock(input) {
    tronWeb.trx.getTransactionFromBlock(input).then(result => console.log(JSON.stringify(result)))
}

function getTransactionFromBlock1(block,index) {
    tronWeb.trx.getTransactionFromBlock(Number(block),Number(index)).then(result => console.log(JSON.stringify(result)))
}

function getTransactionInfo(input) {
    tronWeb.trx.getTransactionInfo(input).then(result => console.log(JSON.stringify(result)))
}

function getApprovedList() {
    var trans = {'visible': true, 'signature': ['1fd210045f5bdcf375cd478cf46ff735f132281b990bc199acf1952bd438929d1d03e12de5ea7dcb89cff5b8cfc5d161661a5c1fe6a6a2422edb313b9139075300'], 'txID': 'ee188aaf5cf78729d2d14d4db698126da2d75ef78a43837dafd6e6f591d103a2', 'raw_data': {'contract': [{'parameter': {'value': {'amount': 125000000, 'owner_address': 'TN9RRaXkCFtTXRso2GdTZxSxxwufzxLQPP', 'to_address': 'TTSFjEG3Lu9WkHdp4JrWYhbGP6K1REqnGQ'}, 'type_url': 'type.googleapis.com/protocol.TransferContract'}, 'type': 'TransferContract'}], 'ref_block_bytes': 'c251', 'ref_block_hash': '5c685c92bf035e72', 'expiration': 1578299967000, 'timestamp': 1578299909600}, 'raw_data_hex': '0a02c25122085c685c92bf035e7240988c89d0f72d5a68080112640a2d747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e5472616e73666572436f6e747261637412330a1541859009fd225692b11237a6ffd8fdba2eb7140cca121541bf97a54f4b829c4e9253b26024b1829e1a3b112018c0b2cd3b70e0cb85d0f72d'}
    // var tr = {'visible':true,'signature':['79051496741d59b70080db0ca74c6e9fbfef943f5da495a842d052e298615cfd309d94fd17a2504e44904d24d8fd2d6df610032d5bb4ee88b47e6d4302a9b75100'],'txID':'1614dbf0d60ecd9b3b37434ed89320e49a0a94da491a0bb5a81c2b9b87fc5f82','raw_data':{'contract':[{'parameter':{'value':{'owner_address':'41ebae50590810b05d4b403f13766f213518edef65','votes':[{'vote_address':'4114f2c09d3de3fe82a71960da65d4935a30b24e1f','vote_count':10},{'vote_address':'4178c842ee63b253f8f0d2955bbc582c661a078c9d','vote_count':1}]},'type_url':'type.googleapis.com/protocol.VoteWitnessContract'},'type':'VoteWitnessContract'}],'ref_block_bytes':'7a6d','ref_block_hash':'d7ba39131fc4be0d','expiration':1595833797000,'timestamp':1595833744078},'raw_data_hex':'0a027a6d2208d7ba39131fc4be0d408893edf8b82e5a860108041281010a30747970652e676f6f676c65617069732e636f6d2f70726f746f636f6c2e566f74655769746e657373436f6e7472616374124d0a1541ebae50590810b05d4b403f13766f213518edef6512190a154114f2c09d3de3fe82a71960da65d4935a30b24e1f100a12190a154178c842ee63b253f8f0d2955bbc582c661a078c9d100170cef5e9f8b82e'}
    tronWeb.trx.getApprovedList(trans).then(result => console.log(JSON.stringify(result)))
}

function getUnconfirmedTransactionInfo(input) {
    tronWeb.trx.getUnconfirmedTransactionInfo(input).then(result => console.log(JSON.stringify(result)))
}

function getConfirmedTransaction(input) {
    tronWeb.trx.getConfirmedTransaction(input).then(result => console.log(JSON.stringify(result)))
}

switch(arguments[0]) {
    case "getTransaction":
        getTransaction(arguments[1]);
        break;
    case "getTransactionFromBlock":
        if (arguments.length === 2){
            getTransactionFromBlock(arguments[1])
        }else {

            getTransactionFromBlock1(arguments[1], arguments[2])
        }
        break;
    case "getTransactionInfo":
        getTransactionInfo(arguments[1]);
        break;
    case "getApprovedList":
        getApprovedList(arguments[1]);
        break;
    case "getUnconfirmedTransactionInfo":
        getUnconfirmedTransactionInfo(arguments[1]);
        break;
    case "getConfirmedTransaction":
        getConfirmedTransaction(arguments[1]);
        break;
    default:
        break;
}