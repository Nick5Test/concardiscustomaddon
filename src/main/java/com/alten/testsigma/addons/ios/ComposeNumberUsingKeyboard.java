package com.alten.testsigma.addons.ios;

import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.IOSAction;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import io.appium.java_client.ios.IOSDriver;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

@Data
@Action(actionText = "Enter Pan test-data using phone keyboard OLD", applicationType = ApplicationType.IOS)
public class ComposeNumberUsingKeyboard extends IOSAction {
    @TestData(reference = "test-data")
    private com.testsigma.sdk.TestData pan;

    @Override
    protected Result execute() throws NoSuchElementException {
        //Your Awesome code starts here
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        IOSDriver iosDriver = (IOSDriver) this.driver;
        logger.info("Initiating execution");
        String [] ArrayPan  = pan.getValue().toString().split("");
        for(int i = 0; i< ArrayPan.length; i++) {
            String a = ArrayPan[i];
            String xpathToPress = "//XCUIElementTypeStaticText[@name = '" + a +"']";
            logger.info("Clicked: " + xpathToPress);
            iosDriver.findElement(By.xpath(xpathToPress)).click();
        }
        return result;
    }
}
