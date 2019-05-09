package so.asch.sdk.transaction;

import so.asch.sdk.TransactionType;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.security.SecurityException;
import so.asch.sdk.security.SecurityStrategy;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class TransactionBuilder {
    public TransactionInfo buildTransaction(TransactionType type, Object[] args, String message, String secret,
                                            String secondSecret) throws SecurityException {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction = newTransaction(type, keyPair.getPublic())
                .setMessage(message)
                .setArgs(args)
                .calcFee();

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    public TransactionInfo buildTransfer(String targetAddress, long amount, String message,
                                         String secret, String secondSecret) throws SecurityException {
        return buildTransaction(
                TransactionType.basic_transfer,
                new Object[]{((Long) amount).toString(), targetAddress},
                message,
                secret,
                secondSecret
        );
    }

    public TransactionInfo buildUIATransfer(String currency, long amount, String targetAddress, String message,
                                            String secret, String secondSecret) throws SecurityException{
        return buildTransaction(
                TransactionType.uia_transfer,
                new Object[]{currency, amount, targetAddress},
                message,
                secret,
                secondSecret
        );
    }


    protected TransactionInfo newTransaction(TransactionType type, PublicKey publicKey) throws SecurityException{
        String publicKeyHex = getSecurity().encodePublicKey(publicKey);
        return new TransactionInfo()
                .setTransactionType(type)
                .setTimestamp(getSecurity().getTransactionTimestamp())
                .setSenderPublicKey(publicKeyHex)
                .setSenderId(getSecurity().getBase58Address(publicKeyHex));
    }

    protected TransactionInfo signatureAndGenerateTransactionId(TransactionInfo transaction,
                                                                PrivateKey privateKey, String secondSecret) throws SecurityException{
        transaction.setSignature(getSecurity().Signature(transaction, privateKey));

        if (null != secondSecret) {
            KeyPair secondKeyPair = getSecurity().generateKeyPair(secondSecret);
            transaction.setSecondSignature(getSecurity().SignSignature(transaction, secondKeyPair.getPrivate()));
        }

        transaction.setTransactionId(getSecurity().generateTransactionId(transaction));
        return transaction;
    }

    protected SecurityStrategy getSecurity(){
        return AschFactory.getInstance().getSecurity();
    }

}
