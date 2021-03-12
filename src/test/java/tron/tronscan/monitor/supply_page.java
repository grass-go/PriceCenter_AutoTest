package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

public class supply_page {

  private JSONObject responseContent;
  private JSONObject responseContent2;
  private JSONArray responseArrayContent;
  private JSONArray responseArrayContent2;
  private JSONObject targetContent;
  private HttpResponse response;
  private HttpResponse response2;
  private JSONObject proposalContent;
  private String accountAddress = Configuration.getByPath("testng.conf")
      .getString("defaultParameter.accountAddress");
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);
  private String tronScanIoNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscanio.ip.list")
      .get(0);


  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "search token")
  public void trx_supply() {
    //Get response
    int end_time = (int) (System.currentTimeMillis() / 1000) ;
    String end_timestamp = String.valueOf(end_time) + "000";
    Map<String, String> params = new HashMap<>();
    params.put("size", "1000");
    params.put("start","1575129600000");
    params.put("end",end_timestamp);
    response = TronscanApiList.trxSupply(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    responseArrayContent = responseContent.getJSONArray("data");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);

    response2 = TronscanApiList.trxSupply(tronScanNode, params);
    Assert.assertEquals(response2.getStatusLine().getStatusCode(), 200);
    responseContent2 = TronscanApiList.parseResponseContent(response2);
    responseArrayContent2 = responseContent2.getJSONArray("data");
    JSONObject responseObject2 = responseArrayContent2.getJSONObject(0);

    Assert.assertEquals(responseObject,responseObject2);
  }

}
