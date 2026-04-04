# Allure Report Setup & Usage Guide

## 📊 What is Allure Report?

Allure Report is a flexible lightweight multi-language test report tool that provides clear representation of what was tested. It allows to represent results of test execution in a neat web-based report form.

---

## 🚀 Installation & Setup

### Prerequisites
- Java 11+
- Maven 3.6+
- Allure Command Line (optional, for viewing reports)

### Step 1: Maven Dependencies
Already added to `pom.xml`:
```xml
<!-- Allure Report -->
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.25.0</version>
</dependency>

<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-selenium</artifactId>
    <version>2.25.0</version>
</dependency>
```

### Step 2: Maven Plugin
Already added to `pom.xml`:
```xml
<!-- Allure Report Plugin -->
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
    <version>2.14.0</version>
</plugin>
```

### Step 3: TestNG Listener
Already added to `testng.xml`:
```xml
<listeners>
    <listener class-name="io.qameta.allure.testng.AllureTestNg"/>
</listeners>
```

---

## 🎯 Running Tests with Allure Report

### Option 1: Maven Command
```bash
mvn clean test
```

This will:
1. Execute all tests
2. Generate Allure results in `target/allure-results/`
3. Create test logs and screenshots

### Option 2: Generate & View Report
```bash
# Generate the report
mvn allure:report

# Serve the report (requires Allure CLI)
allure serve target/allure-results
```

---

## 📁 Report Output

After running tests, files are generated in:
```
target/
├── allure-results/          ← Raw test results
│   ├── *.json              ← Test result data
│   ├── *.txt               ← Test logs
│   └── *.png               ← Screenshots
├── allure-report/          ← HTML report (after mvn allure:report)
│   ├── index.html          ← Main report
│   ├── data/
│   └── static/
```

---

## 📝 Allure Annotations Used

### In LoginTest.java:

```java
@Feature("Login Functionality")
// Groups tests by feature

@Severity(SeverityLevel.CRITICAL)
// Marks test severity level (BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL)

@Test(dataProvider = "jsonDataProvider")
// Data-driven test annotation

Allure.feature("Login");
// Programmatic feature assignment

Allure.story(testId + ": " + description);
// Programmatic story assignment

Allure.description("Testing login with different credentials scenarios");
// Programmatic description
```

---

## 🎨 Report Features

Allure Report provides:

✅ **Test Execution Timeline** - Visual timeline of test execution
✅ **Test History** - Track test results over time
✅ **Screenshots & Artifacts** - Attach screenshots on failure
✅ **Test Parameters** - Data-driven test parameters display
✅ **Test Steps** - Detailed step-by-step execution
✅ **Failure Analysis** - Error messages and stack traces
✅ **Metrics** - Pass/fail statistics
✅ **Behaviors** - Test organized by features and stories

---

## 📊 Report Navigation

### Overview Page
- Overall statistics
- Timeline of execution
- Pass/Fail ratio
- Defects

### Behaviors Tab
- Organized by Feature and Story
- Shows which tests cover which features

### Test Results Tab
- Detailed test result information
- Steps and parameters
- Logs and attachments

### Graphs Tab
- Pass/Fail statistics
- Severity distribution
- Test duration

---

## 💾 Attaching Screenshots & Logs

Currently, screenshots are automatically attached on failure in `BaseTest.java`:

```java
@AfterMethod
public void teardown(ITestResult result) {
    if (ITestResult.FAILURE == result.getStatus()) {
        logger.error("Test failed: " + result.getName());
        takeScreenshot(result.getName());
    }
    // ...
}
```

These screenshots appear in the Allure report as attachments.

---

## 🔧 Troubleshooting

### Issue: "Allure command not found"
**Solution:** Install Allure CLI from: https://docs.qameta.io/allure/#_installing_a_local_copy

### Issue: "Report not generated"
**Solution:** Ensure Maven ran successfully and check `target/allure-results/` folder

### Issue: "No screenshots in report"
**Solution:** Check that tests failed and screenshots were taken in `test-output/screenshots/`

---

## 📚 Example Report URL

After running `allure serve`:
```
http://localhost:4040
```

---

## 🚀 Quick Start

1. **Run tests with Allure:**
   ```bash
   mvn clean test
   ```

2. **View the report:**
   ```bash
   allure serve target/allure-results
   ```

3. **Report opens at:** `http://localhost:4040`

---

## 🎓 Best Practices

✅ Always use `@Feature` and `@Story` annotations
✅ Use `@Severity` to mark critical tests
✅ Add descriptive `@Description`
✅ Use `Allure.step()` for detailed steps
✅ Attach screenshots on failure
✅ Keep test names descriptive
✅ Use data-driven approach for test parameters

---

**Allure Report Version:** 2.25.0  
**Last Updated:** April 2, 2026
