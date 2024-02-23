package com.alten.testsigma.addons.web.test;

import com.alten.testsigma.addons.web.VerifyFile;
import com.testsigma.sdk.TestData;
import com.testsigma.sdk.runners.ActionRunner;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TestWebAction {
    private ActionRunner runner;
    private ChromeDriver driver;

    @BeforeClass
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        runner = new ActionRunner(driver); //Initialie Action runner
        driver.get("https://nexi:gftye56g@wwwstg.nexi.it/it/nocaptcha-login-titolari");

    }
    @Test
    public void validateCountriesCount() throws Exception {
        VerifyFile action = new VerifyFile();
        action.setTestData(new TestData(".pdf"));
        runner.run(action);

    }

    @AfterClass
    public void teardown() {
        if (runner != null) {
            runner.quit();
        }
    }
}
