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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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


  // 暂时关闭，等有结论了打开
  @Test(enabled = true)
  public void allAssetList01(){
    params.put("address","TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t");
    // v2版本 推荐币的 recommandSortId = 1
    params.put("version", "v2");
    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);
  }




  @SneakyThrows
  @Test(enabled = true, description = "check balance add national field")
  public void allAssetList02(){
    params.clear();
    params.put("address",address721_B58);

    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    //check BitTorrent balance
    Object actualBT = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent Old'].balanceStr");
    JSONArray actualBTArray=(JSONArray)actualBT;
    Assert.assertEquals("9", actualBTArray.get(0));
    Object actualBTPrice = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent Old'].price");
    JSONArray actualBTPriceArray=(JSONArray)actualBTPrice;
    BigDecimal btPrice = (BigDecimal) actualBTPriceArray.get(0);
    int btflag = btPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(btflag>0);
    Object national = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent Old'].national[0]");
    Assert.assertEquals("",national.toString());


    //check WINkLink balance
    Object actualWL = JSONPath.eval(responseContent, String.join("","$..data.token[name='WINkLink'].balanceStr"));
    JSONArray actualWLArray=(JSONArray)actualWL;
    Assert.assertEquals("206.349755", actualWLArray.get(0));
    Object actualWLPrice = JSONPath.eval(responseContent, "$..data.token[name='WINkLink'].price");
    JSONArray actualWLPriceArray=(JSONArray)actualWLPrice;
    BigDecimal wlPrice = (BigDecimal) actualWLPriceArray.get(0);
    int wlflag = wlPrice.compareTo(BigDecimal.ZERO);
    Assert.assertTrue(wlflag > 0);
    national = JSONPath.eval(responseContent, "$..data.token[name='WINkLink'].national[0]");
    Assert.assertEquals("",national.toString());
    //

    national = JSONPath.eval(responseContent, "$..data.token[0].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='Decentralized USD'].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='Tether USD'].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='APENFT'].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='TrueUSD'].national[0]");
    Assert.assertEquals("DM",national.toString());

    national = JSONPath.eval(responseContent, "$..data.token[name='JUST'].national[0]");
    Assert.assertEquals("DM",national.toString());

  }

  @Test(enabled = true, description = "Test jToken price")
  public void allAssetList03(){
    params.clear();
    params.put("address",quince_B58);

    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);




    Map<String, String> jTokens = new HashMap<>();
    jTokens.put("jTRX","TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP");
    jTokens.put("jUSDJ","TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA");
    jTokens.put("jWBTT","TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt");
    jTokens.put("jWIN","TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv");


    for(Map.Entry<String, String> entry : jTokens.entrySet()) {
      String shortName = entry.getKey();
      String contractAddr = entry.getValue();
      Object Price = JSONPath.eval(responseContent, String.join("", "$..data.token[shortName='", shortName, "'].price"));
      JSONArray priceArray = (JSONArray) Price;
      BigDecimal actualPrice = new BigDecimal(priceArray.get(0).toString());
      //get transcan price
      int index;
      for (index = 0; index < 5; index++) {
        log.info("cur index is" + index);
        String reqestUrl = TronlinkBase.tronscanApiUrl + "/api/token_trc20?contract=" + contractAddr + "&showAll=1";
        HttpResponse transcanRsp = TronlinkApiList.createGetConnect(reqestUrl,null,null,null);
        JSONObject transcanRspContent = TronlinkApiList.parseResponse2JsonObject(transcanRsp);
        int total = transcanRspContent.getIntValue("total");
        if (total == 0) {
          continue;
        } else {
          log.info(String.join("", "$..trc20_tokens[contract_address='", contractAddr, "'].contract_name"));
          Object expPrice = JSONPath.eval(transcanRspContent, String.join("", "$..trc20_tokens[contract_address='", contractAddr, "'].market_info.priceInTrx"));
          JSONArray expectPriceArray = (JSONArray) expPrice;
          BigDecimal expectPrice = new BigDecimal(expectPriceArray.get(0).toString());
          // assert the gap less than 20% of actual pirce
          BigDecimal absgap = expectPrice.subtract(actualPrice).abs();
          BigDecimal THREE = new BigDecimal("3");
          BigDecimal tolerance = actualPrice.divide(THREE, 6, 1);
          log.info("TranscanPrice:" + expectPrice.toString() + ", TronlinkServer Price:" + actualPrice.toString() + ", Tolerance: " + tolerance.toString() + ", absgap:" + absgap.toString());
          //decide if absgap less than tolerance. actual's 25%.
          //Assert.assertTrue(absgap.compareTo(tolerance) == -1);
          index = 6;
        }
      }
      Assert.assertEquals(7, index);
    }
  }
  @Test(enabled = true, description = "Test each coin level equals to tronscan api")
  public void allAssetList04() throws InterruptedException {
    params.clear();
    params.put("address",quince_B58);

    response = TronlinkApiList.V2AllAssetList(params);
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronlinkApiList.parseResponse2JsonObject(response);

    Object token10s = JSONPath.eval(responseContent, "$..id");
    Object token20s = JSONPath.eval(responseContent, "$..contractAddress");
    List token10sArray = (List) token10s;
    List token20sArray = (List) token20s;
    log.info(token10s.toString());
    log.info(token20s.toString());
    //To do : exclude isOfficial=1
    //isOfficial=1
    ArrayList<String> officialCoin10 = new ArrayList<String>();
    ArrayList<String> officialCoin20 = new ArrayList<String>();

    officialCoin10.add("1002000"); //BitTorrent, BTT
    officialCoin20.add("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t"); //USDT
    officialCoin20.add("TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9"); //SUNOLD
    officialCoin20.add("TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq"); //APENFT
    officialCoin20.add("TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S"); //SUN
    officialCoin20.add("TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt"); //WBTT
    officialCoin20.add("TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4"); //TUSD
    officialCoin20.add("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7"); //WINkLink, WIN
    officialCoin20.add("TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9"); //JST Token
    officialCoin20.add("TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR"); //WTRX Token
    officialCoin20.add("TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT"); //USDJ
    officialCoin20.add("TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7");
    officialCoin20.add("TDyvndWuvX5xTBwHPYJi7J3Yq8pq8yh62h");
    officialCoin20.add("TB95FFYRJMLY6mWZqv4JUMqAqsHF4JCXga");
    officialCoin20.add("TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9");
    officialCoin20.add("THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF");
    officialCoin20.add("TXWkP3jLBqRGojUih1ShzNyDaN5Csnebok");
    officialCoin20.add("TXpw8XeWYeTUd4quDskoUqeQPowRh4jY65");
    officialCoin20.add("TR3DLthpnDdCGabhVDbD3VMsiJoCXY3bZd");
    officialCoin20.add("THbVQp8kMjStKNnf2iCY6NEzThKMK5aBHg");
    officialCoin20.add("TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8");

    for (int i = 0; i<token10sArray.size(); i++) {
      if (!token10sArray.get(i).equals("")) {

        String curId = token10sArray.get(i).toString();
        if (officialCoin10.contains(curId)) {
          continue;
        }
        Object cur_isOfficial = JSONPath.eval(responseContent, String.join("","$..data.token[id='",curId,"'].isOfficial[0]"));
        String requestUrl= TronlinkBase.tronscanApiUrl+"/api/token?id="+curId+"&showAll=1";
        HttpResponse transcanRsp = TronlinkApiList.createGetConnect(requestUrl,null,null,null);
        Thread.sleep(1000);
        JSONObject transcanRspContent = TronlinkApiList.parseResponse2JsonObject(transcanRsp);
        Object scan_levelObject = JSONPath.eval(transcanRspContent, String.join("","$..data[0].level"));
        if(scan_levelObject.equals("")){
          scan_levelObject = "0";
        }
        Integer scan_level = Integer.valueOf(scan_levelObject.toString());

        log.info("curId:"+curId+", cur_isOfficial:"+cur_isOfficial.toString()+", transcan level:"+scan_levelObject.toString());
        log.info("======");
        Assert.assertEquals(Math.abs(Integer.parseInt(cur_isOfficial.toString())), scan_level+1);


      }
    }

    for (int i=0; i<token20sArray.size();i++){
      if (!token20sArray.get(i).equals("")) {
        String curAddress = token20sArray.get(i).toString();
        if (officialCoin20.contains(curAddress)) {
          continue;
        }
        Object cur_isOfficial = JSONPath.eval(responseContent, String.join("","$..data.token[contractAddress='",curAddress,"'].isOfficial[0]"));
        String requestUrl= TronlinkBase.tronscanApiUrl+"/api/token_trc20?contract="+curAddress+"&showAll=1&source=true";
        HttpResponse transcanRsp = TronlinkApiList.createGetConnect(requestUrl,null,null,null);
        Thread.sleep(1000);
        JSONObject transcanRspContent = TronlinkApiList.parseResponse2JsonObject(transcanRsp);
        Object scan_levelObject = JSONPath.eval(transcanRspContent, String.join("","$..trc20_tokens[0].level"));
        org.testng.Assert.assertNotEquals(scan_levelObject, null, "tronscan 接口的数据为空导致");
        Integer scan_level = Integer.valueOf(scan_levelObject.toString());
        log.info("curAddress:"+curAddress+", cur_isOfficial:"+cur_isOfficial.toString()+", transcan level:"+scan_levelObject.toString());
        log.info("======");
        if (curAddress.equals("TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4") || curAddress.equals("TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn")){
          Assert.assertEquals(Math.abs(Integer.parseInt(cur_isOfficial.toString())), 1);
        } else{
          Assert.assertEquals(Math.abs(Integer.parseInt(cur_isOfficial.toString())), scan_level+1);
        }

      }
    }
  }




}
