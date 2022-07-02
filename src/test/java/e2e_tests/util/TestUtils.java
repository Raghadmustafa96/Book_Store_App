package e2e_tests.util;

import commons.SeleniumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class TestUtils extends SeleniumDriver {
    public TestUtils() {
        super();
    }

    public static void takePicture(String name) throws IOException {
        File srcFile = ((TakesScreenshot) seleniumDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File("src/TestReport/e2e_tests_report/" + name + ".png"));
    }
}
