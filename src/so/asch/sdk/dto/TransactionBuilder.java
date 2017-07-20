package so.asch.sdk.dto;

import so.asch.sdk.TransactionType;
import so.asch.sdk.impl.AschConst;
import so.asch.sdk.impl.AschFactory;
import so.asch.sdk.security.SecurityException;
import so.asch.sdk.security.SecurityStrategy;

import java.security.KeyPair;
import java.security.PublicKey;

public class TransactionBuilder {

    public TransactionInfo buildVote(String secret, String secondSecret, String[] votedPublicKeys,
                                     String[] cancelVotedPublicKeys) throws SecurityException {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);
        KeyPair secondKeyPair = getSecurity().generateKeyPair(secondSecret);

        TransactionInfo transaction = newTransaction(
                TransactionType.Vote,
                0L,
                AschConst.Fees.VOTE,
                keyPair.getPublic())
                .setAsset(new AssetInfo().setVote(new AssetInfo.VoteInfo(votedPublicKeys, cancelVotedPublicKeys)));

        transaction.setSignature(getSecurity().Signature(transaction, keyPair.getPrivate() ));
        transaction.setSignSignature(getSecurity().SignSignature(transaction, secondKeyPair.getPrivate()));

        transaction.setTransactionId(getSecurity().generateTransactionId(transaction));
        return transaction;
    }

    public TransactionInfo buildDelegate(String userName, String secret, String secondSecret)  throws SecurityException  {
        KeyPair keyPair = getSecurity().generateKeyPair(secret);
        KeyPair secondKeyPair = getSecurity().generateKeyPair(secondSecret);

        TransactionInfo transaction = newTransaction(
                TransactionType.Delegate,
                0L,
                AschConst.Fees.DELEGATE,
                keyPair.getPublic());

        transaction.setAsset(new AssetInfo()
                .setDelegate(new AssetInfo.DelegateInfo(userName, transaction.getSenderPublicKey())));

        transaction.setSignature(getSecurity().Signature(transaction, keyPair.getPrivate() ));
        transaction.setSignSignature(getSecurity().SignSignature(transaction, secondKeyPair.getPrivate()));

        transaction.setTransactionId(getSecurity().generateTransactionId(transaction));
        return transaction;
    }

    protected TransactionInfo newTransaction(TransactionType type, long amount, long fee, PublicKey publicKey) throws SecurityException{
        return new TransactionInfo()
                .setTransactionType(type)
                .setAmount(amount)
                .setFee(fee)
                .setTimestamp(getSecurity().getTimestamp())
                .setSenderPublicKey(getSecurity().encodePublicKey(publicKey));
    }

    protected SecurityStrategy getSecurity(){
        return AschFactory.getInstance().getSecurity();
    }

}
