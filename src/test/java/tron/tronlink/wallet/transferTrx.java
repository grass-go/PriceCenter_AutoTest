package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class transferTrx {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;

  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();


  @Test(enabled = true,description = "get only trx transaction",groups={"NoSignature"})
  public void getTrxTransferLowVersionWithNoSigManual() throws Exception {
    //param.put("address","TGEMwfQC8LQJ1nUzmVgmF66f3VtuQMR56F"); //sophia's address
    //param.put("address","TPFfHr1CWfTcS9eugQXQmvqHNGufnjxjXP");
    //param.put("address" ,"TMCT99jfDE3u5CNxH6Pk9H9YntbL1itxHB");

    param.put("limit","50");
    param.put("start","0");
    param.put("direction","0"); //0：send and accept
    param.put("reverse","true");
    param.put("db_version","1");
    param.put("filter_small","true");
    param.put("fee","true");
    HashMap<String, String> header = new HashMap<>();
    //header.put("Version","4.11.15");
    //header.put("System", "iOS");

    response = TronlinkApiList.apiTransferTrxNoSig(param,null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    org.testng.Assert.assertNotEquals(responseContent, null);
    responseArrayContent = responseContent.getJSONArray("data");

  }
  @Test(enabled = true,description = "get only trx transaction")
  public void Test000getTrxTransferHighVersionWithNoSig() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","0"); //0：send and accept
    param.put("reverse","true");
    HashMap<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = TronlinkApiList.apiTransferTrxNoSig(param, header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }
  @Test(enabled = true,description = "get only trx transaction")
  public void Test000getTrxTransfer() throws Exception {
    param.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t"); //sophia's address
    //param.put("address","TPFfHr1CWfTcS9eugQXQmvqHNGufnjxjXP");
    //param.put("address" ,"TMCT99jfDE3u5CNxH6Pk9H9YntbL1itxHB");
    param.put("limit","40");
    param.put("start","0");
    param.put("direction","0"); //0：send and accept
    param.put("reverse","true");
    param.put("db_version","1");
    param.put("filter_small","true");
    //param.put("fee","true");
    HashMap<String, String> header = new HashMap<>();
    header.put("Version","4.11.3");
    header.put("System", "iOS");

    response = TronlinkApiList.apiTransferTrx(param,header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);


    //data object
  }
}
