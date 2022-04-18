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
  // Password input box.
  @FindBy(xpath = "//*[@id=\"input\"]")
  public WebElement password_input;
  // Confirmation box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button/span")
  public WebElement login_btn;
  // Error prompt 1.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/span[1]")
  public WebElement tips1;
  // Error prompt 2.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/span[2]")
  public WebElement tips2;
}
