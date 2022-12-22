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

  /**
   * constructor.
   */
  public static HttpResponse updateAccount(String httpNode, byte[] updateAccountAddress,
      String accountName, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updateaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("account_name", str2hex(accountName));
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(updateAccountAddress));
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse setAccountId(String httpNode, byte[] setAccountIdAddress,
      String accountId, Boolean visable, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/setaccountid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("account_id", accountId);
      userBaseObj2.addProperty("owner_address",
          Base58.encode(TronlinkApiList.getFinalAddress(fromKey)));
      userBaseObj2.addProperty("visible", visable);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse updateWitness(String httpNode, byte[] witnessAddress, String updateUrl,
      String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updatewitness";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("update_url", str2hex(updateUrl));
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(witnessAddress));
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse voteWitnessAccount(String httpNode, byte[] ownerAddress,
      JsonArray voteArray, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/votewitnessaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.add("votes", voteArray);
      log.info(userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse createAccount(String httpNode, byte[] ownerAddress,
      byte[] accountAddress, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/createaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("account_address", ByteArray.toHexString(accountAddress));
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse createWitness(String httpNode, byte[] ownerAddress, String url) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/createwitness";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("url", str2hex(url));
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
      //transactionString = EntityUtils.toString(response.getEntity());
      //transactionSignString = gettransactionsign(httpNode,transactionString,fromKey);
      //response = broadcastTransaction(httpNode,transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse withdrawBalance(String httpNode, byte[] witnessAddress) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/withdrawbalance";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(witnessAddress));
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
      //transactionString = EntityUtils.toString(response.getEntity());
      //transactionSignString = gettransactionsign(httpNode,transactionString,fromKey);
      //response = broadcastTransaction(httpNode,transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
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

  /**
   * constructor.
   */
  public static HttpResponse createProposal(String httpNode, byte[] ownerAddress, Long proposalKey,
      Long proposalValue, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/proposalcreate";
      JsonObject userBaseObj2 = new JsonObject();
      JsonObject proposalMap = new JsonObject();
      proposalMap.addProperty("key", proposalKey);
      proposalMap.addProperty("value", proposalValue);
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.add("parameters", proposalMap);

      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse approvalProposal(String httpNode, byte[] ownerAddress,
      Integer proposalId, Boolean isAddApproval, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/proposalapprove";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("proposal_id", proposalId);
      userBaseObj2.addProperty("is_add_approval", isAddApproval);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse deleteProposal(String httpNode, byte[] ownerAddress,
      Integer proposalId, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/proposaldelete";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("proposal_id", proposalId);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getChainParameters(String httpNode) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/getchainparameters";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse accountPermissionUpdate(String httpNode, byte[] ownerAddress,
      JsonObject ownerObject, JsonObject witnessObject, JsonObject activesObject, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/accountpermissionupdate";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.add("owner", ownerObject);
      //userBaseObj2.add("witness", witnessObject);
      userBaseObj2.add("actives", activesObject);
      log.info(userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);

      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse exchangeCreate(String httpNode, byte[] ownerAddress,
      String firstTokenId, Long firstTokenBalance, String secondTokenId, Long secondTokenBalance,
      String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/exchangecreate";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("first_token_id", str2hex(firstTokenId));
      userBaseObj2.addProperty("first_token_balance", firstTokenBalance);
      userBaseObj2.addProperty("second_token_id", str2hex(secondTokenId));
      userBaseObj2.addProperty("second_token_balance", secondTokenBalance);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse exchangeInject(String httpNode, byte[] ownerAddress,
      Integer exchangeId, String tokenId, Long quant, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/exchangeinject";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("exchange_id", exchangeId);
      userBaseObj2.addProperty("token_id", str2hex(tokenId));
      userBaseObj2.addProperty("quant", quant);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse exchangeWithdraw(String httpNode, byte[] ownerAddress,
      Integer exchangeId, String tokenId, Long quant, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/exchangewithdraw";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("exchange_id", exchangeId);
      userBaseObj2.addProperty("token_id", str2hex(tokenId));
      userBaseObj2.addProperty("quant", quant);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse exchangeTransaction(String httpNode, byte[] ownerAddress,
      Integer exchangeId, String tokenId, Long quant, Long expected, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/exchangetransaction";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("exchange_id", exchangeId);
      userBaseObj2.addProperty("token_id", str2hex(tokenId));
      userBaseObj2.addProperty("quant", quant);
      userBaseObj2.addProperty("expected", expected);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse assetIssue(String httpNode, byte[] ownerAddress, String name,
      String abbr, Long totalSupply, Integer trxNum, Integer num, Long startTime, Long endTime,
      Integer voteScore, Integer precision, String description, String url, Long freeAssetNetLimit,
      Long publicFreeAssetNetLimit, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/createassetissue";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("name", str2hex(name));
      userBaseObj2.addProperty("abbr", str2hex(abbr));
      userBaseObj2.addProperty("total_supply", totalSupply);
      userBaseObj2.addProperty("trx_num", trxNum);
      userBaseObj2.addProperty("num", num);
      userBaseObj2.addProperty("precision", precision);
      userBaseObj2.addProperty("start_time", startTime);
      userBaseObj2.addProperty("end_time", endTime);
      userBaseObj2.addProperty("vote_score", voteScore);
      userBaseObj2.addProperty("description", str2hex(description));
      userBaseObj2.addProperty("url", str2hex(url));
      userBaseObj2.addProperty("free_asset_net_limit", freeAssetNetLimit);
      userBaseObj2.addProperty("public_free_asset_net_limit", publicFreeAssetNetLimit);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse transferAsset(String httpNode, byte[] ownerAddress, byte[] toAddress,
      String assetIssueById, Long amount, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/transferasset";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("to_address", ByteArray.toHexString(toAddress));
      userBaseObj2.addProperty("asset_name", str2hex(assetIssueById));
      userBaseObj2.addProperty("amount", amount);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse deployContract(String httpNode, String name, String abi,
      String bytecode, Long bandwidthLimit, Long feeLimit, Integer consumeUserResourcePercent,
      Long originEnergyLimit, Long callValue, Integer tokenId, Long tokenValue, byte[] ownerAddress,
      String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/deploycontract";
      JsonObject userBaseObj2 = new JsonObject();
      //userBaseObj2.addProperty("name", str2hex(name));
      userBaseObj2.addProperty("name", name);
      userBaseObj2.addProperty("abi", abi);
      userBaseObj2.addProperty("bytecode", bytecode);
      userBaseObj2.addProperty("bandwidth_limit", bandwidthLimit);
      userBaseObj2.addProperty("fee_limit", feeLimit);
      userBaseObj2.addProperty("consume_user_resource_percent", consumeUserResourcePercent);
      userBaseObj2.addProperty("origin_energy_limit", originEnergyLimit);
      userBaseObj2.addProperty("call_value", callValue);
      userBaseObj2.addProperty("token_id", tokenId);
      userBaseObj2.addProperty("tokenValue", tokenValue);
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static String deployContractGetTxid(String httpNode, String name, String abi,
      String bytecode, Long bandwidthLimit, Long feeLimit, Integer consumeUserResourcePercent,
      Long originEnergyLimit, Long callValue, Integer tokenId, Long tokenValue, byte[] ownerAddress,
      String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/deploycontract";
      JsonObject userBaseObj2 = new JsonObject();
      //userBaseObj2.addProperty("name", str2hex(name));
      userBaseObj2.addProperty("name", name);
      userBaseObj2.addProperty("abi", abi);
      userBaseObj2.addProperty("bytecode", bytecode);
      userBaseObj2.addProperty("bandwidth_limit", bandwidthLimit);
      userBaseObj2.addProperty("fee_limit", feeLimit);
      userBaseObj2.addProperty("consume_user_resource_percent", consumeUserResourcePercent);
      userBaseObj2.addProperty("origin_energy_limit", originEnergyLimit);
      userBaseObj2.addProperty("call_value", callValue);
      userBaseObj2.addProperty("token_id", tokenId);
      userBaseObj2.addProperty("call_token_value", tokenValue);
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));

      log.info(userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseStringContent(transactionString);
    return responseContent.getString("txID");
  }

  /**
   * constructor.
   */
  public static HttpResponse deployContractGetTxidWithTooBigLong(String httpNode, String name,
      String abi, String bytecode, Long bandwidthLimit, Long feeLimit,
      Integer consumeUserResourcePercent, Long originEnergyLimit, Long callValue, Integer tokenId,
      Long tokenValue, byte[] ownerAddress, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/deploycontract";

      String text = "{\"call_token_value\": 10000000e100000000}";
      JSONObject jsonObject = JSONObject.parseObject(text);
      log.info("jsonObject: " + jsonObject.toString());
      jsonObject.put("name", name);
      jsonObject.put("abi", abi);
      jsonObject.put("bytecode", bytecode);
      jsonObject.put("bandwidth_limit", bandwidthLimit);
      jsonObject.put("fee_limit", feeLimit);
      jsonObject.put("consume_user_resource_percent", consumeUserResourcePercent);
      jsonObject.put("origin_energy_limit", originEnergyLimit);
      jsonObject.put("call_value", callValue);
      jsonObject.put("token_id", tokenId);
      jsonObject.put("owner_address", ByteArray.toHexString(ownerAddress));

      log.info(jsonObject.toString());
      response = createConnect1(requestUrl, jsonObject);
      /*      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);*/
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static String triggerContractGetTxid(String httpNode, byte[] ownerAddress,
      String contractAddress, String functionSelector, String parameter, Long feeLimit,
      Long callValue, Integer tokenId, Long tokenValue, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/triggersmartcontract";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("function_selector", functionSelector);
      userBaseObj2.addProperty("parameter", parameter);
      userBaseObj2.addProperty("fee_limit", feeLimit);
      userBaseObj2.addProperty("call_value", callValue);
      userBaseObj2.addProperty("token_id", tokenId);
      userBaseObj2.addProperty("call_token_value", tokenValue);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);

      transactionSignString = gettransactionsign(httpNode,
          parseStringContent(transactionString).getString("transaction"), fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseStringContent(transactionSignString);
    return responseContent.getString("txID");
  }


  /**
   * constructor.
   */
  public static String triggerContractGetTxidWithVisibleTrue(String httpNode, String ownerAddress,
      String contractAddress, String functionSelector, String parameter, Long feeLimit,
      Long callValue, Integer tokenId, Long tokenValue, String fromKey) {
    return triggerContractGetTxidWithVisibleTrue(httpNode, "", ownerAddress,
        contractAddress, functionSelector, parameter, feeLimit, callValue, tokenId, tokenValue,
        fromKey);

  }

  /**
   * constructor.
   */
  public static String triggerContractGetTxidWithVisibleTrue(String httpNode,
      String anotherHttpNode,
      String ownerAddress,
      String contractAddress, String functionSelector, String parameter, Long feeLimit,
      Long callValue, Integer tokenId, Long tokenValue, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/triggersmartcontract";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ownerAddress);
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("function_selector", functionSelector);
      userBaseObj2.addProperty("parameter", parameter);
      userBaseObj2.addProperty("fee_limit", feeLimit);
      userBaseObj2.addProperty("call_value", callValue);
      userBaseObj2.addProperty("token_id", tokenId);
      userBaseObj2.addProperty("call_token_value", tokenValue);
      userBaseObj2.addProperty("visible", true);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);

      transactionSignString = gettransactionsign(httpNode,
          parseStringContent(transactionString).getString("transaction"), fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
      if (!anotherHttpNode.isEmpty()) {
        broadcastTransaction(anotherHttpNode, transactionSignString);
      }
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseStringContent(transactionSignString);
    return responseContent.getString("txID");
  }


  /**
   * constructor.
   */
  public static HttpResponse triggerConstantContract(String httpNode, byte[] ownerAddress,
      String contractAddress, String functionSelector, String parameter, Long feeLimit,
      String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/triggerconstantcontract";
      JsonObject userBaseObj2 = new JsonObject();

      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("function_selector", functionSelector);
      userBaseObj2.addProperty("parameter", parameter);
      userBaseObj2.addProperty("fee_limit", feeLimit);

      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse triggerConstantContract(String httpNode, byte[] ownerAddress,
      String contractAddress, String functionSelector, String parameter) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/triggerconstantcontract";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("function_selector", functionSelector);
      userBaseObj2.addProperty("parameter", parameter);
      response = createConnect(requestUrl, userBaseObj2);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static HttpResponse triggerConstantContractFromSolidity(String httSoliditypNode,
      byte[] ownerAddress,
      String contractAddress, String functionSelector, String parameter) {
    try {
      final String requestUrl =
          "http://" + httSoliditypNode + "/walletsolidity/triggerconstantcontract";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("function_selector", functionSelector);
      userBaseObj2.addProperty("parameter", parameter);
      response = createConnect(requestUrl, userBaseObj2);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static HttpResponse triggerConstantContractFromPbft(String httpPbftNode,
      byte[] ownerAddress,
      String contractAddress, String functionSelector, String parameter) {
    try {
      final String requestUrl = "http://" + httpPbftNode + "/walletpbft/triggerconstantcontract";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("function_selector", functionSelector);
      userBaseObj2.addProperty("parameter", parameter);
      response = createConnect(requestUrl, userBaseObj2);
      return response;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static HttpResponse participateAssetIssue(String httpNode, byte[] toAddress,
      byte[] ownerAddress, String assetIssueById, Long amount, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/participateassetissue";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("to_address", ByteArray.toHexString(toAddress));
      userBaseObj2.addProperty("asset_name", str2hex(assetIssueById));
      userBaseObj2.addProperty("amount", amount);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse updateAssetIssue(String httpNode, byte[] ownerAddress,
      String description, String url, Long newLimit, Long newPublicLimit, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updateasset";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("url", str2hex(url));
      userBaseObj2.addProperty("description", str2hex(description));
      userBaseObj2.addProperty("new_limit", newLimit);
      userBaseObj2.addProperty("new_public_limit", newPublicLimit);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static Boolean verificationResult(HttpResponse response) {
    if (response.getStatusLine().getStatusCode() != 200) {
      return false;
    }
    Assert.assertEquals(response.getStatusLine().getStatusCode(), 200);
    responseContent = HttpMethed.parseResponseContent(response);
    //HttpMethed.printJsonContent(responseContent);
    return Boolean.valueOf(responseContent.getString("result")).booleanValue();
  }

  /**
   * constructor.
   */
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

  /**
   * constructor.
   */
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

  /**
   * constructor.
   */
  public static HttpResponse broadcasthex(String httpNode, String transactionHex) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/broadcasthex";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("transaction", transactionHex);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getAccountById(String httpNode, String accountId, Boolean visable) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getaccountbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("account_id", accountId);
      userBaseObj2.addProperty("visible", visable);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccountByIdFromSolidity(String httpSolidityNode, String accountId,
      Boolean visable) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getaccountbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("account_id", accountId);
      userBaseObj2.addProperty("visible", visable);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccountByIdFromPbft(String httpSolidityNode, String accountId,
      Boolean visable) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getaccountbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("account_id", accountId);
      userBaseObj2.addProperty("visible", visable);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccount(String httpNode, byte[] queryAddress) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static Long getAccountForResponse(String httpNode, byte[] queryAddress, Integer times) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(queryAddress));
      Long duration = createConnectForResponse(requestUrl, userBaseObj2, times);
      return duration;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return -1L;
    }
  }


  /**
   * constructor.
   */
  public static HttpResponse getAccountFromSolidity(String httpSolidityNode, byte[] queryAddress) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
      log.info(requestUrl);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccountFromPbft(String httpSolidityNode, byte[] queryAddress) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(queryAddress));
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getContract(String httpNode, String contractAddress) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getcontract";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", contractAddress);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getSignWeight(String httpNode, String transactionSignString) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getsignweight";
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
    //httppost.releaseConnection();
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionApprovedList(String httpNode,
      String transactionSignString) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getapprovedlist";
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
    //httppost.releaseConnection();
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse listExchanges(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/listexchanges";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse listExchangesFromSolidity(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/listexchanges";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse listExchangesFromPbft(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/listexchanges";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse listNodes(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/listnodes";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getNextmaintenanceTime(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getnextmaintenancetime";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getChainParameter(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getchainparameters";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getNodeInfo(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getnodeinfo";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse listwitnesses(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/listwitnesses";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse listwitnessesFromSolidity(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/listwitnesses";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse listwitnessesFromPbft(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/listwitnesses";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse listProposals(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/listproposals";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getExchangeById(String httpNode, Integer exchangeId) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getexchangebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("id", exchangeId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getExchangeByIdFromSolidity(String httpSolidityNode,
      Integer exchangeId) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getexchangebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("id", exchangeId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getExchangeByIdFromPbft(String httpSolidityNode, Integer exchangeId) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getexchangebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("id", exchangeId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getProposalById(String httpNode, Integer proposalId) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getproposalbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("id", proposalId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueListByName(String httpNode, String name) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getassetissuelistbyname";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", str2hex(name));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueListByNameFromSolidity(String httpSolidityNode,
      String name) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getassetissuelistbyname";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", str2hex(name));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueListByNameFromPbft(String httpPbftNode,
      String name) {
    try {
      String requestUrl = "http://" + httpPbftNode + "/walletpbft/getassetissuelistbyname";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", str2hex(name));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueById(String httpNode, String assetIssueId) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getassetissuebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", assetIssueId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueByIdFromSolidity(String httpSolidityNode,
      String assetIssueId) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getassetissuebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", assetIssueId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueByIdFromPbft(String httpSolidityNode,
      String assetIssueId) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getassetissuebyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", assetIssueId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getTransactionById(String httpNode, String txid) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/gettransactionbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", txid);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static Long getTransactionByIdForResponse(String httpNode, String txid, Integer times) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/gettransactionbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", txid);
      Long duration = createConnectForResponse(requestUrl, userBaseObj2, times);
      return duration;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
  }


  /**
   * constructor.
   */
  public static HttpResponse getTransactionByIdFromSolidity(String httpSolidityNode, String txid) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/gettransactionbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", txid);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionByIdFromPbft(String httpSolidityNode, String txid) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/gettransactionbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", txid);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoById(String httpNode, String txid) {
    return getTransactionInfoById(httpNode, txid, false);
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoById(String httpNode, String txid, Boolean visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/gettransactioninfobyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", txid);
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoByIdFromSolidity(String httpSolidityNode,
      String txid) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/gettransactioninfobyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", txid);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoByIdFromPbft(String httpSolidityNode, String txid) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/gettransactioninfobyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", txid);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoByBlocknum(String httpNode, long blocknum) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/gettransactioninfobyblocknum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blocknum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionInfoByBlocknumFromSolidity(String httpSolidityNode,
      long blocknum) {
    try {
      String requestUrl =
          "http://" + httpSolidityNode + "/walletsolidity/gettransactioninfobyblocknum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blocknum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionCountByBlocknumFromSolidity(String httpSolidityNode,
      long blocknum) {
    try {
      String requestUrl =
          "http://" + httpSolidityNode + "/walletsolidity/gettransactioncountbyblocknum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blocknum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionCountByBlocknumFromPbft(String httpSolidityNode,
      long blocknum) {
    try {
      String requestUrl =
          "http://" + httpSolidityNode + "/walletpbft/gettransactioncountbyblocknum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blocknum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getTransactionsFromThisFromSolidity(String httpSolidityNode,
      byte[] fromAddress, long offset, long limit) {
    try {
      Map<String, String> map1 = new HashMap<String, String>();
      Map<String, Object> map = new HashMap<String, Object>();
      map1.put("address", ByteArray.toHexString(fromAddress));
      map.put("account", map1);
      map.put("offset", offset);
      map.put("limit", limit);
      String requestUrl = "http://" + httpSolidityNode + "/walletextension/gettransactionsfromthis";
      String jsonStr = new Gson().toJson(map);
      JsonObject jsonObj = new JsonParser().parse(jsonStr).getAsJsonObject();
      response = createConnect(requestUrl, jsonObj);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionsToThisFromSolidity(String httpSolidityNode,
      byte[] toAddress, long offset, long limit) {
    try {
      Map<String, String> map1 = new HashMap<String, String>();
      Map<String, Object> map = new HashMap<String, Object>();
      map1.put("address", ByteArray.toHexString(toAddress));
      map.put("account", map1);
      map.put("offset", offset);
      map.put("limit", limit);
      String requestUrl = "http://" + httpSolidityNode + "/walletextension/gettransactionstothis";
      String jsonStr = new Gson().toJson(map);
      JsonObject jsonObj = new JsonParser().parse(jsonStr).getAsJsonObject();
      response = createConnect(requestUrl, jsonObj);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueByName(String httpNode, String name) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getassetissuebyname";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", str2hex(name));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueByNameFromSolidity(String httpSolidityNode, String name) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getassetissuebyname";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", str2hex(name));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueByNameFromPbft(String httpSolidityNode, String name) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getassetissuebyname";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", str2hex(name));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static Long getBalance(String httpNode, byte[] queryAddress) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getaccount";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseResponseContent(response);
    //HttpMethed.printJsonContent(responseContent);
    //httppost.releaseConnection();
    return Long.parseLong(responseContent.get("balance").toString());
  }


  /**
   * constructor.
   */
  public static HttpResponse getAccountNet(String httpNode, byte[] queryAddress) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getaccountnet";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccountReource(String httpNode, byte[] queryAddress) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getaccountresource";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAccountBalance(String httpNode,
      byte[] queryAddress, Integer blockNum, String blockHash) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/getaccountbalance";
      JsonObject addressObj = new JsonObject();
      addressObj.addProperty("address", Base58.encode(queryAddress));
      JsonObject blockObj = new JsonObject();
      blockObj.addProperty("hash", blockHash);
      blockObj.addProperty("number", blockNum);
      JsonObject accountBalanceObj = new JsonObject();
      accountBalanceObj.add("account_identifier", addressObj);
      accountBalanceObj.add("block_identifier", blockObj);
      accountBalanceObj.addProperty("visible", true);
      response = createConnect(requestUrl, accountBalanceObj);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockBalance(String httpNode, Integer blockNum, String blockHash) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/getblockbalance";
      JsonObject blockObj = new JsonObject();
      blockObj.addProperty("hash", blockHash);
      blockObj.addProperty("number", blockNum);
      blockObj.addProperty("visible", true);
      response = createConnect(requestUrl, blockObj);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static Long getBurnTrx(String httpNode) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/getburntrx";
      JsonObject blockObj = new JsonObject();
      response = createConnect(requestUrl, blockObj);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseResponseContent(response);
    return responseContent.getLong("burnTrxAmount");
  }

  /**
   * constructor.
   */
  public static Long getBurnTrxFromSolidity(String httpNode) {
    try {
      final String requestUrl = "http://" + httpNode + "/walletsolidity/getburntrx";
      JsonObject blockObj = new JsonObject();
      response = createConnect(requestUrl, blockObj);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseResponseContent(response);
    return responseContent.getLong("burnTrxAmount");
  }

  /**
   * constructor.
   */
  public static Long getBurnTrxFromPbft(String httpNode) {
    try {
      final String requestUrl = "http://" + httpNode + "/walletpbft/getburntrx";
      JsonObject blockObj = new JsonObject();
      response = createConnect(requestUrl, blockObj);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseResponseContent(response);
    return responseContent.getLong("burnTrxAmount");
  }




  /**
   * constructor.
   */
  public static HttpResponse getNowBlock(String httpNode) {
    return getNowBlock(httpNode, false);
  }

  /**
   * constructor.
   */
  public static HttpResponse getNowBlock(String httpNode, Boolean visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getnowblock";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static Long getNowBlockNum(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getnowblock";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return parseResponseContent(response).getJSONObject("block_header").getJSONObject("raw_data")
        .getLong("number");
  }

  /**
   * constructor.
   */
  public static Long getNowBlockNumOnSolidity(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/walletsolidity/getnowblock";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return parseResponseContent(response).getJSONObject("block_header").getJSONObject("raw_data")
        .getLong("number");
  }


  /**
   * constructor.
   */
  public static HttpResponse getNowBlockFromSolidity(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getnowblock";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getNowBlockFromPbft(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getnowblock";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static void waitToProduceOneBlock(String httpNode) {
    response = HttpMethed.getNowBlock(httpNode);
    responseContent = HttpMethed.parseResponseContent(response);
    responseContent = HttpMethed.parseStringContent(responseContent.get("block_header").toString());
    responseContent = HttpMethed.parseStringContent(responseContent.get("raw_data").toString());
    Integer currentBlockNum = Integer.parseInt(responseContent.get("number").toString());
    Integer nextBlockNum = 0;
    Integer times = 0;
    while (nextBlockNum <= currentBlockNum + 1 && times++ <= 10) {
      response = HttpMethed.getNowBlock(httpNode);
      responseContent = HttpMethed.parseResponseContent(response);
      responseContent = HttpMethed
          .parseStringContent(responseContent.get("block_header").toString());
      responseContent = HttpMethed.parseStringContent(responseContent.get("raw_data").toString());
      nextBlockNum = Integer.parseInt(responseContent.get("number").toString());
      try {
        Thread.sleep(1200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * constructor.
   */
  public static void waitToProduceOneBlockFromSolidity(String httpNode, String httpSolidityNode) {
    response = HttpMethed.getNowBlock(httpNode);
    responseContent = HttpMethed.parseResponseContent(response);
    responseContent = HttpMethed.parseStringContent(responseContent.get("block_header").toString());
    responseContent = HttpMethed.parseStringContent(responseContent.get("raw_data").toString());
    Integer currentBlockNum = Integer.parseInt(responseContent.get("number").toString());
    Integer nextBlockNum = 0;
    Integer times = 0;
    while (nextBlockNum <= currentBlockNum && times++ <= 3) {
      response = HttpMethed.getNowBlockFromSolidity(httpSolidityNode);
      responseContent = HttpMethed.parseResponseContent(response);
      responseContent = HttpMethed
          .parseStringContent(responseContent.get("block_header").toString());
      responseContent = HttpMethed.parseStringContent(responseContent.get("raw_data").toString());
      nextBlockNum = Integer.parseInt(responseContent.get("number").toString());
      try {
        Thread.sleep(3500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * constructor.
   */
  public static void waitToProduceOneBlockFromPbft(String httpNode, String httpSolidityNode) {
    response = HttpMethed.getNowBlock(httpNode);
    responseContent = HttpMethed.parseResponseContent(response);
    responseContent = HttpMethed.parseStringContent(responseContent.get("block_header").toString());
    responseContent = HttpMethed.parseStringContent(responseContent.get("raw_data").toString());
    Integer currentBlockNum = Integer.parseInt(responseContent.get("number").toString());
    Integer nextBlockNum = 0;
    Integer times = 0;
    while (nextBlockNum <= currentBlockNum && times++ <= 3) {
      response = HttpMethed.getNowBlockFromPbft(httpSolidityNode);
      responseContent = HttpMethed.parseResponseContent(response);
      responseContent = HttpMethed
          .parseStringContent(responseContent.get("block_header").toString());
      responseContent = HttpMethed.parseStringContent(responseContent.get("raw_data").toString());
      nextBlockNum = Integer.parseInt(responseContent.get("number").toString());
      try {
        Thread.sleep(3500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * constructor.
   */
  public static HttpResponse getBlockByNum(String httpNode, Integer blockNUm) {
    return getBlockByNum(httpNode, blockNUm, false);
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByNum(String httpNode, Integer blockNUm, Boolean visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getblockbynum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blockNUm);
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static Long getBlockByNumForResponse(String httpNode, Integer blockNUm, Integer times) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getblockbynum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blockNUm);
      Long duration = createConnectForResponse(requestUrl, userBaseObj2, times);
      return duration;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return -1L;
    }
  }


  /**
   * constructor.
   */
  public static HttpResponse getBlockByNumFromSolidity(String httpSolidityNode, Integer blockNum) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getblockbynum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blockNum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByNumFromPbft(String httpSolidityNode, Integer blockNum) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getblockbynum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", blockNum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getBlockByLimitNext(String httpNode, Integer startNum,
      Integer endNum) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getblockbylimitnext";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("startNum", startNum);
      userBaseObj2.addProperty("endNum", endNum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByLimitNextFromSolidity(String httpNode, Integer startNum,
      Integer endNum) {
    try {
      String requestUrl = "http://" + httpNode + "/walletsolidity/getblockbylimitnext";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("startNum", startNum);
      userBaseObj2.addProperty("endNum", endNum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByLimitNextFromPbft(String httpNode, Integer startNum,
      Integer endNum) {
    try {
      String requestUrl = "http://" + httpNode + "/walletpbft/getblockbylimitnext";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("startNum", startNum);
      userBaseObj2.addProperty("endNum", endNum);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getBlockByLastNum(String httpNode, Integer num) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getblockbylatestnum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", num);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByLastNum2(String httpNode, Integer num) {
    try {
      String requestUrl = "http://" + httpNode + "/walletsolidity/getblockbylatestnum2";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", num);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getBlockByLastNumFromSolidity(String httpNode, Integer num) {
    try {
      String requestUrl = "http://" + httpNode + "/walletsolidity/getblockbylatestnum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", num);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByLastNumFromPbft(String httpNode, Integer num) {
    try {
      String requestUrl = "http://" + httpNode + "/walletpbft/getblockbylatestnum";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("num", num);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getBlockById(String httpNode, String blockId) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getblockbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", blockId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByIdFromSolidity(String httpNode, String blockId) {
    try {
      String requestUrl = "http://" + httpNode + "/walletsolidity/getblockbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", blockId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBlockByIdFromPbft(String httpNode, String blockId) {
    try {
      String requestUrl = "http://" + httpNode + "/walletpbft/getblockbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", blockId);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getDelegatedResource(String httpNode, byte[] fromAddress,
      byte[] toAddress) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getdelegatedresource";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("fromAddress", ByteArray.toHexString(fromAddress));
      userBaseObj2.addProperty("toAddress", ByteArray.toHexString(toAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getDelegatedResourceFromSolidity(String httpSolidityNode,
      byte[] fromAddress, byte[] toAddress) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getdelegatedresource";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("fromAddress", ByteArray.toHexString(fromAddress));
      userBaseObj2.addProperty("toAddress", ByteArray.toHexString(toAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getDelegatedResourceFromPbft(String httpSolidityNode,
      byte[] fromAddress, byte[] toAddress) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getdelegatedresource";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("fromAddress", ByteArray.toHexString(fromAddress));
      userBaseObj2.addProperty("toAddress", ByteArray.toHexString(toAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getDelegatedResourceAccountIndex(String httpNode,
      byte[] queryAddress) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getdelegatedresourceaccountindex";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getDelegatedResourceAccountIndexFromSolidity(String httpSolidityNode,
      byte[] queryAddress) {
    try {
      String requestUrl =
          "http://" + httpSolidityNode + "/walletsolidity/getdelegatedresourceaccountindex";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getDelegatedResourceAccountIndexFromPbft(String httpSolidityNode,
      byte[] queryAddress) {
    try {
      String requestUrl =
          "http://" + httpSolidityNode + "/walletpbft/getdelegatedresourceaccountindex";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", ByteArray.toHexString(queryAddress));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse createConnect(String url) {
    return createConnect(url, null);
  }

  /**
   * constructor.
   */
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

  /**
   * constructor.
   */
  public static HttpResponse createConnectForShieldTrc20(String url, JSONObject requestBody) {
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
      response = httpClient.execute(httppost);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static Long createConnectForResponse(String url, JsonObject requestBody, Integer times) {
    try {

      Long start = 0L;
      Long end = 0L;
      Long duration = 0L;
      while (times-- > 0) {
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

        start = System.currentTimeMillis();
        response = httpClient.execute(httppost);
        /*        responseContent = HttpMethed.parseResponseContent(response);
        log.info(responseContent.toString());*/
        end = System.currentTimeMillis();
        duration = duration + end - start;
        httppost.releaseConnection();
      }
      return duration;
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return -1L;
    }
  }


  /**
   * constructor.
   */
  public static HttpResponse createConnect1(String url, JSONObject requestBody) {
    try {
      httpClient.getParams()
          .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
      httpClient.getParams()
          .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout * 10000);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout * 10000);
      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
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
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getAssetissueList(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getassetissuelist";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static Long getAssetIssueValue(String httpNode, byte[] accountAddress,
      String assetIssueId) {
    response = HttpMethed.getAccount(httpNode, accountAddress);
    responseContent = HttpMethed.parseResponseContent(response);
    JSONArray assetV2 = responseContent.getJSONArray("assetV2");
    if (assetV2 != null && assetV2.size() > 0) {
      for (int i = 0; i < assetV2.size(); i++) {
        String assetString = assetV2.get(i).toString();
        log.info("assetString:" + assetString);
        String assetKey = HttpMethed.parseStringContent(assetString).getString("key");
        if (assetKey.equals(assetIssueId)) {
          return HttpMethed.parseStringContent(assetString).getLong("value");
        }
      }
    }
    return 0L;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueListFromSolidity(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getassetissuelist";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getAssetIssueListFromPbft(String httpSolidityNode) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getassetissuelist";
      response = createConnect(requestUrl);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getPaginatedAssetissueList(String httpNode, Integer offset,
      Integer limit) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/getpaginatedassetissuelist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("offset", offset);
      userBaseObj2.addProperty("limit", limit);
      userBaseObj2.addProperty("visible", "true");
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getPaginatedAssetissueListFromSolidity(String httpSolidityNode,
      Integer offset, Integer limit) {
    try {
      String requestUrl =
          "http://" + httpSolidityNode + "/walletsolidity/getpaginatedassetissuelist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("offset", offset);
      userBaseObj2.addProperty("limit", limit);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getPaginatedAssetissueListFromPbft(String httpSolidityNode,
      Integer offset, Integer limit) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getpaginatedassetissuelist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("offset", offset);
      userBaseObj2.addProperty("limit", limit);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getPaginatedProposalList(String httpNode, Integer offset,
      Integer limit) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getpaginatedproposallist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("offset", offset);
      userBaseObj2.addProperty("limit", limit);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getPaginatedExchangeList(String httpNode, Integer offset,
      Integer limit) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getpaginatedexchangelist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("offset", offset);
      userBaseObj2.addProperty("limit", limit);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse updateSetting(String httpNode, byte[] ownerAddress,
      String contractAddress, Integer consumeUserResourcePercent, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updatesetting";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("consume_user_resource_percent", consumeUserResourcePercent);
      log.info(userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse updateEnergyLimit(String httpNode, byte[] ownerAddress,
      String contractAddress, Integer originEnergyLimit, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updateenergylimit";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      userBaseObj2.addProperty("origin_energy_limit", originEnergyLimit);
      log.info(userBaseObj2.toString());
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      log.info(transactionString);
      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse createAddress(String httpNode, String value) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/createaddress";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", str2hex(value));
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse generateAddress(String httpNode) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/generateaddress";
      JsonObject userBaseObj2 = new JsonObject();
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse validateAddress(String httpNode, String address) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/validateaddress";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", address);
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse easyTransfer(String httpNode, String value, byte[] toAddress,
      Long amount) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/easytransfer";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("toAddress", ByteArray.toHexString(toAddress));
      userBaseObj2.addProperty("passPhrase", str2hex(value));
      userBaseObj2.addProperty("amount", amount);
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse easyTransferByPrivate(String httpNode, String privateKey,
      byte[] toAddress, Long amount) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/easytransferbyprivate";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("privateKey", privateKey);
      userBaseObj2.addProperty("toAddress", ByteArray.toHexString(toAddress));
      userBaseObj2.addProperty("amount", amount);
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse easyTransferAsset(String httpNode, String value, byte[] toAddress,
      Long amount, String assetId) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/easytransferasset";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("toAddress", ByteArray.toHexString(toAddress));
      userBaseObj2.addProperty("passPhrase", str2hex(value));
      userBaseObj2.addProperty("amount", amount);
      userBaseObj2.addProperty("assetId", assetId);
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse easyTransferAssetByPrivate(String httpNode, String privateKey,
      byte[] toAddress, Long amount, String assetId) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/easytransferassetbyprivate";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("privateKey", privateKey);
      userBaseObj2.addProperty("toAddress", ByteArray.toHexString(toAddress));
      userBaseObj2.addProperty("amount", amount);
      userBaseObj2.addProperty("assetId", assetId);
      response = createConnect(requestUrl, userBaseObj2);
      log.info(userBaseObj2.toString());
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static void disConnect() {
    httppost.releaseConnection();
  }

  /**
   * constructor.
   */
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

  /**
   * constructor.
   */
  public static List<JSONObject> parseResponseContentArray(HttpResponse response) {
    try {
      String result = EntityUtils.toString(response.getEntity());
      StringEntity entity = new StringEntity(result, Charset.forName("UTF-8"));
      response.setEntity(entity);
      List<JSONObject> list = new ArrayList<JSONObject>();
      JSONArray objects = JSONArray.parseArray(result);
      for (int i = 0; i < objects.size(); i++) {
        list.add(objects.getJSONObject(i));
      }
      return list;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static JSONObject parseStringContent(String content) {
    try {
      JSONObject obj = JSONObject.parseObject(content);
      return obj;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * constructor.
   */
  public static void printJsonContent(JSONObject responseContent) {
    log.info("----------------------------Print JSON Start---------------------------");
    for (String str : responseContent.keySet()) {
      log.info(str + ":" + responseContent.get(str));
    }
    log.info("JSON content size are: " + responseContent.size());
    log.info("----------------------------Print JSON End-----------------------------");
  }

  /**
   * constructor.
   */
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


  /**
   * constructor.
   */
  public static HttpResponse clearABiGetTxid(String httpNode, byte[] ownerAddress,
      String contractAddress, String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/clearabi";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("contract_address", contractAddress);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      log.info(transactionString);

      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);

      log.info(transactionSignString);
      response = broadcastTransaction(httpNode, transactionSignString);


    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  

  /**
   * constructor.
   */
  /*public static void freedResource(String httpNode, byte[] fromAddress, byte[] toAddress,
      String fromKey) {
    long balance = HttpMethed.getBalance(httpNode, fromAddress);
    //System.out.println("剩余资源：" + balance);
    sendCoin(httpNode, fromAddress, toAddress, balance - 50000, fromKey);
    balance = HttpMethed.getBalance(httpNode, fromAddress);
    //System.out.println("之后资源：" + balance);
  }*/

  /**
   * constructor.
   */
  public static HttpResponse updateBrokerage(String httpNode, byte[] ownerAddress, Long brokerage,
      String fromKey) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updateBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      userBaseObj2.addProperty("brokerage", brokerage);
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse updateBrokerageOnVisible(String httpNode, byte[] ownerAddress,
      Long brokerage, String fromKey, String visible) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/updateBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("brokerage", brokerage);
      if (visible.equals("true")) {
        userBaseObj2.addProperty("owner_address", Base58.encode(ownerAddress));
      } else if (visible.equals("false")) {
        userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
      log.info("userBaseObj2:" + userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getReward(String httpNode, byte[] address) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getReward";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getRewardFromSolidity(String httpSolidityNode, byte[] address) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getReward";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getRewardFromPbft(String httpSolidityNode, byte[] address) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getReward";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getBrokerage(String httpNode, byte[] address) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBrokerageFromSolidity(String httpSolidityNode, byte[] address) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getRewardOnVisible(String httpNode, byte[] address, String visible) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/getReward";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("address", Base58.encode(address));
      } else if (visible.equals("false")) {
        userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      }
      userBaseObj2.addProperty("visible", visible);
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getRewardFromSolidityOnVisible(String httpSolidityNode, byte[] address,
      String visible) {
    try {
      final String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getReward";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("address", Base58.encode(address));
      } else if (visible.equals("false")) {
        userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      }
      userBaseObj2.addProperty("visible", visible);
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBrokerageOnVisible(String httpNode, byte[] address,
      String visible) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/getBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("address", Base58.encode(address));
      } else if (visible.equals("false")) {
        userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      }
      userBaseObj2.addProperty("visible", visible);
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBrokerageFromSolidityOnVisible(String httpSolidityNode,
      byte[] address, String visible) {
    try {
      final String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("address", Base58.encode(address));
      } else if (visible.equals("false")) {
        userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      }
      userBaseObj2.addProperty("visible", visible);
      log.info("userBaseObj2:" + userBaseObj2);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getBrokerageFromPbft(String httpSolidityNode, byte[] address) {
    try {
      final String requestUrl = "http://" + httpSolidityNode + "/walletpbft/getBrokerage";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("address", ByteArray.toHexString(address));
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static String marketSellAssetGetTxId(String httpNode, byte[] ownerAddress,
      String sellTokenId,
      Long sellTokenQuantity, String buyTokenId, Long buyTokenQuantity, String fromKey,
      String visible) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/marketsellasset";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("owner_address", Base58.encode(ownerAddress));
        userBaseObj2.addProperty("sell_token_id", sellTokenId);
        userBaseObj2.addProperty("buy_token_id", buyTokenId);
      } else {
        userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
        userBaseObj2.addProperty("sell_token_id", str2hex(sellTokenId));
        userBaseObj2.addProperty("buy_token_id", str2hex(buyTokenId));
      }
      userBaseObj2.addProperty("sell_token_quantity", sellTokenQuantity);
      userBaseObj2.addProperty("buy_token_quantity", buyTokenQuantity);
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
      System.out.println("userBaseObj2: " + userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      System.out.println("transactionString: " + transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseStringContent(transactionSignString);
    return responseContent.getString("txID");
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderById(String httpNode, String orderId, String visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getmarketorderbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", orderId);
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderByIdFromSolidity(String httpSolidityNode, String orderId,
      String visible) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getmarketorderbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", orderId);
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderByIdFromPbft(String httpPbftNode, String orderId,
      String visible) {
    try {
      String requestUrl = "http://" + httpPbftNode + "/walletpbft/getmarketorderbyid";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value", orderId);
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static String marketCancelOrder(String httpNode, byte[] ownerAddress, String orderId,
      String fromKey, String visible) {
    try {
      final String requestUrl = "http://" + httpNode + "/wallet/marketcancelorder";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("owner_address", Base58.encode(ownerAddress));
      } else {
        userBaseObj2.addProperty("owner_address", ByteArray.toHexString(ownerAddress));
      }
      userBaseObj2.addProperty("order_id", orderId);
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
      System.out.println("userBaseObj2: " + userBaseObj2);
      transactionString = EntityUtils.toString(response.getEntity());
      System.out.println("transactionString: " + transactionString);
      transactionSignString = gettransactionsign(httpNode, transactionString, fromKey);
      response = broadcastTransaction(httpNode, transactionSignString);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    responseContent = HttpMethed.parseStringContent(transactionSignString);
    return responseContent.getString("txID");
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderByAccount(String httpNode, byte[] ownerAddress,
      String visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getmarketorderbyaccount";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("value", Base58.encode(ownerAddress));
      } else {
        userBaseObj2.addProperty("value", ByteArray.toHexString(ownerAddress));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderByAccountFromSolidity(String httpSolidityNode,
      byte[] ownerAddress, String visible) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getmarketorderbyaccount";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("value", Base58.encode(ownerAddress));
      } else {
        userBaseObj2.addProperty("value", ByteArray.toHexString(ownerAddress));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderByAccountFromPbft(String httpPbftNode,
      byte[] ownerAddress, String visible) {
    try {
      String requestUrl = "http://" + httpPbftNode + "/walletpbft/getmarketorderbyaccount";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("value", Base58.encode(ownerAddress));
      } else {
        userBaseObj2.addProperty("value", ByteArray.toHexString(ownerAddress));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketPairList(String httpNode, String visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getmarketpairlist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketPairListFromSolidity(String httpSolidityNode,
      String visible) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getmarketpairlist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketPairListFromPbft(String httpPbftNode, String visible) {
    try {
      String requestUrl = "http://" + httpPbftNode + "/walletpbft/getmarketpairlist";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderListByPair(String httpNode, String sellTokenId,
      String buyTokenId, String visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getmarketorderlistbypair";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("sell_token_id", sellTokenId);
        userBaseObj2.addProperty("buy_token_id", buyTokenId);
      } else {
        userBaseObj2.addProperty("sell_token_id", str2hex(sellTokenId));
        userBaseObj2.addProperty("buy_token_id", str2hex(buyTokenId));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderListByPairFromSolidity(String httpSolidityNode,
      String sellTokenId,
      String buyTokenId, String visible) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getmarketorderlistbypair";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("sell_token_id", sellTokenId);
        userBaseObj2.addProperty("buy_token_id", buyTokenId);
      } else {
        userBaseObj2.addProperty("sell_token_id", str2hex(sellTokenId));
        userBaseObj2.addProperty("buy_token_id", str2hex(buyTokenId));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketOrderListByPairFromPbft(String httpPbftNode,
      String sellTokenId, String buyTokenId, String visible) {
    try {
      String requestUrl = "http://" + httpPbftNode + "/walletpbft/getmarketorderlistbypair";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("sell_token_id", sellTokenId);
        userBaseObj2.addProperty("buy_token_id", buyTokenId);
      } else {
        userBaseObj2.addProperty("sell_token_id", str2hex(sellTokenId));
        userBaseObj2.addProperty("buy_token_id", str2hex(buyTokenId));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketPriceByPair(String httpNode, String sellTokenId,
      String buyTokenId, String visible) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getmarketpricebypair";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("sell_token_id", sellTokenId);
        userBaseObj2.addProperty("buy_token_id", buyTokenId);
      } else {
        userBaseObj2.addProperty("sell_token_id", str2hex(sellTokenId));
        userBaseObj2.addProperty("buy_token_id", str2hex(buyTokenId));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketPriceByPairFromSolidity(String httpSolidityNode,
      String sellTokenId,
      String buyTokenId, String visible) {
    try {
      String requestUrl = "http://" + httpSolidityNode + "/walletsolidity/getmarketpricebypair";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("sell_token_id", sellTokenId);
        userBaseObj2.addProperty("buy_token_id", buyTokenId);
      } else {
        userBaseObj2.addProperty("sell_token_id", str2hex(sellTokenId));
        userBaseObj2.addProperty("buy_token_id", str2hex(buyTokenId));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static HttpResponse getMarketPriceByPairFromPbft(String httpPbftNode,
      String sellTokenId, String buyTokenId, String visible) {
    try {
      String requestUrl = "http://" + httpPbftNode + "/walletpbft/getmarketpricebypair";
      JsonObject userBaseObj2 = new JsonObject();
      if (visible.equals("true")) {
        userBaseObj2.addProperty("sell_token_id", sellTokenId);
        userBaseObj2.addProperty("buy_token_id", buyTokenId);
      } else {
        userBaseObj2.addProperty("sell_token_id", str2hex(sellTokenId));
        userBaseObj2.addProperty("buy_token_id", str2hex(buyTokenId));
      }
      userBaseObj2.addProperty("visible", visible);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  /**
   * constructor.
   */
  public static int getTransactionPendingSize(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/getpendingsize";
      JsonObject userBaseObj2 = new JsonObject();
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return 0;
    }
    responseContent = HttpMethed.parseResponseContent(response);
    return responseContent.getInteger("pendingSize");
  }

  /**
   * constructor.
   */
  public static HttpResponse getTransactionListFromPending(String httpNode) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/gettransactionlistfrompending";
      JsonObject userBaseObj2 = new JsonObject();
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }


  /**
   * constructor.
   */
  public static HttpResponse getTransactionFromPending(String httpNode,String txid) {
    try {
      String requestUrl = "http://" + httpNode + "/wallet/gettransactionfrompending";
      JsonObject userBaseObj2 = new JsonObject();
      userBaseObj2.addProperty("value",txid);
      response = createConnect(requestUrl, userBaseObj2);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }





}
