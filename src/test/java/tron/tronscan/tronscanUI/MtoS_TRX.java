package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import tron.common.utils.Step;
import tron.common.utils.WebBrowser;

public class MtoS_TRX {
    private static String URL = "https://tronscan.org/#/";
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
    public void test() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }

        driver.findElement(By.cssSelector(".account-token-tab> a.btn-default:nth-child(3)")).click();
        driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(5) > button")).click();
        driver.findElement(By.xpath(".//form/div[3]/input")).click();
        driver.findElement(By.xpath("//form/div[3]/input")).sendKeys("10");
        Thread.sleep(200);
        driver.findElement(By.xpath("//form/button")).click();
        Thread.sleep(300);
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > h2 > span")).getText(), "转入成功");
        driver.close();
    }

    @AfterSuite(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
