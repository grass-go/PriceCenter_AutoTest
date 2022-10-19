package tron.tronlink.wallet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class balance extends TronlinkBase {

    private static HttpResponse response;
    private JSONObject responseContent;
    private JSONObject allAssetresponseContent;
    private JSONObject dataContent;
    private JSONObject object;
    private JSONObject assetListRespContent;
    Map<String, String> params = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    public static Map<String,String> trxPriceMap= new HashMap<>();

    @Test(enabled = true, description = "test balance the same with got from allAssetList", groups = {"NoSignature"})
    public void TestBalanceLowVersionWithNoSig() {
        String token20s = "TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn,TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t,TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT,TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8,TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR,TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9,TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4,TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9,TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq,TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S,TXJgMdjVX5dKiQaUi9QobwNxtSQaFqccvd,TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt,TDQaYrhQynYV9aXTYj63nwLAafRffWSEj6,TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA,TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP,TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt,TJMZNqLDPLHDMakYxBKonSvd26VdjWHVhP,TYykbfrB89g1PJRg25HLDpYYesEuXKW4pT,TXGFpNx9DcGpToTmrn3W61XoP1McUUSxE1,TF9SRok9tr1gWrJiPxc7DnVH2Lym1DaoSZ,TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv,TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4";
        params.put("address",quince_B58);
        params.put("tokens", token20s);
        response = TronlinkApiList.v1Balance(params,null);
        //compare balance for each token.
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Object balances = JSONPath.eval(responseContent, String.join("", "$..balance"));
        List<String> balancesList = new ArrayList<>();
        balancesList = (ArrayList)balances;
        Assert.assertTrue(balancesList.size()>0);
    }

    @Test(enabled = true, description = "test balance the same with got from allAssetList")
    public void TestBalanceHighVersionWithNoSig() {
        String token20s = "TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn,TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t,TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT,TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8,TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR,TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9,TUpMhErZL2fhh4sVNULAbNKLokS4GjC1F4,TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9,TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq,TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S,TXJgMdjVX5dKiQaUi9QobwNxtSQaFqccvd,TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt,TDQaYrhQynYV9aXTYj63nwLAafRffWSEj6,TL5x9MtSnDy537FXKx53yAaHRRNdg9TkkA,TE2RzoSV3wFK99w6J9UnnZ4vLfXYoxvRwP,TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt,TJMZNqLDPLHDMakYxBKonSvd26VdjWHVhP,TYykbfrB89g1PJRg25HLDpYYesEuXKW4pT,TXGFpNx9DcGpToTmrn3W61XoP1McUUSxE1,TF9SRok9tr1gWrJiPxc7DnVH2Lym1DaoSZ,TRg6MnpsFXc82ymUPgf5qbj59ibxiEDWvv,TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4";
        params.put("address",quince_B58);
        params.put("tokens", token20s);

        headers.put("System", "Android");
        headers.put("Version", "4.11.0");
        response = TronlinkApiList.v1BalanceNoSig(params,headers);
        //compare balance for each token.
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(20004, responseContent.getIntValue("code"));
        Assert.assertEquals("Error param.", responseContent.getString("message"));
    }

    @Test(enabled = true, description = "test balance the same with got from allAssetList")
    public void test001TestBalanceSameWithAllAssestList() {
        // access to allAssetList to get all trc20 token.
        params.clear();
        params.put("address",quince_B58);
        response = TronlinkApiList.V2AllAssetList(params);
        allAssetresponseContent = TronlinkApiList.parseResponse2JsonObject(response);

        Object trc20Tokens = JSONPath.eval(allAssetresponseContent,"$..contractAddress");
        log.info("debug:"+trc20Tokens.toString());
        List<String> tokenIdList = new ArrayList<>();
        tokenIdList = (ArrayList)trc20Tokens;
        String token20s = "";
        for (String token:tokenIdList) {
            if (token.equals("")) {
                continue;
            }
            token20s = token20s + token + ",";
        }
        token20s = token20s.substring(0,token20s.length()-1);
        log.info("debug: token20s: "+ token20s);
        //access to balance api to get balance
        params.put("address",quince_B58);
        params.put("tokens", token20s);

        headers.put("System", "Android");
        headers.put("Version", "4.11.0");
        response = TronlinkApiList.v1Balance(params,headers);
        //compare balance for each token.
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        for (String token:tokenIdList) {
            if (token.equals("")) {
                continue;
            }
            log.info("cur Token: " + token);
            Object allAssetBalance = JSONPath.eval(allAssetresponseContent, String.join("", "$..data.token[contractAddress='", token, "'].balance[0]"));
            Object allAssetprecision = JSONPath.eval(allAssetresponseContent, String.join("", "$..data.token[contractAddress='", token, "'].precision[0]"));
            log.info("debug jsonpath1: "+String.join("", "$..data.token[contractAddress='", token, "'].precision[0]") );
            log.info("debug jsonpath2: " + String.join("", "$..data.token[contractAddress='", token, "'].balance[0]"));
            String balanceJsonPath = "$..data.balanceInfoList[tokenAddress='"+ token+ "'].balance[0]";
            String decimalJsonPath = "$..data.balanceInfoList[tokenAddress='" + token +  "'].decimal[0]";
            log.info("balanceJsonPath: " + balanceJsonPath);
            log.info("decimalJsonPath: " + decimalJsonPath);
            Object balanceAPIBalance = JSONPath.eval(responseContent, balanceJsonPath);
            Object balanceAPIDecimal = JSONPath.eval(responseContent, decimalJsonPath);
            log.info("allAssetprecision:" + allAssetprecision.toString());
            log.info(", balanceAPIDecimal:" + balanceAPIDecimal.toString());

            log.info("allAssetBalance:" + allAssetBalance + ", balanceAPIBalance:" + balanceAPIBalance);
            Assert.assertEquals(allAssetprecision.toString(), balanceAPIDecimal.toString());

            Double unit = (Double)Math.pow(10, Integer.valueOf(balanceAPIDecimal.toString()));
            log.info("unit:" + unit);
            BigDecimal balanceAPIBalance_bd = new BigDecimal(balanceAPIBalance.toString()).divide(BigDecimal.valueOf(unit));
            BigDecimal allAssetBalance_bd = new BigDecimal(allAssetBalance.toString());
            log.info("balanceAPIBalance_bd: "+balanceAPIBalance_bd + ", allAssetBalance_bd:"+ allAssetBalance_bd );
            Assert.assertEquals(0,allAssetBalance_bd.compareTo(balanceAPIBalance_bd));
        }

    }

}
