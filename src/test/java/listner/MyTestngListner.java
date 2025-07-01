package listner;

import Keywords.ApplicationKeywords;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;

public class MyTestngListner implements ITestListener {

	public void onTestFailure(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		ApplicationKeywords app= (ApplicationKeywords) result.getTestContext().getAttribute("app");
		app.logError(result.getThrowable().getMessage());
//		test.log(Status.FAIL, result.getThrowable().getMessage());
//		test.fail(result.getThrowable().getMessage());
	}

	public void onTestSuccess(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		test.pass("Test Success : " + result.getName());

	}

	public void onTestSkipped(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		test.skip(result.getName() + " :: Test Skipped Due to Critical Error or run mode set as No");
	}
}
