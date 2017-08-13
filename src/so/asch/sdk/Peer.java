package so.asch.sdk;

import so.asch.sdk.dto.query.PeerQueryParameters;

/**
 * Asch节点接口
 * @author eagle
 * 参见 https://github.com/AschPlatform/asch-docs/blob/master/asch_http_interface.md#25-%E8%8A%82%E7%82%B9peers
 */
public interface Peer extends AschInterface {
    //接口地址：/api/peers
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //state	integer	N	节点状态,0: ,1:,2:,3:
    //os	string	N	内核版本
    //version	string	N	asch版本号
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //orderBy	string	N
    //offset	integer	N	偏移量，最小值0
    //port	integer	N	端口，1~65535
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //peers	Array	节点信息json构成的数组
    //totalCount	integer	当前正在运行的节点个数
    AschResult queryPeers(PeerQueryParameters parameters);

    //接口地址：/api/peers/version
    //请求方式：get
    //支持格式：无
    //请求参数说明：无参数
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //version	string	版本号
    //build	timestamp	构建时间
    //net	string	主链或者测试链
    AschResult getVersion();

    //接口地址：/api/peers/get
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //ip	string	Y	待查询节点ip
    //port	integer	Y	待查询节点端口，1~65535
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //peer	json
    AschResult getPeer(String ip, int port);

}
