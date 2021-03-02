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

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "search address")
  public void search_address() {
    Map<String,String> token = new HashMap<>();
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("address", "TKwmkoBFyhekCgkAZ7FmxnN6oaL3GyD8xR");
    response = TronscanApiList.getAccountList(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    responseArrayContent = responseContent.getJSONArray("trc20token_balances");
    JSONObject responseObject = responseArrayContent.getJSONObject(0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(!responseObject.getString("tokenId").isEmpty());
      String trc20Address = responseObject.getString("tokenId");
      Map<String, String> params2 = new HashMap<>();
      params2.put("contract",trc20Address);
      response = TronscanApiList.getTokentrc20(tronScanNode,params2);
      responseContent2 = TronscanApiList.parseResponseContent(response);
//            Assert.assertTrue(responseContent2.getInteger("total") == 1);
      try {
        Thread.sleep(500);
      }catch (Exception ex){
      }
    }
  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "search token")
  public void search_token(){

  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "search hash")
  public void search_hash(){


  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "search web site")
  public void search_web(){


  }

  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "search error")
  public void search_error() {

  }

}
