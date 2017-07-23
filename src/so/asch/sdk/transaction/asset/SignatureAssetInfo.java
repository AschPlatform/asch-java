package so.asch.sdk.transaction.asset;

import so.asch.sdk.codec.Decoding;

import java.nio.ByteBuffer;

public class SignatureAssetInfo extends AssetInfo {

    public static class SignatureInfo {
        public String getPublicKey() {
            return publicKey;
        }

        private SignatureInfo(String publicKey) {
            this.publicKey = publicKey;
        }

        private String publicKey;
    }

    public SignatureInfo getSignature() {
        return signature;
    }

    public SignatureAssetInfo(String publicKey){
        signature = new SignatureInfo(publicKey);
    }

    private SignatureInfo signature = null;

    @Override
    public void addBytes(ByteBuffer buffer){
        buffer.put(Decoding.unsafeDecodeHex(signature.getPublicKey()));
    }
}
