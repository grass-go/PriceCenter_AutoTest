package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class getCollectionInfos extends TronlinkBase {
  private JSONObject responseContent;
  private JSONArray dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();
  JSONObject bodyObject = new JSONObject();
  List<String> assetIdList = new ArrayList<>();

  @BeforeMethod
  void setUp(){
    params.put("address",quince_B58);

    bodyObject.clear();
    assetIdList.clear();
  }


  @Test
  public void getCollectionInfosManual() {
    //trc10tokenList.add("10000001");
    //trc10tokenList.add("100000");
    assetIdList.add("1");
    assetIdList.add("2");
    //trc10tokenList.add("10000003");
    //trc10tokenList.add("10000004");
    //trc10tokenList.add("10000005");
    bodyObject.put("assetIdList",assetIdList);
    //bodyObject.put("tokenAddress", "TUVGZFjjAhkYitwQmveGoCt7W4yNzbN5dY");
    bodyObject.put("tokenAddress", "TJg6fquXUXeQvRV6bdb8wNFqkCyuWSueT3");
    response = TronlinkApiList.v2GetCollectionInfos(params, bodyObject);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);


  }
}
