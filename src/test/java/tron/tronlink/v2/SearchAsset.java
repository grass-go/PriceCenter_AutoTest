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
import tron.tronlink.v2.model.CommonRsp;
import tron.tronlink.v2.model.trc1155.Data;
import tron.tronlink.v2.model.trc1155.search.SearchRsp;
import tron.tronlink.v2.model.trc1155.search.Token;

import java.util.*;

import static tron.common.Constants.GET;
import static tron.common.Constants.searchUrl;
import static tron.common.utils.ErrorMsg.*;
import static tron.common.utils.ErrorMsg.trc1155NotFound;

@Slf4j
public class SearchAsset extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject dataContent;
  private HttpResponse response;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();
  GetSign sig = new GetSign();

  @Test(enabled = true)
  public void searchAssetList01(){
    params.clear();
    params.put("address",addressNewAsset41);
    params.put("keyWord","TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    params.put("page","1");
    params.put("count","10");

    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
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
    params.put("address",addressNewAsset41);
    params.put("keyWord","Baby");
    params.put("page","1");
    params.put("count","50");
    params.put("version","v2");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
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
    params.put("address",addressNewAsset41);
    params.put("page","1");
    params.put("count","10");

    //search by id:
    params.put("keyWord","1004092");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Object count = JSONPath.eval(responseContent, "$..data.count[0]");
    Assert.assertEquals("1", count.toString());
    Object name = JSONPath.eval(responseContent, "$..data.token[*].name[0]");
    Assert.assertEquals("NAPCoin", name.toString());

    //search by name:
    params.put("keyWord","NAPCoin");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Object tokenid = JSONPath.eval(responseContent, "$..data.token[*].id");
    JSONArray tokenidArray = (JSONArray) tokenid;
    Assert.assertFalse(tokenidArray.contains("1004092"));

    //search by symbol/shortname(有其他搜索结果，所以仅判断list不包含此ID即可。)
    params.put("keyWord","NAP");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
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
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Object count = JSONPath.eval(responseContent, "$..data.count[0]");
    Assert.assertEquals("1", count.toString());
    Object name = JSONPath.eval(responseContent, "$..data.token[*].name[0]");
    Assert.assertEquals("EthLend", name.toString());

    //search by name:
    params.put("keyWord","EthLend");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    try {
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Object tokenid = JSONPath.eval(responseContent, "$..data.token[*].contractAddress");
    JSONArray tokenidArray = (JSONArray) tokenid;
    Assert.assertFalse(tokenidArray.contains("TET8rVqicX1Zu93W3LHQGg7sFX2vEGktUR"));
    }catch (Exception e){
      log.info("query EthLend exception"+e.toString());
    }

    //search by symbol/shortname(有其他搜索结果，所以仅判断list不包含此ID即可。)
    params.put("keyWord","LEND");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Object tokenid = JSONPath.eval(responseContent, "$..data.token[*].contractAddress");
    JSONArray tokenidArray = (JSONArray) tokenid;
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

  @Test(enabled = false, description = "721 search result with nrOfTokenHolders and transferCount" )
  public void searchAssetList05(){
    //getAllTokenInfo From tronscan.
    String scanBaseURL = "https://nileapi.tronscan.org/api/tokens/overview?limit=500&order=asc&filter=trc721&verifier=all&sort=createTime&start=";
    JSONArray allScan721 = new JSONArray();
    int curPage=0;
    for (int i=0; i<4; i++)
    {
      int start = curPage * 500;
      String url = scanBaseURL+start;
      response = TronlinkApiList.createGetConnect(url,null,null,null);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      JSONArray curscantokens = responseContent.getJSONArray("tokens");
      allScan721.addAll(curscantokens);
      curPage = curPage + 1;
    }
    log.info("allScan721Object: ");
    String allScan721str = "{\"tokens\":"+ allScan721.toString() +"}";
    JSONObject allScan721Object = new JSONObject();
    allScan721Object = JSON.parseObject(allScan721str);
    log.info(allScan721Object.toString());

    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","EZz0xn2HLH7S6qro9jXDjKN34zg%3D");
    params.put("address",addressNewAsset41);

    //search by id:
    params.put("keyWord","T");
    params.put("version","v2");
    params.put("type","5");
    response = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    dataContent = responseContent.getJSONObject("data");
    array = dataContent.getJSONArray("token");

    for (Object json:array) {
      JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
      String curContract = jsonObject.getString("contractAddress").toLowerCase();
      String nrOfTokenHolders = jsonObject.getString("nrOfTokenHolders");
      String transferCount = jsonObject.getString("transferCount");
      //int transferCount = jsonObject.getIntValue("transferCount");
      //Object scanCurTokenHolders =JSONPath.eval(allScan721Object,String.join("","$..tokens[0].nrOfTokenHolders[0]"));
      Object scanCurTokenHolders =JSONPath.eval(allScan721Object,String.join("","$.tokens[contractAddressLower='",curContract,"'].nrOfTokenHolders[0]"));
      Object scanCurTokenCount = JSONPath.eval(allScan721Object,String.join("","$..tokens[contractAddressLower='",curContract,"'].transferCount[0]"));
      log.info("curToken: "+curContract);
      log.info("Tronlink-Server nrOfTokenHolders:"+nrOfTokenHolders+", transferCount:"+transferCount);
      log.info("TronScan nrOfTokenHolders:"+scanCurTokenHolders+", transferCount:"+scanCurTokenCount);
      Assert.assertEquals(nrOfTokenHolders,scanCurTokenHolders.toString());
      Assert.assertEquals(transferCount,scanCurTokenCount.toString());
    }
  }


  @Test(description = "搜索1155资产")
  public void search1155(){
    init1155Params();
    HttpResponse httpRsp = TronlinkApiList.v2SearchAsset(params);
    Assert.assertEquals(httpRsp.getStatusLine().getStatusCode(), 200);
    String rspStr = TronlinkApiList.parseResponse2String(httpRsp);
    log.info(rspStr);
    SearchRsp rsp = JSONObject.parseObject(rspStr, SearchRsp.class);
    // token在结果中可以找到
    String token = SearchToken;
    AssertSearchResult(rsp, token, true);

  }

  private void AssertSearchResult(SearchRsp rsp, String token, boolean expect){
    org.testng.Assert.assertEquals(rsp.getCode(), 0, SuccessCodeErr);
    org.testng.Assert.assertEquals(rsp.getMessage(), OK,OKErr );
    boolean find = false;
    for(tron.tronlink.v2.model.trc1155.search.Token data : rsp.getData().getToken()){
      if(token.equals(data.getContractAddress())){
        find = true;
        break;
      }
    }
    org.testng.Assert.assertEquals(find, expect, trc1155NotFoundSearch);

  }


  private void init1155Params(){
    params.clear();
    // 计算sig
    params = sig.GenerateParams(B58_1155_user, searchUrl, GET);
    params.put("keyWord",keyWord1155);
    long now = System.currentTimeMillis();
    if(now %2 == 0) {
      // 随机带page 和count，可选参数
      params.put("page", "1");
      params.put("count", "10");
    }
    params.put("version","v3");
    params.put("type","6");

  }

  @Test(description = "搜索增加national字段")
  public void searchNational() {
    List<String> nationalList = new ArrayList<>();
    nationalList.add("TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn");  //USDD
    nationalList.add("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");  //USDT
    nationalList.add("TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4");  //TUSD
    nationalList.add("TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9");  //JST
    nationalList.add("TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq");  //NFT
    nationalList.add("TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4");  //BTT

    for (String token:nationalList){
      params.clear();
      params.put("address",addressNewAsset41);
      params.put("version","v2");
      params.put("keyWord",token);
      response = TronlinkApiList.v2SearchAsset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object national = JSONPath.eval(responseContent, "$..national[0]");
      Assert.assertEquals("DM", national);
      params.put("version","v3");
      response = TronlinkApiList.v2SearchAsset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      national = JSONPath.eval(responseContent, "$..national[0]");
      Assert.assertEquals("DM", national);
    }
  }

  @Test(description = "搜索增加national字段,defiType字段")
  public void searchNonNational() {
    List<String> tokenList = new ArrayList<>();
    tokenList.add("TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR");  //WTRX
    tokenList.add("TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");  //BTC
    tokenList.add("THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");  //TUSD
    tokenList.add("TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");  //USDC
    tokenList.add("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");  //WIN

    for (String token:tokenList){
      params.clear();
      params.put("address",addressNewAsset41);
      params.put("version","v2");
      params.put("keyWord",token);
      response = TronlinkApiList.v2SearchAsset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      Object national = JSONPath.eval(responseContent, "$..national[0]");
      Assert.assertEquals("", national);
      //v4.11.0
      /*Object defiType = JSONPath.eval(responseContent, "$..data.token[0].defiType");
      Assert.assertEquals("0", defiType.toString());*/
      params.put("version","v3");
      response = TronlinkApiList.v2SearchAsset(params);
      responseContent = TronlinkApiList.parseResponse2JsonObject(response);
      national = JSONPath.eval(responseContent, "$..national[0]");
      Assert.assertEquals("", national);
      //v4.11.0
      /*defiType = JSONPath.eval(responseContent, "$..data.token[0].defiType");
      Assert.assertEquals("0", defiType.toString());*/
    }
  }

  //v4.11.0
  @Test(enabled = false , description = "test jtoken has defiType=1")
  public void searchjToken() {
    params.clear();
    params.put("address",addressNewAsset41);
    params.put("version","v2");
    params.put("keyWord","justlend");
    response = TronlinkApiList.v2SearchAsset(params);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokens = dataContent.getJSONArray("token");
    for (int i = 1; i < tokens.size(); i++){
      int curdefiType = tokens.getJSONObject(i).getIntValue("defiType");
      Assert.assertEquals(1,curdefiType);
    }
  }
  //v4.11.0
  @Test(enabled = false , description = "test lp Token has defiType=2")
  public void searchLPToken() {
    params.clear();
    params.put("address",addressNewAsset41);
    params.put("version","v2");
    params.put("keyWord","sunswap");
    response = TronlinkApiList.v2SearchAsset(params);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    dataContent = responseContent.getJSONObject("data");
    JSONArray tokens = dataContent.getJSONArray("token");
    for (int i = 1; i < tokens.size(); i++){
      int curdefiType = tokens.getJSONObject(i).getIntValue("defiType");
      Assert.assertEquals(2,curdefiType);
    }
  }

}
