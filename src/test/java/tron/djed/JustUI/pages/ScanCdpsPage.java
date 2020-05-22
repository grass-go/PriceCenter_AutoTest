package tron.djed.JustUI.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import tron.chromeExtension.pages.AbstractPage;

public class ScanCdpsPage extends AbstractPage {

  public ScanCdpsPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(css = "tr[class='ant-table-row ant-table-row-level-0']")
  public List<WebElement> cdpsList_text;

  @FindBy(css = "input[class='ant-input']")
  public WebElement serachCdp_input;

  @FindBy(css = "button[class='ant-btn ant-input-search-button ant-btn-primary']")
  public WebElement searchCdp_btn;

  @FindBy(css = "div[class='ant-card-head-title']")
  public WebElement cdpId_text;

  @FindBy(css = "tr[class='ant-table-row ant-table-row-level-0']")
  public List<WebElement> cdpActionHistoryList_text;

  public ScanCdpsPage enterScanCdpsPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(13);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {
      TimeUnit.SECONDS.sleep(13);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    return new ScanCdpsPage(driver);
  }
}
