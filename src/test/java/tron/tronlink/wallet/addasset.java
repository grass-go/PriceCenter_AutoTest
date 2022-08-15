package tron.tronlink.wallet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import tron.common.TronlinkApiList;

/**
 * 关注、取消关注v1，尽量不在这里面新加了，使用v2
 */
public class addasset {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;

    private JSONObject tokenJson = new JSONObject();
    List<String> trc10tokenList = new ArrayList<>();
    List<String> trc20ContractAddressList = new ArrayList<>();

    @BeforeClass(enabled = true, description = "please use hex address,get all asset")
    public void removeAllTokenList() throws Exception {
        tokenJson.put("address", "414db7719251ce8ba74549ba35bbdc02418ecde595");
        // 只传输地址的时候，会返回该地址当前添加过的资产
        response = TronlinkApiList.addasset("{\n" + " \"address\": \"414db7719251ce8ba74549ba35bbdc02418ecde595\"}");
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
        JSONArray tokenArray = assetInformation.getJSONArray("data");
        trc10tokenList = TronlinkApiList.getTrc10TokenIdList(tokenArray);
        trc20ContractAddressList = TronlinkApiList.getTrc20AddressList(tokenArray);
    }

    @Test(enabled = false)
    public void addasset_DeadCase() {

        response = TronlinkApiList.addasset("{\n"
                + "  \"address\": \"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe\",\n"
                + "  \"token10\": [\n"
                + "    \"zzz\"\n"
                + "  ],\n"
                + "  \"token10Cancel\": [\n"
                + "    \"aaa\"\n"
                + "  ],\n"
                + "  \"token20\": [\n"
                + "    \"xxx\"\n"
                + "  ],\n"
                + "  \"token20Cancel\": [\n"
                + "    \"ccc\"\n"
                + "  ]\n"
                + "}");
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseJsonObResponseContent(response);
        responseArrayContent = responseContent.getJSONArray("data");
        System.out.println("```````````````````````");
        System.out.println(responseArrayContent);
        System.out.println("```````````````````````");
        // data object
        for (Object json : responseArrayContent) {
            JSONObject jsonObject = (JSONObject) JSON.toJSON(json);
            Assert.assertTrue(jsonObject.containsKey("type"));
            Assert.assertTrue(jsonObject.containsKey("top"));
            Assert.assertTrue(jsonObject.containsKey("isOfficial"));
            Assert.assertTrue(jsonObject.containsKey("name"));
            Assert.assertTrue(jsonObject.containsKey("shortName"));
            Assert.assertTrue(jsonObject.containsKey("id"));
            Assert.assertTrue(jsonObject.containsKey("contractAddress"));
            Assert.assertTrue(jsonObject.containsKey("balance"));
            Assert.assertTrue(jsonObject.containsKey("totalBalance"));
            Assert.assertTrue(jsonObject.containsKey("logoUrl"));
            Assert.assertTrue(jsonObject.containsKey("precision"));
            Assert.assertTrue(jsonObject.containsKey("marketId"));
            Assert.assertTrue(jsonObject.containsKey("price"));
            Assert.assertTrue(jsonObject.containsKey("trxCount"));
            Assert.assertTrue(jsonObject.containsKey("inMainChain"));
            Assert.assertTrue(jsonObject.containsKey("inSideChain"));
        }
    }

    @Test(enabled = true, description = "Test cancel trc10 token to account.1002000 is always exist")
    public void test001CancelTrc10FromAccount() throws Exception {
        tokenJson.clear();
        tokenJson.put("address", "414db7719251ce8ba74549ba35bbdc02418ecde595"); // sophia's address
        tokenJson.put("token10Cancel", trc10tokenList);
        response = TronlinkApiList.addAsset(tokenJson);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
        JSONArray tokenArray = assetInformation.getJSONArray("data");
        Assert.assertTrue(TronlinkApiList.getTrc10TokenIdList(tokenArray).size() == 1);
        // Assert.assertTrue(TronlinkApiList.getTrc20AddressList(tokenArray).size()==trc20ContractAddressList.size());
    }

    @Test(enabled = true, description = "Test add all trc10 token to account.")
    public void test002AddAllTrc10TokenToAccount() throws Exception {
        tokenJson.clear();
        tokenJson.put("address", "414db7719251ce8ba74549ba35bbdc02418ecde595");
        tokenJson.put("token10", trc10tokenList);
        response = TronlinkApiList.addAsset(tokenJson);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
        JSONArray tokenArray = assetInformation.getJSONArray("data");
        Assert.assertTrue(TronlinkApiList.getTrc10TokenIdList(tokenArray).size() == trc10tokenList.size());
        // Assert.assertTrue(TronlinkApiList.getTrc20AddressList(tokenArray).size()==trc20ContractAddressList.size());

    }

    @Test(enabled = true, description = "Test cancel trc20 token ,four trc20 token are always exist")
    public void test003CancelTrc20FromAccount() throws Exception {
        tokenJson.clear();
        tokenJson.put("address", "414db7719251ce8ba74549ba35bbdc02418ecde595");
        tokenJson.put("token20Cancel", trc20ContractAddressList);
        response = TronlinkApiList.addAsset(tokenJson);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
        JSONArray tokenArray = assetInformation.getJSONArray("data");
        Assert.assertTrue(TronlinkApiList.getTrc10TokenIdList(tokenArray).size() > 0);
        Assert.assertTrue(TronlinkApiList.getTrc20AddressList(tokenArray).size() < trc20ContractAddressList.size());
    }

    @Test(enabled = true, description = "Test add all trc20 token to account.")
    public void test004AddAllTrc20ToAccount() throws Exception {
        tokenJson.clear();
        tokenJson.put("address", "414db7719251ce8ba74549ba35bbdc02418ecde595");
        tokenJson.put("token20", trc20ContractAddressList);
        response = TronlinkApiList.addAsset(tokenJson);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
        JSONArray tokenArray = assetInformation.getJSONArray("data");
        Assert.assertTrue(TronlinkApiList.getTrc10TokenIdList(tokenArray).size() > 0);
        // Assert.assertTrue(TronlinkApiList.getTrc20AddressList(tokenArray).size()==trc20ContractAddressList.size());
    }

    @Test(enabled = true, description = "Test add all token to account.")
    public void test005AddAllTokenToAccount() throws Exception {
        tokenJson.clear();
        tokenJson.put("address", "414db7719251ce8ba74549ba35bbdc02418ecde595");
        tokenJson.put("token20", trc20ContractAddressList);
        tokenJson.put("token10", trc10tokenList);
        response = TronlinkApiList.addAsset(tokenJson);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
        JSONArray tokenArray = assetInformation.getJSONArray("data");
        Assert.assertTrue(TronlinkApiList.getTrc10TokenIdList(tokenArray).size() > 0);
        Assert.assertTrue(TronlinkApiList.getTrc20AddressList(tokenArray).size() > 0);
        // Assert.assertTrue(TronlinkApiList.getTrc20AddressList(tokenArray).size()==trc20ContractAddressList.size());
    }

    @Test(enabled = true, description = "restore add all token to account.")
    public void test006AddAllTokenToAccount() throws Exception {
        int RecommendedCurrencyNum = 15;
        if (trc20ContractAddressList.size() == RecommendedCurrencyNum) {
            trc20ContractAddressList = restoreTrc20List();
            tokenJson.clear();
            tokenJson.put("address", "414db7719251ce8ba74549ba35bbdc02418ecde595");
            tokenJson.put("token20", trc20ContractAddressList);
            tokenJson.put("token10", trc10tokenList);
            response = TronlinkApiList.addAsset(tokenJson);
            Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        }
    }

    private ArrayList<String> restoreTrc20List() {
        String[] origins = { "TN3W4H6rK2ce4vX9YnFQHwKENnHjoxb3m9",
                "TAFjULxiVgT4qWk6UZwjqwZXTSaGaqnVp4",
                "THb4CqiFdwNHsWsQCs4JhzwjMWys4aqCbF",
                "TDyvndWuvX5xTBwHPYJi7J3Yq8pq8yh62h",
                "TCFLL5dx5ZJdKnWuesXxi1VPwjLVmWZZy9",
                "TR3DLthpnDdCGabhVDbD3VMsiJoCXY3bZd",
                "TFczxzPhnThNSqr5by8tvxsdCFRRz6cPNq",
                "TKAtLoCB529zusLfLVkGvLNis6okwjB7jf",
                "TYukBQZ2XXCcRCReAUguyXncCWNY9CEiDQ",
                "TUEYcyPAqc4hTg1fSuBCPc18vGWcJDECVw",
                "TEjpEVwm3Xr5VHfa2CWYLqcyKZEGE9CGUz",
                "TEMrHs44addxrafXBWQiQxLjPKwNT8tgWc",
                "TSJWbBJAS8HgQCMJfY5drVwYDa7JBAm6Es",
                "TQn9Y2khEsLJW1ChVWFMSMeRDow5KcbLSE",
                "TSSMHYeV2uE9qYH95DqyoCuNCzEL1NvU3S",
                "TKkeiboTkxXKJpbmVFbv4a8ov5rAfRDMf9",
                "TKTwvJhHAfiUNMrdnWoYkoUY9meYnTgtdk",
                "TQ8kZgvhcP6cHZTPLEJbBojsycZKb8V2wL",
                "TEkxiTehnzSmSe2XqrBj4w32RUN966rdz8",
                "TPYmHEhy5n8TCEfYGqW2rPxsghSfzghPDn",
                "TMwFHYXLJaRUPeW6421aqXL4ZEzPRFGkGT",
                "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t",
                "TKfjV9RNKJJCqPvBtK8L7Knykh7DNWvnYt",
                "TXWkP3jLBqRGojUih1ShzNyDaN5Csnebok",
                "TLa2f6VPqDgRE67v1736s7bJ8Ray5wYjU7",
                "TNUC9Qb1rRpS5CbWLmNMxXBjyFoydXjWFR" };
        return new ArrayList<String>(Arrays.asList(origins));
    }
}
