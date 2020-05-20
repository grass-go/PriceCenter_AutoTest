package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class queryContract extends fullOrSolidityBase {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get contract from trongrid")
  public void test01GetContractFromTrongrid() {
    response = getContract(usdjContract);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertEquals(responseContent.getString("contract_address"),usdjContract);
    Assert.assertEquals(responseContent.getString("origin_address"),usdjOriginAddress);
    Assert.assertTrue(responseContent.containsKey("code_hash"));
    Assert.assertTrue(responseContent.containsKey("origin_energy_limit"));
    Assert.assertTrue(responseContent.containsKey("abi"));
    Assert.assertTrue(responseContent.containsKey("consume_user_resource_percent"));
    Assert.assertTrue(responseContent.containsKey("name"));
    Assert.assertTrue(responseContent.containsKey("bytecode"));

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
