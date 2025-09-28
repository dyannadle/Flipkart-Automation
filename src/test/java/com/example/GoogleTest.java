package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GoogleTest {

    @Test
    public void openGoogle() {
        // With modern Selenium (4.6+), Selenium Manager should auto-download the driver.
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");
        String title = driver.getTitle();
        System.out.println("Title: " + title);
        Assert.assertTrue(title.contains("Google"));

        driver.quit();
    }
}
