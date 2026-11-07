# BigBasket Selenium Automation (Java + Selenium 4 + TestNG + Maven)

End-to-end grocery flow: search Milk -> brand filter -> add -> qty 2 -> search Biscuits -> add ->
basket validation -> Java total check -> checkout -> unauthenticated login validation.

## Prerequisites
- JDK 17+, Maven 3.8+, Chrome (or Firefox/Edge). Selenium Manager downloads the driver automatically.

## Run
```
mvn clean test                                   # default: Chrome, headed
mvn clean test -Dbrowser=firefox
mvn clean test -Dheadless=true
mvn clean test -Dbase.url=https://www.bigbasket.com/ -Dexplicit.wait=30
```
Any key in `src/test/resources/config.properties` can be overridden with `-Dkey=value`.

## Output
- HTML report: `target/reports/ExtentReport_<timestamp>.html` (steps, pass/fail, screenshot)
- Failure screenshots: `target/screenshots/`
- Logs: `target/logs/automation.log`
- Surefire XML/TXT: `target/surefire-reports/`

## Structure
```
src/main/java/.../config      ConfigReader (properties + -D overrides)
src/main/java/.../driver      DriverFactory (ThreadLocal WebDriver, chrome/firefox/edge)
src/main/java/.../utils       WaitUtils (all explicit waits), TextUtils (price/pack parsing), ScreenshotUtils
src/main/java/.../models      ProductDetails, BasketItem
src/main/java/.../components  HeaderComponent (search box + mini basket)
src/main/java/.../pages       HomePage, SearchResultsPage, BasketPage, CheckoutPage (Page Object Model)
src/test/java/.../base        BaseTest (setup/teardown)
src/test/java/.../data        TestData + TestDataProvider (reads testdata.json)
src/test/java/.../listeners   TestListener, ExtentManager, ExtentTestManager (reporting)
src/test/java/.../tests       BigBasketE2ETest
src/test/resources            config.properties, testdata.json, log4j2.xml
```

## Requirement mapping
| Requirement | Where |
|---|---|
| Page Object Model | `pages/`, `components/` |
| Reusable utilities | `utils/` |
| Configuration file | `config.properties` + `ConfigReader` |
| Reporting | ExtentReports + TestNG listener + log4j |
| Data-driven | `testdata.json` -> TestNG `@DataProvider` (add rows to run more scenarios) |
| No Thread.sleep() | Only explicit waits in `WaitUtils` (implicit wait = 0) |

## IMPORTANT: calibrating locators
BigBasket is a React single-page app with generated class names, changing layout and a location prompt.
All locators are `private static final By` constants at the top of each page class. They use
structure-based XPath (e.g. product cards = `li` containing a `/pd/` link) rather than class names, but you
must verify them once against the live site:
1. Open BigBasket in Chrome, press F12, use Ctrl+F in the Elements tab to test each XPath.
2. Fix any constant that does not match exactly one (or the expected number of) element(s).
3. Re-run. Start with: location popup (HomePage), card Add/+ buttons (SearchResultsPage),
   basket rows / sub total (BasketPage), login phone field and error (CheckoutPage).
If the site shows a bot-check or captcha, run headed (`headless=false`).
