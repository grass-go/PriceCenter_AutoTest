package tron.trongrid.trongridCase;

import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class querySr extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List witnesses from trongrid")
  public void test01ListWitnessesFromTrongrid() {
    response = listWitnesses();
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getJSONArray("witnesses").size() > 100);
    JSONObject firstWitnessInfo = responseContent.getJSONArray("witnesses").getJSONObject(0);
    printJsonContent(firstWitnessInfo);
    Assert.assertEquals(firstWitnessInfo.getString("address").substring(0,2), "41");
    Assert.assertTrue(firstWitnessInfo.containsKey("isJobs"));
    Assert.assertTrue(firstWitnessInfo.containsKey("totalProduced"));
    Assert.assertTrue(firstWitnessInfo.containsKey("latestSlotNum"));
    Assert.assertTrue(firstWitnessInfo.containsKey("voteCount"));
    Assert.assertTrue(firstWitnessInfo.containsKey("totalMissed"));
    Assert.assertTrue(firstWitnessInfo.containsKey("url"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get next maintenance time from trongrid")
  public void test02GetNextMaintenanceTimeFromTrongrid() {
    response = getNextMaintenanceTime();
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Long nextMainTenanceTime = responseContent.getLong("num");
    Assert.assertTrue(nextMainTenanceTime % maintenanceTimeInterval == 0);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get brokerage from trongrid")
  public void test03GetBrokerageFromTrongrid() {
    response = getBrokerage(srAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("brokerage") >= 0 && responseContent.getInteger("brokerage") <= 100);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get reward from trongrid")
  public void test04GetRewardFromTrongrid() {
    response = getReward(srAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getLong("reward") >= 0);
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
