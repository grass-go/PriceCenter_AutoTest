package tron.tronlink.v2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.Constants;
import tron.common.TronlinkApiList;
import tron.common.utils.AddressConvert;
import tron.common.utils.Keys;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.v2.model.CommonRsp;
import tron.tronlink.v2.trc1155.AssertGetAllCollection;
import tron.tronlink.v2.trc1155.getCollectionList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关注、取消关注v2
 */
@Slf4j
public class addAsset extends TronlinkBase {
    private JSONObject responseContent;
    private JSONObject dataContent;
    private HttpResponse response;
    private JSONObject object;

    private JSONArray array = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    List<String> trc10tokenList = new ArrayList<>();
    List<String> trc721tokenList = new ArrayList<>();
    Map<String, String> params = new HashMap<>();

    AssertGetAllCollection gac = new AssertGetAllCollection();


    // 关注资产，首页assetList接口可见，取消关注，首页assetList不可见
    @Test(enabled = true, groups={"P2"})
    public void addAsset01() throws InterruptedException {
        params.clear();
        trc10tokenList.clear();
        jsonObject.clear();
        params.put("address",commonUser41);

        trc10tokenList.add("1002000");
        jsonObject.put("address", commonUser41);
        jsonObject.put("token10", trc10tokenList);
        response = TronlinkApiList.v2AddAsset(params, jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals(true, responseContent.getBooleanValue("data"));

        Thread.sleep(500);

        params.clear();
        params.put("address", commonUser41);

        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        dataContent = responseContent.getJSONObject("data");
        JSONArray tokenArray = dataContent.getJSONArray("token");
        boolean flag = false;
        JSONObject token;
        for (int i = 0; i < tokenArray.size(); i++) {
            token = tokenArray.getJSONObject(i);
            if ("1002000".equals(token.getString("id"))) {
                flag = true;
                log.info("1111");
            }
        }
        Assert.assertTrue(flag);

        params.clear();
        trc10tokenList.clear();
        jsonObject.clear();
        params.put("address",commonUser41);
        trc10tokenList.add("1002000");
        jsonObject.put("address", commonUser41);
        jsonObject.put("token10Cancel", trc10tokenList);
        response = TronlinkApiList.v2AddAsset(params, jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals(true, responseContent.getBooleanValue("data"));

        Thread.sleep(500);

        params.clear();
        params.put("address", commonUser41);
        // params.put("address","41F985738AE54FD87ED6CD07065905EBEA355E66CD");

        response = TronlinkApiList.v2AssetList(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        dataContent = responseContent.getJSONObject("data");
        tokenArray = dataContent.getJSONArray("token");
        flag = false;
        for (int i = 0; i < tokenArray.size(); i++) {
            token = tokenArray.getJSONObject(i);
            if ("1002000".equals(token.getString("id"))) {
                flag = true;
                log.info("22222");
            }
        }
        Assert.assertFalse(flag);

    }

    // 关注trc721资产，getAllCollections接口可见，取消关注，getAllCollections不可见

    @Test(enabled = true, groups={"P2"})
    public void addAsset02() throws Exception {
        params.clear();
        trc721tokenList.clear();
        jsonObject.clear();
        params.put("address",address721_Hex);

        trc721tokenList.add("TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99");// Pitaya (PITAYA)
        trc721tokenList.add("TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h");// TAHIGO KOHINAGI (TAHIGO)
        jsonObject.put("address", address721_Hex);
        jsonObject.put("token721", trc721tokenList);
        response = TronlinkApiList.v2AddAsset(params, jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        Assert.assertEquals(0, responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals(true, responseContent.getBooleanValue("data"));

        Thread.sleep(500);

        params.clear();
        params.put("address", address721_B58);
        params.put("version", "v2");
        response = TronlinkApiList.v2GetAllCollection(params);
        // Retry 2 time
        for (int i = 0; i < 2; i++) {
            if (responseContent.getIntValue("code") == 4500) {
                Thread.currentThread().sleep(5000);
                response = TronlinkApiList.v2GetAllCollection(params);
            } else {
                break;
            }
        }

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));

        Object actualName = JSONPath.eval(responseContent,
                "$.data[contractAddress='TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99'].name");
        System.out.println("actualName Object is: " + actualName.toString());
        JSONArray actualNameArray = (JSONArray) actualName;
        Assert.assertEquals(1, actualNameArray.size());
        Assert.assertEquals("Pitaya", actualNameArray.get(0));

        Object actualName2 = JSONPath.eval(responseContent,
                "$.data[contractAddress='TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h'].name");
        System.out.println("actualName2 Object is: " + actualName2.toString());
        JSONArray actualNameArray2 = (JSONArray) actualName2;
        Assert.assertEquals(1, actualNameArray2.size());
        Assert.assertEquals("TAHIGO KOHINAGI", actualNameArray2.get(0));

        // cancel focus 721
        params.clear();
        trc721tokenList.clear();
        jsonObject.clear();
        params.put("address",address721_Hex);
        trc721tokenList.add("TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99");// Pitaya (PITAYA)
        trc721tokenList.add("TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h");// TAHIGO KOHINAGI (TAHIGO)

        jsonObject.put("address", address721_Hex);
        jsonObject.put("token721Cancel", trc721tokenList);
        response = TronlinkApiList.v2AddAsset(params, jsonObject);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals(true, responseContent.getBooleanValue("data"));

        Thread.sleep(500);

        params.clear();
        params.put("address", address721_B58);
        params.put("version", "v2");

        response = TronlinkApiList.v2GetAllCollection(params);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));

        actualName = JSONPath.eval(responseContent,
                "$..data[contractAddress='TBeAjUWtvsJ1NCouwtk7eCVrPzCc2Kco99'].name");
        System.out.println("actualName Object is: " + actualName.toString());
        Assert.assertEquals(actualName.toString(), "[]");

        actualName2 = JSONPath.eval(responseContent,
                "$..data[contractAddress='TPLVhGLc1BWHCHBMnBYakNsqhXQ7v5xp2h'].name");
        System.out.println("actualName2 Object is: " + actualName2.toString());
        Assert.assertEquals(actualName2.toString(), "[]");
    }

    GetSign sig = new GetSign();

    // 关注资产、取消关注
    // follow: true 表示关注目标token
    // unfollow: true 表示取消关注目标token
    public boolean addAssetByTokenType(int type, boolean follow, String address41, String token) throws Exception {
        JSONObject jsonObject = new JSONObject();
        Map<String, String> params;
        params = sig.GenerateParams(AddressConvert.toHex(address41), Constants.addAssetUrl, "POST");

        jsonObject.put("address", address41);
        // "1002000"
        List<String> trcTokens = new ArrayList<>();
        trcTokens.add(token);
        if (follow) {
            if (type == 10) {
                jsonObject.put(Keys.FollowToken10, trcTokens);
            }
            if (type == 20) {
                jsonObject.put(Keys.FollowToken20, trcTokens);
            }
            if (type == Constants.Token1155) {
                jsonObject.put(Keys.FollowToken1155, trcTokens);
            }
        } else {
            if (type == 10) {
                jsonObject.put(Keys.unFollowToken10, trcTokens);
            }
            if (type == 20) {
                jsonObject.put(Keys.unFollowToken20, trcTokens);
            }
            if (type == 1155) {
                jsonObject.put(Keys.unFollowToken1155, trcTokens);
            }
        }

        response = TronlinkApiList.v2AddAsset(params, jsonObject);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        String rspStr = TronlinkApiList.parseResponse2String(response);
        CommonRsp rsp = JSONObject.parseObject(rspStr, CommonRsp.class);
        if ((rsp.getCode() != 0) || (rsp.getData() != true) || (!"OK".equals(rsp.getMessage()))) {
            return false;
        }
        return true;
    }

    // 取消关注用例中关注的trc10
    @AfterClass(enabled = true)
    public void after() throws Exception {
        params.clear();
        trc10tokenList.clear();
        jsonObject.clear();
        params.put("address",commonUser41);
        // params.put("signature","7%2B%2F36luYNVcnean87VL9AaY4O1o%3D");
        trc10tokenList.add("1002000");
        jsonObject.put("address", commonUser41);
        // jsonObject.put("address","41F985738AE54FD87ED6CD07065905EBEA355E66CD");
        jsonObject.put("token10Cancel", trc10tokenList);
        response = TronlinkApiList.v2AddAsset(params, jsonObject);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        Assert.assertTrue(responseContent.containsKey("code"));
        Assert.assertTrue(responseContent.containsKey("message"));
        Assert.assertTrue(responseContent.containsKey("data"));
        Assert.assertEquals(0, responseContent.getIntValue("code"));
        Assert.assertEquals("OK", responseContent.getString("message"));
        Assert.assertEquals(true, responseContent.getBooleanValue("data"));
    }

    getCollectionList g = new getCollectionList();

    @Test(enabled = false, description = "1155 关注资产，覆盖了取消和关注1155的全部逻辑")
    public void test001_1155_addAsset() throws Exception {
        String followToken = g.expFollowAndNoHold;
        String user = address721_B58;
        // 先取消关注一个1155币
        boolean follow = addAssetByTokenType(Constants.Token1155, false, user, followToken);
        org.testng.Assert.assertEquals(true, follow);
        // 关注一个1155币
        follow = addAssetByTokenType(Constants.Token1155, true, user, followToken);
        org.testng.Assert.assertEquals(true, follow);
        // 查询首页，预期可以查到
        gac.AssertNotFoundInGAC(user, followToken, true);

        // 先取消关注一个1155币
        follow = addAssetByTokenType(Constants.Token1155, false, user, followToken);
        org.testng.Assert.assertEquals(true, follow);

        //再次查询首页，预期无法查到
         gac.AssertNotFoundInGAC(user, followToken, false);

        // restore
        follow = addAssetByTokenType(Constants.Token1155, true, user, followToken);
        org.testng.Assert.assertEquals(true, follow);
    }
}
