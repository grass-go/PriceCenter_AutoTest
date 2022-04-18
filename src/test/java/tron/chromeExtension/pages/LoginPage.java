package tron.chromeExtension.pages;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/** mine page */
public class LoginPage extends AbstractPage {

  public LoginPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div/input")
  public WebElement password_input;

  @FindBy(id = "input")
  public WebElement password_input1;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button")
  public WebElement login_btn;

  @FindBy(name = "继续")
  public WebElement password_input2;
}
