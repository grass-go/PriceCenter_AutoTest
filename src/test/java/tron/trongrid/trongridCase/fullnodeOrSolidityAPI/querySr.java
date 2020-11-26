package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class querySr extends fullOrSolidityBase {
  JSONArray witnessList;
  JSONObject firstWitnessInfo;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List witnesses from trongrid")
  public void test01ListWitnessesFromTrongrid() {
    response = listWitnesses(false);
    witnessList = parseResponseContent(response).getJSONArray("witnesses");
    //printJsonContent(responseContent);
    Assert.assertTrue(witnessList.size() > 100);
    firstWitnessInfo = witnessList.getJSONObject(0);
    printJsonContent(firstWitnessInfo);
    Assert.assertEquals(firstWitnessInfo.getString("address").substring(0,2), "41");
/*    Assert.assertTrue(firstWitnessInfo.containsKey("isJobs"));
    Assert.assertTrue(firstWitnessInfo.containsKey("totalProduced"));
    Assert.assertTrue(firstWitnessInfo.containsKey("latestSlotNum"));
    Assert.assertTrue(firstWitnessInfo.containsKey("voteCount"));
    Assert.assertTrue(firstWitnessInfo.containsKey("totalMissed"));*/
    Assert.assertTrue(firstWitnessInfo.containsKey("url"));
  }

  /**
   * constructor.
   */
  @Test(enabled = false, description = "Get next maintenance time from trongrid")
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
  @Test(enabled = false, description = "Get brokerage from trongrid")
  public void test03GetBrokerageFromTrongrid() {
    response = getBrokerage(srAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("brokerage") >= 0 && responseContent.getInteger("brokerage") <= 100);
  }

  /**
   * constructor.
   */
  @Test(enabled = false, description = "Get reward from trongrid")
  public void test04GetRewardFromTrongrid() {
    response = getReward(srAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getLong("reward") >= 0);
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "List witnesses from trongrid solidity")
  public void test05ListWitnessesFromTrongridSolidity() {
    response = listWitnesses(true);
    printJsonContent(parseResponseContent(response));
    JSONArray witnessListSolidity  = parseResponseContent(response).getJSONArray("witnesses");
    Assert.assertEquals(witnessListSolidity.size(),witnessList.size());
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
