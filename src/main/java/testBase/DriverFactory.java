package testBase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

////	public static WebDriver driver;
////	public static Properties prop;
////
//	public static WebDriver getDriver() {
//		return driver;
//	}
//	
//
//    private static WebDriver driver;
//
//    public static void initBrowser(String browser) {
//
//        switch (browser.toLowerCase()) {
//            case "chrome":
//                driver = new ChromeDriver();
//                break;
//            case "firefox":
//                driver = new FirefoxDriver();
//                break;
//            case "edge":
//                driver = new EdgeDriver();
//                break;
//            default:
//                throw new RuntimeException("Invalid browser: " + browser);
//        }
//
//        driver.manage().deleteAllCookies();
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.manage().window().maximize();
//        driver.get(ConfigReader.get("appURL"));
//    }
//
//
////	public static void initBrowser() {
////
////		// CrossBrowser Testing
////		String browserType = ConfigReader.get("browser");
////		switch (browserType) {
////		case "chrome":
////			driver = new ChromeDriver();
////			break;
////		case "firefox":
////			driver = new FirefoxDriver();
////			break;
////		case "edge":
////			driver = new EdgeDriver();
////			break;
////		default:
////			System.out.println("Ivalid browser");
////			return;
////		}
////
////		driver.manage().deleteAllCookies();
////		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
////		driver.manage().window().maximize();
////		driver.get(ConfigReader.get("appURL"));
////	}
//	
//public static void quitDriver() {
//    if (driver != null) {
//        driver.quit();
//        driver = null;
//    }
//}
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver  initBrowser(String browser) {

        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
        WebDriver webDriver;

        switch (browser.toLowerCase()) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--start-maximized");
                webDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                webDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Invalid browser: " + browser);
        }
        driver.set(webDriver);
        return webDriver;
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
	


