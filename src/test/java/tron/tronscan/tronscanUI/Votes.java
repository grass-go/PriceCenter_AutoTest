package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

public class Votes {
    private static String URL = "https://tronscan.org/#/sr/votes";
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

    @Test(enabled = true,retryAnalyzer = MyIRetryAnalyzer.class)
    public void testVoteText() throws Exception{
        System.out.println("LOGIN");
        driver.manage().window().setSize(new Dimension(2560, 1440));
        Thread.sleep(200);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".px-2:nth-child(3) span")).click();
        driver.findElement(By.cssSelector(".form-control:nth-child(2)")).click();
        driver.findElement(By.cssSelector(".form-control:nth-child(2)")).sendKeys("de164ddc64b1783c3ebced73b32c479b3daa203cf323bd738a9ecfc4674d6017");
        driver.findElement(By.cssSelector(".btn-danger:nth-child(3)")).click();
        Thread.sleep(200);
//        Assert.assertTrue(!driver.findElement(By.cssSelector("//*[@id=\"root\"]/main/div[1]/div[3]/div/div/div/div[2]/span/div/div/a/div")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[1]/div/div/h3/span")).getText().isEmpty());
        Assert.assertTrue(!driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/div/div/h3/span")).getText().isEmpty());
    }

    @Test(enabled = false)
    public void testVoteAction() throws Exception{
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div/div[1]/div[2]/div/a/span")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div/div[2]/table/tbody/tr[1]/td[8]/div/div[2]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[2]/div/div/div/div[1]/div[2]/div/div[3]/button[3]")).click();
        Assert.assertEquals(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/h2/span"),"感谢您的投票!");
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div[1]/div[2]/p/span/button")).click();

    }

    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
