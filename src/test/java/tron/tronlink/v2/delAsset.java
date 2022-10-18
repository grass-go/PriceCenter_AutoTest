package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.Constants;
import tron.common.TronlinkApiList;
import tron.common.utils.AddressConvert;
import tron.common.utils.Keys;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.v2.model.CommonRsp;
import tron.tronlink.v2.trc1155.AssertAllCollection;
import tron.tronlink.v2.trc1155.AssertGetAllCollection;
import tron.tronlink.v2.trc1155.getCollectionList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tron.common.Constants.POST;

@Slf4j
public class delAsset extends TronlinkBase {
    private JSONObject responseContent;
    private HttpResponse response;

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

    AssertAllCollection ac = new AssertAllCollection();
    AssertGetAllCollection gac = new AssertGetAllCollection();

    addAsset addAsset = new addAsset();


    @Test(enabled = true, description = "del focus coin, related api should not see.")
    public void delAsset01() {
        //Prepare parameters and post body; Send delAsset request; Check response of delAsset.
        params.clear();
        params.put("address",testDELuser_B58);
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
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals(true, responseContent.getBooleanValue("data"));

        //Check assetList page.
        params.put("version","v2");
        params.put("address",testDELuser_B58);
        params.put("signature","5BSZuynlSpo%2FJAn7zyAsU9d2Hpk%3D");
        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Object shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArray=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //Check assetList V1 page
        Map<String, String> paramsInURL = new HashMap<>();
        paramsInURL.put("address", testDELuser_B58);
        response = TronlinkApiList.assetlist(testDELuser_B58,paramsInURL);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Object shortNamesV1 = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArrayV1=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArrayV1.contains("BTT"));
        Assert.assertFalse(shortNameArrayV1.contains("SUNOLD"));

        //check allAssetList page
        params.clear();
        params.put("address",testDELuser_B58);
        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //check getAllCollection page
        params.put("version","v2");
        response = TronlinkApiList.v2GetAllCollection(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check allCollections page
        params.remove("version");
        response = TronlinkApiList.v2AllCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check account/list
        array.clear();
        String postStr="{\"TMSsn5sSP9u66dgzf2y63KaTmaLBPgJfj4\":1}";
        array.add(JSONObject.parse(postStr));
        response = TronlinkApiList.v2accountList(params,array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        Object balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        BigDecimal balance = new BigDecimal(balanceObject.toString());
        BigDecimal fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

        //check account/list v1 page
        response = TronlinkApiList.accountList(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        balance = new BigDecimal(balanceObject.toString());
        fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

    }

    @Test(enabled = true, description = "add deleted coin to assetList, related api should see.")
    public void delAsset02() throws Exception {
        //addAsset for these del coin.
        params.clear();
        params.put("address",testDELuser_B58);
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
        params.put("version","v2");
        params.put("address",testDELuser_B58);
        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Object shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArray=(JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("BTTOLD"));
        Assert.assertTrue(shortNameArray.contains("SUNOLD"));

        //Check assetList V1 page
        Map<String, String> paramsInURL = new HashMap<>();
        paramsInURL.put("address", testDELuser_B58);
        response = TronlinkApiList.assetlist(testDELuser_B58,paramsInURL);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Object shortNamesV1 = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArrayV1=(JSONArray)shortNames;
        Assert.assertTrue(shortNameArrayV1.contains("BTTOLD"));
        Assert.assertTrue(shortNameArrayV1.contains("SUNOLD"));

        //check allAssetList page
        params.remove("version");
        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("BTTOLD"));
        Assert.assertTrue(shortNameArray.contains("SUNOLD"));

        //check getAllCollection page
        params.put("version","v2");
        response = TronlinkApiList.v2GetAllCollection(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check allCollections page
        params.remove("version");
        params.put("signature","0SX%2Fzp3x7kE%2FGrfAyj3F9%2BBSrp8%3D");
        response = TronlinkApiList.v2AllCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertTrue(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check account/list
        array.clear();
        String postStr="{\"TMSsn5sSP9u66dgzf2y63KaTmaLBPgJfj4\":1}";
        array.add(JSONObject.parse(postStr));
        response = TronlinkApiList.v2accountList(params,array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        Object balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        BigDecimal balance = new BigDecimal(balanceObject.toString());
        log.info("===============########debug##########============");
        log.info("balance:"+balance.toString());
        BigDecimal fullbalance = new BigDecimal("6.792603");
        //BigDecimal fullbalance = new BigDecimal("5.079483");
        BigDecimal absgap = fullbalance.subtract(balance).abs();
        log.info("absgap:"+absgap.toString());
        BigDecimal tolerance = new BigDecimal("2");
        Assert.assertTrue(absgap.compareTo(tolerance) == -1);

        //check account/list v1 page
        response = TronlinkApiList.accountList(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

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
    public void delAsset03() throws Exception {
        //cancel focus
        params.clear();
        params.put("address",testDELuser_B58);
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
        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Object shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArray=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //Check assetList V1 page
        Map<String, String> paramsInURL = new HashMap<>();
        paramsInURL.put("address", testDELuser_B58);
        response = TronlinkApiList.assetlist(testDELuser_B58,paramsInURL);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        Object shortNamesV1 = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        JSONArray shortNameArrayV1=(JSONArray)shortNames;
        Assert.assertFalse(shortNameArrayV1.contains("BTT"));
        Assert.assertFalse(shortNameArrayV1.contains("SUNOLD"));

        //check allAssetList page
        params.clear();
        params.put("address",testDELuser_B58);
        response = TronlinkApiList.V2AllAssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].shortName"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("BTT"));
        Assert.assertFalse(shortNameArray.contains("SUNOLD"));

        //check getAllCollection page
        params.put("version","v2");
        response = TronlinkApiList.v2GetAllCollection(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check allCollections page
        params.remove("version");
        response = TronlinkApiList.v2AllCollections(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        shortNames = JSONPath.eval(responseContent, String.join("","$..data.token[*].contractAddress"));
        shortNameArray = (JSONArray)shortNames;
        Assert.assertFalse(shortNameArray.contains("TTi4R9NBnkHnvxwMVe4C3Xbjh5NMZqZfJG"));

        //check account/list
        array.clear();
        String postStr="{\"TMSsn5sSP9u66dgzf2y63KaTmaLBPgJfj4\":1}";
        array.add(JSONObject.parse(postStr));
        response = TronlinkApiList.v2accountList(params,array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        Object balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        BigDecimal balance = new BigDecimal(balanceObject.toString());
        BigDecimal fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

        //check account/list v1 page
        response = TronlinkApiList.accountList(array);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        balanceObject = JSONPath.eval(responseContent, "$..data.balanceList[0].balance");
        balance = new BigDecimal(balanceObject.toString());
        fullbalance = new BigDecimal("6.792603");
        Assert.assertTrue(balance.compareTo(fullbalance) == -1);

    }

    private GetSign sig = new GetSign();
    getCollectionList g = new getCollectionList();
    @Test(description = "删除1155资产")
    public void delAsset_1155() throws Exception {
        initParams();
        String delToken = g.expFollowAndHold;
        String user = address721_B58;
        jsonObject.put(Keys.Address,user);
        List<String> dels = new ArrayList<>();
        dels.add(delToken);
        jsonObject.put(Keys.delToken1155, dels);

        params = sig.GenerateParams(user, Constants.delAssetUrl,POST);
        response = TronlinkApiList.v2DelAsset(params,jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        String rspStr = TronlinkApiList.parseResponse2String(response);
        CommonRsp rsp = JSONObject.parseObject(rspStr, CommonRsp.class);
        if ((rsp.getCode() != 0) || (rsp.getData() != true) || (!"OK".equals(rsp.getMessage()))){
            org.testng.Assert.assertEquals(false, true);
        }

        // 删除之后无法在全部资产查询
        ac.AssertNotFoundInAC(user, delToken, false);
        // 删除之后无法在首页查询
        gac.AssertNotFoundInGAC(user, delToken, false);
        // 恢复现场再关注下
        addAsset.addAssetByTokenType(1155, true, AddressConvert.toHex(user), delToken);
        ac.AssertNotFoundInAC(user, delToken, true);
        gac.AssertNotFoundInGAC(user, delToken, true);

    }

    // 初始化参数
    private void initParams(){
        params.clear();
        jsonObject.clear();
    }

}
