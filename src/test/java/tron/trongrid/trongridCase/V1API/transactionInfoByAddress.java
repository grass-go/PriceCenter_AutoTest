package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class transactionInfoByAddress extends V1Base {

    JSONObject getTransactionInfoByAddressBody;
    JSONObject transactionData;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by address from trongrid V1 API")
  public void test01GetTransactionInfoByAddressFromTrongridV1() {
    getTransactionInfoByAddressBody = getTransactionInfoByAddress(queryAddress);
    Assert.assertEquals(getTransactionInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getTransactionInfoByAddressBody.containsKey("meta"));
    transactionData = getTransactionInfoByAddressBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(transactionData);
    Assert.assertTrue(transactionData.containsKey("ret"));
    Assert.assertTrue(transactionData.containsKey("signature"));
    Assert.assertEquals(transactionData.getString("txID").length(),64);
    Assert.assertTrue(transactionData.containsKey("net_fee"));
    Assert.assertTrue(transactionData.containsKey("energy_usage"));
    Assert.assertTrue(transactionData.getLong("blockNumber") > 0);
    Assert.assertTrue(transactionData.containsKey("block_timestamp"));
    Assert.assertTrue(transactionData.containsKey("energy_fee"));
    Assert.assertTrue(transactionData.containsKey("energy_usage_total"));
    Assert.assertTrue(transactionData.containsKey("raw_data"));
    Assert.assertTrue(transactionData.containsKey("internal_transactions"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by address with only unconfirmed from trongrid V1 API")
  public void test02GetTransactionInfoByAddressWithOnlyUnconfirmedFromTrongridV1() {
    getTransactionInfoByAddressBody = getTransactionInfoByAddress(queryAddress,false);
    Assert.assertEquals(getTransactionInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getTransactionInfoByAddressBody.containsKey("meta"));
    Assert.assertEquals(transactionData,getTransactionInfoByAddressBody.getJSONArray("data").getJSONObject(0));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by address with only unconfirmed from trongrid V1 API")
  public void test03GetTransactionInfoByAddressWithBase64AddressFromTrongridV1() {
    getTransactionInfoByAddressBody = getTransactionInfoByAddress(queryAddressBase64With41Start);
    Assert.assertEquals(getTransactionInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getTransactionInfoByAddressBody.containsKey("meta"));
    Assert.assertEquals(transactionData,getTransactionInfoByAddressBody.getJSONArray("data").getJSONObject(0));
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
