package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;


@Slf4j
public class Chainparameters {


  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.
   * 提议页面的展示
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get chainparameters list")
  public void getchainparameters() {
    //Get response
    response = TronscanApiList.getChainparameters(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    JSONArray chainParameters = responseContent.getJSONArray("tronParameters");
    log.info(chainParameters.size() + "");
    Assert.assertTrue(chainParameters.size() >= 28);

    //getMaintenanceTimeInterval
    for (int i = 0; i < chainParameters.size(); i++) {
      Assert.assertTrue(!chainParameters.getJSONObject(i).get("key").toString().isEmpty());
      Assert.assertTrue(chainParameters.getJSONObject(0).get("key").equals("getMaintenanceTimeInterval"));
      Assert.assertTrue(!chainParameters.getJSONObject(i).get("value").toString().isEmpty());

    }
  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }


}
