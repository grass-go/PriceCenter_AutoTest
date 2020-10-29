package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;
import tron.trongrid.base.fullOrSolidityBase;

public class eventByTransactionId extends V1Base {

    JSONObject getEventByTransactionIIdBody;
    JSONObject eventData;
  JSONObject getEventByBlockNumberBody;


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by transaction id from trongrid V1 API")
  public void test01GetEventTransactionByIdFromTrongridV1() {
    getEventByTransactionIIdBody = getEventByTransactionId(eventTxid);
    Assert.assertEquals(getEventByTransactionIIdBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByTransactionIIdBody.containsKey("meta"));
    Assert.assertTrue(getEventByTransactionIIdBody.getJSONArray("data").size()>=5);
    eventData = getEventByTransactionIIdBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(eventData);
//    Assert.assertFalse(eventData.getJSONObject("result").isEmpty());
    Assert.assertEquals(eventData.getString("transaction_id"),eventTxid);
//    Assert.assertFalse(eventData.getJSONObject("result_type").isEmpty());
    Assert.assertTrue(eventData.getLong("block_timestamp") == 1587461283000L);
//    Assert.assertEquals(eventData.getString("event_name"),"Transfer");
    Assert.assertFalse(eventData.getString("contract_address").isEmpty());
    Assert.assertTrue(eventData.containsKey("event_index"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by transaction id with not only confirmed from trongrid V1 API")
  public void test02GetEventTransactionByIdWithNotOnlyConfirmedFromTrongridV1() {
    getEventByTransactionIIdBody = getEventByTransactionId(eventTxid,false);
    Assert.assertEquals(getEventByTransactionIIdBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByTransactionIIdBody.containsKey("meta"));
    Assert.assertEquals(eventData,getEventByTransactionIIdBody.getJSONArray("data").getJSONObject(0));
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by block number from trongrid V1 API")
  public void test03GetEventByBlockNumberFromTrongridV1() {
    getEventByBlockNumberBody = getEventByBlockNumber(String.valueOf(eventBlockNumber),true);
    Assert.assertEquals(getEventByBlockNumberBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByBlockNumberBody.containsKey("meta"));
    JSONArray eventByblockNumberArray = getEventByBlockNumberBody.getJSONArray("data");
    Assert.assertTrue(fullOrSolidityBase.jsonarrayContainsJsonobject(eventByblockNumberArray,eventData));
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
