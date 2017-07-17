package so.asch.sdk.impl;

/**
 * SDK配置类，包括接口版本号、服务地址等信息
 * @author eagle.asch
 */
public class SDKConfig {
    private final static SDKConfig instance = new SDKConfig();
    public static SDKConfig getInstance(){ return instance; }
    private SDKConfig(){}


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
