package so.asch.sdk;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.impl.AschSDKConfig;
import so.asch.sdk.security.SecurityStrategy;


public class Main {

    public static void main(String[] args) {
        try {
            String url = "http://127.0.0.1:4096";
            String secret = "early sugar cannon mansion expose tunnel piece manual destroy exhaust helmet rather";
            String address = "7286541193277597873";
            String secondSecret = "asch111111";
            String userName = "asch_g2";

            //asch_g101	11705168753296944226 00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9
            //asch_g96	16093201530526106149 0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366
            //asch_g32	4354832300657989346 0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75
            String[] voted = "00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9,0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366,0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75".split(",");

            //asch_g23	4752137788839516181 486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6
            //asch_g40	16494449392359591122 49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1
            String[] canceled = "486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6,49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1".split(",");
            SecurityStrategy security = AschFactory.getInstance().getSecurity();
            String publicKey = security.encodePublicKey(security.generateKeyPair(secret).getPublic());
            AschSDKConfig.getInstance().setRoot(url);
            Account account = AschFactory.getInstance().getService(Account.class);
            Delegate delegate = AschFactory.getInstance().getService(Delegate.class);

//            JSONObject json = delegate.registerDelegate(userName, secret, secondSecret);
//            System.out.println(json.toString());
//
//            json = account.vote(secret, secondSecret, new String[0], voted);
//            System.out.println(json.toString());
//
//            json = delegate.getDelegateByPublicKey(publicKey);
//            System.out.println(json.toString());
//
//            json = delegate.getDelegateByName(userName);
//            System.out.println(json.toString());

//            json = delegate.getDelegatesCount();
//            System.out.println(json.toString());

            JSONObject json = account.transfer("11705168753296944226", 1* 100000000,
                    "Transfer by Test", secret, secondSecret);
            System.out.println(json);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
