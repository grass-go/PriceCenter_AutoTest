package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class trc20TransactionInfoByAddress extends V1Base {

    JSONObject getTrc20TransactionInfoByAddressBody;
    JSONObject trc20TransactionData;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trc20 transaction info by address from trongrid V1 API")
  public void test01GetTrc20TransactionInfoByAddressFromTrongridV1() {
    getTrc20TransactionInfoByAddressBody = getTrc20TransactionInfoByAddress(queryAddress);
    Assert.assertEquals(getTrc20TransactionInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TransactionInfoByAddressBody.containsKey("meta"));
    trc20TransactionData = getTrc20TransactionInfoByAddressBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(trc20TransactionData);
    Assert.assertEquals(trc20TransactionData.getString("transaction_id").length(),64);
    JSONObject tokenInfo = trc20TransactionData.getJSONObject("token_info");
    Assert.assertTrue(tokenInfo.containsKey("symbol"));
    Assert.assertTrue(tokenInfo.containsKey("address"));
    Assert.assertTrue(tokenInfo.containsKey("decimals"));
    Assert.assertTrue(tokenInfo.containsKey("name"));
    Assert.assertTrue(trc20TransactionData.containsKey("block_timestamp"));
    Assert.assertTrue(trc20TransactionData.containsKey("from"));
    Assert.assertTrue(trc20TransactionData.containsKey("to"));
    Assert.assertTrue(trc20TransactionData.containsKey("type"));
    Assert.assertTrue(trc20TransactionData.containsKey("value"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trc20 transaction info by address with only unconfirmed from trongrid V1 API")
  public void test02GetTrc20TransactionInfoByAddressWithOnlyUnconfirmedFromTrongridV1() {
    getTrc20TransactionInfoByAddressBody = getTrc20TransactionInfoByAddress(queryAddress,false);
    Assert.assertEquals(getTrc20TransactionInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TransactionInfoByAddressBody.containsKey("meta"));
    Assert.assertEquals(trc20TransactionData,getTrc20TransactionInfoByAddressBody.getJSONArray("data").getJSONObject(0));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trc20 transaction info by address with only unconfirmed from trongrid V1 API")
  public void test03GetTrc20TransactionInfoByAddressWithBase64AddressFromTrongridV1() {
    getTrc20TransactionInfoByAddressBody = getTrc20TransactionInfoByAddress(queryAddressBase64With41Start);
    Assert.assertEquals(getTrc20TransactionInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TransactionInfoByAddressBody.containsKey("meta"));
    Assert.assertEquals(trc20TransactionData,getTrc20TransactionInfoByAddressBody.getJSONArray("data").getJSONObject(0));
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
