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

public class contracts_contracts_page {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list").get(0);

    /**
     * constructor.
     * 合约概览展示接口
     */
    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "合约概览")
    public void contracts_page() {
        //Get response
        int limit = 20;
        Map<String, String> params = new HashMap<>();
        params.put("count", "true");
        params.put("limit", String.valueOf(limit));
        params.put("start", "0");
        params.put("confirm", "0");
        params.put("verified-only", "true");
        params.put("open-source-only", "false");
        params.put("sort", "-trxCount");

        response = TronscanApiList.getContractsList(tronScanNode, params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);

        //4 key
        Assert.assertTrue(responseContent.size() == 4);
        Long total = Long
                .valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long
                .valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total <= 10000 && total > 0);
        Assert.assertTrue(!responseContent.getString("status").isEmpty());

        //Address list
        responseArrayContent = responseContent.getJSONArray("data");
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(responseArrayContent.size() > 0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i)
                    .getString("address")).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("verify_status").isEmpty());
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("balance")) >= 0);
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("trxCount")) > 0);
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("verify_status")) == 2);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("date_created").isEmpty());

        }

    }

    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "合约概览,有开源信息")
    public void contracts_page_license() {
        //Get response
        int limit = 20;
        Map<String, String> params = new HashMap<>();
        params.put("count", "true");
        params.put("limit", String.valueOf(limit));
        params.put("start", "0");
        params.put("confirm", "0");
        params.put("verified-only", "true");
        params.put("open-source-only", "true");
        params.put("sort", "-trxCount");

        response = TronscanApiList.getContractsList(tronScanNode, params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);

        //4 key
        Assert.assertTrue(responseContent.size() == 4);
        Long total = Long
                .valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long
                .valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total <= 10000 && total > 0);
        Assert.assertTrue(!responseContent.getString("status").isEmpty());

        //Address list
        responseArrayContent = responseContent.getJSONArray("data");
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(responseArrayContent.size() > 0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i)
                    .getString("address")).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("verify_status").isEmpty());
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("balance")) >= 0);
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("trxCount")) > 0);
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("verify_status")) == 2);
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("license")) > 0);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("date_created").isEmpty());

        }

    }
}
