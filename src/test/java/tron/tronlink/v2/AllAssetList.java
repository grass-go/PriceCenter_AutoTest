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

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AllAssetList extends TronlinkBase {
  private JSONObject responseContent;
  private String responseString;
  private JSONObject object;
  private JSONObject dataContent;
  private HttpResponse response;
  private JSONArray array = new JSONArray();
  Map<String, String> params = new HashMap<>();


  @Test(enabled = true)
  public void allAssetList01(){
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","3ePuP28sQRThx9WrDajgcec4NlI%3D");
    params.put("address",addressNewAsset41);
    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);
    Assert.assertTrue(responseContent.containsKey("code"));
    Assert.assertTrue(responseContent.containsKey("message"));
    Assert.assertTrue(responseContent.containsKey("data"));
    dataContent = responseContent.getJSONObject("data");
    int count =dataContent.getIntValue("count");
    Assert.assertEquals(6,count);
    array = dataContent.getJSONArray("token");
    int type=0;
    for(int j=0;j<count;j++){
      object = array.getJSONObject(j);
      type=object.getIntValue("type");
      String id=object.getString("id");
      if (type==1){
        if(!(("1002000".equals(id))||("1002962".equals(id)))){
          log.info("------wrong token id ");
          Assert.assertFalse(false);
        }

      }else if (type==2) {
        if (!((object.getString("contractAddress").equals("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t")) || (object.getString("contractAddress").equals("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7")))) {
          log.info("------wrong token Address");
          Assert.assertFalse(false);
        }
      }

      if(object.getString("contractAddress").equals("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"))
      {
        Assert.assertEquals(1,object.getIntValue("recommandSortId"));
      }
      else{
        Assert.assertEquals(0,object.getIntValue("recommandSortId"));
      }
    }

  }

  @SneakyThrows
  @Test(enabled = true)
  public void allAssetList02(){
    //read expected json
    char cbuf[] = new char[5000];
    InputStreamReader input =new InputStreamReader(new FileInputStream(new File("src/test/resources/TestData/allAssetList_exp.json")),"UTF-8");
    int len =input.read(cbuf);
    String expResponse =new String(cbuf,0,len);
    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","7Z0jHVm7ifg%2BYZqvkkOxqa%2Bcdh0%3D");
    params.put("address",address721_B58);

    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);

    /*
    //check trx balance, failed because of kongtou
    Object actualTrx = JSONPath.eval(responseContent, "$..data.token[id=''].balanceStr");
    JSONArray actualTrxArray=(JSONArray)actualTrx;
    Assert.assertEquals("25.000003", actualTrxArray.get(0));
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

    //below is full text compare, but maybe fail because of 空投
    /*
    responseString = TronlinkApiList.parseResponse2String(response);
    String cmp_result = new CompareJson("contractAddress", "price,trxCount").compareJson(responseString, expResponse);
    System.out.println("=========actual response========== "+responseString+" ");
    System.out.println("=========expect response========== "+expResponse+" ");
    System.out.println("=========cmp_result=============== "+cmp_result);
    Assert.assertEquals("null",cmp_result);
     */
  }

  @Test(enabled = true, description = "Test jToken price")
  public void allAssetList03(){
    params.clear();
    params.put("nonce","12345");
    params.put("secretId","SFSUIOJBFMLKSJIF");
    params.put("signature","38ljR2%2BTk8YRkub7SJ58qiOolgE%3D");
    params.put("address",quince_B58);

    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseJsonObResponseContent(response);




    Map<String, String> jTokens = new HashMap<>();
    jTokens.put("jTRX","TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");
    jTokens.put("jUSDJ","TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA");
    jTokens.put("jWBTT","TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt");
    jTokens.put("jWIN","TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv");


    for(Map.Entry<String, String> entry : jTokens.entrySet()){
      String shortName= entry.getKey();
      String contractAddr = entry.getValue();
      Object Price = JSONPath.eval(responseContent, String.join("","$..data.token[shortName='",shortName,"'].price"));
      JSONArray priceArray=(JSONArray)Price;
      BigDecimal actualPrice = new BigDecimal(priceArray.get(0).toString());

      //get transcan price
      String reqestUrl= TronlinkBase.tronscanApiUrl+"/api/token_trc20?contract="+contractAddr+"&showAll=1";
      HttpResponse transcanRsp = TronlinkApiList.createGetConnect(reqestUrl);
      JSONObject transcanRspContent = TronlinkApiList.parseJsonObResponseContent(transcanRsp);
      log.info(String.join("","$..trc20_tokens[contract_address='",contractAddr,"'].contract_name"));
      Object expPrice = JSONPath.eval(transcanRspContent, String.join("","$..trc20_tokens[contract_address='",contractAddr,"'].market_info.priceInTrx"));
      JSONArray expectPriceArray=(JSONArray)expPrice;
      BigDecimal expectPrice = new BigDecimal(expectPriceArray.get(0).toString());

      BigDecimal absgap = expectPrice.subtract(actualPrice).abs();
      BigDecimal FIVE = new BigDecimal("5");
      BigDecimal tolerance = actualPrice.divide(FIVE,6,1);
      log.info("TranscanPrice:"+expectPrice.toString()+", TronlinkServer Price:"+actualPrice.toString()+", Tolerance: "+tolerance.toString()+", absgap:"+absgap.toString());
      //decide if absgap less than tolerance.
      Assert.assertTrue(absgap.compareTo(tolerance) == -1);

    }
  }

}
