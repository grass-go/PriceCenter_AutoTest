package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/** mine page */
public class LockPage extends AbstractPage {

  public LockPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }
  // 密码输入框
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement password_input;
  // 确认框
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button/span")
  public WebElement login_btn;
  // 错误提示1
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/span[1]")
  public WebElement tips1;
  // 错误提示2
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/span[2]")
  public WebElement tips2;
}
