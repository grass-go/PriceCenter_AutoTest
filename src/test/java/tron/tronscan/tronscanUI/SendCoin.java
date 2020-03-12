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

public class SendCoin {
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
        driver.findElement(By.cssSelector(".dropdown-item:nth-child(10)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(1) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(1) .form-control")).sendKeys("TYvBUrZp7QboQzKhFVMYYkD4jDYsU33aQh");
        driver.findElement(By.cssSelector(".is-invalid")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).sendKeys("0.1");
        driver.findElement(By.cssSelector(".form-group:nth-child(4) .form-control")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//form/button")).click();
        driver.findElement(By.cssSelector(".btn-primary:nth-child(2)")).click();
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > h2")).getText(), "Successful Transaction");
    }

    @AfterSuite(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
