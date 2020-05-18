package tron.trongrid.trongridCase;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryTrc10 extends Base {

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue by account from trongrid")
  public void test01GetAssetIssueByAccountFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue by id from trongrid")
  public void test02GetAssetIssueByIdFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get asset issue list from trongrid")
  public void test03GetAssetIssueListFromTrongrid() {

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get paginated assetissue list from trongrid")
  public void test04GetPaginatedAssetIssueListFromTrongrid() {

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
