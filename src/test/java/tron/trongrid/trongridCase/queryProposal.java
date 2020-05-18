package tron.trongrid.trongridCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryProposal extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List proposals from trongrid")
  public void test01ListProposalsFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get proposal from trongrid")
  public void test02GetProposalFromTrongrid() {

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
