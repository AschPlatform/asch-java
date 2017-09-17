package so.asch.sdk.codec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 编码辅助类
 * @author eagle
 *
 */
public class Encoding {

    public static String hex(byte[] buffer){
        return new String(Hex.encodeHex(buffer));
    }

    public static String base58(byte[] buffer) {
        return Base58.encode(buffer);
    }

    public static byte[] getUTF8Bytes(String str){
        if (null == str) return new byte[0];
        try{
            return str.getBytes("UTF-8");
        }
        catch (Exception ex){
            return null;
        }
    }

    public static String base64(byte[] buffer){
        return new String(Base64.encodeBase64(buffer));
    }

}
