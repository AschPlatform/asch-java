package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import so.asch.sdk.TestHelper;
import so.asch.sdk.dto.query.QueryParameters;

/**
 * AccountService Tester.
 *
 * @author eagle
 * @since <pre>07/16/2017</pre>
 * @version 1.0
 */
public class AccountServiceTest {
    @Test
    public void testLogin() throws Exception {
        JSONObject result = TestHelper.accountService().login(TestHelper.secret);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testSecureLogin() throws Exception {
        JSONObject result = TestHelper.accountService().secureLogin(TestHelper.secret);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetAccount() throws Exception {
        JSONObject result = TestHelper.accountService().getAccount(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetBalance() throws Exception {
        JSONObject result = TestHelper.accountService().getBalance(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetPublicKey() throws Exception {
        JSONObject result = TestHelper.accountService().getPublicKey(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGeneratePublicKey() throws Exception {
        JSONObject result = TestHelper.accountService().generatePublicKey(TestHelper.secret);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testVote() throws Exception {
        JSONObject result = TestHelper.accountService().vote(
                TestHelper.voted,
                TestHelper.canceled,
                TestHelper.secret,
                TestHelper.secondSecret
        );

        Assert.assertTrue(TestHelper.isSuccess(result) ||
                "account has already voted for this delegate".equals(result.getString("error"))||
                "Failed to remove vote, account has not voted for this delegate".equals(result.getString("error")));
    }

    @Test
    public void testTransfer() throws Exception {
        JSONObject result = TestHelper.accountService().transfer(
                TestHelper.targetAddress,
                1* 100000000,
                "Transfer by Test",
                TestHelper.secret,
                TestHelper.secondSecret);

        Assert.assertTrue(TestHelper.isSuccess(result) && result.containsKey("transactionId"));
    }

    @Test
    public void testGetDelegatesFee() throws Exception {
        JSONObject result = TestHelper.accountService().getDelegatesFee();
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetVotedDelegates() throws Exception {
        JSONObject result = TestHelper.accountService().getVotedDelegates(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }

    @Test
    public void testGetTopAccounts() throws Exception {
        QueryParameters parameters = new QueryParameters()
                .setLimit(50)
                .setOffset(0);

        JSONObject result = TestHelper.accountService().getTopAccounts(parameters);
        Assert.assertTrue(TestHelper.isSuccess(result));
    }
}
