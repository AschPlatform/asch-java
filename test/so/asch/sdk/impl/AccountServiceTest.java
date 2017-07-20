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
        JSONObject json = TestHelper.accountService().login(TestHelper.secret);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testSecureLogin() throws Exception {
        JSONObject json = TestHelper.accountService().secureLogin(TestHelper.secret);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testGetAccount() throws Exception {
        JSONObject json = TestHelper.accountService().getAccount(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testGetBalance() throws Exception {
        JSONObject json = TestHelper.accountService().getBalance(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testGetPublicKey() throws Exception {
        JSONObject json = TestHelper.accountService().getPublicKey(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testGeneratePublicKey() throws Exception {
        JSONObject json = TestHelper.accountService().generatePublicKey(TestHelper.secret);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testVote() throws Exception {
        JSONObject json = TestHelper.accountService().vote(
                TestHelper.secret,
                TestHelper.secondSecret,
                TestHelper.voted,
                TestHelper.canceled
        );

        Assert.assertTrue(TestHelper.isSuccess(json) ||
                "account has already voted for this delegate".equals(json.getString("error"))||
                "Failed to remove vote, account has not voted for this delegate".equals(json.getString("error")));
    }

    @Test
    public void testGetDelegatesFee() throws Exception {
        JSONObject json = TestHelper.accountService().getDelegatesFee();
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testGetVotedDelegates() throws Exception {
        JSONObject json = TestHelper.accountService().getVotedDelegates(TestHelper.address);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @Test
    public void testGetTopAccounts() throws Exception {
        QueryParameters parameters = new QueryParameters()
                .setLimit(50)
                .setOffset(0);

        JSONObject json = TestHelper.accountService().getTopAccounts(parameters);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }
}
