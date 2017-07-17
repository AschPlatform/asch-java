package so.asch.sdk.impl;

/**
 * SDK配置类，包括接口版本号、服务地址等信息
 * @author eagle
 */
public class AschSDKConfig {
    private final static AschSDKConfig instance = new AschSDKConfig();
    public static AschSDKConfig getInstance(){ return instance; }
    private AschSDKConfig(){}


    private final static String version = "1.2.9";


    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getVersion(){ return version; }

    private String root;

}
