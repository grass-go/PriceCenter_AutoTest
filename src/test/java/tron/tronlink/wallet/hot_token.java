package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import org.tron.common.crypto.Hash;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class hot_token extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private JSONObject jsonObject;
  private HttpResponse response;
  private String node =
      Configuration.getByPath("testng.conf").getStringList("tronlink.ip.list").get(0);

  @Test(
      enabled = true,
      description = "hot_token test lower version with no sig",
      groups = {"NoSignature"})
  public void hot_tokenV1LowVersionWithNoSig() {
    response = TronlinkApiList.hot_tokenV1NoSig(quince_B58, null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(targetContent.containsKey("totalTRX"));
    Assert.assertTrue(targetContent.containsKey("token"));
    jsonObject = targetContent.getJSONObject("price");
    Assert.assertTrue(jsonObject.containsKey("priceCny"));
    Assert.assertTrue(jsonObject.containsKey("priceUSD"));
  }

  @Test(enabled = true, description = "hot_token test high version with no sig")
  public void hot_tokenV1HighVersionWithNoSig() {
    HashMap<String, String> header = new HashMap<>();
    header.put("Version", TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);

    response = TronlinkApiList.hot_tokenV1NoSig(quince_B58, header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true, description = "hot_token test high version with sig")
  public void hot_tokenV1HighVersionWithSig() {
    HashMap<String, String> params = new HashMap<>();
    params.put("address", quince_B58);
    response = TronlinkApiList.hot_tokenV1(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    targetContent = responseContent.getJSONObject("data");
    Assert.assertTrue(targetContent.containsKey("totalTRX"));
    Assert.assertTrue(targetContent.containsKey("token"));
    jsonObject = targetContent.getJSONObject("price");
    Assert.assertTrue(jsonObject.containsKey("priceCny"));
    Assert.assertTrue(jsonObject.containsKey("priceUSD"));
  }

  @Test(enabled = true, description = "hot_token national token with nationa field=DM")
  public void hot_token_national_file() {
    HashMap<String, String> params = new HashMap<>();
    params.put("address", quince_B58);
    response = TronlinkApiList.hot_tokenV2(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
  }

  @Test(enabled = true, description = "hot_token national ")
  public void hot_token_test_totalBalanceStr() {
    HashMap<String, String> params = new HashMap<>();
    params.put("address", quince_B58);
    response = TronlinkApiList.hot_tokenV2(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONArray jSONArray = responseContent.getJSONArray("data");
    boolean flag = true;
    for (int i = 0; i < jSONArray.size(); i++) {
      JSONObject jSONObject = jSONArray.getJSONObject(i);
      if (!jSONObject.getString("totalBalanceStr").equals(jSONObject.getString("totalBalance"))) {
        log.info("jSONObject:" + i + jSONObject);
        flag = false;
      }
    }
    Assert.assertTrue(flag);
  }
}
