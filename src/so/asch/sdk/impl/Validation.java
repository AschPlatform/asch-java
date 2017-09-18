package so.asch.sdk.impl;


import so.asch.sdk.ContentEncoding;
import so.asch.sdk.dto.query.*;
import so.asch.sdk.security.Bip39;

import java.util.HashSet;
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
    private static final String BASE64_PATTERN = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$";
    private static final String IP_PATTERN =
            "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

    public static boolean isHex(String hexString){
        return  (null != hexString) && hexString.matches(HEX_PATTERN);
    }

    public static boolean isValidSecret(String secret) {
        return secret != null &&
                Bip39.isValidMnemonicCode(secret);
    }

    public static boolean isValidSecondSecret(String secure) {
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
                ( address.matches(NUMBER_PATTERN) ||
                        ( address.matches(BASE58_PATTERN) && address.charAt(0) == AschConst.BASE58_ADDRESS_PREFIX )
                );
    }

    public static boolean isValidAmount(int amount){
        //TODO:validate amount
        return amount > 0 && amount <100000000;
    }

    public static boolean isValidTransactionId(String transactionId){
        //TODO:validate transaction id
        return transactionId != null;
    }

    public static boolean isValidLimit(Integer limit){
        return limit == null || (limit >= 1 && limit <=100);
    }

    public static boolean isValidOffset(int offset){
        return  offset >= 0;
    }

    public static boolean isValidIP(String ip){
        return ip != null && ip.matches(IP_PATTERN);
    }

    public static boolean isValidPort(int port){
        return port >0 && port <=65535;
    }

    public static boolean isValidMultiSignatureMinAccount(int minAccount){
        return minAccount>=2 && minAccount <=16;
    }

    public static boolean isValidMultiSignatureLifetime(int lifetime){
        return lifetime>=1 && lifetime <=72;
    }

    public static boolean isValidWait(int wait){
        return wait >= 0 && wait<= 6;
    }

    public static boolean isValidVoteKeys(String[] upvotes, String[] downvotes){
        return  (upvotes != null || downvotes != null) &&
                (!isIntersected(upvotes, downvotes)) &&
                (!(isDuplicate(upvotes) || isDuplicate(downvotes)))&&
                ((upvotes == null ? 0 : upvotes.length) + (downvotes == null ? 0 : downvotes.length) <= 33)&&
                (all(upvotes, Validation::isValidPublicKey)) &&
                (all(downvotes, Validation::isValidPublicKey));
    }

    public static boolean isValidMultiSignatureKeys(String[] addKeys, String[]removeKeys){
        return  (addKeys != null || removeKeys != null) &&
                (!isIntersected(addKeys, removeKeys)) &&
                (!(isDuplicate(addKeys) || isDuplicate(removeKeys)))&&
                ((addKeys == null ? 0 : addKeys.length) + (removeKeys == null ? 0 : removeKeys.length) <= 10)&&
                (all(addKeys, Validation::isValidPublicKey)) &&
                (all(removeKeys,Validation::isValidPublicKey));

    }

    public static boolean isValidContent(String content, ContentEncoding encoding){
        if (null == content )  return false;
        switch (encoding){
            case Hex:
                return isHex(content);
            case Base64:
                return content.matches(BASE64_PATTERN);
            case Raw:
                return true;
            default:
                return false;
        }
    }

    public static boolean isValidPeerQueryParameters(PeerQueryParameters queryParameters){
        //TODO: validate queryParameters
        return queryParameters != null;
    }

    private static boolean isValidQueryParameters(QueryParameters queryParameters){
        return queryParameters != null &&
                isValidLimit(queryParameters.getLimit());
    }

    public static boolean isValidAccountQueryParameters(QueryParameters queryParameters){
        return isValidQueryParameters(queryParameters);
    }

    public static boolean isValidTransactionQueryParameters(TransactionQueryParameters queryParameters){
        //todo: validate queryParameters
        return isValidQueryParameters(queryParameters);
    }

    public static boolean isValidDelegateQueryParameters(DelegateQueryParameters queryParameters){
        //TODO: validate queryParameters
        return isValidQueryParameters(queryParameters);
    }

    public static boolean isValidBlockQueryParameters(BlockQueryParameters queryParameters){
        //TODO: validate queryParameters
        return isValidQueryParameters(queryParameters);
    }



    public static <T> boolean all(T[] items, Predicate<T> predicate){
        if (items == null || items.length ==0)
            throw new IllegalArgumentException("items must not null or empty");

        for(T o : items){
            if (!predicate.test(o))
                return false;
        }
        return true;
    }

    public static <T> boolean any(T[] items, Predicate<T> predicate){
        if (items == null || items.length ==0)
            throw new IllegalArgumentException("items must not null or empty");

        for(T o : items){
            if (predicate.test(o))
                return true;
        }
        return false;
    }

    private static <T>boolean isIntersected(T[] array1, T[] array2){
        if (array1 == null || array2 == null)
            return false;

        HashSet<T> set = new HashSet<>();
        for (T item: array1) {
            if (!set.contains(item)) set.add(item);
        }

        for(T item : array2){
            if (set.contains(item)) return true;
        }

        return false;
    }

    private static <T> boolean isDuplicate(T[] array){
        if (array == null)
            return false;

        HashSet<T> set = new HashSet<>();
        for (T item: array) {
            if (!set.contains(item))
                set.add(item);
            else
                return true;
        }

        return false;
    }
}
