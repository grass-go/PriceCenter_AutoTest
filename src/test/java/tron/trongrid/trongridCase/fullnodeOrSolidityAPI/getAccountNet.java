package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class getAccountNet extends fullOrSolidityBase {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account net from trongrid")
  public void test01GetAccountNetFromTrongrid() {
    response = getAccountNet(queryAddress);
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() > 3);
    Assert.assertEquals(responseContent.getString("freeNetLimit"),"1500");
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
