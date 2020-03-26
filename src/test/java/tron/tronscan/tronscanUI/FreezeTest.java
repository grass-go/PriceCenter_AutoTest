package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Step;
import tron.common.utils.WebBrowser;

public class FreezeTest {
    private static String URL = "https://tronscan.org/#/";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test(enabled = true,description = "冻结带宽")
    public void testFreeze() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".btn-primary:nth-child(1)")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).sendKeys("1");
        Thread.sleep(300);
        driver.findElement(By.cssSelector(".form-group:nth-child(4)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(4) > select > option:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".form-check-input")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//p/button")).click();
        driver.findElement(By.cssSelector(".modal-body > p >button")).click();
        Thread.sleep(300);
//        Assert.assertTrue(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted > span")).getText()== "成功冻结"||driver.findElement(By.cssSelector(".sweet-alert > div.text-muted > span")).getText()== "Successfully frozen");
        driver.close();
    }

    @Test(enabled = true,description = "冻结能量")
    public void testEnery() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".btn-primary:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).sendKeys("1");
        Thread.sleep(300);
        driver.findElement(By.cssSelector(".form-group:nth-child(4)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(4) > select > option:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".form-check-input")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("//p/button")).click();
        driver.findElement(By.cssSelector(".modal-body > p >button")).click();
        Thread.sleep(300);
//        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted > span")).getText(), "成功冻结");
    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
