package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/** mine page */
public class TronScanPage extends AbstractPage {

  public TronScanPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }
  // Get sources button.
  @FindBy(xpath = "//*[@id=\"account_title\"]/div/div[2]/div/div/div[1]/section[2]/span/span")
  public WebElement getSources_btn;
  // Amount input.
  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/div[3]/div[2]/input")
  public WebElement amount_input;
  // Check the confirmation box.
  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/div[5]/label/span[1]/input")
  public WebElement confirmation_box;
  // Pledge button.
  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/p/button")
  public WebElement pledge_btn;
  // Pledge success tips.
  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/main/section/main/div/div[1]/div[2]/div[2]/div[2]/span")
  public WebElement pledgeSuccess_tips;
  // Address input.
  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/div[4]/input")
  public WebElement address_input;
  //  Drop-down box .
  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/div[2]/div[2]/div/span[2]")
  public WebElement dropDown_box;
  //  Check get votes and energy .
  @FindBy(xpath = "/html/body/div[3]/div/div/div/div[2]/div[1]/div/div/div[2]/div")
  public WebElement getVotesAndEnergy_option;
}
