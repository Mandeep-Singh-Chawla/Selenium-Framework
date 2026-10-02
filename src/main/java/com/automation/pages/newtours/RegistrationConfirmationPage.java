package com.automation.pages.newtours;

import com.automation.core.BasePage;

import com.automation.locators.newtours.RegistrationConfirmationPageElement;
import com.automation.utils.Log;
import org.openqa.selenium.WebDriver;

import static com.automation.utils.Utilities.addAttachment;

public class RegistrationConfirmationPage extends BasePage {

    public RegistrationConfirmationPage(WebDriver webDriver) {
        super(webDriver);
    }

    public String getConfTextValue() {
        addAttachment("Registration confirmation", webDriver);
        String confirmation = text(RegistrationConfirmationPageElement.CONFIRMATION_TEXT);
        Log.info("Confirmation text: " + confirmation, webDriver);
        return confirmation;
    }
}
