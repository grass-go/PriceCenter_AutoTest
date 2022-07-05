package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import tron.chromeExtension.base.Base;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.util.concurrent.TimeUnit;

import static tron.chromeExtension.base.Base.*;

/** mine page */
public class AuthorizationPage extends AbstractPage {

  public AuthorizationPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(className = "afterIconUnknown")
  public WebElement white_btn;

  @FindBy(className = "primary")
  public WebElement signatureAuthorization_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[7]/div/label/span[2]/span")
  public WebElement authorizationCopyWriting_text;
}
