package tron.tronscan.tronscan_api.block;

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




public class block {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get lasted block details")
    public void getLastBlockDetails() {
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("sort", "-number");
        params.put("limit", "1");
        params.put("count", "true");
        response = TronscanApiList.getBlockDetail(tronScanNode, params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //total
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        responseArrayContent = responseContent.getJSONArray("data");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            String hash = responseArrayContent.getJSONObject(i).getString("hash");
            Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());

            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("size").toString()) > 0);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("txTrieRoot").toString().isEmpty());
            //parentHash
            String parentHash = responseArrayContent.getJSONObject(i).getString("parentHash");
            Assert.assertTrue(patternHash.matcher(parentHash).matches() && !parentHash.isEmpty());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("witnessId"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("nrOfTrx"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String witnessAddress = responseArrayContent.getJSONObject(i).getString("witnessAddress");
            Assert.assertTrue(patternAddress.matcher(witnessAddress).matches() && !witnessAddress.isEmpty());
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("number").toString()) >20000000);

        }
    }


    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get blocks detail")
    public void getForkBlockDetails() {
        //Get response
        Map<String, String> params = new HashMap<>();
        params.put("sort", "-number");
        params.put("limit", "1");
        params.put("count", "true");
        params.put("number", "43131232");

        response = TronscanApiList.getBlockDetail(tronScanNode, params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //total
        Long total = Long.valueOf(responseContent.get("total").toString());
        Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
        Assert.assertTrue(rangeTotal >= total && total > 0);

        responseArrayContent = responseContent.getJSONArray("data");
        for (int i = 0; i < responseArrayContent.size(); i++) {
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
            Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
            String hash = responseArrayContent.getJSONObject(i).getString("hash");
            Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());

            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("size").toString()) == 114806);
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("txTrieRoot").toString().isEmpty());
            //parentHash
            String parentHash = responseArrayContent.getJSONObject(i).getString("parentHash");
            Assert.assertTrue(patternHash.matcher(parentHash).matches() && !parentHash.isEmpty());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("witnessId"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("nrOfTrx"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
            Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            String witnessAddress = responseArrayContent.getJSONObject(i).getString("witnessAddress");
            Assert.assertTrue(patternAddress.matcher(witnessAddress).matches() && !witnessAddress.isEmpty());
            Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("number").toString()) >20000000);
            JSONObject forkInfo = responseArrayContent.getJSONObject(i).getJSONObject("forkInfo");
            Assert.assertEquals(forkInfo.getString("blockHash"),"0000000002922160a85c9ab05501f385df5f0b64407f14070bb0429fd9eed156");
            Assert.assertEquals(forkInfo.getString("witnessAddress"),"TSMC4YzUSfySfqKuFnJbYyU3W6PBebBk2E");
            Assert.assertEquals(forkInfo.getString("witnessName"),"Smart Consensus");



        }
    }
}
