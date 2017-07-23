package so.asch.sdk.transaction.asset;

import com.alibaba.fastjson.annotation.JSONField;
import so.asch.sdk.codec.Decoding;

import java.nio.ByteBuffer;

public class DelegateAssetInfo extends AssetInfo {

    public static class DelegateInfo {
        private String userName = null;
        private String publicKey = null;

        @JSONField(name = "username")
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

        private DelegateInfo(String userName, String publicKey) {
            this.userName = userName;
            this.publicKey = publicKey;
        }
    }

    public DelegateInfo getDelegate() {
        return delegate;
    }

    private DelegateInfo delegate = null;

    public DelegateAssetInfo(String userName, String publicKey){
        delegate = new DelegateInfo(userName, publicKey);
    }

    @Override
    public  void addBytes(ByteBuffer buffer){
        buffer.put(Decoding.unsafeDecodeUTF8(delegate.getUserName()));
    }
}
