package listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestListener implements ITestListener{
	
	 public static ExtentReports extent;

	    @Override
	    public void onStart(ITestContext context) {
	        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	        ExtentSparkReporter spark = new ExtentSparkReporter(
	                ".\\reports\\Test_Report-" + timeStamp + ".html"
	        );

	        spark.config().setTheme(Theme.DARK);
	        spark.config().setDocumentTitle("EHRVMS");
	        spark.config().setReportName("HRVMS Functional Testing");

	        extent = new ExtentReports();
	        extent.attachReporter(spark);
	    }

	    @Override
	    public void onFinish(ITestContext context) {
	        extent.flush();
	    }
}
