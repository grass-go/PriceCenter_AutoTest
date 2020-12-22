package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class GetConfig extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject data;
  private HttpResponse response;


  @Test(enabled = true)
  public void test000getConfig(){
    response = TronlinkApiList.getConfig();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    data = responseContent.getJSONObject("data");
    Assert.assertTrue(data.containsKey("tronscanUrl"));
    Assert.assertTrue(data.getString("tronscanUrl") != null);
    Assert.assertTrue(data.containsKey("tronscanDappChain"));
    Assert.assertTrue(data.getString("tronscanDappChain") != null);
    Assert.assertTrue(data.containsKey("usingCrtFile2020"));
  }
}
