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

@Slf4j
public class CreateMultiTransactionWithSerializable2 extends TronlinkBase {
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
    String address258 = Base58.encode58Check(address2);

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
        /*log.info("wqq debug: ByteArray.toHexString: "+ ByteArray.toHexString(quince));
        String transactionStr2 = HttpMethed2.createAccount(httpnode, ByteArray.toHexString(quince), ByteArray.toHexString(randomAddress),"false",3, wqq1key);
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
*/
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