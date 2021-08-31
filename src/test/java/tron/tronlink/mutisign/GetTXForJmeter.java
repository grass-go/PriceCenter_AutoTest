package tron.tronlink.mutisign;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.tron.common.crypto.ECKey;
import org.tron.common.parameter.CommonParameter;
import org.tron.common.utils.ByteArray;
import org.tron.common.utils.Commons;
import org.tron.common.utils.Sha256Hash;
import org.tron.core.Wallet;
import org.tron.core.services.http.JsonFormat;
import org.tron.protos.contract.BalanceContract;
import org.tron.api.WalletGrpc;
import com.google.protobuf.ByteString;
import org.tron.protos.Protocol;

import java.math.BigInteger;

public class GetTXForJmeter {

    String quince58 = "TX74o6dWugAgdaMv8M39QP9YL5QRgfj32t";
    byte[] quince = Commons.decode58Check(quince58);
    String wqq158 = "TE3if14LPRdKTiQTkEfqUwmWXuLMecQueo";
    String wqq1key = "8d5c18030466b6ab0e5367154d15c4f6cb46d2fb56a0b552e017d183abd8c255";
    byte[] wqq1 = Commons.decode58Check(wqq158);

    public String sendCoinTxHex() {
        WalletGrpc.WalletBlockingStub blockingStubFull = null;
        ManagedChannel channelFull = null;
        channelFull = ManagedChannelBuilder.forTarget("47.252.19.181:50051").usePlaintext().build();
        blockingStubFull = org.tron.api.WalletGrpc.newBlockingStub(channelFull);
        // get Transaction
        Wallet.setAddressPreFixByte((byte) 0x41);
        BalanceContract.TransferContract.Builder builder = BalanceContract.TransferContract.newBuilder();
        ByteString bsTo = ByteString.copyFrom(wqq1);
        ByteString bsOwner = ByteString.copyFrom(quince);
        builder.setToAddress(bsTo);
        builder.setOwnerAddress(bsOwner);
        builder.setAmount(101);
        BalanceContract.TransferContract contract = builder.build();
        Protocol.Transaction transaction = blockingStubFull.createTransaction(contract);
        if (transaction == null || transaction.getRawData().getContractCount() == 0) {
            return null;
        }
        return ByteArray.toHexString(transaction.toByteArray());
    }

    public Object addTransactionSignWithPermissionId(String transactionTxHex) throws InvalidProtocolBufferException {
        Protocol.Transaction transaction = Protocol.Transaction.parseFrom(ByteString.copyFrom(ByteArray.fromHexString(transactionTxHex)));
        Wallet.setAddressPreFixByte((byte) 0x41);
        ECKey temKey = null;
        try {
            BigInteger priK = new BigInteger(wqq1key, 16);
            temKey = ECKey.fromPrivate(priK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        long now = System.currentTimeMillis();
        Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder().setExpiration(now+86400000L);
        //Protocol.Transaction.raw.Builder raw = transaction.getRawData().toBuilder();
        Protocol.Transaction.Contract.Builder contract = raw.getContract(0).toBuilder()
                .setPermissionId(4);
        raw.clearContract();
        raw.addContract(contract);
        transaction = transaction.toBuilder().setRawData(raw).build();

        Protocol.Transaction.Builder transactionBuilderSigned = transaction.toBuilder();
        byte[] hash = Sha256Hash.hash(CommonParameter.getInstance()
                .isECKeyCryptoEngine(), transaction.getRawData().toByteArray());
        ECKey ecKey = temKey;
        ECKey.ECDSASignature signature = ecKey.sign(hash);
        ByteString bsSign = ByteString.copyFrom(signature.toByteArray());
        transactionBuilderSigned.addSignature(bsSign);
        transaction = transactionBuilderSigned.build();
        Object transactionObject = JSONObject.parse(JsonFormat.printToString(transaction));
        return transactionObject;
    }

}