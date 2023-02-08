package tron.tronlink.v2.nft;

import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
//import tron.tronlink.base.GetSign;
import tron.tronlink.base.TronlinkBase;
import tron.common.utils.RetryListener;

@Slf4j
public class getCollectionInfo extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();

  @Test(enabled = true,retryAnalyzer = RetryListener.class)
  @Parameters({"trc721OwnerAddress","trc721TokenAddress","trc721AssetId"})
  public void getCollectionInfoTest001(String trc721OwnerAddress,String trc721TokenAddress,String trc721AssetId) throws Exception {
    params.put("address", quince_B58);
    params.put("tokenAddress", "TJg6fquXUXeQvRV6bdb8wNFqkCyuWSueT3");
    params.put("assetId", "1");

    response = TronlinkApiList.v2GetCollectionInfo(params);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

  }
}
