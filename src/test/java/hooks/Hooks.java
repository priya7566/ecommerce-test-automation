package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.DriverManager;

/**
 * Hooks - Cucumber lifecycle hooks
 * Before: initialize browser
 * After:  capture screenshot on failure, quit driver
 */
public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);

    @Before(order = 1)
    public void setUp(Scenario scenario) {
        log.info("==============================");
        log.info("Starting Scenario: {}", scenario.getName());
        log.info("Tags: {}", scenario.getSourceTagNames());
        DriverManager.initDriver();
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        log.info("Finishing Scenario: {} | Status: {}", scenario.getName(), scenario.getStatus());

        // Capture screenshot on FAILURE and embed in Cucumber report
        if (scenario.isFailed()) {
            log.warn("Scenario FAILED - capturing screenshot.");
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
            } catch (Exception e) {
                log.error("Could not capture screenshot: {}", e.getMessage());
            }
        }

        DriverManager.quitDriver();
        log.info("==============================\n");
    }
}
