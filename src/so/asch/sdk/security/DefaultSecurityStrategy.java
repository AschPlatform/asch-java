package so.asch.sdk.security;

import net.i2p.crypto.eddsa.EdDSAPublicKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import so.asch.sdk.AschSDKConfig;
import so.asch.sdk.codec.Decoding;
import so.asch.sdk.codec.Encoding;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.impl.AschConst;
import so.asch.sdk.impl.Validation;
import so.asch.sdk.security.ripemd.RipeMD160;
import so.asch.sdk.transaction.TransactionInfo;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;


/**
 * Created by eagle on 17-7-18.
 */
public class DefaultSecurityStrategy implements SecurityStrategy{

    private static final String SHA256_DIGEST_ALGORITHM = "SHA-256";
    private static final AschSDKConfig config = AschSDKConfig.getInstance();

    private static final MessageDigest sha256Digest;
    private static final RipeMD160 ripemd160Digest;

    protected static final Logger logger = LoggerFactory.getLogger(DefaultSecurityStrategy.class);

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
    public KeyPair generateKeyPair(String secure) throws SecurityException {
        try {
            Argument.notNullOrEmpty(secure, "secure");
            byte[] hash = sha256Hash(Encoding.getUTF8Bytes(secure));
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
    public String getAddress(String publicKey) throws SecurityException{
        try{
            Argument.require(Validation.isValidPublicKey(publicKey), "invalid public key");

            byte[] hash1 = sha256Hash(Decoding.hex(publicKey));
            byte[] hash2 = ripemd160Hash(hash1);
            //fix by kimziv 
            //reference: https://github.com/AschPlatform/asch-js/blob/master/lib/base58check/index.js#L14
            byte[] checksum=sha256Hash(sha256Hash(hash2));
            byte[] hash3=new byte[hash2.length+4];
            System.arraycopy(hash2,0,hash3,0,hash2.length);
            System.arraycopy(checksum,0,hash3,hash2.length,4);
            return AschConst.BASE58_ADDRESS_PREFIX + Encoding.base58(hash3);
        }
        catch (Exception ex){
            throw new SecurityException("generate key pair failed", ex);
        }
    }

    @Override
    public String generateTransactionId(TransactionInfo transaction)throws SecurityException {
        try {
            byte[] transactionBytes = transaction.getBytes(false, false);
            byte[] hash = sha256Hash(transactionBytes);

            if (logger.isInfoEnabled()) {
                logger.info("transaction bytes:" + Encoding.hex(transactionBytes));
                logger.info("transaction hash:" + Encoding.hex(hash));
            }

            if (config.isLongTransactionIdEnabled())
                return Encoding.hex(hash);

            byte[] transactionIdBytes = new byte[8];
            for (int i = 0; i < 7; i++) {
                transactionIdBytes[i] = hash[7 - 1];
            }

            return new BigInteger(transactionIdBytes).toString();

        } catch (Exception ex) {
            throw new SecurityException("generate transaction id failed", ex);
        }
    }

    @Override
    public String generateSecret(){
        String uuid = UUID.randomUUID().toString()
                .replace("-","");
        return Bip39.generateMnemonicCode(Decoding.unsafeDecodeHex(uuid));
    }

    @Override
    public boolean isValidSecret(String secret) {
        return Bip39.isValidMnemonicCode(secret);
    }

    @Override
    public String Signature(TransactionInfo transaction, PrivateKey privateKey) throws SecurityException {
        try {
            Argument.require(transaction != null, "transaction can not be null");
            Argument.require(privateKey != null, "private key can not be null");

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
            Argument.require(transaction != null, "transaction can not be null");
            Argument.require(privateKey != null, "private key can not be null");

            byte[] transactionBytes = transaction.getBytes( false, true);
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
