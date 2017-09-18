package so.asch.sdk.impl;

import so.asch.sdk.*;
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

    private static Map<Class<? extends AschInterface> , AschInterface> serviceContainer = new ConcurrentHashMap<>();

    /**
     * 注册服务类
     * @param interfaceType Asch接口类
     * @param serviceType Asch接口实现类
     * @return 工厂对象本身，实现链式访问
     * */
    protected AschFactory register(Class<? extends AschInterface> interfaceType, Class<? extends AschInterface> serviceType){
        try{
            serviceContainer.put(interfaceType, serviceType.newInstance());
        }
        catch (Exception ex){
        	ex.printStackTrace();
            //do nothing;
        }

        return this;
    }

    /**
     * 获取Asch接口服务实例
     * @param interfaceType 接口类型
     * @return 服务对象实例
     * */
    public <AI> AI getService(Class<? extends AschInterface> interfaceType){
        try {
            return (AI) serviceContainer.get(interfaceType);
        }
        catch (Exception ex){
            return null;
        }
    }

    public SecurityStrategy getSecurity(){ return securityStrategy; }

    static {
        getInstance()
                .register(Account.class, AccountService.class)
                .register(Delegate.class, DelegateService.class)
                .register(Block.class, BlockService.class)
                .register(Transaction.class, TransactionService.class)
                .register(Signature.class, SignatureService.class)
                .register(Dapp.class, DappService.class)
                .register(UIA.class, UIAService.class)
                .register(Peer.class, PeerService.class)
                .register(Misc.class, MiscService.class);
    }

}
