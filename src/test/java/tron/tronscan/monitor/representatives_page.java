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

public class representatives_page {
    private final String foundationKey = Configuration.getByPath("testng.conf")
            .getString("foundationAccount.key1");
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getStringList("tronscan.ip.list")
            .get(0);


    @Test(enabled = true, retryAnalyzer = MyIRetryAnalyzer.class, description = "委员会-超级代表")
    public void representativesPage() {
        response = TronscanApiList.getGeneral_info(tronScanNode);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronscanApiList.parseResponseContent(response);
        TronscanApiList.printJsonContent(responseContent);
        //total
        Assert.assertTrue(Long.valueOf(responseContent.getString("total")) > 200);
        Assert.assertTrue(Long.valueOf(responseContent.getString("increaseOf30Day")) > 0);
        Pattern patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
        //minBlocksCount
        //出块数量最少
        targetContent = responseContent.getJSONObject("minBlocksCount");
        patternAddress.matcher(targetContent.getString("address")).matches();
        Assert.assertTrue(!targetContent.getString("name").isEmpty());
        Assert.assertTrue(!targetContent.getString("url").isEmpty());
        Assert.assertTrue(Double.valueOf(targetContent.getString("missedTotal")) >= 0);
//        Assert.assertTrue(Double.valueOf(targetContent.getString("producedTotal")) > 100000);
//        Assert.assertTrue(Double.valueOf(targetContent.getString("producePercentage")) < 100);

        //maxBlocksCount
        //出块数量最多
        JSONObject maxContent = responseContent.getJSONObject("maxBlocksCount");
        patternAddress.matcher(maxContent.getString("address")).matches();
        Assert.assertTrue(!maxContent.getString("name").isEmpty());
        Assert.assertTrue(!maxContent.getString("url").isEmpty());
        Assert.assertTrue(Double.valueOf(maxContent.getString("missedTotal")) > 0);
        Assert.assertTrue(Double.valueOf(maxContent.getString("producedTotal")) > 100000);
        Assert.assertTrue(Double.valueOf(maxContent.getString("producePercentage")) < 100);

        //highestEfficiency
        // 出块效率最高
        JSONObject highestContent = responseContent.getJSONObject("highestEfficiency");
        patternAddress.matcher(highestContent.getString("address")).matches();
        Assert.assertTrue(!highestContent.getString("name").isEmpty());
        Assert.assertTrue(!highestContent.getString("url").isEmpty());
        Assert.assertTrue(Double.valueOf(highestContent.getString("missedTotal")) > 0);
        Assert.assertTrue(Double.valueOf(highestContent.getString("producedTotal")) > 100000);
        Assert.assertTrue(Double.valueOf(highestContent.getString("producePercentage")) < 100);
        //lowestEfficiency
        // 出块效率最低
        JSONObject lowestContent = responseContent.getJSONObject("lowestEfficiency");
        patternAddress.matcher(lowestContent.getString("address")).matches();
        Assert.assertTrue(!lowestContent.getString("name").isEmpty());
        Assert.assertTrue(!lowestContent.getString("url").isEmpty());
        Assert.assertTrue(Double.valueOf(lowestContent.getString("missedTotal")) > 0);
//        Assert.assertTrue(Double.valueOf(lowestContent.getString("producedTotal")) > 100000);
//        Assert.assertTrue(Double.valueOf(lowestContent.getString("producePercentage")) < 100);

        //Get response
        response = TronscanApiList.getWitnesses(tronScanNode);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseArrayContent = TronscanApiList.parseArrayResponseContent(response);
        Assert.assertTrue(responseArrayContent.size() >= 27);
        for (int i = 0; i < 28; i++) {
            patternAddress = Pattern.compile("^T[a-zA-Z1-9]{33}");
            Assert.assertTrue(patternAddress.matcher(responseArrayContent.getJSONObject(i).getString("address")).matches());
            Assert.assertTrue(responseArrayContent.getJSONObject(i).containsKey("name"));
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("producer").toString().isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("missedTotal").toString().isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("missedTotal").toString().isEmpty());
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).get("producedTotal").toString().isEmpty());
            String url_key = responseArrayContent.getJSONObject(i).get("url").toString();
            //url长耗时
            Assert.assertTrue(!url_key.isEmpty());
            Assert.assertTrue(
                    responseArrayContent.getJSONObject(i).getLong("latestBlockNumber") <= responseArrayContent.getJSONObject(i).getLong("latestSlotNumber"));

            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("votes") >= 0);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("producePercentage") <= 100);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getDouble("votesPercentage") >= 0);

            //上一轮票数
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("votes") >= 0);
            //变动的投票，有部分数据是负数
            Assert.assertTrue(!responseArrayContent.getJSONObject(i).getString("changeVotes").isEmpty());
            //实时投票
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("realTimeVotes") >= 0);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getLong("producePercentage") <= 100);
            Assert.assertTrue(responseArrayContent.getJSONObject(i).getDouble("votesPercentage") < 100);
            //分红比例和年华收益率
            if (responseArrayContent.getJSONObject(i).getLong("brokerage") < 100) {
                Assert.assertTrue(responseArrayContent.getJSONObject(i).getDouble("annualizedRate") > 0);
            } else if (responseArrayContent.getJSONObject(i).getLong("brokerage") == 100) {
                Assert.assertTrue(responseArrayContent.getJSONObject(i).getDouble("annualizedRate") == 0);

            }

        }
    }
}