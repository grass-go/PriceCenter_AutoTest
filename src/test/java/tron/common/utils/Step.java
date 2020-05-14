package tron.common.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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
            driver.findElement(By.cssSelector(".form-control:nth-child(2)")).sendKeys("a43d44d5781fb43ccb0551c449b8324a5e0999372765d20b9fa3ddca63e463a5");
            driver.findElement(By.cssSelector(".btn-danger:nth-child(3)")).click();
            Thread.sleep(200);
            driver.findElement(By.cssSelector(".dropdown-toggle > span:nth-child(1)")).click();
            driver.findElement(By.cssSelector(".justify-content-end > div")).click();
            Assert.assertEquals(driver.findElement(By.cssSelector("span > .d-flex div")).getText(), "TMEKAfwpJLGSFKbrDDveCQFE9KD95JqPHD");
            Thread.sleep(200);
    }

}
