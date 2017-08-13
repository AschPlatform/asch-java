package so.asch.sdk;

/**
 * Asch UIA接口
 * @author eagle
 */
public interface UIA extends AschInterface {
    //获取全网所有发行商
    //接口地址：/api/uia/issuers
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //返回参数说明：
    //success	boole	是否成功
    //issuers	list	元素为字典，每个字典代表一个发行商，包含发行商名字、描述、id（Asch地址）
    //count	integer	发行商总个数
    AschResult getIssuers(int limit, int offset);

    //查询指定发行商的信息
    //接口地址：/api/uia/issuers/name 请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //name	string	Y	可以为发行商名称或Asch账户地址
    //返回参数说明：
    //success	boole	是否成功
    //issuers	dict	包含发行商名字、描述、id（Asch地址）
    AschResult getIssuer(String nameOrAddress);

    //查看指定发行商的资产
    //接口地址：/api/uia/issuers/name/assets
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //name	string	Y	可以为发行商名称或Asch账户地址
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //返回参数说明：
    //success	boole	是否成功
    //assets	list	每个元素是一个字典，每个字典是一个资产详情，包含资产名字、描述、上限（最大发行量=真实发行量*10**精度）、精度、
    //                  策略、当前发行量、发行高度、发行商id，acl模式（0：黑名单，1：白名单）、是否注销
    //count	interger	该发行商注册的资产总个数（包含已注销的）
    AschResult queryIssuerAssets(String nameOrAddress, int limit, int offset);

    //获取全网所有资产
    //接口地址：/api/uia/assets
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //返回参数说明：
    //success	boole	是否成功
    //assets	list	每个元素是一个字典，每个字典是一个资产详情，包含资产名字、描述、上限、精度、策略、当前发行量、发行高度、发行商id，acl、是否注销
    //count	integer	所有资产的个数
    AschResult getAssets(int limit, int offset);

    //获取指定资产信息
    //接口地址：/api/uia/assets/name
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //name	string	Y	资产名
    //返回参数说明：
    //success	boole	是否成功
    //assets	dict	包含资产名字、描述、上限、精度、策略、当前发行量、发行高度、发行商id，acl、是否注销
    AschResult getAsset(String assertName);

    //获取某个资产的访问控制列表（acl）
    //接口地址：/api/uia/assets/name/acl/flag 请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //name	string	Y	资产名
    //flag	boole	Y	取值0和1，0表示黑名单，1表示白名单
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //返回参数说明：
    //success	boole	是否成功
    //list	list	符合规则的账户列表
    //count	integer	符合规则账户总数
    AschResult getAssetACL(String assertName, boolean whiteOrBlack, int limit, int offset);

    //获取某个地址拥有的所有资产信息
    //接口地址：/api/uia/balances/address 请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	账户地址
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //返回参数说明：
    //success	boole	是否成功
    //balances	list	拥有的资产详情列表，每个元素是一个资产，包含资产名、余额、上限、精度、当前发行量、是否注销（0：未注销，1：已注销）
    //count	integer	当前该地址拥有的资产个数
    AschResult getAddressBalances(String address, int limit, int offset);

    //获取资产交易记录
    //接口地址：/api/uia/transactions
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //ownerPublicKey	string	N	公钥
    //返回参数说明：
    //success	boole	是否成功
    //transactions	list	交易列表，每个元素是一个字典代表一次交易，包含交易id、区块高度、区块id、交易类型、时间戳、发送者公钥、发送者id、
    //                        接收者id（系统为空，如资产注册）、交易数量（资产交易都为0）、手续费0.1XAS、签名、多重签名、确认数、
    //                        资产信息（包含发行商id、发行商名字、描述）、交易id。
    //count	integer	资产交易总个数
    AschResult getTransactions(String ownerPublicKey, int limit, int offset);

    //注册资产发行商
    //请求参数说明：
    //name  string  Y   名称
    //desc  string  Y   描述
    //secret    string  Y   一级密码
    //secondSecret  string  Y   二级密码
    //返回参数说明：
    //success	boole	是否成功
    AschResult createIssuer(String name, String desc, String secret, String secondSecret);

    //注册资产
    //请求参数说明：
    //currency  string  Y   资产名称，发行商名.资产名，唯一标识,如：IssuerName.CNY
    //desc  string  Y   资产描述
    //maximum  integer  Y   上限
    //precision    integer  Y   精度(maximum = '1000000'，精度为3，代表资产IssuerName.CNY的最大发行量为1000.000)
    //strategy  string  Y   策略
    //secret    string  Y   一级密码
    //secondSecret  string  Y   二级密码
    //返回参数说明：
    //success	boole	是否成功
    AschResult createAsset(String currency, String desc, long maximum, byte precision, String strategy, String secret, String secondSecret);


    //资产设置acl模式
    //请求参数说明：
    //currency  string  Y   资产名称，发行商名.资产名，唯一标识,如：IssuerName.CNY
    //assertStatus（flagType)  int  Y   资产状态，1：流通，2：注销 （flagType)
    //whiteListMode(flag)  boole  Y   0：黑名单， 1：白名单，资产创建后默认为黑名单模式
    //secret    string  Y   一级密码
    //secondSecret  string  Y   二级密码
    //返回参数说明：
    //success	boole	是否成功
    AschResult setAssetACL(String currency, int assertStatus, boolean whiteListMode, String secret, String secondSecret);

    //资产发行
    //请求参数说明：
    //currency  string  Y   资产名称，发行商名.资产名，唯一标识,如：IssuerName.CNY
    //amount  int  Y   本次发行量，本次发行量=真实数量（100）*10**精度（3），所有发行量之和需 <= 上限*精度
    //secret    string  Y   一级密码
    //secondSecret  string  Y   二级密码
    //返回参数说明：
    //success	boole	是否成功
    AschResult issue(String currency, long amount, String secret, String secondSecret);

    //资产转账
    //请求参数说明：
    //currency  string  Y   资产名称，发行商名.资产名，唯一标识,如：IssuerName.CNY
    //amount  int  Y   本次发行量，本次发行量=真实数量（100）*10**精度（3），所有发行量之和需 <= 上限*精度
    //recipientId    string  Y   接收人
    //secret    string  Y   一级密码
    //secondSecret  string  Y   二级密码
    //返回参数说明：
    //success	boole	是否成功
    AschResult transfer(String currency, String recipientId, long amount, String message, String secret, String secondSecret);

    //资产注销
    //资产设置acl模式
    //请求参数说明：
    //currency  string  Y   资产名称，发行商名.资产名，唯一标识,如：IssuerName.CNY
    //assertStatus（flagType)  int  Y   资产状态，1：流通，2：注销 （flagType)
    //whiteListMode(flag)  boole  Y   0：黑名单， 1：白名单，资产创建后默认为黑名单模式
    //secret    string  Y   一级密码
    //secondSecret  string  Y   二级密码
    //返回参数说明：
    //success	boole	是否成功
    AschResult setAssetStatus(String currency, int assertStatus, boolean whiteListMode, String secret, String secondSecret);

}
