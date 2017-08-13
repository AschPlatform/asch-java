package so.asch.sdk.impl;

import org.testng.Assert;
import so.asch.sdk.AschResult;
import so.asch.sdk.AschSDK;
import so.asch.sdk.TestData;
import so.asch.sdk.dto.query.DelegateQueryParameters;

public  class DelegateServiceTest {

    @org.testng.annotations.Test
    public void testGetCount() throws Exception {
        Assert.assertNotNull(AschSDK.Delegate);
        AschResult result= AschSDK.Delegate.getDelegatesCount();
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetVoters() throws Exception {
        AschResult result= AschSDK.Delegate.getVoters(TestData.publicKey());
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegateByPublicKey() throws Exception {
        AschResult result= AschSDK.Delegate.getDelegateByPublicKey(TestData.publicKey());
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegateByName() throws Exception {
        AschResult result= AschSDK.Delegate.getDelegateByName(TestData.userName);
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegates() throws Exception {
        DelegateQueryParameters query = new DelegateQueryParameters()
                .setLimit(10);
        AschResult result= AschSDK.Delegate.queryDelegates(query);
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetDelegateFee() throws Exception {
        AschResult result= AschSDK.Delegate.getDelegateFee(TestData.publicKey());
        Assert.assertTrue(result.isSuccessful());
    }

    @org.testng.annotations.Test
    public void testGetForging() throws Exception {
    }

    @org.testng.annotations.Test
    public void testRegisterDelegate() throws Exception {
        AschResult result= AschSDK.Delegate.registerDelegate(TestData.userName, TestData.secret, TestData.secondSecret);
        Assert.assertTrue( result.isSuccessful()||
                "Account is already a delegate".equals(result.getError()));
    }

    @org.testng.annotations.Test
    public void testEnableForge() throws Exception {
    }

    @org.testng.annotations.Test
    public void testDisableForge() throws Exception {
    }

    @org.testng.annotations.Test
    public void testGetForgingStatus() throws Exception {
    }
}