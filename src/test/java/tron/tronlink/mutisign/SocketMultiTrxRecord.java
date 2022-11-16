package tron.tronlink.mutisign;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.net.URI;
import java.util.HashMap;


@Slf4j
public class SocketMultiTrxRecord {
    boolean flag = false;
    private HashMap<String, String> header = new HashMap<>();
    private HashMap<String, String> params = new HashMap<>();

    @Test(enabled = true,description = "create socket connection to get multi sign record",groups="multiSign")
    public void test00GetNotSignRecord() {
        flag=false;
        String host = TronlinkBase.tronlinkUrl.substring(8);

        header.put("System","Chrome");
        header.put("Version","4.11.0");
        header.put("Lang","1");
        header.put("packageName","com.tronlinkpro.wallet");
        header.put("channel","official");
        header.put("chain","MainChain");
        header.put("DeviceID","monitor-test");
        header.put("ts",java.lang.String.valueOf(System.currentTimeMillis()));

        params.put("nonce","12345");
        params.put("secretId","A4ADE880F46CA8D4");
        String thisSignature = TronlinkApiList.getNewSignature("/api/wallet/multi/socket", "GET", "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8",params,header);

        try {
            String url = "wss://" + host +"/api/wallet/multi/socket?nonce=12345&secretId=A4ADE880F46CA8D4&address=TY9touJknFcezjLiaGTjnH1dUHiqriu6L8&netType=main_net&signature="+thisSignature;
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

    @Test(enabled = true,description = "create socket connection to get multi sign record",groups="multiSign")
    public void test00GetNotSignRecordLowVersionWithNoSig() {
        flag=false;
        String host = TronlinkBase.tronlinkUrl.substring(8);
        header.clear();
        header.put("System","Android");
        header.put("Version","3.10.0");
        try {
            String url = "wss://" + host +"/api/wallet/multi/socket?address=TY9touJknFcezjLiaGTjnH1dUHiqriu6L8&start=0&limit=20&netType=main_net&state=0";
            System.out.println(url);
            URI uri = new URI(url);
            WebSocketClient mWs = new WebSocketClient(uri,header){
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

    @Test(enabled = true,description = "create socket connection to get multi sign record",groups="multiSign")
    public void test00GetNotSignRecordHighVersionWithNoSig() throws InterruptedException {
        flag=false;
        Thread.sleep(5000);
        String host = TronlinkBase.tronlinkUrl.substring(8);
        header.clear();
        header.put("System","Chrome");
        header.put("Version","4.11.0");
        try {
            String url = "wss://" + host +"/api/wallet/multi/socket?address=TY9touJknFcezjLiaGTjnH1dUHiqriu6L8&start=0&limit=20&netType=main_net&state=0";
            System.out.println(url);
            URI uri = new URI(url);
            WebSocketClient mWs = new WebSocketClient(uri,header){
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
            Assert.assertFalse(flag);
            mWs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
