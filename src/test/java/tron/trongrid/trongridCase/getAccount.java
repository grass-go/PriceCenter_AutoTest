package tron.trongrid.trongridCase;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class getAccount extends Base {

    JSONObject getAccountBody;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account from trongrid")
  public void test01GetAccountFromTrongrid() {
    response = getAccount(queryAddress);
    responseContent = parseResponseContent(response);
    getAccountBody = responseContent;
    //printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() > 10);
    Assert.assertTrue(responseContent.getLong("balance") >  0);
    Assert.assertEquals(responseContent.getString("create_time"),"1588824369000");
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account from trongrid solidity")
  public void test01GetAccountFromTrongridSolidity() {
    response = getAccount(queryAddress,true);
    responseContent = parseResponseContent(response);
    //printJsonContent(responseContent);
    Assert.assertEquals(responseContent,getAccountBody);
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
