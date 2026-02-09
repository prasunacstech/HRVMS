package testBase;

import java.net.URL;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static WebDriver initBrowser(String browser) {

		boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

		boolean isSauce = Boolean.parseBoolean(System.getProperty("sauce", "false"));

		WebDriver webDriver;

		try {
			if (isSauce) {
				webDriver = initSauceDriver(browser);
			} else {
				webDriver = initLocalDriver(browser, headless);
			}
		} catch (Exception e) {
			throw new RuntimeException("Driver initialization failed", e);
		}

		driver.set(webDriver);
		return webDriver;
	}

	private static WebDriver initLocalDriver(String browser, boolean headless) {

		switch (browser.toLowerCase()) {

		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
			if (headless) 
	            chromeOptions.addArguments(
	                    "--headless=new",
	                    "--no-sandbox",
	                    "--disable-dev-shm-usage",
	                    "--window-size=1920,1080"
	            );
	            return new ChromeDriver(chromeOptions);
	            
			case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless)
                    firefoxOptions.addArguments("-headless");
                return new FirefoxDriver(firefoxOptions);

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless)
                    edgeOptions.addArguments("--headless=new");
                return new EdgeDriver(edgeOptions);

            default:
                throw new RuntimeException("Invalid browser: " + browser);
        }
    

	}

	 private static WebDriver initSauceDriver(String browser) throws Exception {

	        String username = System.getenv("SAUCE_USERNAME");
	        String accessKey = System.getenv("SAUCE_ACCESS_KEY");

	        if (username == null || accessKey == null) {
	            throw new RuntimeException("Sauce Labs credentials not found");
	        }

	        MutableCapabilities sauceOptions = new MutableCapabilities();
	        sauceOptions.setCapability("name", "HRVMS Automation Test");
	        sauceOptions.setCapability("build", "HRVMS-Build-1");

	        MutableCapabilities browserOptions;

	        switch (browser.toLowerCase()) {
	            case "chrome":
	                browserOptions = new ChromeOptions();
	                break;
	            case "firefox":
	                browserOptions = new FirefoxOptions();
	                break;
	            case "edge":
	                browserOptions = new EdgeOptions();
	                break;
	            default:
	                throw new RuntimeException("Invalid browser: " + browser);
	        }

	        browserOptions.setCapability("platformName", "Windows 11");
	        browserOptions.setCapability("browserVersion", "latest");
	        browserOptions.setCapability("sauce:options", sauceOptions);

	        URL sauceURL = new URL(
	                "https://" + username + ":" + accessKey +
	                        "@ondemand.saucelabs.com/wd/hub"
	        );

	        return new RemoteWebDriver(sauceURL, browserOptions);
	    }

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}
}
