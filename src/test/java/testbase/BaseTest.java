package testbase;

import Keywords.ApplicationKeywords;
import Reporter.ExtentManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import runner.DataUtil;
import runner.ExcelReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	public Logger logger;

	@BeforeTest
	public void beforeTest(ITestContext iTestContext) throws FileNotFoundException, IOException, ParseException {

		// base for all the test cases
		app = new ApplicationKeywords();

		extentReports = ExtentManager.getReport();
		extentTest = extentReports.createTest(iTestContext.getCurrentXmlTest().getName());
		extentTest.log(Status.INFO, "Starting Test : " + iTestContext.getCurrentXmlTest().getName());
		app.setExtentTest(extentTest);

		logger=LogManager.getLogger();
		app.setLogger(logger);

		iTestContext.setAttribute("app", app);
		iTestContext.setAttribute("extentReports", extentReports);
		iTestContext.setAttribute("extentTest", extentTest);
		iTestContext.setAttribute("logger", logger);

		// for test data read

		// Read Test JSON
		String testdatafilePath = iTestContext.getCurrentXmlTest().getParameter("testdatafile");
		// String testdataxlsfilePath
		// =iTestContext.getCurrentXmlTest().getParameter("testdataxlsfile");

		String dataFlag = iTestContext.getCurrentXmlTest().getParameter("dataflag");
		int iteration = Integer.parseInt(iTestContext.getCurrentXmlTest().getParameter("dataSetID"));
		String suiteName = iTestContext.getCurrentXmlTest().getParameter("suiteName");
		String testDataType = iTestContext.getCurrentXmlTest().getParameter("testDataType");

//		System.out.println(testdatafilePath + "  " + dataFlag + "  " + iteration + "  " + suiteName);

		JSONObject data=null;
		// This is for Excel Reader
		if(testDataType.equalsIgnoreCase("Excel")) {
			data = new ExcelReader().getTestData(suiteName, dataFlag, testdatafilePath, iteration);
		}
		else {
			// This is for JSON Reader
			data = new DataUtil().getTestData(testdatafilePath, dataFlag, iteration);
		}

		String runMode = (String) data.get("runmode");
		if (!runMode.equalsIgnoreCase("Yes")) {
			extentTest.log(Status.SKIP, "RunMode in Test Data is not True");
			throw new SkipException("RunMode in Test Data is not True");
		}

		iTestContext.setAttribute("testData", data);

		iTestContext.setAttribute("userName", iTestContext.getCurrentXmlTest().getParameter("userName"));
		iTestContext.setAttribute("password", iTestContext.getCurrentXmlTest().getParameter("password"));
	}

	@BeforeMethod
	public void beforeMethod(ITestContext iTestContext) {
		app = (ApplicationKeywords) iTestContext.getAttribute("app");
		extentReports = (ExtentReports) iTestContext.getAttribute("extentReports");
		extentTest = (ExtentTest) iTestContext.getAttribute("extentTest");
		logger=(Logger) iTestContext.getAttribute("logger");

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
