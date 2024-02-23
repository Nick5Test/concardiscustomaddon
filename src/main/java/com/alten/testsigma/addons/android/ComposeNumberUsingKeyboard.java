package com.alten.testsigma.addons.android;

import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.RunTimeData;
import com.testsigma.sdk.annotation.TestData;
import io.appium.java_client.android.AndroidDriver;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

@Data
@Action(actionText = "Insert keySix NexiPay keySix", applicationType = ApplicationType.ANDROID)
public class ComposeNumberUsingKeyboard extends AndroidAction {

    @TestData(reference = "keySix")
    //String uri = "https://stgapi.nexi.it/mfa/getlastotp?user=carta.excellence@yopmail.com";
    private com.testsigma.sdk.TestData testData;

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {

        logger.info("Initiating execution");

        logger.debug("Test-data: "+ this.testData.getValue());
        AndroidDriver androidDriver = (AndroidDriver)this.driver;

        String [] keysixArray = (testData.getValue().toString()).split("");

        for (int i = 0; i < keysixArray.length; i++) {
            String keySix = keysixArray[i];
            String xpathToPress = "//*[@text='" + keySix + "']";
            driver.findElement(By.xpath(xpathToPress)).click();
        }

        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        setSuccessMessage("keySix inserito correttamente");
        return result;

    }
}