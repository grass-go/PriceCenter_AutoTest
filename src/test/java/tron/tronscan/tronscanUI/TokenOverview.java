package tron.tronscan.tronscanUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import tron.common.utils.Configuration;
import tron.common.utils.MyIRetryAnalyzer;
import tron.common.utils.WebBrowser;

public class TokenOverview {
    private String tronScanNode = Configuration.getByPath("testng.conf")
            .getString("tronscanIP");
    private  String URL = "https://"+tronScanNode+"/#/tokens/list";
    WebBrowser webBrowser = new WebBrowser();
    public static WebDriver driver;

    @BeforeMethod(enabled = true)
    public void start() throws Exception {
        try {
            driver = webBrowser.startChrome(URL);
        } catch (Exception e) {
        }
    }
    @Test(enabled = true,description = "通证概览",retryAnalyzer = MyIRetryAnalyzer.class)
    public void tokenTest() throws Exception{

        driver.findElement(By.cssSelector("div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("main > div > div > a > span")).getText().isEmpty();
        //TRON通证总数
        driver.findElement(By.cssSelector("div.token-num-wrap> div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > span")).getText().isEmpty();
        //TRON通证总数文案
        driver.findElement(By.cssSelector("div.token-num-wrap > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > span")).getText().isEmpty();
        //7days通证总数
        driver.findElement(By.cssSelector("div.token-num-wrap> div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > span")).getText().isEmpty();
        //收录的通证数
        driver.findElement(By.cssSelector("div.token-num-wrap> div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > span")).getText().isEmpty();
        //7days新增Tronscan通证数
        driver.findElement(By.cssSelector("div.token-num-wrap> div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > span")).getText().isEmpty();
        //10trc和20trc
        driver.findElement(By.cssSelector("div.d-flex> div.d-md-flex > div > div > label:nth-child(2) > span:nth-child(2)")).click();
        driver.findElement(By.cssSelector("div.d-md-flex > div > div > label:nth-child(3) > span:nth-child(2)")).click();
        driver.close();

    }
    @Test(enabled = true,description = "查看进入的通证是否是10token",retryAnalyzer = MyIRetryAnalyzer.class)
    public void trc10Test() throws Exception{
        driver.findElement(By.cssSelector("div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div.d-flex > div.d-md-flex.apply-trc20.apply-all.align-items-center > div > div > label:nth-child(2) > span:nth-child(2)")).click();
        //点击一个10token详情
        driver.findElement(By.cssSelector("table > tbody > tr.ant-table-row.trc20-star-ad.ant-table-row-level-0 > td.ant_table.d-sm-table-cell.token-list-action.ant-table-row-cell-break-word > div > a > span")).getText().isEmpty();
        Thread.sleep(200);
        driver.findElement(By.cssSelector("table > tbody > tr:nth-child(1) > td.ant_table.d-sm-table-cell.token-list-action.ant-table-row-cell-break-word > div > a > span")).getText().isEmpty();
        driver.close();

    }

    @Test(enabled = true,description = "查看进入的通证是否是20token",retryAnalyzer = MyIRetryAnalyzer.class)
    public void trc20Test() throws Exception{
        driver.findElement(By.cssSelector("div.container.d-flex.sub-header > h4 > span > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div.d-md-flex > div > div > label:nth-child(3) > span:nth-child(2)")).click();
        //点击一个20token详情
        driver.findElement(By.cssSelector("table > tbody > tr:nth-child(1) > td.ant_table.d-sm-table-cell.token-list-action.ant-table-row-cell-break-word > div > a > span")).getText().isEmpty();
        Thread.sleep(200);
        driver.findElement(By.cssSelector("table > tbody > tr:nth-child(1) > td:nth-child(2) > div > div:nth-child(2) > h5 > div > a")).click();
        //匹配是否是20通证
        Thread.sleep(200);
        Assert.assertEquals(driver.findElement(By.cssSelector("div > div > div:nth-child(1) > div.card-body.mt-2 > div > div.token-sign")).getText(), "TRC20");
        //点通证转账
        driver.findElement(By.cssSelector("#root > main > div > div > div.card.mt-3 > div.card-header > ul > li:nth-child(1) > a > span > span")).click();
        //14 days
        driver.findElement(By.cssSelector("div.col-xs-8.col-sm-6 > div > label.ant-radio-button-wrapper.ant-radio-button-wrapper-checked > span:nth-child(2) > span")).click();
        //14天total文案说明
        driver.findElement(By.cssSelector("div.row.transfers.token20Detail > div > div > div:nth-child(1) > div:nth-child(2) > div > div > div > span:nth-child(4) > span:nth-child(1)")).getText().isEmpty();
        Thread.sleep(200);
        //通证持有者
        driver.findElement(By.cssSelector("div > div.card.mt-3 > div.card-header > ul > li:nth-child(2) > a > span > span")).click();
        //通证持有者内部展示
        driver.findElement(By.cssSelector(" div.card.mt-3 > div.card-body.p-0 > div.row.transfers > div > div.holder-distribution > section.distribution-header > span")).getText().isEmpty();
        driver.findElement(By.cssSelector("div > div.distributionWrapper > div:nth-child(2) > div > div:nth-child(1) > span:nth-child(1)")).getText().isEmpty();
        //交易市场
        driver.findElement(By.cssSelector("div.card.mt-3 > div.card-header > ul > li:nth-child(3) > a > span > span")).click();
        //合约
        driver.findElement(By.cssSelector("div.card.mt-3 > div.card-header > ul > li:nth-child(4) > a > span > span")).click();
        //合约页展示:合约代码，
        driver.findElement(By.cssSelector("main > div.tab-container > main > div:nth-child(2) > div.d-flex.mb-1 > span:nth-child(1) > span")).getText().isEmpty();
        //阅读合约
        driver.findElement(By.cssSelector("div > div.ant-radio-group> label.ant-radio-button-wrapper.ant-radio-button-wrapper-checked > span:nth-child(2) > span")).click();
        driver.close();

    }
    @AfterMethod(enabled = true)
    public void end() throws Exception {
        WebBrowser.tearDownBrowser();
    }
}
