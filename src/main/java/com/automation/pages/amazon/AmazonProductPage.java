package com.automation.pages.amazon;

import com.automation.core.BasePage;

import com.automation.locators.amazon.AmazonProductPageElement;
import com.automation.utils.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static com.automation.utils.Utilities.addAttachment;

public class AmazonProductPage extends BasePage {

    public AmazonProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Add the product to the cart")
    public void addToCart() {
        Log.info("Adding the product to the cart", webDriver);
        click(AmazonProductPageElement.ADD_TO_CART);
        addAttachment("Add to Cart clicked", webDriver);
    }

    @Step("Check that the product was added")
    public boolean isAddedToCart() {
        boolean added = isVisible(AmazonProductPageElement.ADDED_TO_CART, 10)
                || webDriver.getCurrentUrl().contains("/cart");
        addAttachment("Add to cart result", webDriver);
        return added;
    }

    @Step("Open the cart")
    public void goToCart() {
        Log.info("Opening the cart", webDriver);
        click(AmazonProductPageElement.CART_LINK);
        addAttachment("Cart opened", webDriver);
    }
}
