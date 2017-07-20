package so.asch.sdk.impl;

import so.asch.sdk.Account;
import so.asch.sdk.AschInterface;
import so.asch.sdk.Delegate;
import so.asch.sdk.security.DefaultSecurityStrategy;
import so.asch.sdk.security.SecurityStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 简单的Asch服务对象工厂，可替换成DI工具管理
 * @author eagle
  */
public final class AschFactory {
    private final static AschFactory instance = new AschFactory();

    private AschFactory(){ }

    public static AschFactory getInstance(){
        return instance;
    }

    private final SecurityStrategy securityStrategy = new DefaultSecurityStrategy();

    private static Map<Class<? extends AschInterface> , Class<? extends AschRESTService>> implMap = new ConcurrentHashMap<>();

    /**
     * 注册服务类
     * @param interfaceType Asch接口类
     * @param serviceType Asch接口实现类
     * @return 工厂对象本身，实现链式访问
     * */
    protected AschFactory register(Class<? extends AschInterface> interfaceType, Class<? extends AschRESTService> serviceType){
        implMap.put(interfaceType, serviceType);
        return this;
    }

    /**
     * 获取Asch接口服务实例
     * @param interfaceType 接口类型
     * @return 服务对象实例
     * */
    public <AschInterface> AschInterface getService(Class<? extends AschInterface> interfaceType){
        try {
            return (AschInterface) implMap.get(interfaceType).newInstance();
        }
        catch (Exception ex){
            return null;
        }
    }

    public SecurityStrategy getSecurity(){ return securityStrategy; }

    static {
        getInstance()
                .register(Account.class, AccountService.class)
                .register(Delegate.class, DelegateService.class);
    }

}
