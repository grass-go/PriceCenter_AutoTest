package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;

public class FailTransfer extends TronlinkBase {

  private JSONObject params = new JSONObject();
  private HttpResponse response;


  @Test(enabled = false)
  public void test00failTransfer(){
    Long time = System.currentTimeMillis();
    params.put("failLog","cxcz-"+time);
    params.put("ownerAddress","cxcz");
    params.put("status","cxcz");
    params.put("transaction","dsda");

    response = TronlinkApiList.failTransfer(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());



  }
}
