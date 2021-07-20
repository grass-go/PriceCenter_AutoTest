package tron.tronlink.v2;

import com.alibaba.fastjson.JSON;
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
import java.util.Map;

@Slf4j
public class SearchAsset extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();

  @Test(enabled = true)
  public void searchAssetList01(){
    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");
    params.put("address",addressNewAsset41);
    params.put("keyWord","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("page","1");
    params.put("count","10");

    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    int count =dataContent.getIntValue("count");
    Assert.assertEquals(1,count);
    array = dataContent.getJSONArray("token");
    JSONObject token = array.getJSONObject(0);
    Assert.assertEquals(2,token.getIntValue("type"));
    Assert.assertEquals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",token.getString("contractAddress"));
    Assert.assertEquals(1,token.getIntValue("isOfficial"));
    Assert.assertEquals(0,token.getIntValue("recommandSortId"));
  }

  //测试搜索结果中包含预期结果里面的token，预期结果中包括trc10，trc20，trc721的token，并且验证几个主要字段的值正确。
  @Test(enabled = false)
  public void searchAssetList02(){
    Map<String, Token> expTokens = new HashMap<>();
    {
      expTokens.put("TGiRb7cYFmU8FTrUAGw996VHtLc2sDtEZH", new Token("BabyToken", "BABY", 2, -1, 2));
      expTokens.put("TSMfJe8Lot3RKanHE2Z6mv5V5FV2cA7XQw", new Token ("BabyTFG", "BTFG", 5, 0, 1));
      expTokens.put("1000784", new Token("BabyLeprechaun","BLEP", 1, -1, 1));
    }
    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");
    params.put("address",addressNewAsset41);
    params.put("keyWord","Baby");
    params.put("page","1");
    params.put("count","50");
    params.put("version","v2");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    for (Map.Entry<String, Token> entry : expTokens.entrySet()){
      String address = entry.getKey();
      System.out.println("address:"+address);
      if ( address.length() > 10 ) {
        Object actualName = JSONPath.eval(responseContent, String.join("","$..data.token[contractAddress='", address, "'].name"));
        JSONArray actualNameArray=(JSONArray)actualName;
        Assert.assertEquals(1, actualNameArray.size());
        Assert.assertEquals(entry.getValue().name, actualNameArray.get(0));

        Object actualSName = JSONPath.eval(responseContent, String.join("","$..data.token[contractAddress='", address, "'].shortName"));
        JSONArray actualSNameArray=(JSONArray)actualSName;
        Assert.assertEquals(1, actualSNameArray.size());
        Assert.assertEquals(entry.getValue().shortName, actualSNameArray.get(0));

        Object actualType = JSONPath.eval(responseContent, String.join("","$..data.token[contractAddress='", address, "'].type"));
        JSONArray actualTypeArray=(JSONArray)actualType;
        Assert.assertEquals(1, actualTypeArray.size());
        Assert.assertEquals(entry.getValue().type, actualTypeArray.get(0));

        Object actualOfficial = JSONPath.eval(responseContent, String.join("","$..data.token[contractAddress='", address, "'].isOfficial"));
        JSONArray actualOfficialArray=(JSONArray)actualOfficial;
        Assert.assertEquals(1, actualOfficialArray.size());
        Assert.assertEquals(entry.getValue().isOfficial, actualOfficialArray.get(0));

        Object actualMF = JSONPath.eval(responseContent, String.join("","$..data.token[contractAddress='", address, "'].matchField"));
        JSONArray actualMFArray=(JSONArray)actualMF;
        Assert.assertEquals(1, actualMFArray.size());
        Assert.assertEquals(entry.getValue().matchField, actualMFArray.get(0));

        Object actualRF = JSONPath.eval(responseContent, String.join("","$..data.token[contractAddress='", address, "'].recommandSortId"));
        JSONArray actualRFArray=(JSONArray)actualRF;
        Assert.assertEquals(1, actualRFArray.size());
        Assert.assertEquals(0, actualRFArray.get(0));

      }
      else{
        Object actualName = JSONPath.eval(responseContent, String.join("","$..data.token[id='", address, "'].name"));
        log.info(actualName.toString());
        JSONArray actualNameArray=(JSONArray)actualName;
        Assert.assertEquals(1, actualNameArray.size());
        Assert.assertEquals(entry.getValue().name, actualNameArray.get(0));

        Object actualSName = JSONPath.eval(responseContent, String.join("","$..data.token[id='", address, "'].shortName"));
        log.info(actualSName.toString());
        JSONArray actualSNameArray=(JSONArray)actualSName;
        Assert.assertEquals(1, actualSNameArray.size());
        Assert.assertEquals(entry.getValue().shortName, actualSNameArray.get(0));

        Object actualType = JSONPath.eval(responseContent, String.join("","$..data.token[id='", address, "'].type"));
        log.info(actualType.toString());
        JSONArray actualTypeArray=(JSONArray)actualType;
        Assert.assertEquals(1, actualTypeArray.size());
        Assert.assertEquals(entry.getValue().type, actualTypeArray.get(0));

        Object actualOfficial = JSONPath.eval(responseContent, String.join("","$..data.token[id='", address, "'].isOfficial"));
        log.info(actualOfficial.toString());
        JSONArray actualOfficialArray=(JSONArray)actualOfficial;
        Assert.assertEquals(1, actualOfficialArray.size());
        Assert.assertEquals(entry.getValue().isOfficial, actualOfficialArray.get(0));

        Object actualMF = JSONPath.eval(responseContent, String.join("","$..data.token[id='", address, "'].matchField"));
        log.info(actualMF.toString());
        JSONArray actualMFArray=(JSONArray)actualMF;
        Assert.assertEquals(1, actualMFArray.size());
        Assert.assertEquals(entry.getValue().matchField, actualMFArray.get(0));

        Object actualRF = JSONPath.eval(responseContent, String.join("","$..data.token[id='", address, "'].recommandSortId"));
        log.info(actualRF.toString());
        JSONArray actualRFArray=(JSONArray)actualRF;
        Assert.assertEquals(1, actualRFArray.size());
        Assert.assertEquals(0, actualRFArray.get(0));
      }
    }

  }
  @Test(enabled = true, description = "TRC10: Coin isOfficial=-5(transcan level=4,诈骗币) only can search by contract address, can't name and symbol" )
  public void searchAssetList03(){
    //trc10 blacklist coin: 1004092:NAPCoin:NAP
    //blackTokens.put("1004092", new Token("NAPCoin", "NAP", 1, -5, 1));

    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");
    params.put("address",addressNewAsset41);
    params.put("page","1");
    params.put("count","10");

    //search by id:
    params.put("keyWord","1004092");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Object count = JSONPath.eval(responseContent, "$..data.count[0]");
    Assert.assertEquals("1", count.toString());
    Object name = JSONPath.eval(responseContent, "$..data.token[*].name[0]");
    Assert.assertEquals("NAPCoin", name.toString());

    //search by name:
    params.put("keyWord","NAPCoin");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Object tokenid = JSONPath.eval(responseContent, "$..data.token[*].id");
    JSONArray tokenidArray = (JSONArray) tokenid;
    Assert.assertFalse(tokenidArray.contains("1004092"));

    //search by symbol/shortname(有其他搜索结果，所以仅判断list不包含此ID即可。)
    params.put("keyWord","NAP");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    tokenid = JSONPath.eval(responseContent, "$..data.token[*].id");
    tokenidArray = (JSONArray) tokenid;
    Assert.assertFalse(tokenidArray.contains("1004092"));
  }

  @Test(enabled = true, description = "TRC20: Coin isOfficial=-5(transcan level=4,诈骗币) only can search by contract address, can't name and symbol" )
  public void searchAssetList04() {
    //trc20 blacklist coin: TET8rVqicX1Zu93W3LHQGg7sFX2vEGktUR: EthLend:LEND
    //blackTokens.put("TET8rVqicX1Zu93W3LHQGg7sFX2vEGktUR", new Token ("EthLend", "LEND", 2, -5, 1));

    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");
    params.put("address",addressNewAsset41);
    params.put("page","1");
    params.put("count","10");

    //search by id:
    params.put("keyWord","TET8rVqicX1Zu93W3LHQGg7sFX2vEGktUR");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Object count = JSONPath.eval(responseContent, "$..data.count[0]");
    Assert.assertEquals("1", count.toString());
    Object name = JSONPath.eval(responseContent, "$..data.token[*].name[0]");
    Assert.assertEquals("EthLend", name.toString());

    //search by name:
    params.put("keyWord","EthLend");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Object tokenid = JSONPath.eval(responseContent, "$..data.token[*].contractAddress");
    JSONArray tokenidArray = (JSONArray) tokenid;
    Assert.assertFalse(tokenidArray.contains("TET8rVqicX1Zu93W3LHQGg7sFX2vEGktUR"));

    //search by symbol/shortname(有其他搜索结果，所以仅判断list不包含此ID即可。)
    params.put("keyWord","LEND");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    tokenid = JSONPath.eval(responseContent, "$..data.token[*].contractAddress");
    tokenidArray = (JSONArray) tokenid;
    Assert.assertFalse(tokenidArray.contains("TET8rVqicX1Zu93W3LHQGg7sFX2vEGktUR"));
  }

  class Token {
    String name;
    String shortName;
    int type;
    int isOfficial;
    int matchField;
    public Token(String name, String shortName, int type, int isOfficial, int matchField) {
      this.name = name;
      this.shortName = shortName;
      this.type = type;
      this.isOfficial = isOfficial;
      this.matchField = matchField;
    }
  }


}
