# Selenium Framework

UI tests for Maven, TestNG, and Selenium 4.49. Run them on JDK 21 or 25.

## Layout

```text
src/main/java/com/automation/core       driver, waits, page manager
src/main/java/com/automation/locators   locators by application
src/main/java/com/automation/pages      page actions by application
src/main/java/com/automation/utils      config, logging, CSV data, screenshots
src/main/resources/config               base config plus qa and staging overrides
src/test/java/com/automation/tests      tests
src/test/resources/suites               TestNG suites
src/test/resources/testdata             CSV input
dockerFiles/docker-compose.yml          Selenium Grid
```

## Grid

From the repository root:

```bash
docker compose -p selenium-infra -f dockerFiles/docker-compose.yml up -d --scale firefox=3 --no-deps selenium-hub firefox
```

That starts Selenium Hub 4.46 with Firefox 152. Add `chrome` to the command when a test needs Chrome 150.

## Tests

```bash
mvn test -DsuiteXmlFile=src/test/resources/suites/registration.xml -Denv=staging -Dretry.count=0
mvn test -DsuiteXmlFile=src/test/resources/suites/amazon.xml -Denv=staging
mvn test
```

`mvn test` runs `src/test/resources/suites/regression.xml`. The Amazon suite is separate because Amazon often blocks automated browsers.

`-Denv=qa` is the default and uses a local browser. `-Denv=staging` uses the Docker Grid. Any `-Dbrowser`, `-Dhost`, `-DhubUrl`, or `-Dbrowser.version` value overrides the property file.

Registration rows live in `src/test/resources/testdata/registration.csv`. Amazon search terms live in `src/test/resources/testdata/amazon.xlsx`. A `${unique}` token in a CSV cell becomes a new value on each read.
