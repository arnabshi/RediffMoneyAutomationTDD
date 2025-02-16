package Keywords;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

public class ApplicationKeywords extends ValidationKeywords {
	public ApplicationKeywords() {
		prop = new Properties();
		try {
			FileInputStream fs = new FileInputStream(
					System.getProperty("user.dir") + "//src//test//resources//Project.properties");
			prop.load(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		softAssert = new SoftAssert();
	}

	public void selectDateFromCalendar(String date) {
		logInfo("Selecting date : " + date);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date dateToSelect = dateFormat.parse(date);
			String day = new SimpleDateFormat("d").format(dateToSelect);
			String month = new SimpleDateFormat("MMMM").format(dateToSelect);
			String year = new SimpleDateFormat("yyyy").format(dateToSelect);

			String monthYearToBeSelected = month + " " + year;

			String monthYearDisplayed = getElement("monthyear_css").getText();
			while (!monthYearToBeSelected.equals(monthYearDisplayed)) {
				click("datebackButoon_xpath");
				monthYearDisplayed = getElement("monthyear_css").getText();
			}

			driver.findElement(By.xpath("//td[text()='" + day + "']")).click();
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void goToBuySell(String companyName) {
		int rowNum = getRowNumWithCellData("stockTable_id", companyName);
		
		if (rowNum == -1) {
			  logError("Stock not present in list");
		}
		
		driver.findElement(By.cssSelector("table#stock > tbody > tr:nth-child(" + rowNum + ") >td:nth-child(1)")).click();
		driver.findElement(By.cssSelector("table#stock > tbody > tr:nth-child(" + rowNum + ") input.buySell")).click();
		waitforWebPageToLoad();
	}

	public void openTrasactionHistory(String companyName) {
		int rowNum = getRowNumWithCellData("stockTable_id", companyName);
		
		if (rowNum == -1) {
			  logError("Stock not present in list");
		}
		
		driver.findElement(By.cssSelector("table#stock > tbody > tr:nth-child(" + rowNum + ") >td:nth-child(1)")).click();
		driver.findElement(By.cssSelector("table#stock > tbody > tr:nth-child(" + rowNum + ") input.equityTransaction")).click();
		waitforWebPageToLoad();
	}
	
	public int findCurrentStockQuantity(String compnayName) {
		int rowNum=getRowNumWithCellData("stockTable_xpath", compnayName);
		if(rowNum==-1) {
			logError("Stock quantity is 0 as given stock - " + compnayName + " is not present in stock list");
			return 0;
		}
		String quantity=driver.findElement(By.cssSelector("table#stock > tbody > tr:nth-child("+ rowNum +") > td:nth-child(4)")).getText();
		logInfo(compnayName + " :: Stock quantity is -- " + quantity);
		return Integer.parseInt(quantity);
	}
	
	public int getRowNumWithCellData(String locatorKey, String compnayName) {

		WebElement table = getElement(locatorKey);
		List<WebElement> rows = table.findElements(By.tagName("tr"));

		for (int rNum = 0; rNum < rows.size(); rNum++) {
			WebElement row = rows.get(rNum);
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for (int cNum = 0; cNum < cells.size(); cNum++) {
				WebElement cell = cells.get(cNum);
				if (cell.getText().contains(compnayName))
					return (rNum+1);
			}
		}
		return -1; // data is not found
	}

}
