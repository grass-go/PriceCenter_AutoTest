package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import tron.common.utils.Step;
import tron.common.utils.WebBrowser;

public class LoginTest {
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
    }

    @AfterSuite(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }

}
