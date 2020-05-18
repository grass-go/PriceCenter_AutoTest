package tron.trongrid.trongridCase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryBlock extends Base {
  String blockId;
  Long curBlockNum;
  JSONObject getNowBlockJsonBody;
  Integer latestNum = 5;
  Integer LimitNextRange = 10;


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get now block from trongrid")
  public void test01GetNowBlockFromTrongrid() throws Exception{
    Integer transactionNumInBlock = 0;
    Integer retryTimes = 0;
    while (transactionNumInBlock < 1 && retryTimes++ < 15) {
      response = getNowBlock();
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
    response = getBlockByNum(curBlockNum);
    JSONObject getBlockByNumJsonBody = parseResponseContent(response);
    Assert.assertEquals(getBlockByNumJsonBody,getNowBlockJsonBody);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by id from trongrid")
  public void test03GetBlockByIdFromTrongrid() {
    response = getBlockById(blockId);
    JSONObject getBlockByIdJsonBody = parseResponseContent(response);
    Assert.assertEquals(getBlockByIdJsonBody,getNowBlockJsonBody);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by latest num from trongrid")
  public void test04GetBlockByLatestNumFromTrongrid() {
    response = getBlockByLatestNum(latestNum);
    responseContent = parseResponseContent(response);
    JSONArray getBlockByLatestNumJsonBody = responseContent.getJSONArray("block");
    Assert.assertTrue(getBlockByLatestNumJsonBody.size() == latestNum);
    Assert.assertTrue(getBlockByLatestNumJsonBody.contains(getNowBlockJsonBody));

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by limit next from trongrid")
  public void test05GetBlockByLimitNextFromTrongrid() {
    response = getBlockByLimitNext(curBlockNum - LimitNextRange, curBlockNum + 1);
    responseContent = parseResponseContent(response);
    JSONArray getBlockByLimitNextJsonBody = responseContent.getJSONArray("block");
    System.out.println("getBlockByLimitNextJsonBody.size():" + getBlockByLimitNextJsonBody.size());
    Assert.assertTrue(getBlockByLimitNextJsonBody.size() >= LimitNextRange);
    Assert.assertTrue(getBlockByLimitNextJsonBody.contains(getNowBlockJsonBody));

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
