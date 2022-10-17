package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.CompareJson;
import tron.tronlink.base.TronlinkBase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AssetList extends TronlinkBase {
  private JSONObject responseContent;
  private String responseString;
  private JSONObject dataContent;
  private JSONObject object;
  private HttpResponse response;
  Map<String, String> params = new HashMap<>();
  JSONObject bodyObject = new JSONObject();
  List<String> trc10tokenList = new ArrayList<>();
  List<String> trc20tokenList = new ArrayList<>();


  //v4.2.1 new user(not even have transfer trx),with parameter version=v1, will return trx only.
  @SneakyThrows
  @Test(enabled = true)
  public void assetList01(){
    char cbuf[] = new char[5000];
    InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/new1_assetList_v1_exp.json")),"UTF-8");
    int len =input.read(cbuf);
    String expResponse =new String(cbuf,0,len);

    params.put("address",new1_B58);
    params.put("version","v1");

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseString = TronlinkApiList.parseResponse2String(response);

    String cmp_result = new CompareJson("data","priceCny,priceUSD").compareJson(responseString, expResponse);
    System.out.println("=========actual response========== "+responseString+" ");
    System.out.println("=========expect response========== "+expResponse+" ");
    System.out.println("=========cmp_result=============== "+cmp_result);
    Assert.assertEquals("null",cmp_result);

    Object national = JSONPath.eval(responseContent, "$..data.token[0].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='Decentralized USD'].national[0]");
    Assert.assertEquals("DM",national.toString());

  }

  //v4.2.1 new user(not even have transfer trx),with parameter version=v2, will return trx only.
  @SneakyThrows
  @Test(enabled = false)
  public void assetList02(){
    char cbuf[] = new char[5000];
    InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/new1_assetList_v2_exp.json")),"UTF-8");
    int len =input.read(cbuf);
    String expResponse =new String(cbuf,0,len);

    params.put("address",new1_B58);
    params.put("version","v2");

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    responseString = TronlinkApiList.parseResponse2String(response);

    String cmp_result = new CompareJson("data","priceCny,priceUSD,price").compareJson(responseString, expResponse);
    System.out.println("=========actual response========== "+responseString+" ");
    System.out.println("=========expect response========== "+expResponse+" ");
    System.out.println("=========cmp_result=============== "+cmp_result);
    Assert.assertEquals("null",cmp_result);

    Object USDTprice = JSONPath.eval(responseContent, "$..data.token[name='Tether USD'].price");
    JSONArray USDTPriceArray=(JSONArray)USDTprice;
    BigDecimal actualTUSDPrice = (BigDecimal) USDTPriceArray.get(0);
    int usdtflag = actualTUSDPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(usdtflag > 0);
  }

  //v4.2.1 old user the first token is TRX. Others order by trxCount.
  @Test(enabled = false)
  public void assetList03() {

    params.clear();
    params.put("address", quince_B58);
    params.put("version", "v2");
    response = TronlinkApiList.v2AssetList(params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokenArray = new JSONArray();
    tokenArray = dataContent.getJSONArray("token");
    int count = tokenArray.size();
    Assert.assertEquals(0,tokenArray.getJSONObject(0).getIntValue("type"));
    Assert.assertEquals(0,tokenArray.getJSONObject(0).getIntValue("top"));
    Assert.assertEquals("",tokenArray.getJSONObject(0).getString ("name"));

    for (int j = 1; j < count-1; j++) {
      BigDecimal curTRXCount = tokenArray.getJSONObject(j).getBigDecimal("trxCount");
      BigDecimal nextTRXCount = tokenArray.getJSONObject(j+1).getBigDecimal("trxCount");
      log.info("curTRXCount: "+curTRXCount.toString()+"    nextTRXCount: "+nextTRXCount.toString());
      int curBiggerFlag = curTRXCount.compareTo(nextTRXCount);
      Assert.assertTrue(curBiggerFlag >= 0);

      if (tokenArray.getJSONObject(j).getString("contractAddress").equals("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"))
      {
        Assert.assertEquals(1,tokenArray.getJSONObject(j).getIntValue("recommandSortId"));
      }
      else{
        log.info("current contractAddress is:"+tokenArray.getJSONObject(j).getString("contractAddress"));
        Assert.assertEquals(0,tokenArray.getJSONObject(j).getIntValue("recommandSortId"));
      }
    }
  }


  //Use Post method Test old prepared user have 2 tokens.
  @Test(enabled = true)
  public void assetList04(){
    params.clear();
    bodyObject.clear();
    trc10tokenList.clear();
    trc20tokenList.clear();

    params.put("address",addressNewAsset41);

    trc10tokenList.add("1002000");
    bodyObject.put("trc10s",trc10tokenList);
    trc20tokenList.add("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    bodyObject.put("trc20s", trc20tokenList);
    bodyObject.put("addressType", "2");

    response = TronlinkApiList.v2AssetList(params, bodyObject);

    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    Assert.assertEquals(0,responseContent.getIntValue("code"));
    Assert.assertEquals("OK",responseContent.get("message"));

    dataContent = responseContent.getJSONObject("data");
    Assert.assertTrue(dataContent.containsKey("totalTRX"));
    JSONArray tokenJsonArray = dataContent.getJSONArray("token");
    JSONObject priceJsonArray = dataContent.getJSONObject("price");

    Assert.assertTrue(priceJsonArray.getFloat("priceUSD") > 0);
    Assert.assertTrue(priceJsonArray.getFloat("priceCny") > 0);

    for (int n = 0; n< tokenJsonArray.size(); n++){
      JSONObject tokenInfo = tokenJsonArray.getJSONObject(n);
      Assert.assertTrue(tokenInfo.containsKey("trxCount"));
      Assert.assertTrue(tokenInfo.containsKey("balance"));
      Assert.assertTrue(tokenInfo.containsKey("contractAddress"));
      Assert.assertTrue(tokenInfo.containsKey("precision"));
      Assert.assertTrue(tokenInfo.containsKey("totalSupply"));
      Assert.assertTrue(tokenInfo.containsKey("tokenDesc"));
      Assert.assertTrue(tokenInfo.containsKey("type"));

      int type = tokenInfo.getIntValue("type");
      String id = tokenInfo.getString("id");
      String contractAddress = tokenInfo.getString("contractAddress");
      switch (type){
        case 0:
          Assert.assertEquals("",id);
          Assert.assertEquals("",contractAddress);
          break;
        case 1:
          Assert.assertEquals("1002000",id);
          Assert.assertEquals("",contractAddress);
          break;
        case 2:
          Assert.assertEquals("",id);
          Assert.assertEquals("TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn".equals(contractAddress) ||
                  "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t".equals(contractAddress) || "THbVQp8kMjStKNnf2iCY6NEzThKMK5aBHg".equals(contractAddress) , true);
          break;
        default:
          break;
      }

    }
  }
}
