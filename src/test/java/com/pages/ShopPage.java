package com.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ShopPage extends BasePage {

    public ShopPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ul[contains(@class,'products ')]//li")
    private List<WebElement> productsList;

    @FindBy(xpath = "//a[@title='Wishlist']")
    private WebElement wishlistIcon;

    private String price = ".//span[@class='price']//span[@class='woocommerce-Price-amount amount']";
    private String wishlist = ".//a[@data-title='Add to wishlist']";
    private String productName = ".//h2[@class='woocommerce-loop-product__title']";
    private String addToCart = ".//a[text()='Add to cart']";

    @Override
    public void seePageTitle() {
        assertThat(getPageTitle(), equalTo("Shop"));
    }


    public List<WebElement> getProductsList() {
        return productsList;
    }

    public String getPrice(WebElement parentElement) {
        List<WebElement> elements = parentElement.findElements(By.xpath(price));
        if (elements.size() == 2) {
            return elements.get(1).getText();
        }
        return elements.get(0).getText();
    }

    public String getStockStatus(WebElement parentElement) {
        return parentElement.getAttribute("class").contains("instock") ? "In Stock" : "Out of Stock";
    }

    public String getProductName(WebElement parentElement) {
        return parentElement.findElement(By.xpath(productName)).getText();
    }

    public void addToWishlist(WebElement parentElement) {
        parentElement.findElement(By.xpath(wishlist)).click();
    }

    public boolean seeAddToCartOption(WebElement parentElement) {
        return isElementPresent(parentElement, addToCart);
    }

    public void clickOnWishlistIcon() {
        waitForElementToBeClickable(wishlistIcon).click();
    }
}
