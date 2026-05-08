package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.List;
import java.util.Map;

/**
 * CheckoutSteps - Cucumber Step Definitions for Checkout feature
 * Maps Gherkin steps to Selenium page object actions
 */
public class CheckoutSteps {

    private final LoginPage     loginPage     = new LoginPage();
    private final InventoryPage inventoryPage = new InventoryPage();
    private final CheckoutPage  checkoutPage  = new CheckoutPage();

    /* ========== GIVEN ========== */

    @Given("I am on the {string} website")
    public void iAmOnTheWebsite(String url) {
        loginPage.navigateTo(url);
    }

    @Given("I log in with valid credentials {string} and {string}")
    public void iLogInWithValidCredentials(String username, String password) {
        loginPage.login(username, password);
    }

    @Given("I add product {string} to the cart")
    public void iAddProductToCart(String productName) {
        inventoryPage.addProductToCart(productName);
    }

    /* ========== WHEN ========== */

    @When("I navigate to the cart")
    public void iNavigateToTheCart() {
        inventoryPage.goToCart();
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        checkoutPage.clickCheckout();
    }

    @When("I enter shipping details:")
    public void iEnterShippingDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = rows.get(0);
        checkoutPage.enterShippingDetails(
            data.get("firstName"),
            data.get("lastName"),
            data.get("zipCode")
        );
        checkoutPage.clickContinue();
    }

    @When("I enter partial shipping details with missing zip:")
    public void iEnterPartialShippingDetails(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> data = rows.get(0);
        checkoutPage.enterShippingDetails(data.get("firstName"), data.get("lastName"), "");
    }

    @When("I leave the first name field empty")
    public void iLeaveFirstNameEmpty() {
        checkoutPage.enterShippingDetails("", "Doe", "10001");
    }

    @When("I click on Continue button")
    public void iClickOnContinueButton() {
        checkoutPage.clickContinue();
    }

    @When("I click on Finish button")
    public void iClickOnFinishButton() {
        checkoutPage.clickFinish();
    }

    @When("I remove product {string} from the cart")
    public void iRemoveProductFromCart(String productName) {
        checkoutPage.removeProductFromCart(productName);
    }

    /* ========== THEN ========== */

    @Then("I should see the order confirmation message {string}")
    public void iShouldSeeOrderConfirmation(String expectedMessage) {
        String actual = checkoutPage.getConfirmationHeader();
        Assert.assertEquals(actual, expectedMessage,
            "Order confirmation message mismatch!");
    }

    @Then("the order should be saved in order history")
    public void theOrderShouldBeSaved() {
        Assert.assertTrue(checkoutPage.isOrderConfirmed(),
            "Order confirmation page not displayed!");
    }

    @Then("I should see {string} items in the cart")
    public void iShouldSeeItemsInCart(String expectedCount) {
        int actual = checkoutPage.getCartItemCount();
        Assert.assertEquals(actual, Integer.parseInt(expectedCount),
            "Cart item count mismatch!");
    }

    @Then("the cart total should display correctly")
    public void theCartTotalShouldDisplayCorrectly() {
        Assert.assertNotNull(checkoutPage.getSubtotal(),
            "Subtotal should not be null!");
    }

    @Then("the cart total should update correctly")
    public void theCartTotalShouldUpdateCorrectly() {
        Assert.assertNotNull(checkoutPage.getSubtotal());
    }

    @Then("the cart should contain only {string} item")
    public void theCartShouldContainItem(String expectedCount) {
        int actual = checkoutPage.getCartItemCount();
        Assert.assertEquals(actual, Integer.parseInt(expectedCount),
            "Cart item count after removal mismatch!");
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeErrorMessage(String expectedError) {
        String actual = checkoutPage.getErrorMessage();
        Assert.assertEquals(actual, expectedError,
            "Error message mismatch!");
    }
}
