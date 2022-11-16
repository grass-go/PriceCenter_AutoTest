package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;

import com.alibaba.fastjson.JSONPath;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.tron.common.utils.Base58;
import org.tron.common.utils.Commons;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

@Slf4j
public class multiTrxRecord extends TronlinkBase {
  private HttpResponse response;
  private JSONObject responseContent;
  private HashMap<String, String> param = new HashMap<>();
  private HashMap<String, String> header = new HashMap<>();
  private String fullnode = "47.75.245.225:50051";  //线上
  private ManagedChannel channelFull = null;
  private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;

  @BeforeClass(enabled = true)
  public void beforeClass() {

    channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
    blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
  }

  @Test(enabled = true, description = "Api multiTrxReword test", groups="multiSign")
  public void multiTrxRecord0() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("start", "0");
    param.put("limit", "10");
    param.put("state", "0");
    param.put("netType", "main_net");
    header.put("System","Android");
    header.put("Version","4.11.0");
    response = TronlinkApiList.multiTrxReword(param,header);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    TronlinkApiList.printJsonObjectContent(responseContent);
    Assert.assertTrue(responseContent.size() >= 3);
    Assert.assertTrue(responseContent.getString("message").equals("OK"));

    JSONArray array = responseContent.getJSONObject("data").getJSONArray("data");
    for (Object signatureProgress : array) {
      JSONObject info = (JSONObject) signatureProgress;
      Assert.assertEquals("0", info.getString("state"));
      Assert.assertTrue(!info.getJSONArray("signatureProgress").isEmpty());
      Assert.assertTrue(!info.getString("originatorAddress").isEmpty());
      Assert.assertTrue(!info.getString("currentWeight").isEmpty());
      Assert.assertTrue(!info.getString("contractType").isEmpty());
      Assert.assertTrue(!info.getString("expireTime").isEmpty());
      Assert.assertTrue(!info.getString("threshold").isEmpty());
      Assert.assertTrue(!info.getString("isSign").isEmpty());
      Assert.assertTrue(!info.getString("state").isEmpty());
      Assert.assertTrue(!info.getString("hash").isEmpty());
    }
  }

  @Test(enabled = true, description = "Api multiTrxReword test success", groups="multiSign")
  public void multiTrxRecord1() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("start", "0");
    param.put("limit", "10");
    param.put("state", "1");
    param.put("netType", "main_net");
    header.put("System","Android");
    header.put("Version","4.11.0");

    int index;
    for(index=0; index<5;index++){
      log.info("cur index is " + index);
      response = TronlinkApiList.multiTrxReword(param,header);
      if(response.getStatusLine().getStatusCode()==200)
      {
        index=6;
      }
      else{
        continue;
      }
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      TronlinkApiList.printJsonObjectContent(responseContent);
      Assert.assertTrue(responseContent.size() >= 3);
      Assert.assertTrue(responseContent.getString("message").equals("OK"));

      JSONArray array = responseContent.getJSONObject("data").getJSONArray("data");
      for (Object signatureProgress : array) {
        JSONObject info = (JSONObject) signatureProgress;
        Assert.assertEquals("1", info.getString("state"));
        Assert.assertTrue(!info.getJSONArray("signatureProgress").isEmpty());
        Assert.assertTrue(!info.getString("originatorAddress").isEmpty());
        Assert.assertTrue(!info.getString("currentWeight").isEmpty());
        Assert.assertTrue(!info.getString("contractType").isEmpty());
        Assert.assertTrue(!info.getString("expireTime").isEmpty());
        Assert.assertTrue(!info.getString("threshold").isEmpty());
        Assert.assertTrue(!info.getString("isSign").isEmpty());
        Assert.assertTrue(!info.getString("state").isEmpty());
        Assert.assertTrue(!info.getString("hash").isEmpty());
      }
    }
    Assert.assertEquals(7, index);

  }

  @Test(enabled = true, description = "Api multiTrxReword test fail", groups="multiSign")
  public void multiTrxRecord2() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("start", "0");
    param.put("limit", "10");
    param.put("state", "2");
    param.put("netType", "main_net");
    header.put("System","Android");
    header.put("Version","4.11.0");

    int index;
    for (index = 0; index < 5; index++) {
      log.info("cur index is " + index);
      response = TronlinkApiList.multiTrxReword(param,header);
      if (response.getStatusLine().getStatusCode() == 200) {
        index = 6;
      } else {
        continue;
      }
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      TronlinkApiList.printJsonObjectContent(responseContent);
      Assert.assertTrue(responseContent.size() >= 3);
      Assert.assertTrue(responseContent.getString("message").equals("OK"));

      JSONArray array = responseContent.getJSONObject("data").getJSONArray("data");
      for (Object signatureProgress : array) {
        JSONObject info = (JSONObject) signatureProgress;
        Assert.assertEquals("2", info.getString("state"));
        Assert.assertTrue(!info.getJSONArray("signatureProgress").isEmpty());
        Assert.assertTrue(!info.getString("originatorAddress").isEmpty());
        Assert.assertTrue(!info.getString("currentWeight").isEmpty());
        Assert.assertTrue(!info.getString("contractType").isEmpty());
        Assert.assertTrue(!info.getString("expireTime").isEmpty());
        Assert.assertTrue(!info.getString("threshold").isEmpty());
        Assert.assertTrue(!info.getString("isSign").isEmpty());
        Assert.assertTrue(!info.getString("state").isEmpty());
        Assert.assertTrue(!info.getString("hash").isEmpty());
      }
    }
    Assert.assertEquals(7,index);
  }

  //multi-v4.1.0-all the below cases
  @Test(enabled = true, description = "Api multiTrxReword test fail", groups="multiSign")
  public void multiTrxRecordLowVersionWithNoSig() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("start", "0");
    param.put("limit", "20");
    param.put("state", "255");
    param.put("netType", "main_net");

    int index;
    for (index = 0; index < 5; index++) {
      log.info("cur index is " + index);
      //v4.1.0
      response = TronlinkApiList.multiTrxRewordNoSig(param,null);
      if (response.getStatusLine().getStatusCode() == 200) {
        index = 6;
      } else {
        continue;
      }
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      TronlinkApiList.printJsonObjectContent(responseContent);
      Assert.assertTrue(responseContent.size() >= 3);
      Assert.assertTrue(responseContent.getString("message").equals("OK"));
    }
    Assert.assertEquals(7,index);
  }

  //multi-v4.1.0-all the below cases
  @Test(enabled = true, description = "Api multiTrxReword test fail", groups="multiSign")
  public void multiTrxRecordHighVersionWithNoSig() throws Exception {
    param.put("address", "TRqgwhHbfscXq3Ym3FJSFwxprpto1S4nSW");
    param.put("netType", "main_net");
    param.put("state", "255");
    param.put("start", "0");
    param.put("limit", "20");
    header.put("System", "iOS");
    header.put("Version", "4.11.0");

    int index;
    for (index = 0; index < 5; index++) {
      log.info("cur index is " + index);
      response = TronlinkApiList.multiTrxRewordNoSig(param,header);
      if (response.getStatusLine().getStatusCode() == 200) {
        index = 6;
      } else {
        continue;
      }
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      TronlinkApiList.printJsonObjectContent(responseContent);
      Assert.assertEquals(20004, responseContent.getIntValue("code"));
      Assert.assertEquals("Error param.", responseContent.getString("message"));
    }
    Assert.assertEquals(7,index);
  }

  //multi-v4.1.0
  @Test(enabled = true, description = "nulti sign send coin", groups="multiSign")
  public void testTrxRecord_with_raw_data_hex() throws Exception {
    //Step1: post one multisign transaction
    String address158= "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
    byte[] address1 = Commons.decode58Check(address158);
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2=TronlinkApiList.getFinalAddress(key2);
    String address258= Base58.encode(address2);
    String getAddress258_2=TronlinkApiList.encode58Check(address2);

    Protocol.Transaction transaction = TronlinkApiList
            .sendcoin(address2, 500_000, address1, blockingStubFull);
    log.info("-----original transaction: "+ JsonFormat.printToString(transaction));

    Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
            transaction, key2, 3, blockingStubFull);
    log.info("-----add one signature: "+JsonFormat.printToString(transaction1));

    //colculate raw_data_hex and add to transaction data.
    Object transaction_json = JSONObject.parse(JsonFormat.printToString(transaction1));
    JSONObject transaction_jsonobj = (JSONObject)transaction_json;
    String rawHex = TronlinkApiList.generateRawdataHex(transaction1);
    transaction_jsonobj.put("raw_data_hex","abc");
    Object transaction_commobj = (JSONObject) transaction_jsonobj;


    JSONObject object = new JSONObject();
    object.put("address",getAddress258_2);
    object.put("netType","main_net");
    object.put("transaction",transaction_commobj);

    param.put("address",getAddress258_2);
    header.put("System","Android");
    header.put("Version","4.11.0");
    response = TronlinkApiList.multiTransaction(object,param,header);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertEquals(0,responseContent.getIntValue("code"));

    //Step2: check trxRecord contains raw_data_hex and value correct.
    param.clear();
    param.put("address", "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8");
    param.put("start", "0");
    param.put("limit", "10");
    param.put("state", "4");
    param.put("netType", "main_net");
    header.clear();
    header.put("System","Android");
    header.put("Version","4.11.0");
    response = TronlinkApiList.multiTrxReword(param,header);
    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.size() >= 3);
    Assert.assertTrue(responseContent.getString("message").equals("OK"));
    Object raw_data_hex_response  = JSONPath.eval(responseContent,"$..data.data[0].currentTransaction.raw_data_hex[0]");
    log.info("raw_data_hex_response:"+raw_data_hex_response);
    Assert.assertEquals(rawHex, raw_data_hex_response.toString());
  }


}

