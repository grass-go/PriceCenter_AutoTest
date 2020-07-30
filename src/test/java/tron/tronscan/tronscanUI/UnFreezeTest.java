package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.print.DocFlavor;

import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.Step;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class UnFreezeTest {
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

    @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class,description = "解冻")
    public void testUnFreeze() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".btn-danger.mr-2:nth-child(1)")).click();
        Thread.sleep(200);
        //点弹窗-解冻
//        driver.findElement(By.cssSelector(".btn-lg.btn-danger ")).click();
//        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > h2")).getText(), "TRX Unfrozen");
    }

    @Test(enabled = false,description = "账户页标签",retryAnalyzer = MyIRetryAnalyzer.class)
    public void tagTest() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        //定位标签
        driver.findElement(By.cssSelector("#root > main > nav > div > ul > li:nth-child(3) > a > span")).getText().isEmpty();
        //标签名称
        Assert.assertEquals(driver.findElement(By.cssSelector("#account_tags > div > div > div > div.d-flex.justify-content-between.account-switch > h5 > span")).getText(),"Tags list");
        //标题说明
        driver.findElement(By.cssSelector("#account_tags > div > div > div > p > span")).getText().isEmpty();
        //点添加
        driver.findElement(By.cssSelector("#account_tags > div > div > div > div.d-flex.justify-content-between.account-switch > button")).getText().isEmpty();
//        driver.findElement(By.cssSelector("#account_tags > div > div > div > div.d-flex.justify-content-between.account-switch > button")).click();
        Thread.sleep(200);

        //添加标题内容
//        Assert.assertEquals(driver.findElement(By.cssSelector("body > div:nth-child(13) > div > div.modal.show > div > div > div.text-center.modal-header > h5 > span")).getText(),"Add a tag");


    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
