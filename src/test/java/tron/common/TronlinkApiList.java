package tron.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import com.google.api.Http;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.spongycastle.util.encoders.Hex;
import org.tron.api.GrpcAPI;
import org.tron.api.WalletGrpc;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.Hash;
import org.tron.common.parameter.CommonParameter;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Sha256Hash;
import org.tron.core.Wallet;
import org.tron.protos.Protocol;
import org.tron.protos.contract.BalanceContract;
import org.tron.protos.contract.SmartContractOuterClass;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;

import lombok.extern.slf4j.Slf4j;
import tron.common.utils.Configuration;
import tron.trondata.base.TrondataBase;
import tron.tronlink.base.GetSign;
import tron.tronlink.base.TronlinkBase;
import tron.common.utils.HttpDeleteWithBody;

@Slf4j
public class TronlinkApiList {

    static HttpClient httpClient;
    static HttpClient httpClient2;
    static HttpPost httppost;
    static HttpGet httpget;
    static HttpGet httpGet;
    static HttpPut httpput;
    static HttpDeleteWithBody httpdelete;

    public static String HttpTronDataNode = TrondataBase.trondataUrl;
    public static String HttpNode = TronlinkBase.tronlinkUrl;
    static HttpResponse response;
    static Integer connectionTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpConnectionTimeout");
    static Integer soTimeout = Configuration.getByPath("testng.conf")
            .getInt("defaultParameter.httpSoTimeout");
    static String transactionString;
    static String transactionSignString;
    static JSONObject responseContent;
    static JSONObject signResponseContent;
    static JSONObject transactionApprovedListContent;
    static Long requestTime = 0L;
    static byte ADD_PRE_FIX_BYTE_MAINNET = (byte) 0x41;
    //new sig needed parameter
    public static String defaultSys = "AndroidTest";
    public static String defaultLang = "1";
    public static String defaultPkg = "com.tronlinkpro.wallet";
    public static String defaultVersion = "1.0.0";

    static {
        PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
        pccm.setDefaultMaxPerRoute(80);
        pccm.setMaxTotal(100);

        httpClient = new DefaultHttpClient(pccm);

        // if httpClient occur "javax.net.ssl.SSLException: Certificate for
        // <list.tronlink.org> doesn't match any of the subject alternative names", then
        // use below code
        TrustStrategy acceptingTrustStrategy = new TrustSelfSignedStrategy();
        try {
            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            httpClient2 = HttpClients.custom().setSSLSocketFactory(scsf).setConnectionTimeToLive(10, TimeUnit.SECONDS)
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }




    public static HttpResponse classify() {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/classify";
            log.info(requestUrl);
            response = createGetConnectClient2(requestUrl, null);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse hot_recommend() {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp/hot_recommend";
            log.info(requestUrl);
            response = createGetConnect(requestUrl);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse multiTrxReword(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/wallet/multi/trx_record";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri);
        return response;
    }

    public static HttpResponse trc20Info(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/wallet/trc20_info";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri);
        return response;
    }

    public static HttpResponse getInterChainEvent(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/interchain-event";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri);
        return response;
    }

    public static HttpResponse apiTransferTrx(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/transfer/trx";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri);
        return response;
    }

    public static HttpResponse apiTransferToken10(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/transfer/token10";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri);
        return response;
    }

    public static HttpResponse apiTransferTrc20(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/transfer/trc20";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri);
        return response;
    }

    public static HttpResponse apiTransferTrc20Status(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/transfer/trc20_status";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (param != null) {
            for (String key : param.keySet()) {
                builder.addParameter(key, param.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri);
        return response;
    }

    public static HttpResponse votingV2Witness(Map<String, String> params) throws Exception {
        String requestUrl = HttpNode + "/api/voting/v2/witness";

        response = createGetConnect(requestUrl, params);
        return response;
    }

    public static boolean getAllWitnessFromTronscan() {
        // String requestUrl = "https://apilist.tronscan.org/api/vote/witness";
        String requestUrl = TronlinkBase.tronscanApiUrl + "/api/vote/witness";
        response = createGetConnect(requestUrl);
        // Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        if ((response.getStatusLine().getStatusCode() != 200)
                || responseContent == null
                || !(responseContent.containsKey("total"))
                || !(responseContent.getLongValue("total") > 0)
                || !(responseContent.containsKey("totalVotes"))
                || !(responseContent.getLongValue("totalVotes") > 0)
                || !(responseContent.containsKey("data"))
                || !(responseContent.getJSONArray("data").size() > 0)) {
            return false;
        }
        return true;
    }

    public static boolean getVoteSelfFromTronscan(Map<String, String> params) {
        String requestUrl = TronlinkBase.tronscanApiUrl + "/api/vote";
        response = createGetConnect(requestUrl, params);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        if ((response.getStatusLine().getStatusCode() != 200)
                || responseContent == null
                || !(responseContent.containsKey("total"))
                || !(responseContent.getLongValue("total") > 0)
                || !(responseContent.containsKey("totalVotes"))
                || !(responseContent.getLongValue("totalVotes") > 0)
                || !(responseContent.containsKey("data"))) {
            return false;
        }
        return true;
    }

    public static HttpResponse votingV2Search(Map<String, String> params) throws Exception {
        String requestUrl = HttpNode + "/api/voting/v2/search";

        response = createGetConnect(requestUrl, params);
        return response;
    }

    public static HttpResponse votingV2Self(Map<String, String> params) throws Exception {
        String requestUrl = HttpNode + "/api/voting/v2/self";

        response = createGetConnect(requestUrl, params);
        return response;
    }

    public static HttpResponse walletMarketBanner() {
        String requestUrl = HttpNode + "/api/wallet/market/banner";

        response = createGetConnect(requestUrl);
        return response;
    }

    public static HttpResponse walletMarketFavorite(Map<String, String> params) throws Exception {
        String requestUrl = HttpNode + "/api/wallet/market/favorite";

        response = createGetConnect(requestUrl, params);
        return response;
    }

    public static HttpResponse walletMarketTrxUsdt(Map<String, String> params) throws Exception {
        String requestUrl = HttpNode + "/api/wallet/market/trx_usdt";

        response = createGetConnect(requestUrl, params);
        return response;
    }

    public static HttpResponse head() {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/head";
            log.info(requestUrl);
            response = createGetConnectClient2(requestUrl, null);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse hot_search() {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp/hot_search";
            log.info(requestUrl);
            response = createGetConnectClient2(requestUrl, null);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse dapp_list(Map<String, String> params) {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp";
            log.info(requestUrl);
            response = createGetConnectClient2(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse dappBanner() {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/banner";
            response = createGetConnectClient2(requestUrl, null);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse dappAuthorizedProject() {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/authorized_project";
            response = createGetConnectClient2(requestUrl, null);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }



    public static HttpResponse swapExchanges(Map<String,String> params) {
        try {
            String requestUrl = HttpNode + "/api/swap/v1/exchanges";
            response = createGetConnectClient2(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse transfer1155(Map<String,String> params) {
        try {
            String curUri = "/api/transfer/v2/trc1155";
            response = CreateGetConnectWithSIgnature(curUri, params, null);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse dappPlug() {
        try {
            String requestUrl = HttpNode + "/dapphouseapp/plug";
            response = createGetConnectClient2(requestUrl, null);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse dappId(Map<String, String> params) {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp/id";
            log.info(requestUrl);
            response = createGetConnect(requestUrl);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse search(Map<String, String> params) {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp/search";
            response = createGetConnectClient2(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse history(Map<String, String> params) {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp/history";
            response = createGetConnectClient2(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse put_history(JSONObject requestBody) {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp/history";
            Map<String, String> header = new HashMap<>();
            header.put("Lang","1");
            header.put("Content-Type","application/json");
            response = createPutConnect(requestUrl, null, requestBody,header);
        } catch (Exception e) {
            e.printStackTrace();
            httpput.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse delete_history(JSONObject requestBody) {
        try {
            String requestUrl = HttpNode + "/api/dapp/v2/dapp/history";
            Map<String, String> header = new HashMap<>();
            header.put("Lang","1");
            header.put("Content-Type","application/json");
            response = createDeleteConnect(requestUrl, null,requestBody,header);
        } catch (Exception e) {
            e.printStackTrace();
            httpdelete.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse accountList(JSONArray body) {
        try {
            String requestUrl = HttpNode + "/api/wallet/account/list";
            response = createPostConnect(requestUrl, body);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse allasset(String address) {
        try {
            String requestUrl = HttpNode + "/api/wallet/class/allasset";
            JsonObject body = new JsonObject();
            body.addProperty("address", address);
            response = createPostConnect(requestUrl, body);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse assetlist(String address) {
        try {
            String requestUrl = HttpNode + "/api/wallet/assetlist";
            JsonObject body = new JsonObject();
            body.addProperty("address", address);
            response = createPostConnect(requestUrl, body);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse lotteryData() throws Exception {
        final String requesturl = HttpNode + "/api/wallet/lottery/default_data";
        URIBuilder builder = new URIBuilder(requesturl);
        URI uri = builder.build();
        response = createGetConnect(requesturl);
        return response;
    }

    public static HttpResponse hot_token(String address) {
        try {
            String requestUrl = HttpNode + "/api/wallet/hot_token";
            JsonObject body = new JsonObject();
            body.addProperty("address", address);
            response = createPostConnect(requestUrl, body);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse addasset(String json) {
        try {
            String requestUrl = HttpNode + "/api/wallet/addasset";
            response = createPostConnect(requestUrl, json);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse ieo() {
        final String requestUrl = HttpNode + "/api/wallet/ieo";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse getNodeInfo(JSONArray body) {
        final String requestUrl = HttpNode + "/api/wallet/node_info";
        response = createPostConnect(requestUrl, body);
        return response;
    }

    // nodeinfo升级的请求接口
    public static HttpResponse getNodeInfoV2(Map<String,String> params, JSONArray body, Map<String,String> headers) {
        final String requestUrl = HttpNode + "/api/wallet/node_info";
        log.info(requestUrl);
        response = createPostConnectWithHeaderV2(requestUrl,params, body, headers);
        return response;
    }



    public static HttpResponse getConfig() {
        final String requestUrl = HttpNode + "/api/wallet/get_config";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse dappToMainFee() {
        final String requestUrl = HttpNode + "/api/transfer/dappToMainFee";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse addAsset(JSONObject address) throws Exception {
        final String requestUrl = HttpNode + "/api/wallet/addasset";
        response = createConnect(requestUrl, address);
        return response;
    }

    public static HttpResponse getAirdropTransaction(Map<String, String> params) {
        final String requestUrl = HttpNode + "/api/wallet/airdrop_transaction";
        response = createGetConnect(requestUrl, params);
        return response;
    }

    public static HttpResponse getAllClassAsset(String node, JSONObject address) throws Exception {
        final String requestUrl = node + "/api/wallet/class/allasset";
        response = createConnect(requestUrl, address);
        return response;
    }

    public static List<String> getTrc10TokenIdList(JSONArray tokenArray) throws Exception {
        List<String> tokenIdList = new ArrayList<>();
        String id = "";
        for (int i = 0; i < tokenArray.size(); i++) {
            id = tokenArray.getJSONObject(i).getString("id");
            if (id.isEmpty()) {
                continue;
            }
            tokenIdList.add(id);
        }
        return tokenIdList;
    }

    public static List<String> getTrc20AddressList(JSONArray tokenArray) throws Exception {
        List<String> trc20ContractAddressList = new ArrayList<>();
        String contractAddress = "";
        for (int i = 0; i < tokenArray.size(); i++) {
            contractAddress = tokenArray.getJSONObject(i).getString("contractAddress");
            if (contractAddress.isEmpty()) {
                continue;
            }
            trc20ContractAddressList.add(contractAddress);
        }
        return trc20ContractAddressList;
    }

    public static HttpResponse getTransferTrx(Map<String, String> params) {
        try {
            String requestUrl = HttpTronDataNode + "/api/transfer/trx";
            response = createGetConnectNoHeader(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse getTransferToken10(Map<String, String> params) {
        try {
            String requestUrl = HttpTronDataNode + "/api/transfer/token10";
            response = createGetConnectNoHeader(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse getTransferTrc20(Map<String, String> params) {
        try {
            String requestUrl = HttpTronDataNode + "/api/transfer/trc20";
            response = createGetConnectNoHeader(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse getTrc20Holders(Map<String, String> params) {
        try {
            String requestUrl = HttpTronDataNode + "/api/trc20/holders";
            response = createGetConnectNoHeader(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse multiTransaction(JSONObject body) {
        try {
            String requestUrl = HttpNode + "/api/wallet/multi/transaction";
            response = createConnect(requestUrl, body);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static Boolean verificationResult(HttpResponse response) {
        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        }
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        return true;
    }

    /**
     * constructor.
     */
    public static HttpResponse getInviteCode(JSONObject param) throws Exception {
        final String requestUrl = HttpNode + "/api/wallet/invite/get_code";
        response = createConnect(requestUrl, param);
        return response;
    }

    public static HttpResponse failTransfer(JSONObject body) {
        final String requestUrl = HttpNode + "/api/wallet/fail_transfer";
        response = createConnect(requestUrl, body);
        return response;
    }

    public static HttpResponse createPutConnect(String url, Map<String, String> params,
                                                JSONObject requestBody, Map<String, String> headers) {
        try {
            // if ssl issue occur, use httpClient.custome().setxxx to set timeout.
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

            httpput = new HttpPut(url);
            httpput.setHeader("Content-type", "application/json; charset=utf-8");
            httpput.setHeader("Connection", "Keep-Alive");
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpput.addHeader(key, headers.get(key));
                }
            }
            Header[] allHeaders = httpput.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                log.info("" + allHeaders[i]);
            }

            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            log.info("Put: "+url);

            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpput.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // log.info("url: " + httppost.toString() + "\n params:"+requestBody);
            response = httpClient.execute(httpput);
        } catch (Exception e) {
            e.printStackTrace();
            httpput.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createDeleteConnect(String url, Map<String, String> params,
                                                JSONObject requestBody, Map<String, String> headers) {

        try {
            httpdelete = new HttpDeleteWithBody(url);
            // if ssl issue occur, use httpClient.custome().setxxx to set timeout.
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

            httpdelete.setHeader("Content-type", "application/json; charset=utf-8");
            httpdelete.setHeader("Connection", "Keep-Alive");
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpdelete.addHeader(key, headers.get(key));
                }
            }
            Header[] allHeaders = httpdelete.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                log.info("" + allHeaders[i]);
            }

            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            log.info("Delete: "+url);

            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpdelete.setEntity(entity);
            }

            response = httpClient.execute(httpdelete);
        } catch (Exception e) {
            e.printStackTrace();
            httpdelete.releaseConnection();
            return null;
        }
        return response;
    }


    public static HttpResponse createPostConnect(String url, String requestBody) {
        try {
            // if ssl issue occur, use httpClient.custome().setxxx to set timeout.
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

            httppost = new HttpPost(url);
            httppost.setHeader("Content-type", "application/json; charset=utf-8");
            httppost.setHeader("Connection", "Keep-Alive");
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody, Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // log.info("url: " + httppost.toString() + "\n params:"+requestBody);
            response = httpClient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse insertInviteCode(JSONObject param) throws Exception {
        final String requestUrl = HttpNode + "/api/wallet/invite/code";
        response = createConnect(requestUrl, param);
        return response;
    }

    public static HttpResponse nilexGetAssetlist(String address, Map<String, String> header) {
        try {
            String requestUrl = "https://niletest.tronlink.org/api/wallet/assetlist";
            JSONObject body = new JSONObject();
            body.put("address", address);
            header.put("Content-type", "application/json; charset=utf-8");
            header.put("Connection", "Close");
            response = createPostConnectWithHeader(requestUrl, null, body, header);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }


    public static Map<String, String> getNewSigHeader(String needSys, String testVersion, String testLang, String testPkg) {
        Map<String, String> header = new HashMap<>();
        if (needSys.equals("chrome-extension-test")){
            header.put("System","chrome-extension-test");
        } else if(needSys.equals("chrome-extension")){
            header.put("System","chrome-extension");
        } else if(needSys.equals("Chrome")){
            header.put("System","Chrome");
        } else if(needSys.equals("AndroidTest")){
            header.put("System","AndroidTest");
        } else if(needSys.equals("Android")){
            header.put("System","Android");
        } else if(needSys.equals("iOSTest")){
            header.put("System","iOSTest");
        } else if(needSys.equals("iOS")){
            header.put("System","iOS");
        }
        header.put("Lang",testLang);
        header.put("Version",testVersion);
        header.put("DeviceID","fca5a022-5526-45f5-a7e7");
        header.put("chain","MainChain");
        header.put("channel","official");

        header.put("ts", java.lang.String.valueOf(System.currentTimeMillis()));
        //header.put("ts","1609302220000");

        //header.put("packageName", "com.tronlinkpro.wallet");
        header.put("packageName", testPkg);
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Keep-Alive");

        header.put("deviceName","wqqtest");
        header.put("osVersion","10.0.0");

        return header;
    }
    public static Map<String, String> getNewSigParams(String needSys) {
        Map<String, String> params = new HashMap<>();

        if (needSys.equals("chrome-extension-test")){
            params.put("secretId","8JKSO2PM4M2K45EL");
        } else if(needSys.equals("chrome-extension")){
            params.put("secretId","AE68A487AA919CAE");
        } else if(needSys.equals("Chrome")){
            params.put("secretId","AE68A487AA919CAE");
        } else if(needSys.equals("AndroidTest")){
            params.put("secretId","SFSUIOJBFMLKSJIF");
        } else if(needSys.equals("Android")){
            params.put("secretId","A4ADE880F46CA8D4");
        } else if(needSys.equals("iOSTest")){
            params.put("secretId","JSKLJKFJDFDSFER3");
        } else if(needSys.equals("iOS")){
            params.put("secretId","ED151200DD0B3B52");
        }
        params.put("nonce","12345");
        return params;
    }

    public static String getNewSignature(String curUri,String httpMethod, String address, Map<String, String> params, Map<String, String> headers) {
        //Map<String, String> params = getNewSigParams(needSys);
        //Map<String, String> headers = getNewSigHeader(needSys, testVersion, testLang, testPkg);
        GetSign getSign = new GetSign();
        String signature = "";
        try {
            signature = URLEncoder.encode(getSign.getSignature(
                    headers.get("channel"),
                    headers.get("chain"),
                    headers.get("Lang"),
                    address,
                    params.get("nonce"),
                    params.get("secretId"),
                    headers.get("System"),
                    headers.get("DeviceID"),
                    headers.get("ts"),
                    headers.get("Version"),
                    curUri,
                    httpMethod));
        }catch (Exception e) {
            log.info("getNewSignature Error!");
            return null;
        }
        return signature;
    }

    public static HttpResponse CreateGetConnectWithSIgnature(String curURI, Map<String, String> caseParams,Map<String, String> caseHeader) {
        String requestUrl = HttpNode + curURI;
        Map<String, String> params = getNewSigParams(defaultSys);
        if(caseParams != null){
            params = AddMap(params, caseParams);
        }
        Map<String, String> headers = getNewSigHeader(defaultSys, defaultVersion, defaultLang, defaultPkg);
        if(caseHeader != null) {
            headers = AddMap(headers, caseHeader);
        }

        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"GET", caseParams.get("address"), params, headers);
        params.put("signature", cursig);

        response = createGetConnectWithHeader(requestUrl, params, null, headers);
        return response;
    }
    
    public static HttpResponse createPostConnectWithSignature(String curURI,Map<String, String> caseParams, Map<String, String> caseHeader,JSONObject object ) {
        Map<String, String> params = getNewSigParams(defaultSys);
        if(caseParams != null){
            params = AddMap(params, caseParams);
        }

        Map<String, String> headers = getNewSigHeader(defaultSys, defaultVersion, defaultLang, defaultPkg);
        if(caseHeader != null) {
            headers = AddMap(headers, caseHeader);
        }
        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"POST", caseParams.get("address"), params, headers);
        params.put("signature", cursig);

        String requestUrl = HttpNode + curURI;
        response = createPostConnectWithHeader(requestUrl, params, object, headers);
        return response;
    }

    public static HttpResponse createPostConnectWithSignature(String curURI,Map<String, String> caseParams, Map<String, String> caseHeader,JSONArray object ) {
        Map<String, String> params = getNewSigParams(defaultSys);
        if(caseParams != null){
            params = AddMap(params, caseParams);
        }

        Map<String, String> headers = getNewSigHeader(defaultSys, defaultVersion, defaultLang, defaultPkg);
        if(caseHeader != null) {
            headers = AddMap(headers, caseHeader);
        }
        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"POST", caseParams.get("address"), params, headers);
        params.put("signature", cursig);

        String requestUrl = HttpNode + curURI;
        response = createPostConnectWithHeader(requestUrl, params, object, headers);
        return response;
    }


    public static HttpResponse V2UnfollowCollections(Map<String, String> params) {
        try {
            String curUri = "/api/wallet/nft/unfollowCollections";
            response = CreateGetConnectWithSIgnature(curUri, params,null);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse V2UnfollowAssetList(Map<String, String> params) {
        try {

            String curUri = "/api/wallet/v2/unfollowAssetList";
            response = CreateGetConnectWithSIgnature(curUri, params,null);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse V2AllAssetList(Map<String, String> caseParams) {

        String curURI = "/api/wallet/v2/allAssetList";
        Map<String, String> params = getNewSigParams(defaultSys);
        params = AddMap(params, caseParams);

        Map<String, String> headers = getNewSigHeader(defaultSys, defaultVersion, defaultLang, defaultPkg);

        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"GET", caseParams.get("address"), params, headers);
        params.put("signature", cursig);

        String requestUrl = HttpNode + curURI;
        response = createGetConnectWithHeader(requestUrl, params, null, headers);
        return response;

    }

    public static HttpResponse v2AssetList(Map<String, String> caseParams)  {

        String curURI = "/api/wallet/v2/assetList";
        response = CreateGetConnectWithSIgnature(curURI, caseParams,null);
        return response;

    }

    public static HttpResponse v2AssetList(Map<String, String> params, JSONObject body) {
        final String curURI = "/api/wallet/v2/assetList";
        response = createPostConnectWithSignature(curURI, params, null, body);
        return response;
    }

    public static HttpResponse v2GetAssetList(Map<String, String> params, JSONObject body,
            Map<String, String> headers) {
        // final String requestUrl = HttpNode + "/api/wallet/v2/assetList";
        final String requestUrl = "https://testpre.tronlink.org" + "/api/wallet/v2/assetList";

        log.info("requestUrl : " + requestUrl);
        response = createGetConnectWithHeader(requestUrl, params, body, headers);
        return response;
    }

    public static HttpResponse v2GetAllCollection(Map<String, String> caseParams) {

        String curURI = "/api/wallet/nft/getAllCollection";
        Map<String, String> params = getNewSigParams(defaultSys);
        params = AddMap(params, caseParams);

        Map<String, String> headers = getNewSigHeader(defaultSys, defaultVersion, defaultLang, defaultPkg);

        //String curUri,String httpMethod, String address, String needSys, String testVersion, String testLang, String testPkg
        String cursig = getNewSignature(curURI,"GET", caseParams.get("address"), params, headers);
        params.put("signature", cursig);

        String requestUrl = HttpNode + curURI;
        response = createGetConnectWithHeader(requestUrl, params, null, headers);
        return response;

    }

    // 根据token类型查询首页信息
    public static HttpResponse v2GetAllCollectionByType(String url, Map<String, String> params) {
        final String requestUrl = HttpNode +  url ;
        response = CreateGetConnectWithSIgnature(url, params,null);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        return response;
    }

    public static HttpResponse v2AllCollections(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/allCollections";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }

    public static HttpResponse v2GetCollectionList(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/getCollectionList";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }

    public static HttpResponse v2GetCollectionInfo(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/getCollectionInfo";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }

    public static HttpResponse v2GetCollectionInfos(Map<String, String> params, JSONObject body) {
        final String curURI = "/api/wallet/nft/getCollectionInfos";
        response = createPostConnectWithSignature(curURI, params, null, body);
        return response;
    }

    public static HttpResponse getDelegatedResource(Map<String, String> params) {
        final String curURI = "/api/wallet/v2/getDelegatedResource";
        response = CreateGetConnectWithSIgnature(curURI, params, null);
        return response;
    }

    public static HttpResponse v2NewAssetList(Map<String, String> params) {
        final String curURI = "/api/wallet/v2/newAssetList";
        response = CreateGetConnectWithSIgnature(curURI, params, null);
        return response;
    }

    public static HttpResponse v2SearchAsset(Map<String, String> params) {
        final String curURI = "/api/wallet/v2/search";
        response = CreateGetConnectWithSIgnature(curURI, params, null);
        return response;
    }

    public static HttpResponse v1GetNoticeRemind() {
        final String requestUrl = HttpNode + "/api/v1/wallet/getNoticeRemind";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse v1GetDappHistory(JSONObject params) {
        final String requestUrl = HttpNode + "/api/activity/add";
        response = createPostConnectWithHeader(requestUrl, null, params, null);
        return response;
    }

    public static HttpResponse v1GetAnnouncement() {
        final String requestUrl = HttpNode + "/api/activity/announcement/reveal_v2";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse v1GetNodes(Map<String, String> params) {
        final String requestUrl = HttpNode + "/api/v1/wallet/nodes";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse v2GetBlacklist(Map<String, String> params) {
        final String requestUrl = HttpNode + "/api/activity/website/blacklist";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse officialToken(Map<String, String> params) {
        final String requestUrl = HttpNode + "/api/wallet/official_token";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse v1PlayScreenInfo(Map<String, String> params) {
        final String requestUrl = HttpNode + "/api/activity/play_screen/info";
        response = createGetConnect(requestUrl, null);
        return response;
    }

    public static HttpResponse v1PlayScreenDeal(String playId, HashMap<String, String> header) {
        final String requestUrl = HttpNode + "/api/activity/play_screen/deal";
        JSONObject body = new JSONObject();
        body.put("playId", playId);
        response = createPostConnectWithHeader(requestUrl, null, body, header);
        return response;
    }

    public static HttpResponse v1GetStartup(Map<String, String> params, Map<String, String> headerMap) {
        final String requestUrl = HttpNode + "/api/v1/wallet/startup";
        Map<String, String> header = getV2Header();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        for (String key : headerMap.keySet()) {
            header.put(key, headerMap.get(key));
        }
        response = createGetConnectWithHeader(requestUrl, params, null, header);
        return response;
    }

    public static HttpResponse v1UpdateUserCreateBNum(String updataNumber, String userhash, Map<String, String> headerMap) {
        final String requestUrl =  HttpNode + "/api/v1/wallet/updateUserCreateBNum";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        for (String key : headerMap.keySet()) {
            header.put(key, headerMap.get(key));
        }
        response = createPostConnectWithHeader(requestUrl,null, (JSONObject) JSONObject.parse(
                        "{\"userhash\":\"" + userhash + "\",\"number\":\"" + updataNumber + "\",\"access\":\"33572\"}"), header);
        return response;
    }

    public static HttpResponse v1UserCreateBlockNum(Map<String, String> params, Map<String, String> headerMap) {
        final String requestUrl = HttpNode + "/api/v1/wallet/userCreateBlockNum";
        Map<String, String> header = getV2Header();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        for (String key : headerMap.keySet()) {
            header.put(key, headerMap.get(key));
        }
        response = createGetConnectWithHeader(requestUrl, params, null, header);
        return response;
    }

    public static HttpResponse v2accountList(Map<String, String> params, JSONArray object) {
        /*try {
            String requestUrl = HttpNode + "/api/wallet/v2/account/list";
            Map<String, String> header = getV2Header();
            header.put("Content-type", "application/json; charset=utf-8");
            header.put("Connection", "Close");
            response = createPostConnectWithHeader(requestUrl, params, object, header);
            // response = createPostConnect(requestUrl,body);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }*/
        String curURI = "/api/wallet/v2/account/list";
        response = createPostConnectWithSignature(curURI, params, null, object );
        return response;
    }

    public static HttpResponse v2Asset(Map<String, String> params) {
        final String curUri = "/api/wallet/v2/asset";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }

    public static HttpResponse v2Auth(Map<String, String> params) {
        final String curUri = "/api/wallet/v2/auth";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }

    public static HttpResponse v2NftSearch(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/search";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }

    public static Map<String, String> AddMap(Map<String, String> map1, Map<String, String> map2){
        Set myKeys = map2.keySet();
        for (Object o : myKeys){
            map1.put((String) o, map2.get(o));
        }
        return map1;
    }

    public static HttpResponse v2AddAsset(Map<String, String> caseParams, JSONObject object) {
        /*String requestUrl = HttpNode + "/api/wallet/v2/addAsset";
        Map<String, String> header = getV2Header();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        response = createPostConnectWithHeader(requestUrl, params, object, header);
        return response;*/
        String curURI = "/api/wallet/v2/addAsset";
        Map<String, String> caseHeader = new HashMap<>();
        caseHeader.put("Content-type", "application/json; charset=utf-8");
        caseHeader.put("Connection", "Close");
        response = createPostConnectWithSignature(curURI, caseParams, caseHeader, object );
        return response;
    }

    public static HttpResponse v2DelAsset(Map<String, String> caseparams, JSONObject object) {
        String curURI = "/api/wallet/v2/delAsset";
        Map<String, String> caseHeader= new HashMap<>();
        caseHeader.put("Content-type", "application/json; charset=utf-8");
        caseHeader.put("Connection", "Close");
        response = createPostConnectWithSignature(curURI, caseparams, caseHeader, object);
        return response;
    }

    public static HttpResponse v1UpgradeV2(Map<String, String> params) {
        String requestUrl = HttpNode + "/api/v1/wallet/v2/upgrade";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        header = AddMap(header, params);

        response = createGetConnectWithHeader(requestUrl, null, null, header);
        return response;
    }

    public static HttpResponse v1Upgrade(Map<String, String> params) {
        String requestUrl = HttpNode + "/api/v1/wallet/upgrade";
        Map<String, String> header = getV2Header();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        header = AddMap(header, params);
        response = createGetConnectWithHeader(requestUrl, null, null, header);
        return response;
    }

    public static JSONObject getTronscanTrc20Price(String address) {
        if (address.startsWith("T")) {
            String reqestUrl = "https://apilist.tronscan.org/api/token_trc20?contract=" + address + "&showAll=1";
            HttpResponse transcanRsp = TronlinkApiList.createGetConnect(reqestUrl);
            JSONObject transcanRspContent = TronlinkApiList.parseJsonObResponseContent(transcanRsp);
            JSONObject priceJson = (JSONObject) JSONPath.eval(transcanRspContent,
                    String.join("", "$..trc20_tokens[contract_address='", address, "'].market_info[0]"));
            return priceJson;
        } else if (address.startsWith("100")) {
            String reqestUrl = "https://apilist.tronscan.org/api/token?id=" + address + "&showAll=1";
            HttpResponse transcanRsp = TronlinkApiList.createGetConnect(reqestUrl);
            JSONObject transcanRspContent = TronlinkApiList.parseJsonObResponseContent(transcanRsp);
            System.out.println(String.join("", "$..data[id=", address, "].market_info[0]"));
            JSONObject priceJson = (JSONObject) JSONPath.eval(transcanRspContent,
                    String.join("", "$..data[id=", address, "].market_info[0]"));
            return priceJson;
        }
        return null;
    }

    // Compare if string format number gap in tolerance.
    public static boolean CompareGapInGivenTolerance(String expectedstr, String actualstr, String toleranceRate) {
        BigDecimal expected = new BigDecimal(expectedstr);
        BigDecimal actual = new BigDecimal(actualstr);
        BigDecimal toleranceRate_bd = new BigDecimal(toleranceRate);
        BigDecimal tolerance_bd = expected.multiply(toleranceRate_bd);
        BigDecimal absgap = actual.subtract(expected).abs();
        log.info("expected:" + expectedstr + ", actual:" + actualstr + ", GAP:" + absgap + ", tolerance:"
                + tolerance_bd.toString());
        Boolean InTolerance = (absgap.compareTo(tolerance_bd) == -1 || absgap.compareTo(tolerance_bd) == 0);
        return InTolerance;
    }

    public static Map<String, String> getV2Header() {
        Map<String, String> header = new HashMap<>();
        header.put("Lang", "1");
        header.put("Version", "v1.0.0");
        header.put("DeviceID", "1:1:1:1");
        header.put("chain", "MainChain");
        header.put("channel", "official");

        header.put("ts", java.lang.String.valueOf(System.currentTimeMillis()));
        header.put("packageName", "com.tronlinkpro.wallet");
        header.put("System", "AndroidTest");
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Keep-Alive");
        return header;
    }
    

    /**
     * constructor.
     */
    public static HttpResponse createPostConnect(String url, JsonObject requestBody) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

            httppost = new HttpPost(url);
            httppost.setHeader("Content-type", "application/json; charset=utf-8");
            httppost.setHeader("Connection", "Keep-Alive");
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            // log.info("url: "+httppost.toString() + "\n params: "+requestBody.toString());
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            response = httpClient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    /**
     * constructor.
     */

    public static HttpResponse createPostConnectWithHeader(String url, Map<String, String> params,
            JSONArray requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httppost = new HttpPost(url);
            if (header != null) {
                for (String key : header.keySet()) {
                    httppost.setHeader(key, header.get(key));
                    log.info(key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toJSONString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // log.info("url: "+httppost.toString()+"\nparams: "+requestBody.toString());
            response = httpClient.execute(httppost);

        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;

    }

    public static HttpResponse createPostConnectWithHeader(String url, Map<String, String> params,
            JSONObject requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httppost = new HttpPost(url);
            // httppost.setHeader("Content-type", "application/json; charset=utf-8");
            // httppost.setHeader("Connection", "Close");
            if (header != null) {
                for (String key : header.keySet()) {
                    httppost.setHeader(key, header.get(key));
                    log.info(key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // if (requestBody == null){
            // log.info("no request body info");
            // requestBody = new JSONObject();
            // }
            // log.info("url: "+httppost.toString()+"\nparams: "+params.toString() + " \n
            // requestbody : "+requestBody.toString());
            //
            printHttpInfo(httppost, params, requestBody);
            response = httpClient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    // 和其他方法的不同是requestBody变成了数组类型
    public static HttpResponse createPostConnectWithHeaderV2(String url, Map<String, String> params,
                                                           JSONArray requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httppost = new HttpPost(url);
            // httppost.setHeader("Content-type", "application/json; charset=utf-8");
            // httppost.setHeader("Connection", "Close");
            if (header != null) {
                for (String key : header.keySet()) {
                    httppost.setHeader(key, header.get(key));
                    log.info(key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
//            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
            // if (requestBody == null){
            // log.info("no request body info");
            // requestBody = new JSONObject();
            // }
            // log.info("url: "+httppost.toString()+"\nparams: "+params.toString() + " \n
            // requestbody : "+requestBody.toString());
            //
//            printHttpInfo(httppost, params, requestBody);
            response = httpClient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static void printHttpInfo(HttpPost httppost, Map<String, String> params, JSONObject requestbody) {
        log.info("begin print http info");
        if (httppost != null) {
            log.info("httppost = " + httppost);
        }
        if (params != null) {
            log.info("params = " + params);
        }
        if (requestbody != null) {
            log.info("requestbody = " + requestbody);
        }
        log.info("end print http info");
    }

    /**
     * constructor.
     */
    public static HttpResponse createGetConnectWithHeader(String url, Map<String, String> params,
            JSONObject requestBody, Map<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httpget = new HttpGet(url);
            if (header != null) {
                for (String key : header.keySet()) {
                    httpget.setHeader(key, header.get(key));
                    log.info("Add key to header: " + key + ": " + header.get(key));
                }
            }
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                // SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
                // log.info("url: "+httpget.toString()+"\nparams: "+requestBody.toString());
            }

            log.info("" + httpget);

            response = httpClient.execute(httpget);
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    /**
     * constructor.
     */
    public static HttpResponse createPostConnect(String url, JSONArray requestBody) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);

            httppost = new HttpPost(url);
            httppost.setHeader("Content-type", "application/json; charset=utf-8");
            httppost.setHeader("Connection", "Close");
            httppost.addHeader("Lang", "1");
            httppost.addHeader("Version", "3.7.0");
            httppost.addHeader("DeviceID", "1111111111");
            httppost.addHeader("chain", "MainChain");
            httppost.addHeader("packageName", "com.tronlinkpro.wallet");
            httppost.addHeader("System", "Android");
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toJSONString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            response = httpClient.execute(httppost);
            printHttpInfoV2(httppost, httppost.getAllHeaders(), httppost.getParams());
            // log.info("url: "+httppost.toString()+"\nparams: "+requestBody.toString());
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    public static void printHttpInfoV2(HttpPost httppost, org.apache.http.Header[] headers,  org.apache.http.params.HttpParams params){
        if (httppost != null) {
            log.info(httppost.toString());

        }
        if (headers == null) {
            for (Header h:
                 headers) {
                log.info(h.getName() + " " + h.getValue());
            }
        }
        if (httppost.getEntity() != null) {
            log.info(JSONObject.toJSONString(httppost.getEntity()));
        }

//        if (params == null) {
//            log.info(params.);
//        }
    }

    /**
     * constructor.
     */
    public static HttpResponse createGetConnect(String url) {
        return createGetConnect(url, null);
    }

    public static HttpResponse createGetConnectClient2(String url, Map<String, String> params) {
        try {
            // httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,connectionTimeout);
            // httpClient2.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
            // soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httpget = new HttpGet(url);
            httpget.addHeader("Lang", "1");
            httpget.addHeader("Version", "3.7.0");
            httpget.addHeader("DeviceID", "1111111111");
            httpget.addHeader("chain", "MainChain");
            httpget.addHeader("packageName", "com.tronlinkpro.wallet");
            httpget.addHeader("System", "Android");
            httpget.setHeader("Content-type", "application/json; charset=utf-8");
            httpget.setHeader("Connection", "Keep-Alive");
            Header[] allHeaders = httpget.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                log.info("" + allHeaders[i]);
            }
            Instant startTime = Instant.now();
            response = httpClient.execute(httpget);
            Instant endTime = Instant.now();
            requestTime = Duration.between(startTime, endTime).toMillis();
            log.info(url + " 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse createGetConnect(URI uri) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            log.info("url: " + uri.toString());
            httpGet = new HttpGet(uri);
            httpGet.setHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Connection", "Close");
            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
            httpGet.releaseConnection();
            return null;
        }
        return response;
    }

    /**
     * constructor.
     */
    public static HttpResponse createGetConnectNoHeader(String url, Map<String, String> params) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httpget = new HttpGet(url);
            httpget.setHeader("Connection", "Keep-Alive");
            log.info("---url: " + url);
            Instant startTime = Instant.now();
            response = httpClient.execute(httpget);
            Instant endTime = Instant.now();
            requestTime = Duration.between(startTime, endTime).toMillis();
            log.info(" 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    /**
     * constructor.
     */
    public static HttpResponse createGetConnect(String url, Map<String, String> params) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            if (params != null) {
                StringBuffer stringBuffer = new StringBuffer(url);
                stringBuffer.append("?");
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
                }
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                url = stringBuffer.toString();
            }
            httpget = new HttpGet(url);
            httpget.addHeader("Lang", "1");
            httpget.addHeader("Version", "3.7.0");
            httpget.addHeader("DeviceID", "1111111111");
            httpget.addHeader("chain", "MainChain");
            httpget.addHeader("packageName", "com.tronlinkpro.wallet");
            httpget.addHeader("System", "Android");
            httpget.setHeader("Content-type", "application/json; charset=utf-8");
            httpget.setHeader("Connection", "Keep-Alive");
            Header[] allHeaders = httpget.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                log.info("" + allHeaders[i]);
            }
            Instant startTime = Instant.now();
            response = httpClient.execute(httpget);
            Instant endTime = Instant.now();
            requestTime = Duration.between(startTime, endTime).toMillis();
            log.info(url + " 请求总耗时：" + Duration.between(startTime, endTime).toMillis() + " 毫秒");
        } catch (Exception e) {
            e.printStackTrace();
            httpget.releaseConnection();
            return null;
        }
        return response;
    }

    /**
     * constructor.
     */
    public static JSONArray parseArrayResponseContent(HttpResponse response) {
        try {
            String result = EntityUtils.toString(response.getEntity());
            log.info(result);
            StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
            response.setEntity(entity);
            JSONArray obj = JSONArray.parseArray(result);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * constructor.
     */
    public static JSONArray parseResponseContent(HttpResponse response) {
        try {
            String result = EntityUtils.toString(response.getEntity());
            // result = result.substring(0, result.lastIndexOf("}"));
            // result = result + ",\"requestTime\":" + requestTime + "}";
            StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
            response.setEntity(entity);
            JSONArray obj = JSONArray.parseArray(result);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String parseResponse2String(HttpResponse response) {
        try {
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject parseJsonObResponseContent(HttpResponse response) {
        try {
            String result = EntityUtils.toString(response.getEntity());
            log.info("======");
            log.info(result);
            log.info("======");
            // result = result.substring(0, result.lastIndexOf("}"));
            // result = result + ",\"requestTime\":" + requestTime + "}";
            StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
            response.setEntity(entity);
            JSONObject obj = JSONObject.parseObject(result);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * constructor.
     */
    public static void printJsonContent(JSONObject responseContent) {
        log.info("----------------------------Print JSON Start---------------------------");
        for (String str : responseContent.keySet()) {
            log.info(str + ":" + responseContent.get(str));
        }
        log.info("JSON content size are: " + responseContent.size());
        log.info("----------------------------Print JSON End-----------------------------");
    }

    /**
     * constructor.
     */
    public static void printJsonArrayContent(JSONArray responseContent) {
        log.info("----------------------------Print JSON Start---------------------------");
        for (int i = 0; i < responseContent.size(); i++) {
            for (String str : responseContent.getJSONObject(i).keySet()) {
                log.info(str + ":" + responseContent.get(i).toString());
            }
        }
        log.info("JSON content size are: " + responseContent.size());
        log.info("----------------------------Print JSON End-----------------------------");
    }

    public static HttpResponse createConnect(String url, JSONObject requestBody) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            httppost = new HttpPost(url);
            httppost.setHeader("Content-type", "application/json; charset=utf-8");
            httppost.setHeader("Connection", "keep-alive");
            httppost.addHeader("Lang", "1");
            httppost.addHeader("Version", "3.7.0");
            httppost.addHeader("DeviceID", "1111111111");
            httppost.addHeader("chain", "MainChain");
            httppost.addHeader("packageName", "com.tronlinkpro.wallet");
            httppost.addHeader("System", "Android");
            if (requestBody != null) {
                StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httppost.setEntity(entity);
            }
            log.info("url:" + httppost.toString());
            if (requestBody != null) {
                log.info("params: " + requestBody.toJSONString());
            }
            Header[] allHeaders = httppost.getAllHeaders();
            for (int i = 0; i < allHeaders.length; i++) {
                log.info("" + allHeaders[i]);
            }
            response = httpClient.execute(httppost);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }

    /**
     * constructor.
     */
    public static void disConnect() {
        httppost.releaseConnection();
    }

    /**
     * constructor.
     */
    public static void disGetConnect() {
        httpget.releaseConnection();
    }

    public static HttpResponse createGetConnect(URI uri, HashMap<String, String> header) {
        try {
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
                    connectionTimeout);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
            log.info("url: " + uri.toString());
            httpGet = new HttpGet(uri);
            httpGet.setHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Connection", "Close");
            for (HashMap.Entry<String, String> entry : header.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
                log.info(entry.getKey() + ":" + entry.getValue());
            }
            response = httpClient.execute(httpGet);
        } catch (Exception e) {
            e.printStackTrace();
            httpGet.releaseConnection();
            return null;
        }
        return response;
    }

    public static HttpResponse upgrade(HashMap<String, String> header) throws Exception {
        final String requestUrl = HttpNode + "/api/v1/wallet/upgrade";
        URIBuilder builder = new URIBuilder(requestUrl);
        if (header != null) {
            for (String key : header.keySet()) {
                builder.addParameter(key, header.get(key));
            }
        }
        URI uri = builder.build();
        response = createGetConnect(uri, header);
        Assert.assertTrue(TronlinkApiList.verificationResult(response));
        return response;
    }

    /**
     * constructor.
     */
    public static Protocol.Transaction sendcoin(byte[] to, long amount, byte[] owner,
            WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
        BalanceContract.TransferContract.Builder builder = BalanceContract.TransferContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(to);
        ByteString bsOwner = ByteString.copyFrom(owner);
        builder.setToAddress(bsTo);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(amount);

        BalanceContract.TransferContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.createTransaction(contract);
        if (transaction == null || transaction.getRawData().getContractCount() == 0) {
            log.info("transaction ==null");
            return null;
        }
        return transaction;

    }

    /**
     * constructor.
     */

    public static byte[] getFinalAddress(String priKey) {
        ECKey temKey = null;
        try {
            BigInteger priK = new BigInteger(priKey, 16);
            temKey = ECKey.fromPrivate(priK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return temKey.getAddress();
    }

    public static Protocol.Transaction addTransactionSignWithPermissionId(Protocol.Transaction transaction,
            String priKey, int permissionId, WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
        ECKey temKey = null;
        try {
            BigInteger priK = new BigInteger(priKey, 16);
            temKey = ECKey.fromPrivate(priK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // transaction = setPermissionId(transaction, permissionId);
        Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder();
        Protocol.Transaction.Contract.Builder contract = raw.getContract(0).toBuilder()
                .setPermissionId(permissionId);
        raw.clearContract();
        raw.addContract(contract);
        transaction = transaction.toBuilder().setRawData(raw).build();

        Protocol.Transaction.Builder transactionBuilderSigned = transaction.toBuilder();
        byte[] hash = Sha256Hash.hash(CommonParameter.getInstance()
                .isECKeyCryptoEngine(), transaction.getRawData().toByteArray());
        ECKey ecKey = temKey;
        ECKey.ECDSASignature signature = ecKey.sign(hash);
        ByteString bsSign = ByteString.copyFrom(signature.toByteArray());
        transactionBuilderSigned.addSignature(bsSign);
        transaction = transactionBuilderSigned.build();
        return transaction;
    }

    public static Protocol.Transaction triggerContract(byte[] contractAddress, String method,
            String argsStr, Boolean isHex, long callValue, long feeLimit, String tokenId, long tokenValue,
            byte[] ownerAddress, WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
        if (argsStr.equalsIgnoreCase("#")) {
            log.info("argsstr is #");
            argsStr = "";
        }

        byte[] owner = ownerAddress;
        byte[] input = Hex.decode(parseMethod(method, argsStr, isHex));

        SmartContractOuterClass.TriggerSmartContract.Builder builder = SmartContractOuterClass.TriggerSmartContract
                .newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(owner));
        builder.setContractAddress(ByteString.copyFrom(contractAddress));
        builder.setData(ByteString.copyFrom(input));
        builder.setCallValue(callValue);
        builder.setTokenId(Long.parseLong(tokenId));
        builder.setCallTokenValue(tokenValue);
        SmartContractOuterClass.TriggerSmartContract triggerContract = builder.build();

        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.triggerContract(triggerContract);
        if (transactionExtention == null || !transactionExtention.getResult().getResult()) {
            System.out.println("RPC create call trx failed!");
            System.out.println("Code = " + transactionExtention.getResult().getCode());
            System.out
                    .println("Message = " + transactionExtention.getResult().getMessage().toStringUtf8());
            return null;
        }
        Protocol.Transaction transaction = transactionExtention.getTransaction();
        if (transaction.getRetCount() != 0 && transactionExtention.getConstantResult(0) != null
                && transactionExtention.getResult() != null) {
            byte[] result = transactionExtention.getConstantResult(0).toByteArray();
            System.out.println("message:" + transaction.getRet(0).getRet());
            System.out.println(
                    ":" + ByteArray.toStr(transactionExtention.getResult().getMessage().toByteArray()));
            System.out.println("Result:" + Hex.toHexString(result));
            return null;
        }

        final GrpcAPI.TransactionExtention.Builder texBuilder = GrpcAPI.TransactionExtention.newBuilder();
        Protocol.Transaction.Builder transBuilder = Protocol.Transaction.newBuilder();
        Protocol.Transaction.raw.Builder rawBuilder = transactionExtention.getTransaction().getRawData()
                .toBuilder();
        rawBuilder.setFeeLimit(feeLimit);
        transBuilder.setRawData(rawBuilder);
        for (int i = 0; i < transactionExtention.getTransaction().getSignatureCount(); i++) {
            ByteString s = transactionExtention.getTransaction().getSignature(i);
            transBuilder.setSignature(i, s);
        }
        for (int i = 0; i < transactionExtention.getTransaction().getRetCount(); i++) {
            Protocol.Transaction.Result r = transactionExtention.getTransaction().getRet(i);
            transBuilder.setRet(i, r);
        }
        texBuilder.setTransaction(transBuilder);
        texBuilder.setResult(transactionExtention.getResult());
        texBuilder.setTxid(transactionExtention.getTxid());
        transactionExtention = texBuilder.build();
        if (transactionExtention == null) {
            return null;
        }
        GrpcAPI.Return ret = transactionExtention.getResult();
        if (!ret.getResult()) {
            System.out.println("Code = " + ret.getCode());
            System.out.println("Message = " + ret.getMessage().toStringUtf8());
            return null;
        }
        transaction = transactionExtention.getTransaction();
        return transaction;
    }

    // isHex must be true
    public static String parseMethod(String methodSign, String input, boolean isHex) {
        return parseSelector(methodSign) + input;
    }

    public static String parseSelector(String methodSign) {
        byte[] selector = new byte[4];
        System.arraycopy(Hash.sha3(methodSign.getBytes()), 0, selector, 0, 4);
        return Hex.toHexString(selector);
    }

    public static boolean urlCanVisited(String urlString, int timeOutMillSeconds) throws Exception {
        long lo = System.currentTimeMillis();
        URL url;
        URLConnection co;
        try {
            url = new URL(urlString);
            co = url.openConnection();
            co.setConnectTimeout(timeOutMillSeconds);
            co.connect();

            System.out.println("连接可用");

        } catch (Exception e1) {
            System.out.println("连接打不开!");
            url = null;
            return false;
        }

        System.out.println(System.currentTimeMillis() - lo);
        System.out.println(co.getContent().toString());
        return true;
    }

    public static HttpResponse v2TokenQuery(Map<String, String> params, JSONObject object) {
        String curUri = "/api/wallet/v2/token/query";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        response = createPostConnectWithSignature(curUri, params, header, object);
        return response;
    }

    public static HttpResponse v2TokenAdd(Map<String, String> params, JSONObject object) {
        String requestUrl = HttpNode + "/api/wallet/v2/token/add";
        Map<String, String> header = getV2Header();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        response = createPostConnectWithHeader(requestUrl, params, object, header);
        return response;
    }

    public static HttpResponse v2TokenSync(Map<String, String> params, JSONObject object) {
        String curUri = "/api/wallet/v2/token/sync";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        response = createPostConnectWithSignature(curUri, params, header, object);
        return response;
    }

    public static List<String> ReadFile(String fileNamePath) throws IOException {
        File tokenFile = new File(fileNamePath);

        List<String> lines = new ArrayList<>();
        if (tokenFile.isFile() && tokenFile.exists()) {
            InputStreamReader Reader = new InputStreamReader(new FileInputStream(tokenFile), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(Reader);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                log.info("data:" + lineTxt);
                lines.add(lineTxt);
            }
            Reader.close();
        } else {
            log.info("ReadFile: can't find file: " + fileNamePath);
        }
        return lines;
    }

    public static HttpResponse v2RiskTokens(Map<String,String> caseParams,JSONObject body) {

        String curUri = "/api/wallet/v2/risktokens";
        response = createPostConnectWithSignature(curUri, caseParams, null, body);
        return response;
    }

    public static HttpResponse v2GetRisk(Map<String, String> caseParams) {
        String curUri = "/api/wallet/v2/risk";
        response = CreateGetConnectWithSIgnature(curUri, caseParams, null);
        return response;
    }



    public static JSONObject getprice(String symbol, String convert) {
        Map<String, String> params = new HashMap<>();
        params.clear();
        params.put("symbol", symbol);
        params.put("convert", convert);
        response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        return responseContent;
    }

    public static HttpResponse v2CheckWhite(Map<String, String> params) throws Exception {
        String curUri =  "/api/wallet/v2/white";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }
    public static HttpResponse v1Balance(Map<String, String> params) {
        try {
            String requestUrl = HttpNode + "/api/wallet/balance";
            response = createGetConnectNoHeader(requestUrl, params);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }
    public static HttpResponse TransferV2Trc721(Map<String, String> params) {
        final String curUri = "/api/transfer/v2/trc721";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }
    public static HttpResponse TransferTrc721(Map<String, String> params) {
        final String curUri = "/api/transfer/trc721";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }
    public static HttpResponse sdk_verify(String json) {
        try {
            String requestUrl = HttpNode + "/api/wallet/sdk_verify";
            response = createPostConnect(requestUrl, json);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }
    public static HttpResponse v2GetAllCollection_1155(Map<String, String> params) {
        final String curUri = "/api/wallet/trc1155/getAllCollection";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }
    
    public static HttpResponse v2AllCollections_1155(Map<String, String> params) {
        final String curUri = "/api/wallet/trc1155/allCollections";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }
    
    public static HttpResponse v2GetCollectionList_1155(Map<String, String> params) {
        final String curUri = "/api/wallet/trc1155/getCollectionList";
        response = CreateGetConnectWithSIgnature(curUri, params, null);
        return response;
    }
    public static HttpResponse v2GetCollectionInfos_1155(Map<String, String> params, JSONObject body) {
        final String curUri = "/api/wallet/trc1155/getCollectionInfos";
        response = createPostConnectWithSignature(curUri, params, null, body);
        return response;
    }

}
