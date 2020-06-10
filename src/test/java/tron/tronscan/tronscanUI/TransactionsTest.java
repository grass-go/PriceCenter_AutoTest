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
private  String URL = "https://"+tronScanNode+"/#/blockchain/transactions";
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

        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"txcont\"]/div/div[1]/span/span/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"txcont\"]/div/div[2]/span/span/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"tradingAmount\"]/div/div[1]/span/span/span/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"tradingAmount\"]/div/div[2]/span/span/span/span")).getText().isEmpty());

        //点击第一条交易记录

    }
    @Test(enabled = true,description = "交易类型分布",retryAnalyzer = MyIRetryAnalyzer.class)
    public void transType() throws Exception{
        //标题：交易数
        Assert.assertEquals(driver.findElement(By.cssSelector("#txcont > h2 > span:nth-child(2)")).getText(),"TxCount");
        //数据更新3sec时间
        driver.findElement(By.cssSelector("#txcont > h2 > span.updatedWrapper > span > span > span:nth-child(1)")).getText().isEmpty();
        //昨日新增交易数
        driver.findElement(By.cssSelector("#txcont > div > div:nth-child(1) > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txcont > div > div:nth-child(1) > div > span > span")).getText(),"New transactions yesterday");
        //累计交易数
        driver.findElement(By.cssSelector("#txcont > div > div:nth-child(2) > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#txcont > div > div:nth-child(2) > div > span > span")).getText(),"Cumulative TxCount");
        //标题：交易额
        Assert.assertEquals(driver.findElement(By.cssSelector("#tradingAmount > h2 > span:nth-child(2)")).getText(),"Trading volume");
        //更新时间为3min
        driver.findElement(By.cssSelector("#tradingAmount > h2 > span.updatedWrapper > span > span:nth-child(3)")).getText().isEmpty();
        //昨日交易额
        driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(1) > span > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(1) > div:nth-child(3) > span > span")).getText(),"New trading volume yesterday");
        //累计交易额
        driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(2) > span > span > span > span")).getText().isEmpty();
        Assert.assertEquals(driver.findElement(By.cssSelector("#tradingAmount > div > div:nth-child(2) > div:nth-child(3) > span > span")).getText(),"Total trading volume");
        //交易类型分布
        Assert.assertEquals(driver.findElement(By.cssSelector(" div.representatives-list-wrap > div > div > div.col-md-6.mb-20-style > div > div > div.bg-tron-light > h6 > span")).getText(),"Transaction type distribution");
        //列表时间刷新文案
//        driver.findElement(By.cssSelector("#root > main > div.row > div > div > div.d-flex > span:nth-child(1) > span")).getText().isEmpty();
        //列表中第一行数据点击
//        driver.findElement(By.cssSelector("div > table > tbody > tr:nth-child(1) > td.ant_table.ant-table-row-cell-break-word > div > span > a > div")).click();
        //跳转正常
//        Assert.assertEquals(driver.findElement(By.cssSelector(" div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText(),"TRANSACTION DETAILS");
    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
