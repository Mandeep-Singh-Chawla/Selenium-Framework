package com.automation.pages.amazon;

import com.automation.core.BasePage;
import com.automation.locators.amazon.AmazonSearchResultsPageElement;
import com.automation.utils.Config;
import com.automation.utils.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.automation.utils.Utilities.addAttachment;

public class AmazonSearchResultsPage extends BasePage {

    public AmazonSearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Add the first shippable search result to the cart")
    public void addFirstAvailableProductToCart() {
        Log.info("Adding the first shippable search result to the cart", webDriver);
        dismissDeliveryPrompt();
        WebElement addToCart = firstAddToCartButton();
        try {
            ((JavascriptExecutor) webDriver).executeScript(
                    "var button = arguments[0].closest('.a-button');"
                            + "var control = button ? button.querySelector('input, button') : arguments[0];"
                            + "control.click();",
                    addToCart);
        } catch (TimeoutException e) {
            Log.warn("Amazon did not finish loading after Add to cart; continuing");
        }
        addAttachment("Add to cart clicked", webDriver);
    }

    @Step("Check that the product was added")
    public boolean isAddedToCart() {
        boolean added = isVisible(AmazonSearchResultsPageElement.ADDED_TO_CART, 10) || cartCount() > 0;
        addAttachment("Add to cart result", webDriver);
        return added;
    }

    @Step("Open the cart")
    public void goToCart() {
        Log.info("Opening the cart", webDriver);
        try {
            click(AmazonSearchResultsPageElement.CART_LINK);
        } catch (TimeoutException e) {
            Log.warn("Amazon did not finish loading the cart page; continuing");
        }
        addAttachment("Cart opened", webDriver);
    }

    private WebElement firstAddToCartButton() {
        return new WebDriverWait(webDriver, Duration.ofSeconds(Config.getInt("timeout.explicit")))
                .until(driver -> {
                    List<WebElement> matches = driver.findElements(AmazonSearchResultsPageElement.ADD_TO_CART);
                    for (WebElement match : matches) {
                        if (match.isDisplayed() && match.isEnabled()) {
                            ((JavascriptExecutor) driver).executeScript(
                                    "arguments[0].scrollIntoView({block:'center'});", match);
                            return match;
                        }
                    }
                    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 700);");
                    return null;
                });
    }

    private void dismissDeliveryPrompt() {
        for (WebElement button : webDriver.findElements(AmazonSearchResultsPageElement.DISMISS_DELIVERY)) {
            if (button.isDisplayed()) {
                button.click();
                break;
            }
        }
    }

    private int cartCount() {
        List<WebElement> counts = webDriver.findElements(AmazonSearchResultsPageElement.CART_COUNT);
        if (counts.isEmpty()) {
            return 0;
        }
        String value = counts.get(0).getText().trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
