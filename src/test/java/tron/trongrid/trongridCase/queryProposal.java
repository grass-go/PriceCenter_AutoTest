package tron.trongrid.trongridCase;

import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.Base;

public class queryProposal extends Base {
  JSONObject proposalJsonBody;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get proposal by id from trongrid")
  public void test01GetProposalByIdFromTrongrid() {
    response = getProposalById(proposalId);
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    proposalJsonBody = responseContent;
    Assert.assertEquals(proposalJsonBody.getInteger("proposal_id"),proposalId);
    Assert.assertTrue(proposalJsonBody.containsKey("create_time"));
    Assert.assertTrue(proposalJsonBody.containsKey("expiration_time"));
    Assert.assertEquals(proposalJsonBody.getString("state"),"APPROVED");
    Assert.assertEquals(proposalJsonBody.getString("proposer_address"),"41d376d829440505ea13c9d1c455317d51b62e4ab6");
    Assert.assertTrue(proposalJsonBody.getJSONArray("parameters").getJSONObject(0).getInteger("value") == 1);
    Assert.assertTrue(proposalJsonBody.getJSONArray("parameters").getJSONObject(0).getInteger("key") == 32);
    Assert.assertEquals(proposalJsonBody.getJSONArray("approvals").size(),23);
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "List proposals from trongrid")
  public void test02ListProposalsFromTrongrid() {
    response = listProposals();
    responseContent = parseResponseContent(response);
    printJsonContent(responseContent);
    Assert.assertTrue(responseContent.getJSONArray("proposals").contains(proposalJsonBody));
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
