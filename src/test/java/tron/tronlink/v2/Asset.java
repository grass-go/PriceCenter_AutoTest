package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Asset extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject object;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  @Test(enabled = true)
  public void asset01(){
    params.put("address",addressNewAsset41);
    params.put("tokenAddress","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("tokenType","2");
    response = TronlinkApiList.v2Asset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",dataContent.getString("contractAddress"));
    Assert.assertEquals(2,dataContent.getIntValue("type"));
    Assert.assertEquals(0,dataContent.getIntValue("top"));
    Assert.assertEquals(0,dataContent.getIntValue("recommandSortId"));

  }



}
