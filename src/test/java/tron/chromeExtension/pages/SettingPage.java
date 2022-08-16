package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** mine page */
public class SettingPage extends AbstractPage {

  public SettingPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(className = "option")
  public List<WebElement> settingList;

  // Add address book button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div[2]")
  public WebElement addAddressBook_btn;

  @FindBy(className = "r2")
  public WebElement addressRecord;
  // Address  already exists.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[4]/span")
  public WebElement duplicateAddressTips;
  // Name  already exists.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/span")
  public WebElement duplicateNameTips;
  // Name input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[2]/input")
  public WebElement name_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[2]/input")
  public WebElement modifyName_input;
  // Address input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/div/input")
  public WebElement address_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[3]/div/input")
  public WebElement modifyAddress_input;
  // Note input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[4]/input")
  public WebElement remarks_input;
  // Save button.
  // @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/button[1]")
  @FindBy(className = "primary")
  public WebElement save_btn;
  // Close button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/button[2]/span")
  public WebElement close_btn;
  // Address book three word position.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/span")
  public WebElement addressBook_content;

  @FindBy(className = "addressName")
  public WebElement addressName_text;

  @FindBy(className = "address")
  public WebElement address_text;

  @FindBy(className = "remarks")
  public WebElement remarks_text;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[4]/input")
  public WebElement modifyRemarks_text;
  // Search input box.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/input")
  public WebElement search_input;
  // The name of the search result.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[1]/span/span")
  public WebElement searchResultName_content;

  // Address of search results.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[2]/span")
  public WebElement searchResultAddress_content;
  // Comments for search results.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div/div/div[3]")
  public WebElement searchResultRemarks_content;
  // Delete address button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div[1]/div/img")
  public WebElement delete_btn;

  // Confirm delete button.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div/div/div/div[4]/button[2]")
  public WebElement confirm_btn;

  // No search results prompt: the address was not found.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/span")
  public WebElement tips_content;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]/div/div/span")
  public WebElement dAPPWhiteList_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/span")
  public WebElement addNode_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[1]/input")
  public WebElement nodeName_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[2]/span")
  public WebElement nodeName_tips;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/input")
  public WebElement wrongFullNode_input;

  @FindBy(xpath = " //*[@id=\"root\"]/div/div[1]/div/div[2]/input")
  public WebElement fullNode_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[4]/span")
  public WebElement fullNode_tips;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[5]/input")
  public WebElement wrongEventServer_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/input")
  public WebElement eventServer_input;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[6]/span")
  public WebElement eventServer_tips;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]")
  public WebElement loseFocus;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div")
  public WebElement back;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/button/span")
  public WebElement addCustomNode_btn;

  @FindBy(className = "item")
  public List<WebElement> node_list;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[1]/div/div[3]/button[2]/span")
  public WebElement deleteConfirm_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[1]")
  public WebElement RMB_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[4]/div/div[2]/div[2]")
  public WebElement dollar_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[5]/div/div[1]/span")
  public WebElement language_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[2]/div[1]/span")
  public WebElement oneMinute_btn;

  // Never auto lock.
  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[6]/div/div[2]/div[6]/span")
  public WebElement neverAutomaticLocking_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[7]/div/div[1]/span")
  public WebElement ledgerConnection_btn;

  @FindBy(className = "item")
  public List<WebElement> aboutUsList;

  @FindBy(className = "unit")
  public List<WebElement> languageList;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[5]/div/div[2]/div[2]")
  public WebElement Chinese_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[5]/div/div[2]/div[1]")
  public WebElement English_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[5]/div/div[2]/div[3]")
  public WebElement Japanese_btn;

  @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]")
  public WebElement version;
}
