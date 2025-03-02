package Keywords;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class ValidationKeywords extends ActionKeywords {

	public void validateTitle(String expectedTitle) {
		logInfo("Expected Title : " + expectedTitle);
		Assert.assertEquals(driver.getTitle(), expectedTitle);
	}

	public void validateTextInPage(String textToFind) {
		logInfo("Check text : "+ textToFind);
		List<WebElement> li = driver.findElements(By.xpath("//*[contains(text(),textToFind)]"));
		if (li.size() > 0) {
			logInfo("Text present : " + textToFind);
		} else {
			logError("Text not present : " + textToFind);
		}
	}

	public void validateSelectedValueInDropDown(String locatorKey, String value) {
		Select dropdown = new Select(getElement(locatorKey));
		String selectedValue = dropdown.getFirstSelectedOption().getText();
		logInfo("Selected Value in DropDown : " + selectedValue);

		if (!selectedValue.equals(value)) {
			reportFailure("Entered " + value + " is not availble in Portfolio List", true);
		}
		logPASS("Selected right portfolio");
	}

	public void validateSelectedValueNotInDropDown(String locatorKey, String value) {
		Select dropdown = new Select(getElement(locatorKey));
		String selectedValue = dropdown.getFirstSelectedOption().getText();
		if (selectedValue.equals(value)) {
			reportFailure("Portfolio " + value + " is  availble in Portfolio List", true);
		}
		logPASS(value + " is not present in dropdown as expected");
	}
}
