package tron.tronlink.dapp;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.Test;
import tron.common.TronlinkApiList;
import tron.common.utils.Configuration;
import tron.tronlink.base.TronlinkBase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dapp_Head extends TronlinkBase {
    private JSONObject responseContent;
    private JSONArray responseArrayContent;
    private JSONObject targetContent;
    private HttpResponse response;
    private String node = Configuration.getByPath("testng.conf")
            .getStringList("tronlink.ip.list")
            .get(0);

    @Test(enabled = true)
    public void head() throws InterruptedException {

        response = TronlinkApiList.head();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
        responseContent = TronlinkApiList.parseResponse2JsonObject(response);
        //data object
        targetContent = responseContent.getJSONObject("data");

        Assert.assertTrue(targetContent.containsKey("roll_data"));
        Assert.assertTrue(targetContent.containsKey("roll_dapp"));
        Assert.assertTrue(targetContent.containsKey("hot_recommend"));
        Assert.assertTrue(targetContent.containsKey("small_banner"));

        JSONArray roll_data = targetContent.getJSONArray("roll_data");
        JSONArray roll_dapp = targetContent.getJSONArray("roll_dapp");
        JSONArray hot_recommend = targetContent.getJSONArray("hot_recommend");
        JSONObject big_banner = targetContent.getJSONObject("big_banner");
        JSONObject small_banner = targetContent.getJSONObject("small_banner");
        JSONArray dapp = targetContent.getJSONArray("dapp");

        Assert.assertEquals(roll_data.size(), 4);
        Assert.assertEquals(roll_dapp.size(), 3);


        for (int n = 0; n < roll_dapp.size(); n++){
            Assert.assertEquals(roll_data.getJSONObject(n).getString("name"), roll_dapp.getJSONObject(n).getString("name"));
            if(n==2){continue;}
            int retryIdx=0;
            for (retryIdx=0;retryIdx<10;retryIdx++){
                log.info("In roll_data #"+n+", cur retry index for image_url is " + retryIdx);
                log.info("image_url:" +roll_data.getJSONObject(n).getString("image_url") );
                int responseCode;
                try {

                    responseCode = TronlinkApiList.createGetConnect(roll_data.getJSONObject(n).getString("image_url"),null,null,null).getStatusLine().getStatusCode();
                    log.info("debug: responseCode:"+responseCode);
                } catch (Exception e) {
                    Thread.sleep(2000);
                    continue;
                }
                if(200==responseCode)
                {
                    retryIdx = 11;
                }
                else{
                    Thread.sleep(10000);
                    continue;
                }
            }
            Assert.assertEquals(12, retryIdx);
            for (retryIdx=0;retryIdx<10;retryIdx++){
                log.info("In roll_data #"+n+", cur retry index for home_url is " + retryIdx);
                log.info("home_url:"+roll_data.getJSONObject(n).getString("home_url"));
                int responseCode;
                try {
                    String home_url;
                    home_url = roll_data.getJSONObject(n).getString("home_url");
                    if(n==2){
                        home_url = home_url.substring(0,home_url.length()-1);
                    }

                    responseCode = TronlinkApiList.createGetConnect(home_url,null,null,null).getStatusLine().getStatusCode();
                    log.info("debug: responseCode:"+ responseCode);

                } catch (Exception e) {
                    Thread.sleep(10000);
                    continue;

                }
                if(200==responseCode)
                {
                    retryIdx = 11;
                }
                else{
                    Thread.sleep(6000);
                    continue;
                }
            }
            Assert.assertEquals(12, retryIdx);
        }

        for (int n = 0; n < hot_recommend.size(); n++){
            JSONObject jo = hot_recommend.getJSONObject(n);
            int retryIdx=0;
            for (retryIdx=0;retryIdx<10;retryIdx++){
                log.info("In hot_recommend #"+n+", cur retry index for image_url is " + retryIdx);
                int responseCode;
                try {
                    responseCode = TronlinkApiList.createGetConnect(jo.getString("image_url"),null,null,null).getStatusLine().getStatusCode();
                } catch(Exception e) {
                    Thread.sleep(2000);
                    continue;
                }
                if(200==responseCode)
                {
                    retryIdx = 11;
                }
                else{
                    Thread.sleep(60000);
                    continue;
                }
            }
            Assert.assertEquals(12, retryIdx);
        }
    }
}
