package com.automation.pages.amazon;

import com.automation.core.BasePage;

import com.automation.locators.amazon.AmazonHomePageElement;
import com.automation.utils.Config;
import com.automation.utils.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

import static com.automation.utils.Utilities.addAttachment;

public class AmazonHomePage extends BasePage {

    public AmazonHomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Open Amazon")
    public void open() {
        goTo(Config.get("url.amazon"));
        Log.info("Waiting for the Amazon search box", webDriver);
        visible(AmazonHomePageElement.SEARCH_BOX);
        addAttachment("Amazon homepage", webDriver);
    }

    @Step("Search for {searchTerm}")
    public void searchProduct(String searchTerm) {
        Log.info("Searching for " + searchTerm, webDriver);
        type(AmazonHomePageElement.SEARCH_BOX, searchTerm);
        click(AmazonHomePageElement.SEARCH_SUBMIT);
        addAttachment("Search submitted for " + searchTerm, webDriver);
    }
}
