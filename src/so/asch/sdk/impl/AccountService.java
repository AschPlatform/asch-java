package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Account;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.transaction.TransactionInfo;
import so.asch.sdk.dto.query.QueryParameters;

import java.security.KeyPair;

/**
 * {@link Account}服务实现
 * @author eagle
 */
public class AccountService extends so.asch.sdk.impl.AschRESTService implements Account  {

    @Override
    public JSONObject login(String secret){
        JSONObject parameters = new JSONObject().fluentPut("secret", secret);
        return post(AschServiceUrls.Account.LOGIN, parameters);
    }

    @Override
    public JSONObject secureLogin(String secret){
        try{
            Argument.require(Validation.isValidSecure(secret), "invalid secret");

            KeyPair keyPair = getSecurity().generateKeyPair(secret);
            String publicKey = getSecurity().encodePublicKey(keyPair.getPublic());
            JSONObject parameters = jsonWithPublicKeyField(publicKey);

            return post(AschServiceUrls.Account.SECURE_LOGIN, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getAccount(String address){
            return getByAddress(AschServiceUrls.Account.GET_ACCOUNT, address);
    }

    @Override
    public JSONObject getBalance(String address){
        return getByAddress(AschServiceUrls.Account.GET_BALANCE, address);
    }

    @Override
    public JSONObject getPublicKey(String address){
        return  getByAddress(AschServiceUrls.Account.GET_PUBLIC_KEY, address);
    }

    @Override
    public JSONObject generatePublicKey(String secret){
        try {
            Argument.require(Validation.isValidSecure(secret), "invalid secret");

            JSONObject parameters = new JSONObject().fluentPut("secret", secret);
            return post(AschServiceUrls.Account.GENERATE_PUBLIC_KEY, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getVotedDelegates(String address){
        return getByAddress(AschServiceUrls.Account.GET_VOTED_DELEGATES, address);
    }

    @Override
    public JSONObject getDelegatesFee(){
        return get(AschServiceUrls.Account.GET_DELEGATE_FEE);
    }

    //todo:验证投票和取消投票的数组都符合规则
    @Override
    public JSONObject vote(String[] upvotePublicKeys, String[] downvotePublicKeys, String secret, String secondSecret){
        try {
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecure, "invalid secondSecret");
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
    public JSONObject transfer(String targetAddress, long amount, String message, String secret, String secondSecret){
        try {
            Argument.require(Validation.isValidAddress(targetAddress), "invalid target address");
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecure, "invalid second secret");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildTransfer(targetAddress, amount, message, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getTopAccounts(QueryParameters parameters){
        try {
            Argument.require(Validation.isValidAccountQueryParameters(parameters), "invalid parameters");

            JSONObject jsonParameters = jsonFromObject(parameters);
            return get(AschServiceUrls.Account.GET_TOP_ACCOUNTS, jsonParameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }
}
