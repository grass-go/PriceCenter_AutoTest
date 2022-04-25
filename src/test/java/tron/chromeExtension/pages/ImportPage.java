package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/** mine page */
public class ImportPage extends AbstractPage {

  public ImportPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[4]/div[1]")
  public WebElement addWallet_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[4]/div[2]")
  public WebElement importWallet_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]")
  public WebElement scrollBar;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[3]")
  public WebElement agree_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[1]/span/textarea")
  public WebElement import_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]/span")
  public WebElement next_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]/input")
  public WebElement walletName_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]/div/span[1]/input")
  public WebElement setPassword_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[2]/div/span[2]/input")
  public WebElement setPasswordAgain_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[3]/span")
  public WebElement import_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[1]/div/button/span")
  public WebElement known_btn;
}
