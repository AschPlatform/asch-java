package so.asch.sdk.security;

import net.i2p.crypto.eddsa.EdDSAPublicKey;

import so.asch.sdk.codec.Base58;
import so.asch.sdk.codec.Decoding;
import so.asch.sdk.codec.Encoding;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.impl.AschConst;
import so.asch.sdk.security.ripemd.RipeMD160;
import so.asch.sdk.transaction.TransactionInfo;
import so.asch.sdk.util.DebugHelper;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Date;


/**
 * Created by eagle on 17-7-18.
 */
public class DefaultSecurityStrategy implements SecurityStrategy{
    private static final String SHA256_DIGEST_ALGORITHM = "SHA-256";

    private static final MessageDigest sha256Digest;
    private static final RipeMD160 ripemd160Digest;

    static{

        MessageDigest messageDigest = null;
        RipeMD160 ripeMD160 = null;
        try {
             messageDigest = MessageDigest.getInstance(SHA256_DIGEST_ALGORITHM);
             ripeMD160 = new RipeMD160();
        }
        catch (Exception ex) {
            //
        }

        sha256Digest = messageDigest;
        ripemd160Digest = ripeMD160;
    }



    @Override
    public KeyPair generateKeyPair(String secret) throws SecurityException {
        try {
            Argument.notNullOrEmpty(secret, "secret");
            byte[] hash = sha256Hash(Encoding.getUTF8Bytes(secret));
            return Ed25519.generateKeyPairBySeed(hash);
        }
        catch (Exception ex){
            throw new SecurityException("generate key pair failed", ex);
        }
    }

    @Override
    public String encodePublicKey(PublicKey publicKey)throws SecurityException {
        try {
            Argument.notNull(publicKey, "publicKey");

            return Encoding.hex(((EdDSAPublicKey) publicKey).getAbyte());
        }
        catch (Exception ex){
            throw new SecurityException("encode public key failed", ex);
        }
    }

    @Override
    public String getBase58Address(String publicKey) throws SecurityException{
        try{
            byte[] hash1 = sha256Hash(Decoding.hex(publicKey));
            byte[] hash2 = ripemd160Hash(hash1);
            byte[] checksum = sha256Hash(sha256Hash(hash2));

            byte[] buffer = new byte[hash2.length + 4];
            System.arraycopy(hash2, 0, buffer, 0, hash2.length);
            System.arraycopy(checksum, 0, buffer, hash2.length, 4);

            return AschConst.BASE58_ADDRESS_PREFIX + Encoding.base58(buffer);
        }
        catch (Exception ex){
            throw new SecurityException("generate base58 checked address failed", ex);
        }
    }

    @Override
    public String generateTransactionId(TransactionInfo transaction)throws SecurityException {
        try {
            byte[] transactionBytes = transaction.getBytes(false, false);
            byte[] hash = sha256Hash(transactionBytes);

            DebugHelper.info("transaction bytes:" + Encoding.hex(transactionBytes));
            DebugHelper.info("transaction hash:" + Encoding.hex(hash));

            return Encoding.hex(hash);

        } catch (Exception ex) {
            throw new SecurityException("generate transaction id failed", ex);
        }
    }


    @Override
    public boolean isValidSecret(String secret) {
        return Bip39.isValidMnemonicCode(secret);
    }

    public boolean isValidBase58Address(String address){
        if (null == address || address.length() < AschConst.MIN_BASE58_ADDRESS_LEN){
            return false;
        }

        if (address.charAt(0) != AschConst.BASE58_ADDRESS_PREFIX){
            return false;
        }

        String base58 = address.substring(1);
        if (!Base58.isBase58String(base58)){
            return false;
        }

        byte[] buffer = Base58.decode(base58);

        byte[] payload = new byte[buffer.length - 4];
        System.arraycopy(buffer, 0, payload, 0, payload.length);
        byte[] checksum = new byte[4];
        System.arraycopy(buffer, payload.length, checksum, 0, checksum.length);

        byte[] payloadChecksum =  sha256Hash(sha256Hash(payload));
        byte[] calcChecksum = new byte[checksum.length];
        System.arraycopy(payloadChecksum, 0, calcChecksum, 0, checksum.length);

        return Arrays.equals(calcChecksum, checksum);
    }


    @Override
    public String Signature(TransactionInfo transaction, PrivateKey privateKey) throws SecurityException {
        try {
            byte[] transactionBytes = transaction.getBytes(true, true);
            byte[] hash = sha256Hash(transactionBytes);
            return Encoding.hex(Ed25519.signature(hash, privateKey));
        }
        catch (Exception ex){
            throw new SecurityException("setSignature transaction failed", ex);
        }
    }

    @Override
    public String SignSignature(TransactionInfo transaction, PrivateKey privateKey) throws SecurityException {
        try {
            byte[] transactionBytes = transaction.getBytes(true, true);
            byte[] hash = sha256Hash(transactionBytes);
            return Encoding.hex(Ed25519.signature(hash, privateKey));
        }
        catch (Exception ex){
            throw new SecurityException("setSignature transaction failed", ex);
        }
    }

    @Override
    public int getTransactionTimestamp() {
        //calendar.add(Calendar.MILLISECOND, (zoneOffset+dstOffset));
        return (int)((new Date().getTime() - AschConst.ASCH_BEGIN_EPOCH.getTime())/1000 - AschConst.CLIENT_DRIFT_SECONDS);
    }

    private byte[] sha256Hash(byte[] message){
        sha256Digest.update(message);
        return sha256Digest.digest();
    }

    private byte[] ripemd160Hash(byte[] message){
        ripemd160Digest.update(message);
        return ripemd160Digest.digest();
    }

}
