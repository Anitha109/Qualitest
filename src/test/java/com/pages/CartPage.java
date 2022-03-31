package com.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//tr[@class='woocommerce-cart-form__cart-item cart_item']/td[@class='product-name']/a")
    private WebElement itemInCart;

    @FindBy(className = "single-title")
    private WebElement pageTitle;

    @Override
    protected String getPageTitle() {
        return pageTitle.getText().trim();
    }

    @Override
    public void seePageTitle() {
        assertThat(getPageTitle(), equalTo("Cart"));
    }

    public String getItemInCart() {
        return itemInCart.getText();
    }
}
