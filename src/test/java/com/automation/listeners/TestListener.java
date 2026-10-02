package com.automation.listeners;

import com.automation.tests.BaseTest;
import com.automation.tests.RetryAnalyzer;
import com.automation.utils.Log;
import com.automation.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener implements ITestListener, IAnnotationTransformer {

    @Override
    public void onTestFailure(ITestResult result) {
        Log.error("Test failed: " + result.getName(), result.getThrowable());
        attachDiagnostics(result, "Failure");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Log.warn("Test skipped: " + result.getName());
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

    private void attachDiagnostics(ITestResult result, String label) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest)) {
            return;
        }
        WebDriver webDriver = ((BaseTest) instance).getWebDriver();
        if (webDriver == null) {
            return;
        }
        Utilities.addAttachment(label + " - " + result.getName(), webDriver);
        Utilities.captureBrowserLogs("browser", webDriver);
    }
}
