package tron.tronlink.nilex;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

/*
public class NilexAssetlist extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private Map<String,String> header = new HashMap<>();

  @Test(enabled = false, description= "Get Assetlist From MainChain")
  public void test000GetAssetlistFromMainChain(){
    header.put("Lang","1");
    header.put("Version","3.7.0");
    header.put("DeviceID","1111111111");
    header.put("chain","MainChain");
    header.put("packageName","com.tronlinkpro.wallet");
    header.put("System","Android");
    response = TronlinkApiList.nilexGetAssetlist(queryAddress,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(targetContent.containsKey("totalTRX"));
    Assert.assertTrue(targetContent.getDoubleValue("totalTRX") > 0);
  }

  @Test(enabled = false, description = "Get Assetlist From DappChain")
  public void test001GetAssetlistFromDappChain(){
    header.put("Lang","1");
    header.put("Version","3.7.0");
    header.put("DeviceID","1111111111");
    header.put("chain","DAppChain");
    header.put("packageName","com.tronlinkpro.wallet");
    header.put("System","Android");
    response = TronlinkApiList.nilexGetAssetlist(queryAddress,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(targetContent.containsKey("totalTRX"));
  }




  
}
*/
