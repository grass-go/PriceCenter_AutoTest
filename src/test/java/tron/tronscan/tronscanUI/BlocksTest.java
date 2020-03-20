package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import tron.common.utils.WebBrowser;

public class BlocksTest {private static String URL = "https://tronscan.org/#/blockchain/blocks";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeSuite(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(enabled = true)
    public void testSRText() throws Exception{
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[1]/div/text()")).getText();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[2]/div/div/div/div/div/div/div/table/tbody/tr[1]/td[1]/a")).click();
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[1]/div/div/h3")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/div/div/h3")).getText().isEmpty());
    }

    @AfterSuite(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}