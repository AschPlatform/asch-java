package so.asch.sdk.impl;

import java.util.function.Predicate;

/**
 * Created by eagle on 17-7-18.
 */
public class Validation {
    private static final int MIN_SECURE_LENGTH = 1;
    private static final int MAX_SECURE_LENGTH = 100;
    private static final int MIN_ADDRESS_LENGTH = 1;
    private static final int MAX_ADDRESS_LENGTH = 100;
    private static final int PUBLIC_KEY_LENGTH = 32;

    private static final String HEX_PATTERN = "^([0-9]|[A-F]|[a-f])+$";
    private static final String NUMBER_PATTERN = "^\\d+$";
    private static final String BASE58_PATTERN = "^([1-9]|[A-H]|[J-Z]|[a-k]|[m-z])+$";

    public static boolean isHex(String hexString){
        if (null == hexString)
            return false;

        return hexString.matches(HEX_PATTERN);
    }

    public static boolean isValidSecure(String secure) {
        return secure != null &&
                (secure.length() >= MIN_SECURE_LENGTH && secure.length()<= MAX_SECURE_LENGTH) ;
    }

    public static boolean isValidPublicKey(String publicKey){
        return publicKey != null &&
                publicKey.length() == PUBLIC_KEY_LENGTH * 2 &&
                isHex(publicKey);
    }

    public static boolean isValidAddress(String address){
        return address != null &&
                ( address.length() >= MIN_ADDRESS_LENGTH && address.length() <= MAX_ADDRESS_LENGTH) &&
                ( address.matches(NUMBER_PATTERN) || address.matches(BASE58_PATTERN) ) &&
                address.charAt(0) == AschConst.BASE58_ADDRESS_PREFIX;
    }

    public static <T> boolean all(Predicate<T> predicate, T... list){
        if (list == null)
            return false;

        for(T o : list){
            if (!predicate.test(o))
                return false;
        }
        return true;
    }

    public static <T> boolean any(Predicate<T> predicate, T... list){
        if (list == null)
            return false;

        for(T o : list){
            if (predicate.test(o))
                return true;
        }
        return false;
    }
}
