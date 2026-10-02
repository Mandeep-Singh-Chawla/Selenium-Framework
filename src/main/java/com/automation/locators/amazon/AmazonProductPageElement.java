package com.automation.locators.amazon;

import org.openqa.selenium.By;

public final class AmazonProductPageElement {

    private AmazonProductPageElement() {
    }

    public static final By ADD_TO_CART = By.id("add-to-cart-button");
    public static final By CART_LINK = By.id("nav-cart");
    public static final By PRODUCT_TITLE = By.id("productTitle");
    public static final By ADDED_TO_CART = By.xpath("//*[contains(text(),'Added to Cart') or contains(text(),'Added to cart')]");
}
