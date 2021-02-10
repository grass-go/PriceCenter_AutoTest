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

@Slf4j
public class AccountList extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject responseContent1;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONArray array = new JSONArray();


  @Test(enabled = true)
  public void accountList(){

    JSONObject jsonObject = new JSONObject();
    JSONObject jsonObject1 = new JSONObject();
    jsonObject.put(queryAddress58,1);
    array.add(jsonObject);
    jsonObject1.put(queryAddress58,3);
    array.add(jsonObject1);
    response = TronlinkApiList.accountList(array);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
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
    response = TronlinkApiList.assetlist(queryAddressTxt41);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent1 = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject targetContent1 = responseContent1.getJSONObject("data");
    String assetBalance = targetContent1.getString("totalTRX");

    JSONObject jsonObject = new JSONObject();
    jsonObject.put(queryAddress58,1);
    array.clear();
    array.add(jsonObject);
    response = TronlinkApiList.accountList(array);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    dataContent = responseContent.getJSONObject("data");
    String accountBalance = dataContent.getString("totalBalance");

    log.info("-------assetBalance: "+assetBalance+"  accountBalance： "+accountBalance);
    Assert.assertEquals(assetBalance,accountBalance);
  }

}
