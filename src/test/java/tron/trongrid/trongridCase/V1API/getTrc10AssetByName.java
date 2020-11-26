package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class getTrc10AssetByName extends V1Base {

    JSONObject getAssetListBody;
    JSONObject firstAssetData;
    String base64Address;
    String fingerprint;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by name from trongrid V1 API")
  public void test01GetAssetByNameFromTrongridV1() {
    getAssetListBody = getAssetByName("BTT",2,"","",true);
    printJsonContent(getAssetListBody);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertTrue(getAssetListBody.getJSONArray("data").size() == 2);
    firstAssetData = getAssetListBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(firstAssetData);
    Assert.assertTrue(firstAssetData.getLong("trx_num") > 0);
    Assert.assertTrue(firstAssetData.getLong("total_supply") > 0);
    Assert.assertTrue(firstAssetData.getLong("num") > 0);
    Assert.assertTrue(firstAssetData.getLong("precision") <= 6 && firstAssetData.getLong("precision") >= 0);
    Assert.assertTrue(firstAssetData.getLong("end_time")/1000 <= System.currentTimeMillis());
    Assert.assertTrue(firstAssetData.containsKey("description"));
    Assert.assertEquals(firstAssetData.getString("owner_address").substring(0,2),"41");
    Assert.assertTrue(firstAssetData.getLong("start_time") < firstAssetData.getLong("end_time"));
    Assert.assertEquals(firstAssetData.getString("name"), "BTT");
    Assert.assertTrue(firstAssetData.getInteger("id") > 1000000);
    Assert.assertTrue(firstAssetData.containsKey("abbr"));
    fingerprint = getAssetListBody.getJSONObject("meta").getString("fingerprint");
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by name with fingerprint from trongrid V1 API")
  public void test02GetAssetByNameWithFingerprintFromTrongridV1() {
    getAssetListBody = getAssetByName("BTT",2,fingerprint,"",true);
    printJsonContent(getAssetListBody);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertNotEquals(firstAssetData,getAssetListBody.getJSONArray("data").getJSONObject(0));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by name with order by from trongrid V1 API")
  public void test03GetAssetByNameWithOrderByFromTrongridV1() {
    getAssetListBody = getAssetByName("BTT",2,"","id,asc",true);
    printJsonContent(getAssetListBody);
    Assert.assertNotEquals(firstAssetData,getAssetListBody.getJSONArray("data").getJSONObject(0));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by name with not only confirmed from trongrid V1 API")
  public void test04GetAssetByNameWithNotOnlyConfirmedFromTrongridV1() {
    getAssetListBody = getAssetByName("BTT",20,"","",false);
    printJsonContent(getAssetListBody);
    Assert.assertEquals(firstAssetData,getAssetListBody.getJSONArray("data").getJSONObject(0));
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
