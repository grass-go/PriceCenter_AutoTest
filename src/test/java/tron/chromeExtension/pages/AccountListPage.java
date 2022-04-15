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

  // 账户列表
  @FindBy(className = "cell")
  public List<WebElement> account_list;

  // 搜索账户按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[1]/img")
  public WebElement searchAccount_btn;
  // 搜索账户输入框
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[1]")
  public WebElement searchAccount_input;

  // 搜索结果账户的地址
  @FindBy(
      xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[2]/div/div[2]/span")
  public WebElement searchAccount_address;

  // 添加钱包
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[1]/div[3]/div/span")
  public WebElement addWallet_btn;

  // 关闭按钮
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[2]/div[1]/div/div[2]")
  public WebElement close_btn;

  // 热钱包列表
  @FindBy(id = "hotWallet")
  public List<WebElement> hotWalletArray;
}
