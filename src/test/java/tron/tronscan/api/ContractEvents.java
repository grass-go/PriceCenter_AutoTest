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
public class ContractEvents {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String,String> testAccount;

  /**
   * constructor.
   * 原是合约中的20转账接口，前端现改为token_trc20/transfer接口
   * 此接口现保持当前状态
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get contract events")
  public void getContractEvents() {
    //Get response
    String keyword = "TBfHSbt7rARV5P9FiZfWQE9Z5a9NVypqbz";
    Map<String, String> params = new HashMap<>();
    params.put("contract", keyword);
    params.put("start", "0");
    params.put("limit", "20");
    params.put("start_timestamp", "1548000000000");
    params.put("end_timestamp", "1548056638507");
    response = TronscanApiList.getContractEvents(tronScanNode,params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    JSONArray exchangeArray = responseContent.getJSONArray("data");
    for (int i = 0; i < exchangeArray.size(); i++) {
      //amount
//      targetContent = exchangeArray.getJSONObject(0);
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("amount").toString()) >= 0);

      //transferFromAddress
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i)
              .getString("transferFromAddress")).matches());

      //data
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("data"));

      //decimals
      Integer decimals = Integer.valueOf(exchangeArray.getJSONObject(i).get("decimals").toString());
      Assert.assertTrue(decimals >= 0 && decimals <= 18);

      //tokenName
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("tokenName"));

      //transferToAddress
      Assert.assertTrue(patternAddress.matcher(exchangeArray.getJSONObject(i)
              .getString("transferToAddress")).matches());

      //block
      Assert.assertTrue(Integer.valueOf(exchangeArray.getJSONObject(i).get("block").toString()) > 1000000);

      //confirmed
      Assert.assertTrue(Boolean.valueOf(exchangeArray.getJSONObject(i).getString("confirmed")));

      //transactionHash
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("transactionHash").toString().isEmpty());

      //timestamp
      Assert.assertTrue(!exchangeArray.getJSONObject(i).get("timestamp").toString().isEmpty());
    }
  }


  /**
   * constructor.
   *
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get contract events")
  public void getInterchain_event() {
    //Get response
    response = TronscanApiList.getInterchain_event(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }


}
