package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import lombok.extern.slf4j.Slf4j;
import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

@Slf4j
public class TransfersTest {

    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    private  String URL = "https://"+tronScanNode+"/#/blockchain/transfers";
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
    public void transfersTest() throws Exception{
        //转账数据
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"txcont\"]/div/div[1]/span/span/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"txcont\"]/div/div[2]/span/span/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"tradingAmount\"]/div/div[1]/span/span/span/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"tradingAmount\"]/div/div[2]/span/span/span/span")).getText().isEmpty());
        driver.close();

    }
    @Test(enabled = true,description = "转账",retryAnalyzer = MyIRetryAnalyzer.class)
    public void transfersType() throws Exception{
        //标题：转账数
        Assert.assertEquals(driver.findElement(By.cssSelector("#txcont > h2 > span:nth-child(2)")).getText(),"Transfer Count");
        //数据更新3sec时间
        driver.findElement(By.cssSelector("#txcont > h2 > span.updatedWrapper > span > span > span:nth-child(1)")).getText().isEmpty();
        //昨日新增转账数
        driver.findElement(By.cssSelector("#txcont > div > div:nth-child(1) > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txcont > div > div:nth-child(1) > div > span > span")).getText(),"New Transfers Yesterday");
        //累计转账数
        driver.findElement(By.cssSelector("#txcont > div > div:nth-child(2) > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txcont > div > div:nth-child(2) > div > span > span")).getText(),"Total Transfer Count");
        //标题：转账额
        Assert.assertEquals(driver.findElement(By.cssSelector("#tradingAmount > h2 > span:nth-child(2)")).getText(),"Trading Volume");
        //更新时间为3min
        driver.findElement(By.cssSelector("#tradingAmount > h2 > span.updatedWrapper > span > span:nth-child(3)")).getText().isEmpty();
        //昨日转账额
        driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(1) > span > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(1) > div:nth-child(3) > span > span")).getText(),"New Trading Volume Yesterday");
        //累计转账额
        driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(2) > span > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(2) > div:nth-child(3) > span > span")).getText(),"Total Trading Volume");
        //转账类型分布
        Assert.assertEquals(driver.findElement(By.cssSelector(" div.representatives-list-wrap > div > div > div.col-md-6.mb-20-style > div > div > div.bg-tron-light > h6 > span")).getText(),"Transfer Type Distribution");
        driver.close();
    }

    @Test(enabled = true,description = "转账列表数据",retryAnalyzer = MyIRetryAnalyzer.class)
    public void transfersList() throws Exception{
        //转账列表时间刷新文案
        driver.findElement(By.cssSelector("#root > main > div.d-flex > span:nth-child(1) > span")).getText().isEmpty();
        Thread.sleep(300);
        //列表中第一行数据点击
        driver.findElement(By.cssSelector("#root > main > div.row > div > div > div > div > div > div > div > div > div > div > table > tbody > tr:nth-child(1) > td:nth-child(1)")).click();
        Thread.sleep(300);
        //跳转交易详情
        Assert.assertEquals(driver.findElement(By.cssSelector(" #root > div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText(),"TRANSACTION DETAILS");
        driver.close();
    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}