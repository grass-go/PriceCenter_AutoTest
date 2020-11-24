package tron.trongrid.trongridCase.V1API;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import tron.trongrid.base.V1Base;

public class trc20TokenAccountBalanceInfo extends V1Base {

    JSONObject getTrc20TokenAccountBalanceInfoBody;
    JSONObject accountBalanceInfoData;
    JSONArray trc20TokenAccountArray;

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trc20 token account balance from trongrid V1 API")
  public void test01GetTrc20TokenAccountBalanceFromTrongridV1() {
    getTrc20TokenAccountBalanceInfoBody = getTrc20TokenAccountBalanceInfo(usdjContractBase64);
    printJsonContent(getTrc20TokenAccountBalanceInfoBody);
    Assert.assertEquals(getTrc20TokenAccountBalanceInfoBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TokenAccountBalanceInfoBody.containsKey("meta"));
    accountBalanceInfoData = getTrc20TokenAccountBalanceInfoBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(accountBalanceInfoData);
    for (Map.Entry<String, Object> entry : accountBalanceInfoData.entrySet()) {
      Assert.assertTrue(Long.valueOf(entry.getValue().toString()) >= 0);
    }
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trc20 token account balance with no confirmed from trongrid V1 API")
  public void test02GetTrc20TokenAccountBalanceWithNoConfirmedFromTrongridV1() {
    getTrc20TokenAccountBalanceInfoBody = getTrc20TokenAccountBalanceInfo(usdjContractBase64,false,"",20);
    printJsonContent(getTrc20TokenAccountBalanceInfoBody);
    Assert.assertEquals(getTrc20TokenAccountBalanceInfoBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TokenAccountBalanceInfoBody.containsKey("meta"));
    accountBalanceInfoData = getTrc20TokenAccountBalanceInfoBody.getJSONArray("data").getJSONObject(0);
    printJsonContent(accountBalanceInfoData);
    for (Map.Entry<String, Object> entry : accountBalanceInfoData.entrySet()) {
      Assert.assertTrue(Long.valueOf(entry.getValue().toString()) >= 0);
    }
  }


  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trc20 token account balance with order by from trongrid V1 API")
  public void test03GetTrc20TokenAccountBalanceWithOrderbyFromTrongridV1() {
    getTrc20TokenAccountBalanceInfoBody = getTrc20TokenAccountBalanceInfo(usdjContractBase64,false,
        "balance,desc",20);
    printJsonContent(getTrc20TokenAccountBalanceInfoBody);
    Assert.assertEquals(getTrc20TokenAccountBalanceInfoBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TokenAccountBalanceInfoBody.containsKey("meta"));
    trc20TokenAccountArray = getTrc20TokenAccountBalanceInfoBody.getJSONArray("data");
    printJsonContent(accountBalanceInfoData);
    List<Long> descBalanceLIst = new ArrayList<>();
    for (int j = 0; j < trc20TokenAccountArray.size();j++) {
      accountBalanceInfoData = trc20TokenAccountArray.getJSONObject(j);
      for (Map.Entry<String, Object> entry : accountBalanceInfoData.entrySet()) {
        descBalanceLIst.add(Long.valueOf(entry.getValue().toString()));
      }
    }

    Long descFirstBalance = descBalanceLIst.get(0);
    for (int i = 1; i < descBalanceLIst.size();i++) {
      Assert.assertTrue(descFirstBalance >= descBalanceLIst.get(i));
      descFirstBalance = descBalanceLIst.get(i);
    }

    getTrc20TokenAccountBalanceInfoBody = getTrc20TokenAccountBalanceInfo(usdjContractBase64,false,
        "balance,asc",20);
    printJsonContent(getTrc20TokenAccountBalanceInfoBody);
    Assert.assertEquals(getTrc20TokenAccountBalanceInfoBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TokenAccountBalanceInfoBody.containsKey("meta"));
    trc20TokenAccountArray = getTrc20TokenAccountBalanceInfoBody.getJSONArray("data");
    printJsonContent(accountBalanceInfoData);
    List<Long> ascBalanceLIst = new ArrayList<>();
    for (int j = 0; j < trc20TokenAccountArray.size();j++) {
      accountBalanceInfoData = trc20TokenAccountArray.getJSONObject(j);
      for (Map.Entry<String, Object> entry : accountBalanceInfoData.entrySet()) {
        ascBalanceLIst.add(Long.valueOf(entry.getValue().toString()));
      }
    }

    Long ascFirstBalance = ascBalanceLIst.get(0);
    for (int i = 1; i < ascBalanceLIst.size();i++) {
      Assert.assertTrue(ascFirstBalance <= ascBalanceLIst.get(i));
      ascFirstBalance = ascBalanceLIst.get(i);
    }

    Assert.assertTrue(descBalanceLIst.get(0) > ascBalanceLIst.get(0));

  }

  /**
   * constructor.
   */
  @Test(enabled = true, description = "Get trc20 token account balance with limit from trongrid V1 API")
  public void test04GetTrc20TokenAccountBalanceWithLimitFromTrongridV1() {
    getTrc20TokenAccountBalanceInfoBody = getTrc20TokenAccountBalanceInfo(usdjContractBase64,false,
        "balance,desc",200);
    printJsonContent(getTrc20TokenAccountBalanceInfoBody);
    Assert.assertEquals(getTrc20TokenAccountBalanceInfoBody.getBoolean("success"),true);
    Assert.assertTrue(getTrc20TokenAccountBalanceInfoBody.containsKey("meta"));
    trc20TokenAccountArray = getTrc20TokenAccountBalanceInfoBody.getJSONArray("data");
    Assert.assertTrue(trc20TokenAccountArray.size() == 200);

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
