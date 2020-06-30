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
            .getStringList("tronexapi.ip.list")
            .get(0);

    /**
     * constructor.根据创建者获取trc20信息
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "根据创建者获取trc20信息")
    public void getTrc20Tokens() {
        String issuer_addr = "TW5y9tuvgummvvuhfmmBQES7fVUhEdqPHK";
        Map<String, String> params = new HashMap<>();
        params.put("issuer_addr", issuer_addr);
        response = TronscanApiList.getTrc20Tokens(tronScanNode,params);
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
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        Assert.assertTrue(responseObject.containsKey("icon_url"));
        Assert.assertTrue(responseObject.containsKey("symbol"));
        Assert.assertTrue(responseObject.containsKey("total_supply"));
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("contract_address")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("issuer_addr")).matches());
        Assert.assertTrue(responseObject.containsKey("home_page"));
        Assert.assertTrue(responseObject.containsKey("token_desc"));
        Assert.assertTrue(responseObject.containsKey("update_time"));
        Assert.assertTrue(responseObject.containsKey("git_hub"));
        Assert.assertTrue(responseObject.containsKey("decimals"));
        Assert.assertTrue(responseObject.containsKey("name"));
        Assert.assertTrue(responseObject.containsKey("issue_time"));
        Assert.assertTrue(responseObject.containsKey("email"));

        Assert.assertTrue(responseObject.containsKey("white_paper"));
        Assert.assertTrue(responseObject.containsKey("social_media"));
        Assert.assertTrue(responseObject.containsKey("status"));
        //sidechains
        sidechainsArrayContent = responseObject.getJSONArray("sidechains");
        JSONObject sidechainsObject = sidechainsArrayContent.getJSONObject(0);
        Pattern sidechainsAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(sidechainsAddress.matcher(sidechainsObject.getString("mainchain_address")).matches());
        Assert.assertTrue(sidechainsAddress.matcher(sidechainsObject.getString("sidechain_address")).matches());
        Assert.assertTrue(sidechainsObject.containsKey("chainid"));
        Assert.assertTrue(sidechainsObject.containsKey("type"));
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
        Assert.assertTrue(Double.valueOf(responseContent.get("trxTransferAmount").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("trc10TransferCount"));
        Assert.assertTrue(responseContent.containsKey("lastDayTransfersCount"));
        Assert.assertTrue(responseContent.containsKey("trxTransferCount"));
        Assert.assertTrue(responseContent.containsKey("transfersCount"));
        Assert.assertTrue(Double.valueOf(responseContent.get("trc20Amount").toString()) >= 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("trc10TransferProportion").toString()) >= 0);
        Assert.assertTrue(responseContent.containsKey("trc20Count"));
        Assert.assertTrue(Double.valueOf(responseContent.get("trc10TransferAmount").toString()) >= 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("transfersAmount").toString()) >= 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("trxTransferProportion").toString()) >= 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("lastDayTransfersAmount").toString()) >= 0);

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
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        Assert.assertTrue(responseObject.containsKey("contractRet"));
        Assert.assertTrue(responseObject.containsKey("amount"));
        Assert.assertTrue(responseObject.containsKey("cost"));
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("owner_address")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("to_address")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("contract_address")).matches());
        Assert.assertTrue(patternAddress.matcher(responseObject.getString("from_address")).matches());
        Assert.assertTrue(responseObject.containsKey("date_created"));
        Assert.assertTrue(responseObject.containsKey("revert"));
        Assert.assertTrue(responseObject.containsKey("type"));
        Assert.assertTrue(responseObject.containsKey("confirmed"));
        Assert.assertTrue(responseObject.containsKey("block"));
        Assert.assertTrue(responseObject.containsKey("contract_ret"));
        Assert.assertTrue(responseObject.containsKey("hash"));

    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        TronscanApiList.disGetConnect();
    }

}
