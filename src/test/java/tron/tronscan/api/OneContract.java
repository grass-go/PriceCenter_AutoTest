package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
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
 * @Date:2019-10-15 18:45
 */
@Slf4j
public class OneContract {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private String contractAddress = Configuration.getByPath("testng.conf")
          .getString("defaultParameter.contractAddress");

  /**
   * constructor.
   * 合约能量统计信息展示接口
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getOneContractEnergy() {
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    response = TronscanApiList.getOneContractEnergy(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() == 3);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalEnergy = Long.valueOf(responseContent.get("totalEnergy").toString());
    Assert.assertTrue(totalEnergy >= total && total>0);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //trx
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("trx").toString()) > 0);
      //day
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("day"));
      //total_energy
      Long total_energy = Long
          .valueOf(responseArrayContent.getJSONObject(i).get("total_energy").toString());
      Long energy = Long
          .valueOf(responseArrayContent.getJSONObject(i).get("energy").toString());
      Assert.assertTrue(total_energy >= energy);

    }
  }

  /**
   * constructor.单个合约调用统计Trigger信息
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getOneContractTrigger() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    response = TronscanApiList.getOneContractTrigger(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() == 3);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalAmount = Long.valueOf(responseContent.get("totalAmount").toString());

    Assert.assertTrue(totalAmount >= total && total >0);

    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //amount
      Assert.assertTrue(
          Double.valueOf(responseArrayContent.getJSONObject(i).get("amount").toString()) > 0);
      //day
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("day").toString()) > 0);
    }
  }

  /**
   * constructor.单个合约调用者统计Caller信息
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getOneContractCaller() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    response = TronscanApiList.getOneContractCaller(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() == 3);
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalCallerAmount = Long.valueOf(responseContent.get("totalCallerAmount").toString());

    Assert.assertTrue(totalCallerAmount >= total && total >0);
    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //amount
      Assert.assertTrue(
          Double.valueOf(responseArrayContent.getJSONObject(i).get("amount").toString()) > 0);
      //day
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("day").toString()) > 0);

    }

  }

  /**
   * constructor.一个合约的caller信息
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getCallers() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    response = TronscanApiList.getCallers(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() == 4);
    //total
    Long total = Long.valueOf(responseContent.get("total").toString());
    Long totalCallerAmount = Long.valueOf(responseContent.get("totalCallerAmount").toString());

    Assert.assertTrue(totalCallerAmount >= total && total >=0);

    //data list
    Assert.assertTrue(responseContent.containsKey("data"));

  }

  /**
   * constructor.单个合约调用者统计信息
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "")
  public void getCaller_value() {
    //Get response
    Map<String, String> params = new HashMap<>();
    params.put("address", contractAddress);
    response = TronscanApiList.getCaller_value(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //System status has 5 key:value
    Assert.assertTrue(responseContent.size() == 2);
    //data list
    responseArrayContent = responseContent.getJSONArray("data");

    Assert.assertTrue(responseArrayContent.size() > 0);
    for (int i = 0; i < responseArrayContent.size(); i++) {
      //amount
      Assert.assertTrue(
          Double.valueOf(responseArrayContent.getJSONObject(i).get("valueInTrx").toString()) >= 0);
      //day
      Assert.assertTrue(
          Long.valueOf(responseArrayContent.getJSONObject(i).get("day").toString()) > 0);
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
