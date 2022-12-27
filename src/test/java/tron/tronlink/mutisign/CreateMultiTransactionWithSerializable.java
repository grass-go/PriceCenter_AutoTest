package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Commons;
import org.tron.core.Wallet;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.*;
import tron.common.HttpMethed;
import tron.common.TronlinkApiList;
import tron.common.utils.Base58;
import tron.tronlink.base.TronlinkBase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CreateMultiTransactionWithSerializable extends TronlinkBase {
    private String httpnode = "47.252.19.181:8090";
//    private Stsring httpnode = "123.56.3.74:8090";
    private ManagedChannel channelFull = null;
    HttpResponse res;
    private JSONObject responseContent;
    String key1 = "c21f0c6fdc467f4ae7dc4c1a0b802234a930bff198ce34fde6850b0afd383cf5"; //线上
//    byte[] address1=TronlinkApiList.getFinalAddress(key1);

    String address158 = "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
    byte[] address1 = Commons.decode58Check(address158);
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2 = TronlinkApiList.getFinalAddress(key2);
    String address258 = Base58.encode(address2);

    String quince58 = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
    byte[] quince = Commons.decode58Check(quince58);
    String quincekey = "b47e686119f2236f38cd0e8a4fe20f8a7fc5cb4284d36131f447c63857e3dac9";
    String yunli58 = "THDEBkMEcWuee6ZpXF1KAmWs8x5YAzvVch";
    byte[] yunli = Commons.decode58Check(yunli58);
    String yunlikey = "cafcc392b9b5518324728a9c43c7d857d6d2766669177ea7515e92f8918ab106";
    String wqq1key = "8d5c18030466b6ab0e5367154d15c4f6cb46d2fb56a0b552e017d183abd8c255";
    String wqq2key = "ee16960c312bb08f691fe011ec81530eb613aa1408aca57d7cf736d82f4383de";
    byte[] wqq2 = TronlinkApiList.getFinalAddress(wqq2key);
    String wqq258 = "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ";
    byte[] wqq1 = TronlinkApiList.getFinalAddress(wqq1key);
    String wqq158 = Base58.encode58Check(wqq1);
    private HashMap<String, String> param = new HashMap<>();

    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void sendCoin() {
        // visible: true
        String transactionStr = HttpMethed.sendCoin(httpnode, quince58, wqq158, 9L, "true", 3, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible: false
        String transactionStr2 = HttpMethed.sendCoin(httpnode, ByteArray.toHexString(quince), ByteArray.toHexString(wqq1), 9L, "false", 3, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign freeze balance with serializable no receiver address")
    public void freezeBalanceNoReceiver() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.freezeBalance(httpnode, quince58, 1000000L, 3,0, null,"true", 5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.freezeBalance(httpnode, ByteArray.toHexString(quince), 1000000L, 3,1, null,"false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign freeze balance with serializable has receiver address")
    public void freezeBalanceHasReceiver() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.freezeBalance(httpnode, quince58, 1000000L, 3,0, wqq158,"true", 5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.freezeBalance(httpnode, ByteArray.toHexString(quince), 1000000L, 3,1, ByteArray.toHexString(wqq1),"false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign unfreeze balance with serializable no receiver address")
    public void unfreezeBalanceNoReceiver() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.unFreezeBalance(httpnode, quince58, 0, null,"true", 5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.unFreezeBalance(httpnode, ByteArray.toHexString(quince), 1, null,"false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign unfreeze balance with serializable has receiver address")
    public void unfreezeBalanceHasReceiver() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.unFreezeBalance(httpnode, quince58,0, "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ","true", 5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.unFreezeBalance(httpnode, ByteArray.toHexString(quince), 1, "41A2E895D1C362860C605405D289A7C99CB6DA2741","false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign create asset issue with serializable has all params")
    public void createAssetIssueHasAllPrarams() throws Exception {
        long totalSupply = 10000L;
        // visible:true
        String transactionStr = HttpMethed.assetIssue(httpnode, "TLShQYjv45P49xwKSYrdQkY3rFA9Z5CDzW", "trc10Test1", "trc10T1", totalSupply,
                1, 1, System.currentTimeMillis() + 600000, System.currentTimeMillis() + 50000000,
                2, 3,"This is multi-sign generated Token","www.address1.com",
                1000L,1000L,11L,1L,"true",2, "aae43bee6d365d08ca09042140970e6b6def7e94d888257eed870da9ad1590c7");
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.assetIssue(httpnode, "412AC4526930DE490BDDE77F98A56BD23626E9EA6A", HttpMethed.str2hex("trc10Test2"),
                HttpMethed.str2hex("trc10T2"), totalSupply, 1, 1, System.currentTimeMillis() + 5000,
                System.currentTimeMillis() + 600000, 2, 3,
                HttpMethed.str2hex("This is multi-sign generated Token"),HttpMethed.str2hex("www.address2.com"),
                1000L,1000L,11L,1L,"false",2, "aae43bee6d365d08ca09042140970e6b6def7e94d888257eed870da9ad1590c7");
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign create asset issue with serializable only required params")
    public void createAssetIssueOnlyRequiredPrarams() throws Exception {
        long totalSupply = 10000L;
        // visible:true
        String transactionStr = HttpMethed.assetIssue(httpnode, "TLShQYjv45P49xwKSYrdQkY3rFA9Z5CDzW", "trc10Test1", "trc10T1", totalSupply,
                1, 1, System.currentTimeMillis() + 600000, System.currentTimeMillis() + 50000000,
                null, null,null,"www.address1.com",
                null,null,null,null,"true",2, "aae43bee6d365d08ca09042140970e6b6def7e94d888257eed870da9ad1590c7");
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.assetIssue(httpnode, "412AC4526930DE490BDDE77F98A56BD23626E9EA6A", HttpMethed.str2hex("trc10Test2"),
                HttpMethed.str2hex("trc10T2"), totalSupply, 1, 1, System.currentTimeMillis() + 600000,
                System.currentTimeMillis() + 50000000, null, null,
                null,HttpMethed.str2hex("www.address2.com"),
                null,null,null,null,"false",2, "aae43bee6d365d08ca09042140970e6b6def7e94d888257eed870da9ad1590c7");
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign transfer asset")
    public void transferTrc10() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.transferAsset(httpnode, quince58, wqq158, "1000323", 1L,
                "true",5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.transferAsset(httpnode, ByteArray.toHexString(quince), ByteArray.toHexString(wqq1),
                HttpMethed.str2hex("1000323"), 1L, "false",5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign participate asset issue")
    public void participateAssetissue() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.participateAssetIssue(httpnode, "TRAPa4mZAKxaZqsdcdpXVtV2X3nKQzA1eN",
                quince58, "1000617", 1L, "true",5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", address258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.participateAssetIssue(httpnode, "41A6A748F34723368B00137F1DC9C15A73BE83C2EB",
                ByteArray.toHexString(quince), HttpMethed.str2hex("1000617"), 1L, "false",5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", address258);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign update asset issue")
    public void updateAssetIssue() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.updateAssetIssue(httpnode, quince58, "update desc1",
                "www.multisignModified1.com", 1000L,2000L, "true", 5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.updateAssetIssue(httpnode, ByteArray.toHexString(quince), HttpMethed.str2hex("update desc2"),
                HttpMethed.str2hex("www.multisignModified2.com"), 1000L,2000L, "false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign unfreeze asset")
    public void unfreezeAsset() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.unfreezeAsset(httpnode, "TLShQYjv45P49xwKSYrdQkY3rFA9Z5CDzW",  "true", 2, "aae43bee6d365d08ca09042140970e6b6def7e94d888257eed870da9ad1590c7");
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", "TLShQYjv45P49xwKSYrdQkY3rFA9Z5CDzW");
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.unfreezeAsset(httpnode, ByteArray.toHexString(quince), "false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign vote witness")
    public void voteWitness() throws Exception {
        // visible:true
        JsonObject voteElement1 = new JsonObject();
        JsonObject voteElement2 = new JsonObject();
        JsonArray voteKeys1 = new JsonArray();
        voteElement1.addProperty("vote_address", "TBFpyDStSr6SV9u3dirUHTbWDnvuDQydyz");
        voteElement1.addProperty("vote_count", 11);
        voteKeys1.add(voteElement1);
        voteElement2.addProperty("vote_address", "TBJx8cETFLLiw4r2ukgmKoQYCsV46jF14P");
        voteElement2.addProperty("vote_count", 12);
        voteKeys1.add(voteElement2);
        String transactionStr = HttpMethed.voteWitnessAccount(httpnode, quince58,  voteKeys1,"true", 5, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        JsonObject voteElement3 = new JsonObject();
        JsonObject voteElement4 = new JsonObject();
        JsonArray voteKeys2 = new JsonArray();
        voteElement3.addProperty("vote_address", "410E1CCE51201B6AEA0CE69B5C7BE762EA9C64F3EB");
        voteElement3.addProperty("vote_count", 13);
        voteKeys2.add(voteElement3);
        voteElement4.addProperty("vote_address", "410EB40844F1C45F1DEAA7A164FC5540491516F62F");
        voteElement4.addProperty("vote_count", 14);
        voteKeys2.add(voteElement4);
        String transactionStr2 = HttpMethed.voteWitnessAccount(httpnode, ByteArray.toHexString(quince), voteKeys2, "false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign withdraw balance")
    public void withdrawBalance() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.withdrawBalance(httpnode, wqq258,"true", 4, wqq2key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.withdrawBalance(httpnode, ByteArray.toHexString(quince),"false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign create witness")
    public void createWitness() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.createWitness(httpnode, "TLShQYjv45P49xwKSYrdQkY3rFA9Z5CDzW","http://tlsh.com", "true", 0, "1ec9c30c9c246572557d8aaf88fd0823b70fb4b5a085be80959d66be0afb2848");
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", "TLShQYjv45P49xwKSYrdQkY3rFA9Z5CDzW");
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.createWitness(httpnode, ByteArray.toHexString(wqq1),HttpMethed.str2hex("http://wqq1.com"),"false", 0, quincekey);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign update witness")
    public void updateWitness() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.updateWitness(httpnode, wqq258,"http://wqq2/updateTest3.com", "true", 0, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.updateWitness(httpnode, ByteArray.toHexString(wqq2),HttpMethed.str2hex("http://wqq2/updateTest4.com"), "false", 0, quincekey);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign update brokerage")
    public void updateBrokerage() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.updateBrokerage(httpnode, wqq258,31L, "true", 2, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.updateBrokerage(httpnode, ByteArray.toHexString(quince),32L, "false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign create proposal")
    public void createProposal() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.createProposal(httpnode, wqq258,3L, 150L, "true", 2, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.createProposal(httpnode, ByteArray.toHexString(quince),3L, 220L, "false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign approval proposal")
    public void approvalProposal() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.approvalProposal(httpnode, wqq258,16777, false, "true", 2, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.approvalProposal(httpnode, ByteArray.toHexString(quince),16776, false, "false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign delete proposal")
    public void deleteProposal() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.deleteProposal(httpnode, wqq258,16778, "true", 2, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", wqq258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.deleteProposal(httpnode, ByteArray.toHexString(quince),16779, "false", 5, wqq1key);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign set account id")
    public void setAccountId() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.setAccountId(httpnode, quince58,"quinceTest123", "true", 8, wqq1key);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.setAccountId(httpnode, ByteArray.toHexString(wqq2),HttpMethed.str2hex("wqq2Test456"), "false", 2, quincekey);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq258);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign market sell asset trx and trc10")
    public void marketSellAsset_TrxAndTrc10() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.marketSellAsset(httpnode, quince58,"1000323", 10L, "_", 5L,"true", 8, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.marketSellAsset(httpnode, ByteArray.toHexString(wqq2),HttpMethed.str2hex("_"), 5L, HttpMethed.str2hex("1000349"),8L, "false",2, quincekey);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq258);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign market sell asset trc10 and trc10")
    public void marketSellAsset_Trc10AndTrc10() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.marketSellAsset(httpnode, quince58,"1000323", 10L, "1000598", 5L,"true", 8, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.marketSellAsset(httpnode, ByteArray.toHexString(wqq2),HttpMethed.str2hex("1000598"), 6L, HttpMethed.str2hex("1000349"),8L, "false",2, quincekey);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq258);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign marketcancelorder")
    public void marketcancelorder() throws Exception {
        // visible:true
        String transactionStr = HttpMethed.marketCancelOrder(httpnode, quince58,"0e0fd7d34561fd4126d56748354cd303cad7846d0f5baf13bc1f824782ed70a1","true", 8, quincekey);
        log.info("-----raw transaction:  " + transactionStr);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

        // visible:false
        String transactionStr2 = HttpMethed.marketCancelOrder(httpnode, ByteArray.toHexString(wqq2), "2811e42444ec064feb3de5a1370c5a64165c03a4df066aaf759dc7d93aeec4be","false",2, quincekey);
        log.info("-----raw transaction2:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq258);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @AfterClass
    public void shutdown() throws InterruptedException {
        if (channelFull != null) {
            channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}