package so.asch.sdk.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eagle on 17-7-17.
 */
public class AssetInfo {

    public static class VoteInfo {
        private List<String> votedKeys = new ArrayList<>();
        private List<String> canceledKeys = new ArrayList<>();

        public void vote(String... keys) {
            if (keys == null) return;

            for (String key : keys) {
                votedKeys.add(key);
            }
        }

        public void cancelVoted(String... keys) {
            if (keys == null) return;

            for (String key : keys) {
                canceledKeys.add(key);
            }
        }

        @Transient
        public List<String> getVotedKeys() {
            return this.votedKeys;
        }

        @Transient
        public List<String> getCanceledKeys() {
            return this.canceledKeys;
        }

        public VoteInfo(String[] votedKeys, String[] canceledKeys) {
            this.vote(votedKeys);
            this.cancelVoted(canceledKeys);
        }

        public String[] getVotes() {
            if (votedKeys.isEmpty() && canceledKeys.isEmpty())
                return null;

            ArrayList<String> votes = new ArrayList<>();
            votedKeys.forEach(key -> votes.add("+" + key));
            canceledKeys.forEach(key -> votes.add("-" + key));

            return votes.toArray(new String[0]);
        }
    }

    public static class TransferInfo {
        public String getDappId() {
            return dappId;
        }

        public String getCurrency() {
            return currency;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public Integer getAmount() {
            return amount;
        }

        private String dappId = null;
        private String currency = null;
        private String transactionId = null;
        private Integer amount = null;

        private TransferInfo() {
        };

        public static TransferInfo createInTransfer(String dappId, String currency) {
            TransferInfo in = new TransferInfo();
            in.dappId = dappId;
            in.currency = currency;
            return in;
        }

        public static TransferInfo createOutTransfer(String dappId, String transactionId, Integer amount, String currency) {
            TransferInfo out = new TransferInfo();
            out.dappId = dappId;
            out.transactionId = transactionId;
            out.amount = amount;
            out.currency = currency;
            return out;
        }
    }

    public static class StorageInfo {
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private String content = null;

        public StorageInfo(String content) {
            this.content = content;
        }
    }

    public static class MultiSignatureInfo {

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getLifetime() {
            return lifetime;
        }

        public void setLifetime(Integer lifetime) {
            this.lifetime = lifetime;
        }

        public String[] getKeysGroup() {
            return keys.isEmpty() ?
                    null :
                    keys.toArray(new String[0]);
        }

        public MultiSignatureInfo(Integer min, Integer lifetime, String... keys) {
            this.min = min;
            this.lifetime = lifetime;
            if (keys != null) {
                for (String key : keys) this.keys.add(key);
            }
        }

        private Integer min = null;
        private Integer lifetime = null;
        private List<String> keys = new ArrayList<>();
    }

    public static class DelegateInfo {
        private String userName = null;
        private String publicKey = null;

        @JSONField(name="username")
        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public DelegateInfo(String userName, String publicKey) {
            this.userName = userName;
            this.publicKey = publicKey;
        }
    }

    public static class SignatureInfo{
        //todo:SignatureInfo
    }

    public static class DappInfo {
        //todo:DappInfo
    }

    public VoteInfo getVote() {
        return vote;
    }

    public AssetInfo setVote(VoteInfo vote) {
        this.vote = vote;
        return this;
    }

    public TransferInfo getTransfer() {
        return transfer;
    }

    public AssetInfo setTransfer(TransferInfo transfer) {
        this.transfer = transfer;
        return this;
    }

    public StorageInfo getStore() {
        return store;
    }

    public AssetInfo setStore(StorageInfo store) {
        this.store = store;
        return this;
    }

    public MultiSignatureInfo getMultiSignature() {
        return multiSignature;
    }

    public AssetInfo setMultiSignature(MultiSignatureInfo multiSignature) {
        this.multiSignature = multiSignature;
        return this;
    }

    public DelegateInfo getDelegate() {
        return delegate;
    }

    public AssetInfo setDelegate(DelegateInfo delegate) {
        this.delegate = delegate;
        return this;
    }

    public TransferInfo getInTransfer() {
        return inTransfer;
    }

    public AssetInfo setInTransfer(TransferInfo inTransfer) {
        this.inTransfer = inTransfer;
        return this;
    }

    public TransferInfo getOutTransfer() {
        return outTransfer;
    }

    public AssetInfo setOutTransfer(TransferInfo outTransfer) {
        this.outTransfer = outTransfer;
        return this;
    }

    public DappInfo getDapp() {
        return dapp;
    }

    public AssetInfo setDapp(DappInfo dapp) {
        this.dapp = dapp;
        return this;
    }

    public SignatureInfo getSignature() {
        return signature;
    }

    public AssetInfo setSignature(SignatureInfo signature) {
        this.signature = signature;
        return this;
    }

    private VoteInfo vote;
    private TransferInfo transfer;
    private StorageInfo store;
    private MultiSignatureInfo multiSignature;
    private DelegateInfo delegate;
    private TransferInfo inTransfer;
    private TransferInfo outTransfer;
    private DappInfo dapp;

    private SignatureInfo signature;

}
