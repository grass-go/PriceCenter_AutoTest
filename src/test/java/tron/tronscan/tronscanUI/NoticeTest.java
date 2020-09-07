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
    @Test(enabled = true,description = "广播栏",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testTop() throws Exception{
       //广播三个广告
       driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-0.item > span.title")).getText().isEmpty();
       driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-0.item > span.date")).getText().isEmpty();
       driver.findElement(By.cssSelector("#root > main > div.homeNotice> div > div > div > div.hidden-mobile > div > div > a.item-1.item > span.title")).getText().isEmpty();
       driver.findElement(By.cssSelector("#root > main > div.homeNotice> div > div > div > div.hidden-mobile > div > div > a.item-1.item > span.date")).getText().isEmpty();
        //
       driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-2.item > span.title")).getText().isEmpty();
       driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-2.item > span.date")).getText().isEmpty();
       //more
       driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > a > span")).click();
       driver.close();
   }

    @Test(enabled =true,description = "数据栏",retryAnalyzer = MyIRetryAnalyzer.class)
    public void topDataTest() throws Exception {
        //最新区块
//        Boolean tot_block = driver.findElement(By.cssSelector("#root > main > div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center")).isDisplayed();
//        if (tot_block) {
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(1) > a > h2 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center> div > div > div:nth-child(1) > a > p > span")).getText().isEmpty();
            Thread.sleep(200);
            //交易总数
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(2) > h2 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector(" div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(2) > p > span")).getText().isEmpty();
            Thread.sleep(200);
            //账户总数
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(3) > h2 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center> div > div > div:nth-child(3) > p > span")).getText().isEmpty();
            //driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.text-center.mr-0 > div > div > div:nth-child(4) > div > h2 >span:nth-child(2) > span")).getText().isEmpty();
            Thread.sleep(200);
            //TRX冻结量
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center> div > div > div:nth-child(6) > a > h2 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(6) > a > p > span")).getText().isEmpty();
            Thread.sleep(200);
            //合约总数
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(7) > h2 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(7) > p > span")).getText().isEmpty();
            //通证总数
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(8) > a > h2 > span")).getText().isEmpty();
            driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div:nth-child(1) > div > div > div.text-center > div > div > div:nth-child(8) > a > p > span")).getText().isEmpty();
//        }
        //展开
        Thread.sleep(200);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/div[2]/div[1]/span/span")).getText().isEmpty();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/div[2]/div[3]/span/span")).getText().isEmpty();
        Thread.sleep(300);
        //收起
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/span/span")).click();
        driver.close();

    }

    @Test(enabled = true,description = "首页市值数据",retryAnalyzer = MyIRetryAnalyzer.class)
     public void trxChart() throws Exception{
       //TRX价格显示
        driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-left > div.trxgroup-left-header > div.trxgroup-logo > div > h4")).getText().isEmpty();
        driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-left > div.trxgroup-left-header > div.trxgroup-logo > div > p")).getText().isEmpty();
       //价格变化
        driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-left > div.trxgroup-left-header > div.trxgroup-time > p.trxgroup-wave.up")).getText().isEmpty();
        //市值
        driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-left > div.trxgroup-left-content > p:nth-child(1) > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-left > div.trxgroup-left-content > p:nth-child(2) > span")).getText().isEmpty();
        //24H交易量
        driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-left > div.trxgroup-left-content > p:nth-child(3) > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-left > div.trxgroup-left-content > p:nth-child(4) > span")).getText().isEmpty();
        driver.close();
     }

     @Test(enabled = true,description = "每日交易数14天",retryAnalyzer = MyIRetryAnalyzer.class)
     public void test14day_transactions()throws Exception{
        //表
         Thread.sleep(300);
         driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-right >div:nth-child(2) >div > svg > rect.highcharts-background")).getText().isEmpty();
         //标题：每日交易数
         driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-right >div:nth-child(2) >div > svg > text > tspan")).getText().isEmpty();
         //全网，主网，sun-network
         driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-right >div:nth-child(2) >div > svg > g.highcharts-legend > g > g > g.highcharts-series-0 > text > tspan")).getText().isEmpty();
         driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-right >div:nth-child(2) >div > svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();
         driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-right >div:nth-child(2) >div > svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();
         //查看更多
         driver.findElement(By.cssSelector("div:nth-child(2) > div > div > div.trxgroup-right >div:nth-child(2) >div > span > a")).click();
         Thread.sleep(200);
         driver.close();
     }
     @Test(enabled = true,description = "首页区块数据",retryAnalyzer = MyIRetryAnalyzer.class)
     public void testBlockTest()throws Exception{
        //标题：区块
         driver.findElement(By.cssSelector("div:nth-child(1) > div.home-blocks > div > div.card-header.blocks-header > div > span")).getText().isEmpty();
         //区块收益
        driver.findElement(By.cssSelector("div.scrollbar-container> ul > li:nth-child(2) > div > div > div.text-left > div.text-gray-dark > span.d-inline-block > span")).getText().isEmpty();
        //
         driver.findElement(By.xpath("//div[1]/div[1]/div/div[2]/ul/li[8]/div/div/div[2]/div[1]/span[1]/span")).getText().isEmpty();
         //点更多
         driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[3]/div/div[1]/div[1]/div/div[1]/a/span")).click();
         driver.close();
     }

     @Test(enabled = true,description = "首页交易",retryAnalyzer = MyIRetryAnalyzer.class)
     public void testTransferTest()throws Exception{
        //标题：交易
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[3]/div/div[1]/div[2]/div/div[1]/div/span")).getText().isEmpty();
        //点更多
         driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[3]/div/div[1]/div[2]/div/div[1]/a/span")).getText().isEmpty();
         driver.close();
     }

    @Test(enabled = true,description = "首页-账户增长表",retryAnalyzer = MyIRetryAnalyzer.class)
     public void testAccountChart()throws Exception{
        //表
         driver.findElement(By.cssSelector("div > div:nth-child(2) > div:nth-child(1) > div > div > div > div > div> div>div>svg > rect.highcharts-background")).getText().isEmpty();
         //标题
        driver.findElement(By.cssSelector("div:nth-child(2) > div:nth-child(1) > div > div > div > div > div> div>div>svg > text.highcharts-title > tspan")).getText().isEmpty();
        //数据标签
        driver.findElement(By.cssSelector("div > div:nth-child(2) > div:nth-child(1) > div > div > div > div > div> div>div> svg > g.highcharts-legend > g > g > g.highcharts-series-0 > text > tspan")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > div:nth-child(2) > div:nth-child(1) > div > div > div > div > div> div>div> svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > div:nth-child(2) > div:nth-child(1) > div > div > div > div > div> div>div> svg > g.highcharts-legend > g > g > g.highcharts-series-2 > text > tspan")).getText().isEmpty();

        driver.close();
     }

    @Test(enabled = true,description = "首页-合约调用表",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testContractChart()throws Exception{
        //表
        driver.findElement(By.cssSelector("div:nth-child(2) > div.mt-md-0.pl-0 > div > div > div > div > div>div>div > svg > rect.highcharts-background")).getText().isEmpty();
        //标题
        driver.findElement(By.cssSelector("div:nth-child(2) > div.mt-md-0.pl-0 > div > div > div > div > div>div>div > svg >text.highcharts-title > tspan")).getText().isEmpty();
        //数据标签
        driver.findElement(By.cssSelector("div:nth-child(2) > div.mt-md-0.pl-0 > div > div > div > div > div>div>div > svg > g.highcharts-legend > g > g > g.highcharts-series-0 > text > tspan")).getText().isEmpty();
        driver.findElement(By.cssSelector("div:nth-child(2) > div.mt-md-0.pl-0 > div > div > div > div > div>div>div > svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();

        driver.close();
    }

    @Test(enabled = true,description = "首页-每日资源消耗表14天",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testResourceChart()throws Exception{
        //表
        driver.findElement(By.cssSelector("div:nth-child(3) > div:nth-child(1) > div > div > div > div > div>div>div> svg > rect.highcharts-background")).getText().isEmpty();
        //标题
        driver.findElement(By.cssSelector("div:nth-child(3) > div:nth-child(1) > div > div > div > div > div>div>div> svg > text.highcharts-title > tspan")).getText().isEmpty();
        //数据标签
        driver.findElement(By.cssSelector("div:nth-child(3) > div:nth-child(1) > div > div > div > div > div>div>div > svg > g.highcharts-legend > g > g > g.highcharts-series-0 > text > tspan")).getText().isEmpty();
        driver.findElement(By.cssSelector("div:nth-child(3) > div:nth-child(1) > div > div > div > div > div>div>div > svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();

        driver.close();
    }

    @Test(enabled = true,description = "首页-每日资源消耗表14天",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testTRXFrozenChart()throws Exception{
        //表
        driver.findElement(By.cssSelector("div:nth-child(3) > div.pl-0 > div > div > div > div > div> div > div > svg > rect.highcharts-background")).getText().isEmpty();
        //标题
        driver.findElement(By.cssSelector("div:nth-child(3) > div.pl-0 > div > div > div > div > div> div > div >  svg > text.highcharts-title > tspan")).getText().isEmpty();
        //数据标签
        driver.findElement(By.cssSelector("div:nth-child(3) > div.pl-0 > div > div > div > div > div> div > div >  svg > g.highcharts-legend > g > g > g.highcharts-series-0 > text > tspan")).getText().isEmpty();
        driver.findElement(By.cssSelector("div:nth-child(3) > div.pl-0 > div > div > div > div > div> div > div >  svg > g.highcharts-legend > g > g > g.highcharts-series-1 > text > tspan")).getText().isEmpty();

        driver.close();
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
