package com.main.pages;

import com.google.common.base.Function;
import com.main.utilities.OneInstance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BasePage {

    private final int DEFAULT_TIMEOUT = 50;
    private WebDriver driver;
    private Logger logger = LogManager.getLogger(BasePage.class.getName());
    OneInstance oneInstance = OneInstance.getInstance();

    BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected void setTextInElement(By by, String text) {
        waitForElementToBeClickableBy(by).sendKeys(text);
    }

    protected void clickOnElement(By by) {
        waitForElementToBeClickableBy(by).click();
    }

    protected void shouldAcceptAlert(Boolean value) {
        if(value) {
            driver.switchTo().alert().accept();
        } else{
            driver.switchTo().alert().dismiss();
        }
    }

    protected void selectFromDropdownByValue(By by, String value) {
        Select select = new Select(waitForElementToBeVisibleBy(by));
        select.selectByValue(value);
    }

    protected String getAttribute(String attributeName, By by) {
        return waitForElementToBeClickableBy(by).getAttribute(attributeName);
    }

    protected String getTextFromElementBy(By by) {
        return getWebElementWithFluentWaitBy(by).getText();
    }

    protected void switchToFrameByName(String name) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(name)).switchTo().frame(name);
    }

    protected void switchToFrameByWebElementBy(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by))
                .switchTo().frame(waitForElementToBeVisibleBy(by));
    }

    protected void scrollDownIntoViewBy(By by) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true)", waitForElementToBeClickableBy(by));
    }

    protected void scrollDown(int times) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        for(int i = 0; i < times; i++) {
            oneInstance.waitFor(2);
            js.executeScript("window.scrollBy(0,500)");
        }
    }

    protected List<WebElement> getListOfAllElementsBy(By by) {
        waitForElementToBeClickableBy(by);
        return driver.findElements(by);
    }

    protected void selectElementsByTextName(By by, String... value) {
        List<WebElement> elements = getListOfAllElementsBy(by);
        for(int i = 0; i < value.length; i++) {
            for(WebElement element : elements) {
                if(element.getText().equals(value[i])) {
                    element.click();
                    elements.remove(element);
                    break;
                }
            }
        }
    }

    protected WebElement waitForElementToBeClickableBy(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected WebElement waitForElementToBeVisibleBy(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForElementToBePresentBy(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    protected WebElement getWebElementWithFluentWaitBy(By by) {
        List<Class<? extends Throwable>> list = new ArrayList<>();
        list.add(ElementNotInteractableException.class);
        list.add(NoSuchElementException.class);
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.pollingEvery(Duration.ofSeconds(1L));
        wait.withTimeout(Duration.ofSeconds(60));
        wait.ignoreAll(list);

        Function<WebDriver, WebElement> function = webDriver -> {
            logger.info("Checking for the object!!");
            WebElement element = webDriver.findElement(by);
            if (element != null) {
                logger.info("Element is found:" + by.toString());
            }
            return element;
        };

        return wait.until(function);

}

    protected Boolean isWebElementWithFluentWaitPresentBy(By by) {
        FluentWait<WebDriver> wait = new FluentWait<>(driver);
        wait.pollingEvery(Duration.ofSeconds(2L));
        wait.withTimeout(Duration.ofSeconds(60));
        wait.ignoring(NoSuchElementException.class);

        Function<WebDriver, Boolean> function = webDriver -> {
            logger.info("Checking for the object!!");
            WebElement element = webDriver.findElement(by);
            if (element != null) {
                logger.info("Element is found:" + by.toString());
                return true;
            }
            return false;
        };

        return wait.until(function);

    }


}
