package tron.tronscan.tronscanPart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import tron.common.utils.WebBrowser;

public class Top_statisticsTest {
        private static String URL = "https://tronscan.org/#/data/bestdata";
        WebBrowser webBrowser = new WebBrowser();
        public static WebDriver driver;

        @BeforeMethod(enabled = true)
        public void start() throws Exception {
            try {
                driver = webBrowser.startChrome(URL);
            } catch (Exception e) {
            }
        }

    @Test(enabled = true,description = "最佳数据-概览")
    public void testTop() throws Exception{
      //  driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(1) > a")).click();
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(1) > a > span > span")).getText();
        //hours;day;week;
        driver.findElement(By.cssSelector("div.time-filter.d-flex.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.d-flex.justify-content-between > ul > li:nth-child(2) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.d-flex.justify-content-between > ul > li:nth-child(3) > span")).click();
        //Thread.sleep(200);
        //four modules
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[1]/div[1]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[1]/div[1]/div[1]/div[1]/span")).getText().isEmpty();

        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[1]/div[2]"));
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[1]/div[2]/div[1]/div[1]/span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[1]"));
        driver.findElement(By.cssSelector("div.data-overview-list > div:nth-child(2) > div:nth-child(1) > div.justify-content-between > div:nth-child(1) > span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[2]"));
        driver.findElement(By.cssSelector("div.data-overview-list > div:nth-child(2) > div:nth-child(2) > div.justify-content-between > div:nth-child(1) > span")).getText().isEmpty();

    }

    @Test(enabled = true,description = "最佳数据-账户")
    public void testTopAccount() throws Exception{
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(2) > a")).click();
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(2) > a > span > span")).getText();
        //hours;day;week;
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(3) > span")).click();
        //Thread.sleep(200);
        //six modules
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[1]"));
        driver.findElement(By.cssSelector("div.card-body.list-style-body__body > div > div.top-data > div > div:nth-child(1) > div > h2 > span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[2]"));
        driver.findElement(By.cssSelector("div.card-body.list-style-body__body > div > div.top-data > div > div:nth-child(2) > div > h2 > span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[3]"));
        driver.findElement(By.cssSelector("div.card-body.list-style-body__body > div > div.top-data > div > div:nth-child(3) > div > h2 > span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[4]"));
        driver.findElement(By.cssSelector("div.card-body.list-style-body__body > div > div.top-data > div > div:nth-child(4) > div > h2 > span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[5]"));
        driver.findElement(By.cssSelector("div.card-body.list-style-body__body > div > div.top-data > div > div:nth-child(5) > div > h2 > span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[6]"));
        driver.findElement(By.cssSelector("div.card-body.list-style-body__body > div > div.top-data > div > div:nth-child(6) > div > h2 > span")).getText().isEmpty();

    }
    @Test(enabled = true,description = "最佳数据-通证")
    public void testTopToken() throws Exception{
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(3) > a")).click();
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(3) > a > span > span")).getText();
        //wen an xian shi
        driver.findElement(By.cssSelector("div.card-body.list-style-body__body > div > div.top-data > div.data-area")).getText().isEmpty();
        //hours;day;week;
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(3) > span")).click();
        //Thread.sleep(200);
        //four modules
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[1]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[1]/div/h2/span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[2]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[2]/div/h2/span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[3]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[3]/div/h2/span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[4]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div[2]/div[4]/div/h2/span")).getText().isEmpty();

    }

    @Test(enabled = true,description = "最佳数据-合约")
    public void testTopContract() throws Exception{
        driver.findElement(By.cssSelector(" div.card-header.list-style-body__header > ul > li:nth-child(4) > a")).click();
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(4) > a > span > span")).getText();
        //hours;day;week;
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(3) > span")).click();
        //four modules

        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[1]"));
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[1]/div/h2/span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[2]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[2]/div/h2/span")).getText().isEmpty();
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[3]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/div/div[3]/div/h2/span")).getText().isEmpty();

    }

    @Test(enabled = true,description = "最佳数据-资源")
    public void testTopResource() throws Exception{
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(5) > a")).click();
        driver.findElement(By.cssSelector("div.card-header.list-style-body__header > ul > li:nth-child(5) > a > span > span")).getText();
        //hours;day;week;
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(1) > span")).click();
        driver.findElement(By.cssSelector("div.time-filter.justify-content-between > ul > li:nth-child(3) > span")).click();
        //four modules
        Thread.sleep(100);
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]"));
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div/div[2]/div/div[2]/h4[1]/span")).getText().isEmpty();

    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
