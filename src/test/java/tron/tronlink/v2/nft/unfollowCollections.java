package tron.tronlink.v2.nft;

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


    @Test(enabled = true)
    public void unfollowCollections01(){
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","YzJifnURxQw%2F0BcoYANZcdLk8TE%3D");
        params.put("address",address721_B58);
        response = TronlinkApiList.V2UnfollowCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        dataContent = responseContent.getJSONObject("data");
        int count =dataContent.getIntValue("count");
        Assert.assertEquals(1,count);
        array = dataContent.getJSONArray("token");
        int tokenlen=array.size();
        Assert.assertEquals(1,tokenlen);
        JSONObject first = array.getJSONObject(0);
        Assert.assertEquals("TCpctLh4QoYrLiWnDNMg1Q3HMnsfpNyxCf", first.getString("id"));
        Assert.assertEquals(1, first.getIntValue("count"));
        Assert.assertEquals("TNFT", first.getString("shortName"));
    }
}
