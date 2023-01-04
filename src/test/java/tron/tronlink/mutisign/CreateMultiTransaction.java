package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.tron.common.parameter.CommonParameter;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Commons;
import org.tron.common.utils.Sha256Hash;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.AssetIssueContractOuterClass;
import org.tron.protos.contract.BalanceContract;
import tron.common.TronlinkApiList;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import tron.common.TronGridHttpApi;
import tron.common.utils.TronlinkServerHttpClient;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class CreateMultiTransaction extends TronlinkBase {
    private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;
    private String fullnode = "47.75.245.225:50051";  //线上
    //nile env: private String httpnode = "47.252.19.181:8090";
    private String httpnode = "3.225.171.164:8090";
    private HttpResponse response;
    private ManagedChannel channelFull = null;
    HttpResponse res;
    private JSONObject responseContent;
    //String key1="c21f0c6fdc467f4ae7dc4c1a0b802234a930bff198ce34fde6850b0afd383cf5"; //线上
    //byte[] address1=TronlinkApiList.getFinalAddress(key1);

    String address158= multiSignOwnerAddress;
    byte[] address1 = Commons.decode58Check(address158);
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2=TronlinkApiList.getFinalAddress(key2);
    String address258=encode58Check(address2);
    String getAddress258_2=TronlinkApiList.encode58Check(address2);
    private HashMap<String, String> param = new HashMap<>();
    private HashMap<String, String> header = new HashMap<>();

    /**
     * constructor.
     */
    @BeforeClass(enabled = true)
    public void beforeClass() {

        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
    }

    @Test(enabled = true, description = "nulti sign send coin", groups="multiSign")
    public void sendCoin() {
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(address2, 500_000, address1, blockingStubFull);
        log.info("-----111111  "+ JsonFormat.printToString(transaction));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",getAddress258_2);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        param.clear();
        param.put("address",getAddress258_2);
        header.clear();
        header.put("System","Android");
        header.put("Version","4.11.0");
        res = TronlinkApiList.multiTransaction(object,param,header);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

        java.util.ArrayList hashAarray = getTrxRecord(address258);
        log.info("hashAarray: " + hashAarray.toString());


    }

    @Test(enabled = false, description = "nulti sign send coin", groups="multiSign")
    public void sendCoin_With_serializable() {
        String transactionStr = TronGridHttpApi.sendCoin(httpnode, address158, address258, 1000L, "true", 3, key2);
        JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionStr);
        String curHash = jsonObject.getString("txID");
        log.info("curHash: " + curHash);
        //log.info("-----raw transaction:  " + transactionStr);
        JSONObject object = new JSONObject();
        object.put("address", address258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.clear();
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object, null, param);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        java.util.ArrayList hashAarray = getTrxRecord(address258);
        log.info("hashAarray: " + hashAarray.toString());
        Assert.assertTrue(hashAarray.contains(curHash));
    }
    @Test(enabled = false, description = "nulti sign send coin", groups="multiSign")
    public void sendCoin_With_serializable_hexFormat() {
        // visible: false
        String transactionStr2 = TronGridHttpApi.sendCoin(httpnode, ByteArray.toHexString(address1), ByteArray.toHexString(address2), 1000L, "false", 3, key2);
        JSONObject jsonObject2 = TronlinkServerHttpClient.parseString2JsonObject(transactionStr2);
        String curHash2 = jsonObject2.getString("txID");
        log.info("curHash2: "+ curHash2);
        JSONObject object2 = new JSONObject();
        object2.put("address", address258);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.clear();
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,null,param);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        java.util.ArrayList hashAarray2 = getTrxRecord(address258);
        log.info("hashAarray2: "+ hashAarray2.toString());
        Assert.assertTrue(hashAarray2.contains(curHash2));
    }

    public java.util.ArrayList getTrxRecord(String address){
        param.clear();
        param.put("address", address);
        param.put("start", "0");
        param.put("limit", "10");
        param.put("state", "0");
        param.put("isSign","true");
        param.put("netType", "main_net");
        param.put("serializable","true");
        header.clear();
        header.put("System","Android");
        header.put("Version","4.11.0");
        response = TronlinkApiList.multiTrxReword(param,header);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object hashs = JSONPath.eval(responseContent, "$..hash");
        java.util.ArrayList hashArray=(java.util.ArrayList)hashs;
        //hashArray.contains("4cfa12260224f5d05de03cfafab23c0c0fd90ba489901be96db73ddb4ef15e94");
        return hashArray;
    }

    @Test(enabled = false,description = "multi sign freeze balandce",groups="multiSign")
    public void freezeBalandce_With_serializable() throws Exception{
        // visible:true
        String transactionStr = TronGridHttpApi.freezeBalance(httpnode, address158, 1000000L, 3,0, null,"true", 3, key2);
        JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionStr);
        String curHash = jsonObject.getString("txID");
        log.info("curHash2: "+ curHash);

        JSONObject object = new JSONObject();
        object.put("address", address258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,null, param);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        java.util.ArrayList hashAarray = getTrxRecord(address258);
        log.info("hashAarray2: "+ hashAarray.toString());
        Assert.assertTrue(hashAarray.contains(curHash));

    }

    @Test(enabled = false,description = "multi sign freeze balandce",groups="multiSign")
    public void freezeBalandce_With_serializable_hexFormat() throws Exception{
        // visible:false
        String transactionStr = TronGridHttpApi.freezeBalance(httpnode, ByteArray.toHexString(address1), 1000000L, 3,0, null,"false", 3, key2);
        JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionStr);
        String curHash = jsonObject.getString("txID");
        log.info("curHash: "+ curHash);

        JSONObject object = new JSONObject();
        object.put("address", address258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,null, param);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        java.util.ArrayList hashAarray = getTrxRecord(address258);
        log.info("hashAarray2: "+ hashAarray.toString());
        Assert.assertTrue(hashAarray.contains(curHash));
    }

    @Test(enabled = false,description = "multi sign transfer asset",groups="multiSign")
    public void transferTrc10_With_serializable() throws Exception{
        // visible:true
        String transactionStr = TronGridHttpApi.transferAsset(httpnode, address158, address258, "1004031", 1L,
                "true",3, key2);
        JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionStr);
        String curHash = jsonObject.getString("txID");
        log.info("curHash: "+ curHash);

        JSONObject object = new JSONObject();
        object.put("address", address258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,null,param);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        java.util.ArrayList hashAarray = getTrxRecord(address258);
        log.info("hashAarray: "+ hashAarray.toString());
        Assert.assertTrue(hashAarray.contains(curHash));

    }

    @Test(enabled = false,description = "multi sign transfer asset",groups="multiSign")
    public void transferTrc10_With_serializable_hexFormat() throws Exception{
        // visible:false
        String transactionStr = TronGridHttpApi.transferAsset(httpnode, ByteArray.toHexString(address1), ByteArray.toHexString(address2), TronGridHttpApi.str2hex("1004031"), 1L,
                "false",3, key2);
        JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionStr);
        String curHash = jsonObject.getString("txID");
        log.info("curHash: "+ curHash);

        JSONObject object = new JSONObject();
        object.put("address", address258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,null,param);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        java.util.ArrayList hashAarray = getTrxRecord(address258);
        log.info("hashAarray: "+ hashAarray.toString());
        Assert.assertTrue(hashAarray.contains(curHash));

    }


    @Test(enabled = true,description = "multi sign freeze balandce",groups="multiSign")
    public void freezeBalandce() throws Exception{
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        ByteString byteAddreess = ByteString.copyFrom(address1);
        builder.setOwnerAddress(byteAddreess).setFrozenBalance(1000000)
                .setFrozenDuration(3).setResourceValue(0);
        BalanceContract.FreezeBalanceContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.freezeBalance(contract);
        log.info("0000 "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----111  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",getAddress258_2);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        param.put("address",address258);
        header.put("System","Android");
        header.put("Version","4.11.0");
        res = TronlinkApiList.multiTransaction(object,param,header);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = true,description = "multi sign transfer asset",groups="multiSign")
    public void transferTrc10() throws Exception{
        AssetIssueContractOuterClass.TransferAssetContract.Builder builder = AssetIssueContractOuterClass.TransferAssetContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(address2);
        ByteString bsName = ByteString.copyFrom("1004031".getBytes());
        ByteString bsOwner = ByteString.copyFrom(address1);
        builder.setToAddress(bsTo);
        builder.setAssetName(bsName);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(1);

        AssetIssueContractOuterClass.TransferAssetContract contract = builder.build();
        log.info("-----0000  "+JsonFormat.printToString(contract));
        Protocol.Transaction transaction = blockingStubFull.transferAsset(contract);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));

        param.put("address",address258);
        header.put("System","Android");
        header.put("Version","4.11.0");
        int index;
        for (index=0; index<5;index++){
            log.info("cur index is " + index);
            res = TronlinkApiList.multiTransaction(object,param,header);
            if(res.getStatusLine().getStatusCode() == 200){
                index=6;
            }
            else {
                continue;
            }
            responseContent = TronlinkApiList.parseResponse2JsonObject(res);
            Assert.assertEquals(0,responseContent.getIntValue("code"));
        }
        Assert.assertEquals(7, index);
    }

    @Test(enabled = false,description = "nulti sign transfer trc20,disable because no permission")
    public void transferTrc20() throws Exception{

        String contractAddress = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "000000000000000000000000ebae50590810b05d4b403f13766f213518edef650000000000000000000000000000000000000000000000000000000000000001";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd,"transfer(address,uint256)",args,true,0,maxFeeLimit,"0",0L,
                address1,blockingStubFull);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        param.put("address",address258);
        header.put("System","Android");
        header.put("Version","4.11.0");
        res = TronlinkApiList.multiTransaction(object,param,header);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));


    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        if (channelFull != null) {
            channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
    //v4.1.0
    @Test(enabled = true, description = "nulti sign send coin", groups="multiSign")
    public void sendCoinLowVersionWithNoSig() {
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(address2, 500_000, address1, blockingStubFull);
        log.info("-----111111  "+ JsonFormat.printToString(transaction));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",getAddress258_2);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransactionNoSig(object,null,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }
    //v4.1.0
    @Test(enabled = true, description = "nulti sign send coin", groups="multiSign")
    public void sendCoinHighVersionWithNoSig() {
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(address2, 500_000, address1, blockingStubFull);
        log.info("-----111111  "+ JsonFormat.printToString(transaction));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",getAddress258_2);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        header.put("System","Chrome");
        header.put("Version","4.0.0");
        res = TronlinkApiList.multiTransactionNoSig(object,header,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(20004, responseContent.getIntValue("code"));
        Assert.assertEquals("Error param.", responseContent.getString("message"));

    }

    public static String encode58Check(byte[] input) {
        byte[] hash0 = Sha256Hash.hash(CommonParameter
                .getInstance().isECKeyCryptoEngine(), input);
        byte[] hash1 = Sha256Hash.hash(CommonParameter
                .getInstance().isECKeyCryptoEngine(), hash0);
        byte[] inputCheck = new byte[input.length + 4];
        System.arraycopy(input, 0, inputCheck, 0, input.length);
        System.arraycopy(hash1, 0, inputCheck, input.length, 4);
        return Base58.encode(inputCheck);
    }
}
