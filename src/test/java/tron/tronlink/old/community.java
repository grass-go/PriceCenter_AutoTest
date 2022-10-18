package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class community extends TronlinkBase {
  private HttpResponse response;

  @Test(enabled = true, description = "Api GET /api/v1/wallet/community test", groups = {"NoSignature"})
  public void CommunityLowVersionWithNoSig() throws Exception {
    response = api.communityNoSig(null);
    JSONObject jsonObject = api.parseResponse2JsonObject(response);
    api.printJsonObjectContent(jsonObject);
    JSONObject data = jsonObject.getJSONObject("data");
    Assert.assertTrue(!data.getString("twitter").isEmpty());
    Assert.assertTrue(!data.getString("WeChat").isEmpty());
    Assert.assertTrue(!data.getString("telegram_en").isEmpty());
    Assert.assertTrue(!data.getString("telegram_cn").isEmpty());
    Assert.assertEquals(jsonObject.getString("msg"),"success");
  }

  @Test(enabled = true, description = "Api GET /api/v1/wallet/community test")
  public void test001CommunityHighVersionWithNoSig() throws Exception {
    HashMap<String, String> header = new HashMap<>();
    header.put("System","Android");
    header.put("Version","4.11.0");
    response = api.communityNoSig(header);
    JSONObject jsonObject = api.parseResponse2JsonObject(response);
    org.junit.Assert.assertEquals(20004, jsonObject.getIntValue("code"));
    org.junit.Assert.assertEquals("Error param.", jsonObject.getString("message"));
  }

  @Test(enabled = true, description = "Api GET /api/v1/wallet/community test")
  public void test001Community() throws Exception {
    HashMap<String, String> params = new HashMap<>();
    params.put("address",quince_B58);
    response = api.community(params);

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