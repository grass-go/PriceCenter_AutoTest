package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import com.alibaba.fastjson.JSONArray;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class getBalance extends fullOrSolidityBase {


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trx balance from trongrid")
  public void test01GetTrxBalanceTrongrid() {
    response = getAccount(queryAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getLong("balance") >  0);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get TRC10 BTT balance from trongrid")
  public void test02GetTrc10BttBalanceTrongrid() {
    response = getAccount(queryAddress);
    responseContent = parseResponseContent(response);
    JSONArray trc10_json = responseContent.getJSONArray("assetV2");
    Boolean hasBttBalance = false;
    for(int i = 0; i < trc10_json.size();i++) {
      if (trc10_json.getJSONObject(i).get("key").equals(bttTokenId)){
        Assert.assertTrue(trc10_json.getJSONObject(i).getLong("value") > 0);
        hasBttBalance = true;
      }
    }
    Assert.assertTrue(hasBttBalance);
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get TRC20 WIN balance from trongrid")
  public void test03GetTrc20WinBalanceTrongrid() {
    response = triggerConstantContract(queryAddress, winAddress, "balanceOf(address)",
        queryAddressBase64, 1000000000L);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getJSONObject("result").getBoolean("result"));
    String base64Balance = responseContent.getJSONArray("constant_result").getString(0);
    System.out.println(base64Balance);
    Assert.assertNotEquals(base64Balance,zeroBase64);
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
