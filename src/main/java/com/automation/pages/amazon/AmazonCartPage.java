package com.automation.pages.amazon;

import com.automation.core.BasePage;

import com.automation.locators.amazon.AmazonCartPageElement;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static com.automation.utils.Utilities.addAttachment;

public class AmazonCartPage extends BasePage {

    public AmazonCartPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Check that the cart has items")
    public boolean hasItemsInCart() {
        boolean hasItems = isVisible(AmazonCartPageElement.SUBTOTAL, 10);
        addAttachment("Cart contents", webDriver);
        return hasItems;
    }

    @Step("Check that checkout can start")
    public boolean isProceedToCheckoutVisible() {
        return isVisible(AmazonCartPageElement.PROCEED_TO_CHECKOUT, 5);
    }
}
