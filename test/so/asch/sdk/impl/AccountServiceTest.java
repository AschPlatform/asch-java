package so.asch.sdk.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import so.asch.sdk.AschResult;
import so.asch.sdk.AschSDK;
import so.asch.sdk.TestData;


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
    public void testTransferXAS() throws Exception {
        AschResult result = AschSDK.Account.transferXAS(
                TestData.targetAddress,
                AschSDK.Helper.amountForCoins(1),
                "Transfer by Test",
                TestData.secret,
                TestData.secondSecret);

        Assert.assertTrue(result.isSuccessful() && result.getRawJson().contains("transactionId"));
    }

}
