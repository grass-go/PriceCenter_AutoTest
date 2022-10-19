package tron.tronlink.old;

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
public class officialToken extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  HashMap<String, String> headers = new HashMap<>();

  @Test(enabled = true,groups = {"NoSignature"})
  public void testOfficialTokenLowVersionWithNoSig(){
    response = TronlinkApiList.officialTokenNoSig(null,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    //JSONObject screenInfo = responseContent.getJSONArray("data").getJSONObject(0);
    Assert.assertTrue(responseContent.getJSONArray("data").contains("_"));
    Assert.assertTrue(responseContent.getJSONArray("data").contains("1002000"));
    Assert.assertTrue(responseContent.getJSONArray("data").contains("TB95FFYRJMLY6mWZqv4JUMqAqsHF4JCXga"));
    Assert.assertTrue(5<responseContent.getJSONArray("data").size() );
  }

  @Test(enabled = true)
  public void testOfficialTokenHighVersionWithNoSig(){
    headers.put("System", "Android");
    headers.put("Version", "4.11.0");
    response = TronlinkApiList.officialTokenNoSig(null,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true)
  public void testOfficialToken(){
    params.put("address",quince_B58);
    headers.put("System", "Android");
    headers.put("Version", "4.11.0");
    response = TronlinkApiList.officialToken(params,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    //JSONObject screenInfo = responseContent.getJSONArray("data").getJSONObject(0);
    Assert.assertTrue(responseContent.getJSONArray("data").contains("_"));
    Assert.assertTrue(responseContent.getJSONArray("data").contains("1002000"));
    Assert.assertTrue(responseContent.getJSONArray("data").contains("TB95FFYRJMLY6mWZqv4JUMqAqsHF4JCXga"));
    Assert.assertTrue(5<responseContent.getJSONArray("data").size() );
  }

}
