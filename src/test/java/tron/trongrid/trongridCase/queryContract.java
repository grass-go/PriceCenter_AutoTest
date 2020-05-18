package tron.trongrid.trongridCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryContract extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get contract from trongrid")
  public void test01ListProposalsFromTrongrid() {

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
