package so.asch.sdk;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.impl.AschSDKConfig;
import so.asch.sdk.security.SecurityStrategy;

/**
 * Created by eagle on 17-7-16.
 */
public class TestHelper {

    public static final String root = "http://127.0.0.1:4096";
    public static final String secret = "early sugar cannon mansion expose tunnel piece manual destroy exhaust helmet rather";
    public static final String address = "7286541193277597873";
    public static final String secondSecret = "asch111111";
    public static final String userName = "asch_g2";

    //asch_g101 11705168753296944226 00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9
    public static final String targetAddress = "11705168753296944226";

    //asch_g101	11705168753296944226 00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9
    //asch_g96	16093201530526106149 0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366
    //asch_g32	4354832300657989346 0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75
    public static final String[] voted = "00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9,0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366,0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75".split(",");

    //asch_g23	4752137788839516181 486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6
    //asch_g40	16494449392359591122 49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1
    public static final String[] canceled = "486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6,49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1".split(",");

    static {

        AschSDKConfig.getInstance().setRoot(root);
    }

    public static Account accountService(){return AschFactory.getInstance().getService(Account.class);}
    public static Block blockService(){return AschFactory.getInstance().getService(Block.class);}
    public static Dapp dappService(){return AschFactory.getInstance().getService(Dapp.class);}
    public static Delegate delegateService(){return AschFactory.getInstance().getService(Delegate.class);}
    public static Peer peerService(){return AschFactory.getInstance().getService(Peer.class);}
    public static Transaction transactionService(){return AschFactory.getInstance().getService(Transaction.class);}
    public static Signature signatureService(){return AschFactory.getInstance().getService(Signature.class);}
    public static SecurityStrategy security(){return AschFactory.getInstance().getSecurity();}
    public static String publicKey(){
        try {
            return security().encodePublicKey(security().generateKeyPair(secret).getPublic());
        }
        catch (Exception ex){
            return "";
        }
    }

    public static boolean isSuccess(JSONObject json){
        return json != null && json.getBoolean("success");
    }
}
