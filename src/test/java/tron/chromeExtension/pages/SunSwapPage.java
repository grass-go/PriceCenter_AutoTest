package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

/** mine page */
public class SunSwapPage extends AbstractPage {

  public SunSwapPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }
  // .
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div/div[2]/span")
  public WebElement connectAccount_btn;
  // Amount input.
  @FindBy(xpath = "//*[@id=\"swap-tab-panel-2\"]/section/div[1]/div[1]/div[2]/input")
  public WebElement amount_input;
  // Check the confirmation box.
  @FindBy(xpath = "//*[@id=\"swap-tab-panel-2\"]/section/div[4]/div[2]/p")
  public WebElement dropDown_btn;
  // Pledge button.
  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div[2]/div[2]/input")
  public WebElement search_input;
  // Pledge success tips.
  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/main/section/main/div/div[1]/div[2]/div[2]/div[2]/span")
  public WebElement search_btn;
  // Address input.
  @FindBy(className = "item-content")
  public List<WebElement> searchedTokenList;

  @FindBy(className = "action-btns")
  public WebElement exchange_btn;

  @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div/button")
    public WebElement confirmExchange_btn;

    @FindBy(xpath = "/html/body/div[3]/div/div[2]/div/div[2]/div/div[6]")
    public WebElement errorMsg;




}
