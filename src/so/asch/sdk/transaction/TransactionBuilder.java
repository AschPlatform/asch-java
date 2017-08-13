package so.asch.sdk.transaction;

import so.asch.sdk.TransactionType;
import so.asch.sdk.impl.AschConst;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.security.SecurityException;
import so.asch.sdk.security.SecurityStrategy;
import so.asch.sdk.transaction.asset.*;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class TransactionBuilder {

    public TransactionInfo buildVote(String[] upvotePublicKeys, String[] downvotePublicKeys,
                                     String secret, String secondSecret ) throws SecurityException {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction = newTransaction(
                TransactionType.Vote,
                0L,
                AschConst.Fees.VOTE,
                keyPair.getPublic())
                .setAsset(new VoteAssetInfo(upvotePublicKeys, downvotePublicKeys));

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    public TransactionInfo buildDelegate(String userName, String secret, String secondSecret)  throws SecurityException  {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);
        KeyPair secondKeyPair = getSecurity().generateKeyPair(secondSecret);

        TransactionInfo transaction = newTransaction(
                TransactionType.Delegate,
                0L,
                AschConst.Fees.DELEGATE,
                keyPair.getPublic());

        transaction.setAsset(new DelegateAssetInfo(userName, transaction.getSenderPublicKey()));

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(),secondSecret);
    }

    public TransactionInfo buildSignature(String secret, String secondSecret) throws  SecurityException{
        KeyPair keyPair = getSecurity().generateKeyPair(secret);
        KeyPair secondKeyPair = getSecurity().generateKeyPair(secondSecret);

        TransactionInfo transaction =  newTransaction(
                TransactionType.Signature,
                0L,
                AschConst.Fees.SECOND_SIGNATURE,
                keyPair.getPublic())
                .setAsset(new SignatureAssetInfo(getSecurity().encodePublicKey(secondKeyPair.getPublic())));

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), null);

    }

    public TransactionInfo buildMultiSignature(int minAccount, int lifetime, String[] addKeys, String[] removeKeys,
                                               String secret, String secondSecret) throws SecurityException{
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction =  newTransaction(
                TransactionType.MultiSignature,
                0L,
                AschConst.Fees.MULTI_SIGNATURE,
                keyPair.getPublic())
                .setAsset(new MultiSignatureAssetInfo(minAccount, lifetime, addKeys, removeKeys));

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);

    }

    public TransactionInfo buildTransfer(String targetAddress, long amount, String message,
                                         String secret, String secondSecret) throws  SecurityException{
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction =  newTransaction(
                TransactionType.Transfer,
                amount,
                AschConst.Fees.TRANSFER,
                keyPair.getPublic())
                .setMessage(message)
                .setRecipientId(targetAddress)
                .setAsset(new TransferAssetInfo());

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    public TransactionInfo buildStore(byte[] contentBuffer, int wait,
                                      String secret, String secondSecret)throws SecurityException{
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        long fee = (int) Math.floor(contentBuffer.length / (200) + 1) * 10000000;
        TransactionInfo transaction =  newTransaction(
                TransactionType.Store,
                0L,
                fee,
                keyPair.getPublic())
                .setAsset(new StorageAssetInfo(contentBuffer));

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    public TransactionInfo buildUIATransfer(String currency, long amount, String targetAddress, String message,
                                            String secret, String secondSecret) throws SecurityException{
        KeyPair keyPair = getSecurity().generateKeyPair(secret);

        TransactionInfo transaction =  newTransaction(
                TransactionType.UIATransfer,
                amount,
                AschConst.Fees.TRANSFER,
                keyPair.getPublic())
                .setMessage(message)
                .setRecipientId(targetAddress)
                .setAsset(new UIATransferAssetInfo(amount, currency));

        return signatureAndGenerateTransactionId(transaction, keyPair.getPrivate(), secondSecret);
    }

    protected TransactionInfo newTransaction(TransactionType type, long amount, long fee, PublicKey publicKey) throws SecurityException{
        return new TransactionInfo()
                .setTransactionType(type)
                .setAmount(amount)
                .setFee(fee)
                .setTimestamp(getSecurity().getTransactionTimestamp())
                .setSenderPublicKey(getSecurity().encodePublicKey(publicKey));
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
