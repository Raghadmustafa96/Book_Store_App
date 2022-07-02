package e2e_tests.web_pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static commons.SeleniumWait.waitTellElementVisibility;

public class HomePage {
    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @FindBy(className = "navbar-brand")
    public WebElement projectNameIcon;

    @FindBy(xpath = "//div/ul[contains(@class,'nav-sidebar')]/li/a")
    public List<WebElement> sidebarTabs;

    @FindBy(xpath = "//div/ul[contains(@class,'nav-sidebar')]/li/a")
    public WebElement sidebar;

    @FindBy(id = "searchID")
    public WebElement searchField;

    public WebElement displaySearchField() {
        return waitTellElementVisibility(searchField, Long.valueOf(5));
    }

    public WebElement displaySidebarTabs() {
        return waitTellElementVisibility(sidebar, Long.valueOf(10));
    }

    public List<WebElement> getSidebarTabs() {
        return sidebarTabs;
    }

    public void clickProjectNameIcon() {
        projectNameIcon.click();
    }
}
