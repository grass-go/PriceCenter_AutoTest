package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import lombok.extern.slf4j.Slf4j;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DappToMainFee extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject data;

  private HttpResponse response;

  @Test(enabled = true,description = "get only trx transaction", groups = {"NoSignature"})
  public void Test000dappToMainFeeLowVersionWithNoSig() throws InterruptedException {
    int index;
    for(index=0; index<5; index++){
      log.info("Test000dappToMainFee cur index is " + index);
      response = TronlinkApiList.dappToMainFeeNoSig(null);
      if(response.getStatusLine().getStatusCode() == 200)
      {
        index = 6;
      }
      else {
        Thread.sleep(1000);
        continue;
      }
    }
    Assert.assertEquals(7,index);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    data = responseContent.getJSONObject("data");
    Assert.assertEquals(0, data.getIntValue("dappToMainFee"));
  }

  @Test(enabled = true,description = "get only trx transaction")
  public void Test000dappToMainFeeHighVersionWithNoSig() throws InterruptedException {
    int index;
    for(index=0; index<5; index++){
      log.info("Test000dappToMainFee cur index is " + index);
      HashMap<String, String> headers = new HashMap<>();
      headers.put("Version","4.12.0");
      response = TronlinkApiList.dappToMainFee(headers);
      if(response.getStatusLine().getStatusCode() == 200)
      {
        index = 6;
      }
      else {
        Thread.sleep(1000);
        continue;
      }
    }
    Assert.assertEquals(7,index);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true,description = "get only trx transaction")
  public void Test000dappToMainFee() throws InterruptedException {
    int index;
    for(index=0; index<5; index++){
      log.info("Test000dappToMainFee cur index is " + index);
      HashMap<String, String> params = new HashMap<>();
      params.put("address",quince_B58);
      response = TronlinkApiList.dappToMainFee(params);
      if(response.getStatusLine().getStatusCode() == 200)
      {
        index = 6;
      }
      else {
        Thread.sleep(1000);
        continue;
      }
    }
    Assert.assertEquals(7,index);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    data = responseContent.getJSONObject("data");
    Assert.assertEquals(0, data.getIntValue("dappToMainFee"));
  }
}
