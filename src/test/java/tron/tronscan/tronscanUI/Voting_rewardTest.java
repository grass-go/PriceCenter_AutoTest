package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.Step;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class Voting_rewardTest {
    private String tronScanNode = Configuration.getByPath("testtronscan.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;

    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {

        }
    }

    @Test(enabled = true,description = "领取奖励")
    public void testVoting_reward() throws Exception {
        Step.login(driver);
         {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        //点定位账户
        driver.findElement(By.cssSelector("#root > main > nav > div > ul > li:nth-child(1) > a > span")).click();
        //点领取奖励
        WebElement vote= driver.findElement ( By.cssSelector("#account_title > div.row.mt-3 > div > div > div > table > tbody > tr:nth-child(4) > td >span"));
        String vote_string = vote.getText();
        String[]  strs=vote_string.split(" ");
        for(int i=0,len=strs.length;i<len;i++){
//            String str11 = strs[0].toString();
//            String voteint = str11.substring(str11.indexOf("\""),str11.lastIndexOf("\""));
//            String voteint = strs[0].replace("\"","").replace("\"","");
//            Double testqq = Integer.parseInt(voteint);
            double testqq = Double.valueOf(strs[0].toString()).doubleValue();
            if( testqq > 0.000000){
                Thread.sleep(300);
                //点领取
                driver.findElement(By.cssSelector("div.row.mt-3 > div > div > div > table > tbody > tr:nth-child(4) > td > a")).click();
                Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted.lead > span")).getText(),"Viewable on account page after transaction is confirmed");
                driver.findElement(By.cssSelector(".sweet-alert > p > span > button"));
            }
        }

    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
