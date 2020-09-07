package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class MarketFavoritor {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true)
  public void Test000getMarketFavoritor() throws Exception {
    param.put("token","TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9_T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb&" +
            "token=TVgAYofpQku5G4zenXnvxhbZxpzzrk8WVK_T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb" +
            "&token=T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb_TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t" +
            "&token=TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT_TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t" +
            "&token=1002000_TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t" +
            "&token=TTVTdn8ipmacfKsCHw5Za48NRnaBRKeJ44_TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"); //win
    response = TronlinkApiList.walletMarketFavorite(param);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    JSONObject data = responseContent.getJSONObject("data");
    Assert.assertTrue(data.containsKey("total"));
    Assert.assertTrue(data.getInteger("total")==6);
    responseArrayContent = data.getJSONArray("rows");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("fTokenName"));
      Assert.assertTrue(jsonObject.containsKey("sTokenName"));
      Assert.assertTrue(jsonObject.containsKey("fShortName"));
      Assert.assertTrue(jsonObject.containsKey("sShortName"));
      Assert.assertTrue(jsonObject.containsKey("fTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("sTokenAddr"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("sPrecision"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("unit"));
      Assert.assertTrue(jsonObject.containsKey("volume"));
      Assert.assertTrue(jsonObject.containsKey("volume24h"));
      Assert.assertTrue(jsonObject.containsKey("gain"));
    }
  }
}
