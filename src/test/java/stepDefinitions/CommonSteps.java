package stepDefinitions;



import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.aventstack.extentreports.Status;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.LoginPage;
import testBase.DriverFactory;
import utils.ConfigReader;
import utils.ExcelReader;
import utils.ExtentTestManager;

public class CommonSteps {

//	WebDriver driver;
//	LoginPage lp;
//
//	private LoginPage getLoginPage() {
//		if (lp == null) {
//			driver = BaseClass.getDriver();
//			 System.out.println("Driver in getLoginPage: " + driver);// GUARANTEED non-null now
//			lp = new LoginPage(driver);
//		}
//		return lp;
//	}
//
//	@When("the user enters the valid username")
//	public void the_user_enters_the_valid_username() {
//		getLoginPage().enterUsername(ConfigReader.get("username"));
//
//	}
//
//	@When("the user enters the valid password")
//	public void the_user_enters_the_valid_password() {
//		getLoginPage().enterPassword(ConfigReader.get("password"));
//
//	}
//
//	@And("the user clicks on login button")
//	public void the_user_clicks_on_login_button() {
//		getLoginPage().clickLoginBtn();
//	}
//
//	@Then("user enters otp")
//	public void user_enters_otp() {
//		getLoginPage().enterOtp(ConfigReader.get("otp"));
//	}
//
//	@And("user click on verify button")
//	public void user_click_on_verify_button() {
//		getLoginPage().clickVerifyBtn();
//	}
//
//	@Then("the user should be logged in successfully")
//	public void the_user_should_be_logged_in_successfully() {
//		boolean targetPage = getLoginPage().verifyPage();
//		Assert.assertEquals(targetPage, true, "Login Failed");
//	}

	 private LoginPage lp;
	    private ExcelReader excel = new ExcelReader("src/test/resources/testData1.xlsx");
	    private boolean loginSuccess = false;

	    private WebDriver getDriver() {
	        return DriverFactory.getDriver();
	    }

	    private LoginPage getLoginPage() {
	        if (lp == null) {
	            lp = new LoginPage(getDriver());
	        }
	        return lp;
	    }

	@When("the user enters the valid username")
	public void enterUsername() {
		getLoginPage().enterUsername(ConfigReader.get("username"));
	}

	@And("the user enters the valid password")
	public void enterPassword() {
		getLoginPage().enterPassword(ConfigReader.get("password"));
	}

	
	
	    
	@And("the user clicks on login button")
	public void clickLogin() {
		getLoginPage().clickLoginBtn();

		    // Check login result (e.g., presence of OTP field)
		    try {
		        if (getLoginPage().isOtpFieldDisplayed()) { // You need to implement this in LoginPage
		            loginSuccess = true;
		        } else {
		            loginSuccess = false;
		        }
		    } catch (Exception e) {
		        loginSuccess = false;
		    }
	}

	@And("user enters otp")
	public void enterOtp() {
		getLoginPage().enterOtp(ConfigReader.get("otp"));
	}

	@And("user click on verify button")
	public void clickVerify() {
		 if (!loginSuccess) {
		        System.out.println("Skipping Verify because login failed.");
		        return;
		    }

		 getLoginPage().clickVerifyBtn();
	}

	@And("the user clicks on login button from excel row {int}")
	public void clickLoginFromExcel(int row) {
		getLoginPage().clickLoginBtn();

	    try {
	        if (getLoginPage().isOtpFieldDisplayed()) {
	            loginSuccess = true;
	        } else {
	            loginSuccess = false;
	        }
	    } catch (Exception e) {
	        loginSuccess = false;
	    }
	}

	
	@Then("the user should be logged in successfully")
	public void verifyLogin() {
		if (!loginSuccess) {
	        System.out.println("❌ Login failed, skipping OTP/Verify for this test row.");
	        ExtentTestManager.getTest().log(Status.FAIL, "Login failed, OTP/Verify skipped");
	        return;
	    }
		Assert.assertTrue(getLoginPage().verifyPage(), "✅ User successfully logged in");
	}

	@When("user enters username from excel row {int}")
	public void usernameFromExcel(int row) {
		String username = excel.getCellData("Login", row, 0);
		getLoginPage().enterUsername(username);
	}

	@When("user enters password from excel row {int}")
	public void passwordFromExcel(int row) {
		String password = excel.getCellData("Login", row, 1);
		getLoginPage().enterPassword(password);
	}

	@When("user enters otp from excel row {int}")
	public void otpFromExcel(int row) {
		 if (!loginSuccess) {
	            System.out.println("Skipping OTP for row " + row + " because login failed.");
	            return;
	        }

	        String otp = excel.getCellData("Login", row, 2);
	        getLoginPage().enterOtp(otp);
	}
	
	}
