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

public class FreezeByOthersTest {
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
        driver.findElement(By.cssSelector(".btn-primary:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control")).sendKeys("TZEZWXYQS44388xBoMhQdpL1HrBZFLfDpt");
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
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted > span")).getText(), "成功冻结");
        driver.close();
    }

    @Test(enabled = true)
    public void testEnery() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".btn-primary:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control")).sendKeys("TZEZWXYQS44388xBoMhQdpL1HrBZFLfDpt");
        Thread.sleep(200);
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
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted > span")).getText(), "成功冻结");
        driver.close();
    }
    @AfterSuite(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
