package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class queryBlock extends fullOrSolidityBase {
  String blockId;
  String blockIdFromSolidity;
  Long curBlockNum;
  Long curBlockNumFromSolidity;
  JSONObject getNowBlockJsonBody;
  JSONObject getNowBlockFromSolidityJsonBody;
  Integer latestNum = 88;
  Integer LimitNextRange = 10;


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get now block from trongrid")
  public void test01GetNowBlockFromTrongrid() throws Exception{
    Integer transactionNumInBlock = 0;
    Integer retryTimes = 0;
    while (transactionNumInBlock < 1 && retryTimes++ < 15) {
      response = getNowBlock(false);
      responseContent = parseResponseContent(response);
      transactionNumInBlock = responseContent.getJSONArray("transactions").size();
      System.out.println("transactionNum: " + transactionNumInBlock);
      TimeUnit.SECONDS.sleep(3);
    }
    getNowBlockJsonBody = responseContent;
    printJsonContent(responseContent);
    blockId = getNowBlockJsonBody.getString("blockID");
    JSONObject blockHeader = responseContent.getJSONObject("block_header").getJSONObject("raw_data");
    curBlockNum = blockHeader.getLong("number");
    printJsonContent(blockHeader);
    Assert.assertTrue(curBlockNum > 19838446);
    Assert.assertTrue(blockHeader.getLong("timestamp") > 1589780175000L);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by number from trongrid")
  public void test02GetBlockByNumFromTrongrid() {
    response = getBlockByNum(curBlockNum,false);
    JSONObject getBlockByNumJsonBody = parseResponseContent(response);
    printJsonContent(getBlockByNumJsonBody);
    Assert.assertTrue(fullOrSolidityBase.compareJsonObject(getBlockByNumJsonBody,getNowBlockJsonBody));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by id from trongrid")
  public void test03GetBlockByIdFromTrongrid() {
    response = getBlockById(blockId);
    JSONObject getBlockByIdJsonBody = parseResponseContent(response);
    printJsonContent(getBlockByIdJsonBody);
    Assert.assertTrue(fullOrSolidityBase.compareJsonObject(getBlockByIdJsonBody,getNowBlockJsonBody));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by latest num from trongrid")
  public void test04GetBlockByLatestNumFromTrongrid() {
    response = getBlockByLatestNum(latestNum);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    JSONArray getBlockByLatestNumJsonBody = responseContent.getJSONArray("block");
    Assert.assertTrue(getBlockByLatestNumJsonBody.size() == latestNum);
    System.out.println("-----------------------------------------------------------");
    Assert.assertTrue(getBlockByLatestNumJsonBody.contains(getNowBlockJsonBody));

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by limit next from trongrid")
  public void test05GetBlockByLimitNextFromTrongrid() {
    response = getBlockByLimitNext(curBlockNum - LimitNextRange, curBlockNum + 1);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    JSONArray getBlockByLimitNextJsonBody = responseContent.getJSONArray("block");
    System.out.println("getBlockByLimitNextJsonBody.size():" + getBlockByLimitNextJsonBody.size());
    Assert.assertTrue(getBlockByLimitNextJsonBody.size() >= LimitNextRange);
    Assert.assertTrue(getBlockByLimitNextJsonBody.contains(getNowBlockJsonBody));

  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get now block from trongrid solidity")
  public void test06GetNowBlockFromTrongridSolidity() throws Exception{
    Integer transactionNumInBlock = 0;
    Integer retryTimes = 0;
    while (transactionNumInBlock < 1 && retryTimes++ < 15) {
      response = getNowBlock(true);
      responseContent = parseResponseContent(response);
      transactionNumInBlock = responseContent.getJSONArray("transactions").size();
      System.out.println("transactionNum: " + transactionNumInBlock);
      TimeUnit.SECONDS.sleep(3);
    }
    getNowBlockFromSolidityJsonBody = responseContent;
    printJsonContent(responseContent);
    blockIdFromSolidity = getNowBlockFromSolidityJsonBody.getString("blockID");
    JSONObject blockHeader = responseContent.getJSONObject("block_header").getJSONObject("raw_data");
    curBlockNumFromSolidity = blockHeader.getLong("number");
    printJsonContent(blockHeader);
    Assert.assertTrue(curBlockNumFromSolidity > 19838446);
    Assert.assertTrue(blockHeader.getLong("timestamp") > 1589780175000L);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by number from trongrid solidity")
  public void test07GetBlockByNumFromTrongridSolidity() {
    response = getBlockByNum(curBlockNumFromSolidity,true);
    JSONObject getBlockByNumFromSolidityJsonBody = parseResponseContent(response);
    printJsonContent(getBlockByNumFromSolidityJsonBody);
    Assert.assertTrue(fullOrSolidityBase.compareJsonObject(getBlockByNumFromSolidityJsonBody,getNowBlockFromSolidityJsonBody));
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
