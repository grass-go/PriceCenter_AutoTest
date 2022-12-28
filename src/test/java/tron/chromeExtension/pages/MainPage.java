package tron.chromeExtension.pages;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import tron.chromeExtension.utils.Helper;

import static tron.chromeExtension.base.Base.*;

/** mine page */
public class MainPage extends AbstractPage {

  public MainPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }
  // Button for switching chains.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[1]")
  public WebElement selectedChain_btn;
  // Nile network.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[1]/div/div[4]")
  public WebElement nile;
  // Network list.
  @FindBy(className = "item")
  public List<WebElement> chainList;

  // Total assets of current account.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[3]")
  public WebElement accountTotalBalance;
  // Current account address.
  //  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[2]/span[1]")
  @FindBy(className = "bottom")
  public WebElement address_content;
  // Current account name.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[1]/span")
  public WebElement accountName_content;

  //
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/div[3]/div/div[1]/div[1]/div[2]")
  public WebElement transferAccount_content;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/div[3]/div/div[1]/div[3]/div[2]")
  public WebElement receiveAccount_content;

  // Current account TRX amount.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[1]/div[3]/span[1]")
  public WebElement trxBalance;

  // Current account amount of 20 token.
  @FindBy(
      xpath =
          " //*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[2]/div[3]/span[1]")
  public WebElement trc20Balance;
  // Current account amount of 10 token.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[2]/div[3]/span[1]")
  public WebElement trc10Balance;
  // Current account amount of 721 token.
  @FindBy(
      xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[3]/div[3]")
  public WebElement trc721Balance;

  @FindBy(className = "tokenItem")
  public List<WebElement> trc721Token_list;

  @FindBy(className = "tokenItem")
  public List<WebElement> token_list;

  // Toggle Collectibles tab button.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[1]/div[1]/div[2]/span")
  public WebElement collectibles_btn;

  // All assets button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[1]/div[2]/div[2]")
  public WebElement allAssets_btn;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[3]/div[2]/span")
  public WebElement allAssetsBottom_btn;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[2]/div/div[1]/div[1]/img[2]")
  public WebElement trxIconWithLetterV;

  // Switch assets.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[3]/div/div[1]/div[1]/div[1]/span")
  public WebElement assets_btn;
  // Switch account button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[1]/span")
  public WebElement switchAccount_btn;
  // Total assets of all accounts.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[1]/div/span[2]")
  public WebElement totalBalanceOfAllAccounts;

  // Transfer accounts.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[1]/div[1]")
  public WebElement transfer_btn;
  // Add Wallet.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[1]/a[1]")
  public WebElement addWallet_btn;

  // Dapp button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[1]/a[2]")
  public WebElement dApp_btn;

  // Top list page first element.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]")
  public WebElement dAppHot_content;
  // Used button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/span")
  public WebElement used_btn;
  // Return button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div")
  public WebElement back_btn;

  // Lock screen button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[2]/div[1]")
  public WebElement lock_btn;

  // Home receive button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[1]/div[2]/span")
  public WebElement receive_btn;

  // Home account address .
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[2]/span[1]")
  public WebElement address_btn;

  // Home page copy button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[2]/span[2]")
  public WebElement copy_btn;

  // Home page more buttons.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]")
  public WebElement more_btn;

  // Modify account name button.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[1]/span[2]")
  public WebElement modifyAccountName_btn;

  // Modify account name input box.
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement modifyAccountName_input;

  // Confirm to modify account name.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[3]/button[2]/span")
  public WebElement modifyAccountNameConfirm_btn;

  // Export Account button.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[2]/span[2]")
  public WebElement exportAccount_btn;
  // Link management button
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[3]/span[2]")
  public WebElement manageConnections_btn;

  // Link management page copywriting.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div/div[2]/span")
  public WebElement manageConnections_content;

  // Connection management page close button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div/div[1]")
  public WebElement manageConnectionsClose_btn;

  // Refresh button.

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[2]/div[3]")
  public WebElement renew_btn;
  // Set button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[2]/div[5]")
  public WebElement set_btn;

  // I have the secure backup button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/button/span")
  public WebElement backedUp_btn;

  // Home send button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[4]/div[2]/span[2]")
  public WebElement send_btn;

  // input.
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement password_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button/span")
  public WebElement login_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[2]/div/div[3]/div[1]/span")
  public WebElement btt_btn;

  // pledge btn
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[1]/div[4]/span")
  public WebElement pledge_btn;

  // exchange btn
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[1]/div[5]/span")
  public WebElement exchange_btn;

  // vote btn
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[3]/div[1]/div[3]")
  public WebElement vote_btn;
  // signature btn
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[6]/button[2]")
  public WebElement signature_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[5]/div/div/div[2]/div[2]")
  public WebElement resourceReception_btn;

  // cancel signature btn
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[6]/button[1]")
  public WebElement cancelSignature_btn;

  // click jump
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[2]/div[2]/div")
  public WebElement clickJump_btn;



    @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/div[2]/div[1]/a[1]")
    public WebElement plus;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/ul/li[2]/div[2]/p[1]")
  public WebElement importWallet;


  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[1]/span/textarea")
  public WebElement ZhuJiCi_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]/span")
  public  WebElement nextStep_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[3]/span")
  public  WebElement importSiYao;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]/span")
  public  WebElement importConfirm;


  public SendPage enterSendPage() throws Exception {
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

  public LockPage enterLockPage() throws Exception {
    try {
      TimeUnit.SECONDS.sleep(5);
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
      lock_btn.click();
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    } catch (Exception e) {

    }
    return new LockPage(driver);
  }
}
