package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class getLatestAPK extends TronlinkBase {

  private HttpResponse response;
  @Test(enabled = true, description = "Api /api/v1/wallet/getLatestAPK  test")
  public void test001LatestAPK() throws Exception {
    response = api.getLatestAPK();

    JSONObject jsonObject = api.parseResponseContent(response);
    JSONObject jsonData = jsonObject.getJSONObject("data");
    api.printJsonContent(jsonObject);
    int index=0;
    for(index=0;index<10;index++)
    {
      log.info("cur index for tesetflight is " + index);
      if(TronlinkApiList.urlCanVisited(jsonData.getString("testflight"),6000 ))
      {
        index=11;
      }
      else{
        Thread.sleep(1000);
        continue;
      }
    }
    Assert.assertEquals(12,index);
    for(index=0;index<10;index++)
    {
      log.info("cur index for china_url is " + index);
      if(TronlinkApiList.urlCanVisited(jsonData.getString("china_url"),6000 ))
      {
        index=11;
      }
      else{
        Thread.sleep(1000);
        continue;
      }
    }
    Assert.assertEquals(12,index);
  }
}