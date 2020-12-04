package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class queryDelegateResource extends fullOrSolidityBase {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get delegate resource from trongrid")
  public void test01GetDelegateResourceFromTrongrid() {
    response = getDelegateResource(delegateResourceFromAddressSophia,delegateResourceToAddressSophia);
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    responseContent = parseResponseContent(response).getJSONArray("delegatedResource").getJSONObject(0);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("expire_time_for_energy"));
    Assert.assertTrue(responseContent.containsKey("expire_time_for_bandwidth"));
    Assert.assertTrue(responseContent.getLong("frozen_balance_for_energy") > 0);
    Assert.assertTrue(responseContent.getLong("frozen_balance_for_bandwidth") > 0);
    Assert.assertEquals(responseContent.getString("from"),delegateResourceFromAddressSophia);
    Assert.assertEquals(responseContent.getString("to"),delegateResourceToAddressSophia);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get delegate resource index from trongrid")
  public void test02GetDelegateResourceIndexFromTrongrid() {
    response = getDelegateResourceIndex(delegateResourceToAddressSophia);
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("fromAccounts"));
    Assert.assertEquals(responseContent.getString("account"),delegateResourceToAddressSophia);

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
