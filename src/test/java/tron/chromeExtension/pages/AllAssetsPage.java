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

  /*  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div/input")*/
  @FindBy(className = "inputArea")
  public WebElement tokenSearch_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[1]/div/input")
  public WebElement tokenSearch1_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div[2]/div/div[2]/div/div/div[1]/div[2]/div[3]")
  // @FindBy(className = "address")
  public WebElement tokenSearchAddressResult;

 // @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/span")
    @FindBy(className = "cancel")
  public WebElement cancelSearch_btn;

  @FindBy(className = "back")
  public WebElement back_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div[2]/div/div/div/button/span")
  public WebElement getCustomToken_tips;

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

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]/span")
  public WebElement customToken_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[1]/div/input")
  public WebElement customTokenAddress_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[1]/div[2]")
  public WebElement tipError;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[2]/div[1]/div/input")
  public WebElement tokenShortName_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[2]/div[2]/div/input")
  public WebElement tokenFullName_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[2]/div[3]/div/input")
  public WebElement tokenPrecision;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div[2]/div[4]/div/input")
  public WebElement tokenType;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/button/span")
  public WebElement next_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/button[2]/span")
  public WebElement confirm_btn;

  // ...btn
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/div/div/div[2]/div[9]/div[2]/div/img[2]")
  public WebElement multiFunction_btn;

  // @FindBy(xpath = "/html/body/div[3]/div/div/div/div[2]/div/div/div[2]/span")
 /* @FindBy(xpath = "/html/body/div[2]/div/div/div/div[2]/div/div/div[2]/span")
  public WebElement delete_btn;*/

    @FindBy(className = "menuContent")
    public WebElement integration_btn;

  @FindBy(className = "assetListComponent")
  public WebElement allAssets_scroll;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[1]/span")
  public WebElement deletePromptTitle;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[2]/span")
  public WebElement deletePromptContent;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/button[1]/span")
  public WebElement deleteCancel_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/button[2]/span")
  public WebElement deleteConfirm_btn;
}
