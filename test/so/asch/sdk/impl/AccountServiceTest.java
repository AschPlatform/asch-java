package so.asch.sdk.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import so.asch.sdk.AschResult;
import so.asch.sdk.AschSDK;
import so.asch.sdk.TestData;
import so.asch.sdk.dto.query.QueryParameters;

import java.io.File;
import java.io.IOException;

/**
 * AccountService Tester.
 *
 * @author eagle
 * @since <pre>07/16/2017</pre>
 * @version 1.0
 */
public class AccountServiceTest {
    @BeforeSuite
    public void SetUp(){
        AschSDK.Config.setAschServer(TestData.root);
    }

    @Test
    public void testLogin() throws Exception {
        AschResult result= AschSDK.Account.login(TestData.secret);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testSecureLogin() throws Exception {
        AschResult result= AschSDK.Account.secureLogin(TestData.secret);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetAccount() throws Exception {
        AschResult result= AschSDK.Account.getAccount(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetBalance() throws Exception {
        AschResult result= AschSDK.Account.getBalance(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetPublicKey() throws Exception {
        AschResult result= AschSDK.Account.getPublicKey(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGeneratePublicKey() throws Exception {
        AschResult result= AschSDK.Account.generatePublicKey(TestData.secret);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testVote() throws Exception {
        AschResult result= AschSDK.Account.vote(
                TestData.voted,
                TestData.canceled,
                TestData.secret,
                TestData.secondSecret
        );

        Assert.assertTrue(result.isSuccessful() ||
                "account has already voted for this delegate".equals(result.getError())||
                "Failed to remove vote, account has not voted for this delegate".equals(result.getError()));
    }

    @Test
    public void testTransfer() throws Exception {
        AschResult result= AschSDK.Account.transfer(
                TestData.targetAddress,
                AschSDK.Helper.amountForCoins(1),
                "Transfer by Test",
                TestData.secret,
                TestData.secondSecret);

        Assert.assertTrue(result.isSuccessful() && result.getRawJson().contains("transactionId"));
    }

    @Test
    public void testGetDelegatesFee() throws Exception {
        AschResult result= AschSDK.Account.getDelegatesFee();
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetVotedDelegates() throws Exception {
        AschResult result= AschSDK.Account.getVotedDelegates(TestData.address);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetTopAccounts() throws Exception {
        QueryParameters parameters = new QueryParameters()
                .setLimit(50)
                .setOffset(0);

        AschResult result= AschSDK.Account.getTopAccounts(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void main() throws IOException {
        File a = FileUtils.getFile("D:/a.txt");
        String str = FileUtils.readFileToString(a,"utf-8");
        JSONObject json = JSON.parseObject(str);
        JSONArray arry = JSONArray.parseArray(json.get("delegates").toString());
        for(int i = 0;i<arry.size();i++){
            JSONObject obj= (JSONObject) arry.get(i);
            String username =  obj.getString("username");
            String productivity =  obj.getString("productivity");
            String producedblocks = obj.getString("producedblocks");
            String balance = obj.getString("balance");
            String approval = obj.getString("approval");
            System.out.println(username +"||" + productivity +"||" + producedblocks + "||" + balance + "||" + approval);
        }
    }
}
