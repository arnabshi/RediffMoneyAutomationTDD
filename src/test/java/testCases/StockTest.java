package testCases;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import testbase.BaseTest;

public class StockTest extends BaseTest {
	
	@Parameters({ "action" })
	@Test
	public void modifyStock(String action, ITestContext context) {
		app.logInfo("modifyStock method started ----------");
		
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String companyName = (String) data.get("companyName");
		String modifiedQuantity = (String) data.get("stockQuantity");
		String stockPrice = (String) data.get("stockPrice");
		String selectionDate = (String) data.get("selectionDate");

		app.logInfo("Modifying Quantity : " + modifiedQuantity + " of Stock :: " + companyName);

		int quantityBeforeModify = app.findCurrentStockQuantity(companyName);
		context.setAttribute("quantityBeforeModify", quantityBeforeModify);

		app.goToBuySell(companyName);
		if(action.equals("addStock")) {
			app.selectByVisibleText("equityaction_id", "Buy");
		}else {
			app.selectByVisibleText("equityaction_id", "Sell");
		}
		
		app.click("buySellCalendar_id");
		app.selectDateFromCalendar(selectionDate);
		app.set("buysellqty_id", modifiedQuantity);
		app.set("buysellprice_id", stockPrice);
		app.click("buySellStockButton_id");
		app.waitforWebPageToLoad();
		app.wait(2);
		app.logPASS("Stock :: " + companyName + " modified Successfully....");
	}
	
	@Parameters({ "action" })
	@Test
	public void verifyStockQuantity(String action, ITestContext context) {
		app.logInfo("verifyStockQuantity method started for :: " + action + "---------");
		
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String companyName = (String) data.get("companyName");
		int modifiedQuantity = Integer.parseInt((String)data.get("stockQuantity"));
		int expectedModifiedQuantity = 0;

		int quantity = app.findCurrentStockQuantity(companyName);

		int quantityBeforeModify = (int) context.getAttribute("quantityBeforeModify");

		if (action.equals("sellStock")) {
			expectedModifiedQuantity = quantityBeforeModify - quantity;
		} else if (action.equals("addStock")) {
			expectedModifiedQuantity = quantity - quantityBeforeModify;
		}

		app.logInfo("Earlier Stock Quantity : " + quantityBeforeModify);
		app.logInfo("New Stock Quantity : " + quantity);

		if (expectedModifiedQuantity != modifiedQuantity) {
			app.reportFailure("Expected Modified Quantity is not matching", true);
		}

		app.logPASS("Stock Quantity Changed as per expected :: " + expectedModifiedQuantity);

	}
	
	@Test
	public void addStock(ITestContext context) {
		app.logInfo("addStock method started ----------");
		
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String companyName = (String) data.get("companyName");
		String stockQuantity = (String) data.get("stockQuantity");
		String stockPrice = (String) data.get("stockPrice");
		String selectionDate = (String) data.get("selectionDate");

		app.click("addStock_id");
		app.set("addstockname_id", companyName);
		app.wait(2);
		app.sendKeysToElement("addstockname_id","Enter");
		app.click("stockPurchaseDate_id");
		app.selectDateFromCalendar(selectionDate);
		app.set("addstockqty_id", stockQuantity);
		app.set("addstockprice_id", stockPrice);
		app.click("addStockButton_id");
		app.waitforWebPageToLoad();

		app.logPASS("Stock Added Successfully....");
		
		int quantityBeforeModify = app.findCurrentStockQuantity(companyName);
		context.setAttribute("quantityBeforeModify", quantityBeforeModify);
	}

	@Test
	public void verifyStockIsPresent(ITestContext context) {
		app.logInfo("verifyStockIsPresent method started ----------");
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String companyName = (String) data.get("companyName");

		int rowNum = app.getRowNumWithCellData("stockTable_id", companyName);

		if (rowNum == -1) {
			app.reportFailure(companyName + " is not present in Stock List!!! ", true);
		}
		app.logPASS(companyName + " -- Found in Portfolio Stocks");
	}

	@Parameters({ "action" })
	@Test
	public void verifyTransactionHistory(String action, ITestContext context) {
		app.logInfo("verifyTransactionHistory method started for :: " + action + "----------");
		JSONObject data = (JSONObject) context.getAttribute("testData");
		String companyName = (String) data.get("companyName");
		String modifiedQuantity = (String) data.get("stockQuantity");

		app.openTrasactionHistory(companyName);
		String quantityDisplayed = app.getText("trasactionTable_xpath");

		if (action.equals("sellStock")) {
			modifiedQuantity = "-" + modifiedQuantity;
		}
		
		if(!modifiedQuantity.equals(quantityDisplayed)) {
			app.reportFailure("Got changed quantity in transaction history as " + quantityDisplayed, true);
		}
		
		app.logInfo("Latest Change in Stock : " + companyName + " is :: " + quantityDisplayed);
		app.logPASS("Transaction verified successfully");
	}
	
//	@Test
//	public void verifyStockQuantity() {
//		String companyName = "HDFC Bank";
//		app.logInfo("Verify stock quantity after add stock");
//		app.findCurrentStockQuantity(companyName);
//	}
}
