package com.automation.core;

import com.automation.pages.amazon.AmazonCartPage;
import com.automation.pages.amazon.AmazonHomePage;
import com.automation.pages.amazon.AmazonProductPage;
import com.automation.pages.amazon.AmazonSearchResultsPage;
import com.automation.pages.newtours.RegistrationConfirmationPage;
import com.automation.pages.newtours.RegistrationPage;
import org.openqa.selenium.WebDriver;

public class PageManager {
    private final WebDriver webDriver;

    public PageManager(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public RegistrationPage getRegistrationPage() {
        return new RegistrationPage(webDriver);
    }

    public RegistrationConfirmationPage getRegistrationConfirmationPage() {
        return new RegistrationConfirmationPage(webDriver);
    }

    public AmazonHomePage getAmazonHomePage() {
        return new AmazonHomePage(webDriver);
    }

    public AmazonSearchResultsPage getAmazonSearchResultsPage() {
        return new AmazonSearchResultsPage(webDriver);
    }

    public AmazonProductPage getAmazonProductPage() {
        return new AmazonProductPage(webDriver);
    }

    public AmazonCartPage getAmazonCartPage() {
        return new AmazonCartPage(webDriver);
    }
}
