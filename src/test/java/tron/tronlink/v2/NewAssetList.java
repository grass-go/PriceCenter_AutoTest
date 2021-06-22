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
  @Test(enabled = true)
  public void newAssetList01(){
    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","0YrPyUSLIbE%2FxRE76n63OZXp2D4%3D");
    params.put("address",addressNewAsset41);

    response = TronlinkApiList.v2NewAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
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

  }

  //用address721账户测试新资产，使用全文对比，资产排序包含其中。
  @SneakyThrows
  @Test(enabled = true)
  public void newAssetList02(){
    //read expected json
    char cbuf[] = new char[5000];
    InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/newAssertList_exp.json")),"UTF-8");
    int len =input.read(cbuf);
    String expResponse =new String(cbuf,0,len);

    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","vamHDpsmhAJiiuRqNdLNqupKWUI%3D");
    params.put("address",address721_B58);

    response = TronlinkApiList.v2NewAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    /*
    //check Count failed by 空投，
    Object actualCount = JSONPath.eval(responseContent, "$..data.count");
    JSONArray actualCountArray=(JSONArray)actualCount;
    Assert.assertEquals(2, actualCountArray.get(0));
    */

    //check BitTorrent balance
    Object actualBT = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].balanceStr");
    JSONArray actualBTArray=(JSONArray)actualBT;
    Assert.assertEquals("9", actualBTArray.get(0));
    Object actualBTPrice = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent'].price");
    JSONArray actualBTPriceArray=(JSONArray)actualBTPrice;
    BigDecimal btPrice = (BigDecimal) actualBTPriceArray.get(0);
    int btflag = btPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(btflag>0);


    //check WINkLink balance
    Object actualWL = JSONPath.eval(responseContent, String.join("","$..data.token[name='WINkLink'].balanceStr"));
    JSONArray actualWLArray=(JSONArray)actualWL;
    Assert.assertEquals("0.01", actualWLArray.get(0));
    Object actualWLPrice = JSONPath.eval(responseContent, "$..data.token[name='WINkLink'].price");
    JSONArray actualWLPriceArray=(JSONArray)actualWLPrice;
    BigDecimal wlPrice = (BigDecimal) actualWLPriceArray.get(0);
    int wlflag = wlPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(wlflag > 0);


    /*
    responseString = TronlinkApiList.parseResponse2String(response);
    String cmp_result = new CompareJson("contractAddress", "price,trxCount,nrOfTokenHolders,transferCount").compareJson(responseString, expResponse);
    System.out.println("=========actual response========== "+responseString+" ");
    System.out.println("=========expect response========== "+expResponse+" ");
    System.out.println("=========cmp_result=============== "+cmp_result);
    Assert.assertEquals("null",cmp_result);
    */

  }


}
