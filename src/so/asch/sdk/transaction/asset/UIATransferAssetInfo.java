package so.asch.sdk.transaction.asset;

import java.nio.ByteBuffer;

public class UIATransferAssetInfo extends AssetInfo {

    public static class UIATransferInfo {
        public long getAmount() {
            return amount;
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

    public UIATransferInfo getUiaTransferInfo() {
        return uiaTransfer;
    }

    private UIATransferInfo uiaTransfer = null;

    public UIATransferAssetInfo(long amount, String currency) {
        uiaTransfer = new UIATransferInfo(amount, currency);
    }

    @Override
    public void addBytes(ByteBuffer buffer){
        buffer.put(uiaTransfer.getCurrency().getBytes());
        Long amount = uiaTransfer.getAmount();
        buffer.put(amount.toString().getBytes());
    }
}
