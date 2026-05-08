package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

/**
 * CheckoutPage - Handles multi-step checkout flow
 * Step 1: Shipping info | Step 2: Order summary | Step 3: Confirmation
 */
public class CheckoutPage extends BasePage {

    // Cart Page
    private final By checkoutButton  = By.id("checkout");
    private final By cartItems       = By.className("cart_item");
    private final By removeButtons   = By.cssSelector("button[id^='remove']");

    // Checkout Step 1 - Shipping Info
    private final By firstNameField  = By.id("first-name");
    private final By lastNameField   = By.id("last-name");
    private final By zipCodeField    = By.id("postal-code");
    private final By continueButton  = By.id("continue");
    private final By errorMessage    = By.cssSelector("[data-test='error']");

    // Checkout Step 2 - Overview
    private final By finishButton    = By.id("finish");
    private final By subtotalLabel   = By.className("summary_subtotal_label");

    // Checkout Step 3 - Confirmation
    private final By confirmHeader   = By.className("complete-header");
    private final By confirmText     = By.className("complete-text");

    /* ======= Cart Page Actions ======= */

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public void clickCheckout() {
        log.info("Clicking Checkout button.");
        click(checkoutButton);
    }

    public void removeProductFromCart(String productName) {
        List<WebElement> items = driver.findElements(cartItems);
        for (WebElement item : items) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equalsIgnoreCase(productName)) {
                item.findElement(By.cssSelector("button.cart_button")).click();
                log.info("Removed '{}' from cart.", productName);
                return;
            }
        }
        throw new RuntimeException("Product not found in cart: " + productName);
    }

    /* ======= Step 1: Shipping Info ======= */

    public void enterShippingDetails(String firstName, String lastName, String zipCode) {
        log.info("Entering shipping info: {} {} {}", firstName, lastName, zipCode);
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(zipCodeField, zipCode);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /* ======= Step 2: Order Overview ======= */

    public String getSubtotal() {
        return getText(subtotalLabel);
    }

    public void clickFinish() {
        log.info("Clicking Finish to complete order.");
        click(finishButton);
    }

    /* ======= Step 3: Order Confirmation ======= */

    public String getConfirmationHeader() {
        return getText(confirmHeader);
    }

    public String getConfirmationText() {
        return getText(confirmText);
    }

    public boolean isOrderConfirmed() {
        return isDisplayed(confirmHeader);
    }
}
