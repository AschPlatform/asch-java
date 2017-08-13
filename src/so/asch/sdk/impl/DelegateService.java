package so.asch.sdk.impl;

import so.asch.sdk.AschResult;
import so.asch.sdk.Delegate;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.DelegateQueryParameters;
import so.asch.sdk.transaction.TransactionInfo;

public class DelegateService extends AschRESTService implements Delegate {

    @Override
    public AschResult getDelegatesCount() {
        return get(AschServiceUrls.Delegate.GET_DELEGATES_COUNT);
    }

    @Override
    public AschResult getVoters(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_VOTES, publicKey);
    }

    @Override
    public AschResult getDelegateByPublicKey(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_DELEGATE, publicKey);
    }

    @Override
    public AschResult getDelegateByName(String userName){
        try {
            Argument.notNull(userName, "invalid user name");

            ParameterMap parameters = new ParameterMap().put("username", userName);
            return get(AschServiceUrls.Delegate.GET_DELEGATE, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult queryDelegates( DelegateQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidDelegateQueryParameters(parameters), "invalid parameters");

            ParameterMap query = parametersFromObject(parameters);
            return get(AschServiceUrls.Delegate.QUERY_DELEGATES,  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getDelegateFee(String publicKey){
        return getByPublicKey(AschServiceUrls.Delegate.GET_DELEGATE_FEE, publicKey);
    }

    @Override
    public AschResult getForging(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_FORGING, publicKey);
    }

    @Override
    public AschResult registerDelegate(String userName, String secret, String secondSecret ) {
        try {
            Argument.notNull(userName, "invalid username");
            Argument.require(Validation.isValidSecret(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecondSecret, "invalid secondSecret");

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
    public AschResult enableForge( String publicKey, String secret ) {

        return null;
    }

    @Override
    public AschResult disableForge(String publicKey, String secret ) {
        return null;
    }

    @Override
    public AschResult getForgingStatus(String publicKey) {
        return getByPublicKey(AschServiceUrls.Delegate.GET_FORGING_STATUS, publicKey);
    }
}
