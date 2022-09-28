package tron.tronlink.old;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.api;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class getNoticeRemind extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();

  @Test(enabled = true, groups={"NoSignature"})
  public void GetNoticeRemindLowVersionWithNoSig(){
    response = TronlinkApiList.v1GetNoticeRemindNoSig(null);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertTrue(responseContent.getString("data").isEmpty());
  }

  @Test(enabled = true)
  public void GetNoticeRemindHighVerisonWithNoSig(){
    HashMap<String, String> header = new HashMap<>();
    header.put("Version",TronlinkApiList.androidUpdateVersion);
    header.put("System", TronlinkApiList.defaultSys);
    response = api.getCoinCapTrxPriceNoSig(header);
    response = TronlinkApiList.v1GetNoticeRemindNoSig(header);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(20004, responseContent.getIntValue("code"));
    Assert.assertEquals("Error param.", responseContent.getString("message"));
  }

  @Test(enabled = true)
  public void test01GetNoticeRemind(){
    HashMap<String, String> params = new HashMap<>();
    params.put("address", quince_B58);
    response = TronlinkApiList.v1GetNoticeRemind(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertTrue(responseContent.getString("data").isEmpty());
  }


  @Test(enabled = true)
  public void test02GetDappHistory(){
    JsonObject body = new JsonObject();
    body.addProperty("transactionString", "e75c536bb24bd5e5ae461afe00b70ee7df7d4395e1b10f181e1e7fcf2c1b2f67");
    body.addProperty("dappName","winke");
    body.addProperty("dappUrl","www.dappurle.com" );

    String dataRaw = "{\n"
        + "    \"transactionString\":\"{\\\"ret\\\":[{\\\"contractRet\\\":\\\"SUCCESS\\\"}],\\\"signature\\\":[\\\"5dc7a3337b0f85518a1072f10e3d3c6ee17f6eb0c495127aacf8c9c345a65c4e0515b0ca3554897d2cfabc50d6bd6005f60c496e83542d0a8aac92b270bf6f7300\\\"],\\\"txID\\\":\\\"cbedc3ca60b4716dc1469768e5dc6c93623da4ccf355dc1379284e08fc31336d\\\",\\\"raw_data\\\":{\\\"contract\\\":[{\\\"parameter\\\":{\\\"value\\\":{\\\"data\\\":\\\"a9059cbb000000000000000000000041159bbfd180869cb5c0e9595cf2227b6514710e5a0000000000000000000000000000000000000000000000000000000011e1a300\\\",\\\"owner_address\\\":\\\"4117858d6084980c642165eed70696fb1baa90ddce\\\",\\\"contract_address\\\":\\\"41a614f803b6fd780986a42c78ec9c7f77e6ded13c\\\"},\\\"type_url\\\":\\\"type.googleapis.com/protocol.TriggerSmartContract\\\"},\\\"type\\\":\\\"TriggerSmartContract\\\",\\\"Permission_id\\\":0}],\\\"ref_block_bytes\\\":\\\"aafa\\\",\\\"ref_block_hash\\\":\\\"f81be5b2f1729363\\\",\\\"expiration\\\":1621451779643,\\\"fee_limit\\\":7000000,\\\"timestamp\\\":1621415779643}}\",\n"
        + "    \"dappName\":\"JustSwap\",\n"
        + "    \"dappUrl\":\"https://justswap.network\"\n"
        + "}";
    response = TronlinkApiList.v1GetDappHistory((JSONObject) JSONObject.parse(dataRaw));
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertTrue(responseContent.containsKey("data"));

  }

  @Test(enabled = true)
  public void test03GetAnnouncement(){
    response = TronlinkApiList.v1GetAnnouncement();
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.getInteger("code") == 0);
    Assert.assertEquals(responseContent.getString("message"),"OK");
    Assert.assertTrue(responseContent.containsKey("data"));
    //Assert.assertTrue(responseContent.getJSONArray("data").getJSONObject(0).containsKey("pic_url"));

  }



}
