package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Asset extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject object;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  @Test(enabled = true)
  public void asset01(){
    params.put("address",commonUser41);
    params.put("tokenAddress","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("tokenType","2");
    response = TronlinkApiList.v2Asset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",dataContent.getString("contractAddress"));
    Assert.assertEquals(2,dataContent.getIntValue("type"));
    Assert.assertEquals(0,dataContent.getIntValue("top"));
    Assert.assertEquals(0,dataContent.getIntValue("recommandSortId"));
  }

  @Test(enabled = true, description = "test token add national field")
  public void asset01National(){
    List<String> nationalList = new ArrayList<>();
    nationalList.add("TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");  //USDD
    nationalList.add("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");  //USDT
    nationalList.add("TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");  //TUSD
    nationalList.add("TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9");  //JST
    nationalList.add("TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq");  //NFT
    nationalList.add("TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");  //BTT

    for (String token:nationalList){
      params.put("address",commonUser41);
      params.put("tokenAddress",token);
      params.put("tokenType","2");
      response = TronlinkApiList.v2Asset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object national = JSONPath.eval(responseContent,"$..national[0]");
      Assert.assertEquals("DM", national);
    }
  }

  @Test(enabled = true, description = "test token add national field and defiType=0")
  public void asset02NonNational(){
    List<String> tokenList = new ArrayList<>();
    tokenList.add("TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR");  //WTRX
    tokenList.add("TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");  //BTC
    tokenList.add("THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");  //TUSD
    tokenList.add("TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");  //USDC
    tokenList.add("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");  //WIN

    for (String token:tokenList){
      params.put("address",commonUser41);
      params.put("tokenAddress",token);
      params.put("tokenType","2");
      response = TronlinkApiList.v2Asset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object national = JSONPath.eval(responseContent,"$..national[0]");
      Assert.assertEquals("", national);
      //v4.11.0-done
      Object defiType = JSONPath.eval(responseContent,"$..defiType[0]");
      Assert.assertEquals("0", defiType.toString());

    }
  }
  //v4.11.0-done
  @Test(enabled = true, description = "test jtoken defiType=1")
  public void asset02jTokenDefiType(){
    List<String> tokenList = new ArrayList<>();
    tokenList.add("TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");  //jTRX
    tokenList.add("TFpPyDCKvNFgos3g3WVsAqMrdqhB81JXHE");  //jNFT
    tokenList.add("TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv");  //jWin
    tokenList.add("TWQhCXaWz4eHK4Kd1ErSDHjMFPoPc9czts");  //jJST
    tokenList.add("TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt");  //jWBTT

    for (String token:tokenList){
      params.put("address",commonUser41);
      params.put("tokenAddress",token);
      params.put("tokenType","2");
      response = TronlinkApiList.v2Asset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object defiType = JSONPath.eval(responseContent,"$..defiType[0]");
      Assert.assertEquals("1", defiType.toString());
    }
  }
  //v4.11.0-done
  @Test(enabled = true, description = "test lp token defiType=2")
  public void asset02LPTokenDefiType(){
    List<String> tokenList = new ArrayList<>();
    tokenList.add("TQn9Y2khEsLJW1ChVWFMSMeRDow5KcbLSE");  //S-USDT-TRX
    tokenList.add("TUEYcyPAqc4hTg1fSuBCPc18vGWcJDECVw");  //S-SUNOLD-TRX
    tokenList.add("TM8WFPUimbkZuTfV7SL1ZQ9JFUoYPotViW");  //S-POSCHE-TRX
    tokenList.add("TYN6Wh11maRfzgG7n5B6nM5VW1jfGs9chu");  //S-WIN-TRX
    tokenList.add("TY69bLD2CWAHGTWKokr1hwWcVJLF9j6g45");  //S-HX-TRX

    for (String token:tokenList){
      params.put("address",commonUser41);
      params.put("tokenAddress",token);
      params.put("tokenType","2");
      response = TronlinkApiList.v2Asset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object defiType = JSONPath.eval(responseContent,"$..defiType[0]");
      Assert.assertEquals("2", defiType.toString());
    }
  }

}
