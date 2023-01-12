package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.CompareJson;
import tron.tronlink.base.TronlinkBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class NewAssetList extends TronlinkBase {
  private JSONObject responseContent;
  private String responseString;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONObject object;

  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  //add v4.2.1 recommandSortId=0
  @SneakyThrows
  @Test(enabled = true)
  public void newAssetList01(){
    params.put("address",addressNewAsset2);

    response = TronlinkApiList.v2NewAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject dataContent = responseContent.getJSONObject("data");
    int count =dataContent.getIntValue("count");
    Assert.assertEquals(3,count);
    array = dataContent.getJSONArray("token");
    Assert.assertEquals(3,array.size());

    Object actualBttOld = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent Old'].balanceStr[0]");
    Assert.assertEquals("1", actualBttOld.toString());
    Object actualBttoldPrice = JSONPath.eval(responseContent, "$..data.token[name='JUST'].price[0]");
    BigDecimal bttoldPrice = (BigDecimal) actualBttoldPrice;
    int bttoldflag = bttoldPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(bttoldflag>0);
    Object actualRSIPrice = JSONPath.eval(responseContent, "$..data.token[name='JUST'].recommandSortId[0]");
    Assert.assertEquals("0",actualRSIPrice.toString());
    Object national = JSONPath.eval(responseContent, "$..data.token[name='JUST'].national[0]");
    Assert.assertEquals("DM",national.toString());
    //v4.11.0-done
    Object defiType = JSONPath.eval(responseContent, "$..data.token[name='JUST'].defiType[0]");
    Assert.assertEquals("0",defiType.toString());


    Object actualBT = JSONPath.eval(responseContent, "$..data.token[name='JUST'].balanceStr");
    JSONArray actualBTArray=(JSONArray)actualBT;
    Assert.assertEquals("1.251755232041081343", actualBTArray.get(0));
    Object actualBTPrice = JSONPath.eval(responseContent, "$..data.token[name='JUST'].price");
    JSONArray actualBTPriceArray=(JSONArray)actualBTPrice;
    BigDecimal btPrice = (BigDecimal) actualBTPriceArray.get(0);
    int btflag = btPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(btflag>0);
    Object justRSI = JSONPath.eval(responseContent, "$..data.token[name='JUST'].recommandSortId[0]");
    Assert.assertEquals("0",justRSI.toString());
    Object justNational = JSONPath.eval(responseContent, "$..data.token[name='JUST'].national[0]");
    Assert.assertEquals("DM",justNational.toString());
    //v4.11.0-done
    Object justDefiType = JSONPath.eval(responseContent, "$..data.token[name='JUST'].defiType[0]");
    Assert.assertEquals("0",justDefiType.toString());


    //check BitTorrent balance
    Object actualWL = JSONPath.eval(responseContent, String.join("","$..data.token[name='BitTorrent'].balanceStr"));
    JSONArray actualWLArray=(JSONArray)actualWL;
    Assert.assertEquals("2", actualWLArray.get(0));
    Object actualWLPrice = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].price");
    JSONArray actualWLPriceArray=(JSONArray)actualWLPrice;
    BigDecimal wlPrice = (BigDecimal) actualWLPriceArray.get(0);
    int wlflag = wlPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(wlflag > 0);
    Object bttRSI = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].recommandSortId[0]");
    Assert.assertEquals("0",bttRSI.toString());
    national = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].national[0]");
    Assert.assertEquals("DM",national.toString());
    //v4.11.0-done
    defiType = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].defiType[0]");
    Assert.assertEquals("0",defiType.toString());


  }
  @Test(enabled = true, description = "check 1155 can be in new Asset API")
  public void newAssetList02_1155(){
    params.put("address",addressNewAsset2);

    response = TronlinkApiList.v2NewAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    JSONObject trc1155token = (JSONObject) JSONPath.eval(responseContent,"$.data.trc1155Token[0]");
    int type = trc1155token.getIntValue("type");
    Assert.assertEquals(6,type);
    int top = trc1155token.getIntValue("top");
    Assert.assertEquals(0,top);
    int isOfficial = trc1155token.getIntValue("isOfficial");
    Assert.assertEquals(0,isOfficial);
    String name = trc1155token.getString("name");
    Assert.assertEquals("bill1155_001",name);
    String shortName = trc1155token.getString("shortName");
    Assert.assertEquals("bill1155_001",shortName);
    String contractAddress = trc1155token.getString("contractAddress");
    Assert.assertEquals("TE2VpPmPQp9UZpDPcBS4G8Pw3R1BZ4Ea6j",contractAddress);
    int count = trc1155token.getIntValue("count");
    Assert.assertEquals(1,count);
    String logoUrl = trc1155token.getString("logoUrl");
    Assert.assertEquals("https://static.tronscan.org/production/upload/logo/new/TE2VpPmPQp9UZpDPcBS4G8Pw3R1BZ4Ea6j.png?t=1661309193530",logoUrl);
    boolean inMainChain = trc1155token.getBooleanValue("inMainChain");
    Assert.assertTrue(inMainChain);
    boolean inSideChain = trc1155token.getBooleanValue("inSideChain");
    Assert.assertFalse(inSideChain);
    String issueTime = trc1155token.getString("issueTime");
    Assert.assertEquals("2022-08-05 07:57:12",issueTime);
    String issueAddress = trc1155token.getString("issueAddress");
    Assert.assertEquals("TXFmkVZkpkv8ghNCKwpeVdVvRVqTedSCAK",issueAddress);
  }

    //read expected json

}
