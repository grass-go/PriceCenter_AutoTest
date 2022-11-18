package tron.priceCenter.DataProviderTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tron.common.PriceCenterApiList;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.priceCenter.base.priceBase;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class CheckPriceWithTronscan extends priceBase {

    private JSONObject allpriceResponseContent;

    @BeforeClass(enabled = true,description = "get all price ")
    public void getAllPrice() throws URISyntaxException {
        String allpriceResponse_str = PriceCenterApiList.getallprice();
        allpriceResponseContent = JSON.parseObject(allpriceResponse_str);
    }

    @DataProvider(name = "allToken")
    public Object[][] data()  {
        List<String> tokenlist = new java.util.ArrayList<String>();
        JSONObject dataContent = allpriceResponseContent.getJSONObject("data");
        JSONArray tokensArray = dataContent.getJSONArray("rows");
        //fShortName, fTokenAddr, sShortName, price, So colomnNum=4.
        for (Object curtoken : tokensArray) {
            JSONObject curToken = (JSONObject) curtoken;
            System.out.println("cur token info: " + curtoken.toString());
            JSONObject token = (JSONObject) JSON.toJSON(curtoken);
            String fTokenAddr = token.getString("fTokenAddr");
            String sShortName = token.getString("sShortName");
            if(fTokenAddr.startsWith("0x") || sShortName.equals("USD") || fTokenAddr.length()==7){
                continue;
            }
            String fShortName = token.getString("fShortName");
            String centerPrice = token.getString("price");
            if(centerPrice.equals("0")){
                continue;
            }
            //exclude some token has the same symbol with others in CMC.
            //TTUwzoZAK6rpDjpSh8B2XFTnxGfbMLHJaq， tronscan从CMC获取的价格有延迟。CMC价格有突落，tronscan在半个小时内没有回落。
            //TSdqRcnAaMUQWRy4zRR8Pd3QuJuBKX9W55 tronscan与CMC本身有差距，price-center与cmc一致。
            //TGB1ZeuHVxyd72hzbmb8m9c9RpHedNA43J 数值太小，导致tronscan给出约值。0.000000014980115310TRX
            //TUKxxRkFi21d6KnqUi7aNsbA2So91xMDFG tronscan与CMC本身有差距，price-center与cmc一致。
            //TLvDJcvKJDi3QuHgFbJC6SeTj3UacmtQU3, tronscan从CMC获取，price-centre从swap获取。
            //TCt4pND9amuUJ2s4WQHbM5cfsRwJA1GC4i tronscan与CMC本身有差距，price-center与cmc一致。

            if(fTokenAddr.equals("TTUwzoZAK6rpDjpSh8B2XFTnxGfbMLHJaq") || fTokenAddr.equals("TSdqRcnAaMUQWRy4zRR8Pd3QuJuBKX9W55")
                    || fTokenAddr.equals("TGB1ZeuHVxyd72hzbmb8m9c9RpHedNA43J") || fTokenAddr.equals("TUKxxRkFi21d6KnqUi7aNsbA2So91xMDFG")
                    || fTokenAddr.equals("TLvDJcvKJDi3QuHgFbJC6SeTj3UacmtQU3") || fTokenAddr.equals("TCt4pND9amuUJ2s4WQHbM5cfsRwJA1GC4i")
             ) {
                continue;
            }

            tokenlist.add(fShortName+","+fTokenAddr+","+sShortName+","+centerPrice);
        }

        int totalNum = tokenlist.size();
        Object[][] data = new Object[totalNum][4];
        int dataIdx = 0;
        for (int i=0; i<tokenlist.size(); i++) {
            String tokeninfo = tokenlist.get(i);
            System.out.println("cur token info: " + tokeninfo);

            String[] lineList = tokeninfo.split(",");
            data[dataIdx][0] = lineList[0];
            data[dataIdx][1] = lineList[1];
            data[dataIdx][2] = lineList[2];
            data[dataIdx][3] = lineList[3];
            dataIdx+=1;
        }
        System.out.println("data:"+data.toString());
        return data;
    }

    @Test(dataProvider = "allToken")
    public void test001CheckPriceWithTronscan(String fShortName, String fTokenAddr, String sShortName,String centerPrice) throws URISyntaxException, IOException, InterruptedException {
        log.info("fShortName:"+fShortName+" fTokenAddr:"+fTokenAddr+" sShortName:"+sShortName+" centerPrice:"+ centerPrice);

        String scanUrl="";
        scanUrl = priceBase.tronscanApiUrl + "/api/token_trc20?contract=" + fTokenAddr + "&showAll=1";
        HttpResponse transcanRsp = TronlinkApiList.createGetConnect(scanUrl);
        JSONObject transcanRspContent = TronlinkApiList.parseJsonObResponseContent(transcanRsp);
        int total = transcanRspContent.getIntValue("total");
        if (total == 0) {
            log.info("test001DiffFormat Can not find token!!");
        } else {
            Object scanPrice = JSONPath.eval(transcanRspContent, String.join("", "$..trc20_tokens[contract_address='", fTokenAddr, "'].market_info.priceInTrx[0]"));
            if (scanPrice==null){
                log.info("Tronscan has no price!!");
            }else {
                //TKRYQndqTdnU4Bg17gY39rooyK6CzM3ush 没问题，与cmc一致
                //TA2KuSXWLGkbuiBcj3buCXygY4HctZyLsq 没问题，与cmc一致
                // TCt4pND9amuUJ2s4WQHbM5cfsRwJA1GC4i 没问题，与cmc一致
                // TFQG8ctrZJdiGokXZ2Jznd9M9Vv9nNhY3N 没问题，与cmc一致
                // TAiSZ9wQ49jutDCCovHaAqs7KpExuJyqph 没问题，与cmc一致
                // TQX9Kh2tLgBSZQ5cHbYMrPM3yxF9tGprj3 没问题，与cmc一致
                log.info(" scanPrice:" + scanPrice.toString() + "centerPrice:" + centerPrice);
                if (fTokenAddr.equals("TNoUWaZgSNia49qShdzB5VdaNF89it6hxf") || fTokenAddr.equals("TFQG8ctrZJdiGokXZ2Jznd9M9Vv9nNhY3N")
                        || fTokenAddr.equals("TA2KuSXWLGkbuiBcj3buCXygY4HctZyLsq") || fTokenAddr.equals("TFQG8ctrZJdiGokXZ2Jznd9M9Vv9nNhY3N")
                        || fTokenAddr.equals("TN7zQd2oCCguSQykZ437tZzLEaGJ7EGyha")
                        || fTokenAddr.equals("TPQB8bnyVWsmY3hYXwBha83mvMe5NdVdWQ")
                ){
                    Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(scanPrice.toString(), centerPrice, "0.25"));
                } else if (fTokenAddr.equals("TQX9Kh2tLgBSZQ5cHbYMrPM3yxF9tGprj3") || fTokenAddr.equals("TKRYQndqTdnU4Bg17gY39rooyK6CzM3ush")
                        || fTokenAddr.equals("TFQG8ctrZJdiGokXZ2Jznd9M9Vv9nNhY3N") || fTokenAddr.equals("TAiSZ9wQ49jutDCCovHaAqs7KpExuJyqph")
                ) {
                    Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(scanPrice.toString(), centerPrice, "0.4"));
                }else {
                    Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(scanPrice.toString(), centerPrice, "0.1"));
                }
            }
        }
        Thread.sleep(500);
    }

}
