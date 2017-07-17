package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import so.asch.sdk.TestHelper;
import so.asch.sdk.dto.query.QueryParameters;
import so.asch.sdk.security.Crypto;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testSecureLogin() throws Exception {
        JSONObject json = TestHelper.accountService().secureLogin(TestHelper.secret);
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testGetAccount() throws Exception {
        JSONObject json = TestHelper.accountService().getAccount(TestHelper.address);
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testGetBalance() throws Exception {
        JSONObject json = TestHelper.accountService().getBalance(TestHelper.address);
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testGetPublicKey() throws Exception {
        JSONObject json = TestHelper.accountService().getPublicKey(TestHelper.address);
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testGeneratePublicKey() throws Exception {
        JSONObject json = TestHelper.accountService().generatePublicKey(TestHelper.secret);
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testVote() throws Exception {
        JSONObject json = TestHelper.accountService().vote(
                TestHelper.secret,
                Crypto.getHexPublicKey(TestHelper.secret),
                TestHelper.secondSecret,
                TestHelper.votedPublicKeys,
                TestHelper.cancelVotedPublicKeys
        );

        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testGetDelegatesFee() throws Exception {
        JSONObject json = TestHelper.accountService().getDelegatesFee();
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testGetVotedDelegates() throws Exception {
        JSONObject json = TestHelper.accountService().getVotedDelegates(TestHelper.address);
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }

    @Test
    public void testGetTopAccounts() throws Exception {
        QueryParameters parameters = new QueryParameters()
                .setLimit(50)
                .setOffset(0);

        JSONObject json = TestHelper.accountService().getTopAccounts(parameters);
        assertThat(TestHelper.isSuccess(json), is(equalTo(true)));
    }
}
