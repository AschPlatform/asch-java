package so.asch.sdk;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.impl.AschSDKConfig;
import so.asch.sdk.security.SecurityStrategy;

/**
 * Created by eagle on 17-7-16.
 */
public class TestHelper {

    public static final String address = "AN9rQVu4o8vDbmz2xcsogPHxoTwZSpj5RK";
    public static final String secret = "nice gold mountain garden actual kick utility talk glass image install flat";
    public static final String root ="http://testnet.asch.so:4096";
    public static final String secondSecret = "asch123456";
    //asch_g46	11311532344948918824,
    //asch_g5	12131151007585128653,
    //sportshark	A3etGK19j3K9X9x4LpMJf9aDkwhKwiEwuy
    public static final String[] votedPublicKeys = new String[]{"11311532344948918824","12131151007585128653","A3etGK19j3K9X9x4LpMJf9aDkwhKwiEwuy"};
    //wanglu	A5pcftQKeNZmGa7disXZGarfnycRx6iT6C
    //asch_g54	14552564717140225834
    public static final String[] cancelVotedPublicKeys = new String[]{"A5pcftQKeNZmGa7disXZGarfnycRx6iT6C","14552564717140225834"};

    private static SecurityStrategy security;
    private static Account account;
    private static Block block;
    private static Dapp dapp;
    private static Delegate delegate;
    private static Peer peer;
    private static Transaction transaction;
    private static Signature signature;

    static
    {
        try {
            AschSDKConfig.getInstance().setRoot(root);
            AschFactory factory = AschFactory.getInstance();

            account = factory.getService(Account.class);
            block = factory.getService(Block.class);
            dapp = factory.getService(Dapp.class);
            delegate = factory.getService(Delegate.class);
            peer = factory.getService(Peer.class);
            transaction = factory.getService(Transaction.class);
            signature = factory.getService(Signature.class);

            security =factory.getSecurity();
        }
        catch (Exception ex){
        }
    }

    public static Account accountService(){return account;}
    public static Block blockService(){return block;}
    public static Dapp dappService(){return dapp;}
    public static Delegate delegateService(){return delegate;}
    public static Peer peerService(){return peer;}
    public static Transaction transactionService(){return transaction;}
    public static Signature signatureService(){return signature;}
    public static SecurityStrategy security(){return security;}

    public static boolean isSuccess(JSONObject json){
        return json != null && json.getBoolean("success");
    }
}
