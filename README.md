# 📚 Library REST API Automation with Rest Assured

This project is a comprehensive API automation testing framework for a fictional **Library Management System**. Built using **REST Assured**, **TestNG**, **Maven**, and integrated with **Allure Reports** and **Jenkins CI/CD**, it demonstrates industry-level practices in API testing.

---

## ✨ Highlights

- ✅ **Modular Test Structure**: Separated test packages for `books`, `users`, `wishlists`, and `household` modules.
- 🔐 **Authentication Testing**: Covers token-based login and session management.
- ⚙️ **Request Reusability**: Uses Request Specifications and POJOs for clean and maintainable code.
- 🔁 **Retry Logic**: Automatic test retry logic for flaky scenarios using `RetryAnalyzer`.
- 📊 **Allure Reporting**: Rich, interactive test reports with step-level logs and visual charts.
- 🤖 **CI/CD Integrated**: Jenkins pipeline to run tests automatically on push or pull requests.

---

## 🧪 Test Coverage

| Module         | Endpoint Examples                                 | Covered Operations            |
|----------------|---------------------------------------------------|-------------------------------|
| **Books**      | `/books`, `/books/{id}`                           | POST, GET, PUT, PATCH, DELETE |
| **Users**      | `/users`, `/users/{id}`                           | Full CRUD + edge cases        |
| **Wishlists**  | `/wishList`, `/wishList/{id}`                     | Positive/Negative testing     |
| **Household**  | `/houseHolding`, `/houseHolding/{id}`             | Partial and full updates      |
| **Auth**       | `/login`                                          | Valid and invalid credentials |

---

## 📂 Technology Stack

- 🧪 **Rest Assured**: Core library for HTTP-based automation
- 🧪 **TestNG**: Testing framework with lifecycle and assertions
- 💡 **Java** (JDK 11)
- ☕ **Maven**: Dependency and build management
- 💼 **Jenkins**: CI/CD pipeline setup
- 📊 **Allure Reports**: For beautiful and informative test result visualization
- 📦 **JSON Server**: Lightweight mock API server

---

## 🚀 How to Run Locally

### 🔧 Prerequisites
- Java 11+
- Maven 3.6+
- Node.js (for JSON Server)
- Git

### 🏗️ Setup

```bash
# Clone the repo
git clone https://github.com/ToubaSlam/Library_RESTAssured.git
cd Library_RESTAssured

# Install and start mock server
npm install -g json-server
json-server --watch db.json --port 3000
