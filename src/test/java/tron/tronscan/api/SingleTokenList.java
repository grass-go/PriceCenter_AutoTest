package tron.tronscan.api;
import tron.common.utils.MyIRetryAnalyzer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import tron.common.TronscanApiList;
import tron.common.utils.Configuration;

@Slf4j
public class SingleTokenList {

  private final String foundationKey = Configuration.getByPath("testng.conf")
      .getString("foundationAccount.key1");
  private JSONObject responseContent;
  private JSONArray responseArrayContent;
  private JSONObject targetContent;
  private HttpResponse response;
  private String tronScanNode = Configuration.getByPath("testng.conf")
      .getStringList("tronscan.ip.list").get(0);

  /**
   * constructor.
   */
  @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class, description = "List a single trc10 token's detail")
  public void test01getSingleTokenList() {
    //Get response
    response = TronscanApiList.getSingleTokenList(tronScanNode);
    log.info("code is " + response.getStatusLine().getStatusCode());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = TronscanApiList.parseResponseContent(response);
    TronscanApiList.printJsonContent(responseContent);

    //Three key, "total","totalAll","data"
    Assert.assertTrue(responseContent.size() == 4);
    Long total = Long
            .valueOf(responseContent.get("total").toString());
    Long totalAll = Long
            .valueOf(responseContent.get("totalAll").toString());
    Assert.assertTrue(totalAll >= total);
    Assert.assertTrue(responseContent.containsKey("contractMap"));
    //data
    responseArrayContent = responseContent.getJSONArray("data");
    for (int i = 0; i < responseArrayContent.size(); i++) {
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("totalTransactions") >= 0);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("country"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("tokenID") >= 1000000);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("participated") > 0);
      Assert.assertTrue(responseArrayContent.getJSONObject(i)
              .getInteger("precision") >= 0 && responseArrayContent.getJSONObject(i).getInteger("precision") <= 7);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("num"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("available"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("reputation"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("description").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("issuedPercentage"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("nrOfTokenHolders"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("voteScore"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("dateCreated").isEmpty());
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getLong("price").toString()) >= 100000);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("percentage"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("startTime").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("id"));
      Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).getLong("issued").toString()) >= 100000000);
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getLong("trxNum").toString()) >= 1000000);
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("abbr").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("website"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("github"));
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getLong("availableSupply").toString()) == 0);
      Assert.assertTrue(Long.valueOf(responseArrayContent.getJSONObject(i).getLong("totalSupply").toString()) >= 1000000000);
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("index"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("frozenTotal"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("frozen"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("canShow"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("remaining"));
      //此地址打印链接打不开https://www.hashcoins.com/,所以不能用状态判断
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("url").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("frozenPercentage"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("isBlack"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("remainingPercentage"));
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("name").isEmpty());
      Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
      Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("ownerAddress")).matches());
      Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("endTime").isEmpty());
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("white_paper"));
      Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("social_media"));
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
