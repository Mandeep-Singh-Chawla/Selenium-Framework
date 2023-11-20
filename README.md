Selenium Framework which is very easy to understand especially for new begineers in Web Automation

Some of Key features:-

1) Have used latest version- Selenium 4 
2) Support for remote webdriver execution also on Selenium Grid using docker
3) Have used Page Object Model (POM) design pattern
4) Parallel run, threads- 5
5) Support to run in headless mode
6) Allure reports support for Reporting 

# practice-selenium

### To create selenium grid using docker compose
Install Docker

`cd dockerFiles/`

`docker-compose -p selenium-infra up --scale firefox_108=2 --scale firefox_109=2 --scale chrome_111=2 --scale chrome_110=2 --force-recreate -d
`


### Local webdriver execution:
`mvn clean test -DmoduleName=Registration -Dbrowser=chrome -Dhost=local`


### Remote webdriver execution on Selenium Grid:
`mvn clean test -DmoduleName=Registration -Dbrowser=chrome -Dhost=grid -DhubUrl=localhost -DbrowserVersion=110.0`


**browser** values:
- chrome
- firefox

**browserVersion** values:
- For chrome 
  - 119.0

- For firefox
  - 108.0
  - 109.0

**host** values:
- local
- grid

**moduleName** values (TestNG XML file):
- Registration
