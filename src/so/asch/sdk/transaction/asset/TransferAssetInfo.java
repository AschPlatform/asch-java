package so.asch.sdk.transaction.asset;

import java.nio.ByteBuffer;

public class TransferAssetInfo extends AssetInfo{

    //如果transaction中ssset为空, Asch会挂掉 :(
    @Override
    public void addBytes(ByteBuffer buffer){
        //do nothing
    }
}
