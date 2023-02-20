package tron.tronlink.v2.trc1155;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class getCollectionInfos extends TronlinkBase {
    private JSONObject responseContent;
    private JSONArray dataContent;
    private HttpResponse response;
    Map<String, String> params = new HashMap<>();
    JSONObject bodyObject = new JSONObject();
    List<String> assetIdList = new ArrayList<>();

    public String expFollowAndHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowAndHold");

    @BeforeMethod(alwaysRun = true)
    void setUp(){
        params.put("address",address721_B58);

        bodyObject.clear();
        assetIdList.clear();
    }


    @Test(enabled = true, groups={"P2"})
    public void getCollectionInfosTest001() {
        assetIdList.add("0");
        assetIdList.add("1");
        assetIdList.add("2");
        bodyObject.put("assetIdList",assetIdList);
        bodyObject.put("tokenAddress", expFollowAndHold);
        response = TronlinkApiList.v2GetCollectionInfos_1155(params, bodyObject);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        Assert.assertEquals(0,(int)responseContent.get("code"));
        Assert.assertEquals("OK",responseContent.get("message"));
        dataContent = responseContent.getJSONArray("data");

        for (int n = 0; n<dataContent.size(); n++){
            JSONObject trc1155Info = (JSONObject)dataContent.get(n);
            //Assert.assertEquals("TPvGT3tWUNakTg23ARKMx46MGLT386nYWD", trc1155Info.get("tokenAddress"));
            Assert.assertTrue(trc1155Info.containsKey("tokenAddress"));
            Assert.assertTrue(trc1155Info.containsKey("assetId"));
            Assert.assertTrue(trc1155Info.containsKey("name"));
            Assert.assertTrue(trc1155Info.containsKey("intro"));
            Assert.assertTrue(trc1155Info.containsKey("imageUrl"));
            Assert.assertTrue(trc1155Info.containsKey("assetUri"));
            Assert.assertTrue(trc1155Info.containsKey("decimals"));
            Assert.assertTrue(trc1155Info.containsKey("totalSupply"));
            Assert.assertTrue(trc1155Info.containsKey("balance"));
        }

    }
}
