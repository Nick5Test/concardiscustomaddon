package com.alten.testsigma.addons.generators.test;


import com.alten.testsigma.addons.generators.NexiPay_newPwd;
import com.testsigma.sdk.TestData;
import com.testsigma.sdk.TestDataParameter;
import com.testsigma.sdk.runners.TestDataFunctionRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class ChangePwd {
    private TestDataFunctionRunner testDataFunctionRunner;

    @BeforeClass
    public void setup() throws Exception {
        testDataFunctionRunner = new TestDataFunctionRunner();
    }

    @Test
    public void Test() throws Exception {

        NexiPay_newPwd testDataFunction = new NexiPay_newPwd();

        testDataFunction.setUser(new TestDataParameter("autom_52_00_00@yopmail.com"));
        testDataFunction.setNewPassword(new TestDataParameter("Cartasi01"));

        TestData testData = testDataFunctionRunner.run(testDataFunction);
    }

    @AfterClass
    public void teardown() {
    }
}
