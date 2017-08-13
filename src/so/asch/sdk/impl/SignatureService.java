package so.asch.sdk.impl;

import so.asch.sdk.AschResult;
import so.asch.sdk.Signature;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.transaction.TransactionInfo;

public class SignatureService extends AschRESTService implements Signature {
    @Override
    public AschResult setSignature(String secret, String secondSecret, String publicKey, String multiSignAccountPublicKey) {
        try{
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(publicKey, Validation::isValidPublicKey, "invalid publicKey");
            Argument.require(Validation.isValidSecondSecret(secondSecret), "invalid secondSecret");
            Argument.optional(multiSignAccountPublicKey, Validation::isValidPublicKey, "invalid multiSignAccountPublicKey");

//            AschResult parameters = new AschResult()
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
    public AschResult getSignatureFee() {
        return get(AschServiceUrls.Signature.GET_SIGNATURE_FEE);
    }

    //TODO:此接口实现有疑问
    @Override
    public AschResult setMultiSignature(int minAccount, int lifetime, String[] addKeys, String[] removeKeys,
                                        String secret, String secondSecret, String publicKey ) {
        try {
            Argument.require(Validation.isValidMultiSignatureMinAccount(minAccount), "invalid minAccount");
            Argument.require(Validation.isValidMultiSignatureKeys(addKeys, removeKeys), "invalid addKeys or removeKeys");
            Argument.require(Validation.isValidMultiSignatureLifetime(lifetime), "invalid lifetime");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(publicKey, Validation::isValidPublicKey, "invalid publicKey");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");

//            List<String> keysGroup = new ArrayList<>();
//            if (addKeys != null)
//                Arrays.asList(addKeys).forEach(key-> keysGroup.add("+"+key));
//            if (removeKeys != null)
//                Arrays.asList(removeKeys).forEach(key-> keysGroup.add("-"+key));
//
//            AschResult parameters = new AschResult()
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
    public AschResult multiSignature(String transactionId, String secret, String secondSecret, String publicKey ) {
        try {
            Argument.require(Validation.isValidTransactionId(transactionId), "invalid transactionId");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");
            Argument.optional(publicKey, Validation::isValidPublicKey, "invalid publicKey");

            ParameterMap parameters = new ParameterMap()
                    .put("secret", secret)
                    .put("publicKey", publicKey)
                    .put("secondSecret", secondSecret)
                    .put("transactionId", transactionId);

            return post(AschServiceUrls.Signature.MULTI_SIGNATURE, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getPendingTransactions(String publicKey) {
        return getByPublicKey(AschServiceUrls.Signature.GET_PENDING_TRANSACTIONS, publicKey);
    }

    @Override
    public AschResult getMultiSignatureAccounts(String publicKey) {
        return getByPublicKey(AschServiceUrls.Signature.GET_MULTI_SIGNATURE_ACCOUNTS, publicKey);
    }
}
