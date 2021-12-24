package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;

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

  @Test(enabled = false, description = "Api multiTrxReword test")
  public void multiTrxRecord0() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("start", "0");
    param.put("limit", "10");
    param.put("state", "0");
    param.put("netType", "main_net");
    response = TronlinkApiList.multiTrxReword(param);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    TronlinkApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() >= 3);
    Assert.assertTrue(responseContent.getString("message").equals("OK"));

    JSONArray array = responseContent.getJSONObject("data").getJSONArray("data");
    for (Object signatureProgress : array) {
      JSONObject info = (JSONObject) signatureProgress;
      Assert.assertEquals("0", info.getString("state"));
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

  @Test(enabled = false, description = "Api multiTrxReword test success")
  public void multiTrxRecord1() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("start", "0");
    param.put("limit", "10");
    param.put("state", "1");
    param.put("netType", "main_net");

    int index;
    for(index=0; index<5;index++){
      log.info("cur index is " + index);
      response = TronlinkApiList.multiTrxReword(param);
      if(response.getStatusLine().getStatusCode()==200)
      {
        index=6;
      }
      else{
        continue;
      }
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      TronlinkApiList.printJsonContent(responseContent);
      Assert.assertTrue(responseContent.size() >= 3);
      Assert.assertTrue(responseContent.getString("message").equals("OK"));

      JSONArray array = responseContent.getJSONObject("data").getJSONArray("data");
      for (Object signatureProgress : array) {
        JSONObject info = (JSONObject) signatureProgress;
        Assert.assertEquals("1", info.getString("state"));
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
    Assert.assertEquals(7, index);

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
      response = TronlinkApiList.multiTrxReword(param);
      if (response.getStatusLine().getStatusCode() == 200) {
        index = 6;
      } else {
        continue;
      }
      responseContent = TronlinkApiList.parseJsonObResponseContent(response);
      TronlinkApiList.printJsonContent(responseContent);
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

