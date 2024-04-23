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
@Action(actionText = "Insert CVV", applicationType = ApplicationType.ANDROID)
public class InsertCVV extends AndroidAction {

    @TestData(reference = "CODE")
    private com.testsigma.sdk.TestData CODE;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        //Your Awesome code starts here

        logger.info("Initiating execution");
        AndroidDriver androidDriver = (AndroidDriver) this.driver;
        String[] cvvSplitted = CODE.getValue().toString().split("");
        String[] character = null;


        for (int i = 1; i < 4; i++) {
            driver.findElement(By.xpath("(//*[@class='android.widget.EditText'])["+i+"]")).sendKeys(cvvSplitted[i-1]);
            logger.info("Cvv correctly insert");
        }
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        return result;
    }

}