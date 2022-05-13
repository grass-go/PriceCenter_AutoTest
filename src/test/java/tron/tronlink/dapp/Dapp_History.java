package tron.tronlink.dapp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class Dapp_History extends TronlinkBase {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;

    @Test(enabled = true)
    public void history() throws InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("address","TAVNk5hkaPNJcTf6TvJVgBWEaRhuiHE5Ab");
        response = TronlinkApiList.history(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseArrayContent = responseContent.getJSONArray("data");
        Assert.assertEquals(1,responseArrayContent.size());

        for (int n = 0; n < responseArrayContent.size(); n++){
            JSONObject history = responseArrayContent.getJSONObject(n);
            Assert.assertTrue(history.containsKey("name"));
            Assert.assertTrue(history.containsKey("slogan"));
            Assert.assertTrue(history.containsKey("createTime"));
            Assert.assertTrue(history.containsKey("updateTime"));

            if(200 != TronlinkApiList.createGetConnect(history.getString("image_url")).getStatusLine().getStatusCode()) {
                Thread.sleep(60000);
                Assert.assertEquals(200, TronlinkApiList.createGetConnect(history.getString("image_url")).getStatusLine().getStatusCode());
            }

            if(200 != TronlinkApiList.createGetConnect(history.getString("home_url")).getStatusLine().getStatusCode()) {
                Thread.sleep(60000);
                Assert.assertEquals(200, TronlinkApiList.createGetConnect(history.getString("home_url")).getStatusLine().getStatusCode());
            }

        }
    }

}

