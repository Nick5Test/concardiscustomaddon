package com.alten.testsigma.addons.generators;

import com.testsigma.sdk.AndroidAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.Element;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;

@Action(actionText = "Compare Stringa if present in the screenshot of the element element-locator", applicationType = ApplicationType.ANDROID)
public class CheckStringScreenshot extends AndroidAction {

    @TestData(reference = "Stringa")
    private com.testsigma.sdk.TestData Stringa;

    @Element(reference = "element-locator")
    private com.testsigma.sdk.Element elementLocator;

    @Override
    protected com.testsigma.sdk.Result execute() throws NoSuchElementException {
        AndroidDriver androidDriver = (AndroidDriver)this.driver;

        String str = "";
        String pathToDownload  = "";
        String os = System.getProperty("os.name").toLowerCase();
        if(os.contains("win")){
            pathToDownload = System.getenv("USERPROFILE") +"\\Downloads";
        }else if (os.contains("mac")){
            pathToDownload = System.getProperty("user.home")+"/Downloads";
        }else{
            //In caso di altri sistemi operativi
            pathToDownload = System.getProperty("user.home")+"Downloads";
        }


        logger.info("Initiating execution");
        ITesseract tesseract = new Tesseract();
        tesseract.setDatapath("target/classes/tessdata/");
        tesseract.setLanguage("ita");
        try {

            AndroidElement element = (AndroidElement) driver.findElement(By.xpath(elementLocator.getValue()));
            File screenshotFile = element.getScreenshotAs(OutputType.FILE);
            //salvataggio del file nel seguente percorso
            String screenshotPath = pathToDownload+"\\screenshot.png";

            FileUtils.copyFile(screenshotFile, new File(screenshotPath));
            String recognizedText = tesseract.doOCR(screenshotFile);

            String wordToFind = Stringa.getValue().toString();
            boolean isWordPresent = recognizedText.contains(wordToFind);

            if (isWordPresent) {
                System.out.println("word '" + wordToFind + "' is present.");
            } else {
                System.out.println("word '" + wordToFind + "' is not present.");
            }
        } catch (TesseractException | IOException e) {
            System.err.println("Errore durante il riconoscimento del testo: " + e.getMessage());
        }
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;

        return result;
    }

}