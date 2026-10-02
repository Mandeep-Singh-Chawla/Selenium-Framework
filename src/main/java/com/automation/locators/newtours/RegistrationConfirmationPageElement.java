package com.automation.locators.newtours;

import org.openqa.selenium.By;

public final class RegistrationConfirmationPageElement {

    private RegistrationConfirmationPageElement() {
    }

    public static final By CONFIRMATION_TEXT = By.xpath("//b[contains(text(),\"Note\")]");
    public static final By FLIGHTS_LINK = By.linkText("Flights");
}
