package so.asch.sdk.security;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;
import so.asch.sdk.codec.Decoding;
import so.asch.sdk.codec.Encoding;
import so.asch.sdk.dbc.Argument;

import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;

/**
 * asch兼容加密及签名功能
 * @author eagle.asch
 *
 */
public class Crypto {

    private static final int SIGNATURE_LENGTH = 64;
    private static final int PUBLIC_KEY_LENGTH = 32;

    private static final String SHA256_DIGEST_ALGORITHM = "SHA-256";
    private static final String EDDSA_CURVE_TABLE = "Ed25519";
    private static final EdDSAParameterSpec EDDSA_PARAMETER_SPEC = EdDSANamedCurveTable.getByName(EDDSA_CURVE_TABLE);

    private static MessageDigest digest;
    private static Signature signature ;
    static
    {
        try {
            digest = MessageDigest.getInstance(SHA256_DIGEST_ALGORITHM);
            signature = new EdDSAEngine(MessageDigest.getInstance(EDDSA_PARAMETER_SPEC.getHashAlgorithm()));
        }
        catch (Exception ex) {
            //
        }
    }

    /**
     * SHA256 hash
     * @param content 需计算Hash的内容
     * @return hash
    * */
    public static byte[] sha256Hash(byte[] content) {
        digest.update(content);
        return digest.digest();
    }


    protected static KeyPair generateSignKeyPairBySeed(byte[] seed) {

        EdDSAPrivateKeySpec keySpec = new EdDSAPrivateKeySpec(seed, EDDSA_PARAMETER_SPEC);
        EdDSAPrivateKey privateKey = new EdDSAPrivateKey(keySpec);
        EdDSAPublicKey publicKey = new EdDSAPublicKey(new EdDSAPublicKeySpec(privateKey.getAbyte(), EDDSA_PARAMETER_SPEC));

        return new KeyPair(publicKey, privateKey);

    }

    /**
     * 根据一级密钥生成Ed25519密钥对
     * @param secret 一级密钥
     * @return 密钥对
     * @see KeyPair
     */
    public static KeyPair getKeys(String secret) throws CryptoException
    {
        try {
            Argument.notNullOrEmpty(secret, "secret");

            byte[] seed = Decoding.utf8(secret);
            byte[] hash = sha256Hash(seed);

            return generateSignKeyPairBySeed(hash);
        }
        catch (Exception ex){
            throw new CryptoException("generate key pair failed", ex);
        }
    }

    /**
     * 根据一级密钥生成密钥对的公钥
     * @param secret 一级密钥
     * @return Hex格式的公钥
     */
    public static String getHexPublicKey(String secret) throws CryptoException {
        EdDSAPublicKey publicKey = (EdDSAPublicKey)getKeys(secret).getPublic();
        return Encoding.hex(publicKey.getAbyte());
    }

    /**
     * 对消息进行签名（Hex格式）
     * @param message Hex格式消息
     * @param privateKey 私钥
     * @return Hex格式签名
    * */
    public static String signHex (String message, PrivateKey privateKey) throws CryptoException
    {
        try {
            Argument.notNullOrEmpty(message, "message");
            Argument.notNull(privateKey, "privateKey");

            byte[] messageBuffer = Decoding.hex(message);
            return Encoding.hex(sign(messageBuffer, privateKey));
        }
        catch(Exception ex){
            throw new CryptoException("signature failed", ex);
        }
    }

    /**
     * 对消息进行签名
     * @param message 消息内容
     * @param privateKey 私钥
     * @return 签名
     * */
    public static byte[] sign (byte[] message, PrivateKey privateKey) throws CryptoException {
        try {
            Argument.notNullOrEmpty(message, "message");
            Argument.notNull(privateKey, "privateKey");

            signature.initSign(privateKey);
            signature.update(sha256Hash(message));
            return signature.sign();
        }
        catch(Exception ex){
            throw new CryptoException("signature failed", ex);
        }
    }

    /**
     * 对消息签名进行验证（Hex格式）
     * @param message Hex格式消息内容
     * @param signatureString Hex格式私钥
     * @param publicKey Hex格式公钥
     * @return 签名是否正确
     * */
    public static boolean verifyHex (String message, String signatureString, String publicKey) throws CryptoException{
        try {
            Argument.notNullOrEmpty(message, "message");
            Argument.length(signatureString, SIGNATURE_LENGTH *2,"signatureString");
            Argument.length(publicKey, PUBLIC_KEY_LENGTH *2,"publicKey");

            byte[] publicKeyBuffer = Decoding.hex(publicKey);
            byte[] messageBuffer = Decoding.hex(message);
            byte[] signatureBuffer = Decoding.hex(signatureString);

            return verify(messageBuffer, signatureBuffer, publicKeyBuffer);
        }
        catch(Exception ex){
            throw new CryptoException("verify failed", ex);
        }
    }

    /**
     * 对消息签名进行验证
     * @param message 消息内容
     * @param sign 私钥
     * @param publicKey 公钥
     * @return 签名是否正确
     * */
    public static boolean verify (byte[] message, byte[] sign, byte[] publicKey) throws CryptoException{
        try {
            Argument.notNullOrEmpty(message, "message");
            Argument.length(sign, SIGNATURE_LENGTH, "sign");
            Argument.length(publicKey, PUBLIC_KEY_LENGTH, "sign");

            EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(publicKey, EdDSANamedCurveTable.getByName(EDDSA_CURVE_TABLE));
            EdDSAPublicKey pKey = new EdDSAPublicKey(spec);
            signature.initVerify(pKey);

            signature.update(sha256Hash(message));
            return signature.verify(sign);
        }
        catch(Exception ex){
            throw new CryptoException("verify failed", ex);
        }
    }
}



