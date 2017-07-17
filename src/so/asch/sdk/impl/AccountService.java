package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import so.asch.sdk.Account;
import so.asch.sdk.dto.query.QueryParameters;
import so.asch.sdk.security.Crypto;

import java.util.List;

/**
 * {@link Account}服务实现
 * @author eagle
 */
public class AccountService extends so.asch.sdk.impl.AschRESTService implements Account  {
    //本地不加密直接登陆（不推荐使用）
    //接口地址：/api/accounts/open/
    //请求方式：post
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //返回参数说明：
    //success	boole	是否登陆成功
    //account	json	账户信息
    public JSONObject login(String secret){
        JSONObject parameters = new JSONObject().fluentPut("secret", secret);
        return post("/api/accounts/open/", parameters);
    }

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
    public JSONObject secureLogin(String secret){
        try{
            String publicKey = Crypto.getHexPublicKey(secret);
            JSONObject parameters = new JSONObject().fluentPut("secret", publicKey);
            return post("/api/accounts/open/", parameters);
        }
        catch (Exception ex){
            return fail(ex);
        }
    }

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
    public JSONObject getAccount(String address){
        JSONObject parameters = new JSONObject().fluentPut("address", address);
        return get("/api/accounts", parameters);
    }

    //接口地址：/api/accounts/getBalance
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	用户地址,最小长度：1
    //返回参数说明
    //success	boole	是否成功获得response数据
    //balance	integer	余额
    //unconfirmedBalance	integer	未确认和已确认的余额之和，该值大于等于balance
    public JSONObject getBalance(String address){
        JSONObject parameters = new JSONObject().fluentPut("address", address);
        return get("/api/accounts/getBalance", parameters);
    }

    //接口地址：/api/accounts/getPublickey
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	用户地址,最小长度：1
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //publicKey	string	公钥*/
    public JSONObject getPublicKey(String address){
        JSONObject parameters = new JSONObject().fluentPut("address", address);
        return get("/api/accounts/getPublickey", parameters);
    }

    //接口地址：/api/accounts/generatePublickey
    //请求方式：post
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //publicKey	string	公钥
    public JSONObject generatePublicKey(String secret){
        JSONObject parameters = new JSONObject().fluentPut("secret", secret);
        return post("/api/accounts/generatePublicKey", parameters);
    }

    //接口地址：/api/accounts/delegates
    //请求方式：get
    //支持格式：urlencoded
    //请求参数说明：
    //address	string	Y	投票人地址
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //delegates	Array	已投票的受托人详情数组
    public JSONObject getVotedDelegates(String address){
        JSONObject parameters = new JSONObject().fluentPut("address", address);
        return get("/api/accounts/delegates", parameters);
    }

    //接口地址：/api/accounts/delegates/fee
    //请求方式：get
    //支持格式：无
    //请求参数说明：无
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //fee	integer	手续费设置
    public JSONObject getDelegatesFee(){
        return get("/api/accounts/delegates/fee");
    }

    //接口地址：/api/accounts/delegates
    //请求方式：put
    //支持格式：json
    //请求参数说明：
    //secret	string	Y	asch账户密码
    //publicKey	string	N	公钥
    //secondSecret	string	N	asch账户二级密码，最小长度：1，最大长度：100
    //delegates	Array	受托人公钥数组，每个公钥前需要加上+或者-号，代表增加/取消对其的投票
    //返回参数说明：
    //名称	类型	说明
    //success	boole	是否成功获得response数据
    //transaction	json	投票交易详情
    public JSONObject vote(String secret, String publicKey, String secondSecret,
                    List<String> votedPublicKeys, List<String> cancelVotedPublicKeys){
        return null;
    }

    //接口地址：/api/accounts/top
    //请求方式：get
    //支持格式：无
    //请求参数说明：如果不加请求参数则返回持币量前100名账户信息
    //limit	integer	N	限制结果集个数，最小值：0,最大值：100
    //offset	integer	N	偏移量，最小值0
    //返回参数说明：
    //success	boole	是否成功获得response数据
    //accounts	json	账户信息元组，每个元素包含地址、余额、公钥
    public JSONObject getTopAccounts(QueryParameters parameters){
        JSONObject jsonParameters = JSONObject.parseObject(JSONObject.toJSONString(parameters));
        return get("/api/accounts/top", jsonParameters);
    }
}
