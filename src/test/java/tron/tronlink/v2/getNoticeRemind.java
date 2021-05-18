package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class getNoticeRemind extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  @Test(enabled = true)
  public void test01GetNoticeRemind(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");

    response = TronlinkApiList.v2GetNoticeRemind(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertTrue(responseContent.getString("data").isEmpty());

  }


  @Test(enabled = true)
  public void test02GetDappHistory(){
    JsonObject body = new JsonObject();
    body.addProperty("transactionString", "d75c536bb24bd5e5ae461afe00b70ee7df7d4395e1b10f181e1e7fcf2c1b2f67");
    body.addProperty("dappName","wink");
    body.addProperty("dappUrl","www.dappurl.com" );

    response = TronlinkApiList.v2GetDappHistory(body);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertTrue(responseContent.containsKey("data"));

  }

  @Test(enabled = true)
  public void test03GetAnnouncement(){
    response = TronlinkApiList.v2GetAnnouncement();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertTrue(responseContent.containsKey("data"));
    //Assert.assertTrue(responseContent.getJSONArray("data").getJSONObject(0).containsKey("pic_url"));

  }



}
