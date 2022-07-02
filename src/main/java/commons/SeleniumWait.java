package commons;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWait extends SeleniumDriver {
    private static WebDriverWait seleniumWait;

    public static WebElement waitTellElementVisibility(WebElement element, Long duration) {
        seleniumWait = new WebDriverWait(seleniumDriver, duration);
        return seleniumWait.until(ExpectedConditions.visibilityOf(element));
    }
}
