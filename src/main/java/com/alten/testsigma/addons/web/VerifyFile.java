package com.alten.testsigma.addons.web;

import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.Result;
import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.Element;
import com.testsigma.sdk.annotation.RunTimeData;
import com.testsigma.sdk.annotation.TestData;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Action(actionText = "Verify if a file with the extension exists in the folder & delete",
        description = "Verify if a file exists in the Downloads folder. At the end delete the file.",
        applicationType = ApplicationType.WEB)
public class VerifyFile extends WebAction {

  @TestData(reference = "extension")
  private com.testsigma.sdk.TestData testData;



  @Override
  public com.testsigma.sdk.Result execute() throws NoSuchElementException {
    //Your Awesome code starts here
    logger.info("Initiating execution");
    String estensione = testData.getValue().toString();



    String name = "Export_";
    String dir = "";


    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String formatterDate = currentDate.format(formatter);
    String fileName = name + formatterDate;
    //System.out.println(fileName);
    String str = "";
    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    if(isWindows){
      str = "windows";
    }else{
      str = "OSX";
    }
    String userName = "";
    switch (str) {
      case "windows":
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("cmd.exe", "/c", "whoami");
        Process process = null;
        try {
          process = pb.start();
        } catch (IOException e) {
          e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        try {
          line = reader.readLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
        String[] var = line.split("\\\\");
        userName = var[1];
        dir = "C:\\Users\\user\\Downloads";
        break;
      case "OSW":
        ProcessBuilder pbOS = new ProcessBuilder();
        pbOS.command("bash", "-c", "whoami");
        try {
          Process processOS = pbOS.start();
          BufferedReader readerOS = new BufferedReader(new InputStreamReader(processOS.getInputStream()));
          String lineOS;
          lineOS = readerOS.readLine();
          userName = lineOS;


        } catch (IOException e) {
          throw new RuntimeException(e);
        }

    }
    String pathProvvisorio = dir.replace("user", userName);
    String path = pathProvvisorio +"\\" + fileName + estensione;
    System.out.println(path);
    File file = new File(path);
    if (file.exists()) {
      boolean deleted = file.delete();
      System.out.println("Deleted");
      Result result = Result.SUCCESS;
      return result;
    } else {
      System.out.println("Ops, error!");
      Result result = Result.FAILED;
      return result;

    }

  }
}