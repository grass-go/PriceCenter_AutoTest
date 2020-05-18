package tron.trongrid.trongridCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class querySr extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List witnesses from trongrid")
  public void test01ListWitnessesFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get next maintenance time from trongrid")
  public void test02GetNextMaintenanceTimeFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get brokerage from trongrid")
  public void test03GetBrokerageFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get reward from trongrid")
  public void test04GetRewardFromTrongrid() {

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
