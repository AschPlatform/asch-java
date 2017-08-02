package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import so.asch.sdk.TestHelper;
import so.asch.sdk.TestHelper;
import so.asch.sdk.dto.query.BlockQueryParameters;

/**
 * @author fisher
 * @version $Id: BlockServiceTest.java, v 0.1 2017/8/1 18:49 fisher Exp $
 */
public class BlockServiceTest {
    @Test
    public void testGetBlockById() throws Exception {
        JSONObject result = TestHelper.blockService().getBlockById(TestHelper.blockId,false);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetBlockByHeight()throws Exception{
        JSONObject result = TestHelper.blockService().getBlockByHeight(TestHelper.blockHeight,false);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetBlockByHash()throws Exception{
        JSONObject result = TestHelper.blockService().getBlockByHash(TestHelper.blockHash,false);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testQueryBlocks()throws Exception{
        BlockQueryParameters parameters = new BlockQueryParameters();
        //parameters.setGeneratorPublicKey(TestHelper.publicKey());
        //parameters.setHeight(6890);
        parameters.setLimit(2);
        //parameters.setOffset(0);
        JSONObject result = TestHelper.blockService().queryBlocks(parameters);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetHeight()throws Exception{
        JSONObject result = TestHelper.blockService().getHeight();
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetFree()throws Exception{
        JSONObject result = TestHelper.blockService().getFree();
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetMilestone()throws Exception{
        JSONObject result = TestHelper.blockService().getMilestone();
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetReward()throws Exception{
        JSONObject result = TestHelper.blockService().getReward();
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetSupply()throws Exception{
        JSONObject result = TestHelper.blockService().getSupply();
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetStauts()throws Exception{
        JSONObject result = TestHelper.blockService().getStauts();
        Assert.assertTrue(TestHelper.isSuccess(result));
    }
}
