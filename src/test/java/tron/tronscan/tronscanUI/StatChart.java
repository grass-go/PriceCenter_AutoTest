package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class StatChart {
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    private  String URL = "https://"+tronScanNode+"/#/data/stats";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;

    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
        }
    }
    @Test(enabled = true,description = "交易",retryAnalyzer = MyIRetryAnalyzer.class)
    public void transTest() throws Exception{
        //统计图表
        driver.findElement(By.cssSelector("div.header-top> div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty();
        //顶部交易，可点击
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(1) > a > span")).click();
        //交易标题
        driver.findElement(By.cssSelector("#transfer > div.d-flex > div.charts-title.mr-3.ml-3 > span > span")).getText().isEmpty();
        //图1
        driver.findElement(By.cssSelector("#transfer > div > div:nth-child(1) > div > a > span > span")).getText().isEmpty();
        //图2
        driver.findElement(By.cssSelector("#transfer > div > div:nth-child(2) > div > a > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#transfer > div > div:nth-child(2) > div > a > span > span")).click();
        driver.close();
    }

    @Test(enabled = true,description = "流通",retryAnalyzer = MyIRetryAnalyzer.class)
    public void circulationTest() throws Exception{
        //顶部流通，可点击
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(2) > a > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(2) > a > span")).click();
        //流通标题
        driver.findElement(By.cssSelector("#currency > div > div.charts-title > span > span")).getText().isEmpty();
        //图1
        driver.findElement(By.cssSelector("#currency > div> div:nth-child(1) > div > a > span > span")).getText().isEmpty();
        //图2
        driver.findElement(By.cssSelector("#currency > div > div:nth-child(2) > div > a > span > span")).getText().isEmpty();
        //图3
        driver.findElement(By.cssSelector("#currency > div> div:nth-child(3) > div > a > span > span")).getText().isEmpty();
        //图4
        driver.findElement(By.cssSelector("#currency > div > div:nth-child(4) > div > a > span > span")).getText().isEmpty();
        //图4，可跳转
        driver.findElement(By.cssSelector("#currency > div > div:nth-child(4) > div > a > span > span")).click();
        driver.close();
    }

    @Test(enabled = true,description = "账户",retryAnalyzer = MyIRetryAnalyzer.class)
    public void accountTest() throws Exception{
        //顶部账户，可点击
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(3) > a > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(3) > a > span")).click();
        //账户标题
        driver.findElement(By.cssSelector("#address > div > div.charts-title> span > span")).getText().isEmpty();
        //图1
        driver.findElement(By.cssSelector("#address > div> div:nth-child(1) > div > a > span > span")).getText().isEmpty();
        //图2
        driver.findElement(By.cssSelector("#address > div > div:nth-child(2) > div > a > span > span")).getText().isEmpty();
        //图3
        driver.findElement(By.cssSelector("#address > div > div:nth-child(3) > div > a > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#address > div > div:nth-child(3) > div > a > span > span")).click();
        driver.close();
    }

    @Test(enabled = true,description = "区块",retryAnalyzer = MyIRetryAnalyzer.class)
    public void blockTest() throws Exception{
        //顶部区块，可点击
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(4) > a > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(4) > a > span")).click();
        //区块
        driver.findElement(By.cssSelector("#block > div> div.charts-title > span > span")).getText().isEmpty();
        //图1
        driver.findElement(By.cssSelector("#block > div> div:nth-child(1) > div > a > span > span")).getText().isEmpty();
        //图2
        driver.findElement(By.cssSelector("#block > div> div:nth-child(2) > div > a > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#block > div> div:nth-child(2) > div > a > span > span")).click();
        driver.close();
    }

    @Test(enabled = true,description = "合约",retryAnalyzer = MyIRetryAnalyzer.class)
    public void contractTest() throws Exception{
        //顶部合约，可点击
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(5) > a > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(5) > a > span")).click();
        //合约标题
        driver.findElement(By.cssSelector("#contract > div > div.charts-title> span > span")).getText().isEmpty();
        //图1
        driver.findElement(By.cssSelector("#contract > div> div:nth-child(1) > div > a > span > span")).getText().isEmpty();
        //图2
        driver.findElement(By.cssSelector("#contract > div> div:nth-child(2) > div > a > span > span")).getText().isEmpty();
        //图3
        driver.findElement(By.cssSelector("#contract > div> div:nth-child(3) > div > a > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#contract > div> div:nth-child(3) > div > a > span > span")).click();
        driver.close();
    }

    @Test(enabled = true,description = "超级代表",retryAnalyzer = MyIRetryAnalyzer.class)
    public void superTest() throws Exception{
        //顶部超级代表，可点击
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(6) > a > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(6) > a > span")).click();
        //超级代表标题
        driver.findElement(By.cssSelector("#sr > div > div.charts-title> span > span")).getText().isEmpty();
        //图1，实时出块分布
        driver.findElement(By.cssSelector("#sr > div> div > div > a > span > span")).getText().isEmpty();
        //图2，超级代表获票分布
        driver.findElement(By.cssSelector("#sr > div > div:nth-child(2) > div > a > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#sr > div > div:nth-child(2) > div > a > span > span")).click();
        driver.close();
    }

    @Test(enabled = true,description = "网络",retryAnalyzer = MyIRetryAnalyzer.class)
    public void networkTest() throws Exception{
        //顶部网络，可点击
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(7) > a > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > nav > ul > li:nth-child(7) > a > span")).click();
        //网络标题
        driver.findElement(By.cssSelector("#network > div > div.charts-title> span > span")).getText().isEmpty();
        //图1，每日能量消耗
        driver.findElement(By.cssSelector("#network > div > div > div > a > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("#network > div > div > div > a > span > span")).click();
        driver.close();
    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
