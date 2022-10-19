package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;

public class GetConfig extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject data;
  private HttpResponse response;


  @Test(enabled = true,groups = {"NoSignature"})
  public void getConfigLowVersionWithNoSig(){
    response = TronlinkApiList.getConfigNoSig(null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    data = responseContent.getJSONObject("data");
    Assert.assertTrue(data.containsKey("tronscanUrl"));
    Assert.assertTrue(data.getString("tronscanUrl") != null);
    Assert.assertTrue(data.containsKey("tronscanDappChain"));
    Assert.assertTrue(data.getString("tronscanDappChain") != null);
    Assert.assertTrue(data.containsKey("usingCrtFile2020"));
  }

  @Test(enabled = true)
  public void getConfigHighVersionWithNoSig(){
    HashMap<String,String> header = new HashMap();
    header.put("System","Android");
    header.put("Version","4.11.0");
    response = TronlinkApiList.getConfig(header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    org.junit.Assert.assertEquals(20004, responseContent.getIntValue("code"));
    org.junit.Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true)
  public void test000getConfig(){
    HashMap<String,String> params = new HashMap();
    params.put("address",quince_B58);
    response = TronlinkApiList.getConfig(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    data = responseContent.getJSONObject("data");
    Assert.assertTrue(data.containsKey("tronscanUrl"));
    Assert.assertTrue(data.getString("tronscanUrl") != null);
    Assert.assertTrue(data.containsKey("tronscanDappChain"));
    Assert.assertTrue(data.getString("tronscanDappChain") != null);
    Assert.assertTrue(data.containsKey("usingCrtFile2020"));
  }

}
