package com.alten.testsigma.addons.generators.test;


import com.alten.testsigma.addons.generators.CleanUserDevice;
import com.alten.testsigma.addons.generators.NexiPay_newPwd;
import com.testsigma.sdk.TestData;
import com.testsigma.sdk.TestDataParameter;
import com.testsigma.sdk.runners.TestDataFunctionRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class ChangePwdMultipleUser {
    private TestDataFunctionRunner testDataFunctionRunner;

    @BeforeClass
    public void setup() throws Exception {
        testDataFunctionRunner = new TestDataFunctionRunner();
    }

    @Test
    public void Test() throws Exception {
        int count = 0;
        NexiPay_newPwd testDataFunction = new NexiPay_newPwd();
        String input = "carta.excellence@yopmail.com," +
                "test_iosi_2@yopmail.com," +
                "automation_bi@yopmail.com," +
                "automation_tri@yopmail.com," +
                "np.centralbilling04@yopmail.com," +
                "central_billing_002.stg@yopmail.com," +
                "test_iosi_1@yopmail.com," +
                "testfactoryfam@yopmail.com," +
                "mista.ca1@yopmail.com," +
                "utenza44@yopmail.com," +
                "nexitestautomation@gmail.com," +
                "ttanexipay@gmail.com," +
                "autom_52_00_00@yopmail.com," + 
                "utenza75@yopmail.com," +
                "tauser1nexipay@gmail.com, " +
                "ptstg.excel.mc02@yopmail.com";

        if(input.contains(",")){
            String [] parts = input.split(",");
            List<String> partList = new ArrayList<>();
            for (String part : parts){
                partList.add(part);
            }
            for (String part : partList){
                testDataFunction.setUser(new TestDataParameter(part));
                testDataFunction.setNewPassword(new TestDataParameter("Cartasi01"));
                TestData testData = testDataFunctionRunner.run(testDataFunction);
                count++;
                System.out.println(count+". "+part+" Password changed in Cartasi01");
            }
        }
    }

    @AfterClass
    public void teardown() {
    }
}
