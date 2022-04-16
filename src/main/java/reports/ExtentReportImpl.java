package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import constants.FrameworkConstants;
import utils.TakeScreenshotImpl;

import java.util.Objects;

public final class ExtentReportImpl {

    private static ExtentReports extent;
    private static ExtentSparkReporter spark;
    private static Markup m;
    private final static String testInfo = "TEST CASE: - ";

    private ExtentReportImpl() {
    }

    /**
     * Method to initialize Extent Report
     */
    public static void initializeReport() {
        if (Objects.isNull(extent)) {
            extent = new ExtentReports();
            spark = new ExtentSparkReporter(FrameworkConstants.getExtentReportPath() + "AutomationReport.html");
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("AutomationReport");
            spark.config().setReportName("Selenium UI Automation`");
            extent.attachReporter(spark);
        }
    }

    /**
     * Method to set the Extent Report on Test run
     *
     * @param testDescription - Test Description from TestNG to append to report
     * @param testName        - Test Name to append to report
     */
    public static void startTestExecution(String testDescription, String testName) {
        ExtentReportManager.setTest(extent.createTest(testDescription, testName));
    }

    /**
     * Method to mark a Test as pass with required info and screenshot
     *
     * @param testName             - Test Name to append to info
     * @param screenshotIsRequired - specifiying if screenshot is required or not
     */
    public static void passTest(String testName, String screenshotIsRequired) {
        String passText = "<b>" + testInfo + testName + " PASSED" + "</b>";
        m = MarkupHelper.createLabel(passText, ExtentColor.GREEN);
        if (screenshotIsRequired.equalsIgnoreCase("yes")) {
            ExtentReportManager.getTest().pass(m).addScreenCaptureFromBase64String(TakeScreenshotImpl.takeScreenshotAsBase64());
        } else {
            ExtentReportManager.getTest().pass(m);
        }

    }

    /**
     * Method to mark a Test as skip with required info and screenshot
     *
     * @param testName             - Test Name to append to info
     * @param screenshotIsRequired - specifiying if screenshot is required or not
     */
    public static void skipTest(String testName, String screenshotIsRequired) {
        String skipTest = "<b>" + testInfo + testName + " SKIPPED" + "</b>";
        m = MarkupHelper.createLabel(skipTest, ExtentColor.GREY);
        if (screenshotIsRequired.equalsIgnoreCase("yes")) {
            ExtentReportManager.getTest().skip(m).addScreenCaptureFromBase64String(TakeScreenshotImpl.takeScreenshotAsBase64());
        } else {
            ExtentReportManager.getTest().skip(m);
        }

    }

    /**
     * Method to mark a Test as fail with required info and screenshot
     *
     * @param testName             - Test Name to append to info
     * @param screenshotIsRequired - specifiying if screenshot is required or not
     */
    public static void failTest(String testName, String screenshotIsRequired) {
        String failText = "<b>" + testInfo + testName + " FAILED" + "</b>";
        m = MarkupHelper.createLabel(failText, ExtentColor.RED);
        if (screenshotIsRequired.equalsIgnoreCase("yes")) {
            ExtentReportManager.getTest().fail(m).addScreenCaptureFromBase64String(TakeScreenshotImpl.takeScreenshotAsBase64());
        } else {
            ExtentReportManager.getTest().fail(m);
        }

    }

    /**
     * Method to flush Extent Report
     */
    public static void flushReports() {
        if (Objects.nonNull(extent)) {
            extent.flush();
        }
    }
}
