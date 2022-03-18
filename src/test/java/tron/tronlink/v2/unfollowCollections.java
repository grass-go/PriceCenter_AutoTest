package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class unfollowCollections extends TronlinkBase{
    private JSONObject responseContent;
    private String responseString;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();


    @Test(enabled = false)
    public void unfollowCollections01(){
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","8nbFVyCw8O4F2wXO4Qy2jgtalM8%3D");
        params.put("address",addressNewAsset41);
        response = TronlinkApiList.V2UnfollowCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        dataContent = responseContent.getJSONObject("data");
        int count =dataContent.getIntValue("count");
        Assert.assertEquals(0,count);
        array = dataContent.getJSONArray("token");
        int tokenlen=array.size();
        Assert.assertEquals(0,tokenlen);

    }
}
