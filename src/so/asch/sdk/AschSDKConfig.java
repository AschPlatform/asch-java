package so.asch.sdk;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.util.DebugHelper;

/**
 * SDK配置类，包括接口版本号、服务地址等信息
 * @author eagle
 */
public final class AschSDKConfig {

    private final static AschSDKConfig instance = new AschSDKConfig();
    public static AschSDKConfig getInstance(){ return instance; }
    private AschSDKConfig(){}


    private final static String sdkVersion = "1.4";

    public String getSDKVersion(){ return sdkVersion; }

    public String getAschServer() {
        return aschServer;
    }

    public void setAschServer(String aschServer) {
        this.aschServer = aschServer;
    }

    public boolean isDebugLogEnabled() {
        return debugLogEnabled;
    }

    public void setDebugLogEnabled(boolean enabled) {
        this.debugLogEnabled = enabled;
        DebugHelper.setEnabled(enabled);
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }


    private String aschServer ="http://127.0.0.1:4096";
    private String magic = "aabbccdd"; //localnet
    private boolean debugLogEnabled = false;

    public boolean tryLoadFromJson(String jsonString){
        try{
            loadFromJson(jsonString);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public void loadFromJson(String jsonString) throws JSONException{
        AschSDKConfig config = JSONObject.parseObject(jsonString, AschSDKConfig.class);
        config.copyTo(instance);
    }

    protected void copyTo(AschSDKConfig another){
        another.magic = this.magic;
        another.debugLogEnabled = this.debugLogEnabled;
        another.aschServer = this.aschServer;
    }

}
