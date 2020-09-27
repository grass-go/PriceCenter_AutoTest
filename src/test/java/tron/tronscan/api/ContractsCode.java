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
public class ContractsCode {

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
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get a single contract's abi & byteCode")
  public void test01getContractsCode() {
    //Get response
    String address = "TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3";
    Map<String, String> params = new HashMap<>();
    params.put("contract", address);
    response = TronscanApiList.getContractCode(tronScanNode, params);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //status object
    targetContent = responseContent.getJSONObject("status");
    Assert.assertTrue(targetContent.containsKey("code"));
    Assert.assertTrue(targetContent.containsKey("message"));

    //data object
    targetContent = responseContent.getJSONObject("data");
    Assert.assertEquals(address, targetContent.getString("address"));
    Assert.assertTrue(!targetContent.getString("name").isEmpty());
    Assert.assertTrue(!targetContent.getString("byteCode").isEmpty());
    Assert.assertTrue(!targetContent.getString("abi").isEmpty());
    Assert.assertTrue(!targetContent.getString("creator").isEmpty());
  }


  /**
   * constructor.
   * 合约展示
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "Get a single contract's detail ")
  public void getContractTest() {
    //Get response
    String address = "TEEXEWrkMFKapSMJ6mErg39ELFKDqEs6w3";
    Map<String, String> Params = new HashMap<>();
    Params.put("contract", address);
    response = TronscanApiList.getContractTest(tronScanNode, Params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    JSONObject responseObject = responseContent.getJSONObject("status");
    Assert.assertTrue(responseObject.containsKey("code"));
    Assert.assertTrue(responseObject.containsKey("message"));

    responseArrayContent = responseContent.getJSONArray("data");
    targetContent = responseArrayContent.getJSONObject(0);
    Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
    String addressOb = targetContent.getString("address");
    Assert.assertTrue(patternAddress.matcher(addressOb).matches());
    Assert.assertEquals(addressOb,address);

    Assert.assertTrue(targetContent.containsKey("creator"));
//    Assert.assertTrue(targetObject.containsKey("balance"));
    Assert.assertTrue(Double.valueOf(targetContent.get("balance").toString()) > 100000);
    Assert.assertTrue(!targetContent.get("verify_status").toString().isEmpty());
    Assert.assertTrue(!targetContent.get("balanceInUsd").toString().isEmpty());
    Assert.assertTrue(Double.valueOf(targetContent.get("trxCount").toString()) > 100000);
    Assert.assertTrue(!targetContent.get("date_created").toString().isEmpty());
    Assert.assertTrue(Double.valueOf(targetContent.get("call_value").toString()) >= 0);
    Assert.assertTrue(!targetContent.get("name").toString().isEmpty());
    JSONObject targetObject = targetContent.getJSONObject("creator");
    //creator Object
      String creator_address = targetObject.getString("address");
      Assert.assertTrue(patternAddress.matcher(creator_address).matches());
      Pattern patternHash = Pattern.compile("^[a-z0-9]{64}");
      Assert.assertTrue(patternHash.matcher(targetObject.getString("txHash")).matches());
      Assert.assertTrue(targetObject.containsKey("address_is_contract"));
      Assert.assertTrue(Double.valueOf(targetObject.get("token_balance").toString()) >= 0);
      Assert.assertTrue(Double.valueOf(targetObject.get("consume_user_resource_percent").toString()) >= 0);

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }

}
