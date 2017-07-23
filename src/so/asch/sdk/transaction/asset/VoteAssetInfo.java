package so.asch.sdk.transaction.asset;

import so.asch.sdk.codec.Decoding;

import java.beans.Transient;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class VoteAssetInfo extends AssetInfo {

    public static class VoteInfo {
        private List<String> upvoteKeys = new ArrayList<>();
        private List<String> downvoteKeys = new ArrayList<>();

        public void upvote(String... keys) {
            if (keys == null) return;

            for (String key : keys) {
                upvoteKeys.add(key);
            }
        }

        public void downvote(String... keys) {
            if (keys == null) return;

            for (String key : keys) {
                downvoteKeys.add(key);
            }
        }

        @Transient
        public List<String> getUpvoteKeys() {
            return this.upvoteKeys;
        }

        @Transient
        public List<String> getDownvoteKeys() {
            return this.downvoteKeys;
        }

        private VoteInfo(String[] upvoteKeys, String[] downvoteKeys) {
            this.upvote(upvoteKeys);
            this.downvote(downvoteKeys);
        }

        public String[] getVotes() {
            if (upvoteKeys.isEmpty() && downvoteKeys.isEmpty())
                return null;

            ArrayList<String> votes = new ArrayList<>();
            upvoteKeys.forEach(key -> votes.add("+" + key));
            downvoteKeys.forEach(key -> votes.add("-" + key));

            return votes.toArray(new String[0]);
        }
    }

    public VoteInfo getVote() {
        return vote;
    }

    public VoteAssetInfo(String[] upvoteKeys, String[] downvoteKeys){
        vote = new VoteInfo(upvoteKeys, downvoteKeys);
    }

    private VoteInfo vote = null;

    @Override
    public void addBytes(ByteBuffer buffer){
        String votes = String.join("", vote.getVotes());
        buffer.put(Decoding.unsafeDecodeUTF8(votes));
    }
}
