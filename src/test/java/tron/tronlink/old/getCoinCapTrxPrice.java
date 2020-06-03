package tron.tronlink.old;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;
import tron.common.api;

public class getCoinCapTrxPrice {

  private HttpResponse response;
  @Test(enabled = true, description = "Api /api/v1/wallet/getCoinCapTrxPrice  test")
  public void test001CoinCapTrxPrice() throws Exception {
    response = api.getCoinCapTrxPrice();

    JSONObject jsonObject = api.parseResponseContent(response);
    api.printJsonContent(jsonObject);

    String data = jsonObject.getString("data");
    JSONObject trxData = (JSONObject) JSON.parse(data);

    Assert.assertTrue(!trxData.getString("price_usd").isEmpty());
    Assert.assertTrue(!trxData.getString("price_cny").isEmpty());
  }
}

