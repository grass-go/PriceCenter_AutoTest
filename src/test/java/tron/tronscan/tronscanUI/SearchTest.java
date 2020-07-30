package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class SearchTest {
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

    @Test(enabled = false)
    public void testSearchAddressText() throws Exception{
        //转义跳转-给出地址跳转的，后期修复
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(1) > span > span > a > span > span")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div/div/div/div/div/input")).click();
//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div/div/div/div/div/input")).sendKeys("TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
//        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.cssSelector("#_searchBox > a > div > span > strong")).getText(),"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
//        Thread.sleep(300);
//        driver.findElement(By.xpath("//*[@id=\"_searchBox\"]/a/div/span/strong")).click();
//        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[1]/div[1]/span/div/div/span/div/a/div")).getText(),"TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");

    }

    @Test(enabled = false)
    public void testSearchContractText() throws Exception{
        //转义跳转-给出地址跳转的，后期修复
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(1) > span > span > a > span > span")).click();
        Thread.sleep(200);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div/div/div/div/div/input")).sendKeys("TVRqMwBZU13c4wx6Hi6jqroCnYv2nrqQU1");
        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"_searchBox\"]/a/div/span/strong")).getText(),"TVRqMwBZU13c4wx6Hi6jqroCnYv2nrqQU1");
//        driver.findElement(By.xpath("//*[@id=\"_searchBox\"]/a")).click();
//        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[1]/div/h6/span/div/div/span/div/a/div")).getText(),"TVRqMwBZU13c4wx6Hi6jqroCnYv2nrqQU1");

    }

    @Test(enabled = false)
    public void testSearchTrc10Text() throws Exception{
        //转义跳转-给出地址跳转的，后期修复
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(1) > span > span > a > span > span")).click();
        Thread.sleep(200);

        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div/div/div/div/div/input")).sendKeys("BTT");
//        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"_searchBox\"]/a/div/span/strong")).getText(),"BitTorrent(BTT) 1002000");
//        driver.findElement(By.xpath("//*[@id=\"_searchBox\"]/a")).click();
//        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[1]/div[2]/div[2]/div[2]/div/span")).getText(),"1002000");

    }

    @Test(enabled = false)
    public void testSearchTrc20Text() throws Exception{
        //转义跳转-给出地址跳转的，后期修复
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(1) > span > span > a > span > span")).click();
        Thread.sleep(200);

        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div[2]/div/div/div/div/div/input")).sendKeys("USDT");
        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"_searchBox\"]/a/div/span/strong")).getText(),"Tether USD(USDT) TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
//        driver.findElement(By.xpath("//*[@id=\"_searchBox\"]/a")).click();
//        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[1]/div[2]/div[2]/div[2]/div/span/span/div/div/span/div/a/div")).getText(),"TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");

    }

        @AfterMethod(enabled = true)
        public void end() throws Exception {
            WebBrowser.tearDownBrowser();
        }
    }

