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
  List<String> trc10tokenList = new ArrayList<>();

  @BeforeMethod(alwaysRun = true)
  void setUp(){
    params.put("address",commonUser41);

    bodyObject.clear();
    trc10tokenList.clear();
  }


  @Test
  public void getCollectionInfosTest001() {
    bodyObject.put("address", "TAYzcfLovWdV83g25Apfd7BA67J44D5z5M");

    trc10tokenList.add("1");
    trc10tokenList.add("133");
    trc10tokenList.add("134");
    bodyObject.put("assetIdList",trc10tokenList);
    bodyObject.put("tokenAddress", "TPvGT3tWUNakTg23ARKMx46MGLT386nYWD");
    response = TronlinkApiList.v2GetCollectionInfos(params, bodyObject);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONArray("data");

    for (int n = 0; n<dataContent.size(); n++){
      JSONObject nftInfo = (JSONObject)dataContent.get(n);
      Assert.assertEquals("TPvGT3tWUNakTg23ARKMx46MGLT386nYWD", nftInfo.get("tokenAddress"));
      Assert.assertTrue(nftInfo.containsKey("assetId"));
      Assert.assertTrue(nftInfo.containsKey("assetUri"));

      Assert.assertEquals(200, TronlinkApiList.createGetConnect(nftInfo.getString("imageUrl"),null,null,null).getStatusLine().getStatusCode());
      Assert.assertEquals(200, TronlinkApiList.createGetConnect(nftInfo.getString("logoUrl"),null,null,null).getStatusLine().getStatusCode());
    }


  }
}
