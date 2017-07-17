package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.AschInterface;

/**
 * Asch服务基类
 * @author eagle.asch
 */
public abstract class AschRESTService implements AschInterface{

    private static final SDKConfig config = SDKConfig.getInstance();

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

    protected JSONObject postMagic(String relativeUrl, JSONObject parameters){
        try{
            return REST.post(getFullUrl(relativeUrl), parameters, true, null);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    protected JSONObject fail(Exception ex){
        return new JSONObject()
                .fluentPut("success", false)
                .fluentPut("exception", ex);
    }
}
