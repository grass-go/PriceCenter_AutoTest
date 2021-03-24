package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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


}
