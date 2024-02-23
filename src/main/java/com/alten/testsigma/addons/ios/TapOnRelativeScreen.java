package com.alten.testsigma.addons.ios;


import com.testsigma.sdk.IOSAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.Element;
import com.testsigma.sdk.annotation.TestDataFunctionParameter;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.offset.PointOption;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.awt.*;


@Data
@Action(actionText = "Tap on the screen insert X and Y", applicationType = ApplicationType.IOS)
public class TapOnRelativeScreen extends IOSAction {

    @TestDataFunctionParameter (reference = "X")
    private com.testsigma.sdk.TestDataParameter X;
    @TestDataFunctionParameter(reference = "Y")
    private com.testsigma.sdk.TestDataParameter Y;

    @Override
    protected com.testsigma.sdk.Result execute() throws NoSuchElementException {
        //Your Awesome code starts here
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        logger.info("Initiating execution");

        IOSDriver iosDriver = (IOSDriver)this.driver;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double relativeX = (screenSize.width) * (Integer.parseInt(X.getValue().toString()));
        double relativeY = (screenSize.height) * (Integer.parseInt(Y.getValue().toString()));

        int xPoint = (Integer.parseInt(X.getValue().toString()));
        int yPoint = (Integer.parseInt(Y.getValue().toString()));

        TouchAction touchAction = new TouchAction(iosDriver);
        touchAction.tap(PointOption.point(xPoint,yPoint)).perform();

        logger.info("Tapped on:"+xPoint +" "+yPoint);
        logger.info("Screen resolution: "+screenSize.width+screenSize.height);


        return result;
    }
}