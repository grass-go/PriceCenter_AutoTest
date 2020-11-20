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
public class TokensList {

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
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = " List all the trc10 tokens in the blockchain")
  public void test01getTokensList() {
    //Get response
    Map<String, String> params = new HashMap<>();
    int limit = 20;
    String status = "ico";
    params.put("sort", "-name");
    params.put("limit", String.valueOf(limit));
    params.put("start", "0");
    params.put("totalAll", "1");
    params.put("status", status);
    response = TronscanApiList.getTokensList(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalAll = Long.valueOf(responseContent.get("totalAll").toString());
    Assert.assertTrue(totalAll >= total);
    //object data
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertEquals(limit, responseArrayContent.size());
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("totalTransactions") >= 0);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("country"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("tokenID") >= 1000000);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("participated") > 1000000000);
      Assert.assertTrue(responseArrayContent.getJSONObject(i)
              .getInteger("precision") >= 0 && responseArrayContent.getJSONObject(i).getInteger("precision") <= 7);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("num") >= 1);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("available"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("reputation"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("description").isEmpty());
      Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("issuedPercentage")) <= 100);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("nrOfTokenHolders"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("voteScore"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("dateCreated").isEmpty());

      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("price") >= 1);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("percentage"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("startTime").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("id") >= 1000000);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("issued").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("trxNum") >= 1000000);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("abbr").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("website"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("github"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("availableSupply"));
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("totalSupply").toString()) >= 1000);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("index"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("frozenTotal") >= 0);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("frozen"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("canShow"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("remaining"));
      Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getString("remaining")) >= 0);
      String url_key = responseArrayContent.getJSONObject(i).get("url").toString();
//      if (url_key != "https://www.hashcoins.com/"){
//        Assert.assertTrue(!url_key.isEmpty());
//        HttpResponse httpResponse = TronscanApiList.getUrlkey(url_key);
//        Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
//      }else{
        Assert.assertTrue(!url_key.isEmpty());
//      }
      //
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("frozenPercentage"));
      //
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("isBlack"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("remainingPercentage"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("name").isEmpty());
      String ownerAddress = responseArrayContent.getJSONObject(i).getString("ownerAddress");
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(ownerAddress).matches() && !ownerAddress.isEmpty());
      //
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("endTime").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("white_paper"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("social_media").isEmpty());
    }
  }

  /**
   * constructor.根据tokenName获取token的信息
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get token holders balance by token name (Deprecated)")
  public void getTokensAddress() {
    Map<String, String> params = new HashMap<>();
    String tokenName = "USDT";
    params.put("tokenName", tokenName);
    response = TronscanApiList.getTokensAddress(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);
    //data
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //name
      Assert.assertEquals(exchangeArray.getJSONObject(i).getString("name"),"USDT");
      //balance
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("balance").toString()) >= 1);
      //address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String address = exchangeArray.getJSONObject(i).getString("address");
      Assert.assertTrue(patternAddress.matcher(address).matches() && !address.isEmpty() );

    }
  }

  /**
     * constructor.获取trc10 token持有者
     */
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get token holders of a trc10 token;")
    public void getTokenholders() {
      String address = "TF5Bn4cJCT6GVeUgyCN4rBhDg42KBrpAjg";
      Map<String, String> params = new HashMap<>();
      params.put("address", address);
      response = TronscanApiList.getTokenholders(tronScanNode, params);
      Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
      responseContent = TronscanApiList.parseResponseContent(response);
      TronscanApiList.printJsonContent(responseContent);
      //total
      Long total = Long.valueOf(responseContent.get("total").toString());
      Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
      Assert.assertTrue(rangeTotal >= total);
      //data list
      responseArrayContent = responseContent.getJSONArray("data");

      Assert.assertTrue(responseArrayContent.size() > 0);
      for (int i = 0; i < responseArrayContent.size(); i++) {
        //name
        Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("name").toString().isEmpty());
        //balance
        Assert.assertTrue(
                Long.valueOf(responseArrayContent.getJSONObject(i).get("balance").toString()) >= 1000000000);
        //address
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        Assert.assertTrue(
                patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("address")).matches());

      }
  }

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getPosition_distribution() {
   //
    response = TronscanApiList.getPosition_distribution(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
  }

  /**
   * constructor
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get token holders of a trc10 token;")
  public void getPrice() {
    //
    response = TronscanApiList.getPrice(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    Assert.assertTrue(Double.valueOf(responseContent.get("price_in_eth").toString()) > 0);
    Assert.assertTrue(Double.valueOf(responseContent.get("price_in_trx").toString()) > 0);
    Assert.assertTrue(Double.valueOf(responseContent.get("price_in_eur").toString()) > 0);
    Assert.assertTrue(Double.valueOf(responseContent.get("price_in_btc").toString()) > 0);
    Assert.assertTrue(!responseContent.getString("token").isEmpty());
    Assert.assertTrue(!responseContent.getString("from").isEmpty());
    Assert.assertTrue(!responseContent.getString("token").isEmpty());
    //
    Assert.assertTrue(Double.valueOf(responseContent.get("percent_change_1h").toString()) > -1);
    Assert.assertTrue(responseContent.containsKey("percent_change_24h"));
    Assert.assertTrue(Double.valueOf(responseContent.get("price_in_usd").toString()) > 0);
    Assert.assertTrue(Double.valueOf(responseContent.get("volume_24h").toString()) > 0);
    Assert.assertTrue(responseContent.containsKey("percent_change_7d"));
  }

  /**
   * constructor
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getStatistic() {
    //
    response = TronscanApiList.getStatistic(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
  }

  /**
   * constructor
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getId_mapper() {
    //
    response = TronscanApiList.getId_mapper(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //
    responseArrayContent = responseContent.getJSONArray("data");
    targetContent = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(!targetContent.getString("address").isEmpty());
    Assert.assertTrue(!targetContent.getString("mapper_id").isEmpty());
    Assert.assertTrue(!targetContent.getString("status").isEmpty());
  }

  /**
   * constructor
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getVolume() {
    //
    response = TronscanApiList.getVolume(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(Long.valueOf(responseContent.getString("total")) > 0);
    //
    responseArrayContent = responseContent.getJSONArray("data");
    targetContent = responseArrayContent.getJSONObject(0);
    Assert.assertTrue(!targetContent.getString("volume").isEmpty());
    Assert.assertTrue(Double.valueOf(targetContent.getString("high")) > 0);
    Assert.assertTrue(Double.valueOf(targetContent.getString("market_cap")) >= 0);
    Assert.assertTrue(Double.valueOf(targetContent.getString("low")) > 0);
    Assert.assertTrue(!targetContent.getString("time").isEmpty());
    Assert.assertTrue(!targetContent.getString("source").isEmpty());
    Assert.assertTrue(Double.valueOf(targetContent.getString("close")) > 0);
    Assert.assertTrue(Double.valueOf(targetContent.getString("open")) > 0);
  }
  /**
   * constructor
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getVolume_sourceList() {
    //
    response = TronscanApiList.getVolume_sourceList(tronScanNode);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
//    TronscanApiList.printJsonContent(responseContent);

  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
