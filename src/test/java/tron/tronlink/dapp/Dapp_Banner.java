package tron.tronlink.dapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class Dapp_Banner extends TronlinkBase {

    private JSONObject responseContent;
    private HttpResponse response;

    @Test(enabled = true)
    public void Test000GetDappBanner(){

        response = TronlinkApiList.dappBanner();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        JSONArray jsonArray = responseContent.getJSONArray("data");
        Assert.assertTrue(jsonArray.size() ==0);
        for (Object json:jsonArray) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(jsonObject.containsKey("id"));
            Assert.assertTrue(jsonObject.containsKey("name"));
            Assert.assertTrue(jsonObject.containsKey("image_url"));
            Assert.assertTrue(jsonObject.containsKey("home_url"));
            Assert.assertTrue(jsonObject.containsKey("content_type"));
            Assert.assertTrue(jsonObject.containsKey("content"));
        }
    }

}
