package so.asch.sdk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import so.asch.sdk.dto.query.TransactionQueryParameters;


public class Main {

    public static void main(String[] args) {
        try {
            initLog();

            String url = "http://127.0.0.1:4096";
            String secret = "early sugar cannon mansion expose tunnel piece manual destroy exhaust helmet rather";
            String address = "7286541193277597873";
            String secondSecret = "asch111111";
            String userName = "asch_g2";

            //String secret2 = "raise cloud royal wonder cradle job gate wife depth device bitter hope";
            //asch_g101	11705168753296944226 00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9
            //asch_g96	16093201530526106149 0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366
            //asch_g32	4354832300657989346 0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75
            String[] voted = "00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9,0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366,0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75".split(",");

            //asch_g23	4752137788839516181 486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6
            //asch_g40	16494449392359591122 49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1
            String[] canceled = "486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6,49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1".split(",");
            String publicKey = AschSDK.Helper.getPublicKey(secret);

            AschSDK.Config.setAschServer(url);


            AschResult result = AschSDK.Delegate.registerDelegate(userName, secret, secondSecret);
            System.out.println(result.toString());

            result = AschSDK.Account.vote(voted, canceled, secret, secondSecret);
            TransactionQueryParameters parameters = new TransactionQueryParameters()
       //             .setRecipientId("XXX")
                    .setType(3)
                    .setLimit(10);
            System.out.println(result.toString());

            result = AschSDK.Transaction.queryTransactions(parameters);
            System.out.println(result.toString());


            for( int i=0; i<10; i++) {
                String newSecret = AschSDK.Helper.generateSecret();
                System.out.println(newSecret);
            }

            result = AschSDK.Account.transfer("11705168753296944226",
                    AschSDK.Helper.amountForCoins(1),
                    "Transfer by Test", secret, secondSecret);
            System.out.println(result.getRawJson());

            result = AschSDK.Block.getMilestone();
            System.out.println(result.toString());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    protected static void initLog(){
        if (AschSDK.Config.isDebugMode()) {
            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
            ctx.getConfiguration()
                    .getRootLogger()
                    .setLevel(Level.ALL);
        }
    }

}
