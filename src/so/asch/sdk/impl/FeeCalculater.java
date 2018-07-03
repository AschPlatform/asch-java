package so.asch.sdk.impl;

import so.asch.sdk.AschHelper;
import so.asch.sdk.TransactionType;
import so.asch.sdk.transaction.TransactionInfo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FeeCalculater {
    protected static Map<TransactionType, Long> staticFeeMap = new HashMap<TransactionType, Long>();
    static {
        String feeJS = 
                "  1: () => 0.1," +
                "  3: () => 5," +
                "  4: () => 0.1," +
                "  5: () => 0," +
                "  6: () => 5," +
                "  7: () => 100," +
                "  8: () => 0.1," +
                "  9: () => 0," +
                "  10: () => 100," +
                "  11: () => 0.1," +
                "  12: () => 0.1," +
                "  100: () => 100," +
                "  101: () => 500," +
                "  102: () => 0.1," +
                "  103: () => 0.1," +
                "  200: () => 100," +
                "  201: () => 1," +
                "  202: () => 1," +
                "  203: () => 1," +
                "  204: () => 0.1," +
                "  205: () => 0.1," +
                "  300: () => 10," +
                "  301: () => 0.1," +
                "  302: () => 0," +
                "  400: () => 0.1," +
                "  401: () => 100," +
                "  402: () => 0.01," +
                "  403: () => 0," +
                "  404: () => 0.01," +
                "  405: () => 0.01," +
                "  406: () => 0.01," +
                "  500: () => 0," +
                "  501: () => 0," +
                "  502: () => 1," +
                "  503: () => 1," +
                "  504: () => 1";
        String[] pairs = feeJS.replace(" ", "")
                .replace("()", "")
                .replace("=>", "")
                .split(",");
        AschHelper helper = new AschHelper();

        for( int i = 0; i< pairs.length; i++ ) {
            if (pairs[i].trim() == "") continue;
            String[] item = pairs[i].split(":");
            int typeCode = Integer.parseInt(item[0]);
            double fee = Double.parseDouble(item[1]);

            staticFeeMap.put(transactionTypeFromCode(typeCode), helper.amountForXAS(BigDecimal.valueOf(fee)));
        }
    }

    protected static TransactionType transactionTypeFromCode( int typeCode ) {
        TransactionType type = TransactionType.unknow;
        try{
            type = TransactionType.fromCode(typeCode);
        }
        catch (Exception ex) {
            // do nothing
        }
        return type;
    }

    public static long calcFee( TransactionInfo trans ) {
        if ( trans.getType() == TransactionType.basic_setName.getCode() ){
            return calcSetNameFee( (String) trans.getArgs()[0] );
        }
        return staticFeeMap.get(transactionTypeFromCode(trans.getType()));
    }

    protected static long calcSetNameFee( String name ) {
        int[] feeSteps = new int[]{
        //-----------------------------//
                10000, // 0
                1000,  // 1
        //-----------------------------//
                200,   // 2
                100,   // 3
                80,    // 4
                40,    // 5
                10,    // 6
                10,    // 7
                10,    // 8
                10,    // 9
                10     // 10
        };

        int len = name.length();
        int coins = len > 10 ? 1 : feeSteps[len];
        return new AschHelper().amountForCoins(coins);
    }

}
