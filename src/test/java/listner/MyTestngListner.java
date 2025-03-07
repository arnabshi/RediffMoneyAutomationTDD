package listner;

import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class MyTestngListner implements ITestListener {

	public void onTestFailure(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
//		test.log(Status.FAIL, result.getThrowable().getMessage());
		test.fail(result.getThrowable().getMessage());
	}

	public void onTestSuccess(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		test.pass("Test Success : " + result.getName());

	}

	public void onTestSkipped(ITestResult result) {
		ExtentTest test = (ExtentTest) result.getTestContext().getAttribute("extentTest");
		test.skip(result.getName() + " :: Test Skipped Due to Critical Error in Previous Test");
	}
}
