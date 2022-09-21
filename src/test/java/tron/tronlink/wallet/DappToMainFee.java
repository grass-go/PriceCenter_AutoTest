package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DappToMainFee {
  private JSONObject responseContent;
  private JSONObject data;

  private HttpResponse response;

  @Test(enabled = true,description = "get only trx transaction")
  public void Test000dappToMainFee() throws InterruptedException {
    int index;
    for(index=0; index<5; index++){
      log.info("Test000dappToMainFee cur index is " + index);
      response = TronlinkApiList.dappToMainFee();
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
