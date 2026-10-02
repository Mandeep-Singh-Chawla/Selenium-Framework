package com.automation.locators.newtours;

import org.openqa.selenium.By;

public final class RegistrationPageElement {

    private RegistrationPageElement() {
    }

    public static final By FIRST_NAME = By.name("firstName");
    public static final By LAST_NAME = By.name("lastName");
    public static final By PHONE = By.name("phone");
    public static final By EMAIL = By.name("userName");
    public static final By USERNAME = By.name("email");
    public static final By PASSWORD = By.name("password");
    public static final By CONFIRM_PASSWORD = By.name("confirmPassword");
    public static final By SUBMIT = By.name("submit");
}
