package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class community extends TronlinkBase {
  private HttpResponse response;

  @Test(enabled = true, description = "Api GET /api/v1/wallet/community test")
  public void test001Community() throws Exception {
    response = api.community();

    JSONObject jsonObject = api.parseResponse2JsonObject(response);
    api.printJsonObjectContent(jsonObject);

    JSONObject data = jsonObject.getJSONObject("data");

    Assert.assertTrue(!data.getString("twitter").isEmpty());
    Assert.assertTrue(!data.getString("WeChat").isEmpty());
    Assert.assertTrue(!data.getString("telegram_en").isEmpty());
    Assert.assertTrue(!data.getString("telegram_cn").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");
  }

}