package com.newtours.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrationPageElement {

    WebDriver webDriver;
    public RegistrationPageElement(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public WebElement getFirstNameTxt() {
        return webDriver.findElement(By.name("firstName"));
    }

    public WebElement getLastNameTxt() {
        return webDriver.findElement(By.name("lastName"));
    }

    public WebElement getPhoneTxt() {
        return webDriver.findElement(By.name("phone"));
    }

    public WebElement getEmailTxt() {
        return webDriver.findElement(By.name("userName"));
    }

    public WebElement getUsernameTxt() {
        return webDriver.findElement(By.name("email"));
    }

    public WebElement getPasswordTxt() {
        return webDriver.findElement(By.name("password"));
    }

    public WebElement getConfirmPasswordTxt() {
        return webDriver.findElement(By.name("confirmPassword"));
    }

    public WebElement getSubmitBtn() {
        return webDriver.findElement(By.name("submit"));
    }

}
