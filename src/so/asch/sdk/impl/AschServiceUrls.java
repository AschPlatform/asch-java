package so.asch.sdk.impl;

public final class AschServiceUrls {
     final static class Account {
        static final String LOGIN ="/api/accounts/open";
        static final String SECURE_LOGIN ="/api/accounts/open2";
        static final String GET_ACCOUNT ="/api/accounts";
        static final String GET_BALANCE ="/api/accounts/getBalance";
        static final String GET_PUBLIC_KEY ="/api/accounts/getPublickey";
        static final String GENERATE_PUBLIC_KEY ="/api/accounts/generatePublicKey";
        static final String GET_VOTED_DELEGATES ="/api/accounts/delegates";
        static final String GET_DELEGATE_FEE ="/api/accounts/delegates/fee";
        static final String GET_TOP_ACCOUNTS ="/api/accounts/top";
    }

    final static class Delegate{
        static final String GET_DELEGATES_COUNT ="/api/delegates/count";
        static final String GET_VOTES ="/api/delegates/voters";
        static final String GET_DELEGATE ="/api/delegates/get";
        static final String QUERY_DELEGATES ="/api/delegates";
        static final String GET_DELEGATE_FEE ="/api/delegates/fee";
        static final String GET_FORGING = "/api/delegates/forging/getForgedByAccount";
        static final String ENABLE_FORGE = "/api/delegates/forging/enable";
        static final String DISABLE_FORGE = "/api/delegates/forging/disable";
        static final String GET_FORGING_STATUS ="/api/delegates/forging/status";
    }

    final static class Block{
        static final String GET_BLOCK_INFO = "/api/blocks/get";
        static final String GET_FULL_BLOCK_INFO = "/api/blocks/full";
        static final String QUERY_BLOCKS = "/api/blocks/";
        static final String GET_HEIGHT = "/api/blocks/getHeight";
        static final String GET_FREE="/api/blocks/getFee";
        static final String GET_MILESTONE = "/api/blocks/getMilestone";
        static final String GET_REWARD = "/api/blocks/getReward";
        static final String GET_SUPPLY = "/api/blocks/getSupply";
        static final String GET_STATUS = "/api/blocks/getStatus";

    }

    final static class Transaction{
        static final String QUERY_TRANSACTIONS = "/api/transactions";
        static final String GET_TRANSACTION = "/api/transactions/get";
        static final String GET_UNCONFIRMED_TRANSACTION = "/api/transactions/unconfirmed/get";
        static final String GET_UNCONFIRMED_TRANSACTIONS = "/api/transactions/unconfirmed";
        static final String CREATE_TRANSACTION = "/api/transactions";
    }

    final static class Peer{
        static final String BROADCAST_TRANSACTION = "/peer/transactions";
        static final String QUERY_PEERS = "/api/peer/getPeers";
        static final String GET_PEER = "/api/peer/get";
        static final String GET_VERSION = "/api/peer/version";
    }

    final static class Signature{
        static final String GET_SIGNATURE_FEE = "/api/signatures/fee";
        //static final String SET_SIGNATURE = "/api/signatures";

        //static final String SET_MULTI_SIGNATURE = "/api/multisignatures/addMultisignature";
        static final String MULTI_SIGNATURE = "/api/multisignatures/sign";
        static final String GET_PENDING_TRANSACTIONS = "/api/multisignatures/pending";
        static final String GET_MULTI_SIGNATURE_ACCOUNTS = "/api/multisignatures/getAccounts";
    }

    final static class Misc{
        static final String  GET_LOAD_STATUS ="/api/loader/status";
        static final String  GET_SYNC_STATUS ="/api/loader/status/sync";
        static final String  GET_STORED_DATA ="/api/storages";
        //static final String  GET_LOAD_STATUS ="";
    }

    final static class UIA{
        static final String GET_ISSUERS = "/api/uia/issuers";
        static final String GET_ISSUER = "/api/uia/issuers/${IssuerName}";
        static final String QUERY_ISSUER_ASSETS = "/api/uia/issuers/${IssuerName}/assets";
        static final String GET_ASSETS = "/api/uia/assets";
        static final String GET_ASSET= "/api/uia/assets/${AssetName}";
        static final String GET＿ASSET＿ACL = "/api/uia/assets/${AssetName}/acl/flag";
        static final String GET_ADDRESS_BALANCES = "/api/uia/balances/";
        static final String GET_TRANSACTIONS = "/api/uia/transactions";
    }

}
