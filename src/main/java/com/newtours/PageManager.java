package com.newtours;

import com.newtours.pages.RegistrationConfirmationPage;
import com.newtours.pages.RegistrationPage;
import org.openqa.selenium.WebDriver;

public class PageManager {
    WebDriver webDriver;

    public PageManager (WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public RegistrationPage getRegistrationPage() {
        return new RegistrationPage(webDriver);
    }

    public RegistrationConfirmationPage getRegistrationConfirmationPage() {
        return new RegistrationConfirmationPage(webDriver);
    }

}
