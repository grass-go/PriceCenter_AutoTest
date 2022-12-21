package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

@Slf4j
public class multiTrxRecord {
  private HttpResponse response;
  private JSONObject responseContent;
  private HashMap<String, String> param = new HashMap<>();
  private HashMap<String, String> header = new HashMap<>();

  @Test(enabled = true, description = "with must needed parameter: low version no sig")
  public void multiTrxRecord0LowVersionWithNoSig() throws Exception {
    param.put("address", "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
    //param.put("address", "41e7d71e72ea48de9144dc2450e076415af0ea745f");
    param.put("netType", "main_net");
    param.put("start", "0");
    param.put("limit", "20");
    param.put("state", "455");
    param.put("serializable","true");
    //param.put("isSign", "false");
    response = TronlinkApiList.multiTrxRewordNoSig(param,null);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
  }


  @Test(enabled = true, description = "with must needed parameter: high version no sig")
  public void multiTrxRecord1HighVersionWithNoSig() throws Exception {
    List<String> testSystems = new ArrayList<>();
    testSystems.add("chrome-extension");
    testSystems.add("Chrome");
    testSystems.add("Firefox");
    testSystems.add("firefox-test");
    testSystems.add("Android");
    testSystems.add("AndroidTest");
    testSystems.add("iOS");
    testSystems.add("iOSTest");
    testSystems.add("chrome-extension-test");

    for (String system : testSystems) {
      param.put("address", "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
      param.put("netType", "main_net");
      param.put("state", "1");
      param.put("start", "0");
      param.put("limit", "20");
      header.put("System", system);
      header.put("Version", "4.11.0");
      response = TronlinkApiList.multiTrxRewordNoSig(param, header);
      Assert.assertEquals(200, response.getStatusLine().getStatusCode());
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      //Assert.assertEquals(20004, responseContent.getIntValue("code"));
      //Assert.assertEquals("Error param.", responseContent.getString("message"));
    }
  }

  @Test(enabled = true, description = "with must needed parameter: high version with sig")
  public void multiTrxRecord1() throws Exception {
    List<String> testSystems = new ArrayList<>();
    testSystems.add("chrome-extension-test");
    testSystems.add("chrome-extension");
    testSystems.add("Chrome");
    testSystems.add("Firefox");
    testSystems.add("firefox-test");
    testSystems.add("Android");
    testSystems.add("AndroidTest");
    testSystems.add("iOS");
    testSystems.add("iOSTest");

    for (String system : testSystems) {
      //param.put("address", "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
      param.put("address","TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo");
      param.put("netType", "main_net");
      param.put("state", "45");
      param.put("isSign", "all");
      param.put("start", "0");
      param.put("limit", "20");
      header.put("System", system);
      header.put("Version", "4.12.0");
      response = TronlinkApiList.multiTrxReword(param, header);
      Assert.assertEquals(200, response.getStatusLine().getStatusCode());
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

  }

  @Test(enabled = true, description = "with must needed parameter: high version with sig")
  public void multiTrxRecord1_hexAccount() throws Exception {
    List<String> testSystems = new ArrayList<>();
    /*testSystems.add("chrome-extension-test");
    testSystems.add("chrome-extension");
    testSystems.add("Chrome");
    testSystems.add("Firefox");
    testSystems.add("firefox-test");
    testSystems.add("Android");
    testSystems.add("AndroidTest");
    testSystems.add("iOS");*/
    testSystems.add("iOSTest");

    for (String system : testSystems) {
      param.put("address", "41e7d71e72ea48de9144dc2450e076415af0ea745f");
      //param.put("address", "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
      param.put("netType", "main_net");
      param.put("state", "3");
      param.put("isSign", "all");
      param.put("start", "0");
      param.put("limit", "20");
      header.put("System", system);
      header.put("Version", "4.12.0");
      response = TronlinkApiList.multiTrxReword(param, header);
      Assert.assertEquals(200, response.getStatusLine().getStatusCode());
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    }

  }





  @Test(enabled = false, description = "Api multiTrxReword test fail")
  public void multiTrxRecord2() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("start", "0");
    param.put("limit", "10");
    param.put("state", "2");
    param.put("netType", "main_net");

    int index;
    for (index = 0; index < 5; index++) {
      log.info("cur index is " + index);
      response = TronlinkApiList.multiTrxRewordNoSig(param,null);
      if (response.getStatusLine().getStatusCode() == 200) {
        index = 6;
      } else {
        continue;
      }
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      TronlinkApiList.printJsonObjectContent(responseContent);
      Assert.assertTrue(responseContent.size() >= 3);
      Assert.assertTrue(responseContent.getString("message").equals("OK"));

      JSONArray array = responseContent.getJSONObject("data").getJSONArray("data");
      for (Object signatureProgress : array) {
        JSONObject info = (JSONObject) signatureProgress;
        Assert.assertEquals("2", info.getString("state"));
        Assert.assertTrue(!info.getJSONArray("signatureProgress").isEmpty());
        Assert.assertTrue(!info.getString("originatorAddress").isEmpty());
        Assert.assertTrue(!info.getString("currentWeight").isEmpty());
        Assert.assertTrue(!info.getString("contractType").isEmpty());
        Assert.assertTrue(!info.getString("expireTime").isEmpty());
        Assert.assertTrue(!info.getString("threshold").isEmpty());
        Assert.assertTrue(!info.getString("isSign").isEmpty());
        Assert.assertTrue(!info.getString("state").isEmpty());
        Assert.assertTrue(!info.getString("hash").isEmpty());
      }
    }
    Assert.assertEquals(7,index);
  }

}

