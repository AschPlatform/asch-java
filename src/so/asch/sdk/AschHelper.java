package so.asch.sdk;

import so.asch.sdk.impl.AschConst;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.impl.Validation;
import so.asch.sdk.security.SecurityStrategy;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class AschHelper {
    private static final AschFactory factory = AschFactory.getInstance();

    public String generateSecret(){
        AschResult result =  ((Account)factory.getService(Account.class)).newAccount(1);
        return !result.isSuccessful() ? null :
                result.parseMap().get("secret").toString();
    }

    public long amountForXAS(BigDecimal xas){
        return xas.multiply(BigDecimal.valueOf(AschConst.COIN)).longValue();
    }

    public long amountForCoins(int coins){
        return BigDecimal.valueOf(coins).multiply(BigDecimal.valueOf(AschConst.COIN)).longValue();
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
        calendar.add(Calendar.SECOND, timestamp);
        return calendar.getTime();
    }

    public Boolean isValidAddress(String address){
        return Validation.isValidAddress(address);
    }

    public boolean isValidBase58Address(String address){
        return  (null != address) && factory.getSecurity().isValidBase58Address(address);
    }
}
