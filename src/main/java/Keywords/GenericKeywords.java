package Keywords;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import extentReports.ExtentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class GenericKeywords {
    public WebDriver driver;
    public Properties prop;
    public ExtentTest test;
    public SoftAssert softAssert;

    public void openBrowser(String browserKey) {
        String browserName = prop.getProperty(browserKey);
        logInfo("Opening browser in ---- " + browserName);
        if (browserName.equals("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized", "--disable-infobars");
            options.addArguments("--disable-extensions");
            //options.addArguments("--incognito");
            options.addArguments("--disable-notifications");
            driver = new ChromeDriver(options);
        } else if (browserName.equals("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--start-maximized", "--disable-infobars");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-notifications");
            driver = new FirefoxDriver(options);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void openURL(String urlKey) {
        String url = prop.getProperty(urlKey);
        logInfo("Opening Web URL : " + url);
        driver.get(url);
    }

    public void quitBrowser() {
        driver.quit();
    	logInfo("Browser closed------- ");
    }

    public WebElement getElement(String locatorkey){
        WebElement element= driver.findElement(getLocator(locatorkey));
        return element;
    }

    private By getLocator(String locatorkey) {
        By by = null;
        if (locatorkey.endsWith("_id")) {
            by = By.id(prop.getProperty(locatorkey));
        } else if (locatorkey.endsWith("_xpath"))
            by = By.xpath(prop.getProperty(locatorkey));
        else if (locatorkey.endsWith("_css"))
            by = By.cssSelector(prop.getProperty(locatorkey));
        else if (locatorkey.endsWith("_linkText"))
            by = By.linkText(prop.getProperty(locatorkey));
        else if (locatorkey.endsWith("_partialLinkText"))
            by = By.partialLinkText(prop.getProperty(locatorkey));
        else if (locatorkey.endsWith("_name"))
            by = By.name(prop.getProperty(locatorkey));
        else if (locatorkey.endsWith("_className"))
            by = By.className(prop.getProperty(locatorkey));
        else if (locatorkey.endsWith("_name"))
            by = By.name(prop.getProperty(locatorkey));
        else if (locatorkey.endsWith("_tagName"))
            by = By.tagName(prop.getProperty(locatorkey));
        else if(locatorkey.endsWith("_text"))
        	by=By.xpath("//*[contains(text(),locatorkey]");
        return by;
    }

    public void takeScreenshot() {
        //creating file name as date
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd_hh-mm-ss");
        String formattedDate = dateFormat.format(date);
        String screenShot = formattedDate + ".png";
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(file, new File(ExtentManager.screenshotPath + "//" + screenShot));
//          logInfo("Screenshot captured");
            test.log(Status.FAIL, MarkupHelper.createLabel("Screenshot", ExtentColor.RED));
            test.log(Status.FAIL, "<img src='" + ExtentManager.screenshotPath + '/' 
            		+ screenShot + "' style='width:100%;'/>");
        } catch (IOException e) {
            logError("Unable to take screenshot" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void waitforWebPageToLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int i = 0;
        while (i != 10) {
            String state = (String) js.executeScript("return document.readyState;");
            if (state.equals("complete")) {
                break;
            } else {
                wait(2);
            }
            i++;
        }
        // check for jQuery status
        i = 0;
        while (i != 10) {

            Long d = (Long) js.executeScript("return jQuery.active;");
            System.out.println(d);
            if (d.longValue() == 0)
                break;
            else
                wait(2);
            i++;

        }
    }

    public void wait(int time) {
        try {
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //initializing test for logs
    public void setExtentTest(ExtentTest extentTest) {
        this.test = extentTest;
    }
    
    public void reportFailure(String msg,boolean isCritical) {
    	logError(msg);
    	takeScreenshot();
    	softAssert.fail(msg);
    	if(isCritical) {
    		Reporter.getCurrentTestResult().getTestContext().setAttribute("isCritical", "true");
    		reportAll();
    	}
    }
    
    public void reportFailure(String msg) {
    	reportFailure(msg, false);
    }
    
    public void reportAll() {
    	softAssert.assertAll();
    }

    //log methods
    public void logInfo(String msg) {
        test.log(Status.INFO, msg);
    }

    public void logError(String msg) {
        test.log(Status.FAIL, msg);
    }

    public void logWarning(String msg) {
        test.log(Status.WARNING, msg);
    }

    public void logSkip(String msg) {
        test.log(Status.SKIP, msg);
    }
    public void logPASS(String msg) {
        test.log(Status.PASS, msg);
    }

}
