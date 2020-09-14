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
 * @Date:2019-10-09 16:03
 */
@Slf4j
public class SearchList {

  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "搜索框查询接口")
  public void getSearch() {
    Map<String, String> Params = new HashMap<>();
    Params.put("term", "1");
    response = TronscanApiList.getSearch(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseArrayContent = TronscanApiList.parseArrayResponseContent(response);
    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("value"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("desc"));
    }
  }


  /**
   * constructor.
   * 热门通证、热门合约查看
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "查看热门token、热门合约")
  public void getSearchHot() {

    response = TronscanApiList.getSearchHot(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 2);
    //hot_tokens
    JSONArray exchangeArray = responseContent.getJSONArray("hot_tokens");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //vip_token
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("vip_token"));
      //icon
      String icon_key = exchangeArray.getJSONObject(i).get("icon").toString();
      Assert.assertTrue(!icon_key.isEmpty());
      HttpResponse httpResponse = TronscanApiList.getUrlkey(icon_key);
      Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);

      //name
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("name").toString().isEmpty());
      //type
      Integer type = Integer.valueOf(exchangeArray.getJSONObject(i).get("type").toString());
      Assert.assertTrue(type == 10 || type == 20);
      if(type == 20){
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i).getString("id")).matches());
      }else{
        Assert.assertTrue(exchangeArray.getJSONObject(i).getLong("id") > 1000000);
      }
      //abbr
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("abbr").toString().isEmpty());

    }
    //hot_contracts
    JSONArray contractArray = responseContent.getJSONArray("hot_contracts");
    for (int i = 0; i < contractArray.size(); i++) {
      //verify_status
      Assert.assertTrue(contractArray.getJSONObject(i).containsKey("verify_status"));
      //name
      Assert.assertTrue(!contractArray.getJSONObject(i).get("name").toString().isEmpty());
      //contract_address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(contractArray.getJSONObject(i).getString("contract_address")).matches());
    }

  }

  /**
   * constructor.
   * 搜索框热门token查看
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "搜索框热门token查看")
  public void getSearchBar() {
    response = TronscanApiList.getSearchBar(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseArrayContent = TronscanApiList.parseArrayResponseContent(response);
    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //vip_token
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("vip_token"));
      //icon
      String icon_key = responseArrayContent.getJSONObject(i).get("icon").toString();
      Assert.assertTrue(!icon_key.isEmpty());
      HttpResponse httpResponse = TronscanApiList.getUrlkey(icon_key);
      Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      //name
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("name").toString().isEmpty());
      //type
      Integer type = Integer.valueOf(responseArrayContent.getJSONObject(i).get("type").toString());
      Assert.assertTrue(type == 10 || type == 20);
      if(type == 20){
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("token_id")).matches());
      }else{
        Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("token_id") > 1000000);
      }
      //abbr
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("abbr").toString().isEmpty());

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
