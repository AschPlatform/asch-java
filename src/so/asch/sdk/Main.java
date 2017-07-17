package so.asch.sdk;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.impl.SDKConfig;


public class Main {

    public static void main(String[] args) {
        try {
            String url = "http://testnet.asch.so:4096";
            String secret = "nice gold mountain garden actual kick utility talk glass image install flat";
            String address = "AN9rQVu4o8vDbmz2xcsogPHxoTwZSpj5RK";

            SDKConfig.getInstance().setRoot(url);
            Account account = AschFactory.getInstance().getService(Account.class);
            JSONObject json = account.getAccount(address);

            System.out.println(json.toJSONString());
            System.out.println(json.getBoolean("success"));

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }
}
