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

public class Representatives {
    private String tronScanNode = Configuration.getByPath("testng.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/sr/representatives";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {

        }
    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "超级代表")
    public void testRepresentatives() throws Exception{
        Assert.assertTrue(!driver.findElement(By.cssSelector("#root > div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty());
        //点申请成为超级代表
        driver.findElement(By.cssSelector("#root > main > div:nth-child(1) > div > div.d-flex.justify-content-end > a > span")).click();
        driver.findElement(By.cssSelector("#root > main > div:nth-child(1) > div.sweet-alert > p > span > button")).click();
        //超级代表文案
        Assert.assertTrue(!driver.findElement(By.cssSelector("div:nth-child(1) > div > div.row.representatives-data-wrap > div> div:nth-child(1) > div > div > div > h2 > span")).getText().isEmpty());
        //出块数量
        Assert.assertTrue(!driver.findElement(By.cssSelector("div:nth-child(1) > div > div.row.representatives-data-wrap > div > div:nth-child(2) > div > div > div > h2 > span")).getText().isEmpty());
        //出块效率
        Assert.assertTrue(!driver.findElement(By.cssSelector("div:nth-child(1) > div > div.row.representatives-data-wrap > div > div:nth-child(3) > div > div > div > h2 > span")).getText().isEmpty());
        //算力分配图
        Assert.assertTrue(!driver.findElement(By.cssSelector("div:nth-child(1) > div > div.row.representatives-data-wrap > div > div > div > div > h6 > span")).getText().isEmpty());
        //点跳转
        driver.findElement(By.cssSelector("div:nth-child(1) > div > div.row.representatives-data-wrap > div > div > div > div > h6 > a > span")).click();
        Assert.assertTrue(!driver.findElement(By.cssSelector("#root > div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.cssSelector("#root > main > div > div > div > div > div > div >div >div > svg > text.highcharts-title > tspan")).getText().isEmpty());
        driver.close();
    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "超级代表详情页")
    public void testRepresentatives01() throws Exception{
        Assert.assertTrue(!driver.findElement(By.cssSelector("#root > div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty());
        //超级代表
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > div > div > a:nth-child(1) > span")).getText().isEmpty());
        //代表下的列表
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > table > tbody > tr:nth-child(1) > td:nth-child(1)")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > table > tbody > tr:nth-child(1) > td:nth-child(2) > div > span")).getText().isEmpty());
        driver.close();
    }

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "超级代表合伙人详情页")
    public void testRepresentatives02() throws Exception{
        //合伙人
        driver.findElement(By.cssSelector(" div.mt-3 > div > div > div > div > a:nth-child(2) > span")).click();
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > div > div > a:nth-child(2) > span")).getText().isEmpty());
        //代表下的列表
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > table > tbody > tr:nth-child(1) > td:nth-child(1)")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > table > tbody > tr:nth-child(1) > td:nth-child(2) > div > span")).getText().isEmpty());
        driver.close();
    }
    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class,description = "超级代表候选人详情页")
    public void testRepresentatives03() throws Exception{
        //候选人
        driver.findElement(By.cssSelector("div.mt-3 > div > div > div > div > a:nth-child(3) > span")).click();
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > div > div > a:nth-child(3) > span")).getText().isEmpty());
        //代表下的列表
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > table > tbody > tr:nth-child(1) > td:nth-child(1)")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > table > tbody > tr:nth-child(1) > td:nth-child(2) > div > span")).getText().isEmpty());
        //点投票
        Assert.assertTrue(!driver.findElement(By.cssSelector("div.mt-3 > div > div > div > a > span")).getText().isEmpty());
        driver.findElement(By.cssSelector("div.mt-3 > div > div > div > a > span")).click();
        Assert.assertTrue(!driver.findElement(By.cssSelector("#root > div.header-top.nav-item-page > div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty());
        driver.close();
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
