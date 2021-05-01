package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

public class getCollectionInfo extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();

  @Test
  public void getCollectionInfoTest001() {
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","A2epHTAV5h6STSjMQ9yC%2FReM0RA%3D");
    params.put("address",addressNewAsset41);
    params.put("tokenAddress","TPvGT3tWUNakTg23ARKMx46MGLT386nYWD");
    params.put("assetId","1");

    response = TronlinkApiList.v2GetCollectionInfo(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertEquals(0,(int)responseContent.get("code"));
    Assert.assertEquals("OK",responseContent.get("message"));
    dataContent = responseContent.getJSONObject("data");

    Assert.assertTrue(dataContent.containsKey("assetId"));
    Assert.assertTrue(dataContent.containsKey("assetUri"));
    Assert.assertTrue(dataContent.containsKey("name"));

    Assert.assertEquals(params.get("tokenAddress"), dataContent.get("tokenAddress"));
    Assert.assertEquals(200, TronlinkApiList.createGetConnect(dataContent.getString("imageUrl")).getStatusLine().getStatusCode());
    Assert.assertEquals(200, TronlinkApiList.createGetConnect(dataContent.getString("logoUrl")).getStatusLine().getStatusCode());


  }
}