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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;

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
import org.tron.protos.contract.AccountContract;
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

import tron.common.utils.TronlinkServerHttpClient;
import tron.common.utils.HttpDeleteWithBody;



@Slf4j
public class TronlinkApiList extends TronlinkServerHttpClient {
    public static String HttpTronDataNode = TrondataBase.trondataUrl;
    static JSONObject responseContent;
    public static Map<String, String> header = new HashMap<>();
    static byte ADD_PRE_FIX_BYTE_MAINNET = (byte) 0x41;
    public static String JustlendNode = "https://labc.ablesdxd.link";   //prod
    //public static String JustlendNode = "http://47.252.29.162:10091";   //nile
    public static String nileFullnode = "47.252.19.181";
    public static String prodfullnode = "3.225.171.164";

    //if request version is larger than fixedVersion ,return false.
    //while request version is smaller than fixed Version return true.
    public static boolean needUpgrade(String reqVersion, String fixedVersion) {
        if (StringUtils.isEmpty(fixedVersion)) {
            return false;
        }

        if (StringUtils.isEmpty(reqVersion)) {
            return false;
        }

        if (Objects.equals(reqVersion, fixedVersion)) {
            return false;
        }

        try {
            final String[] splitReq = reqVersion.split("\\.");
            final String[] splitDB = fixedVersion.split("\\.");

            int minLen = splitReq.length > splitDB.length ? splitDB.length : splitReq.length;

            for (int i = 0; i < minLen; i++) {
                int reqV = Integer.valueOf(splitReq[i]);
                int dbV = Integer.valueOf(splitDB[i]);

                if (dbV > reqV) {
                    return true;
                } else if (dbV < reqV) {
                    return false;
                }
            }

            return splitDB.length > splitReq.length;
        } catch (Exception ex) {
            log.error("", ex);
        }

        return false;
    }

    public static HttpResponse classify() {
        String requestUrl = HttpNode + "/api/dapp/v2/classify";
        header.put("Lang", defaultLang);
        response = createGetConnect(requestUrl, null, null,header);
        return response;
    }

    public static HttpResponse hot_recommend() {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp/hot_recommend";
        header.clear();
        header.put("Lang", defaultLang);
        response = createGetConnect(requestUrl,null,null,header);
        return response;
    }

    public static HttpResponse multiTrxRewordNoSig(HashMap<String, String> param,HashMap<String, String> header) throws Exception {
        String requestUrl = HttpNode + "/api/wallet/multi/trx_record";
        response = createGetConnect(requestUrl,param,null,header);
        return response;
    }

    public static HttpResponse multiTrxReword(HashMap<String, String> param,HashMap<String, String> header) throws Exception {
        String curURI = "/api/wallet/multi/trx_record";
        response = createGetConnectWithSignature(curURI,param,header,null);
        return response;
    }



    public static HttpResponse trc20InfoNoSig(HashMap<String, String> param, HashMap<String, String> header) throws Exception {
        String requestUrl = HttpNode + "/api/wallet/trc20_info";
        response = createGetConnect(requestUrl,param,null,header);
        return response;
    }

    public static HttpResponse trc20Info(HashMap<String, String> param,HashMap<String, String> header) throws Exception {
        String curURI = "/api/wallet/trc20_info";
        response = createGetConnectWithSignature(curURI,param,header,null);
        return response;
    }

    public static HttpResponse getInterChainEvent(HashMap<String, String> param) throws Exception {
        final String requestUrl = HttpNode + "/api/interchain-event";
        response = createGetConnect(requestUrl,param,null,null);
        return response;
    }

    public static HttpResponse apiTransferTrx(HashMap<String, String> param, HashMap<String, String> header) throws Exception {
        final String curURI = "/api/transfer/trx";
        response = createGetConnectWithSignature(curURI,param,header,null);
        return response;
    }

    public static HttpResponse apiTransferTrxNoSig(HashMap<String, String> param, HashMap<String, String> header) {
        final String requestUrl = HttpNode + "/api/transfer/trx";
        response = createGetConnect(requestUrl,param,null,header);
        return response;
    }

    public static HttpResponse getTransferTrx(Map<String, String> params) {
        String requestUrl = HttpTronDataNode + "/api/transfer/trx";
        response = createGetConnect(requestUrl, params,null,null);
        return response;
    }

    public static HttpResponse getTransferToken10(Map<String, String> params) {
        String requestUrl = HttpTronDataNode + "/api/transfer/token10";
        response = createGetConnect(requestUrl, params, null, null);
        return response;
    }
    public static HttpResponse apiTransferToken10(HashMap<String, String> param,HashMap<String, String> header) {
        final String curURI = "/api/transfer/token10";
        response = createGetConnectWithSignature(curURI,param,header,null);
        return response;
    }

    public static HttpResponse apiTransferToken10NoSig(HashMap<String, String> param,HashMap<String, String> header) throws Exception {
        final String requestUrl = HttpNode + "/api/transfer/token10";
        response = createGetConnect(requestUrl,param,null,header);
        return response;
    }

    public static HttpResponse apiTransferTrc20NoSig(HashMap<String, String> param, HashMap<String, String> header) {
        final String requestUrl = HttpNode + "/api/transfer/trc20";
        response = createGetConnect(requestUrl,param,null,header);
        return response;
    }

    public static HttpResponse apiTransferTrc20(HashMap<String, String> param, HashMap<String, String> header) {
        final String curURI = "/api/transfer/trc20";
        response = createGetConnectWithSignature(curURI,param,header,null);
        return response;
    }

    public static HttpResponse getTransferTrc20(Map<String, String> params) {
        String requestUrl = HttpTronDataNode + "/api/transfer/trc20";
        response = createGetConnect(requestUrl, params, null, null);
        return response;
    }

    public static HttpResponse apiTransferTrc20StatusNoSig(HashMap<String, String> param, HashMap<String, String> header) throws Exception {
        String requestUrl = HttpNode + "/api/transfer/trc20_status";
        response = createGetConnect(requestUrl,param,null,header);
        return response;
    }
    public static HttpResponse apiTransferTrc20Status(HashMap<String, String> param,HashMap<String, String> header) throws Exception {
        String curURI = "/api/transfer/trc20_status";
        response = createGetConnectWithSignature(curURI,param,header,null);
        return response;
    }

    public static HttpResponse transferAccount(HashMap<String, String> params, HashMap<String, String> headers) throws Exception {
        String curURI = "/api/transfer/account";
        response = createGetConnectWithSignature(curURI,params,headers,null);
        return response;
    }


    public static HttpResponse votingWitnessNoSig(Map<String, String> params, Map<String, String> header) {
        String requestUrl = HttpNode + "/api/voting/v2/witness";
        response = createGetConnect(requestUrl, params,null,header);
        return response;
    }

    public static HttpResponse votingWitness(Map<String, String> params) {
        String curUri = "/api/voting/v2/witness";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static boolean getAllWitnessFromTronscan() {
        String requestUrl = TronlinkBase.tronscanApiUrl + "/api/vote/witness";
        response = createGetConnect(requestUrl,null,null,null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
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
        response = createGetConnect(requestUrl, params,null,null);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
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

    public static HttpResponse votingV2SearchNoSig(Map<String, String> params,Map<String, String> header) {
        String requestUrl = HttpNode + "/api/voting/v2/search";
        response = createGetConnect(requestUrl, params,null,header);
        return response;
    }

    public static HttpResponse votingV2Search(Map<String, String> params) throws Exception {
        String curUri = "/api/voting/v2/search";
        response = createGetConnectWithSignature(curUri, params,null,null);
        return response;
    }

    public static HttpResponse votingV2SelfNoSig(Map<String, String> params,Map<String, String> header) {
        String requestUrl = HttpNode + "/api/voting/v2/self";
        response = createGetConnect(requestUrl, params, null, header);
        return response;
    }
    public static HttpResponse votingV2Self(Map<String, String> params) {
        String curUri = "/api/voting/v2/self";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static HttpResponse walletMarketBanner() {
        String requestUrl = HttpNode + "/api/wallet/market/banner";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl,null,null,header);
        return response;
    }

    public static HttpResponse walletMarketFavorite(Map<String, String> params) throws Exception {
        String requestUrl = HttpNode + "/api/wallet/market/favorite";
        response = createGetConnect(requestUrl, params,null,null);
        return response;
    }

    public static HttpResponse walletMarketTrxUsdt(Map<String, String> params) throws Exception {
        String requestUrl = HttpNode + "/api/wallet/market/trx_usdt";
        response = createGetConnect(requestUrl, params,null,null);
        return response;
    }

    public static HttpResponse head() {
        String requestUrl = HttpNode + "/api/dapp/v2/head";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl, null, null, header);
        return response;
    }

    public static HttpResponse hot_search() {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp/hot_search";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl, null, null, header);
        return response;
    }

    public static HttpResponse dapp_list(Map<String, String> params) {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl, params, null, header);
        return response;
    }

    public static HttpResponse dappBanner() {
        String requestUrl = HttpNode + "/api/dapp/v2/banner";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl, null,null,header);
        return response;
    }

    public static HttpResponse dappBannerV3(Map<String, String> caseParams,Map<String, String> caseHeader) {
        String curURI = "/api/dapp/v3/banner";
        response = createGetConnectWithSignature(curURI, caseParams, caseHeader,null);
        return response;
    }

    public static HttpResponse dappClassfyV3(Map<String, String> caseParams,Map<String, String> caseHeader) {
        String curURI = "/api/dapp/v3/classify";
        response = createGetConnectWithSignature(curURI, caseParams, caseHeader, null);
        return response;
    }

    public static HttpResponse dappAuthorizedProject() {
        String requestUrl = HttpNode + "/api/dapp/v2/authorized_project";
        response = createGetConnect(requestUrl, null,null,null);
        return response;
    }



    public static HttpResponse swapExchangesNoSig(Map<String,String> params,Map<String,String> header) {
        String requestUrl = HttpNode + "/api/swap/v1/exchanges";
        response = createGetConnect(requestUrl, params,null,header);
        return response;
    }

    public static HttpResponse swapExchanges(Map<String,String> params) {
        String curURI =  "/api/swap/v1/exchanges";
        response = createGetConnectWithSignature(curURI, params,null,null);
        return response;
    }

    public static HttpResponse transfer1155(Map<String,String> params) {
        String curUri = "/api/transfer/v2/trc1155";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static HttpResponse dappPlug() {
        String requestUrl = HttpNode + "/dapphouseapp/plug";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl, null,null,header);
        return response;
    }

    //to do : check api not found?
    public static HttpResponse dappId(Map<String, String> params) {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp/id";
        log.info(requestUrl);
        response = createGetConnect(requestUrl,params,null,null);
        return response;
    }

    public static HttpResponse search(Map<String, String> params) {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp/search";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl, params, null,header);
        return response;
    }


    public static HttpResponse dappSearchV3(Map<String, String> caseParams,Map<String, String> caseHeader) {
        String curURI = "/api/dapp/v3/search";
        response = createGetConnectWithSignature(curURI, caseParams, caseHeader,null);
        return response;
    }

    public static HttpResponse history(Map<String, String> params) {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp/history";
        header.clear();
        header.put("Lang",defaultLang);
        response = createGetConnect(requestUrl, params,null,header);
        return response;
    }

    public static HttpResponse put_history(JSONObject requestBody) {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp/history";
        header.clear();
        header.put("Lang","1");
        header.put("Content-Type","application/json");
        response = createPutConnect(requestUrl, null, requestBody,header);
        return response;
    }

    public static HttpResponse delete_history(JSONObject requestBody) {
        String requestUrl = HttpNode + "/api/dapp/v2/dapp/history";
        header.clear();
        header.put("Lang","1");
        header.put("Content-Type","application/json");
        response = createDeleteConnect(requestUrl, null,requestBody,header);
        return response;
    }

    public static HttpResponse accountListNoSig(JSONArray body,Map<String, String> header ) {
        String requestUrl = HttpNode + "/api/wallet/account/list";
        response = createPostConnect(requestUrl,null, body,header);
        return response;
    }

    public static HttpResponse accountList(JSONArray body,Map<String, String> params ) {
        String curURI = "/api/wallet/account/list";
        response = createPostConnectWithSignature(curURI,params, null,body);
        return response;
    }



    //related case disabled
    public static HttpResponse allasset(String address) {
        String requestUrl = HttpNode + "/api/wallet/class/allasset";
        JSONObject body = new JSONObject();
        body.put("address", address);
        response = createPostConnect(requestUrl,null,body,null);
        return response;
    }



    public static HttpResponse getAllClassAsset(String node, JSONObject address) throws Exception {
        final String requestUrl = node + "/api/wallet/class/allasset";
        response = createPostConnect(requestUrl, null, address,null);
        return response;
    }

    public static HttpResponse assetlistNoSig(String address,Map<String, String> header) {
        String requestUrl = HttpNode + "/api/wallet/assetlist";
        JSONObject body = new JSONObject();
        body.put("address", address);
        response = createPostConnect(requestUrl,null,body,header);
        return response;
    }


    public static HttpResponse assetlist(String address, Map<String, String> params) {
        String curURI = "/api/wallet/assetlist";
        JSONObject body = new JSONObject();
        body.put("address", address);
        response = createPostConnectWithSignature(curURI,params,null,body);
        return response;
    }



    //related case disabled.
    public static HttpResponse lotteryData() throws Exception {
        final String requesturl = HttpNode + "/api/wallet/lottery/default_data";
        response = createGetConnect(requesturl,null,null,null);
        return response;
    }

    public static HttpResponse hot_tokenV2(HashMap<String, String> params) {
        String curURI = "/api/wallet/v2/hot_token";
        response = createGetConnectWithSignature(curURI,params,null,null);
        return response;
    }

    //only /api/wallet/hot_token supports low version. /api/wallet/v2/hot_token must has signature to access.
    public static HttpResponse hot_tokenV1NoSig(String address, HashMap<String, String> header) {
        String requestUrl = HttpNode + "/api/wallet/hot_token";
        JSONObject body = new JSONObject();
        body.put("address", address);
        response = createPostConnect(requestUrl,null,body,header);
        return response;
    }

    public static HttpResponse hot_tokenV1(HashMap<String, String> params) {
        String curURI = "/api/wallet/hot_token";
        JSONObject body = new JSONObject();
        body.put("address", params.get("address"));
        response = createPostConnectWithSignature(curURI,params,null,body);
        return response;
    }


    public static HttpResponse addAssetNoSig(JSONObject address, HashMap<String, String> header ) {
        final String requestUrl = HttpNode + "/api/wallet/addasset";
        response = createPostConnect(requestUrl,null, address,header);
        return response;
    }

    public static HttpResponse addAsset(JSONObject address, HashMap<String, String> params) {
        final String curURI = "/api/wallet/addasset";
        response = createPostConnectWithSignature(curURI,params, null,address);
        return response;
    }

    public static HttpResponse ieo() {
        final String requestUrl = HttpNode + "/api/wallet/ieo";
        response = createGetConnect(requestUrl, null,null,null);
        return response;
    }

    //如果下架不带签名的接口，则删除本函数。
    public static HttpResponse getNodeInfo(JSONArray body, String withSystem) {


        final String requestUrl = HttpNode + "/api/wallet/node_info";
        log.info("requestUrl:"+ requestUrl);
        header.clear();
        header.put("Version", "3.7.0");
        if (withSystem.equals("YES")) {
            header.put("System", "Android");
        }
        response = createPostConnect(requestUrl, null,body,header);
        return response;
    }

    // nodeinfo升级的需要签名的请求接口,传入的header和参数可以覆盖默认的参数
    public static HttpResponse getNodeInfo(Map<String,String> params, JSONArray body, Map<String,String> headers) {
        final String curUri =  "/api/wallet/node_info";
        response = createPostConnectWithSignature(curUri, params, headers, body);
        return response;
    }


    public static HttpResponse getConfigNoSig(HashMap<String,String> header) {
        final String requestUrl = HttpNode + "/api/wallet/get_config";
        response = createGetConnect(requestUrl, null,null,header);
        return response;
    }

    public static HttpResponse getConfig(HashMap<String,String> params) {
        final String curURI = "/api/wallet/get_config";
        response = createGetConnectWithSignature(curURI, params,null,null);
        return response;
    }




    public static HttpResponse dappToMainFeeNoSig(HashMap<String,String> headers) {
        final String requestUrl = HttpNode + "/api/transfer/dappToMainFee";
        response = createGetConnect(requestUrl, null, null, headers);
        return response;
    }

    public static HttpResponse dappToMainFee(HashMap<String,String> params) {
        final String curURI =  "/api/transfer/dappToMainFee";
        response = createGetConnectWithSignature(curURI, params, null,null);
        return response;
    }

    public static HttpResponse getAirdropTransaction(Map<String, String> params) {
        final String requestUrl = HttpNode + "/api/wallet/airdrop_transaction";
        response = createGetConnect(requestUrl, params,null,null);
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

    public static HttpResponse getTrc20Holders(Map<String, String> params) {
        String requestUrl = HttpTronDataNode + "/api/trc20/holders";
        response = createGetConnect(requestUrl, params, null, null);
        return response;
    }

    public static HttpResponse multiTransactionNoSig(JSONObject body, HashMap<String, String> header) {
        String requestUrl = HttpNode + "/api/wallet/multi/transaction";
        response = createPostConnect(requestUrl, null, body, header);
        return response;
    }

    public static HttpResponse multiTransaction(JSONObject body,HashMap<String, String> caseParams, HashMap<String, String> caseHeader ) {
        String curURI =  "/api/wallet/multi/transaction";
        response = createPostConnectWithSignature(curURI, caseParams, caseHeader, body);
        return response;
    }

    public static HttpResponse multiTransactionNoSig(JSONObject body,HashMap<String, String> caseParams, HashMap<String, String> caseHeader ) {
        String requestUrl = HttpNode  + "/api/wallet/multi/transaction";
        response = createPostConnect(requestUrl, caseParams,body,caseHeader);
        return response;
    }

    public static Boolean verificationResult(HttpResponse response) {
        if (response.getStatusLine().getStatusCode() != 200) {
            return false;
        }
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        return true;
    }

    public static HttpResponse getInviteCode(JSONObject body) throws Exception {
        final String requestUrl = HttpNode + "/api/wallet/invite/get_code";
        response = createPostConnect(requestUrl, null, body, null);
        return response;
    }

    public static HttpResponse failTransfer(JSONObject body) {
        final String requestUrl = HttpNode + "/api/wallet/fail_transfer";
        response = createPostConnect(requestUrl,null, body, null);
        return response;
    }

    public static HttpResponse insertInviteCode(JSONObject body) throws Exception {
        final String requestUrl = HttpNode + "/api/wallet/invite/code";
        response = createPostConnect(requestUrl, null,body,null);
        return response;
    }

    public static HttpResponse nilexGetAssetlist(String address, Map<String, String> header) {
        try {
            String requestUrl = "https://niletest.tronlink.org/api/wallet/assetlist";
            JSONObject body = new JSONObject();
            body.put("address", address);
            header.put("Content-type", "application/json; charset=utf-8");
            header.put("Connection", "Close");
            response = createPostConnect(requestUrl, null, body, header);
        } catch (Exception e) {
            e.printStackTrace();
            httppost.releaseConnection();
            return null;
        }
        return response;
    }



    public static HttpResponse V2UnfollowCollections(Map<String, String> params) {
        String curUri = "/api/wallet/nft/unfollowCollections";
        response = createGetConnectWithSignature(curUri, params,null,null);
        return response;
    }

    public static HttpResponse V2UnfollowAssetList(Map<String, String> params,Map<String, String> headers) {
        String curUri = "/api/wallet/v2/unfollowAssetList";
        response = createGetConnectWithSignature(curUri, params,headers,null);
        return response;
    }

    public static HttpResponse V2AllAssetList(Map<String, String> caseParams) {

        String curURI = "/api/wallet/v2/allAssetList";
        response = createGetConnectWithSignature(curURI, caseParams,null,null);
        return response;

    }

    public static HttpResponse v2AssetList(Map<String, String> caseParams)  {
        String curURI = "/api/wallet/v2/assetList";
        response = createGetConnectWithSignature(curURI, caseParams,null,null);
        return response;

    }

    public static HttpResponse v2AssetList(Map<String, String> params, JSONObject body) {
        final String curURI = "/api/wallet/v2/assetList";
        response = createPostConnectWithSignature(curURI, params, null, body);
        return response;
    }

    public static HttpResponse v2GetAssetList(Map<String, String> params, JSONObject body,
            Map<String, String> headers) {
        final String requestUrl = "https://testpre.tronlink.org" + "/api/wallet/v2/assetList";

        log.info("requestUrl : " + requestUrl);
        response = createGetConnect(requestUrl, params, body, headers);
        return response;
    }

    public static HttpResponse v2GetAllCollection(Map<String, String> caseParams) {

        String curURI = "/api/wallet/nft/getAllCollection";
        response = createGetConnectWithSignature(curURI, caseParams, null,null);
        return response;

    }

    // 根据token类型查询首页信息
    public static HttpResponse v2GetAllCollectionByType(String url, Map<String, String> params) {
        final String requestUrl = HttpNode +  url ;
        response = createGetConnectWithSignature(url, params,null,null);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        return response;
    }

    public static HttpResponse v2AllCollections(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/allCollections";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static HttpResponse v2GetCollectionList(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/getCollectionList";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static HttpResponse v2GetCollectionInfo(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/getCollectionInfo";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static HttpResponse v2GetCollectionInfos(Map<String, String> params, JSONObject body) {
        final String curURI = "/api/wallet/nft/getCollectionInfos";
        response = createPostConnectWithSignature(curURI, params, null, body);
        return response;
    }

    public static HttpResponse getDelegatedResource(Map<String, String> params) {
        final String curURI = "/api/wallet/v2/getDelegatedResource";
        response = createGetConnectWithSignature(curURI, params, null,null);
        return response;
    }

    public static HttpResponse v2NewAssetList(Map<String, String> params) {
        final String curURI = "/api/wallet/v2/newAssetList";
        response = createGetConnectWithSignature(curURI, params, null,null);
        return response;
    }

    public static HttpResponse v2SearchAsset(Map<String, String> params) {
        final String curURI = "/api/wallet/v2/search";
        response = createGetConnectWithSignature(curURI, params, null,null);
        return response;
    }

    public static HttpResponse v1GetNoticeRemindNoSig(Map<String, String> header) {
        final String requestUrl = HttpNode + "/api/v1/wallet/getNoticeRemind";
        response = createGetConnect(requestUrl, null, null, header);
        return response;
    }
    public static HttpResponse v1GetNoticeRemind(Map<String, String> params) {
        final String curURI =  "/api/v1/wallet/getNoticeRemind";
        response = createGetConnectWithSignature(curURI, params, null,null);
        return response;
    }

    public static HttpResponse v1DappActivityAddNoSig(JSONObject body, Map<String, String> header) {
        final String requestUrl = HttpNode + "/api/activity/add";
        response = createPostConnect(requestUrl, null, body, header);
        return response;
    }

    public static HttpResponse v1DappActivityAdd(JSONObject body,Map<String, String> params,Map<String, String> header) {
        final String curURI =  "/api/activity/add";
        response = createPostConnectWithSignature(curURI, params, header,body);
        return response;
    }

    public static HttpResponse v1GetAnnouncementNoSig(Map<String, String> headers) {
        final String requestUrl = HttpNode + "/api/activity/announcement/reveal_v2";
        response = createGetConnect(requestUrl, null, null, headers);
        return response;
    }
    public static HttpResponse v1GetAnnouncement(Map<String, String> params) {
        final String curURI =  "/api/activity/announcement/reveal_v2";
        response = createGetConnectWithSignature(curURI, params, null,null);
        return response;
    }

    public static HttpResponse v1GetNodesNoSig(Map<String, String> params, Map<String, String> header) {
        final String requestUrl = HttpNode + "/api/v1/wallet/nodes";
        response = createPostConnect(requestUrl, params, (JSONObject) null, header);
        return response;
    }

    public static HttpResponse v1GetNodes(Map<String, String> params) {
        final String curURI =  "/api/v1/wallet/nodes";
        response = createPostConnectWithSignature(curURI, params,null, (JSONObject) null);
        return response;
    }

    public static HttpResponse v2GetBlacklistNoSig(Map<String, String> headers) {
        final String requestUrl = HttpNode + "/api/activity/website/blacklist";
        response = createGetConnect(requestUrl, null, null, headers);
        return response;
    }

    public static HttpResponse v2GetBlacklist(Map<String, String> params,Map<String, String> headers) {
        final String curURI = "/api/activity/website/blacklist";
        response = createGetConnectWithSignature(curURI, params, headers,null);
        return response;
    }

    public static HttpResponse officialTokenNoSig(Map<String, String> params,Map<String, String> header) {
        final String requestUrl = HttpNode + "/api/wallet/official_token";
        response = createGetConnect(requestUrl, params, null, header);
        return response;
    }

    public static HttpResponse officialToken(Map<String, String> params,Map<String, String> header) {
        final String curURI = "/api/wallet/official_token";
        response = createGetConnectWithSignature(curURI, params,header,null);
        return response;
    }

    public static HttpResponse v1PlayScreenInfoNoSig(Map<String, String> caseheader) {
        final String requestUrl = HttpNode + "/api/activity/play_screen/info";
        response = createGetConnect(requestUrl, null, null , caseheader);
        return response;
    }
    public static HttpResponse v1PlayScreenInfo(Map<String, String> header, Map<String, String> params) {
        final String curURI =  "/api/activity/play_screen/info";

        response = createGetConnectWithSignature(curURI, params, header,null);
        return response;
    }


    public static HttpResponse v1PlayScreenDealNoSig(String playId, HashMap<String, String> header) {
        final String requestUrl = HttpNode + "/api/activity/play_screen/deal";
        JSONObject body = new JSONObject();
        body.put("playId", playId);
        response = createPostConnect(requestUrl, null, body, header);
        return response;
    }

    public static HttpResponse v1PlayScreenDeal(String playId, Map<String, String> params,Map<String, String> header) {
        final String curURI =  "/api/activity/play_screen/deal";
        JSONObject body = new JSONObject();
        body.put("playId", playId);
        response = createPostConnectWithSignature(curURI, params, header,body);
        return response;
    }


    public static HttpResponse v1GetStartupNoSig(Map<String, String> params, Map<String, String> headerMap) {
        final String requestUrl = HttpNode + "/api/v1/wallet/startup";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        for (String key : headerMap.keySet()) {
            header.put(key, headerMap.get(key));
        }
        response = createGetConnect(requestUrl, params, null, header);
        return response;
    }

    public static HttpResponse v1GetStartup(Map<String, String> params, Map<String, String> headerMap) {
        final String curURI = "/api/v1/wallet/startup";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        for (String key : headerMap.keySet()) {
            header.put(key, headerMap.get(key));
        }
        response = createGetConnectWithSignature(curURI, params, header,null);
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
        response = createPostConnect(requestUrl,null, (JSONObject) JSONObject.parse(
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
        response = createGetConnect(requestUrl, params, null, header);
        return response;
    }

    public static HttpResponse v2accountList(Map<String, String> params, JSONArray object, Map<String, String> headers) {
        String curURI = "/api/wallet/v2/account/list";
        response = createPostConnectWithSignature(curURI, params, headers, object );
        return response;
    }

    public static HttpResponse v2Asset(Map<String, String> params) {
        final String curUri = "/api/wallet/v2/asset";
        response = createGetConnectWithSignature(curUri, params, null,null );
        return response;
    }

    public static HttpResponse v2Auth(Map<String, String> params) {
        final String curUri = "/api/wallet/v2/auth";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static HttpResponse v2NftSearch(Map<String, String> params) {
        final String curUri = "/api/wallet/nft/search";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }

    public static HttpResponse v2AddAsset(Map<String, String> caseheader,Map<String, String> caseParams, JSONObject object) {
        String curURI = "/api/wallet/v2/addAsset";
        response = createPostConnectWithSignature(curURI, caseParams, caseheader, object );
        return response;
    }

    public static HttpResponse v2DelAsset(Map<String, String> caseheader,Map<String, String> caseparams, JSONObject object) {
        String curURI = "/api/wallet/v2/delAsset";
        response = createPostConnectWithSignature(curURI, caseparams, caseheader, object);
        return response;
    }

    public static HttpResponse v1UpgradeV2NoSig(Map<String, String> headers) {
        String requestUrl = HttpNode + "/api/v1/wallet/v2/upgrade";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        header = AddMap(header, headers);

        response = createGetConnect(requestUrl, null, null, header);
        return response;
    }


    public static HttpResponse v1UpgradeV2(Map<String, String> caseparams, Map<String, String> caseheader) {
        String curURI = "/api/v1/wallet/v2/upgrade";
        response = createGetConnectWithSignature(curURI, caseparams, caseheader,null);
        return response;
    }

    public static HttpResponse v1UpgradeNoSig(Map<String, String> params) {
        String requestUrl = HttpNode + "/api/v1/wallet/upgrade";
        Map<String, String> header = getV2Header();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        header = AddMap(header, params);
        response = createGetConnect(requestUrl, null, null, header);
        return response;
    }

    public static HttpResponse v1Upgrade(Map<String, String> caseParam,Map<String, String> header) {
        String curURI = "/api/v1/wallet/upgrade";
        Map<String, String> headers = getV2Header();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        headers = AddMap(headers, header);
        response = createGetConnectWithSignature(curURI, caseParam, headers,null);
        return response;
    }

    public static JSONObject getTronscanTrc20Price(String address) {
        if (address.startsWith("T")) {
            String reqestUrl = "https://apilist.tronscan.org/api/token_trc20?contract=" + address + "&showAll=1";
            HttpResponse transcanRsp = createGetConnect(reqestUrl, null, null, null);
            JSONObject transcanRspContent = TronlinkApiList.parseResponse2JsonObject(transcanRsp);
            JSONObject priceJson = (JSONObject) JSONPath.eval(transcanRspContent,
                    String.join("", "$..trc20_tokens[contract_address='", address, "'].market_info[0]"));
            return priceJson;
        } else if (address.startsWith("100")) {
            String reqestUrl = "https://apilist.tronscan.org/api/token?id=" + address + "&showAll=1";
            HttpResponse transcanRsp = TronlinkApiList.createGetConnect(reqestUrl, null, null, null);
            JSONObject transcanRspContent = TronlinkApiList.parseResponse2JsonObject(transcanRsp);
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
    public static boolean CompareGapInGivenToleranceInDecimalFormat(BigDecimal expected, BigDecimal actual, String toleranceRate) {
        BigDecimal toleranceRate_bd = new BigDecimal(toleranceRate);
        BigDecimal tolerance_bd = expected.multiply(toleranceRate_bd);
        BigDecimal absgap = actual.subtract(expected).abs();
        log.info("expected:" + expected.toString() + ", actual:" + actual.toString() + ", GAP:" + absgap + ", tolerance:"
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

    public static HttpResponse upgrade(HashMap<String, String> header) throws Exception {
        final String requestUrl = HttpNode + "/api/v1/wallet/upgrade";
        response = createGetConnect(requestUrl, null, null, header);
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

    public static Protocol.Transaction accountCreate(byte[] ownerAddress, byte[] newAddress,
                                                     WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
        byte[] owner = ownerAddress;
        AccountContract.AccountCreateContract.Builder builder = AccountContract.AccountCreateContract.newBuilder();
        builder.setOwnerAddress(ByteString.copyFrom(owner));
        builder.setAccountAddress(ByteString.copyFrom(newAddress));
        AccountContract.AccountCreateContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.createAccount(contract);
        //transaction = TransactionUtils.setDelaySeconds(transaction, delaySeconds);
        if (transaction == null || transaction.getRawData().getContractCount() == 0) {
            log.info("transaction == null");
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

        //transaction = setPermissionId(transaction, permissionId);
        long now = System.currentTimeMillis();
        //Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder().setExpiration(now+86400000L);
        Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder().setExpiration(now+120000);
        //Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder();
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

    public static Protocol.Transaction addTransactionSignOnly(Protocol.Transaction transaction,
                                                                          String priKey) {
        Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
        ECKey temKey = null;
        try {
            BigInteger priK = new BigInteger(priKey, 16);
            temKey = ECKey.fromPrivate(priK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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


    public static Protocol.Transaction addTransactionSignWithPermissionIdOld(Protocol.Transaction transaction,
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
    public static String generateRawdataHex(Protocol.Transaction transaction) {
        String newHex = ByteArray.toHexString(transaction.getRawData().toByteArray());
        return newHex;
    }

    //String newHex = ByteArray.toHexString(newTrx.getRawData().toByteArray());


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

    public static HttpResponse v2TokenQuery(Map<String, String> headers, Map<String, String> params, JSONObject object) {
        String curUri = "/api/wallet/v2/token/query";
        response = createPostConnectWithSignature(curUri, params, headers, object);
        return response;
    }

    public static HttpResponse v2TokenAdd(Map<String, String> header, Map<String, String> params, JSONObject object) {
        String curUri = "/api/wallet/v2/token/add";
        response = createPostConnectWithSignature(curUri, params, header,object);
        return response;
    }

    public static HttpResponse v2TokenSync(Map<String, String> header, Map<String, String> params, JSONObject object) {
        String curUri = "/api/wallet/v2/token/sync";
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
        response = createGetConnectWithSignature(curUri, caseParams, null,null);
        return response;
    }



    public static JSONObject getprice(String symbol, String convert) {
        Map<String, String> params = new HashMap<>();
        params.clear();
        params.put("symbol", symbol);
        params.put("convert", convert);
        response = PriceCenterApiList.getprice(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        return responseContent;
    }

    public static HttpResponse tokenInfo(Map<String, String> caseparams,Map<String, String> caseheaders) throws Exception {
        String curUri =  "/api/wallet/v2/tokenInfo";
        response = createGetConnectWithSignature(curUri, caseparams, caseheaders,null);
        return response;
    }

    public static HttpResponse v2CheckWhite(Map<String, String> params) throws Exception {
        String curUri =  "/api/wallet/v2/white";
        Map<String, String> header = new HashMap<>();
        header.put("Content-type", "application/json; charset=utf-8");
        header.put("Connection", "Close");
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }
    public static HttpResponse v1BalanceNoSig(Map<String, String> params,Map<String, String> headers) {
        String requestUrl = HttpNode + "/api/wallet/balance";
        response = createGetConnect(requestUrl, params, null, headers);
        return response;
    }

    public static HttpResponse v1Balance(Map<String, String> params,Map<String, String> headers) {
        String curURI = "/api/wallet/balance";
        response = createGetConnectWithSignature(curURI, params, headers,null);
        return response;
    }

    public static HttpResponse TransferV2Trc721(Map<String, String> params) {
        final String curUri = "/api/transfer/v2/trc721";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }
    public static HttpResponse TransferTrc721(Map<String, String> params) {
        final String curUri = "/api/transfer/trc721";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }
    public static HttpResponse sdk_verify(String json) {
        String requestUrl = HttpNode + "/api/wallet/sdk_verify";
        response = createPostConnect(requestUrl, null, json, null);
        return response;
    }
    public static HttpResponse v2GetAllCollection_1155(Map<String, String> params) {
        final String curUri = "/api/wallet/trc1155/getAllCollection";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }
    
    public static HttpResponse v2AllCollections_1155(Map<String, String> params) {
        final String curUri = "/api/wallet/trc1155/allCollections";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }
    
    public static HttpResponse v2GetCollectionList_1155(Map<String, String> params) {
        final String curUri = "/api/wallet/trc1155/getCollectionList";
        response = createGetConnectWithSignature(curUri, params, null,null);
        return response;
    }
    public static HttpResponse v2GetCollectionInfos_1155(Map<String, String> params, JSONObject body) {
        final String curUri = "/api/wallet/trc1155/getCollectionInfos";
        response = createPostConnectWithSignature(curUri, params, null, body);
        return response;
    }

    //add for multiTest
    public static Boolean sendcoinDirectely(byte[] to, long amount, byte[] owner, String priKey,
                                            WalletGrpc.WalletBlockingStub blockingStubFull) {
        log.info("in PublicMethod: priKey is "+priKey.toString());
        Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
        //String priKey = testKey002;
        ECKey temKey = null;
        try {
            BigInteger priK = new BigInteger(priKey, 16);
            temKey = ECKey.fromPrivate(priK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final ECKey ecKey = temKey;
//    ecKey.getAddress();
//    log.info("-------key: "+ByteArray.toHexString(ecKey.getPrivateKey()));



        Integer times = 0;
        while (times++ <= 2) {

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
                continue;
            }
            transaction = signTransaction(ecKey, transaction);
            GrpcAPI.Return response = broadcastTransaction(transaction, blockingStubFull);
            return response.getResult();
        }
        return false;
    }
    public static Protocol.Transaction signTransaction(ECKey ecKey,
                                                       Protocol.Transaction transaction) {
        Wallet.setAddressPreFixByte(ADD_PRE_FIX_BYTE_MAINNET);
        if (ecKey == null || ecKey.getPrivKey() == null) {
            //logger.warn("Warning: Can't sign,there is no private key !!");
            return null;
        }
        transaction = setTimestamp(transaction);
        log.info("Txid in sign is " + ByteArray.toHexString(Sha256Hash
                .hash(CommonParameter.getInstance().isECKeyCryptoEngine(),
                        transaction.getRawData().toByteArray())));
        return sign(transaction, ecKey);
    }

    public static GrpcAPI.Return broadcastTransaction(Protocol.Transaction transaction,
                                                      WalletGrpc.WalletBlockingStub blockingStubFull) {
        int i = 10;
        GrpcAPI.Return response = blockingStubFull.broadcastTransaction(transaction);
        while (!response.getResult() && response.getCode() == GrpcAPI.Return.response_code.SERVER_BUSY
                && i > 0) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
            response = blockingStubFull.broadcastTransaction(transaction);
            log.info("repeate times = " + (10 - i));
        }

        if (response.getResult() == false) {
            log.info("Code = " + response.getCode());
            log.info("Message = " + response.getMessage().toStringUtf8());
        }
        return response;
    }

    public static Protocol.Transaction setTimestamp(Protocol.Transaction transaction) {
        long currentTime = System.currentTimeMillis();//*1000000 + System.nanoTime()%1000000;
        Protocol.Transaction.Builder builder = transaction.toBuilder();
        org.tron.protos.Protocol.Transaction.raw.Builder rowBuilder = transaction.getRawData()
                .toBuilder();
        rowBuilder.setTimestamp(currentTime);
        builder.setRawData(rowBuilder.build());
        return builder.build();
    }

    public static Protocol.Transaction sign(Protocol.Transaction transaction, ECKey myKey) {
        ByteString lockSript = ByteString.copyFrom(myKey.getAddress());
        Protocol.Transaction.Builder transactionBuilderSigned = transaction.toBuilder();

        byte[] hash = Sha256Hash.hash(CommonParameter
                .getInstance().isECKeyCryptoEngine(), transaction.getRawData().toByteArray());
        List<Protocol.Transaction.Contract> listContract = transaction.getRawData().getContractList();
        for (int i = 0; i < listContract.size(); i++) {
            ECKey.ECDSASignature signature = myKey.sign(hash);
            ByteString bsSign = ByteString.copyFrom(signature.toByteArray());
            transactionBuilderSigned.addSignature(
                    bsSign);//Each contract may be signed with a different private key in the future.
        }

        transaction = transactionBuilderSigned.build();
        return transaction;
    }

    public static SmartContractOuterClass.SmartContract.ABI jsonStr2Abi(String jsonStr) {
        if (jsonStr == null) {
            return null;
        }

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElementRoot = jsonParser.parse(jsonStr);
        JsonArray jsonRoot = jsonElementRoot.getAsJsonArray();
        SmartContractOuterClass.SmartContract.ABI.Builder abiBuilder = SmartContractOuterClass.SmartContract.ABI.newBuilder();
        for (int index = 0; index < jsonRoot.size(); index++) {
            JsonElement abiItem = jsonRoot.get(index);
            boolean anonymous =
                    abiItem.getAsJsonObject().get("anonymous") != null ? abiItem.getAsJsonObject()
                            .get("anonymous").getAsBoolean() : false;
            final boolean constant =
                    abiItem.getAsJsonObject().get("constant") != null ? abiItem.getAsJsonObject()
                            .get("constant").getAsBoolean() : false;
            final String name =
                    abiItem.getAsJsonObject().get("name") != null ? abiItem.getAsJsonObject().get("name")
                            .getAsString() : null;
            JsonArray inputs =
                    abiItem.getAsJsonObject().get("inputs") != null ? abiItem.getAsJsonObject().get("inputs")
                            .getAsJsonArray() : null;
            final JsonArray outputs =
                    abiItem.getAsJsonObject().get("outputs") != null ? abiItem.getAsJsonObject()
                            .get("outputs").getAsJsonArray() : null;
            String type =
                    abiItem.getAsJsonObject().get("type") != null ? abiItem.getAsJsonObject().get("type")
                            .getAsString() : null;
            final boolean payable =
                    abiItem.getAsJsonObject().get("payable") != null ? abiItem.getAsJsonObject()
                            .get("payable").getAsBoolean() : false;
            final String stateMutability =
                    abiItem.getAsJsonObject().get("stateMutability") != null ? abiItem.getAsJsonObject()
                            .get("stateMutability").getAsString() : null;
            if (type == null) {
                log.error("No type!");
                return null;
            }
            if (!type.equalsIgnoreCase("fallback") && null == inputs) {
                log.error("No inputs!");
                continue;
            }

            SmartContractOuterClass.SmartContract.ABI.Entry.Builder entryBuilder = SmartContractOuterClass.SmartContract.ABI.Entry.newBuilder();
            entryBuilder.setAnonymous(anonymous);
            entryBuilder.setConstant(constant);
            if (name != null) {
                entryBuilder.setName(name);
            }

            /* { inputs : optional } since fallback function not requires inputs*/
            if (inputs != null) {
                for (int j = 0; j < inputs.size(); j++) {
                    JsonElement inputItem = inputs.get(j);
                    if (inputItem.getAsJsonObject().get("name") == null
                            || inputItem.getAsJsonObject().get("type") == null) {
                        log.error("Input argument invalid due to no name or no type!");
                        return null;
                    }
                    String inputName = inputItem.getAsJsonObject().get("name").getAsString();
                    String inputType = inputItem.getAsJsonObject().get("type").getAsString();
                    SmartContractOuterClass.SmartContract.ABI.Entry.Param.Builder paramBuilder = SmartContractOuterClass.SmartContract.ABI.Entry.Param
                            .newBuilder();
                    paramBuilder.setIndexed(false);
                    paramBuilder.setName(inputName);
                    paramBuilder.setType(inputType);
                    entryBuilder.addInputs(paramBuilder.build());
                }
            }

            /* { outputs : optional } */
            if (outputs != null) {
                for (int k = 0; k < outputs.size(); k++) {
                    JsonElement outputItem = outputs.get(k);

                    SmartContractOuterClass.SmartContract.ABI.Entry.Param.Builder paramBuilder = SmartContractOuterClass.SmartContract.ABI.Entry.Param
                            .newBuilder();
                    if (outputItem.getAsJsonObject().get("name") != null) {
                        String outputName = outputItem.getAsJsonObject().get("name").getAsString();

                        paramBuilder.setName(outputName);
                    }
                    if (outputItem.getAsJsonObject().get("type") == null) {

                        String outputType = outputItem.getAsJsonObject().get("type").getAsString();
                        paramBuilder.setType(outputType);
                    }
                    paramBuilder.setIndexed(false);
                    entryBuilder.addOutputs(paramBuilder.build());
                }
            }

            entryBuilder.setType(getEntryType(type.toLowerCase(Locale.ROOT)));
            entryBuilder.setPayable(payable);
            if (stateMutability != null) {
                entryBuilder.setStateMutability(getStateMutability(stateMutability.toLowerCase(Locale.ROOT)));
            }

            abiBuilder.addEntrys(entryBuilder.build());
        }

        return abiBuilder.build();
    }
    public static SmartContractOuterClass.SmartContract.ABI.Entry.EntryType getEntryType(String type) {
        switch (type) {
            case "constructor":
                return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Constructor;
            case "function":
                return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Function;
            case "event":
                return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Event;
            case "fallback":
                return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.Fallback;
            default:
                return SmartContractOuterClass.SmartContract.ABI.Entry.EntryType.UNRECOGNIZED;
        }
    }

    public static SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType getStateMutability(
            String stateMutability) {
        switch (stateMutability.toLowerCase(Locale.ROOT)) {
            case "pure":
                return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.Pure;
            case "view":
                return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.View;
            case "nonpayable":
                return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.Nonpayable;
            case "payable":
                return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.Payable;
            default:
                return SmartContractOuterClass.SmartContract.ABI.Entry.StateMutabilityType.UNRECOGNIZED;
        }
    }

    public static byte[] decode58Check(String input) {
        byte[] decodeCheck = org.tron.common.utils.Base58.decode(input);
        if (decodeCheck.length <= 4) {
            return null;
        }
        byte[] decodeData = new byte[decodeCheck.length - 4];
        System.arraycopy(decodeCheck, 0, decodeData, 0, decodeData.length);
        byte[] hash0 = Sha256Hash.hash(CommonParameter.getInstance().isECKeyCryptoEngine(), decodeData);
        byte[] hash1 = Sha256Hash.hash(CommonParameter.getInstance().isECKeyCryptoEngine(), hash0);
        if (hash1[0] == decodeCheck[decodeData.length] && hash1[1] == decodeCheck[decodeData.length + 1]
                && hash1[2] == decodeCheck[decodeData.length + 2] && hash1[3] == decodeCheck[
                decodeData.length + 3]) {
            return decodeData;
        }
        return null;
    }

    public static GrpcAPI.Return accountPermissionUpdateForResponse(String permissionJson,
                                                                    byte[] owner, String priKey, WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte((byte) 0x41);
        ECKey temKey = null;
        try {
            BigInteger priK = new BigInteger(priKey, 16);
            temKey = ECKey.fromPrivate(priK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        final ECKey ecKey = temKey;

        AccountContract.AccountPermissionUpdateContract.Builder builder = AccountContract.AccountPermissionUpdateContract.newBuilder();

        JSONObject permissions = JSONObject.parseObject(permissionJson);
        JSONObject ownerpermission = permissions.getJSONObject("owner_permission");
        JSONObject witnesspermission = permissions.getJSONObject("witness_permission");
        JSONArray activepermissions = permissions.getJSONArray("active_permissions");

        if (ownerpermission != null) {
            Protocol.Permission ownerPermission = json2Permission(ownerpermission);
            builder.setOwner(ownerPermission);
        }
        if (witnesspermission != null) {
            Protocol.Permission witnessPermission = json2Permission(witnesspermission);
            builder.setWitness(witnessPermission);
        }
        if (activepermissions != null) {
            List<Protocol.Permission> activePermissionList = new ArrayList<>();
            for (int j = 0; j < activepermissions.size(); j++) {
                JSONObject permission = activepermissions.getJSONObject(j);
                activePermissionList.add(json2Permission(permission));
            }
            builder.addAllActives(activePermissionList);
        }
        builder.setOwnerAddress(ByteString.copyFrom(owner));

        AccountContract.AccountPermissionUpdateContract contract = builder.build();

        GrpcAPI.TransactionExtention transactionExtention = blockingStubFull.accountPermissionUpdate(contract);
        if (transactionExtention == null) {
            return null;
        }
        GrpcAPI.Return ret = transactionExtention.getResult();
        if (!ret.getResult()) {
            System.out.println("Code = " + ret.getCode());
            System.out.println("Message = " + ret.getMessage().toStringUtf8());
            return ret;
        }
        Protocol.Transaction transaction = transactionExtention.getTransaction();
        if (transaction == null || transaction.getRawData().getContractCount() == 0) {
            System.out.println("Transaction is empty");
            return ret;
        }
        System.out.println(
                "Receive txid = " + ByteArray.toHexString(transactionExtention.getTxid().toByteArray()));
        transaction = signTransaction(ecKey, transaction);
        GrpcAPI.Return response = broadcastTransaction(transaction, blockingStubFull);

        return response;
    }

    private static Protocol.Permission json2Permission(JSONObject json) {
        Protocol.Permission.Builder permissionBuilder = Protocol.Permission.newBuilder();
        if (json.containsKey("type")) {
            int type = json.getInteger("type");
            permissionBuilder.setTypeValue(type);
        }
        if (json.containsKey("permission_name")) {
            String permissionName = json.getString("permission_name");
            permissionBuilder.setPermissionName(permissionName);
        }
        if (json.containsKey("threshold")) {
            //long threshold = json.getLong("threshold");
            long threshold = Long.parseLong(json.getString("threshold"));
            permissionBuilder.setThreshold(threshold);
        }
        if (json.containsKey("parent_id")) {
            int parentId = json.getInteger("parent_id");
            permissionBuilder.setParentId(parentId);
        }
        if (json.containsKey("operations")) {
            byte[] operations = ByteArray.fromHexString(json.getString("operations"));
            permissionBuilder.setOperations(ByteString.copyFrom(operations));
        }
        if (json.containsKey("keys")) {
            JSONArray keys = json.getJSONArray("keys");
            List<Protocol.Key> keyList = new ArrayList<>();
            for (int i = 0; i < keys.size(); i++) {
                Protocol.Key.Builder keyBuilder = Protocol.Key.newBuilder();
                JSONObject key = keys.getJSONObject(i);
                String address = key.getString("address");
                long weight = Long.parseLong(key.getString("weight"));
                //long weight = key.getLong("weight");
                //keyBuilder.setAddress(ByteString.copyFrom(address.getBytes()));
                keyBuilder.setAddress(ByteString.copyFrom(decode58Check(address)));
                keyBuilder.setWeight(weight);
                keyList.add(keyBuilder.build());
            }
            permissionBuilder.addAllKeys(keyList);
        }
        return permissionBuilder.build();
    }

    public static HttpResponse totalAssets(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/totalAssets";
        response = createPostConnectWithSignature(curURI,params, headers,postdata);
        return response;
    }
    public static HttpResponse tokenFinancialList(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/tokenFinancialList";
        response = createPostConnectWithSignature(curURI,params, headers,postdata);
        return response;
    }
    public static HttpResponse v2assets(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/wallet/v2/assets";
        response = createPostConnectWithSignature(curURI,params, headers,postdata);
        return response;
    }

    public static HttpResponse myFinancialTokenList(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/myFinancialTokenList";
        response = createPostConnectWithSignature(curURI,params, headers,postdata);
        return response;
    }

    public static HttpResponse myFinancialProjectList(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/myFinancialProjectList";
        response = createPostConnectWithSignature(curURI,params, headers,postdata);
        return response;
    }

    public static HttpResponse justLendDetail( HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/justLendDetail";
        response = createGetConnectWithSignature(curURI,params, headers,null);
        return response;
    }

    public static HttpResponse justLendOperate( HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/justLendOperate";
        response = createGetConnectWithSignature(curURI,params, headers,null);
        return response;
    }


    public static HttpResponse apyList( HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/apyList";
        response = createGetConnectWithSignature(curURI,params, headers,null);
        return response;
    }

    public static HttpResponse apyListNoSig( HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl =HttpNode + "/api/financial/apyList";
        response = createGetConnect(requestUrl,params, null,headers);
        return response;
    }

    public static HttpResponse queryAbi( HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/wallet/v2/queryAbi";
        response = createGetConnectWithSignature(curURI,params, headers,null);
        return response;
    }

    public static HttpResponse statAction(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/stat/action";
        response = createPostConnectWithSignature(curURI,params, headers,postdata);
        return response;
    }

    public static HttpResponse totalAssetsNoSig(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl =HttpNode + "/api/financial/totalAssets";
        response = createPostConnect(requestUrl,params, postdata,headers);
        return response;
    }
    public static HttpResponse tokenFinancialListNoSig(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl = HttpNode + "/api/financial/tokenFinancialList";
        response = createPostConnect(requestUrl,params, postdata, headers);
        return response;
    }

    public static HttpResponse v2assetsNoSig(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl = HttpNode + "/api/wallet/v2/assets";
        response = createPostConnect(requestUrl,params,postdata,headers);
        return response;
    }

    public static HttpResponse myFinancialTokenListNoSig(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl = HttpNode + "/api/financial/myFinancialTokenList";
        response = createPostConnect(requestUrl,params,postdata, headers);
        return response;
    }

    public static HttpResponse myFinancialProjectListNoSig(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl =HttpNode + "/api/financial/myFinancialProjectList";
        response = createPostConnect(requestUrl,params,postdata, headers);
        return response;
    }

    public static HttpResponse justLendDetailNoSig( HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl = HttpNode + "/api/financial/justLendDetail";
        response = createGetConnect(requestUrl,params,null,headers);
        return response;
    }

    public static HttpResponse justLendOperateNoSig( HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl =HttpNode + "/api/financial/justLendOperate";
        response = createGetConnect(requestUrl,params,null, headers);
        return response;
    }

    public static HttpResponse queryAbiNoSig( HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl =HttpNode +  "/api/wallet/v2/queryAbi";
        response = createGetConnect(requestUrl,params,null,header);
        return response;
    }

    public static HttpResponse statActionNoSig(JSONObject postdata, HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl =HttpNode + "/api/stat/action";
        response = createPostConnect(requestUrl,params,postdata, headers);
        return response;
    }

    public static HttpResponse getJustlendDepositeAPY(){
        final String requestUrl =JustlendNode + "/justlend/markets";
        response = createGetConnect(requestUrl,null, null,null);
        return response;
    }
    public static HttpResponse getJustlendMiningAPY(){
        final String requestUrl =JustlendNode + "/third/mining/apy";
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }
    public static HttpResponse getBttcAPY(){
        final String requestUrl ="https://api.newbt.io/bttc/chain/info";
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }
    public static HttpResponse getScanWitnessAPY(){
        final String requestUrl ="https://apilist.tronscanapi.com/api/pagewitness";
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }
    public static HttpResponse getJustlandUserDeposite(String account) {
        String requestUrl = JustlendNode + "/justlend/account?ver=v2&addr="+account;
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }
    public static HttpResponse getJTokenDetailInJustlend(String jToken){
        String requestUrl = JustlendNode + "/justlend/markets/jtokenDetails?jtokenAddr="+jToken;
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }
    public static HttpResponse getTokenPrice(String Symbol, String Convert){
        String requestUrl = "https://c.tronlink.org/v1/cryptocurrency/getprice?symbol="+Symbol+"&convert="+Convert;
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }
    public static HttpResponse getBttInBttcProject(String address){
        String requestUrl = "https://api.bt.io/bttc/account/info?address="+address;
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }

    public static HttpResponse getJustlendMiningReward(String address){
        String requestUrl = JustlendNode + "/third/mining/reward?address="+address;
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }
    public static HttpResponse getFullNodeTrxReward(String address){
        String requestUrl = "https://api.trongrid.io/wallet/getReward?address="+address;
        response = createGetConnect(requestUrl,null,null, null);
        return response;
    }


    public static HttpResponse reward( HashMap<String, String> params,HashMap<String, String> headers) {
        final String curURI = "/api/financial/reward";
        response = createGetConnectWithSignature(curURI,params, headers,null);
        return response;
    }
    public static HttpResponse rewardNoSig( HashMap<String, String> params,HashMap<String, String> headers) {
        final String requestUrl = HttpNode + "/api/financial/reward";
        response = createGetConnect(requestUrl, params, null,headers);
        return response;
    }

    public static HttpResponse totalAddress(String userAccount) {
        final String curURI = "/api/stake/total/"+ userAccount;
        response = createGetConnectWithSignature(curURI, null, null,null);
        return response;
    }

    public static HttpResponse scanGetAccount(String userAccount) {
        final String requestUrl = TronlinkBase.tronscanApiUrl  + "/api/accountv2?address="+userAccount;
        response = createGetConnect(requestUrl, null, null,null);
        return response;
    }

    public static HttpResponse fromAddress(String userAccount, HashMap<String, String> caseParams) {
        final String curURI = "/api/stake/from/"+ userAccount;
        response = createGetConnectWithSignature(curURI, caseParams, null,null);
        return response;
    }


    public static HttpResponse delegateAddress(String userAccount, HashMap<String, String> caseParams) {
        final String curURI = "/api/stake/delegate/"+ userAccount;
        response = createGetConnectWithSignature(curURI, caseParams, null,null);
        return response;
    }

    public static HttpResponse nodeGetResource(String httpnode, String userAccount) {
        //http://47.252.19.181:8090/wallet/getaccountresource?address=41E7D71E72EA48DE9144DC2450E076415AF0EA745F
        final String requestUrl = "http://" + httpnode + "/wallet/getaccountresource?address="+userAccount;
        response = createGetConnect(requestUrl, null, null,null);
        return response;
    }
}
