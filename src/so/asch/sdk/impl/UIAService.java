package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.UIA;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.transaction.TransactionInfo;

public class UIAService extends AschRESTService implements UIA {
    @Override
    public JSONObject getIssuers(int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");

            return get(AschServiceUrls.UIA.GET_ISSUERS ,createLimitAndOffsetParameters(limit, offset));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getIssuer(String nameOrAddress) {
        try{
            Argument.notNullOrEmpty(nameOrAddress, "invalid nameOrAddress");

            return get(issuerUrl(AschServiceUrls.UIA.GET_ISSUER, nameOrAddress));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject queryIssuerAssets(String nameOrAddress, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.notNullOrEmpty(nameOrAddress, "invalid nameOrAddress");

            JSONObject parameters = createLimitAndOffsetParameters(limit, offset);

            return get(issuerUrl(AschServiceUrls.UIA.QUERY_ISSUER_ASSETS, nameOrAddress), parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getAssets(int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");

            return get(AschServiceUrls.UIA.GET_ASSETS, createLimitAndOffsetParameters(limit, offset));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getAsset(String assetName) {
        try {
            Argument.notNullOrEmpty(assetName, "assetName");

            return get(assetUrl(AschServiceUrls.UIA.GET_ASSET, assetName));
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getAssetACL(String assetName, boolean whiteOrBlack, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.notNullOrEmpty(assetName, "invalid assetName");

            JSONObject parameters = createLimitAndOffsetParameters(limit, offset)
                    .fluentPut("name", assetName)
                    .fluentPut("flag", whiteOrBlack);

            return get(assetUrl(AschServiceUrls.UIA.GET＿ASSET＿ACL,assetName), parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getAddressBalances(String address, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.require(Validation.isValidAddress(address), "invalid address");

            JSONObject parameters = createLimitAndOffsetParameters(limit, offset)
                    .fluentPut("address", address);

            return get(AschServiceUrls.UIA.GET_ADDRESS_BALANCES + address, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getTransactions(String ownerPublicKey, int limit, int offset) {
        try {
            Argument.require(Validation.isValidLimit(limit), "invalid limit");
            Argument.require(Validation.isValidOffset(offset), "invalid offset");
            Argument.notNullOrEmpty(ownerPublicKey, "invalid ownerPublicKey");

            JSONObject parameters = createLimitAndOffsetParameters(limit, offset)
                    .fluentPut("publicKey", ownerPublicKey);

            return get(AschServiceUrls.UIA.GET_TRANSACTIONS, parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject createIssuer(String name, String desc, String secret, String secondSecret) {
        return null;
    }

    @Override
    public JSONObject createAsset(String currency, String desc, long maximum, byte precision, String strategy, String secret, String secondSecret) {
        return null;
    }

    @Override
    public JSONObject setAssetACL(String currency, int assertStatus, boolean whiteListMode, String secret, String secondSecret) {
        return null;
    }

    @Override
    public JSONObject issue(String currency, long amount, String secret, String secondSecret) {
        return null;
    }

    @Override
    public JSONObject transfer(String currency, String recipientId, long amount, String message, String secret, String secondSecret) {
        try {
            Argument.notNullOrEmpty(currency, "invalid currency");
            Argument.require(Validation.isValidAddress(recipientId), "invalid recipientId");
            Argument.require(Validation.isValidSecure(secret), "invalid secret");
            Argument.optional(secondSecret, Validation::isValidSecure, "invalid second secret");

            TransactionInfo transaction = getTransactionBuilder()
                    .buildUIATransfer(currency, amount, recipientId, message, secret, secondSecret);

            return broadcastTransaction(transaction);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject setAssetStatus(String currency, int assertStatus, boolean whiteListMode, String secret, String secondSecret) {
        return null;
    }

    private  JSONObject createLimitAndOffsetParameters(int limit, int offset){
        return new JSONObject()
                .fluentPut("limit", limit)
                .fluentPut("offset", offset);
    }

    private String assetUrl(String baseUrl, String assetName){
        return baseUrl.replace("${AssetName}", assetName);
    }

    private String issuerUrl(String baseUrl, String issuerName){
        return baseUrl.replace("${IssuerName}", issuerName);
    }
}
