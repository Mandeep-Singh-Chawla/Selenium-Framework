package com.automation.tests;

import com.automation.core.PageManager;
import com.automation.utils.ExcelTestData;
import com.automation.utils.Log;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AmazonOrderTest extends BaseTest {

    @DataProvider(name = "amazonData", parallel = true)
    public static Object[][] amazonData() {
        return ExcelTestData.load("testdata/amazon.xlsx", "searchTerm");
    }

    @Test(dataProvider = "amazonData")
    @Description("Search Amazon, add a product to the cart, and confirm checkout is available")
    @Severity(SeverityLevel.CRITICAL)
    public void testPlaceOrderOnAmazon(String searchTerm) {
        Log.info("Start Amazon order flow for " + searchTerm);
        PageManager pageManager = new PageManager(getWebDriver());

        pageManager.getAmazonHomePage().open();
        pageManager.getAmazonHomePage().searchProduct(searchTerm);
        pageManager.getAmazonSearchResultsPage().addFirstAvailableProductToCart();

        Assert.assertTrue(pageManager.getAmazonSearchResultsPage().isAddedToCart(),
                "Product should be added to the cart");

        pageManager.getAmazonSearchResultsPage().goToCart();
        Assert.assertTrue(pageManager.getAmazonCartPage().hasItemsInCart(),
                "Cart should contain at least one item");
        Assert.assertTrue(pageManager.getAmazonCartPage().isProceedToCheckoutVisible(),
                "Proceed to checkout should be available");
    }
}
