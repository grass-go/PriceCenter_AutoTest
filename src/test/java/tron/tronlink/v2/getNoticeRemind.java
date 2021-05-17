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


}
