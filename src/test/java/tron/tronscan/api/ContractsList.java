package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class ContractsList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   * 合约概览展示接口
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List contract list")
  public void test01getContractsList() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("count","true");
    params.put("limit",String.valueOf(limit));
    params.put("start","0");

    response = TronscanApiList.getContractsList(tronScanNode,params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);

    //4 key
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long
            .valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total <= 10000 && total>0);
    Assert.assertTrue(!responseContent.getString("status").isEmpty());

    //Address list
    responseArrayContent = responseContent.getJSONArray("data");
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i)
          .getString("address")).matches());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("verify_status").isEmpty());
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("balance")) >= 0);
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getString("trxCount")) > 0);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("date_created").isEmpty());

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
