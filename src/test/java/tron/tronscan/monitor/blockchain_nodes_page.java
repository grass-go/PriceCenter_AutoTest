package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;

import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

public class blockchain_nodes_page {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list").get(0);

    /**
     * constructor.
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List all the nodes in the blockchain")
    public void getNodeMap() {
        response = TronscanApiList.getNodeMap(tronScanNode);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //three object, "total" and "Data"
        Assert.assertTrue(responseContent.size() >= 3);
        Integer total = Integer.valueOf(responseContent.get("total").toString());
        JSONArray exchangeArray = responseContent.getJSONArray("data");
        //当前节点大于0
        Assert.assertTrue(exchangeArray.size() == total && total >500);
        Assert.assertTrue(Double.valueOf(responseContent.get("code").toString()) >= 0);
        //country
        targetContent = exchangeArray.getJSONObject(0);
        Assert.assertTrue(targetContent.containsKey("country"));
        //lng and lat Contain double
        Assert.assertTrue(Double.valueOf(targetContent.get("lng").toString()) > -1000);
        Assert.assertTrue(Double.valueOf(targetContent.get("lat").toString()) > 0);

        Assert.assertTrue(targetContent.containsKey("ip"));


    }


}
