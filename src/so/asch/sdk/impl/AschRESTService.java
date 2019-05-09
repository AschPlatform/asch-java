package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.AschInterface;
import so.asch.sdk.AschResult;
import so.asch.sdk.AschSDKConfig;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.security.SecurityStrategy;
import so.asch.sdk.transaction.TransactionBuilder;
import so.asch.sdk.transaction.TransactionInfo;
import so.asch.sdk.util.DebugHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Asch服务基类
 * @author eagle
 */
public abstract class AschRESTService implements AschInterface{
    private static final String CHAR_SET = "UTF-8";

    protected static final AschSDKConfig config = AschSDKConfig.getInstance();
    private static final Map<String, String> customeHeaders =  new HashMap<>();

    static {
        customeHeaders.put("magic", config.getMagic());
        customeHeaders.put("version", "");
    }

    protected TransactionBuilder getTransactionBuilder(){
        return new TransactionBuilder();
    }

    protected SecurityStrategy getSecurity(){
        return AschFactory.getInstance().getSecurity();
    }

    protected Map<String, String> getCustomeHeaders(){
        if (! config.getMagic().equals(customeHeaders.get("magic"))){
            customeHeaders.put("magic", config.getMagic());
        }
        return customeHeaders;
    }

    protected String getFullUrl(String relativeUrl){
        return config.getAschServer() + relativeUrl;
    }

    protected AschResult get(String relativeUrl){
        try{
            String jsonString = REST.get(getFullUrl(relativeUrl), null);
            return AschResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected AschResult get(String relativeUrl, ParameterMap parameters){
        try{
            String jsonString =  REST.get(getFullUrl(relativeUrl), parameters);
            return AschResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected AschResult post(String relativeUrl, ParameterMap parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, null, CHAR_SET);
            return AschResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected AschResult post(String relativeUrl, String parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, null, CHAR_SET);
            return AschResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected AschResult postMagic(String relativeUrl, ParameterMap parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, getCustomeHeaders(), CHAR_SET);
            return AschResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected AschResult postMagic(String relativeUrl, String parameters){
        try{
            String jsonString =  REST.post(getFullUrl(relativeUrl), parameters, getCustomeHeaders(), CHAR_SET);
            return AschResult.FromJsonString(jsonString);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected AschResult broadcastTransaction(TransactionInfo transaction){
        ParameterMap transactionParameter = new ParameterMap()
                .put("transaction", transaction);
        return postMagic(AschServiceUrls.Peer.BROADCAST_TRANSACTION, transactionParameter );
    }

    protected AschResult fail(Exception ex){
        DebugHelper.error(ex, "rest call");
        return AschResult.Failed(ex);
    }

    protected ParameterMap parametersWithPublicKeyField(String publicKey){
        return new ParameterMap().put("publicKey", publicKey);
    }

    protected AschResult getByPublicKey(String relativeUrl, String publicKey){
        try {
            Argument.require(Validation.isValidPublicKey(publicKey), "invalid public key");

            ParameterMap parameters = parametersWithPublicKeyField(publicKey);
            return get(relativeUrl, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected AschResult getByAddress(String relativeUrl, String address){
        try {
            Argument.require(Validation.isValidAddress(address), "invalid public address");

            ParameterMap parameters = new ParameterMap().put("address", address);
            return get(relativeUrl, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected ParameterMap parametersFromObject(Object object){
        ParameterMap map = new ParameterMap();
        map.putAll(JSONObject.parseObject( JSONObject.toJSONString(object) ));

        return map;
    }
}
