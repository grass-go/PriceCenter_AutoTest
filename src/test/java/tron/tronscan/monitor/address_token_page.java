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

public class address_token_page {

    private JSONObject responseContent;
    private JSONObject responseContent2;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List account")
    public void test01getAccount() {
        System.out.println();
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("address", "TKwmkoBFyhekCgkAZ7FmxnN6oaL3GyD8xR");
        response = TronscanApiList.getAccountList(tronScanNode, params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //data object
        responseArrayContent = responseContent.getJSONArray("trc20token_balances");
        JSONObject responseObject = responseArrayContent.getJSONObject(0);
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(!responseObject.getString("contract_address").isEmpty());
            String trc20Address = responseObject.getString("contract_address");
            Map<String, String> params2 = new HashMap<>();
            params2.put("contract",trc20Address);
            response = TronscanApiList.getTokentrc20(tronScanNode,params2);
            responseContent2 = TronscanApiList.parseResponseContent(response);
            Assert.assertTrue(responseContent2.getInteger("total") == 1);
            try {
                Thread.sleep(500);
            }catch (Exception ex){
            }
        }
    }
}
