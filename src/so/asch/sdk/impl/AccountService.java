package so.asch.sdk.impl;

import so.asch.sdk.Account;
import so.asch.sdk.AschResult;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.QueryParameters;
import so.asch.sdk.transaction.TransactionBuilder;
import so.asch.sdk.transaction.TransactionInfo;

import java.security.KeyPair;

/**
 * {@link Account}服务实现
 * @author eagle
 */
public class AccountService extends so.asch.sdk.impl.AschRESTService implements Account  {

    @Override
    public AschResult login(String secret){
        ParameterMap parameters = new ParameterMap().put("secret", secret);
        return post(AschServiceUrls.Account.LOGIN, parameters);
    }

    @Override
    public AschResult secureLogin(String secret){
        try{
            Argument.require(Validation.isValidSecret(secret), "invalid secret");

            KeyPair keyPair = getSecurity().generateKeyPair(secret);
            String publicKey = getSecurity().encodePublicKey(keyPair.getPublic());
            ParameterMap parameters = parametersWithPublicKeyField(publicKey);

            return post(AschServiceUrls.Account.SECURE_LOGIN, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult newAccount() {
        return get(AschServiceUrls.Account.GENERATE_SECRET);
    }

    @Override
    public AschResult getAccount(String address){
            return getByAddress(AschServiceUrls.Account.GET_ACCOUNT, address);
    }

    @Override
    public AschResult getBalance(String address){
        return getByAddress(AschServiceUrls.Account.GET_BALANCE, address);
    }

    @Override
    public AschResult transferXAS(String targetAddress, long amount, String message, String secret, String secondSecret) {
        try {
            Argument.require(Validation.isValidAddress(targetAddress), "invalid target address");
            Argument.require(amount > 0, "invalid amount for transfer, should be greater than 0");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid second secret");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildTransfer(targetAddress, amount, message, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult transferUIA(String targetAddress, long amount, String currency, String message, String secret, String secondSecret) {
        try {
            Argument.require(Validation.isValidAddress(targetAddress), "invalid target address");
            Argument.require(amount > 0, "invalid amount for transfer, should be greater than 0");
            Argument.notNullOrEmpty(currency, "invalid currency, should not be empty");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid second secret");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildUIATransfer(targetAddress, amount, currency, message, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

}
