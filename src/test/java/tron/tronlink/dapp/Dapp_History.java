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
    public void history() {
        Map<String, String> params = new HashMap<>();
        params.put("address","TAVNk5hkaPNJcTf6TvJVgBWEaRhuiHE5Ab");
        response = TronlinkApiList.history(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseArrayContent = responseContent.getJSONArray("data");
        Assert.assertEquals(1,responseArrayContent.size());
    }

}
