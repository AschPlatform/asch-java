package so.asch.sdk.impl;

public final class AschServiceUrls {
     final static class Account {
        static final String LOGIN ="/api/accounts/open";
        static final String SECURE_LOGIN ="/api/accounts/open2";
        static final String GET_ACCOUNT ="/api/accounts";
        static final String GET_BALANCE ="/api/accounts/getBalance";
        static final String GENERATE_SECRET="/api/accounts/new";
    }


    final static class Block{
        static final String GET_BLOCK_INFO = "/api/v2/blocks/";
        static final String QUERY_BLOCKS = "/api/v2/blocks";
        static final String GET_HEIGHT = "/api/blocks/getHeight";
        static final String GET_FREE = "/api/blocks/getFee";
        static final String GET_MILESTONE = "/api/blocks/getMilestone";
        static final String GET_REWARD = "/api/blocks/getReward";
        static final String GET_SUPPLY = "/api/blocks/getSupply";
        static final String GET_STATUS = "/api/blocks/getStatus";
    }

    final static class Transaction{
        static final String QUERY_TRANSACTIONS = "/api/v2/transactions";
        static final String GET_TRANSACTION = "/api/v2/transactions/";
        static final String GET_UNCONFIRMED_TRANSACTION = "/api/transactions/unconfirmed/get";
        static final String GET_UNCONFIRMED_TRANSACTIONS = "/api/transactions/unconfirmed";
        static final String CREATE_TRANSACTION = "/api/transactions";
    }

    final static class Peer{
        static final String BROADCAST_TRANSACTION = "/peer/transactions";
    }

}
