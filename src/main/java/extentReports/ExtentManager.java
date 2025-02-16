package extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.util.Date;

public class ExtentManager {
    //making extentreport and screenshotpath public static so that we can use one instance and also in all test cases
    public static ExtentReports extentReports;
    public static String screenshotPath;

    //method to intialize report and return it
    public static ExtentReports getReport() {
        if (extentReports == null) {
            extentReports = new ExtentReports();

            Date date = new Date();
            String reportFolderName = date.toString().replaceAll(":", "_").replaceAll(" ", "-");
            String reportPath = System.getProperty("user.dir") + "//reports//" + reportFolderName;
            screenshotPath = reportPath + "//screnshots";
            File file=new File(screenshotPath);
            file.mkdirs();

            ExtentSparkReporter reporter=new ExtentSparkReporter(reportPath);
            reporter.config().setTheme(Theme.STANDARD);
            reporter.config().setReportName("Test Result");
            reporter.config().setDocumentTitle("RediffMoney report");
            reporter.config().setEncoding("utf-8");

            extentReports.attachReporter(reporter);
        }
        return extentReports;
    }
}
