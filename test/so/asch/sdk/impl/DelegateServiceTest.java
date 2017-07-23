package so.asch.sdk.impl;

import com.alibaba.fastjson.JSONObject;
import org.testng.Assert;
import so.asch.sdk.TestHelper;
import so.asch.sdk.dto.query.DelegateQueryParameters;

public  class DelegateServiceTest {

    @org.testng.annotations.Test
    public void testGetCount() throws Exception {
        Assert.assertNotNull(TestHelper.delegateService());
        JSONObject json = TestHelper.delegateService().getDelegatesCount();
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @org.testng.annotations.Test
    public void testGetVoters() throws Exception {
        JSONObject json = TestHelper.delegateService().getVoters(TestHelper.publicKey());
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @org.testng.annotations.Test
    public void testGetDelegateByPublicKey() throws Exception {
        JSONObject json = TestHelper.delegateService().getDelegateByPublicKey(TestHelper.publicKey());
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @org.testng.annotations.Test
    public void testGetDelegateByName() throws Exception {
        JSONObject json = TestHelper.delegateService().getDelegateByName(TestHelper.userName);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @org.testng.annotations.Test
    public void testGetDelegates() throws Exception {
        DelegateQueryParameters query = new DelegateQueryParameters()
                .setLimit(10);
        JSONObject json = TestHelper.delegateService().queryDelegates(query);
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @org.testng.annotations.Test
    public void testGetDelegateFee() throws Exception {
        JSONObject json = TestHelper.delegateService().getDelegateFee(TestHelper.publicKey());
        Assert.assertTrue(TestHelper.isSuccess(json));
    }

    @org.testng.annotations.Test
    public void testGetForging() throws Exception {
    }

    @org.testng.annotations.Test
    public void testRegisterDelegate() throws Exception {
        JSONObject json = TestHelper.delegateService().registerDelegate(TestHelper.userName, TestHelper.secret, TestHelper.secondSecret);
        Assert.assertTrue( TestHelper.isSuccess(json) ||
                "Account is already a delegate".equals(json.getString("error")));
    }

    @org.testng.annotations.Test
    public void testEnableForge() throws Exception {
    }

    @org.testng.annotations.Test
    public void testDisableForge() throws Exception {
    }

    @org.testng.annotations.Test
    public void testGetForgingStatu() throws Exception {
    }
}