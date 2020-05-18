package tron.trongrid.trongridCase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryTransaction extends Base {

  JSONObject transactionInfo;
  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction by id from trongrid")
  public void test01GetTransactionByIdFromTrongrid() {
    response = getTransactionById(txid);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getJSONArray("ret")
        .getJSONObject(0).getString("contractRet").equals("SUCCESS"));
    Assert.assertTrue(responseContent.containsKey("signature"));
    Assert.assertEquals(responseContent.getString("txID"),txid);
    Assert.assertTrue(responseContent.getJSONObject("raw_data").getJSONArray("contract").getJSONObject((0)).getString("type").equals("TriggerSmartContract"));
    Assert.assertTrue(responseContent.getJSONObject("raw_data").getLong("timestamp") ==1589788820805L);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by id from trongrid")
  public void test02GetTransactionInfoByIdFromTrongrid() {
    response = getTransactionInfoById(txid);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    transactionInfo = responseContent;
    Assert.assertTrue(transactionInfo.containsKey("log"));
    Assert.assertTrue(transactionInfo.getInteger("fee") == 3500);
    Assert.assertEquals(transactionInfo.getLong("blockNumber"), txidBlockNum);
    Assert.assertTrue(transactionInfo.containsKey("internal_transactions"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by block number from trongrid")
  public void test03GetTransactionInfoByBlockNumFromTrongrid() {
    response = getTransactionInfoByBlockNum(txidBlockNum);
    JSONArray responseContent = parseResponseContentToArray(response);
    Assert.assertEquals(responseContent.size(),47);
    Assert.assertTrue(responseContent.contains(transactionInfo));
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
