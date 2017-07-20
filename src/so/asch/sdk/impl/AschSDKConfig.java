package so.asch.sdk.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * SDK配置类，包括接口版本号、服务地址等信息
 * @author eagle
 */
public class AschSDKConfig {
    private final static AschSDKConfig instance = new AschSDKConfig();
    public static AschSDKConfig getInstance(){ return instance; }
    private AschSDKConfig(){}


    private final static String version = "1.3";
    private final static Map<String,String> magicHeaders = new HashMap<>();
    static {
        magicHeaders.put("magic", "aabbccdd");  //localnet
        magicHeaders.put("version", "");
    }

    public String getVersion(){ return version; }

    public Map<String, String> getMagicHeaders(){
        return magicHeaders;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isLongTransactionIdEnabled() {
        return longTransactionIdEnabled;
    }

    public void setLongTransactionIdEnabled(boolean longTransactionIdEnabled) {
        this.longTransactionIdEnabled = longTransactionIdEnabled;
    }

    private String root ="127.0.0.1:4096";
    private boolean longTransactionIdEnabled = true;
    private boolean debugMode = true;
}
