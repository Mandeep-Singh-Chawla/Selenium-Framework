package com.automation.tests;

import com.automation.core.PageManager;
import com.automation.utils.CsvTestData;
import com.automation.utils.Log;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationTest extends BaseTest {

    // Guru99 keeps one shared confirmation, so parallel browsers submit one at a time.
    private static final Object SUBMIT_LOCK = new Object();

    @DataProvider(name = "registrationData", parallel = true)
    public static Object[][] registrationData() {
        return CsvTestData.load("testdata/registration.csv",
                "firstName", "lastName", "phone", "email", "username", "password");
    }

    @Test(dataProvider = "registrationData")
    @Description("Register a new user and confirm the username")
    @Severity(SeverityLevel.CRITICAL)
    public void testRegistrationFlow(String firstName, String lastName, String phone, String email,
                                     String username, String password) {
        Log.info("Start registration for " + username);
        PageManager pageManager = new PageManager(getWebDriver());
        pageManager.getRegistrationPage().open();
        pageManager.getRegistrationPage().enterContactInfo(firstName, lastName, phone, email);
        pageManager.getRegistrationPage().enterUserInfo(username, password, password);

        String actualConfirmation;
        synchronized (SUBMIT_LOCK) {
            pageManager.getRegistrationPage().submitInfo();
            actualConfirmation = pageManager.getRegistrationConfirmationPage().getConfTextValue();
        }
        String expectedConfirmation = "Note: Your user name is " + username + ".";
        Assert.assertEquals(actualConfirmation, expectedConfirmation, "Username mismatch on the confirmation page");
    }
}
