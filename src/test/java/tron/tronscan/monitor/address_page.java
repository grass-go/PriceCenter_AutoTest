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

public class address_page {

    private JSONObject responseContent;
    private JSONObject responseContent2;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "address token")
    public void address_token() {
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

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "address transaction")
    public void address_transaction() {
        Map<String, String> Params = new HashMap<>();
        Params.put("sort", "-timestamp");
        Params.put("limit", "20");
        Params.put("count", "true");
        Params.put("start", "0");
        Params.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        response = TronscanApiList.getTransactionList(tronScanNode, Params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        Assert.assertTrue(Long.valueOf(responseContent.get("wholeChainTxCount").toString()) >= 1000000000);
        double total = responseContent.getDouble("total");
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);
        responseArrayContent = responseContent.getJSONArray("data");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
            String hash_key = responseArrayContent.getJSONObject(i).getString("hash");
            Assert.assertEquals(hash_key.length(), 64);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("block") >= 10000000);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("timestamp").isEmpty());

            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("contractType").isEmpty());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String ownerAddress = responseArrayContent.getJSONObject(i).getString("ownerAddress");
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("toAddress"));
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("contractRet").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("result").isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("amount").isEmpty());
            //contractData
            JSONObject responseObject = responseArrayContent.getJSONObject(i).getJSONObject("contractData");
            Assert.assertEquals(responseObject.getString("owner_address"), ownerAddress);
            Assert.assertTrue(patternAddress.matcher(ownerAddress).matches());
        }

        String start = String.valueOf(((int)Math.floor(total/20)*20));
        System.out.println(start);
        Map<String, String> Params2 = new HashMap<>();
        Params2.put("sort", "-timestamp");
        Params2.put("limit", "20");
        Params2.put("count", "true");
        Params2.put("start", start);
        Params2.put("address", "TQ48z1p3kdQeZrf5Dc62U88bsbhvRJJQFn");
        response = TronscanApiList.getTransactionList(tronScanNode, Params2);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        Assert.assertTrue(rangeTotal >= total && total > 0);
        responseArrayContent = responseContent.getJSONArray("data");
        Assert.assertTrue(!responseArrayContent.isEmpty());
    }
}
