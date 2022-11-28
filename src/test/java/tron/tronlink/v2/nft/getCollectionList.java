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
    params.put("address",quince_B58);
    //params.put("tokenAddress","TU4zZAaKMdNGX4gwDhP3yz1zXZ5Z9UezxL");
    //params.put("tokenAddress","TUVGZFjjAhkYitwQmveGoCt7W4yNzbN5dY");
    params.put("tokenAddress","TCaL5uzxWD7unW6NWw8bDGhNsfWbMVXNj2");
    params.put("pageIndex","0");
    params.put("pageSize","10");

    response = TronlinkApiList.v2GetCollectionList(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONObject("data");


  }
}
