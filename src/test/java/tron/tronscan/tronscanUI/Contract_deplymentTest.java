package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.io.IOException;

import tron.common.utils.Step;
import tron.common.utils.WebBrowser;

public class Contract_deplymentTest {
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

    @Test(enabled = true,description = "合约部署与合约验证")
    public void test() throws Exception{
        Step.login(driver);
        {
            WebElement element = driver.findElement(By.cssSelector(".dropdown-toggle > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        Thread.sleep(200);
        //点区块链
        driver.findElement(By.cssSelector("#navbar-top > ul > li:nth-child(2) > span > a > span")).click();
        Thread.sleep(300);
        //点合约部署
        driver.findElement(By.xpath("//ul/li[2]/div/div[2]/a[3]/span")).click();
        //本地上传
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[3]/div/div[2]/span/div/span/input")).sendKeys("/Users/tron/Documents/Tronscan_AutoTest/src/test/resources/right.sol");
        Thread.sleep(300);
        //点编译
        driver.findElement(By.cssSelector(".contract-compiler-button > button.compile-button:nth-child(1)")).click();
        //确认
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div[4]/button")).click();
        Thread.sleep(1000);
        //点部署
        driver.findElement(By.cssSelector(".compile-button.ml-5")).click();
        //确认
        driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div[7]/button")).click();
        Thread.sleep(60000);
        //点合约-开始验证
        WebElement address = driver.findElement(By.cssSelector(".contract-compiler-console > div:nth-child(8) > div > a"));
        String address_string = address.getText();
        //Successful deployed contract 'Ownhoog'. Cost: 0 TRX, 266,052 energy. Transaction confirm here
        WebElement name= driver.findElement ( By.cssSelector(".contract-compiler-console > div:nth-child(7) > div"));
        String name_string = name.getText();
        //截取
        String st1 =name_string.split ( "contract '" )[1];
        String name_number=st1.split ( "'." )[0];
        Thread.sleep(300);
        //合约验证
        //合约地址
        driver.findElement(By.cssSelector(".compile-button-box > div.compile-button.ml-3 > span")).click();
        driver.findElement(By.cssSelector("#contract_verify_contractAddress")).click();
        driver.findElement(By.cssSelector("#contract_verify_contractAddress")).sendKeys(address_string);
        Thread.sleep(300);

        //合约名称
        driver.findElement(By.cssSelector("#contract_verify_contractName")).click();
        driver.findElement(By.cssSelector("#contract_verify_contractName")).sendKeys(name_number);
        //license
        driver.findElement(By.cssSelector("#contract_verify_license > div > div > div > span")).click();
        driver.findElement(By.cssSelector(".ant-select-dropdown-menu> li:nth-child(5)")).click();
        Thread.sleep(300);
        //本地上传
        driver.findElement(By.xpath("//*[@id=\"root\"]/main/div/div/div[3]/div/div/form/div[2]/div[2]/span/div/span/input")).sendKeys("/Users/tron/workSpace/Tronscan_AutoTest/src/test/resources/long_text.sol");
        Thread.sleep(600);
        //验证签名
        driver.findElement(By.cssSelector("#g-recaptcha")).click();
        driver.findElement(By.cssSelector(".text-center > div.contract-compiler-button > button")).click();
        driver.quit();
    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
