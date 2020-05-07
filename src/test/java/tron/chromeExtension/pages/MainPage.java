package tron.chromeExtension.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/**
 * mine page
 */
public class MainPage extends AbstractPage {


    public MainPage(ChromeDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[2]/div/div[1]/div[2]/span[1]")
    public WebElement trxBalance;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[4]/div[2]/span[2]")
    public WebElement send_btn;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[2]/div/div[3]/div[1]/span")
    public WebElement btt_btn;

    public SendPage enterSendPage() throws Exception{
        try {
            TimeUnit.SECONDS.sleep(2);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            send_btn.click();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            TimeUnit.SECONDS.sleep(2);
            send_btn.click();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }
        return new SendPage(driver);
    }

}
