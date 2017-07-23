package so.asch.sdk.transaction.asset;

import java.beans.Transient;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by eagle on 17-7-17.
 */
public abstract class AssetInfo {

    private static final int MAX_BUFFER_SIZE = 1024 * 5;
    private static final byte[] EMPTY_BUFFER = new byte[0];

    @Transient
    public byte[] assetBytes(){
        ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE)
                .order(ByteOrder.LITTLE_ENDIAN);

        addBytes(buffer);

        buffer.flip();
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);

        return result;
    }

    public abstract void addBytes(ByteBuffer buffer);
}
