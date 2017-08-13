package so.asch.sdk.security;

import so.asch.sdk.transaction.TransactionInfo;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by eagle on 17-7-18.
 */
public interface SecurityStrategy {
    KeyPair generateKeyPair(String secure) throws SecurityException ;

    String encodePublicKey(PublicKey publicKey) throws SecurityException;

    String getAddress(String publicKey) throws SecurityException;

    String generateTransactionId(TransactionInfo transaction) throws SecurityException;

    String generateSecret();

    boolean isValidSecret(String secret);

    String Signature(TransactionInfo transaction, PrivateKey privateKey) throws SecurityException;

    String SignSignature(TransactionInfo transaction, PrivateKey privateKey) throws SecurityException;

    int getTransactionTimestamp();
}
