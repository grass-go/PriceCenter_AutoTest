package tron.tronlink.old;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class getLatestAPK extends TronlinkBase {
  private HttpResponse response;

  @Test(enabled = true, description = "Api /api/v1/wallet/getLatestAPK  test")
  public void LatestAPKLowVersionWithNoSig() throws Exception {
    response = api.getLatestAPKNoSig(null);

    JSONObject jsonObject = api.parseResponse2JsonObject(response);
    JSONObject jsonData = jsonObject.getJSONObject("data");
    api.printJsonObjectContent(jsonObject);
    int index=0;
    for(index=0;index<12;index++)
    {
      log.info("cur index for tesetflight is " + index);
      if(TronlinkApiList.urlCanVisited(jsonData.getString("testflight"),6000 ))
      {
        index=11;
      }
      else{
        Thread.sleep(1500);
        continue;
      }
    }
    Assert.assertEquals(12,index);
    for(index=0;index<12;index++)
    {
      log.info("cur index for china_url is " + index);
      if(TronlinkApiList.urlCanVisited(jsonData.getString("china_url"),6000 ))
      {
        index=11;
      }
      else{
        Thread.sleep(1500);
        continue;
      }
    }
    Assert.assertEquals(12,index);
  }

  @Test(enabled = true, description = "Api /api/v1/wallet/getLatestAPK  test", groups={"P2"})
  public void LatestAPKHighVersionWithNoSig() throws Exception {
    HashMap<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = api.getLatestAPKNoSig(header);
    JSONObject responseContent = api.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }


  @Test(enabled = true, description = "Api /api/v1/wallet/getLatestAPK  test")
  public void test001LatestAPK() throws Exception {
    HashMap<String, String> params = new HashMap<>();
    params.put("address", quince_B58);
    response = api.getLatestAPK(params);
    JSONObject jsonObject = api.parseResponse2JsonObject(response);
    JSONObject jsonData = jsonObject.getJSONObject("data");
    api.printJsonObjectContent(jsonObject);
    int index=0;
    for(index=0;index<12;index++)
    {
      log.info("cur index for tesetflight is " + index);
      if(TronlinkApiList.urlCanVisited(jsonData.getString("testflight"),6000 ))
      {
        index=11;
      }
      else{
        Thread.sleep(1500);
        continue;
      }
    }
    Assert.assertEquals(12,index);
    for(index=0;index<12;index++)
    {
      log.info("cur index for china_url is " + index);
      if(TronlinkApiList.urlCanVisited(jsonData.getString("china_url"),6000 ))
      {
        index=11;
      }
      else{
        Thread.sleep(1500);
        continue;
      }
    }
    Assert.assertEquals(12,index);
  }
}