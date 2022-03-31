package com.stepdefinitions;

import com.data.Product;
import com.driver.DriverManager;
import com.pages.CartPage;
import com.pages.HomePage;
import com.pages.ShopPage;
import com.pages.WishlistPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.stream.Collectors;

import static com.data.Product.ColumnName.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MyStepdefs {

    private final DriverManager driverManager = new DriverManager();
    private final HomePage iAtHomePage = new HomePage(driverManager.getDriver());
    private final ShopPage iAtShopPage = new ShopPage(driverManager.getDriver());
    private final WishlistPage iAtWishlistPage = new WishlistPage(driverManager.getDriver());
    private final CartPage iAtCartPage = new CartPage(driverManager.getDriver());

    private final List<Product> products = new ArrayList<>();
    private String nameOfProductWithLowestPrice;
    private Product productWithLowestPrice;

    @Before
    public void launchApplication() {
        driverManager.launchApplication();
        iAtHomePage.seePageTitle();
        iAtHomePage.acceptCookies();
    }

    @After
    public void quit() {
        if (Objects.nonNull(driverManager.getDriver()))
            driverManager.getDriver().quit();
    }

    @Given("I add {int} different products to my wish list")
    public void iAddDifferentProductsToMyWishList(int noOfItems) {
        iAtHomePage.clickOnShopTab();
        iAtShopPage.seePageTitle();
        List<WebElement> productsList = iAtShopPage.getProductsList();
        productsList.stream()
                .filter(iAtShopPage::seeAddToCartOption)
                .limit(noOfItems)
                .forEach(element -> {
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put(PRODUCT_NAME, iAtShopPage.getProductName(element));
                    map.put(UNIT_PRICE, iAtShopPage.getPrice(element));
                    map.put(STOCK_STATUS, iAtShopPage.getStockStatus(element));
                    iAtShopPage.addToWishlist(element);
                    products.add(new Product(map));
                });
    }

    @When("I view my wishlist table")
    public void iViewMyWishlistTable() {
        iAtShopPage.clickOnWishlistIcon();
        assertThat("Not able to view wishlist table.", iAtWishlistPage.seeTable(), is(true));

    }

    @Then("I find total {int} selected items in my Wishlist")
    public void iFindTotalSelectedItemsInMyWishlist(int noOfItems) {
        assertThat("Total wishlist items are not " + noOfItems, iAtWishlistPage.totalWishlistItems(), is(noOfItems));
    }

    @When("I search for lowest price product")
    public void iSearchForLowestPriceProduct() {
        productWithLowestPrice = products.stream()
                .min(Comparator.comparing(product ->
                        Double.parseDouble(product.unitPrice().replace("£", ""))))
                .get();
        nameOfProductWithLowestPrice = productWithLowestPrice.productName();
    }

    @When("I am able to add the lowest price item to my cart")
    public void iAmAbleToAddTheLowestPriceItemToMyCart() {
        iAtWishlistPage.clickAddToCartFor(nameOfProductWithLowestPrice);
    }

    @Then("I am able to verify the item in my cart")
    public void iAmAbleToVerifyTheItemInMyCart() {
        assertThat(iAtWishlistPage.seeCartTooltipWithNumberOfAddedItems(), is(1));
        iAtWishlistPage.clickOnCartIcon();
        iAtCartPage.seePageTitle();
        assertThat(iAtCartPage.getItemInCart(), is(productWithLowestPrice.productName()));
        iAtWishlistPage.waitFor(20);
    }
}