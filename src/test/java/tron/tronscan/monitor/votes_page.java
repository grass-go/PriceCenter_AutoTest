package tron.tronscan.monitor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import tron.common.TronscanApiList;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;

public class votes_page {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);


    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "委员会-投票")
    public void votePage() {
        //Get response
        response = TronscanApiList.getVoteWitnesses(tronScanNode);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);

        //Three key,total/totalVotes/fastestRise/data
        Integer total = responseContent.getInteger("total");
        Long totalVotes = responseContent.getLong("totalVotes");
        Integer dataSize = responseContent.getJSONArray("data").size();
        Assert.assertEquals(total, dataSize);
        Assert.assertTrue(totalVotes > 10000000000L);
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        //Key data
        responseArrayContent = responseContent.getJSONArray("data");
        Long lastCycleVotesNum = responseArrayContent.getJSONObject(0).getLong("lastCycleVotes");
        for (int i = 0; i < 28; i++) {
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("hasPage").isEmpty());
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("address")).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("changeVotes") != 0);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("lastRanking") > 0);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("change_cycle"));
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("realTimeRanking") > 0);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("lastCycleVotes") <= lastCycleVotesNum);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("realTimeVotes") > 100000000L);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("lastCycleVotes") > 100000000L);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("votesPercentage"));
            //annualizedRate
            Assert.assertTrue(Double.valueOf(responseArrayContent.getJSONObject(i).get("annualizedRate").toString()) >= 0);
            //包含非url形式
            String url_key = responseArrayContent.getJSONObject(i).get("url").toString();
            Assert.assertTrue(!url_key.isEmpty());

            //分红比例和年华收益率
            if (responseArrayContent.getJSONObject(i).getLong("brokerage") < 100) {
                Assert.assertTrue(responseArrayContent.getJSONObject(i).getDouble("annualizedRate") > 0);
            } else if (responseArrayContent.getJSONObject(i).getLong("brokerage") == 100) {
                Assert.assertTrue(responseArrayContent.getJSONObject(i).getDouble("annualizedRate") == 0);

            }
        }
        //Key fastestRise
        targetContent = responseContent.getJSONObject("fastestRise");
        Assert.assertTrue(targetContent.containsKey("hasPage"));
        Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
        Assert.assertTrue(targetContent.containsKey("name"));
        Assert.assertTrue(targetContent.containsKey("changeVotes"));
        Assert.assertTrue(targetContent.containsKey("lastRanking"));
        Assert.assertTrue(targetContent.containsKey("change_cycle"));
        Assert.assertTrue(targetContent.containsKey("realTimeRanking"));
        Assert.assertTrue(targetContent.containsKey("lastCycleVotes"));
        Assert.assertTrue(targetContent.containsKey("realTimeVotes"));
        Assert.assertTrue(targetContent.getInteger("lastCycleVotes") > 0);
        Assert.assertTrue(targetContent.getInteger("realTimeVotes") > 0);
        Assert.assertTrue(Double.valueOf(targetContent.get("votesPercentage").toString()) <= 100);
        String url_key = targetContent.get("url").toString();
        Assert.assertTrue(!url_key.isEmpty());

        //Key maxVotesRise
        targetContent = responseContent.getJSONObject("maxVotesRise");
        Assert.assertTrue(targetContent.containsKey("hasPage"));
        Assert.assertTrue(patternAddress.matcher(targetContent.getString("address")).matches());
        Assert.assertTrue(targetContent.containsKey("name"));
        Assert.assertTrue(targetContent.containsKey("changeVotes"));
        Assert.assertTrue(targetContent.containsKey("lastRanking"));
        Assert.assertTrue(targetContent.containsKey("change_cycle"));
        Assert.assertTrue(targetContent.containsKey("realTimeRanking"));
        Assert.assertTrue(targetContent.containsKey("lastCycleVotes"));
        Assert.assertTrue(targetContent.containsKey("realTimeVotes"));
        Assert.assertTrue(targetContent.getInteger("lastCycleVotes") > 10000);
        Assert.assertTrue(targetContent.getInteger("realTimeVotes") > 10000);
        Assert.assertTrue(Double.valueOf(targetContent.get("votesPercentage").toString()) <= 100);
        url_key = targetContent.get("url").toString();
        Assert.assertTrue(!url_key.isEmpty());

    }
}
