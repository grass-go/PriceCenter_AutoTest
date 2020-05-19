package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;
import tron.trongrid.base.fullOrSolidityBase;

public class accountInfoByAddress extends V1Base {

    JSONObject getAccountInfoByAddressBody;
    JSONObject accountData;
    String base64Address;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account info by address from trongrid V1 API")
  public void test01GetAccountInfoByAddressFromTrongridV1() {
    getAccountInfoByAddressBody = getAccountInfoByAddress(queryAddress);
    //printJsonContent(getAccountInfoByAddressBody);
    Assert.assertEquals(getAccountInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getAccountInfoByAddressBody.containsKey("meta"));
    accountData = getAccountInfoByAddressBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(accountData);
    Assert.assertTrue(accountData.containsKey("owner_permission"));
    Assert.assertTrue(accountData.containsKey("active_permission"));
    Assert.assertTrue(queryAddressBase64.toLowerCase()
        .contains(accountData.getString("address").substring(2).toLowerCase()));
    base64Address = "41" + accountData.getString("address").substring(2);
    Assert.assertTrue(accountData.getLong("create_time") == 1588824369000L);
    Assert.assertTrue(accountData.containsKey("free_asset_net_usageV2"));
    Assert.assertTrue(accountData.containsKey("latest_opration_time"));
    Assert.assertTrue(accountData.containsKey("assetV2"));
    Assert.assertTrue(accountData.getLong("balance") > 0);
    Assert.assertTrue(accountData.containsKey("trc20"));
    Assert.assertTrue(accountData.containsKey("latest_consume_free_time"));
    Assert.assertTrue(accountData.containsKey("acquired_delegated_frozen_balance_for_bandwidth"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account info by address with only unconfirmed from trongrid V1 API")
  public void test02GetAccountInfoByAddressWithOnlyUnconfirmedFromTrongridV1() {
    getAccountInfoByAddressBody = getAccountInfoByAddress(queryAddress,false);
    Assert.assertEquals(getAccountInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getAccountInfoByAddressBody.containsKey("meta"));
    Assert.assertEquals(accountData,getAccountInfoByAddressBody.getJSONArray("data").getJSONObject(0));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account info by address with base64 address from trongrid V1 API")
  public void test03GetAccountInfoByAddressWithBase64FromTrongridV1() {
    getAccountInfoByAddressBody = getAccountInfoByAddress(base64Address);
    Assert.assertEquals(getAccountInfoByAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getAccountInfoByAddressBody.containsKey("meta"));
    Assert.assertEquals(accountData,getAccountInfoByAddressBody.getJSONArray("data").getJSONObject(0));
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
