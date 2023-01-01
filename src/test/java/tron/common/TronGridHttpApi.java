package tron.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.util.EntityUtils;
import tron.common.utils.TronlinkServerHttpClient;

@Slf4j
public class TronGridHttpApi {
    static HttpClient httpClient;
    static HttpPost httppost;
    static HttpResponse response;
    static Integer connectionTimeout = 19000;
    static Integer soTimeout = 18000;
    static String transactionString;
    static String transactionSignString;
    static JSONObject responseContent;
    static JSONObject signResponseContent;
    static JSONObject transactionApprovedListContent;
    static String transactionStr;

    static {
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
        pccm.setDefaultMaxPerRoute(80);
        pccm.setMaxTotal(100);

        httpClient = new DefaultHttpClient(pccm);
    }

    public static String sendCoin(String httpNode, String fromAddress, String toAddress,
                                  Long amount, String visible, Integer permissionId, String fromKey) {
        try {
            final String requestUrl = "http://" + httpNode + "/wallet/createtransaction";
            JSONObject userBaseObj2 = new JSONObject();
            userBaseObj2.put("to_address", toAddress);
            userBaseObj2.put("owner_address", fromAddress);
            userBaseObj2.put("amount", amount);
            userBaseObj2.put("visible", visible);
            userBaseObj2.put("Permission_id", permissionId);
            log.info("userBaseObj2:"+userBaseObj2.toString());
            response = TronlinkServerHttpClient.createPostConnect(requestUrl,null, userBaseObj2,null);
            transactionString = EntityUtils.toString(response.getEntity());
            JSONObject transactionObject = TronlinkServerHttpClient.parseString2JsonObject(transactionString);
            transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+120000);

            transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
            log.info("-----------sign information-----------------");
            log.info(transactionSignString);

            JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionSignString);
            jsonObject.remove("visible");
            //jsonObject.remove("txID");
            jsonObject.remove("raw_data_hex");
            transactionStr = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return transactionStr;
    }

    public static String freezeBalance(String httpNode, String ownerAddress, Long frozenBalance,
                                       Integer frozenDuration, Integer resourceCode, String receiverAddress,
                                       String visible, Integer permissionId, String fromKey) {
        try {
            final String requestUrl = "http://" + httpNode + "/wallet/freezebalance";
            JSONObject userBaseObj2 = new JSONObject();
            userBaseObj2.put("owner_address", ownerAddress);
            userBaseObj2.put("frozen_balance", frozenBalance);
            userBaseObj2.put("frozen_duration", frozenDuration);
            if (resourceCode == 0) {
                userBaseObj2.put("resource", "BANDWIDTH");
            }
            if (resourceCode == 1) {
                userBaseObj2.put("resource", "ENERGY");
            }
            if (receiverAddress != null) {
                userBaseObj2.put("receiver_address", receiverAddress);
            }
            userBaseObj2.put("visible", visible);
            userBaseObj2.put("Permission_id", permissionId);
            log.info("userBaseObj2:"+userBaseObj2.toString());
            response = TronlinkServerHttpClient.createPostConnect(requestUrl, null, userBaseObj2, null);
            transactionString = EntityUtils.toString(response.getEntity());
            log.info("transactionString:"+transactionString);
            JSONObject transactionObject = TronlinkServerHttpClient.parseString2JsonObject(transactionString);
            transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

            transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
            log.info("-----------sign information-----------------");
            log.info(transactionSignString);

            JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionSignString);
            jsonObject.remove("visible");
            //jsonObject.remove("txID");
            jsonObject.remove("raw_data_hex");
            transactionStr = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return transactionStr;
    }

    public static String transferAsset(String httpNode, String ownerAddress, String toAddress, String assetIssueById,
                                       Long amount, String visible, Integer permissionId, String fromKey) {
        try {
            final String requestUrl = "http://" + httpNode + "/wallet/transferasset";
            JSONObject userBaseObj2 = new JSONObject();
            userBaseObj2.put("owner_address", ownerAddress);
            userBaseObj2.put("to_address", toAddress);
            userBaseObj2.put("asset_name", assetIssueById);
            userBaseObj2.put("amount", amount);
            userBaseObj2.put("visible", visible);
            userBaseObj2.put("Permission_id", permissionId);
            log.info("userBaseObj2:"+userBaseObj2.toString());
            response = TronlinkServerHttpClient.createPostConnect(requestUrl, null,userBaseObj2,null);
            transactionString = EntityUtils.toString(response.getEntity());
            log.info("transactionString:"+transactionString);
            JSONObject transactionObject = TronlinkServerHttpClient.parseString2JsonObject(transactionString);
            transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

            transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
            log.info("-----------sign information-----------------");
            log.info(transactionSignString);

            JSONObject jsonObject = TronlinkServerHttpClient.parseString2JsonObject(transactionSignString);
            jsonObject.remove("visible");
            //jsonObject.remove("txID");
            jsonObject.remove("raw_data_hex");
            transactionStr = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return transactionStr;
    }

    public static String gettransactionsign(String httpNode, String transactionString,
                                            String privateKey) {
        try {
            String requestUrl = "http://" + httpNode + "/wallet/gettransactionsign";
            JSONObject userBaseObj2 = new JSONObject();
            userBaseObj2.put("transaction", transactionString);
            userBaseObj2.put("privateKey", privateKey);
            response = TronlinkServerHttpClient.createPostConnect(requestUrl,null, userBaseObj2,null);
            transactionSignString = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return transactionSignString;
    }

    public static String str2hex(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }

}




