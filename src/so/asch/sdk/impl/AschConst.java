package so.asch.sdk.impl;

import java.math.BigInteger;

/**
 * Created by eagle on 17-7-18.
 */
public final class AschConst {
    public static final int CLIENT_DRIFT_SECONDS = 5;
    public static final int COIN = 100000000;
    public static final char BASE58_ADDRESS_PREFIX = 'A';

    public static class Fees{
        public static final int TRANSFER = 10000000;
        public static final int VOTE = 10000000;
        public static final int SECOND_SIGNATURE =500000000;
        public static final int MULTI_SIGNATURE =500000000;
        public static final BigInteger DELEGATE = new BigInteger("10000000000");
        public static final BigInteger DAPP = new BigInteger("10000000000");
    }
}
