package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HomePage extends BasePage{

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }


    @FindBy(className = "page-title")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[@title='Accept Cookies']")
    private List<WebElement> acceptCookies;

    @Override
    public void seePageTitle() {
        assertThat(driver.getTitle(), equalTo("TESTSCRIPTDEMO – Automation Practice"));
    }

    @FindBy(xpath = "//a[@class='cc-btn cc-accept-all cc-btn-no-href']")
    private WebElement acceptAllCookiesButton;

    @FindBy(linkText = "Shop")
    private WebElement shop;

    public void acceptCookies() {
        acceptAllCookiesButton.click();
    }

    public void clickOnShopTab() {
        shop.click();
    }

}
