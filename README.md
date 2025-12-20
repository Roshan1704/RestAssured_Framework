# REST Assured API Automation Framework

A scalable and maintainable **API automation framework** built using **Rest Assured**, designed to validate RESTful services with a focus on **reliability, reusability, and CI/CD readiness**.

This framework is structured to support **real-world API testing needs**, not just demo scenarios.

---

## 🎯 Purpose

Modern systems rely heavily on APIs. This framework is built to:

- Validate **business-critical APIs**
- Detect failures **early in the pipeline**
- Support **regression, contract, and negative testing**
- Scale across multiple services and environments

It is designed with **clean separation of concerns** so test logic remains readable and maintainable as the test suite grows.

---

## 🧠 Key Design Principles

- **Modular architecture** – easy to extend and maintain  
- **Reusable request/response components**  
- **Centralized configuration management**  
- **Clear assertion strategy**  
- **CI/CD friendly execution**  

This is not a collection of test scripts — it’s a **test framework**.

---

## 🏗 Framework Architecture

```text
src
 └── test
     ├── base
     │   └── BaseTest.java          # Common setup & configuration
     ├── client
     │   └── ApiClient.java         # Reusable request handling
     ├── endpoints
     │   └── Routes.java            # API endpoints/constants
     ├── payloads
     │   └── RequestBodyBuilder.java
     ├── tests
     │   └── UserApiTests.java      # Test scenarios
     └── utils
         ├── ConfigReader.java
         └── TestDataUtil.java
```

This structure keeps:
- **Test logic clean**
- **HTTP handling reusable**
- **Configuration centralized**

---

## 🛠 Tech Stack

- **Language:** Java  
- **API Automation:** Rest Assured  
- **Test Runner:** TestNG  
- **Build Tool:** Maven  
- **Assertions:** TestNG / Hamcrest  
- **Configuration:** Properties-based  

---

## 🔍 What This Framework Covers

- ✅ CRUD API validation  
- ✅ Positive & negative test scenarios  
- ✅ Response status & schema validation  
- ✅ Header & payload validation  
- ✅ Data-driven testing support  
- ✅ Environment-based execution  

---

## 🚀 How to Run Tests

### Prerequisites
- Java 8+
- Maven installed

### Execute tests
```bash
mvn clean test
```

Tests can be easily integrated into:
- Jenkins
- GitHub Actions
- Any CI/CD pipeline

---

## 🔄 CI/CD Ready

The framework is designed to:
- Run headless
- Produce deterministic results
- Fail fast on API issues
- Fit naturally into automated pipelines

It supports **shift-left API testing**, enabling faster feedback during development.

---

## 📈 Why This Framework Exists

Many API automation setups fail because they:
- Mix test logic with HTTP logic
- Become hard to maintain over time
- Don’t scale across services

This framework focuses on **long-term maintainability** and **clarity**, making it suitable for teams working on complex, API-driven systems.

---

## 🧩 Future Enhancements

- Contract testing support
- Advanced reporting
- Authentication strategy extensions
- Parallel execution optimization

---

## 👤 Author

**Roshan Singh**  
QA Lead | Automation | API | CI/CD  

🔗 LinkedIn: https://www.linkedin.com/in/roshan-singh17  

---

> _“API quality is the foundation of system reliability.”_
