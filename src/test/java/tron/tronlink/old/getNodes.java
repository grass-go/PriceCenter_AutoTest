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

  @Test(enabled = true)
  public void test01GetNodes(){

    response = TronlinkApiList.v1GetNodes(null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("msg"),"success");
    JSONObject nodeInfo = responseContent.getJSONObject("data");
    Assert.assertTrue(nodeInfo.getJSONArray("fullnode").size() >= 3);
    Assert.assertTrue(nodeInfo.getJSONArray("solidity_node").size() >= 3);
    Assert.assertFalse(responseContent.containsKey("upgrade"));
    Assert.assertFalse(responseContent.containsKey("force"));

  }


}
