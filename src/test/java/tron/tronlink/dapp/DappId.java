package tron.tronlink.dapp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;
import java.util.Map;

/**
 * 现在接口不通
 */
public class DappId {

    private JSONObject responseContent;
    private HttpResponse response;

    @Test(enabled = false)
    public void Test000GetDappById(){

        Map<String, String> params = new HashMap<>();
        params.put("dapp_ids","171");
        response = TronlinkApiList.dappId(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        JSONArray jsonArray = responseContent.getJSONArray("data");
        Assert.assertTrue(jsonArray.size() >0);
        for (Object json:jsonArray) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(jsonObject.containsKey("id"));
        }
    }

}
