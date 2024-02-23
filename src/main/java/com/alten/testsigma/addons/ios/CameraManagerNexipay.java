package com.alten.testsigma.addons.ios;

import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.IOSAction;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.RunTimeData;
import io.appium.java_client.ios.IOSDriver;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

@Data
@Action(actionText = "iOS Nexipay camera manager", applicationType = ApplicationType.IOS)
public class CameraManagerNexipay extends IOSAction {

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        logger.info("Initiating execution");
        com.testsigma.sdk.Result result = null;
        IOSDriver iosDriver = (IOSDriver) this.driver;
        try{
            iosDriver.findElement(By.id("PhotoCapture")).click();
            setSuccessMessage("Take a picture button successfully pressed on iPhone");
            System.out.println("Take a picture button successfully pressed on iPhone");
            result = Result.SUCCESS;
        }catch (Exception e){
            setErrorMessage("ERROR: action didn't execute");
            result = Result.FAILED;
        }

        return result;
    }



}
