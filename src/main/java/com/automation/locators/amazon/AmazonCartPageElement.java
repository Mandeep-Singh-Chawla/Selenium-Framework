package com.automation.locators.amazon;

import org.openqa.selenium.By;

public final class AmazonCartPageElement {

    private AmazonCartPageElement() {
    }

    public static final By SUBTOTAL = By.cssSelector("#sc-subtotal-label-activecart, #sc-subtotal-label-buybox");
    public static final By PROCEED_TO_CHECKOUT = By.cssSelector("[name='proceedToRetailCheckout'], #sc-buy-box-ptc-button");
}
