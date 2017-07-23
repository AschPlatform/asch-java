package so.asch.sdk.transaction.asset;

import com.alibaba.fastjson.annotation.JSONField;
import so.asch.sdk.codec.Decoding;

import java.beans.Transient;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MultiSignatureAssetInfo extends AssetInfo{

    public static class MultiSignatureInfo {
        public Integer getMinAccount() {
            return minAccount;
        }

        public void setMinAccount(Integer minAccount) {
            this.minAccount = minAccount;
        }

        public Integer getLifetime() {
            return lifetime;
        }

        public void setLifetime(Integer lifetime) {
            this.lifetime = lifetime;
        }


        private MultiSignatureInfo(Integer min, Integer lifetime, String[] addKeys, String[] removeKeys) {
            this.minAccount = min;
            this.lifetime = lifetime;
            this.addKey(addKeys);
            this.removeKey(removeKeys);
        }


        public void addKey(String... keys) {
            if (keys == null) return;

            for (String key : keys) {
                addKeys.add(key);
            }
        }

        public void removeKey(String... keys) {
            if (keys == null) return;

            for (String key : keys) {
                removeKeys.add(key);
            }
        }

        @Transient
        public List<String> getAddKeys() {
            return this.addKeys;
        }

        @Transient
        public List<String> getRemoveKeys() {
            return this.removeKeys;
        }


        @JSONField(name = "keysgroup")
        public String[] getKeys() {
            if (addKeys.isEmpty() && removeKeys.isEmpty())
                return null;

            ArrayList<String> keys = new ArrayList<>();
            addKeys.forEach(key -> keys.add("+" + key));
            removeKeys.forEach(key -> keys.add("-" + key));

            return keys.toArray(new String[0]);
        }

        private Integer minAccount = null;
        private Integer lifetime = null;
        private List<String> addKeys = new ArrayList<>();
        private List<String> removeKeys = new ArrayList<>();
    }

    public MultiSignatureInfo getMultiSignature() {
        return multiSignature;
    }

    public MultiSignatureAssetInfo(Integer min, Integer lifetime, String[] addKeys, String[] removeKeys){
        multiSignature = new MultiSignatureInfo(min, lifetime, addKeys, removeKeys);
    }

    private MultiSignatureInfo multiSignature = null;

    @Override
    public  void addBytes(ByteBuffer buffer){
        buffer.put(multiSignature.getMinAccount().byteValue());
        buffer.put(multiSignature.getLifetime().byteValue());
        String keys = String.join("",multiSignature.getKeys());
        buffer.put(Decoding.unsafeDecodeUTF8(keys));
    }
}
