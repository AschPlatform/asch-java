package so.asch.sdk;

import so.asch.sdk.dto.query.TransactionQueryParameters;

/**
 * Asch交易接口
 * @author eagle
 * 参见 https://github.com/AschPlatform/asch-docs/blob/master/http_api/zh-cn.md#221-%E8%8E%B7%E5%8F%96%E4%BA%A4%E6%98%93%E4%BF%A1%E6%81%AF
 */
public interface Transaction extends AschInterface{

    //接口地址：/api/transactions
    //请求方式：get
    //支持格式：urlencoded
    //接口备注：如果请求不加参数则会获取全网所有交易
    //请求参数说明：
    //limit     integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N	根据表中字段排序，如：senderId:DESC
    //offset	integer	N	偏移量，最小值0
    //height	integer	N	所在区块高度
    //senderId	string	N	发送者地址
    //message	string	N	备注
    //amount	integer	N	金额

    //返回参数说明：
    //success	boolean	是否成功获得response数据
    //transactions	数组 多个交易详情json构成的列表
    //count	int	获取到的交易总个数
    AschResult queryTransactions(TransactionQueryParameters parameters);

    //接口地址：/api/transactions/get
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //Id	string	Y	交易id
    //返回参数说明：
    //success	boolean	是否成功获得response数据
    //transactions	json	交易详情
    AschResult getTransaction(String transactionId);

    //接口地址：/api/transactions/unconfirmed/get
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //id	string	Y	未确认交易id
    //返回参数说明：
    //success	boolean	是否成功获得response数据
    //transaction	json	未确认交易详情
    AschResult getUnconfirmedTransaction(String unconfirmedTransactionId);


    //接口地址：/api/transactions/unconfirmed
    //请求方式：get
    //支持格式：urlencoded
    //接口说明：如果不加参数，则会获取全网所有未确认交易 请求参数说明：
    //senderPublicKey	string	N	发送者公钥
    //address	string	N	地址
    //返回参数说明：
    //success	boolean	是否成功获得response数据
    //transactions	Array	未确认交易列表
    AschResult getUnconfirmedTransactions(String senderPublicKey, String address);

}
