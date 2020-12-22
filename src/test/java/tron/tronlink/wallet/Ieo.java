package tron.tronlink.wallet;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

public class Ieo {

  private HttpResponse response;


  @Test(enabled = true)
  public void test000Ieo(){
    response = TronlinkApiList.ieo();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    TronlinkApiList.parseJsonObResponseContent(response);
  }
}
