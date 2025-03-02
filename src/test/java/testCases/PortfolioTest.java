package testCases;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import testbase.BaseTest;

public class PortfolioTest extends BaseTest {
//	String portfolioName = "Test port 11";

	@Test
	public void createPortfolio(ITestContext context) {
		app.logInfo("createPortfolio method started ----------");
		
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String portfolioName = (String) data.get("portfolioname");
		
		app.logInfo("Creating Portfolio :: " + portfolioName);
		app.click("createPortfolio_id");
		app.clear("porfolioname_id");
		app.set("porfolioname_id", portfolioName);
		app.click("createPortfolioButton_id");
		app.waitforWebPageToLoad();
		app.wait(2);
		app.validateSelectedValueInDropDown("portfolio_dropdown_id", portfolioName);
	}

	@Test
	public void deletePortfolio(ITestContext context) {
		app.logInfo("deletePortfolio method started ----------");
		
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String portfolioName = (String) data.get("portfolioname");
		
		app.logInfo("Delete Porfolio :: " + portfolioName);
		app.selectByVisibleText("portfolio_dropdown_id", portfolioName);
		app.waitforWebPageToLoad();
		app.wait(2);
		app.click("deletePortfolio_id");
		app.acceptAlert();
		app.waitforWebPageToLoad();
		app.wait(2);
		app.validateSelectedValueNotInDropDown("portfolio_dropdown_id", portfolioName);

	}

	@Test
	public void selectPortfolio(ITestContext context) {
		app.logInfo("selectPortfolio method started ----------");
		
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String portfolioName = (String) data.get("portfolioname");
		
		app.logInfo("Select Portfolio : " + portfolioName);
		app.selectByVisibleText("portfolio_dropdown_id", portfolioName);
		app.waitforWebPageToLoad();
		app.wait(2);
		app.validateSelectedValueInDropDown("portfolio_dropdown_id", portfolioName);
	}
}
