package tron.tronlink.v2;

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
public class playScreen extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  Map<String, String> headers = new HashMap<>();

  @Test(enabled = true)
  public void test01PlayScreenInfo(){

    response = TronlinkApiList.v2PlayScreenInfo(null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    JSONObject screenInfo = responseContent.getJSONArray("data").getJSONObject(0);

    Assert.assertTrue(screenInfo.containsKey("playId"));
  }


  @Test(enabled = true)
  public void test02PlayScreenDeal(){

    Integer playId = 3;
    response = TronlinkApiList.v2PlayScreenDeal(String.valueOf(playId));
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    JSONObject screenInfo = responseContent.getJSONArray("data").getJSONObject(0);

    Assert.assertTrue(screenInfo.containsKey("playId"));
  }


}
