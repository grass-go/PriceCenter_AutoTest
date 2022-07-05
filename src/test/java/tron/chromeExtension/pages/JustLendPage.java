package tron.chromeExtension.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/** mine page */
public class JustLendPage extends AbstractPage {

  public JustLendPage(ChromeDriver driver) {
    super(driver);
    this.driver = driver;
  }

  @FindBy(
      xpath =
          "//*[@id=\"root\"]/div/div/div[2]/div[1]/div[3]/div[2]/div/div/div/div/div/table/tbody/tr[3]/td[6]/span/button[1]")
  public WebElement USDDSupply_btn;

  @FindBy(className = "modal-btn")
  public WebElement approveUSDD_btn;

  @FindBy(className = "afterIconVerify")
  public WebElement white_btn;
}
