package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class getTrc10AssetList extends V1Base {

    JSONObject getAssetListBody;
    JSONObject firstAssetData;
    String base64Address;
    String fingerprint;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset list from trongrid V1 API")
  public void test01GetAssetListFromTrongridV1() {
    getAssetListBody = getAssetList();
    printJsonContent(getAssetListBody);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertEquals(getAssetListBody.getJSONArray("data").size(),20);
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
    Assert.assertTrue(firstAssetData.containsKey("name"));
    Assert.assertTrue(firstAssetData.getInteger("id") > 1000000);
    Assert.assertTrue(firstAssetData.containsKey("abbr"));
    fingerprint = getAssetListBody.getJSONObject("meta").getString("fingerprint");
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset list with max limit from trongrid V1 API")
  public void test02GetAssetListWithMaxLimitFromTrongridV1() {
    getAssetListBody = getAssetList("",100,"");
    printJsonContent(getAssetListBody);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertEquals(getAssetListBody.getJSONArray("data").size(),100);

  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset list with fingerprint from trongrid V1 API")
  public void test03GetAssetListWithFingerprintFromTrongridV1() {
    getAssetListBody = getAssetList("",20,fingerprint);
    printJsonContent(getAssetListBody);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertEquals(getAssetListBody.getJSONArray("data").size(),20);
    Assert.assertNotEquals(firstAssetData,getAssetListBody.getJSONArray("data").getJSONObject(0));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset list with order by from trongrid V1 API")
  public void test04GetAssetListWithOrderByFromTrongridV1() {
    getAssetListBody = getAssetList("id,asc",20,"");
    printJsonContent(getAssetListBody);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertTrue(getAssetListBody.getJSONArray("data").getJSONObject(0).getInteger("id") == 1000001);


    getAssetListBody = getAssetList("id,desc",20,"");
    printJsonContent(getAssetListBody);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertTrue(getAssetListBody.getJSONArray("data").getJSONObject(0).getInteger("id") >= 1003009);
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
