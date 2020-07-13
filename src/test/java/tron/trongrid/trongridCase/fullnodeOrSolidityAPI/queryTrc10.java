package tron.trongrid.trongridCase.fullnodeOrSolidityAPI;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.fullOrSolidityBase;

public class queryTrc10 extends fullOrSolidityBase {

  JSONObject bttJsonBody;
  JSONArray assetList;
  JSONArray paginatedAssetList;
  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue by account from trongrid")
  public void test01GetAssetIssueByAccountFromTrongrid() {
    response = getAssetIssueByAccount(bttOwnerAddress);
    responseContent = parseResponseContent(response);
    bttJsonBody = responseContent.getJSONArray("assetIssue").getJSONObject(0);
    printJsonContent(bttJsonBody);
    Assert.assertTrue(bttJsonBody.getLong("start_time") == 1548000000000L);
    Assert.assertTrue(bttJsonBody.getInteger("trx_num") == 1);
    Assert.assertTrue(bttJsonBody.getLong("total_supply") == 990000000000000000L);
    Assert.assertTrue(bttJsonBody.getInteger("precision") == 6);
    Assert.assertTrue(bttJsonBody.getInteger("num") == 1);
    Assert.assertEquals(bttJsonBody.getString("name"),"BitTorrent");
    Assert.assertTrue(bttJsonBody.containsKey("end_time"));
    Assert.assertTrue(bttJsonBody.containsKey("description"));
    Assert.assertTrue(bttJsonBody.containsKey("url"));
    Assert.assertTrue(bttJsonBody.getString("owner_address").equals(bttOwnerAddress));
    Assert.assertEquals(bttJsonBody.getInteger("id"),Integer.valueOf(bttTokenId));
    Assert.assertEquals(bttJsonBody.getString("abbr"),"BTT");
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue by id from trongrid")
  public void test02GetAssetIssueByIdFromTrongrid() {
    response = getAssetIssueById(Integer.valueOf(bttTokenId), false);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertEquals(responseContent,bttJsonBody);
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue list from trongrid")
  public void test03GetAssetIssueListFromTrongrid() {
    response = getAssetIssueList(false);
    responseContent = parseResponseContent(response);
    //printJsonContent(responseContent);
    assetList = responseContent.getJSONArray("assetIssue");
    Assert.assertTrue(assetList.size() >= 3006);
    //Assert.assertTrue(assetList.contains(bttJsonBody));

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get paginated assetissue list from trongrid")
  public void test04GetPaginatedAssetIssueListFromTrongrid() {
    response = getPaginatedAssetIssueList(1970,50, false);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    paginatedAssetList = responseContent.getJSONArray("assetIssue");
    Assert.assertTrue(paginatedAssetList.size() == 50);
    System.out.println("bttJsonBody:" + bttJsonBody);
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue list from trongrid solidity")
  public void test05GetAssetIssueListFromTrongridSolidity() {
    response = getAssetIssueList(true);
    responseContent = parseResponseContent(response);
    //printJsonContent(responseContent);
    JSONArray assetListFromSolidity = responseContent.getJSONArray("assetIssue");
    Assert.assertEquals(assetListFromSolidity,assetList);

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get paginated assetissue list from trongrid solidity")
  public void test06GetPaginatedAssetIssueListFromTrongridSolidity() {
    response = getPaginatedAssetIssueList(1970,50,true);
    responseContent = parseResponseContent(response);
    //printJsonContent(responseContent);
    Assert.assertEquals(paginatedAssetList,responseContent.getJSONArray("assetIssue"));
  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue by id from trongrid solidity")
  public void test07GetAssetIssueByIdFromTrongridSolidity() {
    response = getAssetIssueById(Integer.valueOf(bttTokenId),true);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertEquals(responseContent,bttJsonBody);
  }



  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    try {
      disConnect();
    } catch (Exception e) {

    }
  }
}
