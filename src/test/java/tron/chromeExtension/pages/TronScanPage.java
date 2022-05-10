package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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
  // Vote button.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/div[3]/div/div/div/div[1]/div/div[2]/div/div[2]/button/span")
  public WebElement beforeEnteringVotes_btn;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/div[3]/div/div/div/div[1]/div/div[2]/div/div[2]/button[3]/span")
  public WebElement votesEntered_btn;

  // Vote input.
  @FindBy(
      xpath =
          "/html/body/div[1]/div[2]/main/div[3]/div/div/div/div[2]/div/div/div/div/div/div/table/tbody/tr[1]/td[8]/div/div/input")
  public WebElement vote_input;
  // Confirm vote button.
  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/main/div[1]/div[2]/p/span/button")
  public WebElement confirmVote_btn;

  // Vote success or failed tips.
  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/main/div[1]/div[2]/div/h3/span")
  public WebElement voteSuccessOrFailed_tips;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/div[1]/div[1]/div/div/div/section/div[2]/div/ul/div[1]/li/a")
  public WebElement accountAddress_btn;

  @FindBy(
      xpath =
          "/html/body/div[1]/div[2]/div[1]/div[1]/div/div/div/section/div[2]/div/ul/div[1]/li/ul/a[5]")
  public WebElement multiSignature_btn;

  @FindBy(
      xpath = "  /html/body/div[2]/div/div[1]/div/div/div[2]/form/div[2]/div/div[1]/div/span[2]")
  public WebElement permission_input;

  @FindBy(xpath = "/html/body/div[3]/div/div/div/div[2]/div[1]/div/div/div[2]/div")
  public WebElement ownerPermission_option;

  @FindBy(xpath = "/html/body/div[3]/div/div/div/div[2]/div[1]/div/div/div[4]/div")
  public WebElement activePermission_option;

  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[5]/div/input")
  public WebElement receive_input;

  @FindBy(className = "ant-select-item")
  public List<WebElement> token_list;

  @FindBy(
      xpath =
          "/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[6]/div/div/div/span[2]/div/div[1]")
  public WebElement token_box;

  /* @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[7]/div/div/div")
  public WebElement collection_box;*/

  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[7]/div/div/div/span[1]")
  public WebElement collection_box;

 /* @FindBy(xpath = "/html/body/div[5]/div/div/div/div[2]/div[1]/div/div/div[1]/div")
  public WebElement trc721Token_btn;*/
 @FindBy(xpath = " /html/body/div[5]/div/div/div/div[2]/div[1]/div/div/div[1]")
 public WebElement trc721Token_btn;

/*  /html/body/div[5]/div/div/div/div[2]/div[1]/div/div/div[1]*/




  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[7]/div/input")
  public WebElement tokenAccount_input;

  @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/form/button/span")
  public WebElement transferConfirm_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[1]/div[1]/div/div[2]/h2/span")
  public WebElement signSuccess_tips;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[1]/div[1]/div/div[2]/div[2]/div/div/div[1]")
  public WebElement cancelSign_tips;
}
