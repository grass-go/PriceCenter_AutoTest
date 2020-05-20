package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class getTrc10AssetByIdentifier extends V1Base {

    JSONObject getAssetListBody;
    JSONObject assetData;
    String base64Address;
    String fingerprint;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by identifier with token id from trongrid V1 API")
  public void test01GetAssetByIdentifierWithTokenIdFromTrongridV1() {
    getAssetListBody = getAssetByIdentifier(bttTokenId);
    Assert.assertEquals(getAssetListBody.getBoolean("success"),true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    assetData = getAssetListBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(assetData);
    Assert.assertTrue(assetData.getInteger("trx_num") == 1);
    Assert.assertTrue(assetData.getLong("total_supply") == 990000000000000000L);
    Assert.assertTrue(assetData.getLong("num") == 1);
    Assert.assertTrue(assetData.getLong("precision") == 6);
    Assert.assertTrue(assetData.getLong("end_time") == 1548000001000L);
    Assert.assertTrue(assetData.getString("description").equals("Official Token of BitTorrent Protocol"));
    Assert.assertEquals(assetData.getString("owner_address").substring(0,2),"41");
    Assert.assertTrue(assetData.getLong("start_time")== 1548000000000L);
    Assert.assertEquals(assetData.getString("name"), "BitTorrent");
    Assert.assertTrue(assetData.getInteger("id") == 1002000);
    Assert.assertEquals(assetData.getString("abbr"),"BTT");
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by identifier with account address from trongrid V1 API")
  public void test02GetAssetByIdentifierWithAccountAddressFromTrongridV1() {
    getAssetListBody = getAssetByIdentifier(bttOwnerAddress);
    Assert.assertEquals(getAssetListBody.getBoolean("success"), true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertEquals(assetData,getAssetListBody.getJSONArray("data").getJSONObject(0));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by identifier with account base 64 address from trongrid V1 API")
  public void test02GetAssetByIdentifierWithAccountBase64AddressFromTrongridV1() {
    getAssetListBody = getAssetByIdentifier(bttOwnerAddressBase64);
    Assert.assertEquals(getAssetListBody.getBoolean("success"), true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertEquals(assetData,getAssetListBody.getJSONArray("data").getJSONObject(0));
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset by identifier with not only confirmed from trongrid V1 API")
  public void test02GetAssetByIdentifierWithNotOnlyConfirmedFromTrongridV1() {
    getAssetListBody = getAssetByIdentifier(bttOwnerAddressBase64,false);
    Assert.assertEquals(getAssetListBody.getBoolean("success"), true);
    Assert.assertTrue(getAssetListBody.containsKey("meta"));
    Assert.assertEquals(assetData,getAssetListBody.getJSONArray("data").getJSONObject(0));
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
