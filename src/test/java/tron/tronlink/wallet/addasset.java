package tron.tronlink.wallet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;

public class addasset {
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String node = Configuration.getByPath("testng.conf")
      .getStringList("tronlink.ip.list")
      .get(0);
  private JSONObject tokenJson = new JSONObject();
  List<String> trc10tokenList = new ArrayList<>();
  List<String> trc20ContractAddressList = new ArrayList<>();

  @BeforeClass(enabled = true)
  public void removeAllTokenList() throws Exception{
    tokenJson.put("address","TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    //只传输地址的时候，会返回该地址当前添加过的资产
    response = TronlinkApiList.addasset(node,"{\n" +" \"address\": \"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe\"}");
    JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
    JSONArray tokenArray = assetInformation.getJSONArray("data");
    trc10tokenList = TronlinkApiList.getTrc10TokenIdList(tokenArray);
    trc20ContractAddressList = TronlinkApiList.getTrc20AddressList(tokenArray);
    tokenJson.put("token10Cancel",trc10tokenList);
    tokenJson.put("token20Cancel",trc20ContractAddressList);
    response = TronlinkApiList.addasset(node,"{\n" +" \"address\": \"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe\"}");
  }

  @Test(enabled = true)
  public void addasset(){

    response = TronlinkApiList.addasset(node,"{\n"
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
    System.out.println(responseArrayContent);
    //data object
    for (Object json:responseArrayContent
    ) {
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
    System.out.println(responseArrayContent.size());
  }
  @Test(enabled = true,description = "Test add all trc10 token to account.")
  public void test001AddAllTrc10TokenToAccount() throws Exception {
    tokenJson.clear();
    tokenJson.put("address","TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    tokenJson.put("token10",trc10tokenList);
    response = TronlinkApiList.addAsset(node,tokenJson);
    JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
    JSONArray tokenArray = assetInformation.getJSONArray("data");
    System.out.println("trc10 token size are:" + tokenArray.size());
    Assert.assertTrue(tokenArray.size() >= 0);
  }

  @Test(enabled = true,description = "Test add all trc20 token to account.")
  public void test002AddAllTrc20ToAccount() throws Exception {
    tokenJson.clear();
    tokenJson.put("address","TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    tokenJson.put("token20",trc20ContractAddressList);
    response = TronlinkApiList.addAsset(node,tokenJson);
    JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
    JSONArray tokenArray = assetInformation.getJSONArray("data");
    //TronlinkApiList.printJsonArray(tokenArray);
    System.out.println("trc10 + trc 20 token size are:" + tokenArray.size());
    Assert.assertTrue(tokenArray.size() >= 0);


    tokenJson.clear();
    tokenJson.put("address","TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    //只传输地址的时候，会返回该地址当前添加过的资产
    response = TronlinkApiList.addAsset(node,tokenJson);
    assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
    tokenArray = assetInformation.getJSONArray("data");
    System.out.println("Now account have token size:" + tokenArray.size());
  }

  @Test(enabled = true,description = "Test cancel trc10 token to account.")
  public void test003CancelTrc10FromAccount() throws Exception {
    tokenJson.clear();
    tokenJson.put("address","TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    tokenJson.put("token10Cancel",trc10tokenList);
    response = TronlinkApiList.addAsset(node,tokenJson);
    JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
    JSONArray tokenArray = assetInformation.getJSONArray("data");
    Assert.assertTrue(tokenArray.size() >= 5);
  }


  @Test(enabled = true,description = "Test cancel trc20 token to account.")
  public void test004CancelTrc20FromAccount() throws Exception {
    tokenJson.clear();
    tokenJson.put("address","TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
    tokenJson.put("token20Cancel",trc20ContractAddressList);
    response = TronlinkApiList.addAsset(node,tokenJson);
    JSONObject assetInformation = TronlinkApiList.parseJsonObResponseContent(response);
    JSONArray tokenArray = assetInformation.getJSONArray("data");
    Assert.assertTrue(tokenArray.size() >= 1);
  }

}
