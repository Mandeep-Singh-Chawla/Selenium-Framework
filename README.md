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
  - 110.0
  - 111.0
- For firefox
  - 108.0
  - 109.0

**host** values:
- local
- grid

**moduleName** values (TestNG XML file):
- Registration
