package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;

import java.util.HashMap;

public class transferToken10 {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private HashMap<String,String> param = new HashMap<>();
  private HashMap<String,String> headers = new HashMap<>();

  @Test(enabled = true,description = "get token10 transaction",groups={"NoSignature"})
  public void getToken10TransferLowVersionWithNoSigManual() throws Exception {
    //param.put("address","TGEMwfQC8LQJ1nUzmVgmF66f3VtuQMR56F"); //sophia's address
    param.put("address","41E7D71E72EA48DE9144DC2450E076415AF0EA745F");
    //param.put("address","TGEMwfQC8LQJ1nUzmVgmF66f3VtuQMR56F");
    param.put("limit","50");
    param.put("start","0");
    param.put("direction","0");
    param.put("reverse","true");
    param.put("fee","true");
    param.put("filter_small","true");
    //param.put("trc10Id","1000323");
    param.put("trc10Id","1004777");
    param.put("db_version","1");
    //headers.put("System","Chrome");
    headers.put("Version","4.11.3");
    response = TronlinkApiList.apiTransferToken10NoSig(param,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);


  }
  @Test(enabled = true,description = "get token10 transaction")
  public void getToken10TransferHighVersionWithNoSig() throws Exception {
    param.put("address","TH48niZfbwHMyqZwEB8wmHfzcvR8ZzJKC6"); //sophia's address
    param.put("limit","20");
    param.put("start","0");
    param.put("direction","0");
    param.put("reverse","true");
    param.put("trc10Id","1002881");
    HashMap<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = TronlinkApiList.apiTransferToken10NoSig(param,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));

  }
  @Test(enabled = true,description = "get token10 transaction")
  public void Test000getToken10TransferManual() throws Exception {
    //param.put("address","TGEMwfQC8LQJ1nUzmVgmF66f3VtuQMR56F"); //sophia's address
    param.put("address","41E7D71E72EA48DE9144DC2450E076415AF0EA745F");
    //param.put("address","TGEMwfQC8LQJ1nUzmVgmF66f3VtuQMR56F");
    param.put("limit","50");
    param.put("start","0");
    param.put("direction","0");
    param.put("reverse","true");
    param.put("fee","true");
    param.put("filter_small","true");
    //param.put("trc10Id","1000323");
    param.put("trc10Id","1004777");
    param.put("db_version","1");
    headers.clear();
    headers.put("System","Chrome");
    headers.put("Version","3.27.2");

    response = TronlinkApiList.apiTransferToken10(param,headers);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertNotEquals(null, responseContent);
    responseArrayContent = responseContent.getJSONArray("data");
    Assert.assertNotEquals(responseArrayContent, null);


  }
}
