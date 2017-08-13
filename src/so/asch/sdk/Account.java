package so.asch.sdk;

import so.asch.sdk.dto.query.QueryParameters;

/**
 * Asch账户接口
 * @author eagle
 * 参见 https://github.com/AschPlatform/asch-docs/blob/master/asch_http_interface.md#21-%E8%B4%A6%E6%88%B7accounts
 */
public interface Account extends AschInterface {

    //本地不加密直接登陆（不推荐使用）
    //接口地址：/api/accounts/open/
    //请求方式：post
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //返回参数说明：
    //success	boole	是否登陆成功
    //account	json	账户信息
    AschResult login(String secret);

    //本地加密后再登陆（推荐使用）
    //接口地址：/api/accounts/open2/
    //请求方式：post
    //支持格式：json
    //接口备注：公钥需要根据用户提供的密码在在本地用程序生成
    //请求参数说明：
    //publicKey	string	Y	asch账户公钥
    //返回参数说明：
    //success	boole	是否登陆成功
    //account	json	账户信息
    AschResult secureLogin(String secret);

    //通过地址取用户信息
    //接口地址：/api/accounts
    //请求方式：get
    //支持格式：urlencoded
    //address	string	Y	用户地址,最小长度：1
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //account	json	账户信息
    //latestBlock	json	最新的区块信息
    //version	json	版本相关信息
    AschResult getAccount(String address);

    //接口地址：/api/accounts/getBalance
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	用户地址,最小长度：1
    //返回参数说明
    //success	boole	是否成功获得response数据
    //balance	integer	余额
    //unconfirmedBalance	integer	未确认和已确认的余额之和，该值大于等于balance
    AschResult getBalance(String address);

    //接口地址：/api/accounts/getPublickey
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	用户地址,最小长度：1
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //publicKey	string	公钥*/
    AschResult getPublicKey(String address);

    //接口地址：/api/accounts/generatePublickey
    //请求方式：post
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //publicKey	string	公钥
    AschResult generatePublicKey(String secret);

    //接口地址：/api/accounts/delegates
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	投票人地址
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //delegates	Array	已投票的受托人详情数组
    AschResult getVotedDelegates(String address);

    //接口地址：/api/accounts/delegates/fee
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //fee	integer	手续费设置
    AschResult getDelegatesFee();

    //投票[transaction]
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //secondSecret	string	N	asch账户二级密码，最小长度：1，最大长度：100
    //delegates	Array	受托人公钥数组，每个公钥前需要加上+或者-号，代表增加/取消对其的投票
    //返回参数说明：
    //名称	类型	说明
    //success	boole	是否成功获得response数据
    //transaction	json	投票交易详情
    AschResult vote( String[] upvotePublicKeys, String[] downvotePublicKeys, String secret, String secondSecret);


    //转账[transaction]
    //请求参数说明：
    //targetAddress string Y    目标地址
    //amount    long    Y   转账金额
    //secret	string	Y	asch账户密码
    //secondSecret	string	N	asch账户二级密码，最小长度：1，最大长度：100
    //返回参数说明：
    //名称	类型	说明
    //success	boole	是否成功获得response数据
    AschResult transfer(String targetAddress, long amount, String message, String secret, String secondSecret);

    //接口地址：/api/accounts/top
    //请求方式：get
    //支持格式：无
    //请求参数说明：如果不加请求参数则返回持币量前100名账户信息
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //accounts	json	账户信息元组，每个元素包含地址、余额、公钥
    AschResult getTopAccounts(QueryParameters parameters);

}
