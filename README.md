# 🛒 Flipkart Automation Framework

[![Java](https://img.shields.io/badge/Java-17-orange.svg?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Selenium](https://img.shields.io/badge/Selenium-4.25.0-43B02A?style=for-the-badge&logo=selenium)](https://www.selenium.dev/)
[![TestNG](https://img.shields.io/badge/TestNG-7.9.0-FF4B4B?style=for-the-badge&logo=testng)](https://testng.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9.x-C71A36?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)

---

## 📖 Overview

A high-performance, robust **Selenium-based automation framework** specifically designed for Flipkart. This project leverages the **Page Object Model (POM)** to ensure modularity, readability, and ease of maintenance for large-scale e-commerce testing suites.

## ✨ Core Features

*   **Modular Architecture**: Implements Page Object Model (POM) for clean separation of test scripts and page-specific logic.
*   **Intelligent Waits**: Combines implicit and explicit waits to handle asynchronous UI updates and lazy loading gracefully.
*   **Dynamic Tab Management**: Seamless context switching between search results and product detail pages.
*   **Fail-Safe Locators**: Uses multi-layered XPath/CSS strategies to mitigate flakiness caused by dynamic UI changes.
*   **Automated Driver Management**: Powered by Selenium Manager for zero-configuration browser setup.

## 🏗️ Project Architecture

```mermaid
graph TD
    subgraph Test Layer
        Tests[com.example]
        Base[com.example.base]
    end
    subgraph Page Layer
        Pages[com.example.pages]
    end
    
    Tests --> Base
    Tests --> Pages
    Base --> WebDriver
```

## 🚀 Getting Started

### Prerequisites
*   **JDK 17** or higher
*   **Apache Maven** 3.8+
*   **Google Chrome**

### Quick Start
1.  **Clone the Repository**
    ```bash
    git clone https://github.com/dyannadle/Flipkart-Automation.git
    cd Flipkart-Automation
    ```
2.  **Run All Tests**
    ```bash
    mvn clean test
    ```

## 🛠️ Configuration
Timeouts and browser settings can be tuned in:
*   `src/test/java/com/example/base/BaseTest.java` (Global Timeouts)
*   `pom.xml` (Dependency and Compiler versions)

## 📜 Documentation
For detailed insights into specific page implementations and troubleshooting common Selenium issues, see the [Full Project Documentation](PROJECT_DOCUMENTATION.md).

---
Developed & Maintained by **[Deepak Yannadle](https://github.com/dyannadle)**
