package tron.trongrid.trongridCase;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class getAccount extends Base {
  private static HttpResponse response1;


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account from trongrid")
  public void test01GetAccountFromTrongrid() {
    response = getAccount(queryAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() > 10);
    Assert.assertTrue(responseContent.getLong("balance") >  0);
    Assert.assertEquals(responseContent.getString("create_time"),"1588824369000");
  }
}
