package so.asch.sdk;

/**
 * Asch账户接口
 * @author eagle
 * 参见 https://github.com/AschPlatform/asch-docs/blob/master/http_api/zh-cn.md#21-%E8%B4%A6%E6%88%B7accounts
 */
public interface Account extends AschInterface {

    //本地不加密直接登陆（不推荐使用）
    //接口地址：/api/accounts/open/
    //请求方式：post
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //返回参数说明：
    //success	boolean	是否登陆成功
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
    //success	boolean	是否登陆成功
    //account	json	账户信息
    AschResult secureLogin(String secret);

    //通过地址取用户信息
    //接口地址：/api/v2/accounts/:address
    //请求方式：get
    //支持格式：urlencoded
    //address	string	Y	用户地址,最小长度：1
    //返回参数说明：
    //success	boolean	是否成功获得response数据
    //account	json	账户信息
    //latestBlock	json	最新的区块信息
    //version	json	版本相关信息
    AschResult getAccount(String address);

    //接口地址：/api/v2/balances/:address
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	用户地址,最小长度：1
    //返回参数说明
    //success	boolean	是否成功获得response数据
    //balance	integer	余额
    //unconfirmedBalance	integer	未确认和已确认的余额之和，该值大于等于balance
    AschResult getBalance(String address);


    //接口地址：/api/accounts/new
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：无
    //返回参数说明：
    //success	boolean	是否成功获得response数据
    //secret    string  新生成的账户助一级密码
    AschResult newAccount();

    //XAS转账[transaction]
    //请求参数说明：
    //targetAddress string Y    目标地址
    //amount    long    Y   转账金额
    //secret	string	Y	asch账户密码
    //secondSecret	string	N	asch账户二级密码，最小长度：1，最大长度：100
    //返回参数说明：
    //名称	类型	说明
    //success	boolean	是否成功获得response数据
    AschResult transferXAS(String targetAddress, long amount, String message, String secret, String secondSecret);

    //UIA转账[transaction]
    //请求参数说明：
    //targetAddress string Y    目标地址
    //amount    long    Y   转账金额
    //currency   string  Y   转账资产名称
    //secret	string	Y	asch账户密码
    //secondSecret	string	N	asch账户二级密码，最小长度：1，最大长度：100
    //返回参数说明：
    //名称	类型	说明
    //success	boolean	是否成功获得response数据
    AschResult transferUIA(String targetAddress, long amount, String currency, String message, String secret, String secondSecret);

}
