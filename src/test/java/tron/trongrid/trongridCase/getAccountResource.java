package tron.trongrid.trongridCase;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class getAccountResource extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get account resource from trongrid")
  public void test01GetAccountResourceFromTrongrid() {
    response = getAccountResource(queryAddress);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.size() > 3);
    Assert.assertEquals(responseContent.getString("freeNetLimit"),"5000");
    Assert.assertEquals(responseContent.getString("TotalNetLimit"),"43200000000");
  }
}
