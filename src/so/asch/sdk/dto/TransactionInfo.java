package so.asch.sdk.dto;

import so.asch.sdk.TransactionType;

/**
 * Created by eagle on 17-7-16.
 */
public class TransactionInfo {

    public Integer getType() {
        return transactionType == null ? null : transactionType.getCode();
    }

    public TransactionInfo setTransactionType(TransactionType type) {
        this.transactionType = type;
        return this;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public TransactionInfo setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
        return this;
    }

    public String getRequesterPublicKey() {
        return requesterPublicKey;
    }

    public TransactionInfo setRequesterPublicKey(String requesterPublicKey) {
        this.requesterPublicKey = requesterPublicKey;
        return this;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public TransactionInfo setRecipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public TransactionInfo setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Integer getFee() {
        return fee;
    }

    public TransactionInfo setFee(Integer fee) {
        this.fee = fee;
        return this;
    }


    public String getTransactionId() {
        return transactionId;
    }

    public TransactionInfo setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String getSignature() {
        return signature;
    }

    public TransactionInfo setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getSignSignature() {
        return signSignature;
    }

    public TransactionInfo setSignSignature(String signSignature) {
        this.signSignature = signSignature;
        return this;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public TransactionInfo setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public AssetInfo getAsset() {
        return assetInfo;
    }

    public TransactionInfo setAsset(AssetInfo assertInfo) {
        this.assetInfo = assertInfo;
        return this;
    }

    private String transactionId = null;
    private TransactionType transactionType = null;
    private String recipientId = null;

    private String requesterPublicKey = null;
    private String senderPublicKey = null;
    private Integer timestamp = null;
    private Long amount = null;
    private Integer fee = null;

    private String signature = null;
    private String signSignature = null;

    private AssetInfo assetInfo = null;
}
