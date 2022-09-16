package tron.tronlink.v2.trc1155;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class getCollectionList  extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject dataContent;
    private HttpResponse response;
    Map<String, String> params = new HashMap<>();

    public String expFollowAndHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowAndHold");

    public String expFollowAndNoHold = Configuration.getByPath("testng.conf").getString("tronlink.trc1155FollowButNoHold");

    @Test
    public void getCollectionListTest001() {
        params.put("address",address721_B58);
        params.put("tokenAddress",expFollowAndHold);
        params.put("pageIndex","0");
        params.put("pageSize","10");

        response = TronlinkApiList.v2GetCollectionList_1155(params);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        Assert.assertEquals(0,(int)responseContent.get("code"));
        Assert.assertEquals("OK",responseContent.get("message"));
        dataContent = responseContent.getJSONObject("data");

        //Assert.assertEquals("TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG", dataContent.getString("tokenAddress"));
        Assert.assertTrue(dataContent.containsKey("tokenAddress"));
        Assert.assertTrue(dataContent.containsKey("intro"));
        Assert.assertTrue(dataContent.containsKey("logoUrl"));
        Assert.assertTrue(dataContent.containsKey("fullName"));
        Assert.assertTrue(dataContent.containsKey("collectionInfoList"));

        JSONArray collectionInfoList = dataContent.getJSONArray("collectionInfoList");
        JSONObject firstCollectionInfo = collectionInfoList.getJSONObject(0);
        Assert.assertTrue(firstCollectionInfo.containsKey("tokenAddress"));
        Assert.assertTrue(firstCollectionInfo.containsKey("assetId"));
        Assert.assertTrue(firstCollectionInfo.containsKey("name"));
        Assert.assertTrue(firstCollectionInfo.containsKey("intro"));
        Assert.assertTrue(firstCollectionInfo.containsKey("imageUrl"));
        Assert.assertTrue(firstCollectionInfo.containsKey("assetUri"));
        Assert.assertTrue(firstCollectionInfo.containsKey("decimals"));
        Assert.assertTrue(firstCollectionInfo.containsKey("totalSupply"));
        Assert.assertTrue(firstCollectionInfo.containsKey("balance"));
    }
}
