package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AssetList extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();
  JSONObject bodyObject = new JSONObject();
  List<String> trc10tokenList = new ArrayList<>();
  List<String> trc20tokenList = new ArrayList<>();


  @Test(enabled = true)
  public void assetList01(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","66f37xLdCz%2FV9geQGc%2FhYd98HR0%3D");
    params.put("address",addressNewAsset41);

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokenArray = dataContent.getJSONArray("token");
    int tokenNum = tokenArray.size();
    Assert.assertEquals(2,tokenNum);
    for(int i=0;i<tokenNum;i++){
      JSONObject token = tokenArray.getJSONObject(0);
      int type = token.getIntValue("type");
      String id = token.getString("id");
      String contractAddress = token.getString("contractAddress");
      switch (type){
        case 0:
          Assert.assertEquals("",id);
          Assert.assertEquals("",contractAddress);
          break;
        case 1:
          Assert.assertEquals("1002000",id);
          Assert.assertEquals("",contractAddress);
          break;
        case 2:
          Assert.assertEquals("",id);
          Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",contractAddress);
          break;
        default:
          break;
      }
      Assert.assertEquals(1,token.getLongValue("isOfficial"));
      Assert.assertTrue(token.getLongValue("balance")>0);
    }
  }


  @Test(enabled = true)
  public void assetList02(){
    params.clear();
    bodyObject.clear();
    trc10tokenList.clear();
    trc20tokenList.clear();

    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","j5nv8itgAnRloCHhkaxI4bFOdSs%3D");
    params.put("address",addressNewAsset41);

    trc10tokenList.add("1002000");
    bodyObject.put("trc10s",trc10tokenList);
    trc20tokenList.add("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    bodyObject.put("trc20s", trc20tokenList);
    bodyObject.put("addressType", "2");

    response = TronlinkApiList.v2AssetList(params, bodyObject);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    Assert.assertEquals(0,responseContent.getIntValue("code"));
    Assert.assertEquals("OK",responseContent.get("message"));

    dataContent = responseContent.getJSONObject("data");
    Assert.assertTrue(dataContent.containsKey("totalTRX"));
    JSONArray tokenJsonArray = dataContent.getJSONArray("token");
    JSONObject priceJsonArray = dataContent.getJSONObject("price");

    Assert.assertTrue(priceJsonArray.getFloat("priceUSD") > 0);
    Assert.assertTrue(priceJsonArray.getFloat("priceCny") > 0);

    for (int n = 0; n< tokenJsonArray.size(); n++){
      JSONObject tokenInfo = tokenJsonArray.getJSONObject(n);
      Assert.assertTrue(tokenInfo.containsKey("trxCount"));
      Assert.assertTrue(tokenInfo.containsKey("balance"));
      Assert.assertTrue(tokenInfo.containsKey("contractAddress"));
      Assert.assertTrue(tokenInfo.containsKey("precision"));
      Assert.assertTrue(tokenInfo.containsKey("totalSupply"));
      Assert.assertTrue(tokenInfo.containsKey("tokenDesc"));
      Assert.assertTrue(tokenInfo.containsKey("type"));

      int type = tokenInfo.getIntValue("type");
      String id = tokenInfo.getString("id");
      String contractAddress = tokenInfo.getString("contractAddress");
      switch (type){
        case 0:
          Assert.assertEquals("",id);
          Assert.assertEquals("",contractAddress);
          break;
        case 1:
          Assert.assertEquals("1002000",id);
          Assert.assertEquals("",contractAddress);
          break;
        case 2:
          Assert.assertEquals("",id);
          Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",contractAddress);
          break;
        default:
          break;
      }

    }

  }


}
