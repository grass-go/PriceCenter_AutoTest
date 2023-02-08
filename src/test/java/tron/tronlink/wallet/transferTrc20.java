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
  private HashMap<String,String> header = new HashMap<>();

  @Test(enabled = true,description = "get trx20 transaction",groups={"NoSignature"})
  public void getTrc20TransferLowVersionWithNoSig() throws Exception {
    //param.put("address","4199AB9DF0BAB6385C5ECADE8B0F7A7E914502F6FC"); //sophia's address
    param.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
    param.put("limit","50");
    param.put("start","0");
    param.put("direction","0");
    param.put("reverse","true");
    param.put("db_version","1");
    param.put("filter_small","true");
    //param.put("trc20Id","TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh");
    param.put("trc20Id","TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");
    header.clear();
    header.put("system","chrome");
    header.put("Version","3.27.2");
    response = TronlinkApiList.apiTransferTrc20NoSig(param,null);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertNotEquals(null, responseContent);



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
  public void Test000getTrc20TransferManual() throws Exception {
    //param.put("address","4199AB9DF0BAB6385C5ECADE8B0F7A7E914502F6FC"); //sophia's address
    param.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
    param.put("limit","50");
    param.put("start","0");
    param.put("direction","0");
    param.put("reverse","true");
    param.put("db_version","1");
    param.put("filter_small","true");
    //param.put("trc20Id","TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh");
    param.put("trc20Id","TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");
    //header.clear();
    //header.put("System","Chrome");
    header.put("Version","4.11.3");

    response = TronlinkApiList.apiTransferTrc20(param,header);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertNotEquals(null, responseContent);
    responseArrayContent = responseContent.getJSONArray("data");



  }
}
