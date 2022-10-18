package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AccountList extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject responseContent1;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONArray array = new JSONArray();

  @Test(enabled = true, groups = {"NoSignature"})
  public void accountListLowVersionWithNoSignature(){
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject.put(queryAddress58,1);
    array.add(jsonObject);
    jsonObject1.put(queryAddress58,3);
    array.add(jsonObject1);
    response = TronlinkApiList.accountListNoSig(array,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
  }

  @Test(enabled = true)
  public void accountListHighVersionWithNoSignature(){
    Map<String, String> header = new HashMap<String, String>();
    header.put("System","Chrome");
    header.put("Version","4.1.0");
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject.put(queryAddress58,1);
    array.add(jsonObject);
    jsonObject1.put(queryAddress58,3);
    array.add(jsonObject1);
    response = TronlinkApiList.accountListNoSig(array,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }




  @Test(enabled = true)
  public void accountList(){
    Map<String, String> params = new HashMap<String, String>();
    params.put("address",queryAddress58);
    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject.put(queryAddress58,1);
    array.add(jsonObject);
    jsonObject1.put(queryAddress58,3);
    array.add(jsonObject1);

    response = TronlinkApiList.accountList(array,params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    Assert.assertTrue(dataContent.getLongValue("totalBalance") > 0);

    BigDecimal sum = new BigDecimal("0");
    JSONArray balanceList =  dataContent.getJSONArray("balanceList");
    Assert.assertTrue(balanceList.size() == 2);
    for(int i=0;i<balanceList.size();i++){
      JSONObject ob = balanceList.getJSONObject(i);
      if(ob.getIntValue("addressType") == 1){
        sum = sum.add(new BigDecimal(ob.getDoubleValue("balance")));
      }
    }
    Assert.assertTrue(sum.subtract(new BigDecimal(dataContent.getLongValue("totalBalance"))).intValue() == 0);
  }





  @Test(enabled = false)
  public void comparteBalanceStr() throws Exception {
    Map<String, String> paramsInURL = new HashMap<>();
    paramsInURL.put("address", queryAddressTxt41);
    response = TronlinkApiList.assetlist(queryAddressTxt41, paramsInURL);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent1 = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject targetContent1 = responseContent1.getJSONObject("data");
    String assetBalance = targetContent1.getString("totalTRX");

    JSONObject jsonObject = new JSONObject();
    jsonObject.put(queryAddress58,1);
    array.clear();
    array.add(jsonObject);
    response = TronlinkApiList.accountList(array,paramsInURL);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    dataContent = responseContent.getJSONObject("data");
    String accountBalance = dataContent.getString("totalBalance");

    log.info("-------assetBalance: "+assetBalance+"  accountBalance： "+accountBalance);
    Assert.assertEquals(assetBalance,accountBalance);
  }

}
