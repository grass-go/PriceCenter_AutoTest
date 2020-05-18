package tron.trongrid.trongridCase;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryExchange extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List exchanges from trongrid")
  public void test01ListExchangesFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get exchange from trongrid")
  public void test02GetExchangeFromTrongrid() {

  }

  /**
   * constructor.
   */
  @AfterClass
  public void shutdown() throws InterruptedException {
    try {
      disConnect();
    } catch (Exception e) {

    }
  }
}
