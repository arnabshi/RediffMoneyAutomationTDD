package testCases;

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
	public void doLogin() {
		app.logInfo("Login to application");
		app.click("signIn_linkText");
		app.waitforWebPageToLoad();
		app.set("userName_id", "arnab1712@rediffmail.com");
		app.set("password_xpath", "Rediff#098");
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
