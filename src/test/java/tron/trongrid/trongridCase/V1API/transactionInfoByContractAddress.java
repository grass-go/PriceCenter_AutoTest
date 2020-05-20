package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.concurrent.ForkJoinPool;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class transactionInfoByContractAddress extends V1Base {

    JSONObject getTransactionInfoByContractAddressBody;
    JSONObject transactionInfoData;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction information by contract address from trongrid V1 API")
  public void test01GetTransactionInformationByContractAddressFromTrongridV1() {
    getTransactionInfoByContractAddressBody = getTransactionInformationByContractAddress(usdjContractBase64);
    Assert.assertEquals(getTransactionInfoByContractAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getTransactionInfoByContractAddressBody.containsKey("meta"));
    transactionInfoData = getTransactionInfoByContractAddressBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(transactionInfoData);
    Assert.assertFalse(transactionInfoData.getString("ret").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("signature").isEmpty());
    Assert.assertTrue(transactionInfoData.getLong("block_timestamp") > 1584501014000L);
    Assert.assertFalse(transactionInfoData.getString("raw_data").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("txID").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("raw_data_hex").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("internal_transactions").isEmpty());
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction information by contract address with not only confirmed from trongrid V1 API")
  public void test02GetTransactionInformationByContractAddressWithNotOnlyConfirmedFromTrongridV1() {
    getTransactionInfoByContractAddressBody = getTransactionInformationByContractAddress(usdjContractBase64,false,"",20,true);
    Assert.assertEquals(getTransactionInfoByContractAddressBody.getBoolean("success"), true);
    Assert.assertTrue(getTransactionInfoByContractAddressBody.containsKey("meta"));
    Assert.assertEquals(getTransactionInfoByContractAddressBody.getJSONArray("data").size(), 20);
    transactionInfoData = getTransactionInfoByContractAddressBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(transactionInfoData);
    Assert.assertFalse(transactionInfoData.getString("ret").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("signature").isEmpty());
    Assert.assertTrue(transactionInfoData.getLong("block_timestamp") > 1584501014000L);
    Assert.assertFalse(transactionInfoData.getString("raw_data").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("txID").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("raw_data_hex").isEmpty());
    Assert.assertFalse(transactionInfoData.getString("internal_transactions").isEmpty());
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction information by contract address with order by block_timestamp from trongrid V1 API")
  public void test03GetTransactionInformationByContractAddressWithOrderByBlockTimestampFromTrongridV1() {
    getTransactionInfoByContractAddressBody = getTransactionInformationByContractAddress(usdjContractBase64,
        true,"block_timestamp,desc",20,true);
    transactionInfoData = getTransactionInfoByContractAddressBody.getJSONArray("data").getJSONObject(0);
    Long first_blockTimestampWithDesc = getTransactionInfoByContractAddressBody
        .getJSONArray("data").getJSONObject(0).getLong("block_timestamp");
    Long second_blockTimestampWithDesc = getTransactionInfoByContractAddressBody
        .getJSONArray("data").getJSONObject(1).getLong("block_timestamp");
    getTransactionInfoByContractAddressBody = getTransactionInformationByContractAddress(usdjContractBase64,
        true,"block_timestamp,asc",20,true);
    transactionInfoData = getTransactionInfoByContractAddressBody.getJSONArray("data").getJSONObject(0);
    Long first_blockTimestampWithAsc = getTransactionInfoByContractAddressBody
        .getJSONArray("data").getJSONObject(0).getLong("block_timestamp");
    Long second_blockTimestampWithAsc = getTransactionInfoByContractAddressBody
        .getJSONArray("data").getJSONObject(1).getLong("block_timestamp");

    Assert.assertTrue(first_blockTimestampWithDesc >= second_blockTimestampWithDesc);
    Assert.assertTrue(first_blockTimestampWithAsc <= second_blockTimestampWithAsc);
    Assert.assertTrue(second_blockTimestampWithDesc > first_blockTimestampWithAsc);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction information by contract address with limit from trongrid V1 API")
  public void test05GetTransactionInformationByContractAddressWithLimitFromTrongridV1() {
    getTransactionInfoByContractAddressBody = getTransactionInformationByContractAddress(usdjContractBase64,
        true,"block_timestamp,asc",200,true);
    JSONArray transactionInfoArray = getTransactionInfoByContractAddressBody.getJSONArray("data");
    Assert.assertEquals(transactionInfoArray.size(),200);
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction information by contract address without search internal trongrid V1 API")
  public void test06GetTransactionInformationByContractAddressWithoutSearchInternalFromTrongridV1() {
    getTransactionInfoByContractAddressBody = getTransactionInformationByContractAddress(usdjContractBase64,
        true,"",50,false);
    JSONArray transactionInfoArray = getTransactionInfoByContractAddressBody.getJSONArray("data");
    for (int i = 0; i < transactionInfoArray.size();i++) {
      Assert.assertFalse(transactionInfoArray.getJSONObject(i).containsKey("internal_tx_id"));
    }
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
