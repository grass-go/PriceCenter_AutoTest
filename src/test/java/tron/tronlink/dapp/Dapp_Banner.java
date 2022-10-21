package tron.tronlink.dapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class Dapp_Banner extends TronlinkBase {

    private JSONObject responseContent;
    private HttpResponse response;
    private Map<String, String> header = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
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

    @Test(enabled = true, description = "Android access banner")
    public void Test000GetDappBannerV3_Android(){
        header.clear();
        header.put("System","Android");
        header.put("Lang","1");
        header.put("Version","4.12.0");
        params.put("address", quince_B58);
        response = TronlinkApiList.dappBannerV3(params,header);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        JSONArray jsonArray = responseContent.getJSONArray("data");
        Assert.assertTrue(jsonArray.size() ==4 );
        for (Object json:jsonArray) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(jsonObject.containsKey("title"));
            Assert.assertTrue(jsonObject.containsKey("subTitle"));
            Assert.assertTrue(jsonObject.containsKey("homeUrl"));
            Assert.assertTrue(jsonObject.containsKey("imageUrl"));
            Assert.assertTrue(jsonObject.containsKey("backgroundColor"));
            Assert.assertTrue(jsonObject.containsKey("anonymous"));
        }
    }

    @Test(enabled = true, description = "iOS access banner")
    public void Test000GetDappBannerV3_iOS(){
        header.clear();
        header.put("System","iOS");
        header.put("Lang","1");
        header.put("Version","4.12.0");
        params.put("address", quince_B58);
        response = TronlinkApiList.dappBannerV3(params,header);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        JSONArray jsonArray = responseContent.getJSONArray("data");
        Assert.assertTrue(jsonArray.size() ==5);
        for (Object json:jsonArray) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(jsonObject.containsKey("title"));
            Assert.assertTrue(jsonObject.containsKey("subTitle"));
            Assert.assertTrue(jsonObject.containsKey("homeUrl"));
            Assert.assertTrue(jsonObject.containsKey("imageUrl"));
            Assert.assertTrue(jsonObject.containsKey("backgroundColor"));
            Assert.assertTrue(jsonObject.containsKey("anonymous"));
        }
    }

}
