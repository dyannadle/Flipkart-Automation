package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By searchBox = By.name("q");
    private By closeLoginPopupButton = By.xpath("//span[@role='button']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToFlipkart() {
        driver.get("https://www.flipkart.com");
    }

    public void closeLoginPopupIfPresent() {
        try {
            WebElement closeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(closeLoginPopupButton));
            closeBtn.click();
        } catch (Exception e) {
            // Popup not present, ignore
        }
    }

    public void searchForProduct(String productName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        searchInput.sendKeys(productName);
        searchInput.sendKeys(Keys.RETURN);
    }
}
