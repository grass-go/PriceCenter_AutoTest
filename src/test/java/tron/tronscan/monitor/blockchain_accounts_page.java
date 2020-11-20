package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

public class blockchain_accounts_page {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    /**
     * constructor.账户页列表接口
     * 根据地址查询出合约下的交易，当前地址显示rangeTotal大于10000条时，目前total只展示10000条
     *
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List account")
    public void test01getAccount() {
        System.out.println();
        //Get response
        int limit = 20;
        Map<String, String> params = new HashMap<>();
        params.put("sort", "-balance");
        params.put("limit", String.valueOf(limit));
        params.put("start", "0");
        response = TronscanApiList.getAccount(tronScanNode, params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //total
        //页面仅展示的数量，如果大于10000，则最多显示10000
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0 && total <= 10000);
        //data object
        responseArrayContent = responseContent.getJSONArray("data");
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertEquals(limit, responseArrayContent.size());
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            //账户
            String address = responseObject.getString("address");
            Assert.assertTrue(patternAddress.matcher(address).matches() && !address.isEmpty());
            //TRX总余额
            Assert.assertTrue(Double.valueOf(responseObject.get("balance").toString()) > 1000000000L);
            //冻结TRX数量
            Assert.assertTrue(Long.valueOf(responseObject.get("power").toString()) >= 0);
            //交易数量
            Assert.assertTrue(Long.valueOf(responseObject.get("totalTransactionCount").toString()) > 0);
            //账户是否为合约
            Assert.assertTrue(responseContent.containsKey("contractMap"));
        }
    }

}
