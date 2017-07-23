package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Transaction;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.TransactionQueryParameters;

public class TransactionService extends AschRESTService implements Transaction {
    @Override
    public JSONObject queryTransactions(TransactionQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidTransactionQueryParameters(parameters), "invalid parameters");

            JSONObject jsonParameters = jsonFromObject(parameters);
            return get(AschServiceUrls.Transaction.QUERY_TRANSACTIONS, jsonParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getTransaction(String transactionId) {
        try {
            Argument.require(Validation.isValidTransactionId(transactionId), "invalid transaction id");

            JSONObject jsonParameters = new JSONObject().fluentPut("id", transactionId);
            return get(AschServiceUrls.Transaction.GET_TRANSACTION, jsonParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getUnconfirmedTransaction(String unconfirmedTransactionId){
        try {
            Argument.require(Validation.isValidTransactionId(unconfirmedTransactionId), "invalid transaction id");

            JSONObject jsonParameters = new JSONObject().fluentPut("id", unconfirmedTransactionId);
            return get(AschServiceUrls.Transaction.GET_UNCONFIRMED_TRANSACTION, jsonParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getUnconfirmedTransactions(String senderPublicKey, String address) {
        try {
            Argument.require(Validation.isValidPublicKey(senderPublicKey), "invalid senderPublicKey");
            Argument.require(Validation.isValidAddress(address), "invalid address");

            JSONObject jsonParameters = new JSONObject()
                    .fluentPut("senderPublicKey", senderPublicKey)
                    .fluentPut("address", address);

            return get(AschServiceUrls.Transaction.GET_UNCONFIRMED_TRANSACTIONS, jsonParameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public JSONObject addTransaction(String secret, int amount, String recipientId, String senderPublicKey, String secondSecret, String multiSignAccountPublicKey) {
        try {
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.require(Validation.isValidSecure(secondSecret), "invalid secondSecret");
            Argument.require(Validation.isValidAmount(amount), "invalid amount");
            Argument.require(Validation.isValidPublicKey(senderPublicKey), "invalid senderPublicKey");
            Argument.require(Validation.isValidPublicKey(multiSignAccountPublicKey), "invalid multiSignAccountPublicKey");

            JSONObject jsonParameters = new JSONObject()
                    .fluentPut("secret", secret)
                    .fluentPut("amount", amount)
                    .fluentPut("recipientId", recipientId)
                    .fluentPut("secondSecret", secondSecret)
                    .fluentPut("senderPublicKey", senderPublicKey)
                    .fluentPut("multisigAccountPublicKey", multiSignAccountPublicKey);

            return post(AschServiceUrls.Transaction.CREATE_TRANSACTION, jsonParameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
