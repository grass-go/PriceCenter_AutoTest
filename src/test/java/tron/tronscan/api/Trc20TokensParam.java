package tron.tronscan.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

@Slf4j
public class Trc20TokensParam {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private JSONArray sidechainsArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    /**
     * constructor.根据创建者获取trc20信息
     * 合约概览中trc20token信息
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "根据创建者获取trc20信息")
    public void getTrc20Tokens() {
        String issuer_addr = "TZEZWXYQS44388xBoMhQdpL1HrBZFLfDpt";
        Map<String, String> params = new HashMap<>();
        params.put("issuer_addr", issuer_addr);
        response = TronscanApiList.getTrc20Tokens(tronScanNode, params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("retMsg"));
        Assert.assertTrue(Double.valueOf(responseContent.get("retCode").toString()) >= 0);
        //data
        targetContent = responseContent.getJSONObject("data");
        responseArrayContent = targetContent.getJSONArray("tokens");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            String icon_url = responseArrayContent.getJSONObject(i).get("icon_url").toString();
            Assert.assertTrue(!icon_url.isEmpty());
            //各别url是图片
            Boolean url_real = responseArrayContent.getJSONObject(i).getString("icon_url").substring(0,8).equals("https://");
            if(url_real){
                HttpResponse httpResponse = TronscanApiList.getUrlkey(icon_url);
                Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
            }
            //
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("symbol").isEmpty());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("total_supply") >= 1000000000);
            //contract_address
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String contract_address = responseArrayContent.getJSONObject(i).getString("contract_address");
            Assert.assertTrue(patternAddress.matcher(contract_address).matches());
            Assert.assertEquals(responseArrayContent.getJSONObject(i).getString("issuer_addr"), issuer_addr);

            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("home_page").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("token_desc").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("update_time").isEmpty());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("git_hub"));
            //decimals
            Assert.assertTrue(responseArrayContent.getJSONObject(i)
                    .getInteger("decimals") >= 0 && responseArrayContent.getJSONObject(i).getInteger("decimals") <= 7);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("name").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("issue_time").isEmpty());
            //email
//            String email = responseArrayContent.getJSONObject(i).getString("email");
//            Assert.assertTrue(email.contains("@"));

            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("white_paper"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("social_media"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("status"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("sidechains"));
            //sidechains
//            sidechainsArrayContent = responseArrayContent.getJSONObject(i).getJSONArray("sidechains");
//            JSONObject sidechainsObject = sidechainsArrayContent.getJSONObject(0);
//            Pattern sidechainsAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
//            Assert.assertEquals(sidechainsObject.getString("mainchain_address"), contract_address);
//            Assert.assertTrue(sidechainsAddress.matcher(sidechainsObject.getString("sidechain_address")).matches());
//            Assert.assertTrue(sidechainsObject.containsKey("chainid"));
//            Assert.assertTrue(sidechainsObject.containsKey("type"));
        }
    }
    /**
     * constructor.转账列表优化新增接口
     * 返回昨日新增转账数、交易额，累积转账数、交易额，TRX转账/10币转账/20币转账的交易数，交易额及分别的转账数占比。
     * 数据均为被动刷新方式更新数据  交易数 3秒更新一次，交易额3分钟更新一次。
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "刷新数据接口")
    public void getStatistics() {
        response = TronscanApiList.getStatistics(tronScanNode);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.containsKey("trc20Proportion"));
        Assert.assertTrue(Double.valueOf(responseContent.get("trxTransferAmount").toString()) > 0);
        Assert.assertTrue(responseContent.containsKey("trc10TransferCount"));
        Assert.assertTrue(responseContent.containsKey("lastDayTransfersCount"));
        Assert.assertTrue(responseContent.containsKey("trxTransferCount"));
        Assert.assertTrue(responseContent.containsKey("transfersCount"));
        Assert.assertTrue(Double.valueOf(responseContent.get("trc20Amount").toString()) >= 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("trc10TransferProportion").toString()) > 0);
        Assert.assertTrue(responseContent.containsKey("trc20Count"));
        Assert.assertTrue(Double.valueOf(responseContent.get("trc10TransferAmount").toString()) > 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("transfersAmount").toString()) > 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("trxTransferProportion").toString()) > 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("lastDayTransfersAmount").toString()) > 0);

    }

    /**
     * constructor.转账列表优化
     * 支持按时间段返回数据
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "按时间段返回数据")
    public void getTrc10trc20() {
        int limit = 20;
        int start = 0;
        Boolean count = true;
        Map<String, String> params = new HashMap<>();
        params.put("sort", "-timestamp");
        params.put("count", String.valueOf(count));
        params.put("limit", String.valueOf(limit));
        params.put("start", String.valueOf(start));
        params.put("start_timestamp", "1529856000000");
        params.put("end_timestamp", "1593509437573");

        response = TronscanApiList.getTrc10trc20(tronScanNode,params);
        log.info("code is " + response.getStatusLine().getStatusCode());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.getLong("total") > 0);
        Assert.assertTrue(responseContent.getLong("rangeTotal") > 0);
        //contractMap
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //transfers
        responseArrayContent = responseContent.getJSONArray("transfers");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            JSONObject responseObject = responseArrayContent.getJSONObject(0);

            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("contractRet"));
            Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("amount")) >0);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("cost"));
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("owner_address")).matches());
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("to_address")).matches());
//            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("contract_address")).matches());
//            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("from_address")).matches());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("date_created").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("type").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("confirmed").isEmpty());
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("block")) > 10000000);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("contract_ret").isEmpty());
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            String hash = responseArrayContent.getJSONObject(i).getString("hash");
            Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());
        }
    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }

}
