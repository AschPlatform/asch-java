package so.asch.sdk.transaction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import so.asch.sdk.TransactionType;
import so.asch.sdk.codec.Decoding;
import so.asch.sdk.codec.Encoding;
import so.asch.sdk.impl.FeeCalculater;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by eagle on 17-7-16.
 */
public class TransactionInfo {
    private static final int BASE_BUFFER_SIZE = 256;

    public Integer getType() {
        return transactionType == null ? null : transactionType.getCode();
    }

    public TransactionInfo setTransactionType(TransactionType type) {
        this.transactionType = type;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public TransactionInfo setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public TransactionInfo setRequestorId(String requestorId) {
        this.requestorId = requestorId;
        return this;
    }


    public String getMessage() { return message; }

    public TransactionInfo setMessage(String message) {
        this.message = message;
        return this;
    }


    public Long getFee() {
        return fee;
    }

    public TransactionInfo setFee(Long fee) {
        this.fee = fee;
        return this;
    }

    public String getId() {
        return transactionId;
    }

    public TransactionInfo setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getSenderPublicKey() {
        return this.senderPublicKey;
    }

    public TransactionInfo setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
        return this;
    }

    @JSONField(serialize = false)
    public TransactionType getTransactionType() {
        return transactionType;
    }

    public String[] getSignatures() {
        return signatures;
    }

    public TransactionInfo setSignatures(String[] signatures) {
        this.signatures = signatures;
        return this;
    }

    public TransactionInfo setSignature(String signature) {
        this.setSignatures( new String[]{ signature } );
        return this;
    }

    public TransactionInfo calcFee() {
        this.setFee(FeeCalculater.calcFee(this));
        return this;
    }

    public String getSecondSignature() {
        return secondSignature;
    }

    public TransactionInfo setSecondSignature(String secondSignature) {
        this.secondSignature = secondSignature;
        return this;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public TransactionInfo setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Integer getMode() {
        return mode;
    }

    public TransactionInfo setMode(Integer mode) {
        this.mode = mode;
        return this;
    }

    @JSONField
    public Object[] getArgs() {
        return args;
    }

    public TransactionInfo setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    private String transactionId = null;
    private TransactionType transactionType = null;

    private String requestorId = null;
    private String senderId = null;
    private String senderPublicKey = null;
    private String message = null;
    private Integer timestamp = null;
    private Long fee = null;
    private Integer mode = null;

    private String[] signatures = null;
    private String secondSignature = null;
    private Object[] args = null;

    public byte[] getBytes(boolean skipSignature , boolean skipSignSignature){
        // 4 + 4 + 8 + 32 + 32 + ? + ? + 32 + 32
        // type(4)|timestamp(4)|fee(8)|senderId(32)|[requestorId(32)]|[message(?)]|
        // args(?)|signature(32)|[secondSignature(32)]

        final byte[] argsBuffer = getArgsBuffer();
        final byte[] messageBuffer = getMessageBuffer();
        final Integer bufferSize = BASE_BUFFER_SIZE + argsBuffer.length + messageBuffer.length;
        ByteBuffer buffer = ByteBuffer.allocate(bufferSize).order(ByteOrder.LITTLE_ENDIAN)
                .putInt(getType())
                .putInt(getTimestamp())
                .putLong(getFee())
                .put(getSenderIdBuffer())
                .put(getRequestorIdBuffer())
                .put(messageBuffer)
                .put(argsBuffer);

        if (!skipSignature){
            buffer.put(getSignaturesBuffer());
        }

        if (!skipSignSignature){
            buffer.put(Decoding.unsafeDecodeHex(getSecondSignature()));
        }

        buffer.flip();
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);

        return result;
    }


    private byte[] getMessageBuffer(){
       return Encoding.getUTF8Bytes(message);
    }

    private byte[] getSenderIdBuffer(){
        return Encoding.getUTF8Bytes(senderId);
    }

    private byte[] getRequestorIdBuffer(){
        return Encoding.getUTF8Bytes(requestorId);
    }

    private byte[] getArgsBuffer(){
        return Encoding.getUTF8Bytes(JSON.toJSONString(getArgs()));
    }

    //FIXME: multiSignatures
    private byte[] getSignaturesBuffer() { return Decoding.unsafeDecodeHex(getSignatures()[0]); }
}
