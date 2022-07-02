package commons;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class ExtentReport {
    public static ExtentReports extent;
    public static ExtentTest logger;

    @BeforeSuite
    public void reportSetUp() {
        extent = new ExtentReports("src/testReport/API_tests_report/index.html", true);

        extent.addSystemInfo("Os", "windows");
        extent.addSystemInfo("Owner", "Raghad Mustafa");
        extent.addSystemInfo("TestName", "Book_Store_APIs - atypon");
    }

    @AfterSuite
    public void reportTearDown() {
        extent.flush();
    }

    @BeforeMethod
    public void setup(Method method) {
        Test test = method.getAnnotation(Test.class);
        logger = extent.startTest(this.getClass().getSimpleName() + " / " + method.getName(), test.description());
    }

    @AfterMethod
    public void tearDown(Method method, ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(LogStatus.PASS, "Test passed");
        } else if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, result.getThrowable()); // show the error message
        } else {
            logger.log(LogStatus.SKIP, "Test Skipped");
        }
    }
}
