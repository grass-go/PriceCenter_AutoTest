package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class NodeInfo extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray targetData;
  private HttpResponse response;


  @Test(enabled = true)
  public void test000GetNodeInfo(){
    JSONArray array = new JSONArray();
    JSONObject ob1 = new JSONObject();
    ob1.put(queryAddress58,2);
    JSONObject ob2 = new JSONObject();
    ob2.put(queryAddressTH48,2);
    array.add(ob1);
    array.add(ob2);
    response = TronlinkApiList.getNodeInfo(array);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    targetData = responseContent.getJSONArray("data");
    for (int i = 0; i < targetData.size(); i++){
        JSONObject ob = targetData.getJSONObject(i);
        if ("MainChain".equals(ob.getString("chainName"))){
            Assert.assertEquals("",ob.getString("chainId"));
            Assert.assertEquals("",ob.getString("sideChainContract"));
            Assert.assertEquals("",ob.getString("mainChainContract"));
            Assert.assertEquals("1",ob.getString("isMainChain"));

        } else {
            Assert.assertEquals("DAppChain", ob.getString("chainName"));
            Assert.assertNotEquals("",ob.getString("chainId"));
            Assert.assertNotEquals("",ob.getString("sideChainContract"));
            Assert.assertNotEquals("",ob.getString("mainChainContract"));
            Assert.assertEquals("0",ob.getString("isMainChain"));
        }
        ob.containsKey("serviceTime");
        Assert.assertEquals("",ob.getString("eventServer"));
        Assert.assertTrue(ob.containsKey("fullNode"));
        Assert.assertTrue(ob.getString("fullNode").length() > 0);
        Assert.assertTrue(ob.containsKey("solidityNode"));
        Assert.assertTrue(ob.getString("solidityNode").length() > 0);
    }

  }
}
