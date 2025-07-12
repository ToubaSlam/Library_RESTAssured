# 📚 Library REST API Automation with Java + Rest-Assured

This project automates end-to-end API testing for a dynamic Library System. Using Java and Rest-Assured, it simulates a real-world API test environment covering CRUD operations for books, users, households, and wishlists.
---

## 🎬 Examples of Run

Take a look at a local execution demo below!  
![Local Testing Execution Example](Rec.gif)

---
## ✨ Features Covered

### ✅ Books
- **POST /books**: Create book with title, author, ISBN, and release date.
- **GET /books/:id**: Retrieve book info by ID.
- **PUT/PATCH /books/:id**: Update book fully/partially.
- **DELETE /books/:id**: Remove book entry.

### 👨‍👩‍👧 Users & Households
- **POST /users**: Register new user.
- **GET/PUT/PATCH /users/:id**: Retrieve or update user info.
- **DELETE /users/:id**: Delete user.
- **POST /households**: Add household.
- **Link** users to households and maintain associations.

### 💖 Wishlists
- **POST /wishlists**: Create wishlist for user.
- **GET /wishlists/:id**: Retrieve wishlist with embedded books.
- **PUT/PATCH /wishlists/:id**: Update wishlist info.
- **DELETE /wishlists/:id**: Remove wishlist.

---

## 🧪 Testing Techniques Used

- **Positive/Negative Test Cases**
- **JSON Schema Validation**
- **Status Code & Content-Type Validation**
- **Dynamic Data Creation** using random string/UUIDs
- **Soft & Hard Assertions**
- **Retry Mechanism** for flaky tests
- **End-to-End Relationship Validation**

---

## 🛠️ Technology Stack

- **Java** (core language)
- **Rest-Assured** (API Testing framework)
- **TestNG** (Testing framework)
- **Allure Reports** (for reporting)
- **Maven** (dependency/build management)
- **Jenkins + GitHub Actions** (CI/CD pipeline)
- **Chain API Server** (Local test server)
- **IntelliJ IDEA** (IDE)

---

## 🔧 Framework Design

- 🔸 Organized in modules: `testcases.books`, `testcases.users`, etc.
- 🔸 POJOs: Request bodies built using model classes.
- 🔸 `BaseTest`: Centralized reusable logic.
- 🔸 `SpecificationFactory`: Preconfigured Request/Response Specs.
- 🔸 All test data generated at runtime.
- 🔸 Detailed logging & report assertion.

---

## 💻 Run Tests Locally

```
cd /d "D:/Training/Library"
mvn clean verify -Denv="http://localhost:3000"
```

---

## 🚀 CI/CD Integration

### Jenkins
- Configured using Groovy pipeline.
- Step: `mvn clean verify -Denv="http://localhost:3000"`
- Archives Allure results.

### GitHub Actions
- Workflow triggers on push to master.
- YAML pipeline includes JDK setup, Maven run, and Allure generation.

---

## 📁 Repository Structure

- `/src/test/java/testcases/books/...` — Book tests
- `/src/test/java/testcases/users/...` — User tests
- `/src/test/java/utils/` — Random data generators, specs, helpers
- `/src/test/java/models/` — POJOs
- `/src/test/resources/suites/` — TestNG XML files

---

## 📊 Reports

- View Allure Report:
```
target/allure-report/index.html
```

---

## 🙌 Acknowledgments

**Special Thanks** to *Shady Ahmed Mohamed* for the mentorship and guidance.

---

## 📌 Links

- 🔗 GitHub: [Library RESTAssured Repo](https://github.com/ToubaSlam/Library_RESTAssured)
- 🗂️ Postman Collection (if needed): [Postman Docs](https://documenter.getpostman.com/view/44829933/2sB2jAa7zy)

---

### 💡 Let's raise the bar in API Automation!

#RestAssured #Java #TestNG #AllureReports #Jenkins #GitHubActions #LibraryAPI #APIAutomation #AutomationEngineer #QALife #OpenSourceTesting #IllinoisQA #MentorshipMatters
