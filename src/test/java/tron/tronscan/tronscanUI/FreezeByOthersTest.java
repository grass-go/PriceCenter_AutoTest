package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.Step;
import tron.common.utils.WebBrowser;
import tron.common.utils.Configuration;

public class FreezeByOthersTest {
    private String tronScanNode = Configuration.getByPath("testng.conf")
    .getString("tronscanIP");
private  String URL = "https://"+tronScanNode+"/#/";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;
    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {

        }
    }

    @Test(enabled = false,description = "给他人冻结带宽",retryAnalyzer = MyIRetryAnalyzer.class)
    public void testFreezeByOthers() throws Exception{
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
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(4) > select > option:nth-child(1)")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-check-input")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".modal-body > p >button")).click();
        Thread.sleep(1000);
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted > span")).getText() , "Successfully frozen");
        driver.close();
    }

    @Test(enabled = false,retryAnalyzer = MyIRetryAnalyzer.class,description = "给他人冻结能量")
    public void testEnery() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".btn-primary:nth-child(1)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(2) .form-control")).sendKeys("TZEZWXYQS44388xBoMhQdpL1HrBZFLfDpt");
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).sendKeys("1");
        Thread.sleep(300);
        driver.findElement(By.cssSelector(".form-group:nth-child(4)")).click();
        Thread.sleep(300);
        driver.findElement(By.cssSelector(".form-group:nth-child(4) > select > option:nth-child(2)")).click();
        Thread.sleep(300);
        driver.findElement(By.cssSelector(".form-check-input")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".modal-body > p >button")).click();
        Thread.sleep(300);
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > div.text-muted > span")).getText() , "Successfully frozen");
    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
