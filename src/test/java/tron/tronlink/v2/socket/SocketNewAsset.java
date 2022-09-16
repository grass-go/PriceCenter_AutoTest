package tron.tronlink.v2.socket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.net.URI;
import java.util.Map;

//To do modify signature
@Slf4j
public class SocketNewAsset {
  boolean flag = false;
  @Test(enabled = false,description = "get new asset push message")
  public void test00GetNewAssetPush() {
    //Map<String,String> header = TronlinkApiList.getV2Header();
    Map<String, String> headers = TronlinkApiList.getNewSigHeader(TronlinkApiList.defaultSys, TronlinkApiList.defaultVersion, TronlinkApiList.defaultLang, TronlinkApiList.defaultPkg);
    Map<String, String> params = TronlinkApiList.getNewSigParams(TronlinkApiList.defaultSys);
    params.put("address", "416BDDAC6267A841836AEE7605F54AEE7677008E07");
    String signature = TronlinkApiList.getNewSignature("/api/message", "POST", params.get("address"),params, headers);
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

    String host = TronlinkBase.tronlinkUrl.substring(8);
    try {
      String url = "wss://" + host +"/api/message?nonce="+params.get("nonce")+"&secretId=" + params.get("secretId") + "&signature="+ signature +"%3D&address="+ params.get("address");
      System.out.println(url);
      URI uri = new URI(url);
      WebSocketClient mWs = new WebSocketClient(uri,headers){
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
      mWs.setConnectionLostTimeout(9);
      mWs.connect();
      Thread.sleep(7000);
      Assert.assertTrue(flag);
      mWs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
