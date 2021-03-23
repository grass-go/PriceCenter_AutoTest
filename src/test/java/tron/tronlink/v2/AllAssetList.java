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
public class AllAssetList extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject object;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  @Test(enabled = false)
  public void allAssetList01(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","3ePuP28sQRThx9WrDajgcec4NlI%3D");
    params.put("address",addressNewAsset41);
    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    int count =dataContent.getIntValue("count");
    Assert.assertEquals(3,count);
    array = dataContent.getJSONArray("token");
    int type=0;
    for(int j=0;j<count;j++){
      object = array.getJSONObject(j);
      type=object.getIntValue("type");
      if (type==1){
        Assert.assertEquals("1002000",object.getString("id"));
      }else if (type==2){
        Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",object.getString("contractAddress"));
      }
      Assert.assertTrue(object.getLongValue("balance")>0);
    }

  }



}
