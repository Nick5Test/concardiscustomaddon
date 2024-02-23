package com.alten.testsigma.addons.ios;

import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.IOSAction;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.Element;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.TestDataFunctionParameter;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.awt.*;

public class InsertPan {

    @Data
    @Action(actionText = "Enter Pan test-data in the field element-locator if visible", applicationType = ApplicationType.IOS)
    public class TapOnRelativeScreen extends IOSAction {

        @TestData(reference = "test-data")
        private com.testsigma.sdk.TestData pan;

        @Element(reference = "element-locator")
        private com.testsigma.sdk.Element element;


        @Override
        protected com.testsigma.sdk.Result execute() throws NoSuchElementException {
            //Your Awesome code starts here
            com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
            IOSDriver iosDriver = (IOSDriver)this.driver;
            WebElement webElement = element.getElement();
            if(webElement.isDisplayed()) {
                webElement.sendKeys(pan.getValue().toString());
                setSuccessMessage(String.format("Successfully entered %s on element with %s::%s", pan.getValue().toString(), element.getBy(), element.getValue()));
            }else {
                result = com.testsigma.sdk.Result.FAILED;
                logger.warn("Element with locator %s is not visible ::"+element.getBy()+"::"+element.getValue());
                setErrorMessage(String.format("Element with locator %s is not visible",element.getBy()));
            }
            return result;
        }
    }
}
