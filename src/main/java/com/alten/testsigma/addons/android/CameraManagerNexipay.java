package com.alten.testsigma.addons.android;

import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.RunTimeData;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;


@Data
@Action(actionText = "Android Nexipay camera manager", applicationType = ApplicationType.ANDROID)
public class CameraManagerNexipay extends AndroidAction {

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        logger.info("Initiating execution");
        com.testsigma.sdk.Result result = null;
        AndroidDriver androidDriver = (AndroidDriver)this.driver;
        String marca = androidDriver.getCapabilities().getCapability("deviceManufacturer").toString();
        //usare active invece del lauch app

        if(marca.toLowerCase().equals("huawei")){
            androidDriver.findElement(By.xpath("//*[@text=\"Camera\"]")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            androidDriver.pressKey(new KeyEvent(AndroidKey.CAMERA));
            setSuccessMessage("Take a picture button successfully pressed on Huawei");
            System.out.println("Take a picture button successfully pressed on Huawei");
            result = Result.SUCCESS;

        }else if (marca.toLowerCase().equals("samsung")){
            androidDriver.findElement(By.xpath("//*[@text=\"Camera\"]")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            androidDriver.pressKey(new KeyEvent(AndroidKey.CAMERA));
            setSuccessMessage("Take a picture button successfully pressed on Samsung");
            System.out.println("Take a picture button successfully pressed on Samsung");
            result = Result.SUCCESS;
        }else{
            setErrorMessage("ERROR: the device is not Huawei or Samsung");
            result = Result.FAILED;
        }
        return result;
    }
}