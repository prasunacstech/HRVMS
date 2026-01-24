package hooks;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import testBase.DriverFactory;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;

import utils.ConfigReader;
import utils.ExtentManager;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        // 1) Browser from system property (default chrome)
        String browser = System.getProperty("browser", "chrome");

        // 2) Initialize driver (ThreadLocal)
        DriverFactory.initBrowser(browser);

        // 3) Validate URL from config and navigate
        String appUrl = ConfigReader.get("appURL");
        if (appUrl == null || appUrl.trim().isEmpty()) {
            // fail fast with clear message
            DriverFactory.quitDriver();
            throw new RuntimeException("appURL is not defined in config.properties (key: appURL)");
        }
        WebDriver driver = DriverFactory.getDriver();
        if (driver == null) {
            throw new RuntimeException("WebDriver was not initialized by DriverFactory.initBrowser()");
        }
        driver.get(appUrl);
        new WebDriverWait(driver, Duration.ofSeconds(30))
        .until(ExpectedConditions
            .visibilityOfElementLocated(By.tagName("body")));

        // 4) Create thread-safe Extent test and store in ThreadLocal manager
        ExtentTest test = ExtentManager.getExtent().createTest(scenario.getName());
        ExtentTestManager.setTest(test);
        test.log(Status.INFO, "Scenario started: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        // Get driver and test for this thread
        WebDriver driver = DriverFactory.getDriver();
        ExtentTest test = ExtentTestManager.getTest();

        try {
            if (test == null) {
                // fallback: create a minimal test so we can still log
                test = ExtentManager.getExtent().createTest(scenario.getName());
                ExtentTestManager.setTest(test);
            }

            if (scenario.isFailed()) {
                // Take screenshot only if driver is available
                String screenshotPath = null;
                if (driver != null) {
                    try {
                        screenshotPath = ScreenshotUtil.takeScreenshot(driver, scenario.getName());
                    } catch (Exception e) {
                        // screenshot failed — log but continue
                        test.warning("Screenshot capture failed: " + e.getMessage());
                    }
                } else {
                    test.warning("Driver was null when attempting to take screenshot.");
                }

                // Attach screenshot to Extent if available
                if (screenshotPath != null) {
                    try {
                        test.fail("Scenario Failed",
                                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    } catch (Exception e) {
                        test.fail("Scenario Failed (screenshot attach failed): " + e.getMessage());
                    }
                } else {
                    test.fail("Scenario Failed (no screenshot available)");
                }

                // Attach screenshot to Cucumber report (bytes) if possible
                try {
                    if (driver != null) {
                        byte[] bytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                        scenario.attach(bytes, "image/png", "Failure Screenshot");
                    }
                } catch (Exception e) {
                    test.warning("Failed to attach screenshot to Cucumber report: " + e.getMessage());
                }

                // Log the exception message to Extent
                if (scenario.getStatus() != null) {
                    test.log(Status.FAIL, "Scenario failed: " + scenario.getStatus().name());
                }
            } else {
                test.log(Status.PASS, "Scenario Passed");
            }
        } finally {
            // always quit driver and unload thread-local test
            try {
                DriverFactory.quitDriver();
            } catch (Exception e) {
                // log to extent if available
                if (ExtentTestManager.getTest() != null) {
                    ExtentTestManager.getTest().warning("Error while quitting driver: " + e.getMessage());
                }
            }
            ExtentTestManager.unload();
        }
    }

    // Use Cucumber's AfterAll to flush report once per JVM
    @AfterAll
    public static void afterAll() {
        try {
            ExtentManager.getExtent().flush();
        } catch (Exception e) {
            System.err.println("Could not flush Extent report: " + e.getMessage());
        }
    }
}
