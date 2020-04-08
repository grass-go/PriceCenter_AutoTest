package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class TokenOverview {
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    private  String URL = "https://"+tronScanNode+"/#/tokens/list";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;

    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
        }
    }
    @Test(enabled = true,description = "通证概览",retryAnalyzer = MyIRetryAnalyzer.class)
    public void tokenTest() throws Exception{

        driver.findElement(By.cssSelector("div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("main > div > div > a > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div.token-num-wrap> div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div.token-num-wrap > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > span")).getText().isEmpty();
        //7days
        driver.findElement(By.cssSelector("div.token-num-wrap > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > span")).getText().isEmpty();
        //收录的
        driver.findElement(By.cssSelector("div.token-num-wrap> div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span")).getText().isEmpty();
        //7days
        driver.findElement(By.cssSelector("div.token-num-wrap > div:nth-child(2) > div:nth-child(3) > div:nth-child(1) > span")).getText().isEmpty();
        //10trc和20trc
        driver.findElement(By.cssSelector("div.d-flex> div.d-md-flex > div > div > label:nth-child(2) > span:nth-child(2)")).click();
        driver.findElement(By.cssSelector("div.d-md-flex > div > div > label:nth-child(3) > span:nth-child(2)")).click();
        driver.close();

    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
