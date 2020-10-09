package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

/**
 * ${params}
 *
 * @Author:jh
 * @Date:2019-08-29 19:03
 */
public class OverViewList {

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
   * 账户列表展示接口
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Blockchain data overview in history")
  public void getOverViewList() {
    int day = 1;
    Map<String, String> params = new HashMap<>();
    params.put("days",Integer.toString(day));
    response = TronscanApiList.getOverViewList(tronScanNode,params);
    //log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //two object "success" and "data"
    Assert.assertTrue(Boolean.valueOf(responseContent.getString("success")));
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //data
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("date"));
      //avgBlockTime
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("avgBlockTime").toString()) > 0);
      //totalBlockCount 区块数
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("totalBlockCount").toString()) >= 0);
      //totalTransaction 交易数
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("totalTransaction").toString()) >= 0);
      //blockchainSize
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("blockchainSize").toString()) >= 0);
      //avgBlockSize
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("avgBlockSize").toString()) > 10);
      //
      Assert.assertFalse(exchangeArray.getJSONObject(i).get("newTransactionSeen").toString().isEmpty());
      Assert.assertFalse(exchangeArray.getJSONObject(i).get("newAddressSeen").toString().isEmpty());
      Assert.assertFalse(exchangeArray.getJSONObject(i).get("totalAddress").toString().isEmpty());
      Assert.assertFalse(exchangeArray.getJSONObject(i).get("newBlockSeen").toString().isEmpty());
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