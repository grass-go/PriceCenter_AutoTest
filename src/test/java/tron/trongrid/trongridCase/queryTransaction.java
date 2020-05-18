package tron.trongrid.trongridCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryTransaction extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction by id from trongrid")
  public void test01GetTransactionByIdFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by id from trongrid")
  public void test02GetTransactionInfoByIdFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get transaction info by block number from trongrid")
  public void test03GetTransactionInfoByBlockNumFromTrongrid() {

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
