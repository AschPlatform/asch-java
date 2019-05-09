package so.asch.sdk.dto.query;

import so.asch.sdk.TransactionType;

import java.beans.Transient;

/**
 * Created by eagle on 17-7-16.
 */
public class TransactionQueryParameters extends QueryParameters {
    public Integer getType() {
        return transactionType == null ? null : transactionType.getCode();
    }

    @Transient
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public TransactionQueryParameters setTransactionType(TransactionType type) {
        this.transactionType = type;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public TransactionQueryParameters setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public TransactionQueryParameters setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }


    public Integer getHeight() {
        return height;
    }

    public TransactionQueryParameters setHeight(Integer height) {
        this.height = height;
        return this;
    }

    private TransactionType transactionType = null;

    private Integer height = null;
    private String senderId = null;
    private String message = null;

}