package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import java.util.Objects;

public abstract class BasePage {

    protected final WebDriver driver;
    private static WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if (Objects.isNull(wait))
            wait = new WebDriverWait(driver, 20);
    }

    @FindBy(className = "page-title")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[@title='Accept Cookies']")
    private List<WebElement> acceptCookies;

    public abstract void seePageTitle();

    protected String getPageTitle() {
        return pageTitle.getText().trim();
    }

    protected void acceptCookies() {
        if (waitForAllElements(acceptCookies).size() > 0) {
            waitFor(2);
            waitForElementToBeClickable(acceptCookies.get(0)).click();
        }
    }

    public void waitFor(long sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isElementPresent(WebElement parentElement, String childElement) {
        try {
            parentElement.findElement( By.xpath(childElement));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected List<WebElement> waitForAllElements(List<WebElement> elementList) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elementList));
    }

}
