package tron.trongrid.trongridCase.ethAPI;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class ethBlockNumber extends V1Base {
    JSONObject getEthBlockNumberBody;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Eth api of eth_blockNumber")
  public void test01GetEthBlockNumber() {
    JsonObject ethJsonObj = new JsonObject();
    ethJsonObj.addProperty("jsonrpc","2.0");
    ethJsonObj.addProperty("method","eth_blockNumber");
    ethJsonObj.add("params",new JsonArray());
    ethJsonObj.addProperty("id",2);

    getEthBlockNumberBody = getEthApi(ethJsonObj);
    printJsonContent(getEthBlockNumberBody);

    Long nowBlockNumberFromEthApi = Long.parseLong(getEthBlockNumberBody
        .getString("result").substring(2), 16);

    response = getNowBlock(false);
    responseContent = parseResponseContent(response);
    Long nowBlockNumberFromFullnode = responseContent.getJSONObject("block_header")
        .getJSONObject("raw_data").getLong("number");



    Assert.assertTrue(Math.abs(nowBlockNumberFromEthApi - nowBlockNumberFromFullnode) <= 5);
  }


  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    try {
      disConnect();
    } catch (Exception e) {

    }
  }
}
