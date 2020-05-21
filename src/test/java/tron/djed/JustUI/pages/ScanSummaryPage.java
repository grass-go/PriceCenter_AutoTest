package tron.djed.JustUI.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import tron.chromeExtension.pages.AbstractPage;

/**
 * mine page
 */
public class ScanSummaryPage extends AbstractPage {


  public ScanSummaryPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[1]/div/div/div[1]/div[3]/div[2]")
  public WebElement trxCollateralization_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[2]/div/div/div[1]/div[3]/div[2]")
  public WebElement usdjSupply_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[3]/div/div/div[1]/div[3]/div[2]")
  public WebElement jstPrice_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/section/main/div/div[2]/div/div[1]/div[4]/div/div/div[1]/div[3]")
  public WebElement cdpInteractions_text;

  @FindBy(css = "tr[class='ant-table-row ant-table-row-level-0']")
  public List<WebElement> recentCdpList_text;

  @FindBy(css = "button[class='ant-btn ant-btn-primary']")
  public WebElement viewAll_btn;

  public ScanSummaryPage enterScanSummaryPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(13);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(13);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    return new ScanSummaryPage(driver);
  }
}
