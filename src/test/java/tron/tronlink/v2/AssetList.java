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
    response = TronlinkApiList.v2AssetList(params);
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
    response = TronlinkApiList.v2AssetList(params);
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
  @Test(enabled = true)
  public void assetListGetManual() {
    params.clear();
    params.put("address", quince_B58);
    params.put("version", "v2");
    response = TronlinkApiList.v2AssetList(params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

  }


  //Use Post method Test old prepared user have 2 tokens.
  @Test(enabled = true)
  public void assetListManual(){
    params.clear();
    bodyObject.clear();
    trc10tokenList.clear();
    trc20tokenList.clear();

    params.put("address",quince_B58);

    trc10tokenList.add("1000323");
    bodyObject.put("trc10s",trc10tokenList);
    trc20tokenList.add("TX78WgVj6mViwvNuDiHjqCXd24SxYGBgpZ");
    bodyObject.put("trc20s", trc20tokenList);
    bodyObject.put("addressType", "2");

    response = TronlinkApiList.v2AssetList(params, bodyObject);

    Assert.assertEquals(200, response.getStatusLine().getStatusCode());
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

  }
}
