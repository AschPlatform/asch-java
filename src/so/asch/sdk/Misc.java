package so.asch.sdk;

/**
 * Created by eagle on 17-7-10.
 */
public interface Misc extends AschInterface{
    //查看本地区块链加载状态
    //接口地址：/api/loader/status
    //请求方式：get
    //支持格式：无
    //请求参数说明：无参数
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //loaded	boole
    //blocksCount	integer
    AschResult getLoadStatus();

    //查看区块同步信息
    //接口地址：/api/loader/status/sync
    //请求方式：get
    //支持格式：无
    //请求参数说明：无参数
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //height	int	区块高度
    AschResult getSyncStatus();

    //上传数据
    //接口地址：/api/storages
    //请求方式：PUT
    //支持格式：json
    //接口备注：创建者账户需在web端钱包登陆过
    //请求参数说明：
    //secondSecret	string	N	发送者二级密码，最小长度1，最大长度：100
    //content	string	Y	上传数据内容
    //encode	string	N	上传数据的格式，可选项为：raw/base64/hex，默认为raw
    //wait	number	N	等待确认数，范围为0-6，默认为0。wait为0表示不等待，速度最快，但无法保证数据在掉电情况下不丢失，大于2时，可以100%确保数据已经同步到大部分机器上了，但需要时间较长10-20秒之间，折中的方案是1,wait为1时虽然不能100%保证同步到其他机器，但失败的几率非常小，只是理论上存在，实际上还没遇到过
    //返回参数说明：
    //success	boole	是否成功获得response数据。
    //transactionId	string	交易id
    AschResult storeData(String content, ContentEncoding encoding, int wait, String secret, String secondSecret);

    //查询存储的数据
    //接口地址：/api/storages
    //请求方式：GET
    //支持格式：urlencode
    //请求参数说明：
    //transactionId(id)	string	Y	交易id
    //返回参数说明：
    //success	boole	是否成功获得response数据。
    //id	string	交易id
    AschResult getStoredData(String transactionId);

}
