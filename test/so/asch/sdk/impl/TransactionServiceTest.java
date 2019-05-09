package so.asch.sdk.impl;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import so.asch.sdk.AschResult;
import so.asch.sdk.AschSDK;
import so.asch.sdk.TestData;
import so.asch.sdk.TransactionType;
import so.asch.sdk.dto.query.QueryParameters;
import so.asch.sdk.dto.query.TransactionQueryParameters;

/**
 * @author fisher
 * @version $Id: TransactionServiceTest.java, v 0.1 2017/8/7 15:16 fisher Exp $
 */
public class TransactionServiceTest {
    @BeforeSuite
    public void SetUp(){
        AschSDK.Config.setAschServer(TestData.root);
    }

    @Test
    public void testQueryTransactionsEmpty() throws Exception {
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

//    @Test
//    public void testQueryTransactionsByBlockId() throws Exception {
//        TransactionQueryParameters parameters = new TransactionQueryParameters();
//        parameters.setBlockId("0dc51ce12b5a58f206f15cb546a29375c30e7cfce31f95fa9244133fc894ea33");
//        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
//        Assert.assertTrue(result.isSuccessful());
//    }

    @Test
    public void testQueryTransactionsByLimit()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setLimit(10);
        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testQueryTransactionsByType()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setTransactionType(TransactionType.basic_transfer);
        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }
    @Test
    // TODO: 2017/8/8 orderBy condition error
    public void testQueryTransactionsByOffset()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setOffset(0);
        parameters.setOrderBy("amount", QueryParameters.SortOrder.DESC);
        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

//    @Test
//    public void testQueryTransactionsBySendPublicKey()throws Exception{
//        TransactionQueryParameters parameters = new TransactionQueryParameters();
//        parameters.setSenderPublicKey("df4fbcc996be9834a70fc58e30d42b3febc289277a004632f91a54c4e8b39ced");
//        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
//        Assert.assertTrue(result.isSuccessful());
//    }
//
//    @Test
//    public void testQueryTransactionsByOwnPublicKey()throws Exception{
//        TransactionQueryParameters parameters = new TransactionQueryParameters();
//        parameters.setOwnerPublicKey("df4fbcc996be9834a70fc58e30d42b3febc289277a004632f91a54c4e8b39ced");
//        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
//        Assert.assertTrue(result.isSuccessful());
//    }
//
//    // TODO: 2017/8/8 test error
//    @Test
//    public void testQueryTransactionsByOwnerAddress()throws Exception{
//        TransactionQueryParameters parameters = new TransactionQueryParameters();
//        parameters.setOwnerAddress("4516770862894053894");
//        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
//        Assert.assertTrue(result.isSuccessful());
//    }

    @Test
    public void testQueryTransactionsBySenderId()throws Exception{
        TransactionQueryParameters parameters = new TransactionQueryParameters();
        parameters.setSenderId("16502644983291819723");
        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
        Assert.assertTrue(result.isSuccessful());
    }

//    @Test
//    public void testQueryTransactionsByRecipientId()throws Exception{
//        TransactionQueryParameters parameters = new TransactionQueryParameters();
//        parameters.setRecipientId("14762548536863074694");
//        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
//        Assert.assertTrue(result.isSuccessful());
//    }

//    // TODO: 2017/8/9 ambiguous condition (amount,fee)
//    @Test
//    public void testQueryTransactionsByAmount()throws Exception{
//        TransactionQueryParameters parameters = new TransactionQueryParameters();
//        parameters.setAmount(100L);
//        AschResult result = AschSDK.Transaction.queryTransactions(parameters);
//        Assert.assertTrue(result.isSuccessful());
//    }

    @Test
    public void testGetTransactions()throws Exception{
        AschResult result = AschSDK.Transaction.getTransaction(TestData.transactionId);
        Assert.assertTrue(result.isSuccessful());
    }

    // TODO: 2017/8/9 The time is too short for testing unconfirm transaction.
    @Test
    public void testGetUnconfirmedTransaction()throws Exception{
        AschResult result =AschSDK.Transaction.getUnconfirmedTransaction(TestData.transactionId);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetUnconfirmedTransactions()throws Exception{
        AschResult result =AschSDK.Transaction.getUnconfirmedTransactions(null, null);
        Assert.assertTrue(result.isSuccessful());
    }

    @Test
    public void testGetUnconfirmedTransactionsByConditions()throws Exception{
        AschResult result = AschSDK.Transaction.getUnconfirmedTransactions("df4fbcc996be9834a70fc58e30d42b3febc289277a004632f91a54c4e8b39ced","ACsJqgmPRjoHm6fb3EswuWoaxu4KRAjv4R");
        Assert.assertTrue(result.isSuccessful());
    }

}
