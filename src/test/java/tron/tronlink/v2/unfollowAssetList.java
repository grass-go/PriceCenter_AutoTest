package tron.tronlink.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.springframework.util.StringUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import lombok.extern.slf4j.Slf4j;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;
import tron.tronlink.v2.model.Token;
import tron.tronlink.v2.model.UnfollowAssertListRsp;

@Slf4j
public class unfollowAssetList extends TronlinkBase {
    private JSONObject responseContent;
    private HttpResponse response;
    private JSONArray array = new JSONArray();
    private addAsset addAsset = new addAsset();
    private GetSign sig = new GetSign();

    @Test(enabled = true, description = "有余额有价值的币，取消关注,可以查到", groups={"P2"})
    public void unfollowAssetList01() throws Exception {
        // bttold 有余额的一个官方币
        String followToken = "1002000";
        // 先关注一个10币
        boolean follow = addAsset.addAssetByTokenType(10, true, unfollowAPIUser41, followToken);

        // 取消关注一个10币
        follow = addAsset.addAssetByTokenType(10, false, unfollowAPIUser41, followToken);
        org.testng.Assert.assertEquals(true, follow);

        // 查询
        Map<String, String> params = sig.GenerateParams(unfollowAPIUser, "/api/wallet/v2/unfollowAssetList", "GET");
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        // 断言
        assertFound(followToken);
        // 断言 unfollow接口包含national接口。 非法定的national字段为：空
        Object usdtNational = JSONPath.eval(responseContent, "$..data.token[name='BitTorrent Old'].national[0]");
        Assert.assertEquals("", usdtNational.toString());

    }

    @Test(enabled = true, description = "有余额有价值的币，关注,无法查到")
    public void unfollowAssetList02() throws Exception {
        String followToken = "1002000";
        // 先关注一个10币
        boolean follow = addAsset.addAssetByTokenType(10, true, unfollowAPIUser41, followToken);
        org.testng.Assert.assertEquals(true, follow);

        Map<String, String> params = sig.GenerateParams(unfollowAPIUser, "/api/wallet/v2/unfollowAssetList", "GET");
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        assertNotFound(followToken);
        // 恢复现场
        follow = addAsset.addAssetByTokenType(10, false, unfollowAPIUser41, followToken);
        log.info("restore unfollowAssetList02, result = " + follow);
    }

    // 断言无法查到该种币
    public void assertNotFound(String destToken) {
        int count = (int) JSONPath.eval(responseContent, "$..data.count[0]");
        org.testng.Assert.assertNotEquals(count, 0);
        Object token10s = JSONPath.eval(responseContent, "$..id");
        Object token20s = JSONPath.eval(responseContent, "$..contractAddress");
        List<String> token10sArray = (List<String>) token10s;
        List<String> token20sArray = (List<String>) token20s;
        token10sArray.addAll(token20sArray);
        for (String token : token10sArray) {
            if (StringUtils.isEmpty(token)) {
                continue;
            }
            log.info("token = " + token + " desttoken = " + destToken);
            org.testng.Assert.assertNotEquals(destToken.equals(token), true, "未关注接口里面应该没有当前token");
        }
    }

    // 断言可以查到该种币
    public void assertFound(String destToken) {
        int count = (int) JSONPath.eval(responseContent, "$..data.count[0]");
        org.testng.Assert.assertNotEquals(count, 0);
        Object token10s = JSONPath.eval(responseContent, "$..id");
        Object token20s = JSONPath.eval(responseContent, "$..contractAddress");
        List<String> token10sArray = (List<String>) token10s;
        List<String> token20sArray = (List<String>) token20s;
        token10sArray.addAll(token20sArray);
        boolean found = false;
        for (String token : token10sArray) {
            if (destToken.equals(token)) {
                found = true;
                break;
            }
        }
        org.testng.Assert.assertEquals(found, true);
    }

    @Test(enabled = true, description = "验证余额balance=0的普通币，取消关注，不在unfollow接口", groups={"P2"})
    public void unfollowAssetList03() throws Exception {
        // 无余额的一个 doge coin
        String followToken = "THbVQp8kMjStKNnf2iCY6NEzThKMK5aBHg";
        // 关注
        boolean follow = addAsset.addAssetByTokenType(20, true, unfollowAPIUser41, followToken);

        // 取消关注一个币
        follow = addAsset.addAssetByTokenType(20, false, unfollowAPIUser41, followToken);
        org.testng.Assert.assertEquals(true, follow);

        // 查询
        Map<String, String> params = sig.GenerateParams(unfollowAPIUser, "/api/wallet/v2/unfollowAssetList", "GET");
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        // 断言
        assertNotFound(followToken);

    }

    @Test(enabled = true, description = "验证余额balance=0的普通币，加关注，也不在unfollow接口",groups={"P2"} )
    public void unfollowAssetList03_1() throws Exception {
        // 无余额的一个官方币 eth
        String followToken = "THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF";
        // 先关注一个币
        boolean follow = addAsset.addAssetByTokenType(20, true, unfollowAPIUser41, followToken);
        org.testng.Assert.assertEquals(true, follow);

        // 查询
        Map<String, String> params = sig.GenerateParams(unfollowAPIUser, "/api/wallet/v2/unfollowAssetList", "GET");
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        // 断言
        assertNotFound(followToken);
        follow = addAsset.addAssetByTokenType(20, false, unfollowAPIUser41, followToken);
        log.info("unfollowAssetList03_1 unfollow eth, result = " + follow);

    }

    @Test(enabled = true, description = "系统推荐币（线上：USDT）没有余额,balance=0， 取消关注，在unfollow接口。",groups={"P2"})
    public void unfollowAssetList04() throws Exception {
        // 无余额的一个推荐币 usdt
        String followToken = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        // 先关注
        boolean follow = addAsset.addAssetByTokenType(20, true, unfollowAPIUser41, followToken);

        // 取消关注一个币
        follow = addAsset.addAssetByTokenType(20, false, unfollowAPIUser41, followToken);
        org.testng.Assert.assertEquals(true, follow);

        // 查询
        Map<String, String> params = sig.GenerateParams(unfollowAPIUser, "/api/wallet/v2/unfollowAssetList", "GET");
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        // 断言
        assertFound(followToken);
        // 断言 unfollow接口包含national接口。 USDT的national字段为：DM
        Object usdtNational = JSONPath.eval(responseContent, "$..data.token[name='Tether USD'].national[0]");
        Assert.assertEquals("DM", usdtNational.toString());

        follow = addAsset.addAssetByTokenType(20, true, unfollowAPIUser41, followToken);
        log.info("restore follow usdt, result = " + follow);


    }

    @Test(enabled = true, description = "系统推荐币（线上：USDT）没有余额balance=0， 关注，不在unfollow接口。", groups={"P2"})
    public void unfollowAssetList04_1() throws Exception {
        // 无余额的一个推荐币
        String followToken = "THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF";
        // 先关注一个币
        boolean follow = addAsset.addAssetByTokenType(20, true, unfollowAPIUser41, followToken);
        org.testng.Assert.assertEquals(follow, true);

        // 查询
        Map<String, String> params = sig.GenerateParams(unfollowAPIUser, "/api/wallet/v2/unfollowAssetList", "GET");
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        // 断言
        assertNotFound(followToken);
        follow = addAsset.addAssetByTokenType(20, false, unfollowAPIUser41, followToken);
        log.info("restore unfollowAssetList04_1, result = ", follow);

    }

    @Test(enabled = true, description = "排序验证：trxCount > 余额 > 简称忽略大小写排序")
    public void unfollowAssetList05() {
        Map<String, String> params = sig.GenerateParams(commonUser, "/api/wallet/v2/unfollowAssetList", "GET");
        //Map<String, String> params = sig.GenerateParams(followAsset, "/api/wallet/v2/unfollowAssetList", "GET");

        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        String rspStr = TronlinkApiList.parseResponse2String(response);
        UnfollowAssertListRsp rsp = JSONObject.parseObject(rspStr, UnfollowAssertListRsp.class);
        checkSort(rsp);
    }

    public void checkSort(UnfollowAssertListRsp rsp) {
        Assert.assertEquals(rsp.getData().getToken().size() == rsp.getData().getCount(), true);
        Assert.assertEquals(rsp.getData().getToken().size() > 0, true);

        // 获取原顺序id
        List<String> ids = getIdsByTokens(rsp.getData().getToken());

        // 排序
        Collections.sort(rsp.getData().getToken(), new Comparator<Token>() {
            @Override
            public int compare(Token t1, Token t2) {
                if (t1.getTrxCount() < t2.getTrxCount()) {
                    return 1;
                }
                if (t1.getTrxCount() > t2.getTrxCount()) {
                    return -1;
                }

                if (t1.getBalance() < t2.getBalance()) {
                    return 1;
                }
                if (t1.getBalance() > t2.getBalance()) {
                    return -1;
                }
                return t1.getShortName().toLowerCase().compareTo(t2.getShortName().toLowerCase());
            }
        });

        // 对二者进行比较
        List<String> newIDs = getIdsByTokens(rsp.getData().getToken());
        org.testng.Assert.assertEquals(ids.size() == newIDs.size(), true);
        int i = 0;
        for (String id : newIDs) {
            org.testng.Assert.assertEquals(id, ids.get(i));
            i++;
        }
    }

    public List<String> getIdsByTokens(List<Token> tokens) {
        log.info("tokens = " + JSONObject.toJSONString(tokens));
        List<String> ids = new ArrayList<>();
        for (Token t : tokens) {
            if (!StringUtils.isEmpty(t.getId())) {
                ids.add(t.getId());
            }
            if (!StringUtils.isEmpty(t.getContractAddress())) {
                ids.add(t.getContractAddress());
            }
        }
        return ids;
    }

    @AfterClass(enabled = true)
    public void restore() throws Exception {
        // usdt
        String followToken = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        boolean follow = addAsset.addAssetByTokenType(20, true, unfollowAPIUser41, followToken);
        log.info("restore usdt, result = ", follow);

        // doge coin
        followToken = "THbVQp8kMjStKNnf2iCY6NEzThKMK5aBHg";
        follow = addAsset.addAssetByTokenType(20, false, unfollowAPIUser, followToken);
        log.info("restore doge coin, result = ", follow);

        // bttold, 由用例2看出，本来应该关注的状态，不应该再次关注。注视掉这几句。
        //followToken = "1002000";
        //follow = addAsset.addAssetByTokenType(10, true, unfollowAsset41, followToken);
        //log.info("restore 1002000, result = ", follow);

    }

    //v4.11.0-done
    @Test(enabled = true, description = "验证unfollowlist包含字段DefiType")
    public void testUnfollowWithDefiType(){
        Map<String, String> params = sig.GenerateParams(unfollowAPIUser, "/api/wallet/v2/unfollowAssetList", "GET");
        response = TronlinkApiList.V2UnfollowAssetList(params);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);

        //v4.11.0-done
        //check lp token defiType=2
        Object defiType = JSONPath.eval(responseContent,"$..data.token[contractAddress='TEjpEVwm3Xr5VHfa2CWYLqcyKZEGE9CGUz'].defiType[0]");
        Assert.assertEquals("2", defiType.toString());
        defiType = JSONPath.eval(responseContent,"$..data.token[contractAddress='TXX1i3BWKBuTxUmTERCztGyxSSpRagEcjX'].defiType[0]");
        Assert.assertEquals("2", defiType.toString());
        //check jtoken defiType=1
        defiType = JSONPath.eval(responseContent,"$..data.token[contractAddress='TUY54PVeH6WCcYCd6ZXXoBDsHytN9V5PXt'].defiType[0]");
        Assert.assertEquals("1", defiType.toString());
        //check none-lptoken, none-jtoken , defiType=0


    }


}
