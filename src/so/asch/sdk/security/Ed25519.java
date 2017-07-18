package so.asch.sdk.security;

import net.i2p.crypto.eddsa.EdDSAEngine;
import net.i2p.crypto.eddsa.EdDSAPrivateKey;
import net.i2p.crypto.eddsa.EdDSAPublicKey;
import net.i2p.crypto.eddsa.spec.EdDSANamedCurveTable;
import net.i2p.crypto.eddsa.spec.EdDSAParameterSpec;
import net.i2p.crypto.eddsa.spec.EdDSAPrivateKeySpec;
import net.i2p.crypto.eddsa.spec.EdDSAPublicKeySpec;
import so.asch.sdk.dbc.ContractException;

import java.security.*;

/**
 * asch兼容加密及签名功能
 * @author eagle
 *
 */
 public class Ed25519 {
    private static final int SIGNATURE_LENGTH = 64;
    private static final int PUBLIC_KEY_LENGTH = 32;

    private static final String EDDSA_CURVE_TABLE = "Ed25519";
    private static final EdDSAParameterSpec EDDSA_PARAMETER_SPEC = EdDSANamedCurveTable.getByName(EDDSA_CURVE_TABLE);

    private static Signature signature ;
    static
    {
        try {
            signature = new EdDSAEngine(MessageDigest.getInstance(EDDSA_PARAMETER_SPEC.getHashAlgorithm()));
        }
        catch (Exception ex) {
            //
        }
    }

    public static KeyPair generateKeyPairBySeed(byte[] seed) throws ContractException {
        EdDSAPrivateKeySpec keySpec = new EdDSAPrivateKeySpec(seed, EDDSA_PARAMETER_SPEC);
        EdDSAPrivateKey privateKey = new EdDSAPrivateKey(keySpec);
        EdDSAPublicKey publicKey = new EdDSAPublicKey(new EdDSAPublicKeySpec(privateKey.getAbyte(), EDDSA_PARAMETER_SPEC));

        return new KeyPair(publicKey, privateKey);
    }

    /**
     * 对消息进行签名
     * @param message 消息内容
     * @param privateKey 私钥
     * @return 签名
     * */
    public static byte[] signature (byte[] message, PrivateKey privateKey) throws InvalidKeyException, SignatureException {
        signature.initSign(privateKey);
        signature.update(message);
        return signature.sign();
    }

    /**
     * 对消息签名进行验证
     * @param message 消息内容
     * @param sign 私钥
     * @param publicKey 公钥
     * @return 签名是否正确
     * */
    public static boolean verify (byte[] message, byte[] sign, byte[] publicKey) throws InvalidKeyException,SignatureException {

        EdDSAPublicKeySpec spec = new EdDSAPublicKeySpec(publicKey, EdDSANamedCurveTable.getByName(EDDSA_CURVE_TABLE));
        EdDSAPublicKey pKey = new EdDSAPublicKey(spec);
        signature.initVerify(pKey);

        signature.update(message);
        return signature.verify(sign);
    }
}



