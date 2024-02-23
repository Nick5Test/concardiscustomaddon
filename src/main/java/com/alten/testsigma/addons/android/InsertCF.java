package com.alten.testsigma.addons.android;

import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

@Data
@Action(actionText = "Insert Fiscal code CODE registration page", applicationType = ApplicationType.ANDROID)
public class InsertCF extends AndroidAction {

    @TestData(reference = "CODE")
    private com.testsigma.sdk.TestData CODE;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        //Your Awesome code starts here

        logger.info("Initiating execution");
        AndroidDriver androidDriver = (AndroidDriver)this.driver;
        String [] cf = CODE.getValue().toString().split("");
        for(int i=0;i< cf.length;i++){
            int a = i+1;
            MobileElement element = (MobileElement) driver.findElement(By.xpath("(//android.widget.EditText)["+a+"]"));
            element.sendKeys(cf[i]);
        }

        setSuccessMessage("Fiscal code correctly insert."+CODE.getValue().toString());
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        return result;
    }
}