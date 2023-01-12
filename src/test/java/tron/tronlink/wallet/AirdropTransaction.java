/*
package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AirdropTransaction extends TronlinkBase {

  private Map<String,String> params = new HashMap<>();
  private HttpResponse response;
  private JSONObject responseContent;

  @Test(enabled = false)
  public void test000getAirdropTransaction(){
    params.put("address",queryAddress);
    response = TronlinkApiList.getAirdropTransaction(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    log.info("responseContent: "+ responseContent.toJSONString());
  }
}
*/
