package so.asch.sdk;

import so.asch.sdk.impl.AschConst;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.security.SecurityStrategy;

import java.util.Calendar;
import java.util.Date;

public class AschHelper {
    private static final AschFactory factory = AschFactory.getInstance();

    public String generateSecret(){
        return factory.getSecurity().generateSecret();
    }

    public long amountForCoins(int coins){
        return coins * AschConst.COIN;
    }

    public String getPublicKey(String secret){
        SecurityStrategy security = factory.getSecurity();
        if (!security.isValidSecret(secret))
            return null;

        try {
            return security.encodePublicKey(security.generateKeyPair(secret).getPublic());
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public  Date dateFromAschTimestamp(int timestamp){
        Date beginEpoch =  AschConst.ASCH_BEGIN_EPOCH ;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginEpoch);
        calendar.add(Calendar.MILLISECOND, timestamp);
        return calendar.getTime();
    }
}
