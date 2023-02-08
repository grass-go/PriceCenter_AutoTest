package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONArray;
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
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Commons;
import org.tron.common.utils.Utils;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.*;
import tron.common.HttpMethed;
import tron.common.HttpMethed2;
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
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
public class CreateMultiTransactionWithSerializable2 extends TronlinkBase {
    //private String httpnode = "http://47.252.19.181:8090";
    //private String httpnode = "https://api.trongrid.io";
    private String httpnode = "3.225.171.164:8090";
    private ManagedChannel channelFull = null;
    HttpResponse res;
    private JSONObject responseContent;
    String key1 = "c21f0c6fdc467f4ae7dc4c1a0b802234a930bff198ce34fde6850b0afd383cf5"; //线上
//    byte[] address1=TronlinkApiList.getFinalAddress(key1);

    String address158 = "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
    byte[] address1 = Commons.decode58Check(address158);
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2 = TronlinkApiList.getFinalAddress(key2);
    String address258 = Base58.encode58Check(address2);

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
    String wqq3key = "119b1042c75e7b797f8d90f3bdffa829171024e2d9f9539e89531b0fbe93833e";
    String wqq358 = "TPmQw8dDLdrH8bTN3u9H1A2XCanbqyE533";
    byte[] wqq3 = TronlinkApiList.getFinalAddress(wqq3key);
    String wqq4key = "15716648fd70cac3e9098335b8084b6f6f0d91d54fe1d0e4475894f7e0e3c7d0";
    String wqq458 = "TEmXqsFgXGUC6pWKXxaWSnQBnaGcMCM6dx";
    byte[] wqq4 = TronlinkApiList.getFinalAddress(wqq4key);

    //byte[] wqq1_byte = Commons.decode58Check(wqq158);
    private HashMap<String, String> param = new HashMap<>();

    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void accountCreate() {

        ECKey ecKey = new ECKey(Utils.getRandom());
        byte[] randomAddress = ecKey.getAddress();
        String randomAddress58 = Base58.encode58Check(randomAddress);
        log.info("-----create address: "+randomAddress58);

        // visible: true
        String transactionStr = HttpMethed2.createAccount(httpnode, quince58, randomAddress58,"true",3, wqq1key);
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

        //visible: false
        ECKey ecKey2 = new ECKey(Utils.getRandom());
        byte[] randomAddress2 = ecKey2.getAddress();
        String randomAddress58_2 = Base58.encode58Check(randomAddress2);
        log.info("-----create address: "+randomAddress58_2);


        log.info("wqq debug: ByteArray.toHexString: "+ ByteArray.toHexString(quince));
        String transactionStr2 = HttpMethed2.createAccount(httpnode, ByteArray.toHexString(quince), ByteArray.toHexString(randomAddress2),"false",3, wqq1key);
        log.info("-----raw transaction:  " + transactionStr2);

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

    @Test(enabled = true, description = "multi sign")
    public void accountNameUpdate() {

        String randomName=RandomStringUtils.randomAlphanumeric(8);
        log.info("debug: randomName: "+randomName);
        // visible: true
        //String transactionStr = HttpMethed2.updateAccount(httpnode, wqq258, randomName,"true",3, quincekey);
        String transactionStr = HttpMethed2.updateAccount(httpnode, ByteArray.toHexString(wqq3), randomName,"false",2, quincekey);

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

        /*//visible: false
        String randomName2=RandomStringUtils.randomAlphanumeric(100);
        log.info("debug: randomName2: "+randomName2);
        log.info("wqq debug: ByteArray.toHexString: "+ ByteArray.toHexString(wqq1));
        String transactionStr2 = HttpMethed2.updateAccount(httpnode, ByteArray.toHexString(wqq1), randomName2,"false",3, quincekey);
        log.info("-----raw transaction:  " + transactionStr2);

        JSONObject object2 = new JSONObject();
        object2.put("address", quince58);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));*/
    }

    //witness必须输入witnessObject，不是witness不能输入witnessObject
    //wqq3是witness， wqq4非witness
    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void accountPermissionsUpdate() {
        String OwnerAddress = wqq158;
        JsonObject ownerObject=new JsonObject();
        JsonObject witnessObject=new JsonObject();

        //ownerObject.addProperty("type","Owner");   //type
        ownerObject.addProperty("threshold",1);  //threshold
        JsonArray keys = new JsonArray();
        JsonObject key1 = new JsonObject();
        key1.addProperty("weight",1);
        key1.addProperty("address",OwnerAddress);
        /*JsonObject key2 = new JsonObject();
        key2.addProperty("weight",1);
        key2.addProperty("address",quince58);*/
        keys.add(key1);
        //keys.add(key2);
        ownerObject.add("keys",keys);     //keys
        //not needed parameter
        //ownerObject.addProperty("operations","7fff1fc0033e0000000000000000000000000000000000000000000000000000");
        ownerObject.addProperty("id",1);        //id

        witnessObject.addProperty("type","Witness");   //type
        witnessObject.addProperty("threshold",1);  //threshold
        JsonArray wit_keys = new JsonArray();
        JsonObject wit_key1 = new JsonObject();
        wit_key1.addProperty("weight",1);
        wit_key1.addProperty("address",quince58);
        wit_keys.add(wit_key1);
        //Witness permission's key count should be 1
        //wit_keys.add(wit_key2);
        witnessObject.add("keys",wit_keys);     //keys
        //Witness permission needn't operations
        //witnessObject.addProperty("operations","7fff1fc0033e0000000000000000000000000000000000000000000000000000");
        witnessObject.addProperty("id",2);        //id

        JsonArray activesArray = new JsonArray();
        JsonObject activesObject=new JsonObject();
        activesObject.addProperty("type","Active");   //type
        activesObject.addProperty("threshold",2);  //threshold
        JsonArray act_keys = new JsonArray();
        JsonObject act_key1 = new JsonObject();
        act_key1.addProperty("weight",1);
        act_key1.addProperty("address",quince58);
        JsonObject act_key2 = new JsonObject();
        act_key2.addProperty("weight",1);
        act_key2.addProperty("address",OwnerAddress);
        act_keys.add(act_key1);
        act_keys.add(act_key2);
        activesObject.add("keys",act_keys);     //keys
        activesObject.addProperty("operations","77ff07c0027e0300000000000000000000000000000000000000000000000000");
        activesObject.addProperty("id",3);
        activesArray.add(activesObject);

        JsonObject activesObject2=new JsonObject();
        //activesObject2.addProperty("type","Active");   //type
        activesObject2.addProperty("threshold",2);  //threshold
        JsonArray act_keys_2 = new JsonArray();
        JsonObject act_key2_1 = new JsonObject();
        act_key2_1.addProperty("weight",1);
        act_key2_1.addProperty("address",quince58);
        JsonObject act_key2_2 = new JsonObject();
        act_key2_2.addProperty("weight",1);
        act_key2_2.addProperty("address",OwnerAddress);
        act_keys_2.add(act_key2_1);
        act_keys_2.add(act_key2_2);
        activesObject2.add("keys",act_keys_2);     //keys
        activesObject2.addProperty("operations","77ff07c0027e0300000000000000000000000000000000000000000000000000");
        activesObject2.addProperty("id",5);
        activesArray.add(activesObject2);

        log.info("========activesArray==============");
        log.info(activesArray.toString());
        String transactionStr = HttpMethed2.accountPermissionUpdate(httpnode, OwnerAddress, ownerObject, witnessObject,activesArray,"true",2, quincekey);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }

    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void accountPermissionsUpdate_NonWitness() {
        /*String OwnerAddress = wqq458;
        JsonObject ownerObject=new JsonObject();
        JsonObject witnessObject=new JsonObject();

        //ownerObject.addProperty("type","Owner");   //type
        ownerObject.addProperty("threshold",1);  //threshold
        JsonArray keys = new JsonArray();
        JsonObject key1 = new JsonObject();
        key1.addProperty("weight",1);
        key1.addProperty("address",OwnerAddress);
        JsonObject key2 = new JsonObject();
        key2.addProperty("weight",1);
        key2.addProperty("address",quince58);
        keys.add(key1);
        //keys.add(key2);
        ownerObject.add("keys",keys);     //keys
        //not needed parameter
        //ownerObject.addProperty("operations","7fff1fc0033e0000000000000000000000000000000000000000000000000000");
        ownerObject.addProperty("id",0);        //id


        JsonArray activesArray = new JsonArray();
        JsonObject activesObject=new JsonObject();
        activesObject.addProperty("type","Active");   //type
        activesObject.addProperty("threshold",5);  //threshold
        JsonArray act_keys = new JsonArray();
        JsonObject act_key1 = new JsonObject();
        act_key1.addProperty("weight",3);
        act_key1.addProperty("address",quince58);
        JsonObject act_key2 = new JsonObject();
        act_key2.addProperty("weight",2);
        act_key2.addProperty("address",OwnerAddress);
        act_keys.add(act_key1);
        act_keys.add(act_key2);
        activesObject.add("keys",act_keys);     //keys
        activesObject.addProperty("operations","77ff07c0027e0300000000000000000000000000000000000000000000000000");
        //activesObject.addProperty("id",3);
        activesArray.add(activesObject);

        JsonObject activesObject2=new JsonObject();
        activesObject2.addProperty("type","Active");   //type
        activesObject2.addProperty("threshold",10);  //threshold
        JsonArray act_keys_2 = new JsonArray();
        JsonObject act_key2_1 = new JsonObject();
        act_key2_1.addProperty("weight",5);
        act_key2_1.addProperty("address",quince58);
        JsonObject act_key2_2 = new JsonObject();
        act_key2_2.addProperty("weight",5);
        act_key2_2.addProperty("address",OwnerAddress);
        act_keys_2.add(act_key2_1);
        act_keys_2.add(act_key2_2);
        activesObject2.add("keys",act_keys_2);     //keys
        activesObject2.addProperty("operations","77ff07c0027e0300000000000000000000000000000000000000000000000000");
        //activesObject2.addProperty("id",4);
        activesArray.add(activesObject2);*/

        String OwnerAddress = quince58;
        JsonObject ownerObject=new JsonObject();
        JsonObject witnessObject=new JsonObject();

        //ownerObject.addProperty("type","Owner");   //type
        ownerObject.addProperty("threshold",1);  //threshold
        JsonArray keys = new JsonArray();
        JsonObject key1 = new JsonObject();
        key1.addProperty("weight",1);
        key1.addProperty("address",ByteArray.toHexString(wqq4));
        JsonObject key2 = new JsonObject();
        key2.addProperty("weight",1);
        key2.addProperty("address",ByteArray.toHexString(quince));
        //keys.add(key1);
        keys.add(key2);
        ownerObject.add("keys",keys);     //keys
        //not needed parameter
        //ownerObject.addProperty("operations","7fff1fc0033e0000000000000000000000000000000000000000000000000000");
        ownerObject.addProperty("id",0);        //id


        JsonArray activesArray = new JsonArray();
        JsonObject activesObject=new JsonObject();
        activesObject.addProperty("type","Active");   //type
        activesObject.addProperty("threshold",5);  //threshold
        JsonArray act_keys = new JsonArray();
        JsonObject act_key1 = new JsonObject();
        act_key1.addProperty("weight",3);
        act_key1.addProperty("address",ByteArray.toHexString(quince));
        JsonObject act_key2 = new JsonObject();
        act_key2.addProperty("weight",2);
        act_key2.addProperty("address",ByteArray.toHexString(wqq4));
        act_keys.add(act_key1);
        act_keys.add(act_key2);
        activesObject.add("keys",act_keys);     //keys
        activesObject.addProperty("operations","77ff07c0027e0300000000000000000000000000000000000000000000000000");
        //activesObject.addProperty("id",3);
        activesArray.add(activesObject);

        JsonObject activesObject2=new JsonObject();
        activesObject2.addProperty("type","Active");   //type
        activesObject2.addProperty("threshold",10);  //threshold
        JsonArray act_keys_2 = new JsonArray();
        JsonObject act_key2_1 = new JsonObject();
        act_key2_1.addProperty("weight",5);
        act_key2_1.addProperty("address",ByteArray.toHexString(quince));
        JsonObject act_key2_2 = new JsonObject();
        act_key2_2.addProperty("weight",5);
        act_key2_2.addProperty("address",ByteArray.toHexString(wqq4));
        act_keys_2.add(act_key2_1);
        act_keys_2.add(act_key2_2);
        activesObject2.add("keys",act_keys_2);     //keys
        activesObject2.addProperty("operations","77ff07c0027e0300000000000000000000000000000000000000000000000000");
        //activesObject2.addProperty("id",4);
        activesArray.add(activesObject2);

        log.info("========activesArray==============");
        log.info(activesArray.toString());
        //String transactionStr = HttpMethed2.accountPermissionUpdate(httpnode, OwnerAddress, ownerObject, null,activesArray,"true",2, quincekey);
        String transactionStr = HttpMethed2.accountPermissionUpdate(httpnode, ByteArray.toHexString(quince), ownerObject, null,activesArray,"false",3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }


    @Test(enabled = true, description = "")
    public void accountPermissionsUpdate_Broadcast() {
        String OwnerAddress = wqq458;
        JsonObject ownerObject=new JsonObject();
        JsonObject witnessObject=new JsonObject();
        JsonObject activesObject=new JsonObject();
        //ownerObject.addProperty("type","Owner");   //type
        ownerObject.addProperty("threshold",1);  //threshold
        JsonArray keys = new JsonArray();
        JsonObject key1 = new JsonObject();
        key1.addProperty("weight",1);
        key1.addProperty("address",OwnerAddress);
        keys.add(key1);
        ownerObject.add("keys",keys);     //keys
        //not needed parameter
        //ownerObject.addProperty("operations","7fff1fc0033e0000000000000000000000000000000000000000000000000000");
        ownerObject.addProperty("id",1);        //id

        witnessObject.addProperty("type","Witness");   //type
        witnessObject.addProperty("threshold",1);  //threshold
        JsonArray wit_keys = new JsonArray();
        JsonObject wit_key1 = new JsonObject();
        wit_key1.addProperty("weight",1);
        wit_key1.addProperty("address",quince58);
        JsonObject wit_key2 = new JsonObject();
        wit_key2.addProperty("weight",1);
        wit_key2.addProperty("address",OwnerAddress);
        wit_keys.add(wit_key1);
        //Witness permission's key count should be 1
        //wit_keys.add(wit_key2);
        witnessObject.add("keys",wit_keys);     //keys
        //Witness permission needn't operations
        //witnessObject.addProperty("operations","7fff1fc0033e0000000000000000000000000000000000000000000000000000");
        witnessObject.addProperty("id",2);        //id

        activesObject.addProperty("type","Active");   //type
        activesObject.addProperty("threshold",2);  //threshold
        JsonArray act_keys = new JsonArray();
        JsonObject act_key1 = new JsonObject();
        act_key1.addProperty("weight",1);
        act_key1.addProperty("address",quince58);
        JsonObject act_key2 = new JsonObject();
        act_key2.addProperty("weight",1);
        act_key2.addProperty("address",OwnerAddress);
        act_keys.add(wit_key1);
        act_keys.add(wit_key2);
        activesObject.add("keys",act_keys);     //keys
        activesObject.addProperty("operations","7fff1fc0033e0000000000000000000000000000000000000000000000000000");
        activesObject.addProperty("id",3);

        HttpMethed2.accountPermissionUpdate_broadcast(httpnode, OwnerAddress, ownerObject, null, activesObject,"true", wqq4key);
    }

    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void smartContractTriggerTrc20Transfer() {
        //qa: String trc20_contract = "TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh";
        String trc20_contract = "TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt";
        String trc20_contract_hex = "416A6337AE47A09AEA0BBD4FAEB23CA94349C7B774";
        String transactionStr=HttpMethed2.triggerSmartContract(httpnode, quince58, trc20_contract,"transfer(address,uint256)","0000000000000000000000002cbaebc9f5fab6d610549f22406ffb8b9a04ae50000000000000000000000000000000000000000000000000000000000000000a", 2000000000L,"true",3, wqq1key);
        //String transactionStr=HttpMethed2.triggerSmartContract(httpnode, ByteArray.toHexString(quince), trc20_contract_hex,"transfer(address,uint256)","0000000000000000000000002cbaebc9f5fab6d610549f22406ffb8b9a04ae50000000000000000000000000000000000000000000000000000000000000000a", 2000000000L,"false",3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void smartContractTriggerTrc721Transfer() {
        String trc721_contract = "TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG";
        String trc721_contract_hex ="41C294DBC7A38E621493E21B5CB33988E5A836CDF8";
        String transactionStr=HttpMethed2.triggerSmartContract(httpnode, quince58, trc721_contract,"transfer(address,uint256)","0000000000000000000000002cbaebc9f5fab6d610549f22406ffb8b9a04ae500000000000000000000000000000000000000000000000000000000000000002", 2000000000L,"true",3, wqq1key);

        //String transactionStr=HttpMethed2.triggerSmartContract(httpnode, ByteArray.toHexString(quince), trc721_contract_hex,"transfer(address,uint256)","0000000000000000000000002cbaebc9f5fab6d610549f22406ffb8b9a04ae500000000000000000000000000000000000000000000000000000000000000008", 2000000000L,"false",3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void smartContractTriggerTrc1155Transfer() {
        String trc1155_contract = "TE2VpPmPQp9UZpDPcBS4G8Pw3R1BZ4Ea6j";
        String trc1155_contract_hex = "412C7FC9D4ABD78E769E6945C87DB562FBDE0A6848";
        String transactionStr=HttpMethed2.triggerSmartContract(httpnode, quince58, trc1155_contract,"safeTransferFrom(address,address,uint256,uint256,bytes)","000000000000000000000000e7d71e72ea48de9144dc2450e076415af0ea745f0000000000000000000000002cbaebc9f5fab6d610549f22406ffb8b9a04ae500000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000a00000000000000000000000000000000000000000000000000000000000000000", 2000000000L,"true",3, wqq1key);
        //String transactionStr=HttpMethed2.triggerSmartContract(httpnode, ByteArray.toHexString(quince), trc1155_contract_hex,"safeTransferFrom(address,address,uint256,uint256,bytes)","000000000000000000000000e7d71e72ea48de9144dc2450e076415af0ea745f0000000000000000000000002cbaebc9f5fab6d610549f22406ffb8b9a04ae500000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000a00000000000000000000000000000000000000000000000000000000000000000", 2000000000L,"false",3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign send coin with serializable")
    public void triggerSmartContractBroadcast() {
        //trc20 Quince: TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh
        String trc20_contract = "TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh";
        //trc721: quinceWithURI: TTFj6AMthQhUET6hP8TraT7d74RfJC9jGz
        String trc721_contract = "TTFj6AMthQhUET6hP8TraT7d74RfJC9jGz";
        //trc1155:bill1155_001 TAp4M94p4ngPqTJnT85mVT9YJeQtVL2Cgk
        String trc1155_contract = "TAp4M94p4ngPqTJnT85mVT9YJeQtVL2Cgk";

        // visible: true
        HttpMethed2.triggerSmartContractBroadcast(httpnode, quince58, trc20_contract,"transfer(address,uint256)","0000000000000000000000002cbaebc9f5fab6d610549f22406ffb8b9a04ae50000000000000000000000000000000000000000000000000000000000000000a", 2000000000L,"true",quincekey);
    }

    @Test(enabled = true, description = "multi sign ")
    public void smartContractCreateBroadcast() {
        String abi = "[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name_\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"symbol_\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"userAddress\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address payable\",\"name\":\"relayerAddress\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bytes\",\"name\":\"functionSignature\",\"type\":\"bytes\"}],\"name\":\"MetaTransactionExecuted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"ERC712_VERSION\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"subtractedValue\",\"type\":\"uint256\"}],\"name\":\"decreaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"userAddress\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"functionSignature\",\"type\":\"bytes\"},{\"internalType\":\"bytes32\",\"name\":\"sigR\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"sigS\",\"type\":\"bytes32\"},{\"internalType\":\"uint8\",\"name\":\"sigV\",\"type\":\"uint8\"}],\"name\":\"executeMetaTransaction\",\"outputs\":[{\"internalType\":\"bytes\",\"name\":\"\",\"type\":\"bytes\"}],\"stateMutability\":\"payable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getChainId\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getDomainSeperator\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"getNonce\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"nonce\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"addedValue\",\"type\":\"uint256\"}],\"name\":\"increaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"mint\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
        String bin = "60806040526005805461ff00191690553480156200001c57600080fd5b50d380156200002a57600080fd5b50d280156200003857600080fd5b5060405162001bbb38038062001bbb833981810160405260408110156200005e57600080fd5b81019080805160405193929190846401000000008211156200007f57600080fd5b9083019060208201858111156200009557600080fd5b8251640100000000811182820188101715620000b057600080fd5b82525081516020918201929091019080838360005b83811015620000df578181015183820152602001620000c5565b50505050905090810190601f1680156200010d5780820380516001836020036101000a031916815260200191505b50604052602001805160405193929190846401000000008211156200013157600080fd5b9083019060208201858111156200014757600080fd5b82516401000000008111828201881017156200016257600080fd5b82525081516020918201929091019080838360005b838110156200019157818101518382015260200162000177565b50505050905090810190601f168015620001bf5780820380516001836020036101000a031916815260200191505b50604052505082518391508290620001df90600390602085019062000598565b508051620001f590600490602084019062000598565b50506005805460ff19166012179055506b204fce5e3e2502611000000062000239620002296001600160e01b036200025616565b826001600160e01b036200027316565b6200024d836001600160e01b036200038b16565b5050506200063a565b60006200026d6200040060201b62000cca1760201c565b90505b90565b6001600160a01b038216620002cf576040805162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f206164647265737300604482015290519081900360640190fd5b620002e6600083836001600160e01b036200045f16565b62000302816002546200046460201b62000d281790919060201c565b6002556001600160a01b038216600090815260208181526040909120546200033591839062000d2862000464821b17901c565b6001600160a01b0383166000818152602081815260408083209490945583518581529351929391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a35050565b600554610100900460ff1615620003da576040805162461bcd60e51b815260206004820152600e60248201526d185b1c9958591e481a5b9a5d195960921b604482015290519081900360640190fd5b620003ee816001600160e01b03620004c616565b506005805461ff001916610100179055565b6000333014156200045a5760606000368080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152505050503601516001600160a01b03169150620002709050565b503390565b505050565b600082820183811015620004bf576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b9392505050565b6040518060800160405280604f815260200162001b6c604f913980516020918201208251838301206040805180820190915260018152603160f81b930192909252907fc89efdaa54c0f20c7adf612882df0950f5a951637e0307cdcb4c672f298b8bc6306200053d6001600160e01b036200058e16565b604080516020808201979097528082019590955260608501939093526001600160a01b03909116608084015260a0808401919091528151808403909101815260c09092019052805191012060065550565b63ffffffff461690565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620005db57805160ff19168380011785556200060b565b828001600101855582156200060b579182015b828111156200060b578251825591602001919060010190620005ee565b50620006199291506200061d565b5090565b6200027091905b8082111562000619576000815560010162000624565b611522806200064a6000396000f3fe6080604052600436106100fe5760003560e01c8063313ce5671161009557806395d89b411161006457806395d89b411461052f578063a0712d681461055e578063a457c2d7146105a4578063a9059cbb146105f7578063dd62ed3e1461064a576100fe565b8063313ce5671461041b5780633408e47014610460578063395093511461048f57806370a08231146104e2576100fe565b806318160ddd116100d157806318160ddd1461030157806320379ee51461034257806323b872dd146103715780632d0335ab146103ce576100fe565b806306fdde0314610103578063095ea7b3146101a75780630c53c51c1461020e5780630f7e5970146102d2575b600080fd5b34801561010f57600080fd5b50d3801561011c57600080fd5b50d2801561012957600080fd5b5061013261069f565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561016c578181015183820152602001610154565b50505050905090810190601f1680156101995780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101b357600080fd5b50d380156101c057600080fd5b50d280156101cd57600080fd5b506101fa600480360360408110156101e457600080fd5b506001600160a01b038135169060200135610735565b604080519115158252519081900360200190f35b610132600480360360a081101561022457600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561024f57600080fd5b82018360208201111561026157600080fd5b8035906020019184600183028401116401000000008311171561028357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550508235935050506020810135906040013560ff16610752565b3480156102de57600080fd5b50d380156102eb57600080fd5b50d280156102f857600080fd5b50610132610a55565b34801561030d57600080fd5b50d3801561031a57600080fd5b50d2801561032757600080fd5b50610330610a72565b60408051918252519081900360200190f35b34801561034e57600080fd5b50d3801561035b57600080fd5b50d2801561036857600080fd5b50610330610a78565b34801561037d57600080fd5b50d3801561038a57600080fd5b50d2801561039757600080fd5b506101fa600480360360608110156103ae57600080fd5b506001600160a01b03813581169160208101359091169060400135610a7e565b3480156103da57600080fd5b50d380156103e757600080fd5b50d280156103f457600080fd5b506103306004803603602081101561040b57600080fd5b50356001600160a01b0316610b0b565b34801561042757600080fd5b50d3801561043457600080fd5b50d2801561044157600080fd5b5061044a610b26565b6040805160ff9092168252519081900360200190f35b34801561046c57600080fd5b50d3801561047957600080fd5b50d2801561048657600080fd5b50610330610b2f565b34801561049b57600080fd5b50d380156104a857600080fd5b50d280156104b557600080fd5b506101fa600480360360408110156104cc57600080fd5b506001600160a01b038135169060200135610b39565b3480156104ee57600080fd5b50d380156104fb57600080fd5b50d2801561050857600080fd5b506103306004803603602081101561051f57600080fd5b50356001600160a01b0316610b8d565b34801561053b57600080fd5b50d3801561054857600080fd5b50d2801561055557600080fd5b50610132610ba8565b34801561056a57600080fd5b50d3801561057757600080fd5b50d2801561058457600080fd5b506105a26004803603602081101561059b57600080fd5b5035610c09565b005b3480156105b057600080fd5b50d380156105bd57600080fd5b50d280156105ca57600080fd5b506101fa600480360360408110156105e157600080fd5b506001600160a01b038135169060200135610c1d565b34801561060357600080fd5b50d3801561061057600080fd5b50d2801561061d57600080fd5b506101fa6004803603604081101561063457600080fd5b506001600160a01b038135169060200135610c8b565b34801561065657600080fd5b50d3801561066357600080fd5b50d2801561067057600080fd5b506103306004803603604081101561068757600080fd5b506001600160a01b0381358116916020013516610c9f565b60038054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561072b5780601f106107005761010080835404028352916020019161072b565b820191906000526020600020905b81548152906001019060200180831161070e57829003601f168201915b5050505050905090565b6000610749610742610d89565b8484610d98565b50600192915050565b606061075c611338565b50604080516060810182526001600160a01b0388166000818152600760209081529084902054835282015290810186905261079a8782878787610e84565b6107d55760405162461bcd60e51b815260040180806020018281038252602181526020018061145e6021913960400191505060405180910390fd5b6001600160a01b0387166000908152600760205260409020546107ff90600163ffffffff610d2816565b6001600160a01b03881660008181526007602090815260408083209490945583519283523383820181905260609484018581528b51958501959095528a517f5845892132946850460bff5a0083f71031bc5bf9aadcd40f1de79423eac9b10b958d9592948d94919260808501928601918190849084905b8381101561088e578181015183820152602001610876565b50505050905090810190601f1680156108bb5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a160006060306001600160a01b0316888a6040516020018083805190602001908083835b6020831061090c5780518252601f1990920191602091820191016108ed565b6001836020036101000a038019825116818451168082178552505050505050905001826001600160a01b03166001600160a01b031660601b8152601401925050506040516020818303038152906040526040518082805190602001908083835b6020831061098b5780518252601f19909201916020918201910161096c565b6001836020036101000a0380198251168184511680821785525050505050509050019150506000604051808303816000865af19150503d80600081146109ed576040519150601f19603f3d011682016040523d82523d6000602084013e6109f2565b606091505b509150915081610a49576040805162461bcd60e51b815260206004820152601c60248201527f46756e6374696f6e2063616c6c206e6f74207375636365737366756c00000000604482015290519081900360640190fd5b98975050505050505050565b604051806040016040528060018152602001603160f81b81525081565b60025490565b60065490565b6000610a8b848484610f61565b610b0184610a97610d89565b610afc85604051806060016040528060288152602001611436602891396001600160a01b038a16600090815260016020526040812090610ad5610d89565b6001600160a01b03168152602081019190915260400160002054919063ffffffff6110c816565b610d98565b5060019392505050565b6001600160a01b031660009081526007602052604090205490565b60055460ff1690565b63ffffffff461690565b6000610749610b46610d89565b84610afc8560016000610b57610d89565b6001600160a01b03908116825260208083019390935260409182016000908120918c16815292529020549063ffffffff610d2816565b6001600160a01b031660009081526020819052604090205490565b60048054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561072b5780601f106107005761010080835404028352916020019161072b565b610c1a610c14610d89565b8261115f565b50565b6000610749610c2a610d89565b84610afc856040518060600160405280602581526020016114c86025913960016000610c54610d89565b6001600160a01b03908116825260208083019390935260409182016000908120918d1681529252902054919063ffffffff6110c816565b6000610749610c98610d89565b8484610f61565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b600033301415610d225760606000368080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152505050503601516001600160a01b03169150610d259050565b50335b90565b600082820183811015610d82576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b9392505050565b6000610d93610cca565b905090565b6001600160a01b038316610ddd5760405162461bcd60e51b81526004018080602001828103825260248152602001806114a46024913960400191505060405180910390fd5b6001600160a01b038216610e225760405162461bcd60e51b81526004018080602001828103825260228152602001806113c96022913960400191505060405180910390fd5b6001600160a01b03808416600081815260016020908152604080832094871680845294825291829020859055815185815291517f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259281900390910190a3505050565b60006001600160a01b038616610ecb5760405162461bcd60e51b81526004018080602001828103825260258152602001806114116025913960400191505060405180910390fd5b6001610ede610ed98761125b565b6112e7565b83868660405160008152602001604052604051808581526020018460ff1660ff1681526020018381526020018281526020019450505050506020604051602081039080840390855afa158015610f38573d6000803e3d6000fd5b505050602060405103516001600160a01b0316866001600160a01b031614905095945050505050565b6001600160a01b038316610fa65760405162461bcd60e51b815260040180806020018281038252602581526020018061147f6025913960400191505060405180910390fd5b6001600160a01b038216610feb5760405162461bcd60e51b81526004018080602001828103825260238152602001806113636023913960400191505060405180910390fd5b610ff6838383611333565b611039816040518060600160405280602681526020016113eb602691396001600160a01b038616600090815260208190526040902054919063ffffffff6110c816565b6001600160a01b03808516600090815260208190526040808220939093559084168152205461106e908263ffffffff610d2816565b6001600160a01b038084166000818152602081815260409182902094909455805185815290519193928716927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef92918290030190a3505050565b600081848411156111575760405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561111c578181015183820152602001611104565b50505050905090810190601f1680156111495780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b505050900390565b6001600160a01b0382166111ba576040805162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f206164647265737300604482015290519081900360640190fd5b6111c660008383611333565b6002546111d9908263ffffffff610d2816565b6002556001600160a01b038216600090815260208190526040902054611205908263ffffffff610d2816565b6001600160a01b0383166000818152602081815260408083209490945583518581529351929391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a35050565b600060405180608001604052806043815260200161138660439139805190602001208260000151836020015184604001518051906020012060405160200180858152602001848152602001836001600160a01b03166001600160a01b03168152602001828152602001945050505050604051602081830303815290604052805190602001209050919050565b60006112f1610a78565b82604051602001808061190160f01b81525060020183815260200182815260200192505050604051602081830303815290604052805190602001209050919050565b505050565b60405180606001604052806000815260200160006001600160a01b0316815260200160608152509056fe45524332303a207472616e7366657220746f20746865207a65726f20616464726573734d6574615472616e73616374696f6e2875696e74323536206e6f6e63652c616464726573732066726f6d2c62797465732066756e6374696f6e5369676e61747572652945524332303a20617070726f766520746f20746865207a65726f206164647265737345524332303a207472616e7366657220616d6f756e7420657863656564732062616c616e63654e61746976654d6574615472616e73616374696f6e3a20494e56414c49445f5349474e455245524332303a207472616e7366657220616d6f756e74206578636565647320616c6c6f77616e63655369676e657220616e64207369676e617475726520646f206e6f74206d6174636845524332303a207472616e736665722066726f6d20746865207a65726f206164647265737345524332303a20617070726f76652066726f6d20746865207a65726f206164647265737345524332303a2064656372656173656420616c6c6f77616e63652062656c6f77207a65726fa2646970667358221220e88a39c1974a6a6b8e7eaf7397b6dacc73b02fab4ffa0a1b3e70cb5b93b63efc64736f6c63430006080033454950373132446f6d61696e28737472696e67206e616d652c737472696e672076657273696f6e2c6164647265737320766572696679696e67436f6e74726163742c627974657333322073616c7429";

        //fabi,fbin,param,10000000000,DummyERC20,0,0,self.accountHex,10000000000
        //def deployContract(self,abi,bytecode,params,originEnergyLimit,name,callValue,consumeUserResourcePercent,accountHex,bandwidthLimit):

        // visible: true
        HttpMethed2.smartContractCreateBroadcast(httpnode, abi, bin,"","DummyERC20", quince58,"true",quincekey);
    }
    @Test(enabled = true, description = "multi sign ")
    public void smartContractCreate() {
        String abi = "[{\"inputs\":[{\"internalType\":\"string\",\"name\":\"name_\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"symbol_\",\"type\":\"string\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Approval\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"userAddress\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address payable\",\"name\":\"relayerAddress\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bytes\",\"name\":\"functionSignature\",\"type\":\"bytes\"}],\"name\":\"MetaTransactionExecuted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"value\",\"type\":\"uint256\"}],\"name\":\"Transfer\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"ERC712_VERSION\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"owner\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"}],\"name\":\"allowance\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"approve\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"balanceOf\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"decimals\",\"outputs\":[{\"internalType\":\"uint8\",\"name\":\"\",\"type\":\"uint8\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"subtractedValue\",\"type\":\"uint256\"}],\"name\":\"decreaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"userAddress\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"functionSignature\",\"type\":\"bytes\"},{\"internalType\":\"bytes32\",\"name\":\"sigR\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"sigS\",\"type\":\"bytes32\"},{\"internalType\":\"uint8\",\"name\":\"sigV\",\"type\":\"uint8\"}],\"name\":\"executeMetaTransaction\",\"outputs\":[{\"internalType\":\"bytes\",\"name\":\"\",\"type\":\"bytes\"}],\"stateMutability\":\"payable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getChainId\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getDomainSeperator\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"getNonce\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"nonce\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"spender\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"addedValue\",\"type\":\"uint256\"}],\"name\":\"increaseAllowance\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"mint\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"name\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"symbol\",\"outputs\":[{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"totalSupply\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transfer\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"recipient\",\"type\":\"address\"},{\"internalType\":\"uint256\",\"name\":\"amount\",\"type\":\"uint256\"}],\"name\":\"transferFrom\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
        String bin = "60806040526005805461ff00191690553480156200001c57600080fd5b50d380156200002a57600080fd5b50d280156200003857600080fd5b5060405162001bbb38038062001bbb833981810160405260408110156200005e57600080fd5b81019080805160405193929190846401000000008211156200007f57600080fd5b9083019060208201858111156200009557600080fd5b8251640100000000811182820188101715620000b057600080fd5b82525081516020918201929091019080838360005b83811015620000df578181015183820152602001620000c5565b50505050905090810190601f1680156200010d5780820380516001836020036101000a031916815260200191505b50604052602001805160405193929190846401000000008211156200013157600080fd5b9083019060208201858111156200014757600080fd5b82516401000000008111828201881017156200016257600080fd5b82525081516020918201929091019080838360005b838110156200019157818101518382015260200162000177565b50505050905090810190601f168015620001bf5780820380516001836020036101000a031916815260200191505b50604052505082518391508290620001df90600390602085019062000598565b508051620001f590600490602084019062000598565b50506005805460ff19166012179055506b204fce5e3e2502611000000062000239620002296001600160e01b036200025616565b826001600160e01b036200027316565b6200024d836001600160e01b036200038b16565b5050506200063a565b60006200026d6200040060201b62000cca1760201c565b90505b90565b6001600160a01b038216620002cf576040805162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f206164647265737300604482015290519081900360640190fd5b620002e6600083836001600160e01b036200045f16565b62000302816002546200046460201b62000d281790919060201c565b6002556001600160a01b038216600090815260208181526040909120546200033591839062000d2862000464821b17901c565b6001600160a01b0383166000818152602081815260408083209490945583518581529351929391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a35050565b600554610100900460ff1615620003da576040805162461bcd60e51b815260206004820152600e60248201526d185b1c9958591e481a5b9a5d195960921b604482015290519081900360640190fd5b620003ee816001600160e01b03620004c616565b506005805461ff001916610100179055565b6000333014156200045a5760606000368080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152505050503601516001600160a01b03169150620002709050565b503390565b505050565b600082820183811015620004bf576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b9392505050565b6040518060800160405280604f815260200162001b6c604f913980516020918201208251838301206040805180820190915260018152603160f81b930192909252907fc89efdaa54c0f20c7adf612882df0950f5a951637e0307cdcb4c672f298b8bc6306200053d6001600160e01b036200058e16565b604080516020808201979097528082019590955260608501939093526001600160a01b03909116608084015260a0808401919091528151808403909101815260c09092019052805191012060065550565b63ffffffff461690565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620005db57805160ff19168380011785556200060b565b828001600101855582156200060b579182015b828111156200060b578251825591602001919060010190620005ee565b50620006199291506200061d565b5090565b6200027091905b8082111562000619576000815560010162000624565b611522806200064a6000396000f3fe6080604052600436106100fe5760003560e01c8063313ce5671161009557806395d89b411161006457806395d89b411461052f578063a0712d681461055e578063a457c2d7146105a4578063a9059cbb146105f7578063dd62ed3e1461064a576100fe565b8063313ce5671461041b5780633408e47014610460578063395093511461048f57806370a08231146104e2576100fe565b806318160ddd116100d157806318160ddd1461030157806320379ee51461034257806323b872dd146103715780632d0335ab146103ce576100fe565b806306fdde0314610103578063095ea7b3146101a75780630c53c51c1461020e5780630f7e5970146102d2575b600080fd5b34801561010f57600080fd5b50d3801561011c57600080fd5b50d2801561012957600080fd5b5061013261069f565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561016c578181015183820152602001610154565b50505050905090810190601f1680156101995780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156101b357600080fd5b50d380156101c057600080fd5b50d280156101cd57600080fd5b506101fa600480360360408110156101e457600080fd5b506001600160a01b038135169060200135610735565b604080519115158252519081900360200190f35b610132600480360360a081101561022457600080fd5b6001600160a01b03823516919081019060408101602082013564010000000081111561024f57600080fd5b82018360208201111561026157600080fd5b8035906020019184600183028401116401000000008311171561028357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550508235935050506020810135906040013560ff16610752565b3480156102de57600080fd5b50d380156102eb57600080fd5b50d280156102f857600080fd5b50610132610a55565b34801561030d57600080fd5b50d3801561031a57600080fd5b50d2801561032757600080fd5b50610330610a72565b60408051918252519081900360200190f35b34801561034e57600080fd5b50d3801561035b57600080fd5b50d2801561036857600080fd5b50610330610a78565b34801561037d57600080fd5b50d3801561038a57600080fd5b50d2801561039757600080fd5b506101fa600480360360608110156103ae57600080fd5b506001600160a01b03813581169160208101359091169060400135610a7e565b3480156103da57600080fd5b50d380156103e757600080fd5b50d280156103f457600080fd5b506103306004803603602081101561040b57600080fd5b50356001600160a01b0316610b0b565b34801561042757600080fd5b50d3801561043457600080fd5b50d2801561044157600080fd5b5061044a610b26565b6040805160ff9092168252519081900360200190f35b34801561046c57600080fd5b50d3801561047957600080fd5b50d2801561048657600080fd5b50610330610b2f565b34801561049b57600080fd5b50d380156104a857600080fd5b50d280156104b557600080fd5b506101fa600480360360408110156104cc57600080fd5b506001600160a01b038135169060200135610b39565b3480156104ee57600080fd5b50d380156104fb57600080fd5b50d2801561050857600080fd5b506103306004803603602081101561051f57600080fd5b50356001600160a01b0316610b8d565b34801561053b57600080fd5b50d3801561054857600080fd5b50d2801561055557600080fd5b50610132610ba8565b34801561056a57600080fd5b50d3801561057757600080fd5b50d2801561058457600080fd5b506105a26004803603602081101561059b57600080fd5b5035610c09565b005b3480156105b057600080fd5b50d380156105bd57600080fd5b50d280156105ca57600080fd5b506101fa600480360360408110156105e157600080fd5b506001600160a01b038135169060200135610c1d565b34801561060357600080fd5b50d3801561061057600080fd5b50d2801561061d57600080fd5b506101fa6004803603604081101561063457600080fd5b506001600160a01b038135169060200135610c8b565b34801561065657600080fd5b50d3801561066357600080fd5b50d2801561067057600080fd5b506103306004803603604081101561068757600080fd5b506001600160a01b0381358116916020013516610c9f565b60038054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561072b5780601f106107005761010080835404028352916020019161072b565b820191906000526020600020905b81548152906001019060200180831161070e57829003601f168201915b5050505050905090565b6000610749610742610d89565b8484610d98565b50600192915050565b606061075c611338565b50604080516060810182526001600160a01b0388166000818152600760209081529084902054835282015290810186905261079a8782878787610e84565b6107d55760405162461bcd60e51b815260040180806020018281038252602181526020018061145e6021913960400191505060405180910390fd5b6001600160a01b0387166000908152600760205260409020546107ff90600163ffffffff610d2816565b6001600160a01b03881660008181526007602090815260408083209490945583519283523383820181905260609484018581528b51958501959095528a517f5845892132946850460bff5a0083f71031bc5bf9aadcd40f1de79423eac9b10b958d9592948d94919260808501928601918190849084905b8381101561088e578181015183820152602001610876565b50505050905090810190601f1680156108bb5780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a160006060306001600160a01b0316888a6040516020018083805190602001908083835b6020831061090c5780518252601f1990920191602091820191016108ed565b6001836020036101000a038019825116818451168082178552505050505050905001826001600160a01b03166001600160a01b031660601b8152601401925050506040516020818303038152906040526040518082805190602001908083835b6020831061098b5780518252601f19909201916020918201910161096c565b6001836020036101000a0380198251168184511680821785525050505050509050019150506000604051808303816000865af19150503d80600081146109ed576040519150601f19603f3d011682016040523d82523d6000602084013e6109f2565b606091505b509150915081610a49576040805162461bcd60e51b815260206004820152601c60248201527f46756e6374696f6e2063616c6c206e6f74207375636365737366756c00000000604482015290519081900360640190fd5b98975050505050505050565b604051806040016040528060018152602001603160f81b81525081565b60025490565b60065490565b6000610a8b848484610f61565b610b0184610a97610d89565b610afc85604051806060016040528060288152602001611436602891396001600160a01b038a16600090815260016020526040812090610ad5610d89565b6001600160a01b03168152602081019190915260400160002054919063ffffffff6110c816565b610d98565b5060019392505050565b6001600160a01b031660009081526007602052604090205490565b60055460ff1690565b63ffffffff461690565b6000610749610b46610d89565b84610afc8560016000610b57610d89565b6001600160a01b03908116825260208083019390935260409182016000908120918c16815292529020549063ffffffff610d2816565b6001600160a01b031660009081526020819052604090205490565b60048054604080516020601f600260001961010060018816150201909516949094049384018190048102820181019092528281526060939092909183018282801561072b5780601f106107005761010080835404028352916020019161072b565b610c1a610c14610d89565b8261115f565b50565b6000610749610c2a610d89565b84610afc856040518060600160405280602581526020016114c86025913960016000610c54610d89565b6001600160a01b03908116825260208083019390935260409182016000908120918d1681529252902054919063ffffffff6110c816565b6000610749610c98610d89565b8484610f61565b6001600160a01b03918216600090815260016020908152604080832093909416825291909152205490565b600033301415610d225760606000368080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152505050503601516001600160a01b03169150610d259050565b50335b90565b600082820183811015610d82576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b9392505050565b6000610d93610cca565b905090565b6001600160a01b038316610ddd5760405162461bcd60e51b81526004018080602001828103825260248152602001806114a46024913960400191505060405180910390fd5b6001600160a01b038216610e225760405162461bcd60e51b81526004018080602001828103825260228152602001806113c96022913960400191505060405180910390fd5b6001600160a01b03808416600081815260016020908152604080832094871680845294825291829020859055815185815291517f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b9259281900390910190a3505050565b60006001600160a01b038616610ecb5760405162461bcd60e51b81526004018080602001828103825260258152602001806114116025913960400191505060405180910390fd5b6001610ede610ed98761125b565b6112e7565b83868660405160008152602001604052604051808581526020018460ff1660ff1681526020018381526020018281526020019450505050506020604051602081039080840390855afa158015610f38573d6000803e3d6000fd5b505050602060405103516001600160a01b0316866001600160a01b031614905095945050505050565b6001600160a01b038316610fa65760405162461bcd60e51b815260040180806020018281038252602581526020018061147f6025913960400191505060405180910390fd5b6001600160a01b038216610feb5760405162461bcd60e51b81526004018080602001828103825260238152602001806113636023913960400191505060405180910390fd5b610ff6838383611333565b611039816040518060600160405280602681526020016113eb602691396001600160a01b038616600090815260208190526040902054919063ffffffff6110c816565b6001600160a01b03808516600090815260208190526040808220939093559084168152205461106e908263ffffffff610d2816565b6001600160a01b038084166000818152602081815260409182902094909455805185815290519193928716927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef92918290030190a3505050565b600081848411156111575760405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b8381101561111c578181015183820152602001611104565b50505050905090810190601f1680156111495780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b505050900390565b6001600160a01b0382166111ba576040805162461bcd60e51b815260206004820152601f60248201527f45524332303a206d696e7420746f20746865207a65726f206164647265737300604482015290519081900360640190fd5b6111c660008383611333565b6002546111d9908263ffffffff610d2816565b6002556001600160a01b038216600090815260208190526040902054611205908263ffffffff610d2816565b6001600160a01b0383166000818152602081815260408083209490945583518581529351929391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a35050565b600060405180608001604052806043815260200161138660439139805190602001208260000151836020015184604001518051906020012060405160200180858152602001848152602001836001600160a01b03166001600160a01b03168152602001828152602001945050505050604051602081830303815290604052805190602001209050919050565b60006112f1610a78565b82604051602001808061190160f01b81525060020183815260200182815260200192505050604051602081830303815290604052805190602001209050919050565b505050565b60405180606001604052806000815260200160006001600160a01b0316815260200160608152509056fe45524332303a207472616e7366657220746f20746865207a65726f20616464726573734d6574615472616e73616374696f6e2875696e74323536206e6f6e63652c616464726573732066726f6d2c62797465732066756e6374696f6e5369676e61747572652945524332303a20617070726f766520746f20746865207a65726f206164647265737345524332303a207472616e7366657220616d6f756e7420657863656564732062616c616e63654e61746976654d6574615472616e73616374696f6e3a20494e56414c49445f5349474e455245524332303a207472616e7366657220616d6f756e74206578636565647320616c6c6f77616e63655369676e657220616e64207369676e617475726520646f206e6f74206d6174636845524332303a207472616e736665722066726f6d20746865207a65726f206164647265737345524332303a20617070726f76652066726f6d20746865207a65726f206164647265737345524332303a2064656372656173656420616c6c6f77616e63652062656c6f77207a65726fa2646970667358221220e88a39c1974a6a6b8e7eaf7397b6dacc73b02fab4ffa0a1b3e70cb5b93b63efc64736f6c63430006080033454950373132446f6d61696e28737472696e67206e616d652c737472696e672076657273696f6e2c6164647265737320766572696679696e67436f6e74726163742c627974657333322073616c7429";

        //fabi,fbin,param,10000000000,DummyERC20,0,0,self.accountHex,10000000000
        //def deployContract(self,abi,bytecode,params,originEnergyLimit,name,callValue,consumeUserResourcePercent,accountHex,bandwidthLimit):

        // visible: true
        String constructParam = "00000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000000c22746573744d756c7469312200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000003544d310000000000000000000000000000000000000000000000000000000000";
        String transactionStr = HttpMethed2.smartContractCreate(httpnode, abi, bin,constructParam,"DummyERC20", quince58,"true",3,wqq1key);
        //String transactionStr = HttpMethed2.smartContractCreate(httpnode, abi, bin,constructParam,HttpMethed2.str2hex("DummyERC20"), ByteArray.toHexString(quince),"false",3,wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", quince58);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));

    }


    @Test(enabled = true, description = "multi sign ")
    public void smartContractUpdate() {
        String contractAddress="TJMZNqLDPLHDMakYxBKonSvd26VdjWHVhP";
        String contractAddress_hex = "415BFB2A7DF6303F0D733548507C2922C70438542C";
        // visible: true
        String transactionStr = HttpMethed2.updateContractSetting(httpnode, quince58, contractAddress, 38,"true",3,wqq1key);
        //String transactionStr = HttpMethed2.updateContractSetting(httpnode, ByteArray.toHexString(quince), contractAddress_hex, 38,"false",3,wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign ")
    public void smartContractUpdateEnergyLimit() {
        //String contractAddress="TU4zZAaKMdNGX4gwDhP3yz1zXZ5Z9UezxL";
        //String contractAddress_hex ="41C68A5EC02BC77533790A3702F42C05071E004735";
        String contractAddress="TJMZNqLDPLHDMakYxBKonSvd26VdjWHVhP";
        String contractAddress_hex = "415BFB2A7DF6303F0D733548507C2922C70438542C";
        // visible: true
        String transactionStr = HttpMethed2.updateEnergyLimit(httpnode, quince58, contractAddress, 2000000,"true",3,wqq1key);
        //String transactionStr = HttpMethed2.updateEnergyLimit(httpnode, ByteArray.toHexString(quince), contractAddress_hex, 3000000,"false",3,wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign ")
    public void smartContractClearABI() {
        String contractAddress="TYSLxryyvX4LCZo6GvXtG2QUe6UKjfH9dN";
        String contractAddress1 = "TJMZNqLDPLHDMakYxBKonSvd26VdjWHVhP";
        String contractAddress1_hex="415BFB2A7DF6303F0D733548507C2922C70438542C";
        // visible: true
        String transactionStr = HttpMethed2.clearABi(httpnode, quince58, contractAddress1, "true",3,wqq1key);
        //String transactionStr = HttpMethed2.clearABi(httpnode, ByteArray.toHexString(quince), contractAddress1_hex, "false",3,wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }


    @Test(enabled = true, description = "visible true/false all tested")
    public void bosorExchangeCreate() {
        /*String firstId="1000340";
        String SecondId="1000323";
        // visible: true
        String transactionStr = HttpMethed2.exchangeCreate(httpnode, quince58, firstId, 1000000L,SecondId,1000L, "true", 3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));*/

        String first_Id="1000323";
        String Second_Id="1000082";
        // visible: true
        String transactionStr2 = HttpMethed2.exchangeCreate(httpnode, ByteArray.toHexString(quince), first_Id, 10000L,Second_Id,100L, "false", 3, wqq1key);

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
    @Test(enabled = true, description = "multi sign ")
    public void bosorExchangeTransaction() {
        // list exchanges: https://nile.trongrid.io/wallet/listexchanges
        // exchangeId = 17
        //qa: String firstId="1000340";
        //qa; String secondId="1000323";
        String firstId="1003394";
        String secondId="1002922";
        log.info("firstId: "+ HttpMethed2.str2hex(firstId));
        log.info("SecondId: "+ HttpMethed2.str2hex(secondId));

        // visible: true
        //String transactionStr = HttpMethed2.exchangeTransaction(httpnode, quince58, 17, firstId, 2000L,1L, "true", 3, wqq1key);
        String transactionStr = HttpMethed2.exchangeTransaction(httpnode, ByteArray.toHexString(quince), 184, firstId, 0L,0L, "false", 3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign ")
    public void bosorExchangeInject() {
        // list exchanges: https://nile.trongrid.io/wallet/listexchanges
        // exchangeId = 17
        String firstId="1000340";
        String secondId="1000323";

        // visible: true
        //String transactionStr = HttpMethed2.exchangeInject(httpnode, quince58, 17, secondId, 2L,"true", 3, wqq1key);
        String transactionStr = HttpMethed2.exchangeInject(httpnode, ByteArray.toHexString(quince), 17, secondId, 2L,"false", 3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }

    @Test(enabled = true, description = "multi sign ")
    public void bosorExchangeWithdraw() {
        // list exchanges: https://nile.trongrid.io/wallet/listexchanges
        // exchangeId = 17
        /*String firstId="1000340";
        String secondId="1000323";

        // visible: true
        String transactionStr = HttpMethed2.exchangeWithdraw(httpnode, quince58, 17, firstId, 3689L,"true", 3, wqq1key);

        JSONObject object = new JSONObject();
        object.put("address", wqq158);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(transactionStr));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));*/


        String first_Id="1000323";
        String second_Id="1000082";

        // visible: true
        String transactionStr2 = HttpMethed2.exchangeWithdraw(httpnode, quince58, 18, first_Id, 100L,"true", 3, wqq1key);
        //String transactionStr2 = HttpMethed2.exchangeWithdraw(httpnode, ByteArray.toHexString(quince), 18, first_Id, 100L,"false", 3, wqq1key);

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

    @Test(enabled = true, description = "multi sign ")
    public void getMarketOrderByAccount() {

        //HttpMethed2.getMarketOrderByAccount(httpnode,quince,"true");
        //HttpMethed2.getMarketPairList(httpnode,"true");
        HttpMethed2.marketSellAssetGetTxId(httpnode,quince58,"1000323",1000L, "1000340",5L, quincekey, "true");
        /*String first_Id="1000323";
        String second_Id="1000082";

        // visible: true
        String transactionStr2 = HttpMethed2.exchangeWithdraw(httpnode, quince58, 18, first_Id, 100L,"true", 3, wqq1key);
        //String transactionStr2 = HttpMethed2.exchangeWithdraw(httpnode, ByteArray.toHexString(quince), 18, first_Id, 100L,"false", 3, wqq1key);

        JSONObject object2 = new JSONObject();
        object2.put("address", wqq158);
        object2.put("netType", "main_net");
        object2.put("transaction", JSONObject.parse(transactionStr2));
        param.put("serializable", "true");
        res = TronlinkApiList.multiTransactionNoSig(object2,param,null);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));*/
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
}