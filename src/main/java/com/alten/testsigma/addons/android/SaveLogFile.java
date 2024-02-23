package com.alten.testsigma.addons.android;

import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import io.appium.java_client.android.AndroidDriver;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Action(actionText = "Save file log of the testName Test case", applicationType = ApplicationType.ANDROID)
public class SaveLogFile extends AndroidAction {

    @TestData(reference = "testName")
    private com.testsigma.sdk.TestData testName;


    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {

        logger.info("Initiating execution");
        AndroidDriver androidDriver = (AndroidDriver)this.driver;

        //Elementi utilizzati
        String downloadFile = "//android.widget.Button[@content-desc=\"DOWNLOAD LOG FILE\"]";
        String log_txt = "android.widget.EditText";
        String bottone_salva = "//*[@text='SALVA' or @text='SAVE']";


        LocalDate today = LocalDate.now();
        DateTimeFormatter forma = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = today.format(forma);


        Instant currentInstant = Instant.now();
        // Salva il timestamp in una variabile
        long myTimestamp = currentInstant.toEpochMilli();



        String fileName = "Log_"+testName.getValue().toString()+"_"+currentDate+"_"+myTimestamp;

        driver.findElement(By.xpath(downloadFile)).click();

        WebDriverWait wait = new WebDriverWait(androidDriver, 10);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(log_txt))));
        driver.findElement(By.className(log_txt)).clear();
        driver.findElement(By.className(log_txt)).sendKeys(fileName);
        driver.findElement(By.xpath(bottone_salva)).click();



        setSuccessMessage("Test executed correctly !");
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        return result;
    }
}