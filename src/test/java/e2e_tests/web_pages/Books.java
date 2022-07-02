package e2e_tests.web_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.xml.xpath.XPath;
import java.util.List;

import static commons.SeleniumWait.waitTellElementVisibility;

public class Books {

    public Books(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[@id=\"navbar\"]/ul/li[2]/a")
    public WebElement bookTabInHeader;

    @FindBy(xpath = "/html/body/div/div/div[1]/ul/li[2]/a")
    public WebElement bookTabInSidebar;

    @FindBy(xpath = "/html/body/div/div/div[2]/table/tbody/tr/td[1]")
    public List<WebElement> bookList;

    @FindBy(xpath = "/html/body/div/div/div[2]/div/a")
    public WebElement createBookButton;

    @FindBy(id = "title")
    public WebElement titleField;

    @FindBy(id = "year")
    public WebElement yearField;

    @FindBy(className = "btn-success")
    public WebElement saveButton;

    @FindBy(xpath = "/html/body/div/div/div[2]/form/div[1]/div[1]/b")
    public WebElement ErrorMessageBelowTheField;

    @FindBy(id = "btn-clear")
    public WebElement clearButton;

    @FindBy(xpath = "/html/body/div/div/div[2]/table/tbody/tr/td[3]/a")
    public List<WebElement> editButtons;

    @FindBy (xpath = "//*[@id=\"bookForm\"]/div[2]/button[1]")
    public WebElement saveButtonInEditForm;

    @FindBy(xpath = "/html/body/div/div/div[2]/table/tbody/tr/td[4]/a")
    public List<WebElement> removeButtons;

    // methods
    public void clickBookTabInHeader() {
        bookTabInHeader.click();
    }

    public void clickBookTabInSidebar() {
        bookTabInSidebar.click();
    }

    public List<WebElement> getBookList() {
        return bookList;
    }

    public void clickCreateBookButton() {
        createBookButton.click();
    }

    public void fillTitleField(String title) {
        titleField.sendKeys(title);
    }

    public void fillYearField(String year) {
        yearField.sendKeys(year);
    }

    public void clickSaveButton(){
        saveButton.click();
    }

    public WebElement displayErrorMessageBelowTheField() {
       return waitTellElementVisibility(ErrorMessageBelowTheField, Long.valueOf(5));
    }

    public void clickClearButton(){
        clearButton.click();
    }

    public List<WebElement> getEditButtons() {
        return editButtons;
    }

    public void clickSaveButtonInEditForm(){
        saveButtonInEditForm.click();
    }

    public List<WebElement> getRemoveButtons() {
        return removeButtons;
    }
}
