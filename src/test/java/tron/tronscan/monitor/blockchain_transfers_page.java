package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class blockchain_transfers_page {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private JSONArray sidechainsArrayContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "区块链-转账")
    public void blockchain_transfers_page() {
        int limit = 20;
        int start = 0;
        int end_time = (int) (System.currentTimeMillis() / 1000) ;
        String end_timestamp = String.valueOf(end_time) + "000";
        Boolean count = true;
        Map<String, String> params = new HashMap<>();
        params.put("sort", "-timestamp");
        params.put("count", String.valueOf(count));
        params.put("limit", String.valueOf(limit));
        params.put("start", String.valueOf(start));
        params.put("start_timestamp", "1529856000000");
        params.put("end_timestamp", end_timestamp);

        response = TronscanApiList.getTrc10trc20(tronScanNode,params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "retCode" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Assert.assertTrue(responseContent.getLong("total") > 0);
        Assert.assertTrue(responseContent.getLong("rangeTotal") > 336660327);
        Long rangeTotal = responseContent.getLong("rangeTotal");
        //contractMap
        Assert.assertTrue(responseContent.containsKey("contractMap"));
        //transfers
        responseArrayContent = responseContent.getJSONArray("transfers");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            JSONObject responseObject = responseArrayContent.getJSONObject(0);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("contractRet"));
            Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("amount")) >0);
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("owner_address")).matches());
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("to_address")).matches());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("date_created").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("type").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("confirmed").isEmpty());
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("block")) > 10000000);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("contract_ret").isEmpty());
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            String hash = responseArrayContent.getJSONObject(i).getString("hash");
            Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());
        }



        //转账数据
        response = TronscanApiList.getStatistics(tronScanNode);
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
        Assert.assertTrue((rangeTotal - responseContent.getLong("transfersCount")) < 1000);
        Assert.assertTrue(Double.valueOf(responseContent.get("trc20Amount").toString()) > 10000);
        Assert.assertTrue(Double.valueOf(responseContent.get("transfersCount").toString()) > 336422769);
        Assert.assertTrue(Double.valueOf(responseContent.get("trc10TransferProportion").toString()) > 0);
        Assert.assertTrue(responseContent.getLong("trc20Count") > 10000);
        Assert.assertTrue(Double.valueOf(responseContent.get("trc10TransferAmount").toString()) > 10000);
        Assert.assertTrue(Double.valueOf(responseContent.get("transfersAmount").toString()) > 1090332348169994L);
        Assert.assertTrue(Double.valueOf(responseContent.get("trxTransferProportion").toString()) > 0);
        Assert.assertTrue(Double.valueOf(responseContent.get("lastDayTransfersAmount").toString()) > 10000);
    }

}
