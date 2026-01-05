# Flipkart Automation Framework - Project Documentation

## 📋 Table of Contents
1. [Project Overview](#project-overview)
2. [Project Structure](#project-structure)
3. [Dependencies](#dependencies)
4. [Framework Architecture](#framework-architecture)
5. [How It Works](#how-it-works)
6. [Running Tests](#running-tests)

---

## 🎯 Project Overview

This is a **Selenium-based automation framework** for testing Flipkart's e-commerce website. The framework follows the **Page Object Model (POM)** design pattern and uses **TestNG** as the testing framework.

**Purpose:** Automate end-to-end testing of Flipkart's product search functionality, including:
- Navigating to Flipkart
- Handling login popups
- Searching for products
- Verifying search results
- Handling multiple browser windows/tabs

---

## 📁 Project Structure

```
Flipkart-Automation/
│
├── pom.xml                          # Maven configuration file
│
└── src/
    ├── main/java/com/example/
    │   └── pages/                   # Page Object Model classes
    │       ├── HomePage.java        # Flipkart home page actions
    │       ├── SearchResultsPage.java   # Search results page actions
    │       └── ProductPage.java     # Product details page actions
    │
    └── test/java/com/example/
        ├── base/
        │   └── BaseTest.java        # Base test class with setup/teardown
        │
        └── FlipkartSearchTest.java  # Main test class
```

---

## 📦 Dependencies

All dependencies are managed through Maven in `pom.xml`:

### 1. **Selenium Java (v4.25.0)**
- **Artifact:** `org.seleniumhq.selenium:selenium-java`
- **Purpose:** Core library for browser automation
- **Why:** Provides WebDriver API to control Chrome browser, find elements, and interact with web pages
- **How it works:** 
  - Uses Selenium Manager to auto-download ChromeDriver
  - Provides APIs like `WebDriver`, `WebElement`, `By` locators
  - Handles browser interactions (click, type, navigate, etc.)

### 2. **TestNG (v7.9.0)**
- **Artifact:** `org.testng:testng`
- **Scope:** `test` (only used during testing)
- **Purpose:** Testing framework for organizing and running tests
- **Why:** Provides annotations like `@Test`, `@BeforeMethod`, `@AfterMethod` for test lifecycle management
- **How it works:**
  - `@BeforeMethod`: Runs before each test (sets up browser)
  - `@Test`: Marks methods as test cases
  - `@AfterMethod`: Runs after each test (closes browser)
  - Provides assertions for validation

### 3. **Maven Compiler Plugin (v3.11.0)**
- **Purpose:** Compiles Java source code
- **Configuration:** Set to use Java 17

### 4. **Maven Surefire Plugin (v3.2.5)**
- **Purpose:** Executes TestNG tests during Maven build
- **Configuration:** Runs all files matching `**/*Test.java` pattern

---

## 🏗️ Framework Architecture

### **Page Object Model (POM) Design Pattern**

The framework uses POM to separate test logic from page-specific code:

```
┌─────────────────────┐
│   FlipkartSearchTest │  ← Test Layer (What to test)
└──────────┬──────────┘
           │ uses
           ▼
┌─────────────────────┐
│   BaseTest          │  ← Base Layer (Setup/Teardown)
└─────────────────────┘
           │ provides WebDriver
           ▼
┌─────────────────────┐
│   Page Objects      │  ← Page Layer (How to interact)
│  - HomePage         │
│  - SearchResultsPage│
│  - ProductPage      │
└─────────────────────┘
```

**Benefits:**
- ✅ **Maintainability:** If Flipkart changes UI, only update page classes
- ✅ **Reusability:** Page methods can be used across multiple tests
- ✅ **Readability:** Tests read like user actions, not technical code
- ✅ **Reduced duplication:** Locators defined once, used everywhere

---

## ⚙️ How It Works

### **1. BaseTest.java** (Test Foundation)

**Purpose:** Provides common setup and teardown for all tests

**Key Components:**
```java
protected WebDriver driver;        // Browser controller
protected WebDriverWait wait;      // Explicit wait utility
```

**Lifecycle:**
- `@BeforeMethod setUp()`: 
  - Creates ChromeDriver instance
  - Maximizes browser window
  - Sets implicit wait (5 seconds)
  - Creates WebDriverWait (10 seconds)
  
- `@AfterMethod tearDown()`:
  - Closes browser and cleans up resources

**Why it's needed:** Ensures every test starts with a fresh browser and cleans up afterward

---

### **2. HomePage.java** (Home Page Actions)

**Purpose:** Encapsulates all actions on Flipkart's home page

**Locators:**
- `searchBox`: `By.name("q")` - Search input field
- `closeLoginPopupButton`: `By.xpath("//span[@role='button']")` - Login popup close button

**Methods:**

| Method | Purpose | How It Works |
|--------|---------|--------------|
| `navigateToFlipkart()` | Opens Flipkart | Uses `driver.get()` |
| `closeLoginPopupIfPresent()` | Dismisses login modal | Waits for close button, clicks it, ignores if not present |
| `searchForProduct(String)` | Searches for product | Waits for search box, types product name, presses Enter |

**Key Features:**
- Uses `WebDriverWait` for reliable element detection
- Handles exceptions gracefully (popup may not always appear)

---

### **3. SearchResultsPage.java** (Search Results Actions)

**Purpose:** Handles interactions on search results page

**Locators:**
- `productTitles`: `By.xpath("//div[contains(@class, 'KzDlHZ') or contains(@class, 'wjcEIp')]")`
  - Targets Flipkart's product title divs using class names

**Methods:**

| Method | Purpose | How It Works |
|--------|---------|--------------|
| `selectFirstProduct()` | Clicks first search result | 1. Waits for products to load<br>2. Finds all matching elements<br>3. Clicks first one<br>4. Has fallback text-based locator if classes change |

**Robust Locator Strategy:**
- **Primary:** Uses Flipkart's CSS classes
- **Fallback:** Uses text-based XPath if classes change
- **Why:** Flipkart may update CSS classes, this ensures tests don't break

---

### **4. ProductPage.java** (Product Details Actions)

**Purpose:** Handles product page interactions and window switching

**Methods:**

| Method | Purpose | How It Works |
|--------|---------|--------------|
| `switchToProductTab()` | Switches to new tab | 1. Gets original window handle<br>2. Gets all window handles<br>3. Switches to the new one |
| `getProductTitle()` | Returns page title | Waits for title to load, returns it |

**Why Window Handling?**
- Flipkart opens product pages in new tabs
- Must switch context to interact with new tab

---

### **5. FlipkartSearchTest.java** (Main Test)

**Purpose:** Orchestrates the complete test flow

**Test Flow:**
```
1. Initialize page objects (HomePage, SearchResultsPage, ProductPage)
2. Navigate to Flipkart
3. Close login popup if present
4. Search for "iPhone 15"
5. Click first search result
6. Switch to product tab
7. Verify product title contains "iphone 15" or "apple"
```

**Assertions:**
```java
Assert.assertTrue(
    title.toLowerCase().contains("iphone 15") || 
    title.toLowerCase().contains("apple"),
    "Page title should contain product name"
);
```

**Why this assertion?**
- Flexible: Accepts variations in product titles
- Robust: Doesn't break if exact title changes

---

## 🚀 Running Tests

### **Option 1: Using Maven (Recommended)**

```bash
# Run all tests
mvn clean test

# Run specific test
mvn test -Dtest=FlipkartSearchTest
```

**What happens:**
1. Maven compiles Java code
2. Surefire plugin finds `FlipkartSearchTest.java`
3. TestNG executes the test
4. Results shown in console

### **Option 2: Using IDE (IntelliJ/Eclipse)**

1. Right-click on `FlipkartSearchTest.java`
2. Select "Run as TestNG Test"

### **Expected Output:**

```
Browser started successfully.
Navigated to https://www.flipkart.com
Login pop-up closed.
Product Page Title: Apple iPhone 15 (128 GB) - Black
PASSED: testSearchProduct
```

---

## 🔍 Key Concepts Explained

### **Implicit vs Explicit Waits**

**Implicit Wait (in BaseTest):**
```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
```
- Waits up to 5 seconds for ANY element to appear
- Applied globally to all `findElement()` calls

**Explicit Wait (in Page Objects):**
```java
wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
```
- Waits for SPECIFIC condition on SPECIFIC element
- More reliable for dynamic content

### **Locator Strategies**

| Strategy | Example | When to Use |
|----------|---------|-------------|
| `By.name()` | `By.name("q")` | For form inputs with name attribute |
| `By.xpath()` | `By.xpath("//span[@role='button']")` | For complex element selection |
| `By.className()` | `By.className("product")` | For elements with specific class |

### **Window Handling**

Flipkart opens products in new tabs. To interact:
```java
String originalWindow = driver.getWindowHandle();  // Save original
Set<String> allWindows = driver.getWindowHandles(); // Get all tabs
// Switch to new tab
driver.switchTo().window(newWindowHandle);
```

---

## 📝 Summary

**What this project does:**
- Automates Flipkart product search testing

**Why it's structured this way:**
- POM pattern for maintainability
- BaseTest for code reuse
- Robust locators for stability

**How to extend:**
1. Add new page classes for other pages
2. Add new test methods in FlipkartSearchTest
3. Create new test classes extending BaseTest

**Dependencies:**
- Selenium: Browser automation
- TestNG: Test framework
- Maven: Build and dependency management

---

## 🎓 Learning Resources

- **Selenium Docs:** https://www.selenium.dev/documentation/
- **TestNG Docs:** https://testng.org/doc/documentation-main.html
- **POM Pattern:** https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/

---

*Last Updated: January 2026*
