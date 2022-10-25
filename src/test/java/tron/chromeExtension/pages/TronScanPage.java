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

  @FindBy(className = "input-group-arrow")
  public WebElement controlAccount_dropDown;

  @FindBy(className = "item")
  public WebElement controlAccount;

  @FindBy(
      xpath = "  /html/body/div[2]/div/div[1]/div/div/div[2]/form/div[2]/div/div[1]/div/span[2]")
  public WebElement permission_input;

  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div[2]/div[3]/form/div[5]/div/div/div")
  public WebElement permission_dropDown;

  @FindBy(className = "permission-item")
  public List<WebElement> permission_option;

  @FindBy(xpath = "/html/body/div[3]/div/div/div/div[2]/div[1]/div/div/div[2]/div")
  public WebElement ownerPermission_option;

  @FindBy(xpath = "/html/body/div[3]/div/div/div/div[2]/div[1]/div/div/div[4]/div")
  public WebElement activePermission_option;

  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div[2]/div[3]/form/div[2]/div/input")
  public WebElement receive_input;

  @FindBy(className = "rc-virtual-list-holder-inner")
  public List<WebElement> token_list;

  @FindBy(className = "suffix-icon")
  public WebElement tokenBox_dropDown;

  @FindBy(xpath = "//*[@id=\"rc_select_1\"]")
  public WebElement tokenBox_input;

  @FindBy(className = "rc-virtual-list-holder-inner")
  public WebElement token_searched;

  /* @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div[2]/form/div[7]/div/div/div")
  public WebElement collection_box;*/

  @FindBy(xpath = "//*[@id=\"rc_select_2\"]")
  public WebElement collection_box;

  /* @FindBy(xpath = "/html/body/div[5]/div/div/div/div[2]/div[1]/div/div/div[1]/div")
  public WebElement trc721Token_btn;*/
  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div[2]/div[3]/form/div[4]/div/div[2]/div/div/div/div[2]/div[1]/div/div/div[1]")
  public WebElement trc721Token_btn;

  /*  /html/body/div[5]/div/div/div/div[2]/div[1]/div/div/div[1]*/

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/main/section/aside/div/ul/li[4]/span")
  public WebElement multiSignTransactionTab_btn;

/*    @FindBy(xpath = "//*[@id=\"root\"]/div[2]/main/section/aside/div/ul/li[4]/span/a/span")
    public WebElement multipleTransaction_btn;*/


  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/section/main/div/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/label[2]/span[2]/span")
  public WebElement toBeSignedTab_btn;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/section/main/div/div/div[2]/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/div/table/tbody/tr[1]/td[6]/span/div/a[1]/span")
  public WebElement sign_btn;

  @FindBy(xpath = "/html/body/div[2]/div/div[2]/div/div[2]/div[3]/form/div[4]/div[2]/input")
  public WebElement tokenAccount_input;

  @FindBy(className = "send-btn")
  public WebElement transferConfirm_btn;

  @FindBy(className = "result-des")
  public WebElement signSuccess_tips;

  @FindBy(xpath = "/html/body/div[4]/div/div[2]/div/div[2]/div/div[2]/div[2]/span")
  public WebElement cancelSign_tips;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/section/main/div/div/div[2]/div/div[2]/div[2]/div[2]/h2/span")
  public WebElement multipleSignaturesSucceeded_tips;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/section/main/div/div/div[2]/div/div[2]/div[2]/div[2]/div[2]/div/div/div[1]")
  public WebElement multipleSignaturesFailed_tips;

  @FindBy(xpath = "//*[@id=\"root\"]/div[2]/div[1]/div[1]/div/div[2]/p/span/button/span")
  public WebElement confirm_btn;

  //
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/div/div/div[2]/div[2]/main/div[1]/div/div/div[1]/label[3]/span[2]/span")
  public WebElement prepareContract_btn;
  // Approve button.
  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/div/div/div[2]/div[2]/main/div[2]/div[2]/div/div[2]/main/div/form/div/div/div")
  public WebElement approve_btn;

  @FindBy(xpath = "//*[@id=\"contract_info_submitValues[0]\"]")
  public WebElement spenderAddress_input;

  @FindBy(xpath = "//*[@id=\"contract_info_submitValues[1]\"]")
  public WebElement value_input;

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div[2]/main/div/div/div[2]/div[2]/main/div[2]/div[2]/div/div[2]/main/div/form/div/div/div[2]/div/div/div[2]/div/div[1]")
  public WebElement send_btn;
}
