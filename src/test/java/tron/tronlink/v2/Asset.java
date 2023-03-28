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
  public void asset01Manual(){
    //params.put("address","TKGRE6oiU3rEzasue4MsB6sCXXSTx9BAe3");
    params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
    //params.put("address","TSbfmgnSs3KgW9yPJB33QKZ6DKJLPVpR7L");
    //.put("tokenAddress","TGjgvdTWWrybVLaVeFqSyVqJQWjxqRYbaK");
    params.put("tokenAddress","TU4zZAaKMdNGX4gwDhP3yz1zXZ5Z9UezxL"); //nile
    //params.put("tokenAddress","TKD31dvCMiKkqYSagVGMgiw9FaDME2WZkV"); //trc721 prod
    //params.put("tokenAddress","TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"); //trc20
    params.put("tokenType","2");
    response = TronlinkApiList.v2Asset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
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
      params.put("address",addressNewAsset41);
      params.put("tokenAddress",token);
      params.put("tokenType","2");
      response = TronlinkApiList.v2Asset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object national = JSONPath.eval(responseContent,"$..national[0]");
      Assert.assertEquals("DM", national);
    }
  }

  @Test(enabled = true, description = "test token add national field")
  public void asset02NonNational(){
    List<String> tokenList = new ArrayList<>();
/*    tokenList.add("TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR");  //WTRX
    tokenList.add("TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");  //BTC
    tokenList.add("THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");  //TUSD
    tokenList.add("TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");  //USDC
    tokenList.add("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");  //WIN*/

    tokenList.add("_");

    for (String token:tokenList){
      params.put("address",quince_B58);
      params.put("tokenAddress",token);
      params.put("tokenType","2");
      response = TronlinkApiList.v2Asset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object national = JSONPath.eval(responseContent,"$..national[0]");
      Assert.assertEquals("", national);
    }
  }

}
