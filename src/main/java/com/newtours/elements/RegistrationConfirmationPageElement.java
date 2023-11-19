package com.newtours.elements;

import com.newtours.utils.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationConfirmationPageElement {

    WebDriver webDriver;

    public RegistrationConfirmationPageElement(WebDriver webDriver){
        this.webDriver = webDriver;
    }
    public WebElement getConfText() {
        return webDriver.findElement(By.xpath("//b[contains(text(),\"Note\")]"));
    }

    public WebElement getFlightsLink() {
        return webDriver.findElement(By.linkText("Flights"));
    }
}
