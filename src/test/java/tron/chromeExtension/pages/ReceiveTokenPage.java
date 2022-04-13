package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/**
 * mine page
 */
public class ReceiveTokenPage extends AbstractPage {


    public ReceiveTokenPage(ChromeDriver driver) {
        super(driver);
        this.driver = driver;
    }

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div/p[2]")
  public WebElement accountName;


    @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div/div[2]/span")
    public WebElement accountAddress;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div/div[1]/canvas")
    public WebElement qRCode;
}
