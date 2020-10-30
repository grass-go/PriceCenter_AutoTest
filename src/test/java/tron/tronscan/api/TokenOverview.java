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

/**
 * ${params}
 *
 * @Author:tron
 * @Date:2019-09-17 17:31
 */
@Slf4j
public class TokenOverview {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list")
      .get(0);

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询通证列表")
  public void getTokenOverview() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "volume24hInTrx");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("order", "desc");
    params.put("filter", "all");
    params.put("order_current", "descend");
    response = TronscanApiList.getTokenOverview(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() >= 7);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalAll = Long.valueOf(responseContent.get("totalAll").toString());
    Assert.assertTrue(totalAll >= total);
    Long all = Long.valueOf(responseContent.get("all").toString());
    Long valueAtLeast = Long.valueOf(responseContent.get("valueAtLeast").toString());
    Assert.assertTrue(all >= valueAtLeast);
    Assert.assertTrue(Long.valueOf(valueAtLeast) == 10000);
    Long currentWeekAll = Long.valueOf(responseContent.get("currentWeekAll").toString());
    Long currentWeekTotalAll = Long.valueOf(responseContent.get("currentWeekTotalAll").toString());
    Assert.assertTrue(currentWeekAll >= currentWeekTotalAll);

    JSONArray exchangeArray = responseContent.getJSONArray("tokens");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //description
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("description").toString().isEmpty());
      //supply
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("supply").toString().isEmpty());
      //imgUrl
      String imgUrl_key = exchangeArray.getJSONObject(i).get("imgUrl").toString();
      Assert.assertTrue(!imgUrl_key.isEmpty());
      HttpResponse httpResponse = TronscanApiList.getUrlkey(imgUrl_key);
      Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      //nrOfTokenHolders
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("nrOfTokenHolders").toString().isEmpty());
      //isTop
      //Assert.assertTrue(Boolean.valueOf(targetContent.getString("isTop")));
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("name").toString().isEmpty());
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("projectSite").toString().isEmpty());
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("abbr").toString().isEmpty());
      Integer decimals = Integer.valueOf(exchangeArray.getJSONObject(i).get("decimal").toString());
      Assert.assertTrue(decimals >= 0 && decimals <= 18);
      //tokenType
      String tokenType = exchangeArray.getJSONObject(i).get("tokenType").toString();
      Assert.assertTrue(!tokenType.isEmpty());
      if (tokenType == "trc20"){
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        String contractAddress = exchangeArray.getJSONObject(i).getString("contractAddress");
        Assert.assertTrue(patternAddress.matcher(contractAddress).matches() && !contractAddress.isEmpty());
      }
    }
  }


  /**
   * constructor.
   * 按照sort=createTime时间固定排序
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询通证列表")
  public void getTokenOverview02() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "createTime");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("order", "desc");
    params.put("filter", "all");
    params.put("order_current", "descend");
    response = TronscanApiList.getTokenOverview(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() >= 7);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalAll = Long.valueOf(responseContent.get("totalAll").toString());
    Assert.assertTrue(totalAll >= total);
    Long all = Long.valueOf(responseContent.get("all").toString());
    Long valueAtLeast = Long.valueOf(responseContent.get("valueAtLeast").toString());
    Assert.assertTrue(all >= valueAtLeast);
    Assert.assertTrue(Long.valueOf(valueAtLeast) == 10000);
    Long currentWeekAll = Long.valueOf(responseContent.get("currentWeekAll").toString());
    Long currentWeekTotalAll = Long.valueOf(responseContent.get("currentWeekTotalAll").toString());
    Assert.assertTrue(currentWeekAll >= currentWeekTotalAll);

    JSONArray exchangeArray = responseContent.getJSONArray("tokens");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //description
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("description").toString().isEmpty());
      //supply
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("supply").toString().isEmpty());
      //imgUrl
      String imgUrl_key = exchangeArray.getJSONObject(i).get("imgUrl").toString();
      Assert.assertTrue(!imgUrl_key.isEmpty());
      if(imgUrl_key.substring(0, 8) == "https://") {
        HttpResponse httpResponse = TronscanApiList.getUrlkey(imgUrl_key);
        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      }
      //nrOfTokenHolders
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("nrOfTokenHolders").toString().isEmpty());
      //isTop
      //Assert.assertTrue(Boolean.valueOf(targetContent.getString("isTop")));
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("name").toString().isEmpty());
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("projectSite").toString().isEmpty());
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("abbr").toString().isEmpty());
      Integer decimals = Integer.valueOf(exchangeArray.getJSONObject(i).get("decimal").toString());
      Assert.assertTrue(decimals >= 0 && decimals <= 18);
      //tokenType
      String tokenType = exchangeArray.getJSONObject(i).get("tokenType").toString();
      Assert.assertTrue(!tokenType.isEmpty());
      if (tokenType == "trc20"){
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        String contractAddress = exchangeArray.getJSONObject(i).getString("contractAddress");
        Assert.assertTrue(patternAddress.matcher(contractAddress).matches() && !contractAddress.isEmpty());
      }
    }
  }

  /**
   * constructor.获取TRC20 Token
   * 按照field=contractAddress后只返回部分字段
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查询通证列表")
  public void getTokenOverview03() {
    //Get response
    int limit = 20;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "priceInTrx");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("filter", "trc20");
    params.put("field", "contractAddress");
    response = TronscanApiList.getTokenOverview(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //data object
    Assert.assertTrue(responseContent.size() >= 7);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalAll = Long.valueOf(responseContent.get("totalAll").toString());
    Assert.assertTrue(totalAll >= total);
    Long all = Long.valueOf(responseContent.get("all").toString());
    Long valueAtLeast = Long.valueOf(responseContent.get("valueAtLeast").toString());
    Assert.assertTrue(all >= valueAtLeast);
    Assert.assertTrue(Long.valueOf(valueAtLeast) == 10000);
    Long currentWeekAll = Long.valueOf(responseContent.get("currentWeekAll").toString());
    Long currentWeekTotalAll = Long.valueOf(responseContent.get("currentWeekTotalAll").toString());
    Assert.assertTrue(currentWeekAll >= currentWeekTotalAll);

    JSONArray exchangeArray = responseContent.getJSONArray("tokens");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //imgUrl
      String imgUrl_key = exchangeArray.getJSONObject(i).get("imgUrl").toString();
      Assert.assertTrue(!imgUrl_key.isEmpty());
      HttpResponse httpResponse = TronscanApiList.getUrlkey(imgUrl_key);
      Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("name").toString().isEmpty());
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("abbr").toString().isEmpty());
      Integer decimals = Integer.valueOf(exchangeArray.getJSONObject(i).get("decimal").toString());
      Assert.assertTrue(decimals >= 0 && decimals <= 18);
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String contractAddress = exchangeArray.getJSONObject(i).getString("contractAddress");
      Assert.assertTrue(patternAddress.matcher(contractAddress).matches() && !contractAddress.isEmpty());

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
