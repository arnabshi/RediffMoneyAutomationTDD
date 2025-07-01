package Keywords;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionKeywords extends GenericKeywords {
	
	public void click(String locatorKey) {
		logInfo("Perform click : " + prop.getProperty(locatorKey));
//        try{
		getElement(locatorKey).click();
//		logInfo("element clicked : " + locatorkey);
//        }catch(Exception e){
//            logError("unable to click element : " + locatorkey);
//            e.printStackTrace();
//        }

	}

	public void sendKeysToElement(String locatorKey,String key) {
		logInfo("Pressing Key Enter");
		if(key.equalsIgnoreCase("Enter"))
		getElement(locatorKey).sendKeys(Keys.ENTER);
	}

	public void clickButton(String locatorKey) {
		logInfo("Perform Button Click : " + prop.getProperty(locatorKey));
		getElement(locatorKey).click();
	}

	public void set(String locatorKey, String value) {
		logInfo("Set Text : " + value + " - In Locator : " + prop.getProperty(locatorKey));
		getElement(locatorKey).sendKeys(value);
	}

	public void selectByVisibleText(String locatorKey, String value) {
		logInfo("Selecting Text : " + value);
		WebElement element=getElement(locatorKey);
		element.click();
		Select dropDown = new Select(element);
		List<WebElement> options=dropDown.getOptions();
		for(WebElement wb:options){
			logInfo("available options to select - "+ wb.getText());
		}
		try {
			dropDown.selectByValue(value);
		}
		catch(Exception e){
			throw new NoSuchElementException("not able to select - " +value+e.getMessage());
		}
	}

	public void acceptAlert() {
		logInfo("Accepting the Alert");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.alertIsPresent());

		driver.switchTo().alert().accept();
		logInfo("Accepted the Alert Successfully");
	}

	public void clear(String locatorKey) {
		logInfo("Clear Default Text : " + locatorKey);
		getElement(locatorKey).clear();
	}

	public String getText(String locatorKey) {
		logInfo("Get Text : " + locatorKey);
		return getElement(locatorKey).getText();
	}
}
