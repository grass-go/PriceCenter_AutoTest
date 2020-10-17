package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

/**
 * ${params}
 *
 * @Author:jh
 * @Date:2019-08-29 15:22
 */
public class ProposalList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONObject targetContent;
  private JSONObject proposalContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);
  private HashMap<String, String> testAccount;

  /**
   * constructor.提议
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List all the proposals in the blockchain")
  public void getProposalList() {
    //Get response
    Map<String, String> Params = new HashMap<>();
    Params.put("sort", "-number");
    Params.put("limit", "20");
    Params.put("start", "0");
    response = TronscanApiList.getProposalList(tronScanNode, Params);

    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);
    //two object, "total" and "Data"
    Assert.assertTrue(responseContent.size() == 2);
    Integer total = Integer.valueOf(responseContent.get("total").toString());
    JSONArray exchangeArray = responseContent.getJSONArray("data");
    Assert.assertTrue(total > 0);
    //
    for (int i = 0; i < exchangeArray.size(); i++) {
      Assert.assertTrue(Double.valueOf(exchangeArray.getJSONObject(i).get("proposalId").toString()) <= total);
      //expirationTime
      Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("expirationTime").isEmpty());
      Assert.assertTrue(!exchangeArray.getJSONObject(i).getString("createTime").isEmpty());
      //totalVotes
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("totalVotes").toString()) > 0);
      Assert.assertTrue(Long.valueOf(exchangeArray.getJSONObject(i).get("validVotes").toString()) >= 0);
      //proposer json
      proposalContent = exchangeArray.getJSONObject(i).getJSONObject("proposer");
      //producedTotal
      Assert.assertTrue(Double.valueOf(proposalContent.get("producedTotal").toString()) >= 0);
      //address
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(proposalContent.getString("address")).matches());
      //name
      Assert.assertTrue(!proposalContent.get("name").toString().isEmpty());
      //url
      String url_key = proposalContent.get("url").toString();
      Assert.assertTrue(!url_key.isEmpty());
//      各别地址超时报错
//      HttpResponse httpResponse = TronscanApiList.getUrlkey(url_key);
//      Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      //producePercentage
      Assert.assertTrue(Double.valueOf(proposalContent.get("producePercentage").toString()) < 100);
      //latestSlotNumber
      //producer
      //votes
      Assert.assertTrue(Double.valueOf(proposalContent.get("votes").toString()) >= 0);
      //missedTotal
      Assert.assertTrue(Double.valueOf(proposalContent.get("missedTotal").toString()) >= 0);
      //producedTrx
      Assert.assertTrue(Double.valueOf(proposalContent.get("producedTrx").toString()) >= 0);
      //votesPercentage
      Assert.assertTrue(Double.valueOf(proposalContent.get("votesPercentage").toString()) < 100);
      //latestBlockNumber
      Assert.assertTrue(
              proposalContent.getLong("latestBlockNumber") <= proposalContent.getLong("latestSlotNumber"));
      //approvals
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("approvals"));
      //paramters
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("paramters"));
      //state
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("state"));
      //typeApprovals object
      Assert.assertTrue(exchangeArray.getJSONObject(i).containsKey("typeApprovals"));

    }
  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    TronscanApiList.disGetConnect();
  }


}
