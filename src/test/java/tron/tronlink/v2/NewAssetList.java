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

  //因为新资产不稳定的bug ONLIVE-318，所以enabled=false
  @Test(enabled = false)
  public void newAssetList01(){
    params.clear();
    params.put("address",addressNewAsset41);

    response = TronlinkApiList.v2NewAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    int count =dataContent.getIntValue("count");
    Assert.assertEquals(1,count);
    array = dataContent.getJSONArray("token");
    Assert.assertEquals(1,array.size());
    object = array.getJSONObject(0);
    int type=object.getIntValue("type");
    Assert.assertEquals(1,type);
    Assert.assertEquals("1002962",object.getString("id"));
    Assert.assertTrue(object.getLongValue("balance")>0);
    Assert.assertFalse(object.getBooleanValue("isInAssets"));
    Assert.assertEquals(0,object.getIntValue("recommandSortId"));

  }

  //用address721账户测试新资产，使用全文对比，资产排序包含其中。
  //add v4.2.1 recommandSortId=0
  @SneakyThrows
  @Test(enabled = true)
  public void newAssetList02(){
    //read expected json
    char cbuf[] = new char[5000];
    InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/newAssertList_exp.json")),"UTF-8");
    int len =input.read(cbuf);
    String expResponse =new String(cbuf,0,len);

    params.put("address",addressNewAsset2);

    response = TronlinkApiList.v2NewAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);


    //check BitTorrent balance
    Object actualBT = JSONPath.eval(responseContent, "$..data.token[name='JUST'].balanceStr");
    JSONArray actualBTArray=(JSONArray)actualBT;
    Assert.assertEquals("1.251755232041081343", actualBTArray.get(0));
    Object actualBTPrice = JSONPath.eval(responseContent, "$..data.token[name='JUST'].price");
    JSONArray actualBTPriceArray=(JSONArray)actualBTPrice;
    BigDecimal btPrice = (BigDecimal) actualBTPriceArray.get(0);
    int btflag = btPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(btflag>0);
    Object actualRSIPrice = JSONPath.eval(responseContent, "$..data.token[name='JUST'].recommandSortId");
    JSONArray actualRSIArray=(JSONArray)actualRSIPrice;
    Assert.assertEquals(0,actualRSIArray.get(0));
    Object national = JSONPath.eval(responseContent, "$..data.token[name='JUST'].national[0]");
    Assert.assertEquals("DM",national.toString());


    //check BitTorrent balance
    Object actualWL = JSONPath.eval(responseContent, String.join("","$..data.token[name='BitTorrent'].balanceStr"));
    JSONArray actualWLArray=(JSONArray)actualWL;
    Assert.assertEquals("2", actualWLArray.get(0));
    Object actualWLPrice = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].price");
    JSONArray actualWLPriceArray=(JSONArray)actualWLPrice;
    BigDecimal wlPrice = (BigDecimal) actualWLPriceArray.get(0);
    int wlflag = wlPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(wlflag > 0);
    actualRSIPrice = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].recommandSortId");
    actualRSIArray=(JSONArray)actualRSIPrice;
    Assert.assertEquals(0,actualRSIArray.get(0));
    national = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].national[0]");
    Assert.assertEquals("DM",national.toString());


  }
  @Test(enabled = false, description = "check 1155 can be in new Asset API")
  public void newAssetList03_1155(){
    params.put("address",address721_B58);

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
    Assert.assertEquals("TBgckQzPTLcyvVG7fuGwSowzfhmykA7V4L",contractAddress);
    int count = trc1155token.getIntValue("count");
    Assert.assertEquals(1,count);
    String logoUrl = trc1155token.getString("logoUrl");
    Assert.assertEquals("https://image.tronlink.org/pictures/nft_contract_default_logo.png",logoUrl);
    boolean inMainChain = trc1155token.getBooleanValue("inMainChain");
    Assert.assertTrue(inMainChain);
    boolean inSideChain = trc1155token.getBooleanValue("inSideChain");
    Assert.assertFalse(inSideChain);
    String issueTime = trc1155token.getString("issueTime");
    Assert.assertEquals("2022-08-05 10:29:06",issueTime);
    String issueAddress = trc1155token.getString("issueAddress");
    Assert.assertEquals("TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t",issueAddress);
  }

    //read expected json

}
