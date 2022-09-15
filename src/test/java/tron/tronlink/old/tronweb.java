package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;
import tron.common.TronlinkApiList;

public class tronweb extends TronlinkBase {
  private HttpResponse response;
  private JSONObject responseContent;

  @Test(enabled = true, description = "Api /api/web/v1/tronweb test")
  public void test001Tronweb() throws Exception {
    response = TronlinkApiList.tronweb(quince_B58, "iOS");
    JSONObject tronwebData = api.parseResponseContent(response).getJSONObject("data");
    api.printJsonContent(tronwebData);
    JSONObject tronWebParams = tronwebData.getJSONObject("tronWebParams");
   /* Assert.assertEquals(tronWebParams.getString("sunWebHash"),"46011df65eb7cc24cb25884d2d8c506f");
    Assert.assertEquals(tronWebParams.getString("sunWebUrl"), "https://static.poloniex.org/SunWeb-1.1.js");
    Assert.assertEquals(tronWebParams.getString("tronWebHash"),"2e6e226697d803f10a1bbdc1295cf3e8");
    Assert.assertEquals(tronWebParams.getString("tronWebUrl"),"https://tronweb.tronlink.org/mTronWeb-4.3.0.1.js");

    JSONObject balanceLimit = tronwebData.getJSONObject("balanceLimit");
    Assert.assertEquals(balanceLimit.getIntValue("assetValueLimit"),1);
    Assert.assertEquals(balanceLimit.getIntValue("assetThousandthLimit"), 1);
    Assert.assertEquals(balanceLimit.getIntValue("nftCountLimit"), 1);

    JSONObject accountActiveFeeParams = tronwebData.getJSONObject("accountActiveFeeParams");
    Assert.assertEquals(accountActiveFeeParams.getString("baseActiveFee"),"0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("extraActiveFee"), "1");
    Assert.assertEquals(accountActiveFeeParams.getString("feeBandwidth"), "0.001");
    Assert.assertEquals(accountActiveFeeParams.getString("feeEnergy"), "0.00028");
    Assert.assertEquals(accountActiveFeeParams.getString("freeNetLimit"), "1500");
    Assert.assertEquals(accountActiveFeeParams.getString("dappBaseActiveFee"), "0.1");
    Assert.assertEquals(accountActiveFeeParams.getString("dappExtraActiveFee"),"0");
    Assert.assertEquals(accountActiveFeeParams.getString("dappFeeBandwidth"),"0.000010");
    Assert.assertEquals(accountActiveFeeParams.getString("triggerType"),"2");

    JSONObject dappScore = tronwebData.getJSONObject("dappScore");
    Assert.assertEquals(dappScore.getString("dappAccess"),"1");
    Assert.assertEquals(dappScore.getString("dappSign"), "15");
    Assert.assertEquals(dappScore.getString("walletConnect"), "10");
    Assert.assertEquals(dappScore.getString("wane"), "2");

    JSONObject swapParams = tronwebData.getJSONObject("swapParams");
    Assert.assertEquals(swapParams.getString("justFee"),"0.003");

    JSONObject transferCost = tronwebData.getJSONObject("transferCost");
    Assert.assertEquals(transferCost.getString("energyCost"),"20000");
    Assert.assertEquals(transferCost.getString("netCost"), "345");
  }*/
  }



}

