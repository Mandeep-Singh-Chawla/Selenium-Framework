package com.automation.pages.newtours;

import com.automation.core.BasePage;

import com.automation.locators.newtours.RegistrationConfirmationPageElement;
import com.automation.locators.newtours.RegistrationPageElement;
import com.automation.utils.Config;
import com.automation.utils.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static com.automation.utils.Utilities.addAttachment;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Open registration page")
    public void open() {
        goTo(Config.get("url.registration"));
        Log.info("Waiting for the registration form", webDriver);
        visible(RegistrationPageElement.FIRST_NAME);
        addAttachment("Registration page opened", webDriver);
    }

    @Step("Enter contact information")
    public void enterContactInfo(String firstName, String lastName, String phone, String email) {
        Log.info("Entering contact information", webDriver);
        type(RegistrationPageElement.FIRST_NAME, firstName);
        type(RegistrationPageElement.LAST_NAME, lastName);
        type(RegistrationPageElement.PHONE, phone);
        type(RegistrationPageElement.EMAIL, email);
        addAttachment("Contact information entered", webDriver);
    }

    @Step("Enter user information")
    public void enterUserInfo(String username, String password, String confirmPassword) {
        Log.info("Entering user information", webDriver);
        type(RegistrationPageElement.USERNAME, username);
        type(RegistrationPageElement.PASSWORD, password);
        type(RegistrationPageElement.CONFIRM_PASSWORD, confirmPassword);
        addAttachment("User information entered", webDriver);
    }

    @Step("Submit registration")
    public void submitInfo() {
        Log.info("Submitting registration", webDriver);
        click(RegistrationPageElement.SUBMIT);
        visible(RegistrationConfirmationPageElement.CONFIRMATION_TEXT);
    }
}
