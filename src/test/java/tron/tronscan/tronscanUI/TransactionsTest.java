package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.Step;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class TransactionsTest {
    private String tronScanNode = Configuration.getByPath("testng.conf")
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

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
    public void transactionTest() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        //点击交易
        driver.findElement(By.cssSelector(".dropdown-item:nth-child(7) > span:nth-child(3)")).click();
        //点击第一条交易记录
        driver.findElement(By.cssSelector(".ant-table-row:nth-child(1) .ellipsis_box_start")).click();
        Assert.assertTrue(!driver.findElement(By.cssSelector(".block-status-tag > span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.cssSelector(".badge > span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.cssSelector(".block-status-tag > span")).getText().isEmpty());
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
