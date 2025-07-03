# Library REST Assured Tests

This project uses TestNG for executing REST API tests. The active suite definitions reside under [`src/test/resources/suites`](src/test/resources/suites).

Use `testng.xml` in the project root to run all maintained suites together:

```bash
mvn test -DsuiteXmlFile=testng.xml
```

Each suite file in the `suites` directory can also be run individually if needed.
