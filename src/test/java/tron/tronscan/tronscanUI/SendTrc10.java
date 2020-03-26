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

public class SendTrc10 {
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

    @Test(enabled = true)
    public void testTrc10() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".dropdown-item:nth-child(10)")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(1) .form-control")).click();
        driver.findElement(By.cssSelector(".form-group:nth-child(1) .form-control")).sendKeys("TYvBUrZp7QboQzKhFVMYYkD4jDYsU33aQh");
        //选择TRC10
        Thread.sleep(200);
        driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[2]/div/div/div/div")).click();
        Thread.sleep(200);
        driver.findElement(By.xpath("/html/body/div[3]/div/div"));
        driver.findElement(By.xpath("/html/body/div[3]/div/div/div/ul/li[1]/ul/li[2]")).click();

        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(3) .form-control")).sendKeys("0.01");
        driver.findElement(By.cssSelector(".form-group:nth-child(4) .form-control")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".form-group:nth-child(4) .form-control")).sendKeys("TRC 10");
        Thread.sleep(300);
        driver.findElement(By.xpath("//form/button")).click();
        Thread.sleep(200);
        driver.findElement(By.cssSelector(".btn-primary:nth-child(2)")).click();
        Thread.sleep(300);
        Assert.assertEquals(driver.findElement(By.cssSelector(".sweet-alert > h2")).getText(), "Successful Transaction");
    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}