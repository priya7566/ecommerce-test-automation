package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * TestRunner - Cucumber + TestNG Runner
 *
 * Run specific tags from CLI:
 *   mvn test -Dcucumber.filter.tags="@smoke"
 *   mvn test -Dcucumber.filter.tags="@regression"
 *   mvn test -Dbrowser=firefox -Dheadless=true
 */
@CucumberOptions(
    features  = "src/test/resources/features",
    glue      = {"stepDefinitions", "hooks"},
    tags      = "@regression",
    plugin    = {
        "pretty",
        "html:reports/cucumber-html-report.html",
        "json:reports/cucumber.json",
        "junit:reports/cucumber.xml",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
    },
    monochrome = true,
    dryRun     = false
)
public class TestRunner extends AbstractTestNGCucumberTests {

    /**
     * Enable parallel execution of Scenario Outlines
     * Each row in Examples table runs in its own thread
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
