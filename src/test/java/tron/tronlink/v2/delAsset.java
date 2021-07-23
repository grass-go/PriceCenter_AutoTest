package tron.tronlink.v2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class delAsset extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONObject object;

    private JSONArray array = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    List<String> token10List = new ArrayList<>();
    List<String> token20List = new ArrayList<>();
    List<String> token721List = new ArrayList<>();

    List<String> token10CancelList = new ArrayList<>();
    List<String> token20CancelList = new ArrayList<>();
    List<String> token721CancelList = new ArrayList<>();

    List<String> tokenDelList = new ArrayList<>();
    List<String> token721DelList = new ArrayList<>();
    Map<String, String> params = new HashMap<>();


    @Test(enabled = true, description = "del focus coin, related api should not see.")
    public void delAsset01() {
        //Prepare parameters and post body; Send delAsset request; Check response of delAsset.
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","DaAyekV3wg5qX%2B1eiWWut6ZPB9Y%3D");
        tokenDelList.clear();
        tokenDelList.add("1002000");
        tokenDelList.add("TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9");
        token721DelList.clear();
        token721DelList.add("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG");
        jsonObject.clear();
        jsonObject.put("address",testDELuser_B58);
        jsonObject.put("tokenDel",tokenDelList);
        jsonObject.put("token721Del",token721DelList);
        response = TronlinkApiList.v2DelAsset(params,jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals(true, responseContent.getBooleanValue("data"));

        //Check assetList page.
        params.put("version","v2");
        params.put("address",testDELuser_B58);
        params.put("signature","5BSZuynlSpo%2FJAn7zyAsU9d2Hpk%3D");
        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Object shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArray=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //Check assetList V1 page
        response = TronlinkApiList.assetlist(testDELuser_B58);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Object shortNamesV1 = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArrayV1=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArrayV1.contains("BTT"));
        Assert.assertFalse(shortNameArrayV1.contains("SUNOLD"));

        //check allAssetList page
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","g0%2FGbl9FFU95hT6O8DYAN8nLBmc%3D");
        params.put("address",testDELuser_B58);
        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //check getAllCollection page
        params.put("signature","rLqVnuTlZgzGd27HyQ%2FTSp3yMc8%3D");
        params.put("version","v2");
        response = TronlinkApiList.v2GetAllCollection(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check allCollections page
        params.remove("version");
        params.put("signature","0SX%2Fzp3x7kE%2FGrfAyj3F9%2BBSrp8%3D");
        response = TronlinkApiList.v2AllCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check account/list
        array.clear();
        params.put("signature","%2F3I27r0%2FGwZ5Rm%2BkiF17Dpg%2FvOg%3D");
        String postStr="{\"TMSsn5sSP9u66dgzf2y63KaTmaLBPgJfj4\":1}";
        array.add(JSONObject.parse(postStr));
        response = TronlinkApiList.v2accountList(params,array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        Object balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        BigDecimal balance = new BigDecimal(balanceObject.toString());
        BigDecimal fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

        //check account/list v1 page
        response = TronlinkApiList.accountList(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        balance = new BigDecimal(balanceObject.toString());
        fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

    }

    @Test(enabled = true, description = "add deleted coin to assetList, related api should see.")
    public void delAsset02() {
        //addAsset for these del coin.
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","jbcAnE2zojRRewzq84eS8skMaTM%3D");
        token10List.add("1002000");
        token20List.add("TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9");
        token721List.add("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG");
        jsonObject.clear();
        jsonObject.put("address",testDELuser_B58);
        jsonObject.put("token10", token10List);
        jsonObject.put("token20", token20List);
        jsonObject.put("token721", token721List);
        response = TronlinkApiList.v2AddAsset(params,jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        //Check assetList page.
        params.put("signature","5BSZuynlSpo%2FJAn7zyAsU9d2Hpk%3D");
        params.put("version","v2");
        params.put("address",testDELuser_B58);
        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Object shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArray=(JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("BTT"));
        Assert.assertTrue(shortNameArray.contains("SUNOLD"));

        //Check assetList V1 page
        response = TronlinkApiList.assetlist(testDELuser_B58);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Object shortNamesV1 = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArrayV1=(JSONArray)shortNames;
        Assert.assertTrue(shortNameArrayV1.contains("BTT"));
        Assert.assertTrue(shortNameArrayV1.contains("SUNOLD"));

        //check allAssetList page
        params.remove("version");
        params.put("signature","g0%2FGbl9FFU95hT6O8DYAN8nLBmc%3D");
        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("BTT"));
        Assert.assertTrue(shortNameArray.contains("SUNOLD"));

        //check getAllCollection page
        params.put("signature","rLqVnuTlZgzGd27HyQ%2FTSp3yMc8%3D");
        params.put("version","v2");
        response = TronlinkApiList.v2GetAllCollection(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check allCollections page
        params.remove("version");
        params.put("signature","0SX%2Fzp3x7kE%2FGrfAyj3F9%2BBSrp8%3D");
        response = TronlinkApiList.v2AllCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check account/list
        array.clear();
        params.put("signature","%2F3I27r0%2FGwZ5Rm%2BkiF17Dpg%2FvOg%3D");
        String postStr="{\"TMSsn5sSP9u66dgzf2y63KaTmaLBPgJfj4\":1}";
        array.add(JSONObject.parse(postStr));
        response = TronlinkApiList.v2accountList(params,array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        Object balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        BigDecimal balance = new BigDecimal(balanceObject.toString());
        log.info("===============########debug##########============");
        log.info("balance:"+balance.toString());
        BigDecimal fullbalance = new BigDecimal("6.792603");
        BigDecimal absgap = fullbalance.subtract(balance).abs();
        log.info("absgap:"+absgap.toString());
        BigDecimal tolerance = new BigDecimal("1");
        Assert.assertTrue(absgap.compareTo(tolerance) == -1);

        //check account/list v1 page
        response = TronlinkApiList.accountList(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        balance = new BigDecimal(balanceObject.toString());
        log.info("===============########debug##########============");
        log.info("balance:"+balance.toString());
        fullbalance = new BigDecimal("6.792603");
        absgap = fullbalance.subtract(balance).abs();
        log.info("absgap:"+absgap.toString());
        tolerance = new BigDecimal("1");
        Assert.assertTrue(absgap.compareTo(fullbalance) == -1);
    }

    @Test(enabled = true, description = "Del \"cancel focus\" coin, check all apis.")
    public void delAsset03() {
        //cancel focus
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","hgo5H0FFWsCGHyxNZ7JehNYShL0%3D");
        token10CancelList.add("1002000");
        token20CancelList.add("TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9");
        token721CancelList.add("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG");
        jsonObject.clear();
        jsonObject.put("address",testDELuser_B58);
        jsonObject.put("token10Cancel", token10List);
        jsonObject.put("token20Cancel", token20List);
        jsonObject.put("token721Cancel", token721List);
        response = TronlinkApiList.v2AddAsset(params,jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        //del prepared token.
        params.put("signature","DaAyekV3wg5qX%2B1eiWWut6ZPB9Y%3D");
        tokenDelList.clear();
        tokenDelList.add("1002000");
        tokenDelList.add("TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9");
        token721DelList.clear();
        token721DelList.add("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG");
        jsonObject.clear();
        jsonObject.put("address",testDELuser_B58);
        jsonObject.put("tokenDel",tokenDelList);
        jsonObject.put("token721Del",token721DelList);
        response = TronlinkApiList.v2DelAsset(params,jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);

        //check all apis as the first case do.
        //Check assetList page.
        params.put("version","v2");
        params.put("address",testDELuser_B58);
        params.put("signature","5BSZuynlSpo%2FJAn7zyAsU9d2Hpk%3D");
        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Object shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArray=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //Check assetList V1 page
        response = TronlinkApiList.assetlist(testDELuser_B58);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Object shortNamesV1 = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArrayV1=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArrayV1.contains("BTT"));
        Assert.assertFalse(shortNameArrayV1.contains("SUNOLD"));

        //check allAssetList page
        params.clear();
        params.put("nonce","12345");
        params.put("secretId","SFSUIOJBFMLKSJIF");
        params.put("signature","g0%2FGbl9FFU95hT6O8DYAN8nLBmc%3D");
        params.put("address",testDELuser_B58);
        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //check getAllCollection page
        params.put("signature","rLqVnuTlZgzGd27HyQ%2FTSp3yMc8%3D");
        params.put("version","v2");
        response = TronlinkApiList.v2GetAllCollection(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check allCollections page
        params.remove("version");
        params.put("signature","0SX%2Fzp3x7kE%2FGrfAyj3F9%2BBSrp8%3D");
        response = TronlinkApiList.v2AllCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check account/list
        array.clear();
        params.put("signature","%2F3I27r0%2FGwZ5Rm%2BkiF17Dpg%2FvOg%3D");
        String postStr="{\"TMSsn5sSP9u66dgzf2y63KaTmaLBPgJfj4\":1}";
        array.add(JSONObject.parse(postStr));
        response = TronlinkApiList.v2accountList(params,array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        Object balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        BigDecimal balance = new BigDecimal(balanceObject.toString());
        BigDecimal fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

        //check account/list v1 page
        response = TronlinkApiList.accountList(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);

        balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        balance = new BigDecimal(balanceObject.toString());
        fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

    }

}
