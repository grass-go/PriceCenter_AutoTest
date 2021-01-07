package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.protobuf.ByteString;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.testng.annotations.Test;
import org.tron.common.utils.Sha256Hash;
import org.tron.tronj.api.GrpcAPI.EmptyMessage;
import org.tron.tronj.api.WalletGrpc;
import org.tron.tronj.proto.Chain.Transaction;
import org.tron.tronj.proto.Contract.AccountCreateContract;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;
import org.tron.tronj.client.TronClient;
import org.tron.common.crypto.ECKey;
import org.tron.common.crypto.ECKey.ECDSASignature;

public class createMutisignTransaction extends TronlinkBase {
  private JSONObject responseContent;
  private JSONObject targetContent;
  public static HttpResponse response;
  private Map<String,String> header = new HashMap<>();
  public static TronClient  nileClient1;
  public static TronClient  nileClient2;
  public static String key1 = "ed64bc406f8d7bab96e05e697b59070fb0612be50337ab62b63b9feb0c46bafe";
  public static String key2 = "2667b13c6c7f83898a1dfbd12960392c2cb49886fae776cdc8e530c7a6907ec5";
  public static String address1Str = "TYiZ8BkyYPBGwDYBfjnvhLqWyqSX2U8mwB";
  public static String address2Str = "THVAp1ywPLSSqaZQyFdfNvKTCMycF5KG1K";
  public static HttpClient httpClient;
  public static HttpPost httppost;


  @Test(enabled = false,description = "multi sign accountCreate")
  public void accountCreate() throws Exception{
    nileClient1 = TronClient.ofNile(key1);
    nileClient2 = TronClient.ofNile(key2);
    HttpResponse res;


    byte[] address1= nileClient1.getAccount(address1Str).getAddress().toByteArray();
    byte[] address2= nileClient2.getAccount(address2Str).getAddress().toByteArray();
    byte[] lowBalAddress = nileClient1.blockingStub.generateAddress(EmptyMessage.newBuilder().build()).getAddress().getBytes();

//    Protocol.Transaction transaction = PublicMethedForMutiSign
//            .sendcoin2(address2, 1000_000, address1, key1, blockingStubFull);

    Transaction transaction = createAccount7(address1, lowBalAddress,
        key1, nileClient1.blockingStub);
    //System.out.println("-----111111  "+ JsonFormat.printToString(transaction));
    Transaction.Builder transactionBuilder = Transaction.newBuilder();
    Transaction transaction1 = addTransactionSignWithPermissionId(
        transaction, key2, 3, nileClient1.blockingStub);
    //logger.info("-----2222  "+JsonFormat.printToString(transaction1));
    JSONObject object = new JSONObject();
    object.put("address",address1);
    object.put("netType","main_net");
    object.put("transaction",JSONObject.parse(org.tron.core.services.http.JsonFormat.printToString(transaction1)));
    String url="https://testlist.tronlink.org/api/wallet/multi/transaction";
//    String url="https://niletest.tronlink.org/api/wallet/multi/transaction";
    res = createConnect666(url,object);
    System.out.println(res.getStatusLine().getStatusCode());
    System.out.println(EntityUtils.toString(res.getEntity()));
  }

  public static Transaction createAccount7(byte[] ownerAddress, byte[] newAddress, String priKey,
      WalletGrpc.WalletBlockingStub blockingStubFull) {
    byte[] owner = ownerAddress;
    AccountCreateContract.Builder builder = AccountCreateContract.newBuilder();
    builder.setOwnerAddress(ByteString.copyFrom(owner));
    builder.setAccountAddress(ByteString.copyFrom(newAddress));
    AccountCreateContract contract = builder.build();
    Transaction transaction = blockingStubFull.createAccount(contract);
    return transaction;
  }


  public static Transaction addTransactionSignWithPermissionId(Transaction transaction,
      String priKey, int permissionId, WalletGrpc.WalletBlockingStub blockingStubFull) {
    //Wallet.setAddressPreFixByte(CommonConstant.ADD_PRE_FIX_BYTE_MAINNET);
    ECKey temKey = null;
    try {
      BigInteger priK = new BigInteger(priKey, 16);
      temKey = ECKey.fromPrivate(priK);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //transaction = setPermissionId(transaction, permissionId);
    Transaction.raw.Builder raw = transaction.getRawData().toBuilder();
    Transaction.Contract.Builder contract = raw.getContract(0).toBuilder()
        .setPermissionId(permissionId);
    raw.clearContract();
    raw.addContract(contract);
    transaction = transaction.toBuilder().setRawData(raw).build();


    Transaction.Builder transactionBuilderSigned = transaction.toBuilder();
    byte[] hash = Sha256Hash.hash(true, transaction.getRawData().toByteArray());
    ECKey ecKey = temKey;
    ECDSASignature signature = ecKey.sign(hash);
    ByteString bsSign = ByteString.copyFrom(signature.toByteArray());
    transactionBuilderSigned.addSignature(bsSign);
    transaction = transactionBuilderSigned.build();
    return transaction;
  }

  /**
   * constructor.
   */
  public static HttpResponse createConnect666(String url, JSONObject requestBody) {
    try {
      httpClient.getParams()
          .setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
      httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
      httppost = new HttpPost(url);
      httppost.setHeader("Content-type", "application/json; charset=utf-8");
      httppost.setHeader("Connection", "Close");
      httppost.addHeader("Lang","1");
      httppost.addHeader("Version","3.7.0");
      httppost.addHeader("DeviceID","1111111111");
      httppost.addHeader("chain","MainChain");
      httppost.addHeader("packageName","com.tronlinkpro.wallet");
      httppost.addHeader("System","Android");
      if (requestBody != null) {
        StringEntity entity = new StringEntity(requestBody.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        System.out.println("***********");
        httppost.setEntity(entity);
      }
      System.out.println(httppost.toString());
      response = httpClient.execute(httppost);
    } catch (Exception e) {
      e.printStackTrace();
      httppost.releaseConnection();
      return null;
    }
    return response;
  }

  
}
