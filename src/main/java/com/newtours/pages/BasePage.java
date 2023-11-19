package com.newtours.pages;

import com.newtours.utils.Log;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class BasePage {

    WebDriver webDriver;

    BasePage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    @Step("Step to open the web URL")
    public void goTo (String url){
        Log.info("inside goTo - opening URL - "+url, webDriver);
        webDriver.get(url);
    }

}
