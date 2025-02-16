package testbase;

import Keywords.ApplicationKeywords;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import extentReports.ExtentManager;
import runner.DataUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BaseTest {
	public ApplicationKeywords app;
	public ExtentReports extentReports;
	public ExtentTest extentTest;

	@BeforeTest
	public void beforeTest(ITestContext iTestContext) throws FileNotFoundException, IOException, ParseException {

		// base for all the test cases
		app = new ApplicationKeywords();

		extentReports = ExtentManager.getReport();
		extentTest = extentReports.createTest(iTestContext.getCurrentXmlTest().getName());
		extentTest.log(Status.INFO, "Starting Test : " + iTestContext.getCurrentXmlTest().getName());
		app.setExtentTest(extentTest);

		iTestContext.setAttribute("app", app);
		iTestContext.setAttribute("extentReports", extentReports);
		iTestContext.setAttribute("extentTest", extentTest);

		// for test data read

		// Read Test JSON
		String testdatajsonfilePath = iTestContext.getCurrentXmlTest().getParameter("testdatajsonfile");
		// String testdataxlsfilePath
		// =iTestContext.getCurrentXmlTest().getParameter("testdataxlsfile");

		String dataFlag = iTestContext.getCurrentXmlTest().getParameter("dataflag");
		int iteration = Integer.parseInt(iTestContext.getCurrentXmlTest().getParameter("dataSetID"));
		String sheetName = iTestContext.getCurrentXmlTest().getParameter("suiteName");

		System.out.println(testdatajsonfilePath + "  " + dataFlag + "  " + iteration + "  " + sheetName);

		// This is for JSON Reader
		JSONObject data = new DataUtil().getTestData(testdatajsonfilePath, dataFlag, iteration);

		// This is for Excel Reader
//		JSONObject data = new ExcelReader().getTestData(sheetName, dataFlag, testdatajsonfilePath, iteration);
		iTestContext.setAttribute("testData", data);

		String runMode = (String) data.get("runmode");

		if (!runMode.equalsIgnoreCase("Yes")) {
			extentTest.log(Status.SKIP, "RunMode in Test Data is not True");
			throw new SkipException("RunMode in Test Data is not True");
		}
	}

	@BeforeMethod
	public void beforeMethod(ITestContext iTestContext) {
		app = (ApplicationKeywords) iTestContext.getAttribute("app");
		extentReports = (ExtentReports) iTestContext.getAttribute("extentReports");
		extentTest = (ExtentTest) iTestContext.getAttribute("extentTest");

		String isCritical = (String) iTestContext.getAttribute("isCritical");
		if (isCritical != null && isCritical.equals("true")) {
			app.logSkip("Critical Failure in Prevoius Test Method");
			throw new SkipException("Critical Failure in Prevoius Test Method");
		}
	}

	@AfterMethod
	public void afterMethod(ITestContext iTestContext) {
		app = (ApplicationKeywords) iTestContext.getAttribute("app");
		app.reportAll();
	}

	@AfterTest
	public void afterTest(ITestContext iTestContext) {
		app = (ApplicationKeywords) iTestContext.getAttribute("app");
		extentReports = (ExtentReports) iTestContext.getAttribute("extentReports");
		app.quitBrowser();
		extentReports.flush();
	}

}
