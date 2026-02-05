package stepDefinitions;



import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pageObjects.LogoutPage;
import testBase.DriverFactory;
import utils.WaitUtils;

public class LogoutSteps {
	
	 private LogoutPage lo;

	    private WebDriver getDriver() {
	        return DriverFactory.getDriver();
	    }

	    private LogoutPage getLogoutPage() {
	        if (lo == null) {
	            lo = new LogoutPage(getDriver());
	        }
	        return lo;
	    }
	
	
	@And("the user clicks on image icon")
	public void clickImgIcon() {
		WaitUtils.waitForLoaderToDisappear(getDriver());
		getLogoutPage().clickDropDownImg();
	}
	
	@And("the user clicks on logout")
	public void clickLogoutDropDown() {
		getLogoutPage().clickDropDownLogout();
	}
	
	@And("the user clicks on logout button")
	public void clickLogout() {
		getLogoutPage().clicklogout();
		 WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		    wait.until(ExpectedConditions.urlContains("/Account/Login"));
		    System.out.println("Current URL after logout: " + getDriver().getCurrentUrl());

	}

	@Then("the user should navigate to the loginpage")
	public void verifyLogout() {
		//System.out.println("Current URL: " + getDriver().getCurrentUrl());
		Assert.assertTrue( getLogoutPage().isLoginPageDisplayed(), "❌ Logout failed – Login page not displayed" );
	}

	

}
