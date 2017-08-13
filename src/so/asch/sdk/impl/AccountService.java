package so.asch.sdk.impl;

import so.asch.sdk.Account;
import so.asch.sdk.AschResult;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.QueryParameters;
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
    public AschResult getAccount(String address){
            return getByAddress(AschServiceUrls.Account.GET_ACCOUNT, address);
    }

    @Override
    public AschResult getBalance(String address){
        return getByAddress(AschServiceUrls.Account.GET_BALANCE, address);
    }

    @Override
    public AschResult getPublicKey(String address){
        return  getByAddress(AschServiceUrls.Account.GET_PUBLIC_KEY, address);
    }

    @Override
    public AschResult generatePublicKey(String secret){
        try {
            Argument.require(Validation.isValidSecret(secret), "invalid secret");

            ParameterMap parameters = new ParameterMap().put("secret", secret);
            return post(AschServiceUrls.Account.GENERATE_PUBLIC_KEY, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getVotedDelegates(String address){
        return getByAddress(AschServiceUrls.Account.GET_VOTED_DELEGATES, address);
    }

    @Override
    public AschResult getDelegatesFee(){
        return get(AschServiceUrls.Account.GET_DELEGATE_FEE);
    }

    //todo:验证投票和取消投票的数组都符合规则
    @Override
    public AschResult vote(String[] upvotePublicKeys, String[] downvotePublicKeys, String secret, String secondSecret){
        try {
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");
            Argument.require(Validation.isValidVoteKeys(upvotePublicKeys, downvotePublicKeys), "invalid upvoteKeys or downvoteKeys");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildVote( upvotePublicKeys, downvotePublicKeys,secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult transfer(String targetAddress, long amount, String message, String secret, String secondSecret){
        try {
            Argument.require(Validation.isValidAddress(targetAddress), "invalid target address");
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
    public AschResult getTopAccounts(QueryParameters parameters){
        try {
            Argument.require(Validation.isValidAccountQueryParameters(parameters), "invalid parameters");

            ParameterMap getParameters = parametersFromObject(parameters);
            return get(AschServiceUrls.Account.GET_TOP_ACCOUNTS, getParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }
}
