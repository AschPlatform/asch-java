package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Delegate;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.transaction.TransactionInfo;
import so.asch.sdk.dto.query.DelegateQueryParameters;

public class DelegateService extends AschRESTService implements Delegate {

    @Override
    public JSONObject getDelegatesCount() {
        return get(AschServiceUrls.Delegate.GET_DELEGATES_COUNT);
    }

    @Override
    public JSONObject getVoters(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_VOTES, publicKey);
    }

    @Override
    public JSONObject getDelegateByPublicKey(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_DELEGATE, publicKey);
    }

    @Override
    public JSONObject getDelegateByName(String userName){
        try {
            Argument.notNull(userName, "invalid user name");

            JSONObject parameters = new JSONObject().fluentPut("username", userName);
            return get(AschServiceUrls.Delegate.GET_DELEGATE, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject queryDelegates( DelegateQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidDelegateQueryParameters(parameters), "invalid parameters");

            JSONObject query = jsonFromObject(parameters);
            return get(AschServiceUrls.Delegate.QUERY_DELEGATES,  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getDelegateFee(String publicKey){
        return getByPublicKey(AschServiceUrls.Delegate.GET_DELEGATE_FEE, publicKey);
    }

    @Override
    public JSONObject getForging(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_FORGING, publicKey);
    }

    @Override
    public JSONObject registerDelegate(String userName, String secret, String secondSecret ) {
        try {
            Argument.notNull(userName, "invalid username");
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.optional(secondSecret, ss->Validation.isValidSecure(ss), "invalid secondSecret");

            TransactionInfo transaction = getTransactionBuilder().buildDelegate(userName, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    /**
    *    enableForge和disableForge接口不知接口地址，待确认
    * */
    @Override
    public JSONObject enableForge( String publicKey, String secret ) {

        return null;
    }

    @Override
    public JSONObject disableForge(String publicKey, String secret ) {
        return null;
    }

    @Override
    public JSONObject getForgingStatus(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_FORGING_STATUS, publicKey);
    }
}
