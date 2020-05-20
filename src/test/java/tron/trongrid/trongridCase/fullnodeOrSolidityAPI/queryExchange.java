package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class queryExchange extends fullOrSolidityBase {
  JSONObject exchangeJsonBody;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get exchange by id from trongrid")
  public void test01GetExchangeFromTrongrid() {
    response = getExchangeById(bttVsTrxExchange);
    responseContent = parseResponseContent(response);
    exchangeJsonBody = responseContent;
    printJsonContent(responseContent);
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "List exchanges from trongrid")
  public void test02ListExchangesFromTrongrid() {
    response = listExchanges();
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    JSONArray exchangsArray = responseContent.getJSONArray("exchanges");
    Assert.assertTrue(exchangsArray.contains(exchangeJsonBody));

  }



  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    try {
      disConnect();
    } catch (Exception e) {

    }
  }
}
