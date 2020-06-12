package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class AccountTest {
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    private String URL = "https://" + tronScanNode + "/#/blockchain/accounts";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;

    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {

        }
    }

    @Test(enabled = true,description = "账户页-跳转到地址页",retryAnalyzer = MyIRetryAnalyzer.class)
    public void accountList() throws Exception{
        //账户
        driver.findElement(By.cssSelector("div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty();
        //点地址
        driver.findElement(By.cssSelector("div > table > tbody > tr:nth-child(2) > td:nth-child(2) > div > div:nth-child(1)")).click();
        //地址页账户标题
        driver.findElement(By.cssSelector("#root > div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty();
        driver.close();

    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}