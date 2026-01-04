package com.example;

import com.example.base.BaseTest;
import com.example.pages.HomePage;
import com.example.pages.ProductPage;
import com.example.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FlipkartSearchTest extends BaseTest {

    @Test
    public void testSearchProduct() {
        // 1. Initialize Page Objects
        HomePage homePage = new HomePage(driver);
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        ProductPage productPage = new ProductPage(driver);

        // 2. Navigate and Search
        homePage.navigateToFlipkart();
        homePage.closeLoginPopupIfPresent();
        homePage.searchForProduct("iPhone 15");

        // 3. Select Product
        searchResultsPage.selectFirstProduct();

        // 4. Verification (Window Handling)
        productPage.switchToProductTab();

        String title = productPage.getProductTitle();
        System.out.println("Product Page Title: " + title);

        // Robust check: ensure it's a product page and contains relevant text
        Assert.assertTrue(title.toLowerCase().contains("iphone 15") || title.toLowerCase().contains("apple"),
                "Page title should contain product name");
    }
}
