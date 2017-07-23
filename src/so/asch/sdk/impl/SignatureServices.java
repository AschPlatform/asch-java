package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Signature;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.transaction.TransactionInfo;

public class SignatureServices extends AschRESTService implements Signature {
    @Override
    public JSONObject setSignature(String secret, String secondSecret, String publicKey, String multiSignAccountPublicKey) {
        try{
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.optional(publicKey, Validation::isValidSecure, "invalid publicKey");
            Argument.require(Validation.isValidSecure(secondSecret), "invalid secondSecret");
            Argument.optional(multiSignAccountPublicKey, Validation::isValidSecure, "invalid multiSignAccountPublicKey");

//            JSONObject parameters = new JSONObject()
//                    .fluentPut("secret", secret)
//                    .fluentPut("publicKey", publicKey)
//                    .fluentPut("secondSecret", secondSecret)
//                    .fluentPut("multisigAccountPublicKey", multiSignAccountPublicKey); //注意名称要与asch源码一致!!!

            TransactionInfo transaction = getTransactionBuilder()
                    .buildSignature(secret, secondSecret);

            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getSignatureFee() {
        return get(AschServiceUrls.Signature.GET_SIGNATURE_FEE);
    }

    //TODO:此接口实现有疑问
    @Override
    public JSONObject setMultiSignature(int minAccount, int lifetime, String[] addKeys, String[] removeKeys,
                                        String secret, String secondSecret, String publicKey ) {
        try {
            Argument.require(Validation.isValidMultiSignatureMinAccount(minAccount), "invalid minAccount");
            Argument.require(Validation.any(keys-> keys!= null && keys.length > 0, addKeys, removeKeys ), "no keys in addKeys and removeKeys");
            Argument.optional(addKeys, keys->Validation.all(Validation::isValidPublicKey, keys), "invalid addKeys");
            Argument.optional(removeKeys, keys->Validation.all(Validation::isValidPublicKey, keys), "invalid removeKeys");
            Argument.require(Validation.isValidMultiSignatureLifetime(lifetime), "invalid lifetime");
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.optional(publicKey, Validation::isValidSecure, "invalid publicKey");
            Argument.optional(secondSecret, Validation::isValidSecure, "invalid secondSecret");

//            List<String> keysGroup = new ArrayList<>();
//            if (addKeys != null)
//                Arrays.asList(addKeys).forEach(key-> keysGroup.add("+"+key));
//            if (removeKeys != null)
//                Arrays.asList(removeKeys).forEach(key-> keysGroup.add("-"+key));
//
//            JSONObject parameters = new JSONObject()
//                    .fluentPut("secret", secret)
//                    .fluentPut("publicKey", publicKey)
//                    .fluentPut("secondSecret", secondSecret)
//                    .fluentPut("min", minAccount)
//                    .fluentPut("lifetime", lifetime)
//                    .fluentPut("keysgroup", keysGroup);
//            return post(AschServiceUrls.Signature.SET_MULTI_SIGNATURE, parameters);
            TransactionInfo transaction = getTransactionBuilder()
                    .buildMultiSignature(minAccount, lifetime, addKeys, removeKeys, secret, secondSecret);

            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }

    }

    //TODO:此接口实现有疑问
    @Override
    public JSONObject multiSignature(String transactionId, String secret, String secondSecret, String publicKey ) {
        try {
            Argument.require(Validation.isValidTransactionId(transactionId), "invalid transactionId");
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecure, "invalid secondSecret");
            Argument.optional(publicKey, Validation::isValidSecure, "invalid publicKey");

            JSONObject parameters = new JSONObject()
                    .fluentPut("secret", secret)
                    .fluentPut("publicKey", publicKey)
                    .fluentPut("secondSecret", secondSecret)
                    .fluentPut("transactionId", transactionId);

            return post(AschServiceUrls.Signature.MULTI_SIGNATURE, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getPendingTransactions(String publicKey) {
        return getByPublicKey(AschServiceUrls.Signature.GET_PENDING_TRANSACTIONS, publicKey);
    }

    @Override
    public JSONObject getMultiSignatureAccounts(String publicKey) {
        return getByPublicKey(AschServiceUrls.Signature.GET_MULTI_SIGNATURE_ACCOUNTS, publicKey);
    }
}
