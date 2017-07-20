package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Delegate;
import so.asch.sdk.dto.TransactionInfo;
import so.asch.sdk.dto.query.DelegateQueryParameters;

public class DelegateService extends AschRESTService implements Delegate {

    @Override
    public JSONObject getCount() {
        return get("/api/delegates/count");
    }

    @Override
    public JSONObject getVoters(String publicKey) {
        JSONObject parameters = jsonWithPublicKeyField(publicKey);
        return get("/api/delegates/voters", parameters);
    }

    @Override
    public JSONObject getDelegateByPublicKey(String publicKey){
        JSONObject parameters = jsonWithPublicKeyField(publicKey);
        return get("/api/delegates/get", parameters);
    }

    @Override
    public JSONObject getDelegateByName(String userName){
        JSONObject parameters = new JSONObject().fluentPut("username", userName);
        return get("/api/delegates/get", parameters);
    }

    @Override
    public JSONObject getDelegates( DelegateQueryParameters parameters) {
        JSONObject query = jsonFromObject(parameters);
        return get("/api/delegates/",  query);
    }

    @Override
    public JSONObject getDelegateFee(String publicKey){
        JSONObject parameters = jsonWithPublicKeyField(publicKey);
        return get("/api/delegates/fee", parameters);
    }

    @Override
    public JSONObject getForging(String publicKey) {
        JSONObject parameters = jsonWithPublicKeyField(publicKey);
        return get("/api/delegates/fee", parameters);
    }

    @Override
    public JSONObject registerDelegate(String userName, String secret, String secondSecret ) {
        try {
            TransactionInfo transaction = getTransactionBuilder().buildDelegate(userName, secret, secondSecret);
            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject enableForge(String secret, String publicKey) {
        return null;
    }

    @Override
    public JSONObject disableForge(String secret, String publicKey) {
        return null;
    }

    @Override
    public JSONObject getForgingStatus(String publicKey) {
        return null;
    }
}
