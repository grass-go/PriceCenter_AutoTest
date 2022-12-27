package tron.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.protobuf.ByteString;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.testng.collections.Lists;
import org.tron.api.GrpcAPI;
import org.tron.common.utils.Base58;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.ByteUtil;
import org.tron.core.zen.address.DiversifierT;

import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class HttpMethed {

  static HttpClient httpClient;
  static HttpPost httppost;
  static HttpResponse response;
  static Integer connectionTimeout = 19000;
  static Integer soTimeout = 18000;
  static String transactionString;
  static String transactionSignString;
  static JSONObject responseContent;
  static JSONObject signResponseContent;
  static JSONObject transactionApprovedListContent;
  static String transactionStr;

  static {
    PoolingClientConnectionManager pccm = new PoolingClientConnectionManager();
    pccm.setDefaultMaxPerRoute(80);
    pccm.setMaxTotal(100);

    httpClient = new DefaultHttpClient(pccm);
  }

  public static String sendCoin(String httpNode, String fromAddress, String toAddress,
      Long amount, String visible, Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/createtransaction";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("to_address", toAddress);
      userBaseObj2.addProperty("owner_address", fromAddress);
      userBaseObj2.addProperty("amount", amount);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+120000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String freezeBalance(String httpNode, String ownerAddress, Long frozenBalance,
                                           Integer frozenDuration, Integer resourceCode, String receiverAddress,
                                           String visible, Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/freezebalance";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("frozen_balance", frozenBalance);
      userBaseObj2.addProperty("frozen_duration", frozenDuration);
      if (resourceCode == 0) {
        userBaseObj2.addProperty("resource", "BANDWIDTH");
      }
      if (resourceCode == 1) {
        userBaseObj2.addProperty("resource", "ENERGY");
      }
      if (receiverAddress != null) {
        userBaseObj2.addProperty("receiver_address", receiverAddress);
      }
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String unFreezeBalance(String httpNode, String ownerAddress, Integer resourceCode,
                                       String receiverAddress, String visible, Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/unfreezebalance";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      if (resourceCode == 0) {
        userBaseObj2.addProperty("resource", "BANDWIDTH");
      }
      if (resourceCode == 1) {
        userBaseObj2.addProperty("resource", "ENERGY");
      }
      if (receiverAddress != null) {
        userBaseObj2.addProperty("receiver_address", receiverAddress);
      }
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String assetIssue(String httpNode, String ownerAddress, String name, String abbr, Long totalSupply,
                                  Integer trxNum, Integer num, Long startTime, Long endTime, Integer voteScore,
                                  Integer precision, String description, String url, Long freeAssetNetLimit,
                                  Long publicFreeAssetNetLimit,Long frozenAmount,Long frozenDays, String visible,
                                  Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/createassetissue";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("name", name);
      userBaseObj2.addProperty("abbr", abbr);
      userBaseObj2.addProperty("total_supply", totalSupply);
      userBaseObj2.addProperty("trx_num", trxNum);
      userBaseObj2.addProperty("num", num);
      if (precision != null) {
        userBaseObj2.addProperty("precision", precision);
      }
      userBaseObj2.addProperty("start_time", startTime);
      userBaseObj2.addProperty("end_time", endTime);
      if (voteScore != null) {
        userBaseObj2.addProperty("vote_score", voteScore);
      }
      if (description != null) {
        userBaseObj2.addProperty("description", description);
      }
      if (url != null) {
        userBaseObj2.addProperty("url", url);
      }
      if (freeAssetNetLimit != null) {
        userBaseObj2.addProperty("free_asset_net_limit", freeAssetNetLimit);
      }
      if (publicFreeAssetNetLimit != null) {
        userBaseObj2.addProperty("public_free_asset_net_limit", publicFreeAssetNetLimit);
      }
      if (frozenAmount != null && frozenDays != null) {
        JsonObject frozenSupplyObj = new JsonObject();
        frozenSupplyObj.addProperty("frozen_amount",frozenAmount);
        frozenSupplyObj.addProperty("frozen_days",frozenDays);
        userBaseObj2.add("frozen_supply", frozenSupplyObj);
      }
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+420000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String transferAsset(String httpNode, String ownerAddress, String toAddress, String assetIssueById,
                                     Long amount, String visible, Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/transferasset";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("to_address", toAddress);
      userBaseObj2.addProperty("asset_name", assetIssueById);
      userBaseObj2.addProperty("amount", amount);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String participateAssetIssue(String httpNode, String toAddress, String ownerAddress, 
                                             String assetIssueById, Long amount, String visible, Integer permissionId,
                                             String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/participateassetissue";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("to_address", toAddress);
      userBaseObj2.addProperty("asset_name", assetIssueById);
      userBaseObj2.addProperty("amount", amount);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }
  
  public static String updateAssetIssue(String httpNode, String ownerAddress, String description, String url,
                                        Long newLimit, Long newPublicLimit, String visible, Integer permissionId,
                                        String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updateasset";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("url", url);
      userBaseObj2.addProperty("description", description);
      userBaseObj2.addProperty("new_limit", newLimit);
      userBaseObj2.addProperty("new_public_limit", newPublicLimit);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String unfreezeAsset(String httpNode, String ownerAddress, String visible, Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/unfreezeasset";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String voteWitnessAccount(String httpNode, String ownerAddress, JsonArray voteArray, String visible,
                                          Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/votewitnessaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.add("votes", voteArray);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String withdrawBalance(String httpNode, String witnessAddress, String visible, Integer permissionId,
                                       String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/withdrawbalance";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", witnessAddress);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String createWitness(String httpNode, String ownerAddress, String url, String visible, Integer permissionId,
                                     String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/createwitness";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("url", url);
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String updateWitness(String httpNode, String witnessAddress, String updateUrl, String visible,
                                     Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updatewitness";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("update_url", updateUrl);
      userBaseObj2.addProperty("owner_address", witnessAddress);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String updateBrokerage(String httpNode, String ownerAddress, Long brokerage, String visible,
                                       Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updateBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("brokerage", brokerage);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String createProposal(String httpNode, String ownerAddress, Long proposalKey, Long proposalValue,
                                      String visible, Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/proposalcreate";
      JsonObject userBaseObj2 = new JsonObject();
      JsonObject proposalMap = new JsonObject();
      proposalMap.addProperty("key", proposalKey);
      proposalMap.addProperty("value", proposalValue);
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.add("parameters", proposalMap);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String approvalProposal(String httpNode, String ownerAddress, Integer proposalId, Boolean isAddApproval,
                                        String visible, Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/proposalapprove";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("proposal_id", proposalId);
      userBaseObj2.addProperty("is_add_approval", isAddApproval);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String deleteProposal(String httpNode, String ownerAddress, Integer proposalId, String visible,
                                      Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/proposaldelete";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("proposal_id", proposalId);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String setAccountId(String httpNode, String address, String accountId, String visible,
                                    Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/setaccountid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("account_id", accountId);
      userBaseObj2.addProperty("owner_address", address);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String marketSellAsset(String httpNode, String ownerAddress, String sellTokenId, Long sellTokenQuantity,
                                       String buyTokenId, Long buyTokenQuantity, String visible, Integer permissionId,
                                       String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/marketsellasset";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("sell_token_id", sellTokenId);
      userBaseObj2.addProperty("buy_token_id", buyTokenId);
      userBaseObj2.addProperty("sell_token_quantity", sellTokenQuantity);
      userBaseObj2.addProperty("buy_token_quantity", buyTokenQuantity);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String marketCancelOrder(String httpNode, String ownerAddress, String orderId, String visible,
                                         Integer permissionId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/marketcancelorder";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("order_id", orderId);
      userBaseObj2.addProperty("visible", visible);
      userBaseObj2.addProperty("Permission_id", permissionId);
      log.info("userBaseObj2:"+userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info("transactionString:"+transactionString);
      JSONObject transactionObject = HttpMethed.parseStringContent(transactionString);
      transactionObject.getJSONObject("raw_data").replace("expiration",transactionObject.getJSONObject("raw_data").getLongValue("expiration")+360000);

      transactionSignString = gettransactionsign(httpNode, transactionObject.toString(), fromKey);
      log.info("-----------sign information-----------------");
      log.info(transactionSignString);

      JSONObject jsonObject = HttpMethed.parseStringContent(transactionSignString);
      jsonObject.remove("visible");
      jsonObject.remove("txID");
      jsonObject.remove("raw_data_hex");
      transactionStr = jsonObject.toString();
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionStr;
  }

  public static String gettransactionsign(String httpNode, String transactionString,
                                          String privateKey) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/gettransactionsign";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("transaction", transactionString);
      userBaseObj2.addProperty("privateKey", privateKey);
      response = createConnect(requestUrl, userBaseObj2);
      transactionSignString = EntityUtils.toString(response.getEntity());
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return transactionSignString;
  }

  public static HttpResponse broadcastTransaction(String httpNode, String transactionSignString) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/broadcasttransaction";
      httpClient.getParams()
              .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      httppost = new HttpPost(requestUrl);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      if (transactionSignString != null) {
        StringEntity entity = new StringEntity(transactionSignString, Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }
      response = httpClient.execute(httppost);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }

    responseContent = HttpMethed.parseResponseContent(response);
    Integer times = 0;

    while (times++ <= 10 && responseContent.getString("code") != null && responseContent
            .getString("code").equalsIgnoreCase("SERVER_BUSY")) {
      log.info("retry num are " + times);
      try {
        response = httpClient.execute(httppost);
      } catch (Exception e) {
        e.printStackTrace();
        httppost.releaseConnection();
        return null;
      }
      responseContent = HttpMethed.parseResponseContent(response);
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    httppost.releaseConnection();
    return response;
  }

  public static HttpResponse createConnect(String url) {
    return createConnect(url, null);
  }

  public static HttpResponse createConnect(String url, JsonObject requestBody) {
    try {
      httpClient.getParams()
              .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        httppost.setEntity(entity);
      }

      log.info(httppost.toString());
      response = httpClient.execute(httppost);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  public static void disConnect() {
    httppost.releaseConnection();
  }

  public static JSONObject parseResponseContent(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      JSONObject obj = JSONObject.parseObject(result);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static JSONObject parseStringContent(String content) {
    try {
      JSONObject obj = JSONObject.parseObject(content);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void printJsonContent(JSONObject responseContent) {
    log.info("----------------------------Print JSON Start---------------------------");
    for (String str : responseContent.keySet()) {
      log.info(str + ":" + responseContent.get(str));
    }
    log.info("JSON content size are: " + responseContent.size());
    log.info("----------------------------Print JSON End-----------------------------");
  }

  public static String str2hex(String str) {
    char[] chars = "0123456789ABCDEF".toCharArray();
    StringBuilder sb = new StringBuilder();
    byte[] bs = str.getBytes();
    int bit;
    for (int i = 0; i < bs.length; i++) {
      bit = (bs[i] & 0x0f0) >> 4;
      sb.append(chars[bit]);
      bit = bs[i] & 0x0f;
      sb.append(chars[bit]);
      // sb.append(' ');
    }
    return sb.toString().trim();
  }
}
