package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class queryTransaction extends fullOrSolidityBase {

  JSONObject transactionInfoBody;
  JSONObject transactionBody;
  JSONArray getTransactionByBlockNumJsonArrayBody;
  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction by id from trongrid")
  public void test01GetTransactionByIdFromTrongrid() {
    response = getTransactionById(txid,false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = parseResponseContent(response);
    transactionBody = responseContent;
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
    response = getTransactionInfoById(txid,false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    transactionInfoBody = responseContent;
    Assert.assertTrue(transactionInfoBody.containsKey("log"));
    Assert.assertTrue(transactionInfoBody.getInteger("fee") == 3500);
    Assert.assertEquals(transactionInfoBody.getLong("blockNumber"), txidBlockNum);
    Assert.assertTrue(transactionInfoBody.containsKey("internal_transactions"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by block number from trongrid")
  public void test03GetTransactionInfoByBlockNumFromTrongrid() {
    response = getTransactionInfoByBlockNum(txidBlockNum, false);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    JSONArray responseContent = parseResponseContentToArray(response);
    getTransactionByBlockNumJsonArrayBody = responseContent;
    Assert.assertEquals(responseContent.size(),47);
    Assert.assertTrue(fullOrSolidityBase.jsonarrayContainsJsonobject(responseContent,transactionInfoBody));
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction by id from trongrid solidity")
  public void test04GetTransactionByIdFromTrongridSolidity() {
    response = getTransactionById(txid,true);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = parseResponseContent(response);
    Assert.assertTrue(fullOrSolidityBase.compareJsonObject(transactionBody,responseContent));
//    Assert.assertEquals(transactionBody,responseContent);
   }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by id from trongrid solidity")
  public void test05GetTransactionInfoByIdFromTrongridSolidity() {
    response = getTransactionInfoById(txid,true);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = parseResponseContent(response);
    printJsonContent(transactionInfoBody);
    printJsonContent(responseContent);
//    Assert.assertTrue(fullOrSolidityBase.compareJsonObject(transactionInfoBody,responseContent));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by block number from trongrid solidity")
  public void test06GetTransactionInfoByBlockNumFromTrongridSolidity() {
    response = getTransactionInfoByBlockNum(txidBlockNum,true);
    JSONArray responseContent = parseResponseContentToArray(response);
//    Assert.assertEquals(getTransactionByBlockNumJsonArrayBody,responseContent);
    Assert.assertTrue(fullOrSolidityBase.compareJsonArray(getTransactionByBlockNumJsonArrayBody,responseContent));
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
