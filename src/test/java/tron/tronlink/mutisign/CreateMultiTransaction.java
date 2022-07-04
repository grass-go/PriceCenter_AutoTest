package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
//import com.sun.istack.internal.NotNull;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.testng.annotations.*;
import org.tron.common.utils.Base58;
import org.tron.common.utils.Commons;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.AssetIssueContractOuterClass;
import org.tron.protos.contract.BalanceContract;
import tron.common.TronlinkApiList;
import tron.tronlink.base.TronlinkBase;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CreateMultiTransaction {
    private org.tron.api.WalletGrpc.WalletBlockingStub blockingStubFull = null;
    private String fullnode = "47.75.245.225:50051";  //线上
    private ManagedChannel channelFull = null;
//    HttpResponse res;
    private JSONObject responseContent;
//    String key1="c21f0c6fdc467f4ae7dc4c1a0b802234a930bff198ce34fde6850b0afd383cf5"; //线上
//    byte[] address1=TronlinkApiList.getFinalAddress(key1);

//    String address158= "TY9touJknFcezjLiaGTjnH1dUHiqriu6L8";
//    byte[] address1 = Commons.decode58Check(address158);
    String address158;
    byte[] address1;
    String priKey1;
    String priKey2;
    String key2 = "7ef4f6b32643ea063297416f2f0112b562a4b3dac2c960ece00a59c357db3720";//线上
    byte[] address2=TronlinkApiList.getFinalAddress(key2);
    String address258=Base58.encode(address2);
    String toAddress;
    byte[] toAddressByte;

    /**
     * constructor.
     */
    @BeforeClass(enabled = true)
    @Parameters({"fullnode", "firstAddress", "secondAddress", "firstPriKey", "secondPriKey","toAddress"})
    public void beforeClass(String fullnode, String address158, String address258, String firstPriKey,String secondPriKey, String toAddress) {
        TronlinkBase.tronlinkUrl = "http://101.201.66.150";
        initEnv(fullnode, address158, address258, firstPriKey,secondPriKey, toAddress);
        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
    }

    // 初始化环境
    private void initEnv(String fullnode, String address158, String address258, String firstPriKey,String secondPriKey, String toAddress){
        this.fullnode = fullnode;
        this.address158 = address158;
        this.address258 = address258;
        address1 = Commons.decode58Check(address158);
        address2 = Commons.decode58Check(address258);
        priKey1 = firstPriKey;
        priKey2 = secondPriKey;
        this.toAddress = toAddress;
        toAddressByte = Commons.decode58Check(toAddress);
    }

    /**
     * invocationCount设定的是这个方法的执行次数.
     * threadPoolSize 这个属性表示的是开启线程数的多少.
     */
    @Test(enabled = true,invocationCount = 1, threadPoolSize = 1 ,description = "multi sign performance test，A and B control account of C")
    public void createMultiSign(){
        log.info("address1 : " + address158 + " address2 = " + address258);
        // 发起一笔交易
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(toAddressByte, 600_000, address1, blockingStubFull);
        log.info("send coin finished!  " + JsonFormat.printToString(transaction));

        // 第一个用户签名
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, priKey1, 3, blockingStubFull);
        log.info("key1 sign finished!  " + JsonFormat.printToString(transaction1));
        // 广播 & 断言
        HttpResponse res;
        res = postTransction(address158, transaction1);
        assertResponse(res);

        // 第二个用户签名
        Protocol.Transaction transaction2 = TronlinkApiList.addTransactionSignWithPermissionIdAndExpiredTime(
                transaction1, priKey2, 3, blockingStubFull);
        log.info("key2 sign finished!  " + JsonFormat.printToString(transaction2));
        // 广播 & 断言
        res = postTransction(address258, transaction2);
        assertResponse(res);

        log.info("test finished!");
    }

    private void assertResponse( HttpResponse res){
//        if (res == null) {
//            log.error("res is null !!");
//            return;
//        }
        Assert.assertNotEquals(res, null);
        // 结果校验
        log.info( res.toString());
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0, responseContent.getIntValue("code"));
    }


    private HttpResponse postTransction(String address, Protocol.Transaction transaction) {
        // 开始广播
        JSONObject object = new JSONObject();
        object.put("address", address);
        object.put("netType", "main_net");
        object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction)));
        TronlinkApiList.HttpNode = "http://101.201.66.150";
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);
        return res;
    }

    @Test(enabled = false,description = "multi sign send coin")
    public void sendCoin() {
        for (int i = 1;i < 200;i ++) {
            Protocol.Transaction transaction = TronlinkApiList
                    .sendcoin(toAddressByte, 500_000, address1, blockingStubFull);
            log.info("-----111111  " + JsonFormat.printToString(transaction));

            Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                    transaction, priKey1, 3, blockingStubFull);
            log.info("-----2222  " + JsonFormat.printToString(transaction1));

            JSONObject object = new JSONObject();
            object.put("address", address158);
            object.put("netType", "main_net");
            object.put("transaction", JSONObject.parse(JsonFormat.printToString(transaction1)));
            TronlinkApiList.HttpNode = "http://101.201.66.150";
            HttpResponse res;
            res = TronlinkApiList.multiTransaction(object);
            Assert.assertEquals(200, res.getStatusLine().getStatusCode());
            responseContent = TronlinkApiList.parseJsonObResponseContent(res);
            Assert.assertEquals(0, responseContent.getIntValue("code"));
        }
    }

    @Test(enabled = false,description = "multi sign freeze balandce")
    public void freezeBalandce() throws Exception{
        BalanceContract.FreezeBalanceContract.Builder builder = BalanceContract.FreezeBalanceContract.newBuilder();
        ByteString byteAddreess = ByteString.copyFrom(address1);
        builder.setOwnerAddress(byteAddreess).setFrozenBalance(1000000)
                .setFrozenDuration(3).setResourceValue(0);
        BalanceContract.FreezeBalanceContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.freezeBalance(contract);
        log.info("0000 "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 2, blockingStubFull);
        log.info("-----111  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = false,description = "multi sign transfer asset")
    public void transferTrc10() throws Exception{
        AssetIssueContractOuterClass.TransferAssetContract.Builder builder = AssetIssueContractOuterClass.TransferAssetContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(address2);
        ByteString bsName = ByteString.copyFrom("1002000".getBytes());
        ByteString bsOwner = ByteString.copyFrom(address1);
        builder.setToAddress(bsTo);
        builder.setAssetName(bsName);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(1);

        AssetIssueContractOuterClass.TransferAssetContract contract = builder.build();
        log.info("-----0000  "+JsonFormat.printToString(contract));
        Protocol.Transaction transaction = blockingStubFull.transferAsset(contract);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 2, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));
        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);

        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));
    }

    @Test(enabled = false,description = "nulti sign transfer trc20")
    public void transferTrc20() throws Exception{

        String contractAddress = "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t";
        byte[] contractAdd = Commons.decode58Check(contractAddress);
        String args = "000000000000000000000000ebae50590810b05d4b403f13766f213518edef650000000000000000000000000000000000000000000000000000000000000001";
        Long maxFeeLimit = 1000000000L;
        Protocol.Transaction transaction = TronlinkApiList.triggerContract(contractAdd,"transfer(address,uint256)",args,true,0,maxFeeLimit,"0",0L,
                address1,blockingStubFull);
        log.info("-----111111  "+JsonFormat.printToString(transaction));
        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction, key2, 3, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();
        object.put("address",address258);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        HttpResponse res;
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));


    }

    /**
     * constructor.
     */
    @AfterClass
    public void shutdown() throws InterruptedException {
        if (channelFull != null) {
            channelFull.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
