package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.spongycastle.util.encoders.Hex;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.tron.api.GrpcAPI;
import org.tron.api.WalletGrpc;
import org.tron.common.utils.Base58;
import org.tron.common.utils.Commons;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.*;
import tron.common.TronlinkApiList;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CreateMultiTransactionPerf {
    private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;
    private String fullnode = "47.252.19.181:50051";  //线上
    private ManagedChannel channelFull = null;
    HttpResponse res;
    private JSONObject responseContent;
    String key1="c21f0c6fdc467f4ae7dc4c1a0b802234a930bff198ce34fde6850b0afd383cf5"; //线上
//    byte[] address1=TronlinkApiList.getFinalAddress(key1);

    String address158= "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
    byte[] address1 = Commons.decode58Check(address158);
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2=TronlinkApiList.getFinalAddress(key2);
    String address258=Base58.encode(address2);

    String quince58 = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
    byte[] quince = Commons.decode58Check(quince58);
    String quincekey = "b47e686119f2236f38cd0e8a4fe20f8a7fc5cb4284d36131f447c63857e3dac9";
    String wqq1key = "8d5c18030466b6ab0e5367154d15c4f6cb46d2fb56a0b552e017d183abd8c255";
    byte[] wqq1 = TronlinkApiList.getFinalAddress(wqq1key);
    String wqq158 =Base58.encode(wqq1);
    String liuyue58 = "TFDP1vFeSYPT6FUznL7zUjhg5X7p2AA8vw";
    byte[] liuyue = Commons.decode58Check(liuyue58);

    /**
     * constructor.
     */
    @BeforeClass(enabled = true)
    public void beforeClass() {

        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
    }

    @Test(enabled = true,invocationCount = 110, description = "send coin 100 times")
    public void sendCoinDirectely() {
        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext(true).build();
        blockingStubFull = WalletGrpc.newBlockingStub(channelFull);

        TronlinkApiList.sendcoinDirectely(liuyue, 22L, wqq1, wqq1key,
                        blockingStubFull);
    }

    @Test(enabled = true,invocationCount = 4, description = "nulti sign send coin")
    public void sendCoin() {
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(wqq1, 500_000, quince, blockingStubFull);
        log.info("-----111111  "+ JsonFormat.printToString(transaction));

        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = true,invocationCount = 5,description = "multi sign freeze balandce")
    public void freezeBalandce() throws Exception{
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        ByteString byteAddreess = ByteString.copyFrom(quince);
        builder.setOwnerAddress(byteAddreess).setFrozenBalance(4000000)
                .setFrozenDuration(3).setResourceValue(0);
        BalanceContract.FreezeBalanceContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.freezeBalance(contract);
        log.info("0000 "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----111  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    //modify coin id before go prod
    //modify permission id before go prod
    @Test(enabled = true,invocationCount = 5,description = "multi sign transfer asset")
    public void transferTrc10() throws Exception{
        AssetIssueContractOuterClass.TransferAssetContract.Builder builder = AssetIssueContractOuterClass.TransferAssetContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(wqq1);
        ByteString bsName = ByteString.copyFrom("1000323".getBytes());
        ByteString bsOwner = ByteString.copyFrom(quince);
        builder.setToAddress(bsTo);
        builder.setAssetName(bsName);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(1);

        AssetIssueContractOuterClass.TransferAssetContract contract = builder.build();
        log.info("-----0000  "+JsonFormat.printToString(contract));
        Protocol.Transaction transaction = blockingStubFull.transferAsset(contract);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = true,invocationCount = 5,description = "nulti sign transfer trc20")
    public void transferTrc20() throws Exception{

        //String contractAddress = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        String contractAddress = "TT8vc3zKCmGCUryYWLtFvu5PqAyYoZ3KMh";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "0000000000000000000000000c497701e37f11b042bdc7aabfc0cd5d45f7a0c70000000000000000000000000000000000000000000000000000000000000001";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd,"transfer(address,uint256)",args,true,0,maxFeeLimit,"0",0L,
                quince,blockingStubFull);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, wqq1key, 4, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",wqq158);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));



        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }

        /**
         * constructor.
         */
        @AfterClass
        public void shutdown () throws InterruptedException {
            if (channelFull != null) {
                channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            }
        }

    }