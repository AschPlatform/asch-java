package so.asch.sdk;

//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.LoggerContext;
import so.asch.sdk.dto.query.TransactionQueryParameters;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) {
        try {

            //如果不需要在控制台查看HTTP通讯细节，可以把下面这行代码注释掉。
            initLog();

            String url = "http://127.0.0.1:4096";
            String secret = "early sugar cannon mansion expose tunnel piece manual destroy exhaust helmet rather";
            String address = "7286541193277597873";
            String secondSecret = "asch111111";
            String userName = "asch_g2";

            //String secret2 = "raise cloud royal wonder cradle job gate wife depth device bitter hope";
            //asch_g101	11705168753296944226 00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9
            //asch_g96	16093201530526106149 0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366
            //asch_g32	4354832300657989346 0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75
            String[] voted = "00dd0e70fe22e9b580ba0ccf696f1d108a6475bb303b8689947ba346b0b02ad9,0225c340addf24211f4b51ea24cb0565c67f09600f57bad89af350b38b08a366,0ae2e3bcd8c959bccc34445a9473eab1bece60300f3aa00d89612923470dee75".split(",");

            //asch_g23	4752137788839516181 486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6
            //asch_g40	16494449392359591122 49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1
            String[] canceled = "486179424b4bcfaf7960a4a121277d73e4a7c9e0c27b254edf978762d5a6dfe6,49b369ff2635e2ac083d62a6c59baf872fbdef6990297f296c95e1830118aba1".split(",");
            String publicKey = AschSDK.Helper.getPublicKey(secret);

            long lastCheckedBlockHeight = 0;

            //设置Asch服务地址
            AschSDK.Config.setAschServer(url);
            //设置成对应的网络Magic值
            AschSDK.Config.setMagic("5f5b3cf5");


            //生成10个一级密钥（账户）
            for( int i=0; i<10; i++) {
                String newSecret = AschSDK.Helper.generateSecret();
                System.out.println(newSecret);
            }

            //登录
            AschResult result = AschSDK.Account.secureLogin(secret);
            System.out.println(result.getRawJson());
            if(result.isSuccessful()){
                Map<String,Object> accountMap = (Map<String,Object>)result.parseMap().get("account");
                String accountPublicKey = AschSDK.Helper.getPublicKey(secret);
                String accountAddress = accountMap.get("address").toString();
                BigDecimal balance = BigDecimal.valueOf(Long.parseLong(accountMap.get("balance").toString()), 8);

                System.out.println(String.format("登录成功, publicKey:%s, address:%s, XAS余额：%s", accountPublicKey, accountAddress, balance.toString()));
            }

            //转账(金额为1XAS)
            result = AschSDK.Account.transfer("11705168753296944226",
                    AschSDK.Helper.amountForCoins(1),
                    /*"Transfer by Test" */
                    "  ", secret, secondSecret);
            System.out.println(result.getRawJson());
            if(result.isSuccessful()){
                String transactionId = result.parseMap().get("transactionId").toString();
                System.out.println("交易成功,transaction id：" + transactionId);

                int retry = 10 ;
                do {
                    //查询单条交易，注意：刚完成的交易查不到，正常情况需要1s-10s不等。建议多等一段时间。
                    result = AschSDK.Transaction.getTransaction(transactionId);
                    if (!result.isSuccessful())
                    {
                        System.out.println("查询交易失败或未查询到交易信息，3秒后重试...");
                        Thread.sleep(3000);
                    }
                    else
                    {
                        Map<String,Object> transactionMap = (Map<String,Object>)result.parseMap().get("transaction");
                        long blockHeight = Integer.parseInt(transactionMap.get("height").toString());
                        String blockId = transactionMap.get("blockId").toString();
                        TransactionType type = TransactionType.fromCode(Integer.parseInt(transactionMap.get("type").toString()));
                        Date time = AschSDK.Helper.dateFromAschTimestamp(Integer.parseInt(transactionMap.get("type").toString()));
                        String senderPublicKey = transactionMap.get("senderPublicKey").toString();
                        String senderId = transactionMap.get("senderId").toString();
                        String recipientId = transactionMap.get("recipientId").toString();
                        BigDecimal xasAmount = BigDecimal.valueOf(Long.parseLong(transactionMap.get("amount").toString()), 8);
                        BigDecimal fee = BigDecimal.valueOf(Long.parseLong(transactionMap.get("fee").toString()), 8);
                        //确认数，一般认为确认数达到6就可以认为交易已经成功了。。
                        int confirmations = Integer.parseInt(transactionMap.get("confirmations").toString());

                        String transactionInfo = String.format("block height:%d,\n block id:%s,\n transaction type:%s,\n " +
                                "time:%s,\n sender public key:%s,\n sender id:%s,\n recipient id:%s,\n XAS amount:%s,\n fee:%s,\n confirmations:%d ",
                                blockHeight, blockId, type.getCode(), time.toString(), senderPublicKey, senderId, recipientId,
                                xasAmount.toString(), fee.toString(), confirmations);
                        System.out.println("查询交易成功，交易信息：\n" +transactionInfo);
                    }

                }while(!result.isSuccessful() && retry-- > 0);

            }

            //查询交易transaction
            TransactionQueryParameters parameters = new TransactionQueryParameters()
                    .setRecipientId("21705168753296944226")
                    .setLimit(10)
                    .orderByAscending("t_timestamp");
            result = AschSDK.Transaction.queryTransactions(parameters);
            System.out.println(result.getRawJson());

            //这个值建议保存到数据库中，每次接着上次检查的块后开始。
            //下面的代码建议放到线程中循环执行，两次调用可间隔一段时间。
            long currentHeight = 0;
            result = AschSDK.Block.getHeight(); /*监控系统产生的当前最新区块信息*/
            if (result.isSuccessful()){
                currentHeight = Long.parseLong(result.parseMap().get("height").toString());
                if (currentHeight > lastCheckedBlockHeight++){
                    //枚举区块中的所有交易，判断是否有接收人为自己的交易
                    handleBlockTransactions(lastCheckedBlockHeight);
                }
            }

       }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private static boolean handleBlockTransactions(long height){
        AschResult result = AschSDK.Block.getBlockByHeight(height, false);
        if (!result.isSuccessful()){
            return false;
        }

        Map<String,Object> block = ( Map<String,Object>) result.parseMap().get("block");
        int numberOftransactions = (int)block.get("numberOfTransactions");
        if (numberOftransactions == 0) {
            return true;
        }

        String blockId = block.get("id").toString();
        result = AschSDK.Transaction.queryTransactions(new TransactionQueryParameters().setBlockId(blockId));
        if (!result.isSuccessful()){
            return false;
        }

        List<Map<String, Object>> transactions = (List<Map<String, Object>>)result.parseMap().get("transactions");
        for (Map<String, Object> trans : transactions) {
            String recipientId = trans.get("recipientId").toString();
            if (recipientId == "自己平台的地址"){
                //TODO:处理自己的交易，交易信息结构请参考：
                /*
                {
                    "id": "5a61b58b75a70a42a6d51deba4dba560c78b2d671dfac68d37984eb464421d81", // 交易id
                    "height": "3183940",    //区块高度
                    "blockId": "951e14ef5100a9724a133f74e8f5c35e0d872aee654e7ea5323e57cd1c7b004e",  //区块id
                    "type": 0, // 交易类型，0：普通转账
                    "timestamp": 36252686,  // 交易时间戳，Asch时间，可以换算成现实世界的时间
                    "senderPublicKey": "40e322be1ec9084f48a17b5fecf88d59d0c70ce7ab06b1c4f6d285acfa3b0525",
                    "senderId": "AC4i4srjg1TyW24p8M4B8NTcYApUgvTpkd",   // 发送地址
                    "recipientId": "ALu3f2GaGrWzG4iczamDmGKr4YsbMFCdxB", // 接收地址,如果是平台地址，则需要做处理
                    "amount": 80000000, //转账金额，除以100000000后是真实的XAS个数，这里0.8XAS
                    "fee": 10000000,
                    "signature": "08a97ba29f7db324b31f782272e17c048f4b99d1761830bd7f541c484c28fcf14b1ee0dbbdd05ab2e80d186473e67d9bfed8e27b8c5e096d29a7f521236d8900",
                    "signSignature": "",
                    "signatures": null,
                    "confirmations": "20",  // 区块确认数
                    "args": [],
                    "message": "",  // 转账备注
                    "asset": {
                    }
                }
                * */
            }
        }

        return true;
    }

    protected static void initLog(){

        if (AschSDK.Config.isDebugMode()) {
//            final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
//            ctx.getConfiguration()
//                    .getRootLogger()
//                    .setLevel(Level.ALL);
        }
    }

}
