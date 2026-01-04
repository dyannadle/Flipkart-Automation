package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    // Robust Xpath: Find any DIV that contains the text "iPhone" and has a
    // reasonable length,
    // OR matches common Flipkart product classes.
    private By productTitles = By.xpath("//div[contains(@class, 'KzDlHZ') or contains(@class, 'wjcEIp')]");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectFirstProduct() {
        try {
            // Wait for main class
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitles));
            List<WebElement> products = driver.findElements(productTitles);

            if (!products.isEmpty()) {
                products.get(0).click();
                return;
            }
        } catch (Exception e) {
            // Fallback: Use a text-based locator if classes change
            System.out.println("Standard locators failed, trying text-based...");
        }

        // Fallback Strategy
        try {
            By textLocator = By.xpath("//div[contains(text(), 'iPhone') and string-length(text()) > 10]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(textLocator));
            driver.findElement(textLocator).click();
        } catch (Exception e) {
            throw new RuntimeException("Product not found in search results with any locator strategy.");
        }
    }
}
