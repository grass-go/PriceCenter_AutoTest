package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class generateaddress extends fullOrSolidityBase {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Generate address from trongrid")
  public void test01GenerateAddressFromTrongrid() {
    response = generateAddress(true);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    String base58Address = responseContent.getString("address");
    String base64Address = responseContent.getString("hexAddress");
    String privateKey = responseContent.getString("privateKey");

    Assert.assertTrue(base58Address.charAt(0) == 'T');
    Assert.assertTrue(base64Address.substring(0,2).equals("41"));
    Assert.assertEquals(privateKey.length(), 64);

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
