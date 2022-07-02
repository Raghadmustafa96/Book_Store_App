package e2e_tests.testcases_logic;

import commons.SeleniumDriver;
import e2e_tests.util.TestUtils;
import e2e_tests.web_pages.HomePage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *   1. These tests could be run from the TestNG file
 *        src/test/resources/e2e_tests/booksTestCases.xml
 *
 *   2. could check the extent report and screenShots  src/testReport/e2e_tests_report/index.html
 * **/

public class HomepageTestCases extends SeleniumDriver {
    @Test(description = "Verify that the home page title appears correctly")
    public void VerifyHomePageTitleAppearsCorrect(Method method) throws IOException {
        HomePage homepage = new HomePage(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        String actualTitle = seleniumDriver.getTitle();
        String expected = "Home";

        softAssert.assertEquals(actualTitle , expected);
        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify clicking the project name button redirects to the home page")
    public void VerifyClickingProjectNameRedirectsToHomepage(Method method) throws IOException {
        HomePage homepage = new HomePage(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        homepage.clickProjectNameIcon();

        String redirectURL = seleniumDriver.getCurrentUrl();
        softAssert.assertEquals(redirectURL,siteUrl);

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "verify that all required tabs are displayed in the sidebar")
    public void VerifyDisplaySidebarContentOnHomepage(Method method) throws IOException {
        HomePage homepage = new HomePage(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        String expectedTabs [] = {"Home","Books","Authors"};
        homepage.displaySearchField();

        List<WebElement> sideTabs = homepage.getSidebarTabs();

        int index = 0;
        for (WebElement tabName : sideTabs) {
            softAssert.assertEquals(tabName.getText(), expectedTabs[index++]);
        }
        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify that the search section is displayed on the home page")
    public void VerifyDisplaySearchSectionOnHomepage(Method method) throws IOException {
        HomePage homepage = new HomePage(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertNotNull(homepage.displaySearchField());

        TestUtils.takePicture(method.getName());
    }
}
