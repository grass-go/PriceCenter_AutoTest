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

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[2]/div/div/div/div/div/div/div/div/div/table/tbody/tr/td[1]/a")
  public WebElement txHash_link;

  public ScanGovernancePage enterScanGovernancePage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(10);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(10);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    return new ScanGovernancePage(driver);
  }
}
