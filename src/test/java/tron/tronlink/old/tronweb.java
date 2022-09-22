package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

public class tronweb extends TronlinkBase {
  private HttpResponse response;
  private JSONObject responseContent;

  @Test(enabled = true, description = "Api /api/web/v1/tronweb test")
  public void test001Tronweb() throws Exception {
    response = api.tronweb();
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

}

