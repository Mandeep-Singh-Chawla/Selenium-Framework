package com.automation.locators.amazon;

import org.openqa.selenium.By;

public final class AmazonSearchResultsPageElement {

    private AmazonSearchResultsPageElement() {
    }

    public static final By ADD_TO_CART = By.xpath(
            "(//div[contains(@class,'s-main-slot')]//span[contains(@class,'a-button-text') and normalize-space()='Add to cart'])[1]");
    public static final By ADDED_TO_CART = By.xpath(
            "//*[contains(text(),'Added to Cart') or contains(text(),'Added to cart')]");
    public static final By CART_COUNT = By.id("nav-cart-count");
    public static final By CART_LINK = By.id("nav-cart");
    public static final By DISMISS_DELIVERY = By.xpath(
            "//*[self::button or self::input or self::a][contains(normalize-space(.),'Dismiss')]");
}
