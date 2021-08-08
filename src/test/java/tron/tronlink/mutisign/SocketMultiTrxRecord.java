package tron.tronlink.mutisign;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.tronlink.base.TronlinkBase;

import java.net.URI;



@Slf4j
public class SocketMultiTrxRecord {
    boolean flag = false;
    @Test(enabled = false,description = "create socket connection to get multi sign record")
    public void test00GetNotSignRecord() {
        String host = TronlinkBase.tronlinkUrl.substring(8);
        try {
            String url = "wss://" + host +"/api/wallet/multi/socket?address=TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW&start=0&limit=20&netType=main_net&state=0";
            System.out.println(url);
            URI uri = new URI(url);
            WebSocketClient mWs = new WebSocketClient(uri){
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info("onOpen");
                }

                @Override
                public void onMessage(String s) {
                    log.info(s);
                    flag = true;
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
