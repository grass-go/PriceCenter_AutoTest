package tron.common.utils;

/**
 * 地址转换：私钥转T 私钥转41 T转41 41转T
 */

import lombok.extern.slf4j.Slf4j;
import org.spongycastle.util.encoders.Hex;
import org.springframework.util.StringUtils;
import org.tron.common.parameter.CommonParameter;
import org.tron.common.utils.Base58;
import org.tron.common.utils.Sha256Hash;

import java.util.Arrays;

@Slf4j
public class AddressConvert {

    public static final String ZERO_ADDRESS = "T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb";

    public static String hexTo58(String address) {
        if (StringUtils.isEmpty(address)) {
            return address;
        }

        if (address.startsWith("T")) {
            return address;
        }

        if (!address.startsWith("41")) {
            return null;
        }

        try {
            final byte[] decode = Hex.decode(address);
            return encode58Check(decode);
        } catch (Exception ex) {
            log.error("", ex);
        }

        return null;
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

    public static String toHex(String address) {
        if (StringUtils.isEmpty(address) || address.startsWith("41")) {
            return address;
        }

        if (address.startsWith("41")) {
            return address.toLowerCase();
        }

        if (!address.startsWith("T")) {
            return null;
        }

        final byte[] decode = Base58.decode(address);
        final String string = Hex.toHexString(decode);
        return string.substring(0, 42);
    }

    public static void main(String[] args) {
        String aaa = "41597E58EBCB28CBB3A919CAE68A487A84CA179C70";
        System.out.println(hexTo58(aaa));
        final byte[] decode1 = Hex.decode(aaa);
        System.out.println(decode1.length);
        System.out.println(Arrays.toString(decode1));
        System.out.println(hexTo58(aaa).equals("TBLWdxVgqDUd9XR2CSuddtLxC9JZTuECkU"));
        String bbb = "TZEjhseYC8tbi2a7GVVfmD5Mw11tiimyFS";
        final byte[] decode = Base58.decode(bbb);
        System.out.println(decode.length);
        System.out.println(Hex.toHexString(decode));
        System.out.println(Hex.toHexString(decode).substring(0, 42));
        System.out.println(Hex.toHexString(decode).length());
    }
}

