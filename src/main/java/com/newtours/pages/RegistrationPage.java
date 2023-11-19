package com.newtours.pages;

import com.newtours.elements.RegistrationConfirmationPageElement;
import com.newtours.elements.RegistrationPageElement;
import com.newtours.utils.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.newtours.utils.Utilities.addAttachment;

public class RegistrationPage extends BasePage{

    private RegistrationPageElement registrationPageElement;
    private RegistrationConfirmationPageElement registrationConfirmationPageElement;

    public RegistrationPage(WebDriver webDriver){
        super(webDriver);
        this.registrationPageElement = new RegistrationPageElement(webDriver);
        this.registrationConfirmationPageElement = new RegistrationConfirmationPageElement(webDriver);
    }

    @Step("Step to open the web URL")
    public void open(){
        super.goTo("https://demo.guru99.com/test/newtours/register.php");
        Log.info("Waiting for FirstName to be visible..", webDriver);
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOf(registrationPageElement.getFirstNameTxt()));
        addAttachment("ScreenShot after opening URL", webDriver);
    }

    @Step("Step to Enter the Contact info")
    public void enterContactInfo(String fname, String lname, String phone, String email){
        Log.info("inside enterContactInfo", webDriver);
        registrationPageElement.getFirstNameTxt().sendKeys(fname);
        registrationPageElement.getLastNameTxt().sendKeys(lname);
        registrationPageElement.getPhoneTxt().sendKeys(phone);
        registrationPageElement.getEmailTxt().sendKeys(email);
        addAttachment("ScreenShot enterContactInfo", webDriver);
    }

    @Step("Step to Enter the User info")
    public void enterUserInfo(String uname, String pass, String cpass){
        Log.info("inside enterUserInfo", webDriver);
        registrationPageElement.getUsernameTxt().sendKeys(uname);
        registrationPageElement.getPasswordTxt().sendKeys(pass);
        registrationPageElement.getConfirmPasswordTxt().sendKeys(cpass);
        addAttachment("ScreenShot enterUserInfo", webDriver);
    }

    @Step("Step to Click Submit")
    public void submitInfo(){
        Log.info("inside submitInfo", webDriver);
        registrationPageElement.getSubmitBtn().click();
        Log.info("Waiting for next page to be visible..", webDriver);
        new WebDriverWait(webDriver, Duration.ofSeconds(30)).until(ExpectedConditions.visibilityOf(registrationConfirmationPageElement.getConfText()));
    }
}
