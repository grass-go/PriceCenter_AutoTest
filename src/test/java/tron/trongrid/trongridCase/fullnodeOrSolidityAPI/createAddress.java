package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class createAddress extends fullOrSolidityBase {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Create address from trongrid")
  public void test01CreateAddressFromTrongrid() {
    response = createAddress("Test0001");
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    String base58Address = responseContent.getString("base58checkAddress");
    String base64Address = responseContent.getString("value");
    Assert.assertTrue(base58Address.charAt(0) == 'T');
    Assert.assertTrue(base64Address.substring(0,2).equals("41"));
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

