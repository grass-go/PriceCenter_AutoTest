package tron.trongrid.trongridCase;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class getAccountNet extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account net from trongrid")
  public void test01GetAccountNetFromTrongrid() {
    response = getAccountNet(queryAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() > 3);
    Assert.assertEquals(responseContent.getString("freeNetLimit"),"5000");
    Assert.assertEquals(responseContent.getString("TotalNetLimit"),"43200000000");
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    try {
      disConnect();
    } catch (Exception e) {

    }
  }
}
