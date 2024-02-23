package com.alten.testsigma.addons.generators.test;


import com.alten.testsigma.addons.generators.*;
import com.testsigma.sdk.TestDataParameter;
import com.testsigma.sdk.runners.TestDataFunctionRunner;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.testsigma.sdk.TestData;


public class TestCustomTestDataFunction {
    private TestDataFunctionRunner testDataFunctionRunner;

    @BeforeClass
    public void setup() throws Exception {
        testDataFunctionRunner = new TestDataFunctionRunner();
    }

    @Test
    public void Test() throws Exception {

        //NexiPay_newPwd testDataFunction = new NexiPay_newPwd();
        CleanUserDevice testDataFunction = new CleanUserDevice();
        //UnsubscribeSMS_ACS testDataFunction = new UnsubscribeSMS_ACS();

        testDataFunction.setUser(new TestDataParameter("utenza44@yopmail.com"));
        //testDataFunction.setNewPassword(new TestDataParameter("Cartasi99"));


        //testDataFunction.setPan(new TestDataParameter("4539970010904858"));
        //testDataFunction.setPhoneNumber(new TestDataParameter(""));
        //testDataFunction.setPanAliasList(new TestDataParameter(""));



        TestData testData = testDataFunctionRunner.run(testDataFunction);
    }

    @AfterClass
    public void teardown() {
    }
}
