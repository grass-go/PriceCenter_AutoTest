package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.junit.Assert;
import org.spongycastle.util.encoders.Hex;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.tron.api.GrpcAPI;
import org.tron.api.WalletGrpc;
import org.tron.common.crypto.ECKey;
import org.tron.common.parameter.CommonParameter;
import org.tron.common.utils.*;
import org.tron.core.Wallet;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.Protocol;
import org.tron.protos.contract.*;
import tron.common.TronlinkApiList;

import java.io.*;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.Date;

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
    String wqq158_2 = encode58Check(wqq1);
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

    @Test(enabled = true, description = "Test in eth: checkpoint can proposed to rootChainProxy contract normally")
    public void testlog() throws IOException {
        log.info("wqq");

    }

    @Test(enabled = true,description = "Set Permission ForMultiUser")
    public void setPermissionForMultiUserTest() throws IOException {

        File file = new File("/Users/wqq/Text/test_mulSign_v4.6.0/testAccount2.txt");
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file));
        String tempString = null;
        String curUser = null;
        String curUserKey = null;
        String accountPermissionJson = null;
        int line = 1;
        //while ((tempString = reader.readLine()) != null) {
        while (line<2)  {
            tempString = reader.readLine();
            log.info("line"+line+":"+tempString);
            String[] userinfo = tempString.split("\t");
            curUser = userinfo[0];
            byte[] curUserBytes = Commons.decode58Check(curUser);
            curUserKey = userinfo[1];

            TronlinkApiList.sendcoinDirectely(curUserBytes, 150000000L, quince, quincekey,
                    blockingStubFull);
            waitProduceNextBlock(blockingStubFull);

            //accountPermissionJson = "{\"owner_permission\":{\"type\":0,\"permission_name\":\"\",\"threshold\":1,\"keys\":[{\"address\":\"" + curUser + "\",\"weight\":1}]},"
            //        + "\"active_permissions\":[{\"type\":2,\"permission_name\":\"active1\",\"threshold\":1,\"operations\":\"7fff1fc0033e3b00000000000000000000000000000000000000000000000000\",\"keys\":[{\"address\":\"TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo\",\"weight\":1}]},{\"type\":2,\"permission_name\":\"AAmodified2\",\"threshold\":60,\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\",\"keys\":[{\"address\":\"TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo\",\"weight\":30},{\"address\":\"TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t\",\"weight\":20},{\"address\":\"TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ\",\"weight\":20}]}]}";

            accountPermissionJson = "{\"owner_permission\":{\"type\":0,\"permission_name\":\"\",\"threshold\":1,\"keys\":[{\"address\":\"" + curUser + "\",\"weight\":1}]},"
                    + "\"active_permissions\":[{\"type\":2,\"permission_name\":\"active1\",\"threshold\":1,\"operations\":\"7fff1fc0033e3b00000000000000000000000000000000000000000000000000\",\"keys\":[{\"address\":\"TQWnq5DpQ87LmE2BBAcia1fJ1Z2hP1d3cg\",\"weight\":1}]},{\"type\":2,\"permission_name\":\"AAmodified2\",\"threshold\":41,\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\",\"keys\":[{\"address\":\"TQWnq5DpQ87LmE2BBAcia1fJ1Z2hP1d3cg\",\"weight\":11},{\"address\":\"TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ\",\"weight\":30}]}]}";



            log.info(accountPermissionJson);
            GrpcAPI.Return response = TronlinkApiList.accountPermissionUpdateForResponse(
                    accountPermissionJson, curUserBytes, curUserKey, blockingStubFull);
            Assert.assertTrue(response.getResult());


            line++;
        }
       reader.close();


    }


    public static boolean waitProduceNextBlock(WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte((byte)0x41);
        Protocol.Block currentBlock = blockingStubFull.getNowBlock(GrpcAPI.EmptyMessage.newBuilder().build());
        final Long currentNum = currentBlock.getBlockHeader().getRawData().getNumber();

        Protocol.Block nextBlock = blockingStubFull.getNowBlock(GrpcAPI.EmptyMessage.newBuilder().build());
        Long nextNum = nextBlock.getBlockHeader().getRawData().getNumber();

        Integer wait = 0;
        log.info(
                "Block num is " + currentBlock.getBlockHeader().getRawData().getNumber());
        while (nextNum <= currentNum + 1 && wait <= 45) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //logger.info("Wait to produce next block");
            nextBlock = blockingStubFull.getNowBlock(GrpcAPI.EmptyMessage.newBuilder().build());
            nextNum = nextBlock.getBlockHeader().getRawData().getNumber();
            if (wait == 45) {
                log.info("These 45 second didn't produce a block,please check.");
                return false;
            }
            wait++;
        }
        log.info("quit normally");
        return true;
    }

    @Test(enabled = true,description = "Set quince Permission")
    public void setPermissionTest() {
        String accountPermissionJson = "{\"owner_permission\":{\"type\":0,\"permission_name\":\"\","
                + "\"threshold\": 1,\"keys\":["
                + "{\"address\":\""+quince58+"\",\"weight\":1}]},"
                + "\"witness_permission\":{\"type\":1,\"permission_name\":\"\","
                + "\"threshold\": 1,\"keys\":["
                + "{\"address\":\""+quince58+"\",\"weight\":1}]},"
                + "\"active_permissions\":["
                + "{\"type\":2,\"permission_name\":\"active1\",\"threshold\":1,"
                + "\"operations\":\"7fff1fc0033e3b00000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + quince58 + "\",\"weight\":1}]},"
                + "{\"type\":2,\"permission_name\":\"AAmodified2\",\"threshold\":60,"
                + "\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo" + "\",\"weight\":30},"
                + "{\"address\":\"" + "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ" + "\",\"weight\":20},"
                + "{\"address\":\"" + "TPbpoVgFDzkE7Sio9mZWKEC4rv3xWRL22C" + "\",\"weight\":20}"
                +"]},"
                + "{\"type\":2,\"permission_name\":\"all3\",\"threshold\":50,"
                + "\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo" + "\",\"weight\":1},"
                + "{\"address\":\"" + "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t" + "\",\"weight\":49}"
                +"]},"
                + "{\"type\":2,\"permission_name\":\"all4\",\"threshold\":50,"
                + "\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo" + "\",\"weight\":1},"
                + "{\"address\":\"" + "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t" + "\",\"weight\":49},"
                + "{\"address\":\"" + "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ" + "\",\"weight\":20}"
                +"]},"
                + "{\"type\":2,\"permission_name\":\"all5\",\"threshold\":120,"
                + "\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo" + "\",\"weight\":2},"
                + "{\"address\":\"" + "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t" + "\",\"weight\":28},"
                + "{\"address\":\"" + "TPmQw8dDLdrH8bTN3u9H1A2XCanbqyE533" + "\",\"weight\":30},"
                + "{\"address\":\"" + "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ" + "\",\"weight\":60}"
                +"]},"
                + "{\"type\":2,\"permission_name\":\"all6\",\"threshold\":50,"
                + "\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo" + "\",\"weight\":3},"
                + "{\"address\":\"" + "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t" + "\",\"weight\":47}"
                +"]},"
                + "{\"type\":2,\"permission_name\":\"all7\",\"threshold\":50,"
                + "\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo" + "\",\"weight\":4},"
                + "{\"address\":\"" + "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t" + "\",\"weight\":46}"
                +"]},"
                + "{\"type\":2,\"permission_name\":\"all8\",\"threshold\":200,"
                + "\"operations\":\"77ff07c0027e0300000000000000000000000000000000000000000000000000\","
                + "\"keys\":["
                + "{\"address\":\"" + "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo" + "\",\"weight\":41},"
                + "{\"address\":\"" + "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t" + "\",\"weight\":39},"
                + "{\"address\":\"" + "TQpb6SWxCLChged64W1MUxi2aNRjvdHbBZ" + "\",\"weight\":40},"
                + "{\"address\":\"" + "TPbpoVgFDzkE7Sio9mZWKEC4rv3xWRL22C" + "\",\"weight\":40},"
                + "{\"address\":\"" + "TPmQw8dDLdrH8bTN3u9H1A2XCanbqyE533" + "\",\"weight\":40}"
                +"]}"
                + "]}";
        log.info(accountPermissionJson);
        GrpcAPI.Return response = TronlinkApiList.accountPermissionUpdateForResponse(
                accountPermissionJson, quince, quincekey, blockingStubFull);
        Assert.assertTrue(response.getResult());

    }


    @Test(enabled = true, description = "generate 4000 users")
    public void GenerateUsers() {
        for(int i=0; i<4000; i++) {
            ECKey curECKey = new ECKey(Utils.getRandom());
            byte[] curAddress_byte = curECKey.getAddress();
            String curKey = ByteArray.toHexString(curECKey.getPrivKeyBytes());
            String address_base58 = encode58Check(curAddress_byte);
            String address_hex = ByteArray.toHexString(curAddress_byte);
            log.info(address_base58+" : "+curKey+" : "+address_hex);
        }
    }



    @Test(enabled = true, threadPoolSize = 1,invocationCount = 1, description = "send coins to perf users")
    public void sendCoinToPerfUsers() {
        File file = new File("/Users/wqq/Perf/perf-msg/data/AccountActive.txt");
        String fromAddress = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
        byte[] fromAddress_byte = Commons.decode58Check(fromAddress);
        String fromKey = "b47e686119f2236f38cd0e8a4fe20f8a7fc5cb4284d36131f447c63857e3dac9";
                BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            Date date = new Date();
            log.info("start date:" + date);
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                String[] accounts = tempString.split("\t");
                String curAddress = accounts[0];
                byte[] curAddress_byte = Commons.decode58Check(curAddress);

                TronlinkApiList.sendcoinDirectely(curAddress_byte, 1L, fromAddress_byte, fromKey,
                        blockingStubFull);
                Thread.sleep(100);
                line++;
            }
            log.info("Total transfer times:"+line);
            Date enddate = new Date();
            log.info("end date:" + enddate);
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    @Test(enabled = true, description = "get 4000 users account")
    public void get4000UsersAccount() {
        File file = new File("/Users/wqq/Perf/perf-msg/data/accountInfo.txt");
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                Thread.sleep(500);
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                String[] accounts = tempString.split("\t");
                String curAddress = accounts[0];
                byte[] curAddress_byte = Commons.decode58Check(curAddress);
                String curAddressKey = accounts[1];

                //channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext(true).build();
                //blockingStubFull = WalletGrpc.newBlockingStub(channelFull);

                Protocol.Account accountinfo = queryAccount(curAddressKey, blockingStubFull);
                Long curBalance = accountinfo.getBalance();
                log.info("curAddress:"+curAddress+" curbalance:"+curBalance);
                //TronlinkApiList.sendcoinDirectely(curAddress_byte, 1L, fromAddress_byte, fromKey,
                //        blockingStubFull);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    public static Protocol.Account queryAccount(String priKey,
                                                WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte((byte) 0x41);
        byte[] address;
        ECKey temKey = null;
        try {
            BigInteger priK = new BigInteger(priKey, 16);
            temKey = ECKey.fromPrivate(priK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ECKey ecKey = temKey;
        if (ecKey == null) {
            String pubKey = loadPubKey(); //04 PubKey[128]
            if (StringUtils.isEmpty(pubKey)) {
                log.warn("Warning: QueryAccount failed, no wallet address !!");
                return null;
            }
            byte[] pubKeyAsc = pubKey.getBytes();
            byte[] pubKeyHex = Hex.decode(pubKeyAsc);
            ecKey = ECKey.fromPublicOnly(pubKeyHex);
        }
        return grpcQueryAccount(ecKey.getAddress(), blockingStubFull);
    }
    public static String loadPubKey() {
        Wallet.setAddressPreFixByte((byte) 0x41);
        char[] buf = new char[0x100];
        return String.valueOf(buf, 32, 130);
    }
    public static Protocol.Account grpcQueryAccount(byte[] address,
                                                    WalletGrpc.WalletBlockingStub blockingStubFull) {
        Wallet.setAddressPreFixByte((byte) 0x41);
        ByteString addressBs = ByteString.copyFrom(address);
        Protocol.Account request = Protocol.Account.newBuilder().setAddress(addressBs).build();
        return blockingStubFull.getAccount(request);
    }

    @Test(enabled = true,invocationCount = 1, description = "send coin 100 times")
    public void sendCoinDirectely() {
        channelFull = ManagedChannelBuilder.forTarget(fullnode).usePlaintext(true).build();
        blockingStubFull = WalletGrpc.newBlockingStub(channelFull);

        TronlinkApiList.sendcoinDirectely(wqq1, 33L, quince, quincekey,
                        blockingStubFull);
    }

    @Test(enabled = true,invocationCount = 1, description = "multi sign send coin")
    public void sendCoin() throws InvalidProtocolBufferException {
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(wqq1, 500_000, quince, blockingStubFull);
        log.info("-----111111  "+ JsonFormat.printToString(transaction));
        String transactionHex = ByteArray.toHexString(transaction.toByteArray());
        log.info("-----111111 wqq----"+transactionHex);
        Protocol.Transaction transaction_cov = Protocol.Transaction.parseFrom(ByteString.copyFrom(ByteArray.fromHexString(transactionHex)));


        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction_cov, wqq1key, 9, blockingStubFull);
        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();

        log.info("--wqq debug--: wqq158: "+wqq158);
        log.info("--wqq debug--: wqq158_2: " + wqq158_2 );
        object.put("address",wqq158_2);
        object.put("netType","main_net");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }
    @Test(enabled = true,invocationCount = 1, description = "multi sign send coin")
    public void sendCoin2() throws InvalidProtocolBufferException {
        Protocol.Transaction transaction = TronlinkApiList
                .sendcoin(wqq1, 500_000, quince, blockingStubFull);
        log.info("-----111111  "+ JsonFormat.printToString(transaction));
        String transactionHex = ByteArray.toHexString(transaction.toByteArray());
        log.info("-----111111 wqq----"+transactionHex);
        Protocol.Transaction transaction_cov = Protocol.Transaction.parseFrom(ByteString.copyFrom(ByteArray.fromHexString(transactionHex)));


        Protocol.Transaction transaction1 = TronlinkApiList.addTransactionSignWithPermissionId(
                transaction_cov, quincekey, 3, blockingStubFull);

        log.info("-----2222  "+JsonFormat.printToString(transaction1));

        JSONObject object = new JSONObject();

        log.info("--wqq debug--: wqq158: "+wqq158);
        log.info("--wqq debug--: wqq158_2: " + wqq158_2 );
        object.put("address",quince58);
        object.put("netType","shasta");
        object.put("transaction",JSONObject.parse(JsonFormat.printToString(transaction1)));
        res = TronlinkApiList.multiTransaction(object);
        Assert.assertEquals(200, res.getStatusLine().getStatusCode());
        responseContent = TronlinkApiList.parseJsonObResponseContent(res);
        Assert.assertEquals(0,responseContent.getIntValue("code"));

    }

    @Test(enabled = true,invocationCount = 1,description = "multi sign freeze balandce")
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
        object.put("address",wqq158_2);
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

    public static String encode58Check(byte[] input) {
        byte[] hash0 = Sha256Hash.hash(CommonParameter
                .getInstance().isECKeyCryptoEngine(), input);
        byte[] hash1 = Sha256Hash.hash(CommonParameter
                .getInstance().isECKeyCryptoEngine(), hash0);
        byte[] inputCheck = new byte[input.length + 4];
        System.arraycopy(input, 0, inputCheck, 0, input.length);
        System.arraycopy(hash1, 0, inputCheck, input.length, 4);
        return Base58.encode(inputCheck);
    }

    private static byte[] decode58Check(String input) {
        byte[] decodeCheck = Base58.decode(input);
        if (decodeCheck.length <= 4) {
            return null;
        }
        byte[] decodeData = new byte[decodeCheck.length - 4];
        System.arraycopy(decodeCheck, 0, decodeData, 0, decodeData.length);
        byte[] hash0 = Sha256Hash.hash(CommonParameter.getInstance()
                .isECKeyCryptoEngine(), decodeData);
        byte[] hash1 = Sha256Hash.hash(CommonParameter.getInstance()
                .isECKeyCryptoEngine(), hash0);
        if (hash1[0] == decodeCheck[decodeData.length]
                && hash1[1] == decodeCheck[decodeData.length + 1]
                && hash1[2] == decodeCheck[decodeData.length + 2]
                && hash1[3] == decodeCheck[decodeData.length + 3]) {
            return decodeData;
        }
        return null;
    }

    }