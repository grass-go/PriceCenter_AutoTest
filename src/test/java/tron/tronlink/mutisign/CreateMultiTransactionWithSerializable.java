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
//    private String httpnode = "123.56.3.74:8090";
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
        voteElement3.addProperty("vote_count", 11);
        voteKeys2.add(voteElement3);
        voteElement4.addProperty("vote_address", "410EB40844F1C45F1DEAA7A164FC5540491516F62F");
        voteElement4.addProperty("vote_count", 12);
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

    //modify coin id before go prod
    //modify permission id before go prod
    /*
    @Test(enabled = true, description = "nulti sign transfer trc20")
    public void transferTrc20() throws Exception {

        //String contractAddress = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        String contractAddress = "TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "0000000000000000000000000c497701e37f11b042bdc7aabfc0cd5d45f7a0c70000000000000000000000000000000000000000000000000000000000000001";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd, "transfer(address,uint256)", args, true, 0, maxFeeLimit, "0", 0L,
                quince, blockingStubFull);
        log.info("-----111111  " + JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));


        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }

    @Test(enabled = true, description = "nulti sign transfer trc20")
    public void transferTrc721() throws Exception {
        String contractAddress = "TVwK7UVwVpkfcGSyhCgvW3JzmowveztmQE";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "000000000000000000000000e7d71e72ea48de9144dc2450e076415af0ea745f0000000000000000000000002CBAEBC9F5FAB6D610549F22406FFB8B9A04AE500000000000000000000000000000000000000000000000000000000000000457";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd, "transferFrom(address,address,uint256)", args, true, 0, maxFeeLimit, "0", 0L,
                quince, blockingStubFull);
        log.info("-----111111  " + JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 6, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));


        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }



    @Test(enabled = true, description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void updateSettingContract() throws Exception {

        SmartContractOuterClass.UpdateSettingContract.Builder builder = SmartContractOuterClass.UpdateSettingContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String contractName = "TNgWF1NYWYRki1DxjMp7Buq8NRmt34FAiQ";
        byte[] contractAddress = TronlinkApiList.decode58Check(contractName);
        builder.setContractAddress(ByteString.copyFrom(contractAddress));
        builder.setConsumeUserResourcePercent(9);

        SmartContractOuterClass.UpdateSettingContract updateSettingContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.updateSetting(updateSettingContract);
        log.info("-----111111  " + JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }

    @Test(enabled = true, description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void UpdateEnergyLimitContract() throws Exception {
        SmartContractOuterClass.UpdateEnergyLimitContract.Builder builder = SmartContractOuterClass.UpdateEnergyLimitContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String contractName = "TNgWF1NYWYRki1DxjMp7Buq8NRmt34FAiQ";
        byte[] contractAddress = TronlinkApiList.decode58Check(contractName);
        builder.setContractAddress(ByteString.copyFrom(contractAddress));
        builder.setOriginEnergyLimit(30L);

        SmartContractOuterClass.UpdateEnergyLimitContract updateEnergyLimitContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.updateEnergyLimit(updateEnergyLimitContract);
        log.info("-----111111  " + JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }

    //ClearABIContract
    @Test(enabled = true, description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void ClearABIContract() throws Exception {
        SmartContractOuterClass.ClearABIContract.Builder builder = SmartContractOuterClass.ClearABIContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String contractName = "TNgWF1NYWYRki1DxjMp7Buq8NRmt34FAiQ";
        byte[] contractAddress = TronlinkApiList.decode58Check(contractName);
        builder.setContractAddress(ByteString.copyFrom(contractAddress));

        SmartContractOuterClass.ClearABIContract clearABIContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.clearContractABI(clearABIContract);
        log.info("-----111111  " + JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }

    //WitnessCreateContract
    @Test(enabled = true, description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void WitnessCreateContract() throws Exception {
        WitnessContract.WitnessCreateContract.Builder builder = WitnessContract.WitnessCreateContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(address1));
        String url = "www.address1.com";
        byte[] urlByte = url.getBytes();
        builder.setUrl(ByteString.copyFrom(urlByte));

        WitnessContract.WitnessCreateContract witnessCreateContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.createWitness2(witnessCreateContract);
        log.info("-----111111  " + JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), key2, 3, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address", address258);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    //WitnessUpdateContract
    @Test(enabled = true, description = "multiSignTest: Participate Asset Issue Asset Issue")
    public void WitnessUpdateContract() throws Exception {
        WitnessContract.WitnessUpdateContract.Builder builder = WitnessContract.WitnessUpdateContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(quince));
        String url = "www.wqq.com";
        byte[] urlByte = url.getBytes();
        builder.setUpdateUrl(ByteString.copyFrom(urlByte));

        WitnessContract.WitnessUpdateContract witnessUpdateContract = builder.build();
        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.updateWitness2(witnessUpdateContract);
        log.info("-----111111  " + JsonFormat.printToString(transactionExtention.getTransaction()));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transactionExtention.getTransaction(), wqq1key, 4, blockingStubFull);
        log.info("-----2222  " + JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));

        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }

    //UpdateBrokerageContract
    @Test(enabled = true, description = "multiSignTest: Participate Asset Issue Asset Issue")
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

        res = TronlinkApiList.multiTransactionNoSig(object,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }*/

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        if (channelFull != null) {
            channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}