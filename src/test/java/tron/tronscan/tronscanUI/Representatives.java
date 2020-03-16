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

public class Representatives {
    private static String URL = "https://tronscan.org/#/sr/representatives";
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
        Assert.assertTrue(!driver.findElement(By.cssSelector(".text-primary > span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div/div[1]/div[2]/div/div/h3/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div/div[1]/div[3]/div/div/h3/span")).getText().isEmpty());
    }

    @AfterSuite(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
