package testCases;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import testbase.BaseTest;

public class SessionTest extends BaseTest {

	@Test
	public void openUp() {
		app.openBrowser("browser_name");
		app.openURL("URL");
//		app.click("rediffMoney_linkText");
		app.validateTextInPage("rediff Moneywiz");
//		app.takeScreenshot();
	}

	@Test
	public void doLogin(ITestContext context) {
		app.logInfo("doLogin method started ---------- ");
		app.click("signIn_linkText");
		app.waitforWebPageToLoad();

		String userName = (String) context.getAttribute("userName");
		String password = (String) context.getAttribute("password");
		app.set("userName_id", userName);
		app.set("password_xpath", password);

		app.wait(30);
		app.clickButton("submitBtn_id");
		app.validateTextInPage("arnab1712");
	}

	@Test
	public void doLogout() {
		app.logInfo("Log out from application");
		app.click("signOut_linkText");
		app.waitforWebPageToLoad();
	}

//	@Test(dependsOnMethods = "testCases.PortfolioTest.deletePortfolio")
//	public void doLogout2() {
//		app.logInfo("Log out from application");
//		app.click("signOut_linkText");
//		app.waitforWebPageToLoad();
//	}
}
