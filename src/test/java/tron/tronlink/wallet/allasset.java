package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;

public class allasset {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);

  @Test(enabled = true)
  public void Test000GetAllAsset(){

    response = TronlinkApiList.allasset("TAVNk5hkaPNJcTf6TvJVgBWEaRhuiHE5Ab");
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    responseArrayContent = responseContent.getJSONArray("data");
    //data object
    boolean TG4 =false,TG12 =false,TG18 =false;;

    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("type"));
      Assert.assertTrue(jsonObject.containsKey("top"));
      Assert.assertTrue(jsonObject.containsKey("isOfficial"));
      Assert.assertTrue(jsonObject.containsKey("name"));
      Assert.assertTrue(jsonObject.containsKey("shortName"));
      Assert.assertTrue(jsonObject.containsKey("id"));
      Assert.assertTrue(jsonObject.containsKey("contractAddress"));
      String contractAddress = jsonObject.getString("contractAddress");
      if("TKTwvJhHAfiUNMrdnWoYkoUY9meYnTgtdk".equals(contractAddress)){
        TG12 = true;
      }else if("TWPGEQ6iD4t2BrSMi1Lwj8Pha5GpGEXLay".equals(contractAddress)){
        TG18 = true;
      }else if("TQ8kZgvhcP6cHZTPLEJbBojsycZKb8V2wL".equals(contractAddress)){
        TG4 = true;
      }
      Assert.assertTrue(jsonObject.containsKey("balance"));
      Assert.assertTrue(jsonObject.containsKey("totalBalance"));
      Assert.assertTrue(jsonObject.containsKey("logoUrl"));
      Assert.assertTrue(jsonObject.containsKey("precision"));
      Assert.assertTrue(jsonObject.containsKey("marketId"));
      Assert.assertTrue(jsonObject.containsKey("price"));
      Assert.assertTrue(jsonObject.containsKey("trxCount"));
      Assert.assertTrue(jsonObject.containsKey("inMainChain"));
      Assert.assertTrue(jsonObject.containsKey("inSideChain"));
    }
    System.out.println("TG12:"+ TG12+"  TG18: "+TG18  + "TG4: " + TG4);
    Assert.assertTrue(TG4&&TG12&&TG18);
    System.out.println(responseArrayContent.size());
  }
}
