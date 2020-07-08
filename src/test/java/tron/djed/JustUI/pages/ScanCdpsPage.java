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

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/header/div/div[3]/div[1]/div/div/div")
  public WebElement login_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/header/div/div[3]/div[1]/div/div/div")
  public WebElement login;

  @FindBy(css = "li[class='ant-select-dropdown-menu-item tronlinkHeadRight']")
  public WebElement loginWithTronlink;

  @FindBy(css = "li[class='ant-select-dropdown-menu-item tronlinkHeadRight ant-select-dropdown-menu-item-active']")
  public WebElement loginWithTronlink_btn;

  @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[2]/div/div[2]/button")
  public WebElement login_tronlink_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[1]/section/header/div/div[3]/a[2]")
  public WebElement login_address;

  @FindBy(xpath = "/html/body/div[2]/div/div/ul/li[2]/div")
  public WebElement exit_btn;

  @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[2]/div/div[2]/a")
  public WebElement tronlink_link;

  @FindBy(css = "li[class='ant-select-dropdown-menu-item ledgerHeadRight']")
  public WebElement loginWithLedger;

  @FindBy(css = "li[class='ant-select-dropdown-menu-item ledgerHeadRight ant-select-dropdown-menu-item-active']")
  public WebElement loginWithLedger_btn;

  @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div[2]/div/div[2]/div/div/div[1]")
  public WebElement loginWithLedger_text;


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
