package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

public class DappToMainFee {
  private JSONObject responseContent;
  private JSONObject data;

  private HttpResponse response;

  @Test(enabled = true,description = "get only trx transaction")
  public void Test000dappToMainFee(){
    response = TronlinkApiList.dappToMainFee();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    data = responseContent.getJSONObject("data");
    Assert.assertEquals(0, data.getIntValue("dappToMainFee"));
  }
}
