package so.asch.sdk.impl;


import so.asch.sdk.AschResult;
import so.asch.sdk.Block;
import so.asch.sdk.dbc.Argument;
import so.asch.sdk.dto.query.BlockQueryParameters;

public class BlockService extends AschRESTService implements Block {
    @Override
    public AschResult getBlockById(String id, boolean withTransactions) {
        try {
            Argument.notNull(id, "id is null");

            String url = AschServiceUrls.Block.GET_BLOCK_INFO + id;
            ParameterMap parameters = new ParameterMap();
            if (withTransactions) {
                parameters.put("transactions", true);
            }
            return get(url, parameters);
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @Override
    public AschResult getBlockByHeight(long height, boolean withTransactions) {
        String url = AschServiceUrls.Block.GET_BLOCK_INFO + height;
        ParameterMap parameters = new ParameterMap();
        if (withTransactions) {
            parameters.put("transactions", true);
        }
        return get(url, parameters);
    }


    @Override
    public AschResult queryBlocks(BlockQueryParameters parameters) {
        try {
            Argument.require(Validation.isValidBlockQueryParameters(parameters), "invalid parameters");

            ParameterMap query = parametersFromObject(parameters);
            return get(AschServiceUrls.Block.QUERY_BLOCKS,  query);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

    @Override
    public AschResult getHeight() {
        return get(AschServiceUrls.Block.GET_HEIGHT);
    }

    @Override
    public AschResult getFree() {
        return get(AschServiceUrls.Block.GET_FREE);
    }

    @Override
    public AschResult getMilestone() {
        return get(AschServiceUrls.Block.GET_MILESTONE);
    }

    @Override
    public AschResult getReward() {
        return get(AschServiceUrls.Block.GET_REWARD);
    }

    @Override
    public AschResult getSupply() {
        return get(AschServiceUrls.Block.GET_SUPPLY);
    }

    @Override
    public AschResult getStauts() {
        return get(AschServiceUrls.Block.GET_STATUS);
    }
}
