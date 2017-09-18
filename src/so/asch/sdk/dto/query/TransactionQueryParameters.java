package so.asch.sdk.dto.query;

import so.asch.sdk.TransactionType;

import java.beans.Transient;

/**
 * Created by eagle on 17-7-16.
 */
public class TransactionQueryParameters extends QueryParameters {
    //blockId	string	N	区块id
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //type	integer	N	交易类型,0:普通转账，1:设置二级密码，2:注册受托人，3:投票，4:多重签名，5:DAPP，6:IN_TRANSFER，7:OUT_TRANSFER
    //orderBy	string	N	根据表中字段排序，senderPublicKey:desc
    //offset	integer	N	偏移量，最小值0
    //senderPublicKey	string	N	发送者公钥
    //ownerPublicKey	string	N
    //ownerAddress	string	N
    //senderId	string	N	发送者地址
    //recipientId	string	N	接收者地址,最小长度：1
    //amount	integer	N	金额
    //fee	integer	N	手续费

    public String getBlockId() {
        return blockId;
    }

    public TransactionQueryParameters setBlockId(String blockId) {
        this.blockId = blockId;
        return this;
    }

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

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public TransactionQueryParameters setSednderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
        return this;
    }

    public String getOwnerPublicKey() {
        return ownerPublicKey;
    }

    public TransactionQueryParameters setOwnerPublicKey(String ownerPublicKey) {
        this.ownerPublicKey = ownerPublicKey;
        return this;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public TransactionQueryParameters setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public TransactionQueryParameters setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public TransactionQueryParameters setRecipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public TransactionQueryParameters setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Integer getFee() {
        return fee;
    }

    public TransactionQueryParameters setFee(Integer fee) {
        this.fee = fee;
        return this;
    }

    private String blockId = null;
    private TransactionType transactionType = null;
    private String senderPublicKey = null;
    private String ownerPublicKey = null;
    private String ownerAddress = null;
    private String senderId = null;
    private String recipientId = null;
    private Long amount = null;
    private Integer fee = null;
}