package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

/**
 * mine page
 */
public class SendPage extends AbstractPage {


    public SendPage(ChromeDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[2]/div[1]/input")
    public WebElement receiverAddress_input;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[4]/div[1]/input")
    public WebElement amount_input;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/button/span")
    public WebElement send_btn;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[1]/div[2]")
    public WebElement balanceInSendPage_text;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/div")
    public WebElement select_coin_type_btn;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[3]/span[1]")
    public WebElement btt_in_selection_btn;

    @FindBy(xpath = "//*[@id=\"root\"]/div/div[2]/div[3]/div/div[2]/div[4]/span[1]")
    public WebElement win_in_selection_btn;



}
