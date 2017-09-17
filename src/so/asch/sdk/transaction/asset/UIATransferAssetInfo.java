package so.asch.sdk.transaction.asset;

import so.asch.sdk.codec.Encoding;

import java.nio.ByteBuffer;

public class UIATransferAssetInfo extends AssetInfo {

    public static class UIATransferInfo {
        public String getAmount() {
            return Long.toString(amount);
        }

        public String getCurrency() {
            return currency;
        }

        private UIATransferInfo(long amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }

        private long amount;
        private String currency;
    }

    public UIATransferInfo getUiaTransfer() {
        return uiaTransfer;
    }

    private UIATransferInfo uiaTransfer = null;

    public UIATransferAssetInfo(long amount, String currency) {
        uiaTransfer = new UIATransferInfo(amount, currency);
    }

    @Override
    public void addBytes(ByteBuffer buffer){
        buffer.put(Encoding.getUTF8Bytes(uiaTransfer.getCurrency()));
        buffer.put(Encoding.getUTF8Bytes(uiaTransfer.getAmount()));
    }
}
