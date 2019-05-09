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

            return get(AschServiceUrls.Transaction.GET_TRANSACTION + transactionId);
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
            Argument.optional(senderPublicKey, Validation::isValidPublicKey, "invalid senderPublicKey");
            Argument.optional(address, Validation::isValidAddress, "invalid address");

            ParameterMap parameters = new ParameterMap()
                    .put("senderPublicKey", senderPublicKey)
                    .put("address", address);

            return get(AschServiceUrls.Transaction.GET_UNCONFIRMED_TRANSACTIONS, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }

}
