package so.asch.sdk.transaction.asset;

import so.asch.sdk.codec.Encoding;

import java.nio.ByteBuffer;

/**
 * @author fisher
 * @version $Id: InTransferAssetInfo.java, v 0.1 04/12/2017 18:49 fisher Exp $
 */
public class InTransferAssetInfo extends AssetInfo  {
    public static class InTransferInfo {
        public String getDappId() {
            return dappId;
        }

        public String getCurrency() {
            return currency;
        }

        public String getAmount(){return Long.toString(amount);}

        private InTransferInfo(String dappId, String currency,long amount) {
            this.dappId = dappId;
            this.currency = currency;
            this.amount = amount;
        }

        private String dappId;
        private String currency;
        private long   amount;
    }

    public InTransferAssetInfo.InTransferInfo getInTransfer() {
        return inTransfer;
    }

    private InTransferAssetInfo.InTransferInfo inTransfer = null;

    public InTransferAssetInfo(String dappId, String currency,long amount) {
        inTransfer = new InTransferAssetInfo.InTransferInfo(dappId, currency,amount);
    }

    @Override
    public void addBytes(ByteBuffer buffer){
        buffer.put(Encoding.getUTF8Bytes(inTransfer.getCurrency()));
        buffer.put(Encoding.getUTF8Bytes(inTransfer.getDappId()));
        buffer.put(Encoding.getUTF8Bytes(inTransfer.getAmount()));
    }
}
