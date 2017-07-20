package so.asch.sdk.impl;

/**
 * Created by eagle on 17-7-18.
 */
public final class AschConst {
    public static final int CLIENT_DRIFT_SECONDS = 5;
    public static final int COIN = 100000000;
    public static final char BASE58_ADDRESS_PREFIX = 'A';

    public static class Fees{
        public static final long TRANSFER = 10000000;
        public static final long VOTE = 10000000;
        public static final long SECOND_SIGNATURE =500000000;
        public static final long MULTI_SIGNATURE =500000000;
        public static final long DELEGATE = 10000000000L;
        public static final long DAPP = 10000000000L;
    }
}
