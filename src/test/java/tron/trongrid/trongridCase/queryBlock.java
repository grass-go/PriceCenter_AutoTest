package tron.trongrid.trongridCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryBlock extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get now block from trongrid")
  public void test01GetNowBlockFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by number from trongrid")
  public void test02GetBlockByNumFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by id from trongrid")
  public void test03GetBlockByIdFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by latest num from trongrid")
  public void test04GetBlockByLatestNumFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get block by limit next from trongrid")
  public void test05GetBlockByLimitNextFromTrongrid() {

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
