package hooks;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import testBase.DriverFactory;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

public class Hooks {
	
//	// private BaseClass base;
//	 private WebDriver driver;
//	 
//	 @Before
////	    public void setUp() {
////	        String browser = System.getProperty("browser", "chrome");
////	        BaseClass.initBrowser(browser);
////	        driver = BaseClass.getDriver();
////	    }
//
//	 
//
//	    @After
//	    public void tearDown(Scenario scenario) {
//
//	        if (scenario.isFailed()) {
//	            byte[] screenshot =
//	                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
//	            scenario.attach(screenshot, "image/png", scenario.getName());
//	        }
//
//	        BaseClass.quitDriver();
//	    }
	


    @Before
    public void setUp(Scenario scenario) {

        // 1️⃣ Browser from system property
        String browser = System.getProperty("browser", "chrome");

        // 2️⃣ Initialize driver (ThreadLocal)
        DriverFactory.initBrowser(browser);

        // 3️⃣ Open application
        DriverFactory.getDriver().get(ConfigReader.get("appURL"));

        // 4️⃣ Create Extent test (ThreadLocal)
        ExtentTest test = ExtentManager.getExtent()
                .createTest(scenario.getName());

        ExtentTestManager.setTest(test);

        test.log(Status.INFO,
                "Scenario started: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {

        WebDriver driver = DriverFactory.getDriver();
        ExtentTest test = ExtentTestManager.getTest();

        if (scenario.isFailed()) {

            // 📸 Screenshot
            String screenshotPath =
                    ScreenshotUtil.takeScreenshot(driver, scenario.getName());

            if (screenshotPath != null) {
                test.fail("Scenario Failed",
                        MediaEntityBuilder
                                .createScreenCaptureFromPath(screenshotPath)
                                .build());
            } else {
                test.fail("Scenario Failed (Screenshot not captured)");
            }

            // Attach to Cucumber report
            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot");

        } else {
            test.pass("Scenario Passed");
        }

        DriverFactory.quitDriver();
        ExtentTestManager.unload();
    }
    
    @AfterClass(alwaysRun = true)
    public static void afterAll() {
        ExtentManager.getExtent().flush();
    }

}
