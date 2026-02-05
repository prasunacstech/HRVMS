package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutPage extends BasePage {

	public LogoutPage(WebDriver driver) {

		super(driver);
	}

	@FindBy(xpath = "//a[.//b[text()='vineeth']]")
	WebElement dropDownImg;

	@FindBy(xpath = "//a[@data-target='#logoutModal']")
	WebElement dropDownLogout;

	@FindBy(id = "logout")
	WebElement btnlogout;

	@FindBy(xpath = "//input[@name='LoginUser']")
	WebElement assertLogin;

	public void clickDropDownImg() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(dropDownImg)).click();

	}

	public void clickDropDownLogout() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(dropDownLogout)).click();
	}

	public void clicklogout() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(btnlogout)).click();

	}

	public boolean isLoginPageDisplayed() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOf(assertLogin));
			return assertLogin.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

}
