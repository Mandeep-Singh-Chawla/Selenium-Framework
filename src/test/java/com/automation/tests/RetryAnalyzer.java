package com.automation.tests;

import com.automation.utils.Config;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempts;

    @Override
    public boolean retry(ITestResult result) {
        int maxAttempts = Config.getInt("retry.count");
        if (attempts < maxAttempts) {
            attempts++;
            return true;
        }
        return false;
    }
}
