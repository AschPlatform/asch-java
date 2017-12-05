package so.asch.sdk;

import so.asch.sdk.dto.query.DappQureyParameters;

/**
 * Created by eagle on 17-7-7.
 */
public interface Dapp extends AschInterface{

    //获取dapp区块高度
    //接口地址：/api/dapps/dappId/blocks/height
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //height	integer	dapp区块高度
    AschResult getBlocksHeight(String dappId);

    //获取dapp区块数据
    //接口地址：/api/dapps/dappId/blocks
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N	根据表中字段排序，如height:desc
    //offset	integer	N	偏移量，最小值0
    //generatorPublicKey	string	N	区块生成者公钥
    //totalAmount	integer	N	交易总额，最小值：0，最大值：10000000000000000
    //totalFee	integer	N	手续费总额，最小值：0，最大值：10000000000000000
    //previousBlock	string	N	上一个区块
    //height	integer	N	区块高度
    AschResult getBlocks(String dappId, DappQureyParameters dappQureyParameters);


    //接口地址：/api/dapps/dappID/accounts/:address
    //请求方式：GET
    //支持格式：urlencode
    //请求参数说明：
    //address	string	Y	asch地址
    AschResult getAccount(String dappId,String address);

    //接口地址：/peer/transactions
    //请求方式：POST
    //支持格式：json
    //备注：充值时在主链发生type=6的交易（intransfer），dapp内部会自动调用编号为1的智能合约进行dapp内部充值 请求参数说明：
    AschResult dappDeposit(String dappId,String currency,long amount,String secret,String secondSecret);
}
