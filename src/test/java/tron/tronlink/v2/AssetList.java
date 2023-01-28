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

    params.put("address",newUnactive_B58);
    params.put("version","v1");

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokenArray = new JSONArray();
    tokenArray = dataContent.getJSONArray("token");
    int count = tokenArray.size();
    Assert.assertEquals(2,count);
    for (int i = 0; i < count-1;  i++) {
      JSONObject tokenobject = tokenArray.getJSONObject(i);
      Assert.assertTrue(tokenobject.containsKey("balance"));
      Assert.assertTrue(tokenobject.containsKey("balanceStr"));
      Assert.assertTrue(tokenobject.containsKey("cnyCount"));
      Assert.assertTrue(tokenobject.containsKey("cnyPrice"));
      Assert.assertTrue(tokenobject.containsKey("contractAddress"));
      Assert.assertTrue(tokenobject.containsKey("homePage"));
      Assert.assertTrue(tokenobject.containsKey("id"));
      Assert.assertTrue(tokenobject.containsKey("inMainChain"));
      Assert.assertTrue(tokenobject.containsKey("inSideChain"));
      Assert.assertTrue(tokenobject.containsKey("isInAssets"));
      Assert.assertTrue(tokenobject.containsKey("isOfficial"));
      Assert.assertTrue(tokenobject.containsKey("isShield"));
      Assert.assertTrue(tokenobject.containsKey("issueAddress"));
      Assert.assertTrue(tokenobject.containsKey("issueTime"));
      Assert.assertTrue(tokenobject.containsKey("maincontractAddress"));
      Assert.assertTrue(tokenobject.containsKey("marketId"));
      Assert.assertTrue(tokenobject.containsKey("name"));
      Assert.assertTrue(tokenobject.containsKey("precision"));
      Assert.assertTrue(tokenobject.containsKey("price"));
      Assert.assertTrue(tokenobject.containsKey("recommandSortId"));
      Assert.assertTrue(tokenobject.containsKey("shortName"));
      Assert.assertTrue(tokenobject.containsKey("tokenDesc"));
      Assert.assertTrue(tokenobject.containsKey("tokenStatus"));
      Assert.assertTrue(tokenobject.containsKey("top"));
      Assert.assertTrue(tokenobject.containsKey("totalBalance"));
      Assert.assertTrue(tokenobject.containsKey("totalSupply"));
      Assert.assertTrue(tokenobject.containsKey("totalSupplyStr"));
      Assert.assertTrue(tokenobject.containsKey("transferStatus"));
      Assert.assertTrue(tokenobject.containsKey("trxCount"));
      Assert.assertTrue(tokenobject.containsKey("type"));
      Assert.assertTrue(tokenobject.containsKey("usdCount"));
      Assert.assertTrue(tokenobject.containsKey("usdPrice"));
    }

    Object national = JSONPath.eval(responseContent, "$..data.token[0].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='Decentralized USD'].national[0]");
    Assert.assertEquals("DM",national.toString());

  }

  //v4.2.1 new user(not even have transfer trx),with parameter version=v2, will return trx only.
  @SneakyThrows
  @Test(enabled = true)
  public void assetList02(){

    params.put("address",newUnactive_Hex);
    params.put("version","v2");

    response = TronlinkApiList.v2AssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokenArray = new JSONArray();
    tokenArray = dataContent.getJSONArray("token");
    int count = tokenArray.size();
    Assert.assertEquals(3,count);
    for (int i = 0; i < count-1;  i++) {
      JSONObject tokenobject = tokenArray.getJSONObject(i);
      Assert.assertTrue(tokenobject.containsKey("balance"));
      Assert.assertTrue(tokenobject.containsKey("balanceStr"));
      Assert.assertTrue(tokenobject.containsKey("cnyCount"));
      Assert.assertTrue(tokenobject.containsKey("cnyPrice"));
      Assert.assertTrue(tokenobject.containsKey("contractAddress"));
      Assert.assertTrue(tokenobject.containsKey("homePage"));
      Assert.assertTrue(tokenobject.containsKey("id"));
      Assert.assertTrue(tokenobject.containsKey("inMainChain"));
      Assert.assertTrue(tokenobject.containsKey("inSideChain"));
      Assert.assertTrue(tokenobject.containsKey("isInAssets"));
      Assert.assertTrue(tokenobject.containsKey("isOfficial"));
      Assert.assertTrue(tokenobject.containsKey("isShield"));
      Assert.assertTrue(tokenobject.containsKey("issueAddress"));
      Assert.assertTrue(tokenobject.containsKey("issueTime"));
      Assert.assertTrue(tokenobject.containsKey("maincontractAddress"));
      Assert.assertTrue(tokenobject.containsKey("marketId"));
      Assert.assertTrue(tokenobject.containsKey("name"));
      Assert.assertTrue(tokenobject.containsKey("precision"));
      Assert.assertTrue(tokenobject.containsKey("price"));
      Assert.assertTrue(tokenobject.containsKey("recommandSortId"));
      Assert.assertTrue(tokenobject.containsKey("shortName"));
      Assert.assertTrue(tokenobject.containsKey("tokenDesc"));
      Assert.assertTrue(tokenobject.containsKey("tokenStatus"));
      Assert.assertTrue(tokenobject.containsKey("top"));
      Assert.assertTrue(tokenobject.containsKey("totalBalance"));
      Assert.assertTrue(tokenobject.containsKey("totalSupply"));
      Assert.assertTrue(tokenobject.containsKey("totalSupplyStr"));
      Assert.assertTrue(tokenobject.containsKey("transferStatus"));
      Assert.assertTrue(tokenobject.containsKey("trxCount"));
      Assert.assertTrue(tokenobject.containsKey("type"));
      Assert.assertTrue(tokenobject.containsKey("usdCount"));
      Assert.assertTrue(tokenobject.containsKey("usdPrice"));
    }

    Object USDTprice = JSONPath.eval(responseContent, "$..data.token[name='Tether USD'].price");
    JSONArray USDTPriceArray=(JSONArray)USDTprice;
    BigDecimal actualTUSDPrice = (BigDecimal) USDTPriceArray.get(0);
    int usdtflag = actualTUSDPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(usdtflag > 0);

    Object national = JSONPath.eval(responseContent, "$..data.token[0].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='Decentralized USD'].national[0]");
    Assert.assertEquals("DM",national.toString());

  }

  //v4.2.1 old user the first token is TRX. second token is USDD, Others order by trxCount.
  @Test(enabled = true)
  public void assetList03() {

    params.clear();
    params.put("address", unfollowAPIUser);
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

    Assert.assertEquals(2,tokenArray.getJSONObject(1).getIntValue("type"));
    Assert.assertEquals(2,tokenArray.getJSONObject(1).getIntValue("top"));
    Assert.assertEquals("Decentralized USD",tokenArray.getJSONObject(1).getString ("name"));


    for (int j = 2; j < count-1; j++) {
      BigDecimal curTRXCount = tokenArray.getJSONObject(j).getBigDecimal("trxCount");
      BigDecimal nextTRXCount = tokenArray.getJSONObject(j+1).getBigDecimal("trxCount");
      log.info("curTRXCount: "+curTRXCount.toString()+"    nextTRXCount: "+nextTRXCount.toString());
      int curBiggerFlag = curTRXCount.compareTo(nextTRXCount);
      Assert.assertTrue(curBiggerFlag >= 0);

      if (tokenArray.getJSONObject(j).getString("contractAddress").equals("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"))
      {
        Assert.assertEquals(1,tokenArray.getJSONObject(j).getIntValue("recommandSortId"));
      }else if (tokenArray.getJSONObject(j).getString("id").equals("1002000"))
      {
        Assert.assertEquals(0,tokenArray.getJSONObject(j).getIntValue("recommandSortId"));
      } else{
        log.info("current contractAddress is:"+tokenArray.getJSONObject(j).getString("contractAddress"));
        Assert.assertEquals(0,tokenArray.getJSONObject(j).getIntValue("recommandSortId"));
      }

      //v4.11.0-done
      if (tokenArray.getJSONObject(j).getString("defiType").equals("1"))
      {
        Assert.assertEquals(0,tokenArray.getJSONObject(j).getString("shortName").indexOf("j"));
      }
      if (tokenArray.getJSONObject(j).getString("defiType").equals("2"))
      {
        Assert.assertEquals(0,tokenArray.getJSONObject(j).getString("shortName").indexOf("S-"));
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

    params.put("address",unfollowAPIUser);

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
          Assert.assertEquals("",contractAddress);
          break;
        case 2:
          Assert.assertEquals("",id);
          break;
        default:
          break;
      }

    }
  }
}
