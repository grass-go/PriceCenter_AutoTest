package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class SocketApiMessage {
    boolean flag = false;
    @Test(enabled = true,description = "get new asset push message ")
    public void test00GetNotSignRecord() {
        Map<String,String> header = TronlinkApiList.getV2Header();
        JSONObject data = new JSONObject();
        JSONObject addressInfo = new JSONObject();
        JSONObject addressInfo1 = new JSONObject();
        JSONArray addressInfoList = new JSONArray();

        addressInfo.put("address","416BDDAC6267A841836AEE7605F54AEE7677008E07");
        addressInfo.put("addressType","1");
        data.put("addressInfo",addressInfo);

        addressInfo1.put("address","416BDDAC6267A841836AEE7605F54AEE7677008E07");
        addressInfo1.put("addressType","2");

        addressInfoList.add(addressInfo);
        addressInfoList.add(addressInfo1);

        data.put("addressInfoList",addressInfoList);
        data.put("deviceToken","api-monitor");
        data.put("msgTypes","[1]");
        data.put("msgType","0");
        data.put("wssAddrTypes","[1,2]");
        data.put("firebaseAddrTypes","[1]");

        try {
            String url = "wss://list.tronlink.org/api/message?nonce=12345&secretId=SFSUIOJBFMLKSJIF&signature=QvMckbIsFggRBSzkamjVyrCtoHk%3D&address=416BDDAC6267A841836AEE7605F54AEE7677008E07";
            URI uri = new URI(url);
            WebSocketClient mWs = new WebSocketClient(uri,header){
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("onOpen");
                }

                @Override
                public void onMessage(String s) {
                    log.info(s);
                    if(s.contains("OK")){
                        flag = true;
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    log.info("onClose");
                }

                @Override
                public void onError(Exception e) {
                    log.info("onError:"+e.getMessage());
                }
            };
            mWs.setConnectionLostTimeout(8);
            mWs.connect();
            Thread.sleep(5000);
            Assert.assertTrue(flag);
            mWs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
