package so.asch.sdk;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.impl.AschSDKConfig;
import so.asch.sdk.security.SecurityStrategy;


public class Main {

    public static void main(String[] args) {
        try {
            String url = "http://testnet.asch.so:4096";
            String secret = "nice gold mountain garden actual kick utility talk glass image install flat";
            String address = "AN9rQVu4o8vDbmz2xcsogPHxoTwZSpj5RK";
            String secondSecret = "";

            AschSDKConfig.getInstance().setRoot(url);
            Account account = AschFactory.getInstance().getService(Account.class);
            SecurityStrategy security = AschFactory.getInstance().getSecurity();
            JSONObject json = account.getAccount(address);

            System.out.println(json.toString());
            System.out.println(json.getBoolean("success"));

            json = account.vote(
                    secret,
                    security.generatePublicKey(secret),
                    secondSecret,
                    new String[0],
                    new String[0]
            );
            System.out.println(json.toString());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
