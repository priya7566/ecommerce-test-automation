# 🛒 E-Commerce Checkout Automation Framework

> **BDD Test Automation Framework** for validating end-to-end checkout flows on an e-commerce platform using Selenium, Cucumber, Java, and Jenkins CI/CD.

---

## 📌 Project Overview

| Attribute     | Detail |
|---------------|--------|
| **Domain**    | E-Commerce / Retail |
| **Type**      | Test Automation Framework (BDD) |
| **AUT**       | SauceDemo (https://www.saucedemo.com) |
| **Approach**  | Page Object Model (POM) + BDD |
| **CI/CD**     | Jenkins Declarative Pipeline |

---

## 🛠️ Technology Stack

| Technology         | Version  | Purpose |
|--------------------|----------|---------|
| Java               | 11+      | Core language |
| Selenium WebDriver | 4.15.0   | Browser automation |
| Cucumber           | 7.14.0   | BDD / Gherkin scenarios |
| TestNG             | 7.8.0    | Test execution engine |
| Maven              | 3.9+     | Build & dependency management |
| Jenkins            | 2.x LTS  | CI/CD pipeline |
| WebDriverManager   | 5.6.3    | Automatic driver management |
| ExtentReports      | 5.1.1    | Rich HTML reports |
| Log4j2             | 2.21.0   | Logging |
| JavaFaker          | 1.0.2    | Test data generation |

---

## 📂 Project Structure

```
ecommerce-test-automation/
├── src/test/
│   ├── java/
│   │   ├── pages/              # Page Object Model classes
│   │   │   ├── BasePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── InventoryPage.java
│   │   │   └── CheckoutPage.java
│   │   ├── stepDefinitions/    # Cucumber step bindings
│   │   │   └── CheckoutSteps.java
│   │   ├── hooks/              # Before/After hooks
│   │   │   └── Hooks.java
│   │   ├── runners/            # TestNG + Cucumber runner
│   │   │   └── TestRunner.java
│   │   └── utils/              # Utilities
│   │       ├── DriverManager.java
│   │       └── ConfigReader.java
│   └── resources/
│       ├── features/           # Gherkin feature files
│       │   └── Checkout.feature
│       └── config/
│           ├── staging.properties
│           └── prod.properties
├── reports/                    # Generated test reports
├── Jenkinsfile                 # CI/CD pipeline definition
└── pom.xml                     # Maven dependencies
```

---

## ✅ Test Scenarios Covered

| Scenario | Tags |
|----------|------|
| Successful single-item checkout | `@smoke @happy-path` |
| Multi-product checkout (data-driven) | `@data-driven` |
| Missing first name validation | `@negative` |
| Missing zip code validation | `@negative` |
| Remove item from cart | `@cart-validation` |

---

## 🚀 Running Tests

### Locally
```bash
# Run all regression tests (Chrome, headless)
mvn test -Dtags="@regression" -Dbrowser=chrome -Dheadless=true

# Run smoke tests on Firefox
mvn test -Dtags="@smoke" -Dbrowser=firefox

# Run on specific environment
mvn test -Denv=staging -Dtags="@regression"
```

### Via Jenkins
1. Configure pipeline from `Jenkinsfile`
2. Trigger **Build with Parameters**:
   - `BROWSER`: chrome / firefox / edge
   - `ENVIRONMENT`: staging / dev / prod
   - `TAGS`: @smoke / @regression / @negative
   - `HEADLESS`: true / false

---

## 📊 Reporting

- **Cucumber HTML Report** → `reports/cucumber-html-report.html`
- **ExtentReports**        → `reports/ExtentReport.html`
- **JUnit XML**            → `reports/cucumber.xml` (parsed by Jenkins)
- **Screenshots** on failures auto-embedded in Cucumber report

---

## 🔑 Key Design Patterns

- **Page Object Model (POM)** — Separation of test logic and UI locators
- **Singleton WebDriver** — Thread-safe driver via `ThreadLocal`
- **BDD with Gherkin** — Business-readable test scenarios
- **Data-Driven Testing** — Scenario Outlines with `Examples` tables
- **CI/CD Integration** — Parameterized Jenkins pipeline with email alerts
- **Environment Config** — Environment-specific property files

---

## 👨‍💻 Resume Bullet Points

> *"Designed and implemented a BDD Automation Framework for e-commerce checkout flows using Selenium 4, Cucumber 7, and Java 11, following Page Object Model and Singleton design patterns. Integrated with Jenkins declarative pipelines for nightly regression, parallel execution, and automated email reporting via ExtentReports."*
