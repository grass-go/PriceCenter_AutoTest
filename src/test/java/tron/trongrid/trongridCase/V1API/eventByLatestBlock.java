package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class eventByLatestBlock extends V1Base {

    JSONObject getEventByLatestBlockBody;
    JSONObject eventData;
    JSONArray eventArray = new JSONArray();


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by latest block from trongrid V1 API")
  public void test01GetEventByLatestBlockFromTrongridV1() throws Exception{
    Integer retryTime = 10;
    while (eventArray.size() == 0 && retryTime-- > 0) {
      getEventByLatestBlockBody = getEventByLatestBlockNumber();
      Assert.assertEquals(getEventByLatestBlockBody.getBoolean("success"), true);
      Assert.assertTrue(getEventByLatestBlockBody.containsKey("meta"));
      Thread.sleep(3000);
    }
    Assert.assertTrue(getEventByLatestBlockBody.getJSONArray("data").size() > 0);
    eventArray = getEventByLatestBlockBody.getJSONArray("data");

    System.out.println("eventarray size:" + eventArray.size());
    for (int i = 0; i < eventArray.size();i++) {
      Assert.assertTrue(eventArray.getJSONObject(i).containsKey("event_name"));
    }
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get event by latest block without confirmed from trongrid V1 API")
  public void test01GetEventByLatestBlockWithoutConfirmedFromTrongridV1() {
    getEventByLatestBlockBody = getEventByLatestBlockNumber(false);
    Assert.assertEquals(getEventByLatestBlockBody.getBoolean("success"),true);
    Assert.assertTrue(getEventByLatestBlockBody.containsKey("meta"));
    Assert.assertTrue(getEventByLatestBlockBody.getJSONArray("data").size() >=0);
    eventArray = getEventByLatestBlockBody.getJSONArray("data");

    System.out.println("eventarray size:" + eventArray.size());
    for (int i = 0; i < eventArray.size();i++) {
      Assert.assertTrue(eventArray.getJSONObject(i).containsKey("event_name"));
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
