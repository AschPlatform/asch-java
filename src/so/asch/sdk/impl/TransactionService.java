package so.asch.sdk.impl;

import so.asch.sdk.AschResult;
import so.asch.sdk.Transaction;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.TransactionQueryParameters;

public class TransactionService extends AschRESTService implements Transaction {
    @Override
    public AschResult queryTransactions(TransactionQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidTransactionQueryParameters(parameters), "invalid parameters");

            ParameterMap getParameters = parametersFromObject(parameters);
            return get(AschServiceUrls.Transaction.QUERY_TRANSACTIONS, getParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getTransaction(String transactionId) {
        try {
            Argument.require(Validation.isValidTransactionId(transactionId), "invalid transaction id");

            ParameterMap parameters = new ParameterMap().put("id", transactionId);
            return get(AschServiceUrls.Transaction.GET_TRANSACTION, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getUnconfirmedTransaction(String unconfirmedTransactionId){
        try {
            Argument.require(Validation.isValidTransactionId(unconfirmedTransactionId), "invalid transaction id");

            ParameterMap parameters = new ParameterMap()
                    .put("id", unconfirmedTransactionId);
            return get(AschServiceUrls.Transaction.GET_UNCONFIRMED_TRANSACTION, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getUnconfirmedTransactions(String senderPublicKey, String address) {
        try {
            Argument.require(Validation.isValidPublicKey(senderPublicKey), "invalid senderPublicKey");
            Argument.require(Validation.isValidAddress(address), "invalid address");

            ParameterMap parameters = new ParameterMap()
                    .put("senderPublicKey", senderPublicKey)
                    .put("address", address);

            return get(AschServiceUrls.Transaction.GET_UNCONFIRMED_TRANSACTIONS, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public AschResult addTransaction(String secret, int amount, String recipientId, String senderPublicKey, String secondSecret, String multiSignAccountPublicKey) {
        try {
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.require(Validation.isValidSecondSecret(secondSecret), "invalid secondSecret");
            Argument.require(Validation.isValidAmount(amount), "invalid amount");
            Argument.require(Validation.isValidPublicKey(senderPublicKey), "invalid senderPublicKey");
            Argument.require(Validation.isValidPublicKey(multiSignAccountPublicKey), "invalid multiSignAccountPublicKey");

            ParameterMap parameters = new ParameterMap()
                    .put("secret", secret)
                    .put("amount", amount)
                    .put("recipientId", recipientId)
                    .put("secondSecret", secondSecret)
                    .put("senderPublicKey", senderPublicKey)
                    .put("multisigAccountPublicKey", multiSignAccountPublicKey);

            return post(AschServiceUrls.Transaction.CREATE_TRANSACTION, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
