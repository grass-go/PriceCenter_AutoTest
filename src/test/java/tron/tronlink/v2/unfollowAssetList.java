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

public class unfollowAssetList extends TronlinkBase{
    private JSONObject responseContent;
    private String responseString;
    private JSONObject object;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    Map<String, String> params = new HashMap<>();


    @Test(enabled = false)
    public void unfollowAssetList01(){
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","wZG2VUWX2bl4PN9ubRZGD8TR2C8%3D");
        params.put("address",addressNewAsset41);
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        dataContent = responseContent.getJSONObject("data");
        int count =dataContent.getIntValue("count");
        Assert.assertEquals(4,count);
        array = dataContent.getJSONArray("token");
        int tokenlen=array.size();
        Assert.assertEquals(4,tokenlen);
    }
}
