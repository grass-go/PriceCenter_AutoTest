package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** mine page */
public class AllAssetsPage extends AbstractPage {

  public AllAssetsPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/input")
  public WebElement receiverAddress_input;

  // All assets title.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/span")
  public WebElement allAssets_title;

  @FindBy(className = "tokenItem")
  public List<WebElement> tokenList;


  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/span[1]")
  public WebElement specificAsset_title;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/span[2]/span")
  public WebElement details_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div[4]/div[2]/a")
  public WebElement contract_address;



}
