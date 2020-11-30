package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class queryNode extends fullOrSolidityBase {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List nodes from trongrid")
  public void test01ListNodesFromTrongrid() {
    response = listNodes();
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    JSONArray nodeArray = responseContent.getJSONArray("nodes");
    Assert.assertTrue(nodeArray.size()  > 20);
    Assert.assertTrue(nodeArray.getJSONObject(0).getJSONObject("address").containsKey("port")
        && nodeArray.getJSONObject(0).getJSONObject("address").containsKey("host"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get node info from trongrid")
  public void test02GetNodeInfoFromTrongrid() {
    response = getNodeInfo();
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.containsKey("totalFlow"));
    Assert.assertTrue(responseContent.containsKey("solidityBlock"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get chain parameters from trongrid")
  public void test03GetChainParametersFromTrongrid() {
    response = getChainParameters();
    Assert.assertEquals(200,response.getStatusLine().getStatusCode());
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    JSONArray chainParametersArray = responseContent.getJSONArray("chainParameter");
    Assert.assertTrue(chainParametersArray.size() > 28);
    JSONObject mainTenanceTimeJsonObject = new JSONObject();
    mainTenanceTimeJsonObject.put("value",21600000);
    mainTenanceTimeJsonObject.put("key","getMaintenanceTimeInterval");
    Assert.assertTrue(chainParametersArray.contains(mainTenanceTimeJsonObject));

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
