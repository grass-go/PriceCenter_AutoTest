package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class eventByContractAddress extends V1Base {

    JSONObject getEventByContractAddressBody;
    JSONObject eventData;
    JSONArray eventArray;
    String eventName = "Transfer";
    Long blockNumber = 19897651L;
    Long minBlockTimestamp = 1589957721000L;
    Long maxBlockTimestamp = 1589959729000L;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by contract address from trongrid V1 API")
  public void test01GetEventByContractAddressFromTrongridV1() {
    getEventByContractAddressBody = getEventByContractAddress(usdjContract);
    printJsonContent(getEventByContractAddressBody);
    Assert.assertEquals(getEventByContractAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByContractAddressBody.containsKey("meta"));
    eventData = getEventByContractAddressBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(eventData);
    Assert.assertFalse(eventData.getJSONObject("result").isEmpty());
    Assert.assertFalse(eventData.getString("transaction_id").isEmpty());
    Assert.assertFalse(eventData.getJSONObject("result_type").isEmpty());
    Assert.assertTrue(eventData.getLong("block_timestamp") > 1584501014000L);
    Assert.assertFalse(eventData.getString("event_name").isEmpty());
    Assert.assertFalse(eventData.getString("contract_address").isEmpty());
    Assert.assertTrue(eventData.containsKey("event_index"));
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by contract address with  event_name from trongrid V1 API")
  public void test02GetEventByContractAddressWithEventNameFromTrongridV1() {
    getEventByContractAddressBody = getEventByContractAddress(usdjContract,true,eventName,
        0L,0L,0L,"");
    printJsonContent(getEventByContractAddressBody);
    Assert.assertEquals(getEventByContractAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByContractAddressBody.containsKey("meta"));
    eventArray = getEventByContractAddressBody.getJSONArray("data");
    for (int i = 0; i < eventArray.size();i++) {
      Assert.assertEquals(eventArray.getJSONObject(i).getString("event_name"),eventName);
    }
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by contract address with block number from trongrid V1 API")
  public void test03GetEventByContractAddressWithblockNumberFromTrongridV1() {
    getEventByContractAddressBody = getEventByContractAddress(usdjContract,true,eventName,0L,
        minBlockTimestamp,maxBlockTimestamp,"");
    printJsonContent(getEventByContractAddressBody);
    Assert.assertEquals(getEventByContractAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByContractAddressBody.containsKey("meta"));
    eventArray = getEventByContractAddressBody.getJSONArray("data");
    Assert.assertEquals(eventArray.size(),7);
    for (int i = 0; i < eventArray.size();i++) {
      Assert.assertTrue(eventArray.getJSONObject(i).getLong("block_timestamp") >= minBlockTimestamp &&
          eventArray.getJSONObject(i).getLong("block_timestamp") <= maxBlockTimestamp);
    }
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by contract address with order by from trongrid V1 API")
  public void test04GetEventByContractAddressWithOrderByFromTrongridV1() {
    getEventByContractAddressBody = getEventByContractAddress(usdjContract,true,eventName,0L,
        0L,0L,"block_timestamp,desc");
    printJsonContent(getEventByContractAddressBody);
    Assert.assertEquals(getEventByContractAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByContractAddressBody.containsKey("meta"));
    eventArray = getEventByContractAddressBody.getJSONArray("data");
    Long descFirstBlockTimestamp = eventArray.getJSONObject(0).getLong("block_timestamp");
    Long descSecondBlockTimestamp = eventArray.getJSONObject(1).getLong("block_timestamp");


    getEventByContractAddressBody = getEventByContractAddress(usdjContract,true,eventName,0L,
        0L,0L,"block_timestamp,asc");
    Assert.assertEquals(getEventByContractAddressBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByContractAddressBody.containsKey("meta"));
    eventArray = getEventByContractAddressBody.getJSONArray("data");
    Long ascFirstBlockTimestamp = eventArray.getJSONObject(0).getLong("block_timestamp");
    Long ascSecondBlockTimestamp = eventArray.getJSONObject(1).getLong("block_timestamp");


    Assert.assertTrue(descFirstBlockTimestamp >= descSecondBlockTimestamp);
    Assert.assertTrue(ascFirstBlockTimestamp <= ascSecondBlockTimestamp);
    Assert.assertTrue(descSecondBlockTimestamp > ascFirstBlockTimestamp);


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
