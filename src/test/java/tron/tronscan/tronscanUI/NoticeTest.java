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
    @Test(enabled = true,description = "广播栏",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testTop() throws Exception{
        //转义跳转-给出地址跳转的，后期修复
       driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(1) > span > span > a > span > span")).click();
        Thread.sleep(200);
         //
//       driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-0.item > span.title")).getText().isEmpty();
//       driver.findElement(By.cssSelector("#root > main > div.homeNotice > div > div > div > div.hidden-mobile > div > div > a.item-0.item > span.date")).getText().isEmpty();
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

    @Test(enabled = true,description = "数据栏",retryAnalyzer = MyIRetryAnalyzer.class)
    public void topDataTest() throws Exception {
        //转义跳转-给出地址跳转的，后期修复
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(1) > span > span > a > span > span")).click();
        Thread.sleep(200);
        //最新区块
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

        //展开
        Thread.sleep(200);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/div[2]/div[1]/span/span")).getText().isEmpty();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/div[2]/div[3]/span/span")).getText().isEmpty();
        Thread.sleep(200);
        //收起
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div[1]/div/div/span/span")).click();
        driver.close();

    }


    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
