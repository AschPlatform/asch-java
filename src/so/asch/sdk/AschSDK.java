package so.asch.sdk;

import so.asch.sdk.impl.AschFactory;

/**
 * Asch接口门面类，对外提供简单的访问模式
 * @author eagle
 *
 */
public final class AschSDK {
    public final static Account Account;
    public final static Transaction Transaction;
    public final static Block Block;


    public final static AschSDKConfig Config = AschSDKConfig.getInstance();
    public final static AschHelper Helper = new AschHelper();

    static {
        AschFactory factory = AschFactory.getInstance();

        Account = factory.getService(so.asch.sdk.Account.class);
        Block = factory.getService(so.asch.sdk.Block.class);
        Transaction = factory.getService(so.asch.sdk.Transaction.class);
    }

}
