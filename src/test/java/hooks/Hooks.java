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

		// Read environment (default = qa)
		String env = System.getProperty("env", "qa");

		// Read browser (default = chrome)
		String browser = System.getProperty("browser", "chrome");

		// Initialize WebDriver (ThreadLocal)
		DriverFactory.initBrowser(browser);

		WebDriver driver = DriverFactory.getDriver();
		if (driver == null) {
			throw new RuntimeException("WebDriver initialization failed");
		}

		// Read app URL based on environment
		String appUrl = ConfigReader.get("appURL." + env);
		if (appUrl == null || appUrl.trim().isEmpty()) {
			DriverFactory.quitDriver();
			throw new RuntimeException("appURL is not defined in config.properties for env: " + env);
		}

		// Navigate to application
		driver.get(appUrl);

		// Basic wait to ensure page load
		new WebDriverWait(driver, Duration.ofSeconds(30))
				.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

		// 7️⃣ Create Extent test (Thread-safe)
		ExtentTest test = ExtentManager.getExtent().createTest(scenario.getName());
		ExtentTestManager.setTest(test);

		test.log(Status.INFO, "Scenario started: " + scenario.getName() + " | Env: " + env + " | Browser: " + browser);
	}

	

   @After
public void tearDown(Scenario scenario) {

    WebDriver driver = DriverFactory.getDriver();
    ExtentTest test = ExtentTestManager.getTest();

    try {
        if (test == null) {
            // This should never happen if @Before is correct
            System.err.println("ExtentTest was null for scenario: " + scenario.getName());
            return;
        }

        if (scenario.isFailed()) {

            test.fail("Scenario Failed: " + scenario.getName());

            if (driver != null) {
                try {
                    String screenshotPath =
                            ScreenshotUtil.takeScreenshot(driver, scenario.getName());

                    test.addScreenCaptureFromPath(screenshotPath);

                    byte[] bytes =
                            ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(bytes, "image/png", "Failure Screenshot");

                } catch (Exception e) {
                    test.warning("Screenshot capture failed: " + e.getMessage());
                }
            }
        } else {
            test.pass("Scenario Passed");
        }

    } finally {
        DriverFactory.quitDriver();
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

