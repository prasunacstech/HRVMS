package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import testBase.DriverFactory;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener{
	
//	@Override
//	public void onTestFailure(ITestResult result) {
//	    ExtentTest test = ExtentTestManager.getTest();
//	    if (test == null) {
//	        System.out.println("ExtentTest is null! Skipping reporting for retry.");
//	        return;
//	    }
//
//	    Object retryCount = result.getAttribute("retryCount");
//	    int attempt = retryCount == null ? 0 : (int) retryCount;
//
//	    // Get the thread-safe driver
//	    WebDriver driver = DriverFactory.getDriver();
//
//	    // Take screenshot
//	    String screenshotPath = ScreenshotUtil.takeScreenshot(
//	            driver, result.getMethod().getMethodName() + "_Retry_" + attempt
//	    );
//
//	    // Log failure with screenshot
//	    try {
//	        if (screenshotPath != null) {
//	            test.fail("❌ Test failed on attempt: " + attempt,
//	                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build()
//	            );
//	        } else {
//	            test.fail("❌ Test failed on attempt: " + attempt + " (Screenshot missing)");
//	        }
//	        test.fail(result.getThrowable());
//	    } catch (Exception e) {
//	        test.fail("❌ Test failed on attempt: " + attempt + " (Exception attaching screenshot)");
//	        test.fail(result.getThrowable());
//	    }
//
//	    // Retry warning
//	    if (retryCount != null) {
//	        test.warning("🔁 Retrying test: Attempt " + attempt + " of 2");
//	    }
//	}
//
//	    @Override
//	    public void onTestSuccess(ITestResult result) {
//
//	        Object retryCount = result.getAttribute("retryCount");
//
//	        if (retryCount != null) {
//	            ExtentTestManager.getTest()
//	                    .pass("✅ Test passed after retry "
//	                            + retryCount);
//	        }
//	    }

}
