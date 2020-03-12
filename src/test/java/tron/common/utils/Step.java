package tron.common.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import tron.common.utils.WebBrowser;

public class Step {
    WebBrowser webBrowser = new WebBrowser();

    public static void login(WebDriver driver) throws Exception{
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
            driver.findElement(By.cssSelector(".dropdown-toggle > span:nth-child(1)")).click();
            driver.findElement(By.cssSelector(".justify-content-end > div")).click();
            Assert.assertEquals(driver.findElement(By.cssSelector("span > .d-flex div")).getText(), "TN2jfdYCX9vvozqjwVvPjMd7vRj8HKyxUe");
            Thread.sleep(200);
    }

}
