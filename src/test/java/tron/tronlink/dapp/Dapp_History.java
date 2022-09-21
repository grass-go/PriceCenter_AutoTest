package tron.tronlink.dapp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Dapp_History extends TronlinkBase {

    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private JSONObject jsonObject = new JSONObject();

    @Test(enabled = true)
    public void history() throws InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("address","TAVNk5hkaPNJcTf6TvJVgBWEaRhuiHE5Ab");
        response = TronlinkApiList.history(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        responseArrayContent = responseContent.getJSONArray("data");
        Assert.assertEquals(1,responseArrayContent.size());

        for (int n = 0; n < responseArrayContent.size(); n++){
            JSONObject history = responseArrayContent.getJSONObject(n);
            Assert.assertTrue(history.containsKey("name"));
            Assert.assertTrue(history.containsKey("slogan"));
            Assert.assertTrue(history.containsKey("createTime"));
            Assert.assertTrue(history.containsKey("updateTime"));

            if(200 != TronlinkApiList.createGetConnect(history.getString("image_url"),null,null,null).getStatusLine().getStatusCode()) {
                Thread.sleep(60000);
                Assert.assertEquals(200, TronlinkApiList.createGetConnect(history.getString("image_url"),null,null,null).getStatusLine().getStatusCode());
            }

            if(200 != TronlinkApiList.createGetConnect(history.getString("home_url"),null,null,null).getStatusLine().getStatusCode()) {
                Thread.sleep(60000);
                Assert.assertEquals(200, TronlinkApiList.createGetConnect(history.getString("home_url"),null,null,null).getStatusLine().getStatusCode());
            }

        }
    }

    @Test(enabled = true)
    public void put_get_delete_history() throws InterruptedException {
        Map<String, String> params = new HashMap<>();
        params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        response = TronlinkApiList.history(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        responseArrayContent = responseContent.getJSONArray("data");
        Assert.assertEquals(1,responseArrayContent.size());
        JSONObject history = responseArrayContent.getJSONObject(0);
        int id = history.getIntValue("id");
        Assert.assertEquals(405, id);
        String name = history.getString("name");
        Assert.assertEquals("APENFT", name);

        log.info("delete history... ...");
        jsonObject.clear();
        jsonObject.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        jsonObject.put("dapp_ids","405");
        response = TronlinkApiList.delete_history(jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals("SUCCESS", responseContent.getString("data"));


        log.info("get history to check delete!");
        response = TronlinkApiList.history(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertNull(responseContent.getString("data"));

        log.info("put history... ...");
        jsonObject.clear();
        jsonObject.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
        jsonObject.put("dapp_id","405");
        jsonObject.put("dapp_name","APENFT");
        response = TronlinkApiList.put_history(jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals("SUCCESS", responseContent.getString("data"));

        log.info("get history to check put!");
        response = TronlinkApiList.history(params);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        responseArrayContent = responseContent.getJSONArray("data");
        Assert.assertEquals(1,responseArrayContent.size());
        history = responseArrayContent.getJSONObject(0);
        id = history.getIntValue("id");
        Assert.assertEquals(405, id);
        name = history.getString("name");
        Assert.assertEquals("APENFT", name);

    }


}

