package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class getCollectionInfos extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();
  JSONObject bodyObject = new JSONObject();
  List<String> trc10tokenList = new ArrayList<>();

  @BeforeMethod
  void setUp(){
    params.put("address",quince_B58);

    bodyObject.clear();
    trc10tokenList.clear();
  }


  @Test
  public void getCollectionInfosTest001() {
    //trc10tokenList.add("10000001");
    //trc10tokenList.add("100000");
    trc10tokenList.add("10000004");
    //trc10tokenList.add("10000003");
    //trc10tokenList.add("10000004");
    //trc10tokenList.add("10000005");
    bodyObject.put("assetIdList",trc10tokenList);
    //bodyObject.put("tokenAddress", "TUVGZFjjAhkYitwQmveGoCt7W4yNzbN5dY");
    bodyObject.put("tokenAddress", "TCaL5uzxWD7unW6NWw8bDGhNsfWbMVXNj2");
    response = TronlinkApiList.v2GetCollectionInfos(params, bodyObject);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONArray("data");

  }
}
