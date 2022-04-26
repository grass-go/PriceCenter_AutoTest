package tron.chromeExtension.pages;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import tron.chromeExtension.base.Base;
import tron.chromeExtension.utils.Helper;

import static tron.chromeExtension.base.Base.*;
import static tron.chromeExtension.base.Base.log;

public abstract class AbstractPage {

  public ChromeDriver driver;

  public AbstractPage(ChromeDriver driver) {

    this.driver = driver;
    PageFactory.initElements(driver, this);
    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
  }

  public WebElement waitForElement(By element) {
    PageFactory.initElements(driver, this);
    new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(element));
    return driver.findElement(element);
  }

  //  Copy clipboard contents and return string.
  public String fetchClipboardContents(Clipboard clip)
      throws IOException, UnsupportedFlavorException {
    // Get the contents of the clipboard.
    Transferable clipT = clip.getContents(null);
    if (clipT != null) {
      // Check whether the content is of text type.
      if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor))
        return (String) clipT.getTransferData(DataFlavor.stringFlavor);
    }
    return null;
  }

  // Verify back to home page.
  public Boolean onTheHomepageOrNot() throws Exception {
    MainPage mainPage = new MainPage(DRIVER);
    TimeUnit.SECONDS.sleep(5);
    mainPage.copy_btn.click();
    TimeUnit.SECONDS.sleep(5);
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    String accountAddress = fetchClipboardContents(clip);
    Base.log("accountAddress: " + accountAddress);
    Assert.assertNotNull(accountAddress);
    if (loginAddress.equals(accountAddress)) {
      return true;
    }
    return false;
  }
}
