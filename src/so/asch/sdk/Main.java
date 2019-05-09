package so.asch.sdk;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Main {

    private static int lastCheckedBlockHeight = 0;

    public static void main(String[] args) {
        //配置SDK
        configSDK();

        //生成5个新账户（一级密码）
        String[] secrets = generateAccounts(5);

        //使用第一个账号登录，并获取其地址
        String address = login(secrets[0]);

        //从创世账号向第一个账号转账 10 XAS
        String secret = "stone elephant caught wrong spend traffic success fetch inside blush virtual element";
        String transactionId = transfer(secret, null, address, BigDecimal.valueOf(10));

        //取最新区块高度
        int lastHeight = getLastHeight();
        //验证交易写入区块
        boolean found = getTransactionWithRetry(lastHeight, transactionId);
        if (!found) return;

        //再次登录，检查余额
        login(secrets[0]);

        //检查新区块中感兴趣的交易
        monitorTransactions();
    }

    private static void configSDK() {
        String serverUrl = "http://127.0.0.1:4096"; //本地节点
        String magic = "594fe0f3"; //localnet 或 testnet，mainnet 为 5f5b3cf5

        //设置Asch服务地址
        AschSDK.Config.setAschServer(serverUrl);
        //设置成对应的网络Magic
        AschSDK.Config.setMagic(magic);
        //不打印控制台日志
        AschSDK.Config.setDebugLogEnabled(false);
    }

    private static void printFailed(String operation, AschResult result) {
        System.out.println(operation + "失败, error: " + result.getError() + ", ex" + result.getError());
    }

    //生成一级密钥（账户）
    private static String[] generateAccounts(int count) {
        String[] secrets = new String[count];
        for (int i = 0; i < count; i++) {
            AschResult newAccountResult = AschSDK.Account.newAccount();
            if (!newAccountResult.isSuccessful()) {
                printFailed("生成新账户", newAccountResult);
            } else {
                String secret = newAccountResult.parseMap().get("secret").toString();
                secrets[i] = secret;
                System.out.println("账户 " + (i + 1) + " 一级密钥 : " + secret);
            }
        }
        return secrets;
    }

    //登录
    private static String login(String secret) {
        AschResult result = AschSDK.Account.secureLogin(secret);
        if (!result.isSuccessful()) {
            printFailed("登录", result);
            return null;
        }

        Map<String, Object> accountMap = (Map<String, Object>) result.parseMap().get("account");
        String publicKey = AschSDK.Helper.getPublicKey(secret);
        String address = accountMap.get("address").toString();
        //处理精度，XAS精度为8
        BigDecimal balance = BigDecimal.valueOf(Long.parseLong(accountMap.get("balance").toString()), 8);

        System.out.println(String.format("登录成功, publicKey: %s, address: %s, XAS余额：%s", publicKey, address, balance.toString()));
        System.out.println("原始JSON: " + result.getRawJson());

        return address;
    }

    private static String transfer(String secret, String secondSecret, String targetAddress, BigDecimal amount) {
        AschResult result;
        //需要做精度转换（辅助函数AschSDK.Helper.amountForXAS）
        result = AschSDK.Account.transferXAS(targetAddress, AschSDK.Helper.amountForXAS(amount), "  ", secret, secondSecret);
        if (!result.isSuccessful()) {
            printFailed("转账", result);
            return null;
        }

        String transactionId = (String) result.parseMap().get("transactionId");
        //注意，提交交易成功不代表交易成功。需要经过区块确认才可以认为成功
        System.out.println("提交交易成功, id : " + transactionId);

        return transactionId;
    }

    private static boolean getTransactionWithRetry(int lastHeight, String transactionId) {
        int retry = 10;
        AschResult result;
        try {
            do {
                //查询单条交易，注意：刚完成的交易查不到，正常情况需要1s-10s不等。建议多等一段时间。
                result = AschSDK.Transaction.getTransaction(transactionId);
                if (!result.isSuccessful()) {
                    System.out.println("查询交易失败或未查询到交易信息，5秒后重试...");
                    Thread.sleep(5000);
                } else {
                    Map<String, Object> transactionMap = (Map<String, Object>) result.parseMap().get("transaction");
                    int height = Integer.parseInt(transactionMap.get("height").toString());
                    TransactionType type = TransactionType.fromCode(Integer.parseInt(transactionMap.get("type").toString()));
                    Date time = AschSDK.Helper.dateFromAschTimestamp(Integer.parseInt(transactionMap.get("timestamp").toString()));
                    String senderPublicKey = transactionMap.get("senderPublicKey").toString();
                    String senderId = transactionMap.get("senderId").toString();
                    String message = transactionMap.get("message").toString();
                    String args = transactionMap.get("args").toString();
                    BigDecimal fee = BigDecimal.valueOf(Long.parseLong(transactionMap.get("fee").toString()), 8);
                    //确认数，一般认为确认数达到6就可以认为交易已经成功了。。
                    int confirmations = Math.max(lastHeight - height, 1);

                    String transactionInfo = String.format("  block height: %d,\n  transaction type: %s,\n" +
                                    "  time: %s,\n  sender public key: %s,\n  sender id: %s,\n  fee: %s,\n  message: %s \n" +
                                    "  args: %s,\n  confirmations: %d ",
                            height, type.getName(), time.toString(), senderPublicKey, senderId,
                            fee.toString(), message, args, confirmations);
                    System.out.println("查询交易成功，交易信息：\n" + transactionInfo);
                    return true;
                }

            } while (!result.isSuccessful() && retry-- > 0);
        } catch (Exception ex) {
            System.out.println("查询交易失败，ex: " + ex.getMessage());
            ex.printStackTrace();
        }
        return false;
    }

    private static int getLastHeight() {
        AschResult result = AschSDK.Block.getHeight(); /*监控系统产生的当前最新区块信息*/
        if (!result.isSuccessful()) {
            return -1;
        }
        return Integer.parseInt(result.parseMap().get("height").toString());
    }

    //检查交易的代码建议放到线程中循环执行，两次调用可间隔 5-10秒。
    private static void monitorTransactions() {
        try {
            //这个值保存到数据库中，每次接着上次检查完成的区块后开始
            int currentHeight = getLastHeight();
            if (currentHeight <= lastCheckedBlockHeight) {
                return;
            }

            //枚举区块中的所有交易，判断是否有接收人为自己的交易
            do {
                int height = lastCheckedBlockHeight + 1;
                System.out.println("检查区块高度为 " + height + " 的交易信息...");
                handleBlockTransactions(height);
                Thread.sleep(3000);
                lastCheckedBlockHeight = height;
            } while (lastCheckedBlockHeight < currentHeight);
        } catch (Exception ex) {
            System.out.println("检查交易失败，ex: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static boolean handleBlockTransactions(long height){
        AschResult result = AschSDK.Block.getBlockByHeight(height, true);
        if (!result.isSuccessful()){
            return false;
        }

        Map<String,Object> block = ( Map<String,Object>) result.parseMap().get("block");
        int transactionsCount = (int) block.get("count");
        System.out.println("共有交易 " + transactionsCount + " 条");

        if (transactionsCount == 0) {
            return true;
        }

        List<Map<String, Object>> transactions = (List<Map<String, Object>>) block.get("transactions");
        for (Map<String, Object> trans : transactions) {
            String senderId = trans.get("senderId").toString();
            System.out.println("transaction: " + trans.toString());
            if (senderId == "感兴趣的转账者") { //或是其他感兴趣的交易
                //TODO:处理自己的交易，交易信息结构请参考：
                /*
                {
                    "id": "7ead693c1fcbd6abe8e422548c121054b1cdc00f1a35eeb415afba3f637b56f3",
                    "type": 1,
                    "timestamp": 90235198,
                    "senderId": "ABuH9VHV3cFi9UKzcHXGMPGnSC4QqT2cZ5",
                    "senderPublicKey": "116025d5664ce153b02c69349798ab66144edd2a395e822b13587780ac9c9c09",
                    "requestorId": null,
                    "fee": 10000000,
                    "signatures": [
                      "528b9c6ea65fc538351f4c62771344675c71168739f9b37b9b5747bd35f3afea50c5b1d47148bdc8154d5a9c7806272346fe14ce1b7c82430afb3fea5334be05"
                    ],
                    "secondSignature": null,
                    "args": [
                      "1000000000",
                      "A7BAf9nowwsAxRUe7TxKx37A5Ff4xBkkq2"
                    ],
                    "height": 10452,
                    "message": "  "
                }
                * */
            }
        }

        return true;
    }


}
