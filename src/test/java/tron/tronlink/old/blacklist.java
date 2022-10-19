package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
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
public class blacklist extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  Map<String, String> headers = new HashMap<>();

  //no need signature

  @Test(enabled = true,groups = {"NoSignature"})
  public void test01GetNodesLowVersionWithNoSig(){
    response = TronlinkApiList.v2GetBlacklistNoSig(null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    JSONArray blacklistInfo = responseContent.getJSONArray("data");

    Assert.assertTrue(blacklistInfo.size() >= 1);
  }


  @Test(enabled = true)
  public void test01GetNodesHighVersionWithNoSig(){
    headers.put("System","Android");
    headers.put("Version","4.12.0");
    response = TronlinkApiList.v2GetBlacklistNoSig(headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }



  @Test(enabled = true)
  public void test01GetNodes(){
    params.put("address",quince_B58);
    headers.put("System","Android");
    headers.put("Version","4.12.0");
    response = TronlinkApiList.v2GetBlacklist(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    JSONArray blacklistInfo = responseContent.getJSONArray("data");

    Assert.assertTrue(blacklistInfo.size() >= 1);
  }


}
