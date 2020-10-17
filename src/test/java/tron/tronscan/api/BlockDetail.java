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
public class BlockDetail {

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
   * constructor
   * 区块展示接口
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get a single block's detail")
  public void test01getBlockDetail() {
    //Get response
    Map<String, String> params = new HashMap<>();
    String blockNumber = "458888";
    params.put("sort", "-number");
    params.put("limit", "20");
    params.put("count", "true");
    params.put("start", "0");
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total > 0);

    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      String hash = responseArrayContent.getJSONObject(i).getString("hash");
      Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());

      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("size").toString()) > 0);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("txTrieRoot").toString().isEmpty());
      //parentHash
      String parentHash = responseArrayContent.getJSONObject(i).getString("parentHash");
      Assert.assertTrue(patternHash.matcher(parentHash).matches() && !parentHash.isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("witnessId"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("nrOfTrx"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String witnessAddress = responseArrayContent.getJSONObject(i).getString("witnessAddress");
      Assert.assertTrue(patternAddress.matcher(witnessAddress).matches() && !witnessAddress.isEmpty());
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("number").toString()) >1000000);

    }
  }
  /**
   * constructor limit不为零
   * 查寻时间范围内数据
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the blocks in the blockchain")
  public void test02getBlocksList() {
    //Get response
    int limit = 11;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-number");
    params.put("limit", String.valueOf(limit));
    params.put("count", "true");
    params.put("start", "20");
    params.put("start_timestamp", "1551715200000");
    params.put("end_timestamp", "1551772172616");
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
   //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total > 0);

    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      String hash = responseArrayContent.getJSONObject(i).getString("hash");
      Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());

      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("size").toString()) > 0);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("txTrieRoot").toString().isEmpty());
      //parentHash
      String parentHash = responseArrayContent.getJSONObject(i).getString("parentHash");
      Assert.assertTrue(patternHash.matcher(parentHash).matches() && !parentHash.isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("witnessId"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("nrOfTrx"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String witnessAddress = responseArrayContent.getJSONObject(i).getString("witnessAddress");
      Assert.assertTrue(patternAddress.matcher(witnessAddress).matches() && !witnessAddress.isEmpty());
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("number").toString()) >1000000);
    }
  }

  /**
   * constructor 查询当前时间范围内的数据
   * 翻页到0页，显示data为0
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List the blocks in the blockchain")
  public void test03getBlocksList() {
    //Get response
    int limit = 0;
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-number");
    params.put("limit", String.valueOf(limit));
    params.put("count", "true");
    params.put("start", "20");
    params.put("start_timestamp", "1551715200000");
    params.put("end_timestamp", "1551772172616");
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() == 3);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total);

  }

  /**
   * constructor.
   * producer为出块者，进入出块者详情页
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List all the blocks produced by the specified SR in the blockchain")
  public void getBlocksList() {
    //Get response
    int limit = 20;
    String address = "TLyqzVGLV1srkB7dToTAEqgDSfPtXRJZYH";
    Map<String, String> params = new HashMap<>();
    params.put("sort", "-number");
    params.put("limit", String.valueOf(limit));
    params.put("count", "true");
    params.put("start", "0");
    params.put("producer", address);

    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total > 0);

    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("hash"));
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      String hash = responseArrayContent.getJSONObject(i).getString("hash");
      Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());

      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("size").toString()) > 0);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("timestamp").toString().isEmpty());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("txTrieRoot").toString().isEmpty());
      //parentHash
      String parentHash = responseArrayContent.getJSONObject(i).getString("parentHash");
      Assert.assertTrue(patternHash.matcher(parentHash).matches() && !parentHash.isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("witnessId"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("nrOfTrx"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("confirmed"));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String witnessAddress = responseArrayContent.getJSONObject(i).getString("witnessAddress");
      Assert.assertTrue(patternAddress.matcher(witnessAddress).matches() && !witnessAddress.isEmpty());
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).get("number").toString()) >1000000);
    }
  }

  /**
   * constructor
   * 区块展示接口
   * 根据区块名称，进入区块详情页
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get a single block's detail")
  public void test01getBlockDetail05() {
    //Get response
    Map<String, String> params = new HashMap<>();
    String blockNumber = "458888";
    params.put("sort", "-number");
    params.put("limit", "20");
    params.put("count", "true");
    params.put("start", "0");
    params.put("number", blockNumber);
    response = TronscanApiList.getBlockDetail(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long rangeTotal = Long.valueOf(responseContent.get("rangeTotal").toString());
    Assert.assertTrue(rangeTotal >= total && total > 0);

    responseArrayContent = responseContent.getJSONArray("data");
    targetContent = responseArrayContent.getJSONObject(0);
      Assert.assertTrue(targetContent.containsKey("hash"));
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      String hash = targetContent.getString("hash");
      Assert.assertTrue(patternHash.matcher(hash).matches() && !hash.isEmpty());

      Assert.assertTrue(Long.valueOf(targetContent.get("size").toString()) > 0);
      Assert.assertTrue(!targetContent.get("timestamp").toString().isEmpty());
      Assert.assertTrue(!targetContent.get("txTrieRoot").toString().isEmpty());
      //parentHash
      String parentHash = targetContent.getString("parentHash");
      Assert.assertTrue(patternHash.matcher(parentHash).matches() && !parentHash.isEmpty());
      Assert.assertTrue(targetContent.containsKey("witnessId"));
      Assert.assertTrue(targetContent.containsKey("nrOfTrx"));
      Assert.assertTrue(targetContent.containsKey("confirmed"));
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      String witnessAddress = targetContent.getString("witnessAddress");
      Assert.assertTrue(patternAddress.matcher(witnessAddress).matches() && !witnessAddress.isEmpty());
      Assert.assertTrue(Long.valueOf(targetContent.get("number").toString()) ==458888);
      Assert.assertEquals(blockNumber, targetContent.getString("number"));

  }
  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
