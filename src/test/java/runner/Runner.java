package runner;

import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static void main(String[] args) {
		TestNGRunner testNG = new TestNGRunner(1);
		testNG.createSuite("Manage Stocks", false);
		testNG.addListener("listner.MyTestngListner");
		testNG.addTest("Add Stock Test");
		testNG.addTestParameter("action", "addStock");

		// Adding doLogin Test Method
		List<String> includeMethods = new ArrayList<String>();
		includeMethods.add("doLogin");
		testNG.addTestClass("testCases.SessionTest", includeMethods);

		// Add Select Portfolio Method
		includeMethods = new ArrayList<String>();
		includeMethods.add("selectPortfolio");
		testNG.addTestClass("testCases.PortfolioTest", includeMethods);

		// Add "Add Stock" Methods
		includeMethods = new ArrayList<String>();
		includeMethods.add("addStockTest");
		includeMethods.add("verifyStockIsPresent");
		includeMethods.add("verifyTransactionHistory");
		testNG.addTestClass("testCases.StockTest", includeMethods);

		testNG.run();
	}
}
