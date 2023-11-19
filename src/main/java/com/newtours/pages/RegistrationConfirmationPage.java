package com.newtours.pages;

import com.newtours.elements.RegistrationConfirmationPageElement;
import com.newtours.utils.Log;
import org.openqa.selenium.WebDriver;

import static com.newtours.utils.Utilities.addAttachment;

public class RegistrationConfirmationPage extends BasePage{

    private RegistrationConfirmationPageElement registrationConfirmationPageElement;

    public RegistrationConfirmationPage(WebDriver webDriver) {
        super(webDriver);
        this.registrationConfirmationPageElement = new RegistrationConfirmationPageElement(webDriver);
    }

    public String getConfTextValue(){
        addAttachment("ScreenShot on Registration Conf page", webDriver);
        Log.info("inside getConfTextValue - after attach");
        return registrationConfirmationPageElement.getConfText().getText();
    }
}
