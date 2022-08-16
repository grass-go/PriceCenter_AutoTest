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
            if(fTokenAddr.startsWith("0x") || sShortName.equals("USD")){
                continue;
            }
            String fShortName = token.getString("fShortName");
            String centerPrice = token.getString("price");
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
    public void test001DiffFormat(String fShortName, String fTokenAddr, String sShortName,String centerPrice) throws URISyntaxException, IOException, InterruptedException {
        log.info("fShortName:"+fShortName+" fTokenAddr:"+fTokenAddr+" sShortName:"+sShortName+" centerPrice:"+ centerPrice);

        String scanUrl=null;
        if( fTokenAddr.length() == 7 || centerPrice.equals("0")) {
            log.info("No Need Compare!");
        }else {
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
                    log.info(" scanPrice:" + scanPrice.toString() + "centerPrice:" + centerPrice);
                    Assert.assertTrue(PriceCenterApiList.CompareGapInGivenTolerance(scanPrice.toString(), centerPrice, "0.1"));
                }
            }
            Thread.sleep(500);
        }

    }




}