package e2e_tests.testcases_logic;

import commons.SeleniumDriver;
import constants.Endpoint;
import e2e_tests.util.TestUtils;
import e2e_tests.web_pages.Books;
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

public class BooksTestCases extends SeleniumDriver {
    @Test(description = "Verify clicking on header Books tab redirect to books page")
    public void verifyClickingHeaderBookTabRedirectToBookPage(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInHeader();

        String redirectURL = seleniumDriver.getCurrentUrl();
        softAssert.assertEquals(redirectURL, Endpoint.HOST + Endpoint.BOOKS_API);

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify clicking on sidebar Books tab redirect to books page")
    public void verifyClickingSidebarBookTabRedirectToBookPage(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();

        String redirectURL = seleniumDriver.getCurrentUrl();
        softAssert.assertEquals(redirectURL, Endpoint.HOST + Endpoint.BOOKS_API);

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify display the books list")
    public void verifyDisplayBookList(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        List<WebElement> bookList = books.getBookList();

        for (WebElement book : bookList) {
            softAssert.assertNotNull(book.getText());
        }

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify clicking create book button redirect to create book page")
    public void verifyClickingCreateButtonRedirectToCreatePage(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();
        books.clickCreateBookButton();

        String redirectURL = seleniumDriver.getCurrentUrl();
        softAssert.assertEquals(redirectURL, Endpoint.HOST + Endpoint.CREATE_BOOK_PAGE);

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify create new book correctly")
    public void verifyCreateNewBookCorrectly(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();
        books.clickCreateBookButton();

        String bookTitle = "macleod's clinical examination";

        books.fillTitleField(bookTitle);
        books.fillYearField("1900");
        books.clickSaveButton();

        List<WebElement> bookList = books.getBookList();
        String actualBookTitle = bookList.get(bookList.size()-1).getText();
        softAssert.assertEquals(actualBookTitle , bookTitle);

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify the Title length should have at least 8 characters")
    public void verifyLengthOfTitleMoreThanEightCharacters(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();
        books.clickCreateBookButton();

        String bookTitle = "secret"; // Title length less than 8
        books.fillTitleField(bookTitle);
        books.fillYearField("1900");
        books.clickSaveButton();

        String errorMessage = books.displayErrorMessageBelowTheField().getText();
        softAssert.assertEquals(errorMessage , "Title should have at least 8 characters");

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify Clicking the Clear button on the create page should reset all fields")
    public void VerifyClickClearButtonResetAllFields(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();
        books.clickCreateBookButton();

        books.fillTitleField("The secret book");
        books.fillYearField("2005");

        softAssert.assertFalse(books.titleField.getAttribute("value").isEmpty());
        softAssert.assertFalse(books.yearField.getAttribute("value").isEmpty());

        books.clickClearButton();

        softAssert.assertTrue(books.titleField.getAttribute("value").isEmpty());
        softAssert.assertTrue(books.yearField.getAttribute("value").isEmpty());

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify the title field in create page is mandatory")
    public void VerifyTitleFieldIsMandatory(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();
        books.clickCreateBookButton();

        books.fillTitleField("");
        books.fillYearField("1900");
        books.clickSaveButton();

        String errorMessage = books.displayErrorMessageBelowTheField().getText();
        softAssert.assertEquals(errorMessage , "Title should have at least 8 characters");

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify the year field in create page is optional")
    public void VerifyYearFieldIsOptional(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();
        books.clickCreateBookButton();

        String bookTitle = "The secret book";
        books.fillTitleField(bookTitle);
        books.fillYearField("");
        books.clickSaveButton();

        // The new book must be added to the book list even though the year field is empty
        List<WebElement> bookList = books.getBookList();
        String actualBookTitle = bookList.get(bookList.size()-1).getText();
        softAssert.assertEquals(actualBookTitle , bookTitle);

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify clicking edit button redirect to edit book page")
    public void VerifyClickEditButtonRedirectToEditPage(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();

        List<WebElement> bookList = books.getBookList();

        // Before clicking the edit button, you should check if there is already books in the list
        if(bookList.size() == 0){
            System.out.println(" book list is empty ");
        } else {
            List<WebElement> editButtons = books.getEditButtons();
            editButtons.get(editButtons.size()-1).click();

            String redirectURL = seleniumDriver.getCurrentUrl();
            softAssert.assertEquals(redirectURL, Endpoint.HOST + "/edit");
        }

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "verify After editing book info, the modification should be successfully reflected")
    public void VerifyTheEditingIsReflectedCorrectly(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();

        List<WebElement> bookList = books.getBookList();

        // Before clicking the edit button, you should check if there is already books in the list
        if(bookList.size() == 0){
            System.out.println(" book list is empty ");
        } else {
            List<WebElement> editButtons = books.getEditButtons();
            editButtons.get(editButtons.size() - 1).click(); // edit the last book in list

            String currentTitleField = books.titleField.getAttribute("value");
            books.titleField.clear();
            String newTitleField = "The Merchant of Venice";
            books.fillTitleField(newTitleField);

            books.clickSaveButtonInEditForm();

            List<WebElement> updatedBooksList = books.getBookList();
            String actualBookTitle = bookList.get(bookList.size() - 1).getText();
            softAssert.assertEquals(actualBookTitle, newTitleField); // last book title updated correctly
        }

        TestUtils.takePicture(method.getName());
    }

    @Test(description = "Verify after deleting the book it should be removed from the book list")
    public void VerifyRemoveBookProperly(Method method) throws IOException {
        Books books = new Books(seleniumDriver);
        SoftAssert softAssert = new SoftAssert();

        books.clickBookTabInSidebar();

        List<WebElement> bookList = books.getBookList();

        // Before clicking the remove button, you should check if there is already books in the list
        if(bookList.size() == 0){
            System.out.println(" book list is empty ");
        } else {
            int bookListSizeBeforeDelete = bookList.size();
            String lastBookTitle = bookList.get(bookList.size() - 1).getText(); // Title of the last book in list

            List<WebElement> removeButtons = books.getRemoveButtons();
            removeButtons.get(removeButtons.size() - 1).click(); // remove the last book in list

            int bookListSizeAfterDelete = bookList.size();

            // Book list size reduced by one
            softAssert.assertEquals(bookListSizeBeforeDelete, bookListSizeAfterDelete - 1);

            // The book marked for deletion has already been deleted
            String newLastBookTitle = bookList.get(bookList.size() - 1).getText(); // Title of the new last book in list
            softAssert.assertNotEquals(lastBookTitle, newLastBookTitle );
        }

        TestUtils.takePicture(method.getName());
    }
}
