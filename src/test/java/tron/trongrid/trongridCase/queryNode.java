package tron.trongrid.trongridCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryNode extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "List nodes from trongrid")
  public void test01ListNodesFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get node info from trongrid")
  public void test02GetNodeInfoFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get chain parameters from trongrid")
  public void test03GetChainParametersFromTrongrid() {

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
