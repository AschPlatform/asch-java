package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.AschInterface;
import so.asch.sdk.dto.TransactionBuilder;
import so.asch.sdk.dto.TransactionInfo;
import so.asch.sdk.security.SecurityStrategy;

/**
 * Asch服务基类
 * @author eagle
 */
public abstract class AschRESTService implements AschInterface{

    private static final AschSDKConfig config = AschSDKConfig.getInstance();

    protected TransactionBuilder getTransactionBuilder(){
        return new TransactionBuilder();
    }

    protected SecurityStrategy getSecurity(){
        return AschFactory.getInstance().getSecurity();
    }

    protected String getFullUrl(String relativeUrl){

        return config.getRoot() + relativeUrl;
    }

    protected JSONObject get(String relativeUrl){
        try{
            return REST.get(getFullUrl(relativeUrl), null);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected JSONObject get(String relativeUrl, JSONObject parameters){
        try{
            return REST.get(getFullUrl(relativeUrl), parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected JSONObject post(String relativeUrl, JSONObject parameters){
        try{
            return REST.post(getFullUrl(relativeUrl), parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected JSONObject post(String relativeUrl, String parameters){
        try{
            return REST.post(getFullUrl(relativeUrl), parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected JSONObject postMagic(String relativeUrl, JSONObject parameters){
        try{
            return REST.post(getFullUrl(relativeUrl), parameters, config.getMagicHeaders(), null);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected JSONObject postMagic(String relativeUrl, String parameters){
        try{
            return REST.post(getFullUrl(relativeUrl), parameters, config.getMagicHeaders(), null);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected JSONObject broadcastTransaction(TransactionInfo transaction){
        return postMagic("/peer/transactions", new JSONObject().fluentPut("transaction", transaction));
    }

    protected JSONObject fail(Exception ex){
        return config.isDebugMode() ?
                new JSONObject().fluentPut("success", false).fluentPut("exception", ex) :
                new JSONObject().fluentPut("success", false).fluentPut("exception", ex.getMessage());
    }

    protected JSONObject jsonWithPublicKeyField(String publicKey){
        return new JSONObject().fluentPut("publicKey", publicKey);
    }

    protected JSONObject jsonFromObject(Object object){
        return JSONObject.parseObject(JSONObject.toJSONString(object));
    }
}
