package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** mine page */
public class AccountListPage extends AbstractPage {

  public AccountListPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  // Account list.
  @FindBy(className = "cell")
  public List<WebElement> account_list;

  // Search Account button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[1]/img")
  public WebElement searchAccount_btn;
  // Search Account input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[1]/span/input")
  public WebElement searchAccount_input;

  // Address of search result account.
  @FindBy(
      xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[2]/div/div[2]/span")
  public WebElement searchAccount_address;

  // Add Wallet.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[3]/div/span")
  public WebElement addWallet_btn;

  // Close button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[2]")
  public WebElement close_btn;

  // Hot wallet list.
  @FindBy(id = "hotWallet")
  public List<WebElement> hotWalletArray;
}
