package so.asch.sdk;

import so.asch.sdk.dto.query.BlockQueryParameters;

/**
 * Asch区块接口
 * @author eagle
 * 参见 https://github.com/AschPlatform/asch-docs/blob/master/asch_http_interface.md#23-%E5%8C%BA%E5%9D%97blocks
 */
public interface Block extends AschInterface {
    //接口地址：/api/blocks/get
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //id	string	参数3选1	区块id
    //height	string	参数3选1	区块高度
    //hash	string	参数3选1	区块hash
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //block	json	区块详情
    AschResult getBlockById(String id, boolean fullBlockInfo);
    AschResult getBlockByHeight(long height, boolean fullBlockInfo);
    AschResult getBlockByHash(String hash, boolean fullBlockInfo);


    //接口地址：/api/blocks
    //请求方式：get
    //支持格式：urlencoded
    //接口说明：不加参数则获取全网区块详情
    //请求参数说明：
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N	根据表中字段排序，如height:desc
    //offset	integer	N	偏移量，最小值0
    //generatorPublicKey	string	N	区块生成者公钥
    //totalAmount	integer	N	交易总额，最小值：0，最大值：10000000000000000
    //totalFee	integer	N	手续费总额，最小值：0，最大值：10000000000000000
    //reward	integer	N	奖励金额，最小值：0
    //previousBlock	string	N	上一个区块
    //height	integer	N	区块高度
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //blocks	Array	由区块详情json串构成的数组
    //count	integer	区块链高度
    AschResult queryBlocks(BlockQueryParameters parameters);

    //接口地址：/api/blocks/getHeight
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //height	integer	区块链高度
    AschResult getHeight();

    //接口地址：/api/blocks/getFee
    //接口说明： 获取交易手续费
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //milestone	integer	交易手续费
    AschResult getFree();

    //接口地址：/api/blocks/getMilestone
    //接口说明：获取里程碑
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //milestone	integer	里程碑
    AschResult getMilestone();

    //接口地址：/api/blocks/getReward
    //接口说明：获取单个区块奖励
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //reward	integer	区块奖励
    AschResult getReward();

    //接口地址：/api/blocks/getSupply
    //接口说明：获取区块链当前最大供应值
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //supply	integer	全网XAS个数
    AschResult getSupply();


    //接口地址：/api/blocks/getStatus
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //height	integer	区块链高度
    //fee	integer	交易手续费
    //milestone	integer
    //reward	integer	区块奖励
    //supply	integer	全网XAS个数
    AschResult getStauts();

}
