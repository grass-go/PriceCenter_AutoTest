package tron.tronlink.old;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class getNodes extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  Map<String, String> headers = new HashMap<>();


  @Test(enabled = true, groups = {"NoSignature"})
  public void test01GetNodesLowVersionWithNoSignature(){
    response = TronlinkApiList.v1GetNodesNoSig(null,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    JSONObject nodeInfo = responseContent.getJSONObject("data");
    Assert.assertTrue(nodeInfo.getJSONArray("fullnode").size() >= 3);
    Assert.assertTrue(nodeInfo.getJSONArray("solidity_node").size() >= 3);
    Assert.assertFalse(responseContent.containsKey("upgrade"));
    Assert.assertFalse(responseContent.containsKey("force"));

  }

  @Test(enabled = true)
  public void test01GetNodesHighVersionWithNoSignature(){
    headers.put("System","Android");
    headers.put("Version","4.12.0");
    response = TronlinkApiList.v1GetNodesNoSig(null,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true)
  public void test01GetNodes(){
    params.put("address",quince_B58);
    response = TronlinkApiList.v1GetNodes(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    JSONObject nodeInfo = responseContent.getJSONObject("data");
    Assert.assertTrue(nodeInfo.getJSONArray("fullnode").size() >= 3);
    Assert.assertTrue(nodeInfo.getJSONArray("solidity_node").size() >= 3);
    Assert.assertFalse(responseContent.containsKey("upgrade"));
    Assert.assertFalse(responseContent.containsKey("force"));

  }


}
