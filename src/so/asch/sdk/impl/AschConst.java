package so.asch.sdk.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by eagle on 17-7-18.
 */
public final class AschConst {
    public static final int CLIENT_DRIFT_SECONDS = 5;
    public static final int COIN = 100000000;
    public static final char BASE58_ADDRESS_PREFIX = 'A';
    public static final int MIN_BASE58_ADDRESS_LEN = 30;
    public static final Date ASCH_BEGIN_EPOCH;


    static {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = df.parse("2016-06-27T20:00:00Z");
        } catch (Exception ex) {
            //never
        }
        ASCH_BEGIN_EPOCH = date;
    }
}
