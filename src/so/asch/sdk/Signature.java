package so.asch.sdk;

/**
 * Asch签名接口
 * @author eagle
 * 参见 https://github.com/AschPlatform/asch-docs/blob/master/asch_http_interface.md#27-%E4%BA%8C%E7%BA%A7%E5%AF%86%E7%A0%81signatures
 *     https://github.com/AschPlatform/asch-docs/blob/master/asch_http_interface.md#28-%E5%A4%9A%E9%87%8D%E7%AD%BE%E5%90%8Dmultisignatures
 */
public interface Signature extends AschInterface{

    //设置二级密码
    //接口地址：/api/signatures
    //请求方式：put
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //publicKey	string	N	公钥
    //secondSecret	string	Y	asch账户二级密码，最小长度：1，最大长度：100
    //multisigAccountPublicKey	string	N	多重签名账户公钥
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transaction	json	设置二级密码产生的交易详情
    AschResult setSignature(String secret, String secondSecret, String publicKey, String multiSignAccountPublicKey);

    //获取二级密码设置费
    //接口地址：/api/signatures/fee
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //fee	integer	费用
    AschResult getSignatureFee();


    //设置普通账户为多重签名账户
    //接口地址：/api/multisignatures
    //请求方式：put
    //支持格式：json
    //接口说明：返回结果只是生成交易id，还需要其他人签名后该账户才能成功设置成多重签名账户。注册多重签名账户后任意一笔转账都需要多人签名，
    // 签名最少个数为min的值（含交易发起人自身）
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //publicKey	string	N	公钥
    //secondSecret	string	N	asch账户二级密码，最小长度：1，最大长度：100
    //min	integer	Y	多重签名交易账户的任意一笔转账都需要多人签名的最少个数，如果是注册多重签名账户操作，这该值不生效（此时需要所有人都签名）。
    //                  最小值：2，最大值：16,该值需要小于keysgroup.length+1
    //lifetime	integer	Y	多重签名交易的最大挂起时间，最小值：1，最大值：24，暂时不生效
    //keysgroup	array	Y	其它签名人的公钥数组，每个公钥前需要加上+或者-号，代表增加/删除多重签名账户，数组最小长度：1，数组最大长度：10
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transactionId	string	多重签名交易的id
    AschResult setMultiSignature(int minAccount, int lifetime, String[] addKeys, String[] removeKeys,
                                  String secret, String secondSecret, String publicKey);

    //非交易发起人对交易进行多重签名
    //接口地址：/api/multisignatures/sign
    //请求方式：post
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //secondSecret	string	N	asch账户二级密码，最小长度：1，最大长度：100
    //publicKey	string	N	公钥
    //transactionId	string	Y	交易id
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transactionId	string	多重签名交易id
    AschResult multiSignature(String transactionId, String secret, String secondSecret, String publicKey );

    //获取挂起的多重签名交易详情
    //接口地址：/api/multisignatures/pending
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //publicKey	string	Y	公钥
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //transactions	Array	交易json组成的数组
    AschResult getPendingTransactions(String publicKey);

    //获取多重签名账户信息
    //接口地址：/api/multisignatures/accounts
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //publicKey	string	Y	多重签名参与者之一的公钥
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //accounts	Array	多重签名账户详情
    AschResult getMultiSignatureAccounts(String publicKey);

}
