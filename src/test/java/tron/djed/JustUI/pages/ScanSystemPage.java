package tron.djed.JustUI.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import tron.chromeExtension.pages.AbstractPage;

public class ScanSystemPage extends AbstractPage {

  public ScanSystemPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[1]/div/div[2]/div/div/div[1]/canvas")
  public WebElement Overview_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[2]/div/div/div[2]/div/div/div[1]/canvas")
  public WebElement PriceFeeds_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[2]/div/div[1]")
  public WebElement cdpActions_text;

  @FindBy(css = "tr[class='ant-table-row ant-table-row-level-0']")
  public List<WebElement> cdpActionsList_text;

  public ScanSystemPage enterScanSystemPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(10);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(10);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    return new ScanSystemPage(driver);
  }
}
