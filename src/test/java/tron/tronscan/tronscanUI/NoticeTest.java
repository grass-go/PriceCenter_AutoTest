package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class NoticeTest {
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    private  String URL = "https://"+tronScanNode+"/#/blockchain/blocks";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
        }
    }
    @Test(enabled = true,description = "广播栏")
    public void testTop() throws Exception{
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(1) > span > span > a > span > span")).click();
        Boolean real_display = driver.findElement(By.cssSelector("#root > main > div.homeNotice> div > div > div > div.hidden-mobile > div > img")).isDisplayed();
        if(real_display){
           //广播三条数据
           //driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div/div[1]/div/div/div"));
           //
           driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-0.item > span.title")).getText().isEmpty();
           driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-0.item > span.date")).getText().isEmpty();
            //
           driver.findElement(By.cssSelector("#root > main > div.homeNotice> div > div > div > div.hidden-mobile > div > div > a.item-1.item > span.title")).getText().isEmpty();
           driver.findElement(By.cssSelector("#root > main > div.homeNotice> div > div > div > div.hidden-mobile > div > div > a.item-1.item > span.date")).getText().isEmpty();
            //
           driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-2.item > span.title")).getText().isEmpty();
           driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-2.item > span.date")).getText().isEmpty();
           //more
           driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > a > span")).click();
           driver.close();
       }

    }

    @Test(enabled = true,description = "数据栏",retryAnalyzer = MyIRetryAnalyzer.class)
    public void topDataTest() throws Exception {
        Boolean onlinedata_display = driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0")).isDisplayed();
        if (onlinedata_display){
            //线上节点
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(2) > p > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(2) > h2 > span")).getText().isEmpty();
            Thread.sleep(200);
            //区块高度
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(3) > p > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(3) > h2 > span")).getText().isEmpty();
            Thread.sleep(200);
            //峰值
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(4) > p > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(4) > div > h2 >span:nth-child(1) > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(4) > div > h2 >span:nth-child(2) > span")).getText().isEmpty();
            Thread.sleep(200);
            //一天交易
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(5) > p > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(5) > h2 > span")).getText().isEmpty();
            Thread.sleep(200);
            //总账户数
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(6) > p > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0> div > div > div:nth-child(6) > h2 > span")).getText().isEmpty();

        }
        //数据详情
        Thread.sleep(200);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div/div[2]/div/div/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div/div[2]/div/div/div[2]/div[1]/span/span")).getText().isEmpty();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div/div[2]/div/div/div[2]/div[3]/span/span")).getText().isEmpty();
        Thread.sleep(200);
        //收起
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div/div[2]/div/div/span/span")).click();
        driver.close();

    }
    @Test(enabled = true,description = "过去14天交易数",retryAnalyzer = MyIRetryAnalyzer.class)
    public void day14_transactions() throws Exception{
        Boolean transactions_display = driver.findElement(By.cssSelector("div:nth-child(1) > div > div>div >div> div > div > svg > rect.highcharts-plot-background")).isDisplayed();
        if (transactions_display) {
            driver.findElement(By.cssSelector("div:nth-child(1) > div > div> div > div >div >div > svg > text.highcharts-title > tspan")).getText().isEmpty();
            Thread.sleep(200);
            driver.findElement(By.cssSelector("div:nth-child(1) > div > div> div > div >div >div >svg > g.highcharts-legend > g > g > g.highcharts-series-0 > text > tspan")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(1) > div > div> div > div >div >div >svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(1) > div > div> div > div >div >div >svg > g.highcharts-legend > g > g > g.highcharts-series-2 > text > tspan")).getText().isEmpty();
            Thread.sleep(200);
            driver.findElement(By.cssSelector("div:nth-child(1) > div > div>div >div> div > div > svg > g.highcharts-exporting-group > g ")).click();
            driver.close();
        }

    }

    @Test(enabled = true,description = "过去14天账户数",retryAnalyzer = MyIRetryAnalyzer.class)
    public void day14_accounts() throws Exception{
        Boolean accounts_display = driver.findElement(By.cssSelector("div:nth-child(2) > div > div>div >div> div > div > svg > rect.highcharts-plot-background")).isDisplayed();
        if (accounts_display) {
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div> div > div >div >div > svg > text.highcharts-title > tspan")).getText().isEmpty();
            Thread.sleep(200);
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div> div > div >div >div >svg > g.highcharts-legend > g > g > g.highcharts-series-0 > text > tspan")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div> div > div >div >div >svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div> div > div >div >div >svg > g.highcharts-legend > g > g > g.highcharts-series-2 > text > tspan")).getText().isEmpty();
            Thread.sleep(200);
            //driver.findElement(By.cssSelector("div:nth-child(2) > div > div>div >div> div > div > svg > g.highcharts-exporting-group > g")).click();
            driver.close();
        }
    }

    @Test(enabled = true,description = "账户",retryAnalyzer = MyIRetryAnalyzer.class)
    public void top_accounts() throws Exception{
        Boolean topacc_display = driver.findElement(By.cssSelector("div.row.indxe-page-bottom-sec-pc > div.mt-md-0.text-center > div")).isDisplayed();
        if (topacc_display){
            driver.findElement(By.cssSelector("div.row.indxe-page-bottom-sec-pc > div.mt-md-0.text-center > div > div > h5 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("li:nth-child(12) > div > div > div.ml-auto.text-right.d-flex > div.list-item-word > span.d-inline-block > span")).getText().isEmpty();
            //
            Thread.sleep(200);
            driver.findElement ( By.cssSelector("li:nth-child(12) > div > div > div.ml-auto.text-right.d-flex > div.list-item-word > span:nth-child(2)")).getText().isEmpty();
            driver.findElement(By.cssSelector("div.indxe-page-bottom-sec-pc > div.mt-md-0.text-center > div > div > a > span")).click();
        }
    }

    @Test(enabled = true,description = "转账",retryAnalyzer = MyIRetryAnalyzer.class)
    public void top_transfer() throws Exception{
        Boolean topacc_display = driver.findElement(By.cssSelector("div.row.indxe-page-bottom-sec-pc > div:nth-child(2) > div")).isDisplayed();
        if (topacc_display){
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div.card-header.bg-tron-light.d-flex > h5 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div.indxe-page-bottom-sec-pc > div:nth-child(2) > div > div.card-header.bg-tron-light.d-flex > a > span")).click();
        }
    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
