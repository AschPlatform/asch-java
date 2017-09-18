package so.asch.sdk;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * SDK配置类，包括接口版本号、服务地址等信息
 * @author eagle
 */
public final class AschSDKConfig {

    private final static AschSDKConfig instance = new AschSDKConfig();
    public static AschSDKConfig getInstance(){ return instance; }
    private AschSDKConfig(){}


    private final static String sdkVersion = "1.3";

    public String getSDKVersion(){ return sdkVersion; }

    public String getAschServer() {
        return aschServer;
    }

    public void setAschServer(String aschServer) {
        this.aschServer = aschServer;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public boolean isLongTransactionIdEnabled() {
        return longTransactionIdEnabled;
    }

    public void setLongTransactionIdEnabled(boolean longTransactionIdEnabled) {
        this.longTransactionIdEnabled = longTransactionIdEnabled;
    }
    
    

    public int getPlatform() {
		return platform;
	}
	public void setPlatform(int platform) {
		this.platform = platform;
	}


	private String aschServer ="http://127.0.0.1:4096";
    private String magic = "aabbccdd"; //localnet
    private boolean longTransactionIdEnabled = true;
    private boolean debugMode = true;
    private int platform=1; //0 PC, 1 android

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
        another.debugMode = this.debugMode;
        another.aschServer = this.aschServer;
        another.longTransactionIdEnabled = this.longTransactionIdEnabled;
        another.platform=this.platform;
    }

}
