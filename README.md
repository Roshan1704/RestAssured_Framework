# RestAssured API Automation Framework

A production-ready, enterprise-level REST API automation framework built with RestAssured, TestNG, and comprehensive reporting capabilities.

## Features

- **RestAssured** for API testing with fluent interface
- **TestNG** for test management and parallel execution
- **Dual Reporting**: Allure Reports & ExtentReports
- **Multi-source Test Data**: JSON, Excel, YAML support
- **Database Validation**: JDBC utilities for MySQL/PostgreSQL
- **POJO Models** with Lombok for clean code
- **Custom Listeners** for enhanced logging and reporting
- **Retry Mechanism** for flaky tests
- **Page Object Model** pattern for maintainability
- **Comprehensive Utilities** for common operations

## Project Structure

```
restassured-framework/
├── src/test/java/com/api/automation/
│   ├── base/
│   │   └── BaseTest.java                  # Base test class with common setup
│   ├── client/
│   │   └── ApiClient.java                 # Fluent API client for HTTP requests
│   ├── config/
│   │   └── ConfigManager.java             # Configuration management
│   ├── endpoints/
│   │   └── Routes.java                    # API endpoint constants
│   ├── models/
│   │   ├── User.java                      # User POJO
│   │   ├── UserResponse.java              # User response POJO
│   │   ├── UserListResponse.java          # User list response POJO
│   │   ├── AuthRequest.java               # Authentication request POJO
│   │   └── AuthResponse.java              # Authentication response POJO
│   ├── payloads/
│   │   └── UserPayload.java               # Request payload builders
│   ├── utils/
│   │   ├── JsonUtil.java                  # JSON parsing utilities
│   │   ├── YamlUtil.java                  # YAML parsing utilities
│   │   ├── ExcelUtil.java                 # Excel data utilities
│   │   ├── DatabaseUtil.java              # Database operations
│   │   ├── ResponseValidator.java         # Response validation methods
│   │   ├── DateTimeUtil.java              # Date/time utilities
│   │   └── LoggerUtil.java                # Logging utilities
│   ├── dataproviders/
│   │   └── TestDataProvider.java          # TestNG data providers
│   ├── listeners/
│   │   ├── ExtentReportListener.java      # ExtentReports listener
│   │   ├── AllureListener.java            # Allure listener
│   │   ├── TestListener.java              # Custom test listener
│   │   └── RetryAnalyzer.java             # Retry logic for failed tests
│   ├── helpers/
│   │   └── AllureReportHelper.java        # Allure reporting helper
│   └── tests/
│       ├── UserApiTests.java              # User API test cases
│       ├── AuthenticationTests.java       # Authentication test cases
│       └── DataDrivenTests.java           # Data-driven test examples
├── src/test/resources/
│   ├── config.properties                   # Framework configuration
│   ├── allure.properties                   # Allure configuration
│   ├── log4j2.xml                         # Logging configuration
│   └── testdata/
│       ├── config.yml                     # YAML configuration
│       ├── users.json                     # JSON test data
│       └── testdata.xlsx                  # Excel test data
├── pom.xml                                 # Maven dependencies
├── testng.xml                              # TestNG suite configuration
└── README.md                               # Project documentation
```

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6+
- Allure Command Line (optional, for Allure reports)

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd restassured-framework
```

### 2. Install Dependencies

```bash
mvn clean install -DskipTests
```

### 3. Configure Framework

Edit `src/test/resources/config.properties`:

```properties
base.uri=https://reqres.in
base.path=/api
environment=QA
request.timeout=30000
retry.count=2
