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
public class getStartup extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  Map<String, String> headers = new HashMap<>();

  @Test(enabled = true)
  public void test01GetStartup(){
    params.put("height","1440");
    params.put("width","1440");

    headers.put("System","Android");
    headers.put("Version","3.7.5");

    response = TronlinkApiList.v2GetStartup(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertFalse(responseContent.getString("data").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("picType").isEmpty());
    Assert.assertFalse(responseContent.getJSONObject("data").getString("url").isEmpty());
  }


}
