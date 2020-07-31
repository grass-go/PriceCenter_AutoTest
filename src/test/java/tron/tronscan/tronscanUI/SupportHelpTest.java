package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class SupportHelpTest {
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    private  String URL = "https://"+tronScanNode+"/#/";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
            driver.navigate().refresh();
            Thread.sleep(300);
        } catch (Exception e) {

        }
    }

    @Test(enabled = true,description = "支持与帮助--新手引导",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testHelp() throws Exception{
        //点更多
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > span > span > span > span")).click();
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > div > div:nth-child(3) > h6 > span")).getText().isEmpty();
        //新手引导
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > div > div:nth-child(3) > a:nth-child(3) > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > div > div:nth-child(3) > a:nth-child(3) > span")).click();
//        driver.findElement(By.cssSelector("body > main > div.container > div > div > header > h1")).getText().isEmpty();
        driver.close();
    }

    @Test(enabled = true,description = "支持与帮助--常见问题",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testFAQ() throws Exception{
        //更多中常见问题
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > span > span > span > span")).click();
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > div > div:nth-child(3) > h6 > span")).getText().isEmpty();
        //常见问题
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > div > div:nth-child(3) > a:nth-child(4) > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(9) > div > div > div:nth-child(3) > a:nth-child(4) > span")).click();

        driver.close();
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
