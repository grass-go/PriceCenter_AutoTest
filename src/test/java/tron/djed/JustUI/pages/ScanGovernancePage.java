package tron.djed.JustUI.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import tron.chromeExtension.pages.AbstractPage;

public class ScanGovernancePage extends AbstractPage {

  public ScanGovernancePage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div/div/div[2]/div/div/div[1]/canvas")
  public WebElement stabilityFee_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div/div/table/tbody/tr[1]/td[1]/a")
  public WebElement txHash_link;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/header/div/div[3]/a")
  public WebElement helpCenter_link;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/footer/div/ul/li[4]/a")
  public WebElement contactUs_link;

  public ScanGovernancePage enterScanGovernancePage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(20);
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(20);
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    return new ScanGovernancePage(driver);
  }
}
