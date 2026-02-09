package runners;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
	    features = "src/test/resources/features",
	    glue = {"stepDefinitions", "hooks"},
	    plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber.json"},
	   // tags = "@smoke",
	    monochrome = true
	)

@Listeners({listeners.RetryTransformer.class, listeners.TestListener.class})
public class CucumberRunnerTest extends AbstractTestNGCucumberTests{
	@Override
	@DataProvider(parallel = false)
	public Object[][] scenarios() {
	    return super.scenarios();
	}

	

}
