package com.alten.testsigma.addons.android.test;

import com.alten.testsigma.addons.android.CameraManagerNexipay;

import com.testsigma.sdk.TestData;
import com.testsigma.sdk.runners.ActionRunner;
import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestAndroidAction {
    private ActionRunner runner;
    private AndroidDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        //Make sure to start Appium server
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("app", "C:\\NexiPay\\NEXIPay_7.9.1-mock-debugDexguard.apk");
        caps.setCapability("deviceName", "huawei-ana_nx9-VWS0220428003258");
        caps.setCapability("udid", "VWS0220428003258");
        caps.setCapability("platformName", "android");
        caps.setCapability("noReset","true");
        caps.setCapability("fullReset","false");
        driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.launchApp();
        runner = new ActionRunner(driver); //Initialie Action runner


    }


    @Test
    public void prova() throws Exception {

        CameraManagerNexipay action = new CameraManagerNexipay();

        /*action.setPan(new TestData("5255000048551676"));
        action.setPhoneNumber(new TestData("3482140383"));
        action.setPanAliasList(new TestData("0100000900257320"));*/



        //Element element = new Element("", By.className("android.widget.RadioButton"));
        //action.setElement(element);

        runner.run(action);



    }

    @AfterClass
    public void teardown() {
        if (runner != null) {
            runner.quit();
        }
    }
}
