package so.asch.sdk.transaction;

import so.asch.sdk.TransactionType;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.security.SecurityException;
import so.asch.sdk.security.SecurityStrategy;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class TransactionBuilder {

    public TransactionInfo buildVote(String[] votePublicKeys, String secret, String secondSecret ) throws SecurityException {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction = newTransaction(
                TransactionType.basic_vote,
                keyPair.getPublic())
                .setArgs(new Object[]{ String.join(",", votePublicKeys) })
                .calcFee();

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    public TransactionInfo buildUnvote(String[] unvotePublicKeys, String secret, String secondSecret ) throws SecurityException {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction = newTransaction(
                TransactionType.basic_unvote,
                keyPair.getPublic())
                .setArgs(new Object[]{ String.join(",", unvotePublicKeys) })
                .calcFee();

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    public TransactionInfo buildDelegate(String userName, String secret, String secondSecret)  throws SecurityException  {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction = newTransaction(
                TransactionType.basic_registerDelegate,
                keyPair.getPublic())
                .setArgs(new Object[]{userName})
                .calcFee();

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(),secondSecret);
    }

    public TransactionInfo buildTransfer(String targetAddress, long amount, String message,
                                         String secret, String secondSecret) throws  SecurityException{
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction =  newTransaction(
                TransactionType.basic_transfer,
                keyPair.getPublic())
                .setMessage(message)
                .setArgs(new Object[]{ amount, targetAddress })
                .calcFee();

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    public TransactionInfo buildUIATransfer(String currency, long amount, String targetAddress, String message,
                                            String secret, String secondSecret) throws SecurityException{
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction =  newTransaction(
                TransactionType.uia_transfer,
                keyPair.getPublic())
                .setMessage(message)
                .setArgs(new Object[]{ currency, amount, targetAddress })
                .calcFee();

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }


    protected TransactionInfo newTransaction(TransactionType type, PublicKey publicKey) throws SecurityException{
        return new TransactionInfo()
                .setTransactionType(type)
                .setTimestamp(getSecurity().getTransactionTimestamp())
                .setSenderId(getSecurity().getBase58Address(getSecurity().encodePublicKey(publicKey)));
    }

    protected TransactionInfo signatureAndGenerateTransactionId(TransactionInfo transaction,
                                                                PrivateKey privateKey, String secondSecret) throws SecurityException{
        transaction.setSignature(getSecurity().Signature(transaction, privateKey));

        if (null != secondSecret) {
            KeyPair secondKeyPair = getSecurity().generateKeyPair(secondSecret);
            transaction.setSignSignature(getSecurity().SignSignature(transaction, secondKeyPair.getPrivate()));
        }

        transaction.setTransactionId(getSecurity().generateTransactionId(transaction));
        return transaction;
    }

    protected SecurityStrategy getSecurity(){
        return AschFactory.getInstance().getSecurity();
    }

}
