# REST Assured Library Test Suite

This project contains automated API tests for a sample Library service using REST Assured and TestNG.

## Prerequisites

- **Java 17** must be installed and available on your `PATH`.
- **Apache Maven** is required to build and execute the tests.

## Running Test Suites

The project uses TestNG suites located under `src/test/resources/suites`.
You can execute a specific suite by passing it to Maven via the
`-Dsurefire.suiteXmlFiles` property.

### Positive tests

```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/suites/positiveScenarios.xml
```

### Negative tests

```bash
mvn test -Dsurefire.suiteXmlFiles=src/test/resources/suites/negativeScenarios.xml
```

## Generating Allure Report

After running tests, Allure result files will be stored in `target/allure-results`.
Generate a report with:

```bash
mvn allure:report
```

The HTML report will be available in `target/site/allure-maven-plugin/index.html`.
