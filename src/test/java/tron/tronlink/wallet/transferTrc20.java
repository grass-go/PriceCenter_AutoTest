package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class transferTrc20 {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;

  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();

  @Test(enabled = true,description = "get trx20 transaction",groups={"NoSignature"})
  public void getTrc20TransferLowVersionWithNoSig() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","2");
    param.put("reverse","true");
    param.put("trc20Id","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
    int index;

    for(index=0; index<5; index++){
      log.info("Test000getTrc20Transfer cur index is " + index);
      response = TronlinkApiList.apiTransferTrc20NoSig(param,null);
      if(response.getStatusLine().getStatusCode() == 200)
      {
        index = 6;
      }
      else {
        Thread.sleep(1000);
        continue;
      }
    }

    Assert.assertEquals(7,index);

    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertNotEquals(null, responseContent);
    responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("block_timestamp"));
      Assert.assertTrue(jsonObject.containsKey("amount"));
      Assert.assertTrue(jsonObject.getInteger("amount")>0);
      Assert.assertTrue(jsonObject.containsKey("block"));
      Assert.assertTrue(jsonObject.containsKey("from"));
      Assert.assertTrue(jsonObject.containsKey("to"));
      Assert.assertTrue(jsonObject.containsKey("hash"));
      Assert.assertTrue(jsonObject.containsKey("confirmed"));
      Assert.assertTrue(jsonObject.containsKey("contract_type"));
      Assert.assertTrue(jsonObject.containsKey("issue_address"));
      Assert.assertTrue(jsonObject.containsKey("decimals"));
      Assert.assertTrue(jsonObject.containsKey("token_name"));
      Assert.assertTrue(jsonObject.containsKey("direction"));
    }
  }
  @Test(enabled = true,description = "get trx20 transaction")
  public void getTrc20TransferHighVersionWithNoSig() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","2");
    param.put("reverse","true");
    param.put("trc20Id","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
    HashMap<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = TronlinkApiList.apiTransferTrc20NoSig(param, header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));

  }

  @Test(enabled = true,description = "get trx20 transaction")
  public void Test000getTrc20Transfer() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","2");
    param.put("reverse","true");
    param.put("trc20Id","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
    int index;

    for(index=0; index<5; index++){
      log.info("Test000getTrc20Transfer cur index is " + index);
      response = TronlinkApiList.apiTransferTrc20(param);
      if(response.getStatusLine().getStatusCode() == 200)
      {
        index = 6;
      }
      else {
        Thread.sleep(1000);
        continue;
      }
    }

    Assert.assertEquals(7,index);

    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertNotEquals(null, responseContent);
    responseArrayContent = responseContent.getJSONArray("data");

    //data object
    for (Object json:responseArrayContent) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      Assert.assertTrue(jsonObject.containsKey("block_timestamp"));
      Assert.assertTrue(jsonObject.containsKey("amount"));
      Assert.assertTrue(jsonObject.getInteger("amount")>0);
      Assert.assertTrue(jsonObject.containsKey("block"));
      Assert.assertTrue(jsonObject.containsKey("from"));
      Assert.assertTrue(jsonObject.containsKey("to"));
      Assert.assertTrue(jsonObject.containsKey("hash"));
      Assert.assertTrue(jsonObject.containsKey("confirmed"));
      Assert.assertTrue(jsonObject.containsKey("contract_type"));
      Assert.assertTrue(jsonObject.containsKey("issue_address"));
      Assert.assertTrue(jsonObject.containsKey("decimals"));
      Assert.assertTrue(jsonObject.containsKey("token_name"));
      Assert.assertTrue(jsonObject.containsKey("direction"));
    }
  }
}
