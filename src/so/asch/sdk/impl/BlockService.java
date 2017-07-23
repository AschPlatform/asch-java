package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Block;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.BlockQueryParameters;

public class BlockService extends AschRESTService implements Block {
    @Override
    public JSONObject getBlockById(String id, boolean fullBlockInfo) {
        try {
            Argument.notNull(id, "id is null");

            JSONObject parameters = new JSONObject().fluentPut("id", id);
            String url = fullBlockInfo ?
                    AschServiceUrls.Block.GET_FULL_BLOCK_INFO:
                    AschServiceUrls.Block.GET_BLOCK_INFO ;
            return get(url, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public JSONObject getBlockByHeight(long height, boolean fullBlockInfo) {
        JSONObject parameters = new JSONObject().fluentPut("height", height);
        String url = fullBlockInfo ?
                AschServiceUrls.Block.GET_FULL_BLOCK_INFO:
                AschServiceUrls.Block.GET_BLOCK_INFO ;
        return get(url, parameters);
    }

    @Override
    public JSONObject getBlockByHash(String hash, boolean fullBlockInfo) {
        try {
            Argument.notNull(hash, "hash is null");

            JSONObject parameters = new JSONObject().fluentPut("hash", hash);
            String url = fullBlockInfo ?
                    AschServiceUrls.Block.GET_FULL_BLOCK_INFO :
                    AschServiceUrls.Block.GET_BLOCK_INFO;
            return get(url, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }


    @Override
    public JSONObject queryBlocks(BlockQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidBlockQueryParameters(parameters), "invalid parameters");

            JSONObject query = jsonFromObject(parameters);
            return get(AschServiceUrls.Block.QUERY_BLOCKS,  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public JSONObject getHeight() {
        return get(AschServiceUrls.Block.GET_HEIGHT);
    }


    @Override
    public JSONObject getStauts() {
        return get(AschServiceUrls.Block.GET_STATUS);
    }
}
