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

public class search_page {

  private JSONObject responseContent;
  private JSONObject responseContent2;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private JSONObject proposalContent;
  private String accountAddress = Configuration.getByPath("testng.conf")
      .getString("defaultParameter.accountAddress");
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "search token")
  public void search_token() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("term", "usdt");
    params.put("type","token");
    params.put("start","0");
    params.put("limit","20");
    response = TronscanApiList.searchMain(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    responseArrayContent = responseContent.getJSONArray("search_result");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(!responseObject.getString("desc").isEmpty());
      Assert.assertTrue(!responseObject.getString("contract_address").isEmpty());
      Assert.assertTrue(!responseObject.getString("vip").isEmpty());
      try {
        Thread.sleep(500);
      }catch (Exception ex){
      }
    }
  }

}
