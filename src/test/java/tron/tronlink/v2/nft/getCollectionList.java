package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class getCollectionList extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();

  @Test
  public void getCollectionListTest001() {
    params.put("address",addressNewAsset);
    params.put("tokenAddress","TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG");
    params.put("pageIndex","0");
    params.put("pageSize","10");

    response = TronlinkApiList.v2GetCollectionList(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONObject("data");

    Assert.assertEquals("TCzUYnFSwtH2bJkynGB46tWxWjdTQqL1SG", dataContent.getString("tokenAddress"));
    Assert.assertNotEquals("", dataContent.getString("intro"));
    Assert.assertNotEquals("", dataContent.getString("logoUrl"));
    Assert.assertNotEquals("", dataContent.getString("fullName"));
    Assert.assertTrue(dataContent.containsKey("dappUrl"));
    Assert.assertNotNull(dataContent.containsKey("collectionInfoList"));

    Assert.assertEquals(200, TronlinkApiList.createGetConnect(dataContent.getString("logoUrl"),null,null,null).getStatusLine().getStatusCode());

  }
}
