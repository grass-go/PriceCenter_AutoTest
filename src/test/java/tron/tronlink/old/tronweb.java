package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

public class tronweb extends TronlinkBase {
  private HttpResponse response;
  private JSONObject responseContent;
  HashMap<String, String> params = new HashMap<>();
  HashMap<String, String> headers = new HashMap<>();

  @Test(enabled = true, description = "Api /api/web/v1/tronweb test",  groups = {"NoSignature"})
  public void test001TronwebLowVersionWithNoSig() throws Exception {
    response = api.tronwebNoSig(null);
    JSONObject tronwebData = api.parseResponse2JsonObject(response).getJSONObject("data");
    api.printJsonObjectContent(tronwebData);
    JSONObject balanceLimit = tronwebData.getJSONObject("balanceLimit");
    Assert.assertEquals(balanceLimit.getIntValue("assetValueLimit"),1);
    Assert.assertEquals(balanceLimit.getIntValue("assetThousandthLimit"), 1);
    Assert.assertEquals(balanceLimit.getIntValue("nftCountLimit"), 1);

    JSONObject accountActiveFeeParams = tronwebData.getJSONObject("accountActiveFeeParams");
    Assert.assertEquals(accountActiveFeeParams.getString("baseActiveFee"),"0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("extraActiveFee"), "1");
    Assert.assertEquals(accountActiveFeeParams.getString("feeBandwidth"), "0.001");
    Assert.assertEquals(accountActiveFeeParams.getString("freeNetLimit"), "1500");
    Assert.assertEquals(accountActiveFeeParams.getString("dappBaseActiveFee"), "0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("dappExtraActiveFee"),"0");
    Assert.assertEquals(accountActiveFeeParams.getString("dappFeeBandwidth"),"0.000010");

  }

  @Test(enabled = true, description = "Api /api/web/v1/tronweb test")
  public void test001TronwebHighVersionWithNoSig() throws Exception {
    headers.put("System", "Android");
    headers.put("Version","4.14.0");
    response = api.tronwebNoSig(headers);
    JSONObject tronwebData = api.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, tronwebData.getIntValue("code"));
    Assert.assertEquals("Error param.", tronwebData.getString("message"));
  }

  @Test(enabled = true, description = "Api /api/web/v1/tronweb test")
  public void test001Tronweb() throws Exception {
    params.put("address", "41E7D71E72EA48DE9144DC2450E076415AF0EA745F");
    headers.put("System", "Android");
    headers.put("Version","4.14.0");
    response = api.tronweb(params,headers);
    JSONObject tronwebData = api.parseResponse2JsonObject(response).getJSONObject("data");
    /*api.printJsonObjectContent(tronwebData);
    JSONObject balanceLimit = tronwebData.getJSONObject("balanceLimit");
    Assert.assertEquals(balanceLimit.getIntValue("assetValueLimit"),1);
    Assert.assertEquals(balanceLimit.getIntValue("assetThousandthLimit"), 1);
    Assert.assertEquals(balanceLimit.getIntValue("nftCountLimit"), 1);

    JSONObject accountActiveFeeParams = tronwebData.getJSONObject("accountActiveFeeParams");
    Assert.assertEquals(accountActiveFeeParams.getString("baseActiveFee"),"0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("extraActiveFee"), "1");
    Assert.assertEquals(accountActiveFeeParams.getString("feeBandwidth"), "0.001");
    Assert.assertEquals(accountActiveFeeParams.getString("freeNetLimit"), "1500");
    Assert.assertEquals(accountActiveFeeParams.getString("dappBaseActiveFee"), "0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("dappExtraActiveFee"),"0");
    Assert.assertEquals(accountActiveFeeParams.getString("dappFeeBandwidth"),"0.000010");*/

  }

  @Test(enabled = true, description = "manual test")
  public void test001TronwebManualTest() throws Exception {
    params.put("address", quince_B58);
    headers.put("System", "Android");
    headers.put("Version","4.14.0");
    response = api.tronweb(params,headers);
    JSONObject tronwebData = api.parseResponse2JsonObject(response).getJSONObject("data");
    //api.printJsonObjectContent(tronwebData);
    /*JSONObject balanceLimit = tronwebData.getJSONObject("balanceLimit");
    Assert.assertEquals(balanceLimit.getIntValue("assetValueLimit"),1);
    Assert.assertEquals(balanceLimit.getIntValue("assetThousandthLimit"), 1);
    Assert.assertEquals(balanceLimit.getIntValue("nftCountLimit"), 1);

    JSONObject accountActiveFeeParams = tronwebData.getJSONObject("accountActiveFeeParams");
    Assert.assertEquals(accountActiveFeeParams.getString("baseActiveFee"),"0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("extraActiveFee"), "1");
    Assert.assertEquals(accountActiveFeeParams.getString("feeBandwidth"), "0.001");
    Assert.assertEquals(accountActiveFeeParams.getString("freeNetLimit"), "1500");
    Assert.assertEquals(accountActiveFeeParams.getString("dappBaseActiveFee"), "0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("dappExtraActiveFee"),"0");
    Assert.assertEquals(accountActiveFeeParams.getString("dappFeeBandwidth"),"0.000010");*/
  }

}

