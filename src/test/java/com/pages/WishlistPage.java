package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class WishlistPage extends BasePage {

    public WishlistPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//table[@class='shop_table cart wishlist_table wishlist_view traditional responsive   ']")
    private WebElement table;

    @FindBy(xpath = "//table[@class='shop_table cart wishlist_table wishlist_view traditional responsive   ']//tr[child::td[@class='product-name']]")
    private List<WebElement> products;

    @FindBy(xpath = "//div[@class='site-header container-fluid']//a[@class='cart-contents']//span")
    private WebElement cartTooltip;

    @FindBy(xpath = "//a[@title='Cart']")
    private WebElement cart;

    private String addToCartTemplate = "//td[preceding-sibling::td/a[contains(text(),'%s')]]//a[text()='Add to cart']";

    @Override
    public void seePageTitle() {
        assertThat(getPageTitle(), equalTo("Wishlist"));
    }

    public boolean seeTable() {
        return table.isDisplayed();
    }

    public int totalWishlistItems() {
        return products.size();
    }

    public void clickAddToCartFor(String productName) {
        waitForElementToBeClickable(driver.findElement(By.xpath(String.format(addToCartTemplate, productName)))).click();
        waitFor(2);
    }

    public int seeCartTooltipWithNumberOfAddedItems() {
        return Integer.parseInt(cartTooltip.getText());
    }

    public void clickOnCartIcon() {
        waitForElementToBeClickable(cart).click();
    }

}

