package com.alten.testsigma.addons.android;

import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.time.Duration;

@Data
@Action(actionText = "Tap on relative screen using the coordinates X and Y", applicationType = ApplicationType.ANDROID)
public class TapOnRelativeScreen extends AndroidAction {

    @TestData(reference = "X")
    double Xpoint;
    @TestData(reference = "Y")
    double YPoint;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        //Your Awesome code starts here
        logger.info("Initiating execution");

        AndroidDriver androidDriver = (AndroidDriver)this.driver;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int relativeX = (int) ((screenSize.width) * Xpoint);
        int relativeY = (int) ((screenSize.height) * YPoint);



        TouchAction touchAction = new TouchAction(androidDriver);
        touchAction.tap(PointOption.point(relativeX,relativeY)).perform();

        logger.info("Tapped on:"+relativeX +" "+relativeY);
        logger.info("Screen resolution: "+screenSize.width+" x "+screenSize.height);

        System.out.println("Tapped on:"+relativeX +" "+relativeY);
        System.out.println("Screen resolution: "+screenSize.width+" x "+screenSize.height);


        return null;
    }
}