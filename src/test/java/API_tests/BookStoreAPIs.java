package API_tests;

import commons.ExtentReport;
import constants.Constant;
import constants.Endpoint;
import enums.StatusCode;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static io.restassured.RestAssured.*;
import static io.restassured.path.xml.XmlPath.CompatibilityMode.HTML;
import java.util.List;

/**
 *   1. These tests should be run from the TestNG file due to some methods depend on each other
 *        src/test/resources/API_tests/bookStore.xml
 *
 *   2. could check the extent report  src/testReport/API_tests_report/index.html
 * **/

public class BookStoreAPIs extends ExtentReport {
    private final SoftAssert softAssert = new SoftAssert();
    private static String errorMsg = "";
    private Response response;
    private Response response2;

    @Test(description = "Successfully display all books in store")
    public void getBooksList() {
        //Expected values
        int expectedCode = StatusCode.OK.getValue();
        long expectedResponseTime = Constant.EXPECTED_RESPONSE_TIME;

        try {
            response = given().that().header(Constant.CONTENT_TYPE, Constant.text_html_CONTENT_TYPE)
                        .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                        .when().get(Endpoint.BOOKS_API).thenReturn();

        } catch (Throwable throwable) {
            errorMsg = "Error: " + throwable.getMessage();
        }

        Assert.assertNotNull(response, "Response was null");

        //Validating response
        Assert.assertEquals(response.statusCode(), expectedCode, "Status code must be 200");
        softAssert.assertTrue(response.getBody().asString().contains("Books"));
//        softAssert.assertTrue(response.getTime() < expectedResponseTime, "response time must be less than 500");
        softAssert.assertAll();
    }

    @Test(description = "Successfully create new book")
    public void createBook(ITestContext context) {
        //Expected values
        int expectedCode = StatusCode.FOUND.getValue();
        int expectedCode2 = StatusCode.OK.getValue();
        long expectedResponseTime = Constant.EXPECTED_RESPONSE_TIME;

        try {
            response = given().that()
                    .contentType("application/x-www-form-urlencoded; charset=utf-8")
                    .and().formParam("title", "Head First Design Patterns")
                    .and().formParam("year", "1988")
                    .when().post(Endpoint.BOOKS_API).thenReturn();

            response2 = given().that().header(Constant.CONTENT_TYPE, Constant.text_html_CONTENT_TYPE)
                    .accept(Constant.text_html_CONTENT_TYPE)
                    .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                    .when().get(Endpoint.BOOKS_API).thenReturn();

        } catch (Throwable throwable) {
            errorMsg = "Error: " + throwable.getMessage();
        }

        Assert.assertNotNull(response, "Response was null");

        //Validating response
        Assert.assertEquals(response.statusCode(), expectedCode, "Status code must be 302");
        Assert.assertEquals(response2.statusCode(), expectedCode2, "Status code must be 200");
        softAssert.assertTrue(response2.getBody().asString().contains("Head First Design Patterns"));
//        softAssert.assertTrue(response.getTime() < expectedResponseTime, "response time must be less than 500");
        softAssert.assertAll();

        XmlPath xmlPath = new XmlPath(HTML,response2.asInputStream());
        List<String> booksLinks = xmlPath.get("**.findAll { it.name() == 'a' && it.text() == 'Edit'}.@href");
        context.getSuite().setAttribute(Constant.BOOK_ENDPOINT, booksLinks.get(booksLinks.size()-1));
        context.getSuite().setAttribute(Constant.ID, booksLinks.get(booksLinks.size()-1).replaceAll("[^0-9]", ""));
    }

    @Test(description = "Successfully display a book based on id")
    public void getBookInfo(ITestContext context) {
        //Expected values
        int expectedCode = StatusCode.OK.getValue();
        String bookAPI = (String) context.getSuite().getAttribute(Constant.BOOK_ENDPOINT);

        try {
            response = given().that().header(Constant.CONTENT_TYPE, Constant.text_html_CONTENT_TYPE)
                    .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                    .when().get(Endpoint.HOST + bookAPI).thenReturn();
        } catch (Throwable throwable) {
            errorMsg = "Error: " + throwable.getMessage();
        }

        Assert.assertNotNull(response, "Response was null");

        //Validating response
        Assert.assertEquals(response.statusCode(), expectedCode, "Status code must be 200");
        softAssert.assertTrue(response.getBody().asString().contains("Head First Design Patterns"));
        softAssert.assertAll();
    }

    @Test(description = "Successfully edit the book information based on id")
    public void editBookInfo(ITestContext context) {
        //Expected values
        int expectedCode = StatusCode.FOUND.getValue();
        int expectedCode2 = StatusCode.OK.getValue();
        String bookAPI = (String) context.getSuite().getAttribute(Constant.BOOK_ENDPOINT);

        try {
            response = given().that().contentType("application/x-www-form-urlencoded; charset=utf-8")
                    .and().formParam("id", (String) context.getSuite().getAttribute(Constant.ID))
                    .and().formParam("title", "Head First Design Patterns_Edit*****")
                    .and().formParam("year", "1996")
                    .when().post(Endpoint.BOOKS_API).thenReturn();

            response2 = given().that().header(Constant.CONTENT_TYPE, Constant.text_html_CONTENT_TYPE)
                    .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                    .when().get(Endpoint.HOST + bookAPI).thenReturn();

        } catch (Throwable throwable) {
            errorMsg = "Error: " + throwable.getMessage();
        }

        Assert.assertNotNull(response, "Response was null");

        //Validating response
        Assert.assertEquals(response.statusCode(), expectedCode, "Status code must be 302");
        Assert.assertEquals(response2.statusCode(), expectedCode2, "Status code must be 200");
        softAssert.assertTrue(response2.getBody().asString().contains("Head First Design Patterns_Edit*****"));
        softAssert.assertAll();
    }

    @Test(description = "Successfully delete any book based on id")
    public void deleteBook(ITestContext context) {
        //Expected values
        int expectedCode = StatusCode.OK.getValue();
        int expected_internalServerError = StatusCode.INTERNAL_SERVER_ERROR.getValue();
        String id = (String) context.getSuite().getAttribute(Constant.ID);
        String deleteURL = Endpoint.BOOKS_API +"/"+id + Endpoint.DELETE_BOOK_API;

//        System.out.println(id);
        try {
            response = given().that()
                    .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                    .when().get(deleteURL).thenReturn();

            response2 = given().that().header(Constant.CONTENT_TYPE, Constant.text_html_CONTENT_TYPE)
                    .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                    .when().get(Endpoint.BOOKS_API + "/"+ id + Endpoint.GET_BOOK_API).thenReturn();
        } catch (Throwable throwable) {
            errorMsg = "Error: " + throwable.getMessage();
        }

        Assert.assertNotNull(response, "Response was null");

        //Validating response
        Assert.assertEquals(response.statusCode(), expectedCode, "Status code must be 200");

        // When trying to get API based on id was previously deleted, "get book API" should return 500
        Assert.assertEquals(response2.statusCode(), expected_internalServerError, "Status code must be 500");
        softAssert.assertFalse(response2.getBody().asString().contains("Head First Design Patterns_Edit*****"));
        softAssert.assertAll();
    }

    // Authors API returns Not Found (404)
    @Test(description = "Successfully returns the book's author list")
    public void getAuthors() {
        //Expected values
        int expectedCode = StatusCode.OK.getValue();

        try {
            response = given().that().header(Constant.CONTENT_TYPE, Constant.text_html_CONTENT_TYPE)
                    .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                    .when().get(Endpoint.GET_AUTHORS_API).thenReturn();
        } catch (Throwable throwable) {
            errorMsg = "Error: " + throwable.getMessage();
        }

        Assert.assertNotNull(response, "Response was null");

        //Validating response
        Assert.assertEquals(response.statusCode(), expectedCode, "Status code must be 200");
    }

    // search API returns Not Found (404)
    @Test(description = "Successfully returns data related to the search that was entered")
    public void searchAPI() {
        //Expected values
        int expectedCode = StatusCode.OK.getValue();

        try {
            response = given().that().header(Constant.CONTENT_TYPE, Constant.text_html_CONTENT_TYPE)
                    .and().header(Constant.CONTENT_LANGUAGE,Constant.ENGLISH )
                    .and().param("searchID","Head First Design Patterns_Edit*****" )
                    .when().get(Endpoint.GET_AUTHORS_API).thenReturn();
        } catch (Throwable throwable) {
            errorMsg = "Error: " + throwable.getMessage();
        }

        Assert.assertNotNull(response, "Response was null");

        //Validating response
        Assert.assertEquals(response.statusCode(), expectedCode, "Status code must be 200");
    }
}
