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
public class playScreen extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  HashMap<String, String> headers = new HashMap<>();

  @Test(enabled = true)
  public void test01PlayScreenInfo(){

    response = TronlinkApiList.v1PlayScreenInfo(null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
  }


  @Test(enabled = true)
  public void test02PlayScreenDeal(){
    headers.put("DeviceID","hhkhkjhkj887");
    headers.put("System", "Android");
    headers.put("playId", String.valueOf("1"));
    headers.put("Lang", "1");
    headers.put("Version", String.valueOf("4.0.1"));
    headers.put("chain","MainChain" );
    headers.put("packageName","com.tronlinkpro.wallet" );

    Integer playId = 11;
    response = TronlinkApiList.v1PlayScreenDeal(String.valueOf(playId),headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    //JSONObject screenInfo = responseContent.getJSONArray("data").getJSONObject(0);

    //Assert.assertTrue(screenInfo.containsKey("playId"));
  }


}
