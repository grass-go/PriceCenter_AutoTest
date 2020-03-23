package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import sun.jvm.hotspot.types.CIntegerField;
import tron.common.utils.Step;
import tron.common.utils.WebBrowser;

public class Voting_rewardTest {
    private static String URL = "https://tronscan.org/#/";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;

    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(enabled = true,description = "领取奖励")
    public void test() throws Exception {
        Step.login(driver);
         {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        //
        WebElement vote= driver.findElement ( By.cssSelector(".font-weight-bold"));
        String vote_string = vote.getText();
        String[]  strs=vote_string.split(" ");
        for(int i=0,len=strs.length;i<len;i++){
            int voteint = Integer.parseInt(strs[0].toString());
            if( voteint > 0){
                Thread.sleep(300);
                //点领取
                driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div > table > tbody > tr:nth-child(4) > td > a")).click();
                Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted.lead > span")).getText(),"稍后交易确认后可在账户页查看");
                driver.findElement(By.cssSelector(".sweet-alert > p > span > button"));
            }
        }

    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
