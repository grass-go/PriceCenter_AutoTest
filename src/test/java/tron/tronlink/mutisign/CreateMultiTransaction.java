package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.spongycastle.util.encoders.Hex;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.tron.api.GrpcAPI;
import org.tron.common.parameter.CommonParameter;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Commons;
import org.tron.core.exception.CancelException;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.*;
import tron.common.TronlinkApiList;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CreateMultiTransaction {
    private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;
    private String fullnode = "47.252.19.181:50051";  //线上
    private ManagedChannel channelFull = null;
    HttpResponse res;
    private JSONObject responseContent;
    String key1="c21f0c6fdc467f4ae7dc4c1a0b802234a930bff198ce34fde6850b0afd383cf5"; //线上
//    byte[] address1=TronlinkApiList.getFinalAddress(key1);

    String address158= "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
    byte[] address1 = Commons.decode58Check(address158);
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2=TronlinkApiList.getFinalAddress(key2);
    String address258=Base58.encode(address2);

    String quince58 = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
    byte[] quince = Commons.decode58Check(quince58);
    String quincekey = "b47e686119f2236f38cd0e8a4fe20f8a7fc5cb4284d36131f447c63857e3dac9";
    String yunli58 = "TYyptUzArG7hUprUCUk2kzBrY4faKD4ioz";
    byte[] yunli = Commons.decode58Check(yunli58);
    String yunlikey = "cafcc392b9b5518324728a9c43c7d857d6d2766669177ea7515e92f8918ab106";
    String wqq1key = "8d5c18030466b6ab0e5367154d15c4f6cb46d2fb56a0b552e017d183abd8c255";
    byte[] wqq1 = TronlinkApiList.getFinalAddress(wqq1key);
    String wqq158 =Base58.encode(wqq1);
    private byte[] byteCode;

    /**
     * constructor.
     */
    @BeforeClass(enabled = true)
    public void beforeClass() {

        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
    }

    @Test(enabled = true,description = "nulti sign send coin")
    public void sendCoin() {
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(wqq1, 5, quince, blockingStubFull);
        log.info("-----111111  "+ JsonFormat.printToString(transaction));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 6, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = true,description = "multi sign freeze balandce")
    public void freezeBalandce() throws Exception{
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        ByteString byteAddreess = ByteString.copyFrom(quince);
        builder.setOwnerAddress(byteAddreess).setFrozenBalance(1000000)
                .setFrozenDuration(3).setResourceValue(0);
        BalanceContract.FreezeBalanceContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.freezeBalance(contract);
        log.info("0000 "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 5, blockingStubFull);
        log.info("-----111  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    //modify coin id before go prod
    //modify permission id before go prod
    @Test(enabled = true,description = "multi sign transfer asset")
    public void transferTrc10() throws Exception{
        AssetIssueContractOuterClass.TransferAssetContract.Builder builder = AssetIssueContractOuterClass.TransferAssetContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(wqq1);
        ByteString bsName = ByteString.copyFrom("1000323".getBytes());
        ByteString bsOwner = ByteString.copyFrom(quince);
        builder.setToAddress(bsTo);
        builder.setAssetName(bsName);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(1);

        AssetIssueContractOuterClass.TransferAssetContract contract = builder.build();
        log.info("-----0000  "+JsonFormat.printToString(contract));
        Protocol.Transaction transaction = blockingStubFull.transferAsset(contract);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 5, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = true,description = "nulti sign transfer trc20")
    public void transferTrc20() throws Exception{

        //String contractAddress = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        String contractAddress = "TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "0000000000000000000000000c497701e37f11b042bdc7aabfc0cd5d45f7a0c70000000000000000000000000000000000000000000000000000000000000001";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd,"transfer(address,uint256)",args,true,0,maxFeeLimit,"0",0L,
                quince,blockingStubFull);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));



        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }
    @Test(enabled = true,description = "nulti sign transfer trc20")
    public void transferTrc721() throws Exception {
        String contractAddress = "TQD3TUrfQbLsCBKPgP9UVvv7iyReG9BHts";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "000000000000000000000000e7d71e72ea48de9144dc2450e076415af0ea745f0000000000000000000000000c497701e37f11b042bdc7aabfc0cd5d45f7a0c70000000000000000000000000000000000000000000000000000000000000457";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd,"transferFrom(address,address,uint256)",args,true,0,maxFeeLimit,"0",0L,
                quince,blockingStubFull);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));



        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }
    @Test(enabled = true,description = "multiSignTest: create Asset Issue")
    public void createAssetIssue() throws Exception {
        long now = System.currentTimeMillis();
        long totalSupply = 10000L;
        Long start = System.currentTimeMillis() + 300000;
        Long end = System.currentTimeMillis() + 1000000000;
        AssetIssueContractOuterClass.AssetIssueContract.Builder builder = AssetIssueContractOuterClass.AssetIssueContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(yunli));
        String token10Name = "multiGen10qwer221";
        builder.setName(ByteString.copyFrom(token10Name.getBytes()));
        builder.setTotalSupply(totalSupply);
        builder.setTrxNum(1);
        builder.setNum(1);
        builder.setStartTime(start);
        builder.setEndTime(end);
        builder.setVoteScore(1);
        String description = "This is multi-sign generated Token";
        builder.setDescription(ByteString.copyFrom(description.getBytes()));
        String url = "www.address1.com";
        builder.setUrl(ByteString.copyFrom(url.getBytes()));
        builder.setFreeAssetNetLimit(2000L);
        builder.setPublicFreeAssetNetLimit(2000L);
        AssetIssueContractOuterClass.AssetIssueContract.FrozenSupply.Builder frozenBuilder = AssetIssueContractOuterClass.AssetIssueContract.FrozenSupply
                .newBuilder();
        frozenBuilder.setFrozenAmount(1L);
        frozenBuilder.setFrozenDays(111L);
        builder.addFrozenSupply(0, frozenBuilder);

        GrpcAPI.TransactionExtention assetIssue2 = blockingStubFull.createAssetIssue2(builder.build());
        log.info(assetIssue2.toString());
        Protocol.Transaction transaction = assetIssue2.getTransaction();
        if (transaction == null || transaction.getRawData().getContractCount() == 0) {
            log.info("transaction == null");
            Assert.assertNotNull(transaction);
            Assert.assertFalse(transaction.getRawData().getContractCount() == 0);
        }
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, quincekey, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",quince58);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
        /*Protocol.Transaction transaction2 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction1, key1, 3, blockingStubFull);

        GrpcAPI.Return response = blockingStubFull.broadcastTransaction(transaction2);
        log.info(response.toString());*/


    }

    @Test(enabled = true,description = "multiSignTest: create Asset Issue")
    public void updateAssetIssue() throws Exception {
        AssetIssueContractOuterClass.UpdateAssetContract.Builder builder = AssetIssueContractOuterClass.UpdateAssetContract.newBuilder();
        ByteString basAddreess = ByteString.copyFrom(quince);
        String description ="multi sign";
        builder.setDescription(ByteString.copyFrom(description.getBytes()));
        String url="www.multisignModified.com";
        builder.setUrl(ByteString.copyFrom(url.getBytes()));
        //builder.setNewLimit(100L);
        //builder.setNewPublicLimit(100L);
        builder.setOwnerAddress(basAddreess);

        AssetIssueContractOuterClass.UpdateAssetContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.updateAsset(contract);
        Assert.assertNotNull(transaction);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }

    //address1 have multi permission of address2.
    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void ParticipateAssetissue() throws Exception {
        AssetIssueContractOuterClass.ParticipateAssetIssueContract.Builder builder = AssetIssueContractOuterClass.ParticipateAssetIssueContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(address1);
        String assertName = "1000418";
        ByteString bsName = ByteString.copyFrom(assertName.getBytes());
        ByteString bsOwner = ByteString.copyFrom(address2);
        builder.setToAddress(bsTo);
        builder.setAssetName(bsName);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(10L);
        AssetIssueContractOuterClass.ParticipateAssetIssueContract contract = builder.build();
        //Protocol.Transaction transaction = blockingStubFull.participateAssetIssue2(contract);


        GrpcAPI.TransactionExtention participateAssetIssue2 = blockingStubFull.participateAssetIssue2(builder.build());
        Protocol.Transaction transaction = participateAssetIssue2.getTransaction();
        if (transaction == null || transaction.getRawData().getContractCount() == 0) {
            log.info("transaction == null");
            Assert.assertNotNull(transaction);
            Assert.assertFalse(transaction.getRawData().getContractCount() == 0);
        }

        Assert.assertNotNull(transaction);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key1, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void createSmartContract() throws Exception {

        String contractName = "QQToken";

        String code = "608060405234801561001057600080fd5b50d3801561001d57600080fd5b50d2801561002a57600080fd5b506040518060400160405280600b81526020016a5175696e63654d756c746960a81b8152506040518060400160405280600b81526020016a5155494e43454d554c544960a81b815250600c826003908051906020019061008b929190610244565b50815161009f906004906020850190610244565b506005805460ff191660ff92909216919091179055506100da9050336100c36100df565b60ff16600a0a6305f5e100026100e960201b60201c565b6102dc565b60055460ff165b90565b6001600160a01b038216610144576040805162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f206164647265737300604482015290519081900360640190fd5b61015d816002546101e360201b61078c1790919060201c565b6002556001600160a01b0382166000908152602081815260409091205461018d91839061078c6101e3821b17901c565b6001600160a01b0383166000818152602081815260408083209490945583518581529351929391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a35050565b60008282018381101561023d576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b9392505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061028557805160ff19168380011785556102b2565b828001600101855582156102b2579182015b828111156102b2578251825591602001919060010190610297565b506102be9291506102c2565b5090565b6100e691905b808211156102be57600081556001016102c8565b6108ad806102eb6000396000f3fe608060405234801561001057600080fd5b50d3801561001d57600080fd5b50d2801561002a57600080fd5b50600436106100b35760003560e01c806306fdde03146100b8578063095ea7b31461013557806318160ddd1461017557806323b872dd1461018f578063313ce567146101c557806339509351146101e357806370a082311461020f57806395d89b4114610235578063a457c2d71461023d578063a9059cbb14610269578063dd62ed3e14610295575b600080fd5b6100c06102c3565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100fa5781810151838201526020016100e2565b50505050905090810190601f1680156101275780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101616004803603604081101561014b57600080fd5b506001600160a01b038135169060200135610359565b604080519115158252519081900360200190f35b61017d61036f565b60408051918252519081900360200190f35b610161600480360360608110156101a557600080fd5b506001600160a01b03813581169160208101359091169060400135610375565b6101cd6103cc565b6040805160ff9092168252519081900360200190f35b610161600480360360408110156101f957600080fd5b506001600160a01b0381351690602001356103d5565b61017d6004803603602081101561022557600080fd5b50356001600160a01b0316610411565b6100c061042c565b6101616004803603604081101561025357600080fd5b506001600160a01b03813516906020013561048d565b6101616004803603604081101561027f57600080fd5b506001600160a01b0381351690602001356104c9565b61017d600480360360408110156102ab57600080fd5b506001600160a01b03813581169160200135166104d6565b60038054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561034f5780601f106103245761010080835404028352916020019161034f565b820191906000526020600020905b81548152906001019060200180831161033257829003601f168201915b5050505050905090565b6000610366338484610501565b50600192915050565b60025490565b60006103828484846105ed565b6001600160a01b0384166000908152600160209081526040808320338085529252909120546103c29186916103bd908663ffffffff61072f16565b610501565b5060019392505050565b60055460ff1690565b3360008181526001602090815260408083206001600160a01b038716845290915281205490916103669185906103bd908663ffffffff61078c16565b6001600160a01b031660009081526020819052604090205490565b60048054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561034f5780601f106103245761010080835404028352916020019161034f565b3360008181526001602090815260408083206001600160a01b038716845290915281205490916103669185906103bd908663ffffffff61072f16565b60006103663384846105ed565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b6001600160a01b0383166105465760405162461bcd60e51b81526004018080602001828103825260248152602001806108566024913960400191505060405180910390fd5b6001600160a01b03821661058b5760405162461bcd60e51b815260040180806020018281038252602281526020018061080f6022913960400191505060405180910390fd5b6001600160a01b03808416600081815260016020908152604080832094871680845294825291829020859055815185815291517f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259281900390910190a3505050565b6001600160a01b0383166106325760405162461bcd60e51b81526004018080602001828103825260258152602001806108316025913960400191505060405180910390fd5b6001600160a01b0382166106775760405162461bcd60e51b81526004018080602001828103825260238152602001806107ec6023913960400191505060405180910390fd5b6001600160a01b0383166000908152602081905260409020546106a0908263ffffffff61072f16565b6001600160a01b0380851660009081526020819052604080822093909355908416815220546106d5908263ffffffff61078c16565b6001600160a01b038084166000818152602081815260409182902094909455805185815290519193928716927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef92918290030190a3505050565b600082821115610786576040805162461bcd60e51b815260206004820152601e60248201527f536166654d6174683a207375627472616374696f6e206f766572666c6f770000604482015290519081900360640190fd5b50900390565b6000828201838110156107e4576040805162461bcd60e51b815260206004820152601b60248201527a536166654d6174683a206164646974696f6e206f766572666c6f7760281b604482015290519081900360640190fd5b939250505056fe45524332303a207472616e7366657220746f20746865207a65726f206164647265737345524332303a20617070726f766520746f20746865207a65726f206164647265737345524332303a207472616e736665722066726f6d20746865207a65726f206164647265737345524332303a20617070726f76652066726f6d20746865207a65726f2061646472657373a26474726f6e5820eeb9c5f589051bd01dfbf784228961b80c33fe127623fbbddc7c36af12ef2b5564736f6c63430005110031";
        String abiString = "[{\"stateMutability\":\"Nonpayable\",\"type\":\"Constructor\"},{\"inputs\":[{\"indexed\":true,\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"spender\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"Event\"},{\"inputs\":[{\"indexed\":true,\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"name\":\"to\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"Event\"},{\"outputs\":[{\"type\":\"uint256\"}],\"constant\":true,\"inputs\":[{\"name\":\"owner\",\"type\":\"address\"},{\"name\":\"spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"stateMutability\":\"View\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"bool\"}],\"inputs\":[{\"name\":\"spender\",\"type\":\"address\"},{\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"approve\",\"stateMutability\":\"Nonpayable\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"uint256\"}],\"constant\":true,\"inputs\":[{\"name\":\"account\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"stateMutability\":\"View\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"uint8\"}],\"constant\":true,\"name\":\"decimals\",\"stateMutability\":\"View\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"bool\"}],\"inputs\":[{\"name\":\"spender\",\"type\":\"address\"},{\"name\":\"subtractedValue\",\"type\":\"uint256\"}],\"name\":\"decreaseAllowance\",\"stateMutability\":\"Nonpayable\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"bool\"}],\"inputs\":[{\"name\":\"spender\",\"type\":\"address\"},{\"name\":\"addedValue\",\"type\":\"uint256\"}],\"name\":\"increaseAllowance\",\"stateMutability\":\"Nonpayable\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"string\"}],\"constant\":true,\"name\":\"name\",\"stateMutability\":\"View\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"string\"}],\"constant\":true,\"name\":\"symbol\",\"stateMutability\":\"View\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"uint256\"}],\"constant\":true,\"name\":\"totalSupply\",\"stateMutability\":\"View\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"bool\"}],\"inputs\":[{\"name\":\"recipient\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"stateMutability\":\"Nonpayable\",\"type\":\"Function\"},{\"outputs\":[{\"type\":\"bool\"}],\"inputs\":[{\"name\":\"sender\",\"type\":\"address\"},{\"name\":\"recipient\",\"type\":\"address\"},{\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"stateMutability\":\"Nonpayable\",\"type\":\"Function\"}]";

        //don't know how to use now.===========begin=============
        byte[] owner = quince;

        SmartContractOuterClass.SmartContract.ABI abi = TronlinkApiList.jsonStr2Abi(abiString);
        if (abi == null) {
            log.error("abi is null");
            Assert.assertNotNull(abi);
        }
        //byte[] codeBytes = Hex.decode(code);
        SmartContractOuterClass.SmartContract.Builder builder = SmartContractOuterClass.SmartContract.newBuilder();
        builder.setName(contractName);
        builder.setOriginAddress(ByteString.copyFrom(quince));
        builder.setAbi(abi);
        builder.setConsumeUserResourcePercent(100);
        builder.setOriginEnergyLimit(1000L);

        builder.setCallValue(0L);

        byte[] byteCode;

        byteCode = Hex.decode(code);

        builder.setBytecode(ByteString.copyFrom(byteCode));

        SmartContractOuterClass.CreateSmartContract.Builder contractBuilder = SmartContractOuterClass.CreateSmartContract.newBuilder();
        contractBuilder.setOwnerAddress(ByteString.copyFrom(owner));
        contractBuilder.setCallTokenValue(0L);
        contractBuilder.setTokenId(Long.parseLong("0"));
        SmartContractOuterClass.CreateSmartContract contractDeployContract = contractBuilder.setNewContract(builder.build())
                .build();

        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull
                .deployContract(contractDeployContract);
        if (transactionExtention == null || !transactionExtention.getResult().getResult()) {
            System.out.println("RPC create trx failed!");
            if (transactionExtention != null) {
                System.out.println("Code = " + transactionExtention.getResult().getCode());
                System.out
                        .println("Message = " + transactionExtention.getResult().getMessage().toStringUtf8());
            }
        }
        Protocol.Transaction transaction = transactionExtention.getTransaction();
        log.info("----1111: "+ JsonFormat.printToString(transaction));


        final GrpcAPI.TransactionExtention.Builder texBuilder = GrpcAPI.TransactionExtention.newBuilder();
        Protocol.Transaction.Builder transBuilder = Protocol.Transaction.newBuilder();
        Protocol.Transaction.raw.Builder rawBuilder = transactionExtention.getTransaction().getRawData()
                .toBuilder();
        rawBuilder.setFeeLimit(1000000000L);
        transBuilder.setRawData(rawBuilder);

        transaction = transBuilder.build();

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }
    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void updateSettingContract() throws Exception {

        SmartContractOuterClass.UpdateSettingContract.Builder builder = SmartContractOuterClass.UpdateSettingContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String contractName = "TNgWF1NYWYRki1DxjMp7Buq8NRmt34FAiQ";
        byte[] contractAddress = TronlinkApiList.decode58Check(contractName);
        builder.setContractAddress(ByteString.copyFrom(contractAddress));
        builder.setConsumeUserResourcePercent(9);

        SmartContractOuterClass.UpdateSettingContract updateSettingContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.updateSetting(updateSettingContract);
        log.info("-----111111  "+JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }
    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void UpdateEnergyLimitContract() throws Exception {
        SmartContractOuterClass.UpdateEnergyLimitContract.Builder builder = SmartContractOuterClass.UpdateEnergyLimitContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String contractName = "TNgWF1NYWYRki1DxjMp7Buq8NRmt34FAiQ";
        byte[] contractAddress = TronlinkApiList.decode58Check(contractName);
        builder.setContractAddress(ByteString.copyFrom(contractAddress));
        builder.setOriginEnergyLimit(30L);

        SmartContractOuterClass.UpdateEnergyLimitContract updateEnergyLimitContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.updateEnergyLimit(updateEnergyLimitContract);
        log.info("-----111111  "+JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }
    //ClearABIContract
    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void ClearABIContract() throws Exception {
        SmartContractOuterClass.ClearABIContract.Builder builder = SmartContractOuterClass.ClearABIContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String contractName = "TNgWF1NYWYRki1DxjMp7Buq8NRmt34FAiQ";
        byte[] contractAddress = TronlinkApiList.decode58Check(contractName);
        builder.setContractAddress(ByteString.copyFrom(contractAddress));

        SmartContractOuterClass.ClearABIContract clearABIContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.clearContractABI(clearABIContract);
        log.info("-----111111  "+JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }
    //WitnessCreateContract
    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void WitnessCreateContract() throws Exception {
        WitnessContract.WitnessCreateContract.Builder builder = WitnessContract.WitnessCreateContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(address1));
        String url = "www.address1.com";
        byte[] urlByte = url.getBytes();
        builder.setUrl(ByteString.copyFrom(urlByte));

        WitnessContract.WitnessCreateContract witnessCreateContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.createWitness2(witnessCreateContract);
        log.info("-----111111  "+JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    //WitnessUpdateContract
    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void WitnessUpdateContract() throws Exception {
        WitnessContract.WitnessUpdateContract.Builder builder = WitnessContract.WitnessUpdateContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String url = "www.wqq.com";
        byte[] urlByte = url.getBytes();
        builder.setUpdateUrl(ByteString.copyFrom(urlByte));

        WitnessContract.WitnessUpdateContract witnessUpdateContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.updateWitness2(witnessUpdateContract);
        log.info("-----111111  "+JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }
    //UpdateBrokerageContract
    @Test(enabled = true,description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void UpdateBrokerageContract() throws Exception {
        StorageContract.UpdateBrokerageContract.Builder builder = StorageContract.UpdateBrokerageContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        builder.setBrokerage(55);
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull
                    .updateBrokerage(builder.build());
        Protocol.Transaction transaction = transactionExtention.getTransaction();

        log.info("-----111111  " + JsonFormat.printToString(transaction));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                    transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

        /**
         * constructor.
         */
        @AfterClass
        public void shutdown () throws InterruptedException {
            if (channelFull != null) {
                channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            }
        }

    }