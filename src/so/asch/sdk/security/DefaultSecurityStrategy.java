package so.asch.sdk.security;

import net.i2p.crypto.eddsa.EdDSAPublicKey;
import so.asch.sdk.codec.Decoding;
import so.asch.sdk.codec.Encoding;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dbc.ContractException;
import so.asch.sdk.dto.AssetInfo;
import so.asch.sdk.dto.TransactionInfo;
import so.asch.sdk.impl.AschConst;
import so.asch.sdk.impl.Validation;
import so.asch.sdk.security.ripemd.RipeMD160;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;

/**
 * Created by eagle on 17-7-18.
 */
public class DefaultSecurityStrategy implements SecurityStrategy{

    private static final int MAX_BUFFER_LENGTH = 1024;
    private static final byte[] EMPTY_BUFFER = new byte[0];


    private static final String SHA256_DIGEST_ALGORITHM = "SHA-256";

    private static MessageDigest sha256Digest;
    private static RipeMD160 ripemd160Digest;

    static
    {
        try {
            sha256Digest = MessageDigest.getInstance(SHA256_DIGEST_ALGORITHM);
            ripemd160Digest = new RipeMD160();
        }
        catch (Exception ex) {
            //
        }
    }

    @Override
    public KeyPair generateKeyPair(String secure) throws SecurityException {
        try {
            Argument.notNullOrEmpty(secure, "secure");
            byte[] hash = sha256Hash(Decoding.utf8(secure));
            return Ed25519.generateKeyPairBySeed(hash);
        }
        catch (Exception ex){
            throw new SecurityException("generate key pair failed", ex);
        }
    }

    @Override
    public String generatePublicKey(String secure)throws SecurityException {
        EdDSAPublicKey publicKey = (EdDSAPublicKey)generateKeyPair(secure).getPublic();
        return Encoding.hex(publicKey.getAbyte());
    }

    @Override
    public String getAddress(String publicKey) throws SecurityException{
        try{
            Argument.require(Validation.isValidPublicKey(publicKey), "invalid public key");

            byte[] hash1 = sha256Hash(Decoding.hex(publicKey));
            byte[] hash2 = ripemd160Hash(hash1);
            return AschConst.BASE58_ADDRESS_PREFIX + Encoding.base58(hash2);
        }
        catch (Exception ex){
            throw new SecurityException("generate key pair failed", ex);
        }
    }

    @Override
    public String generateTransactionId(TransactionInfo transaction, PrivateKey privateKey)throws SecurityException {
        //todo:generateTransactionId;
        return "";
    }

    @Override
    public String Signature(TransactionInfo transaction, PrivateKey privateKey) throws SecurityException {
        try {
            Argument.require(transaction != null, "transaction can not be null");
            Argument.require(privateKey != null, "private key can not be null");

            byte[] transactionBytes = getTransactionBytes(transaction, false, false);
            byte[] hash = sha256Hash(transactionBytes);
            return Encoding.hex(Ed25519.signature(hash, privateKey));
        }
        catch (Exception ex){
            throw new SecurityException("signature transaction failed", ex);
        }
    }

    @Override
    public String SignSignature(TransactionInfo transaction, PrivateKey privateKey) throws SecurityException {
        try {
            Argument.require(transaction != null, "transaction can not be null");
            Argument.require(privateKey != null, "private key can not be null");

            byte[] transactionBytes = getTransactionBytes(transaction, true, false);
            byte[] hash = sha256Hash(transactionBytes);
            return Encoding.hex(Ed25519.signature(hash, privateKey));
        }
        catch (Exception ex){
            throw new SecurityException("signature transaction failed", ex);
        }
    }

    @Override
    public long getTimestamp() {
        return System.currentTimeMillis()/1000 - AschConst.CLIENT_DRIFT_SECONDS;
    }

    private byte[] sha256Hash(byte[] message){
        sha256Digest.update(message);
        return sha256Digest.digest();
    }

    private byte[] ripemd160Hash(byte[] message){
        ripemd160Digest.update(message);
        return ripemd160Digest.digest();
    }

    private byte[] getSignatueBytes(TransactionInfo transaction){
        return EMPTY_BUFFER;
    }

    private byte[] getTransactionBytes(TransactionInfo transaction, boolean containsSignature,
                                       boolean containsSignSignature) throws ContractException{
        Argument.notNull(transaction, "transaction");
        Argument.notNull(transaction.getTransactionType(), "transaction.transactionType");

        //type(1)|timestamp(4)|senderPublicKey(32)|requesterPublicKey(32)|recipientId(32)|amount(8)|
        //asset(?)|signature(32)|signSignature(32)

        ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_LENGTH)
                .put(transaction.getType().byteValue())
                .putInt(transaction.getTimestamp())
                .put(unsafeDecodeHex(transaction.getSenderPublicKey()))
                .put(unsafeDecodeHex(transaction.getRequesterPublicKey()))
                .put(getRecipientIdBuffer(transaction.getRecipientId()))
                .putLong(transaction.getAmount())
                .put(getAssetBuffer(transaction));

        if (containsSignature){
            buffer.put(unsafeDecodeHex(transaction.getSignature()));
        }

        if (containsSignSignature){
            buffer.put(unsafeDecodeHex(transaction.getSignSignature()));
        }

        return buffer.array();
    }

    private byte[] getRecipientIdBuffer(String recipientId){
        //todo:getRecipientIdBuffer
        return EMPTY_BUFFER;
    }

    private byte[] getAssetBuffer(TransactionInfo transaction){
        ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_LENGTH);
        AssetInfo asset = transaction.getAsset();

        switch (transaction.getTransactionType()){
            case Signature:
                //todo:getSignatureBytes
                //buffer.put(unsafeDecodeHex(asset.getSignature()));
                break;

            case Delegate:
                buffer.put(unsafeDecodeUTF8(asset.getDelegate().getUserName()));
                break;

            case Vote:
                buffer.put(unsafeDecodeUTF8(asset.getVote().getVote()));
                break;

            case MultiSignature:
                buffer.put(asset.getMultiSignature().getMin().byteValue());
                buffer.put(asset.getMultiSignature().getLifetime().byteValue());
                String keys = String.join("",asset.getMultiSignature().getKeysGroup());
                buffer.put(unsafeDecodeUTF8(keys));
                break;

            case Dapp:
                buffer.put(getDappBytes(asset.getDapp()));
                break;

            case InTransfer:
                buffer.put(getInTransferBytes(asset.getInTransfer()));
                break;

            case OutTransfer:
                buffer.put(getOutTransferBytes(asset.getOutTransfer()));
                break;

            case Store:
                buffer.put(unsafeDecodeHex(asset.getStore().getContent()));
                break;

            default:
                break;
        }
        return buffer.array();
    }

    private byte[] getDappBytes(AssetInfo.DappInfo dapp){
        //todo:getDappBytes
        return EMPTY_BUFFER;
    }

    private byte[] getInTransferBytes(AssetInfo.TransferInfo transfer){
        //todo:getInTransferBytes
        return EMPTY_BUFFER;
    }

    private byte[] getOutTransferBytes(AssetInfo.TransferInfo transfer){
        //todo:getOutTransferBytes
        return EMPTY_BUFFER;
    }

    private byte[] unsafeDecodeHex(String hexString){
        if (null == hexString)
            return EMPTY_BUFFER;
        try{
            return Decoding.hex(hexString);
        }
        catch (Exception ex){
            return EMPTY_BUFFER;
        }
    }

    private byte[] unsafeDecodeUTF8(String utf8String){
        if (null == utf8String)
            return EMPTY_BUFFER;
        try{
            return Decoding.utf8(utf8String);
        }
        catch (Exception ex){
            return EMPTY_BUFFER;
        }
    }
}
