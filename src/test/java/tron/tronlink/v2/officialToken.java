package tron.tronlink.v2;

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

  @Test(enabled = true)
  public void testOfficialToken(){

    response = TronlinkApiList.officialToken(null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    //JSONObject screenInfo = responseContent.getJSONArray("data").getJSONObject(0);
    Assert.assertTrue(responseContent.getJSONArray("data").contains("_"));
    Assert.assertTrue(responseContent.getJSONArray("data").contains("1002000"));
    Assert.assertTrue(responseContent.getJSONArray("data").contains("TB95FFYRJMLY6mWZqv4JUMqAqsHF4JCXga"));
    Assert.assertTrue(5<responseContent.getJSONArray("data").size() );
  }


}
