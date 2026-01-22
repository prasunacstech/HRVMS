package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver driver) {

		super(driver);
	}
	
	@FindBy(xpath = "//input[@id='LoginUser']")
	WebElement txtLoginUserName;

	@FindBy(xpath = "//input[@id='password-field']")
	WebElement txtLoginPassword;

	@FindBy(id = "Login")
	WebElement btnlogin;

	@FindBy(xpath = "//input[@name='otpCode']")
	WebElement textOpt;
	
	@FindBy(xpath = "//input[@value='Verify']")
	WebElement btnVerify;
	
	@FindBy(xpath = "//h1[text()='Dashboard']")
	WebElement dashboard;

	
	public void enterUsername(String loginUserVal) {
		txtLoginUserName.clear();
		txtLoginUserName.sendKeys(loginUserVal);
	}
	
	public void enterPassword(String loginPasswordVal) {
		txtLoginPassword.clear();
		txtLoginPassword.sendKeys(loginPasswordVal);
	}
	
	public void clickLoginBtn() {
		btnlogin.click();
	}
	
	public void enterOtp(String otpVal) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(textOpt)); // wait for element to be visible
	    textOpt.clear();
	    textOpt.sendKeys(otpVal);
	}
	
	public void clickVerifyBtn() {
		btnVerify.click();
	}

	public boolean verifyPage() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.visibilityOf(dashboard));
	        return dashboard.isDisplayed();
		}catch(Exception e){
			return false;
		}
		
	}
	
	public boolean isOtpFieldDisplayed() {
	    try {
	        return textOpt.isDisplayed();
	    } catch (NoSuchElementException e) {
	        return false;
	    }

}
}
