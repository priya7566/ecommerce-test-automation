package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * InventoryPage - Products listing page
 */
public class InventoryPage extends BasePage {

    private final By productTitles  = By.className("inventory_item_name");
    private final By cartBadge      = By.className("shopping_cart_badge");
    private final By cartIcon       = By.className("shopping_cart_link");

    public void addProductToCart(String productName) {
        log.info("Adding product to cart: {}", productName);
        // Find product and click its Add to Cart button dynamically
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        for (WebElement product : products) {
            String title = product.findElement(By.className("inventory_item_name")).getText();
            if (title.equalsIgnoreCase(productName)) {
                product.findElement(By.cssSelector("button.btn_inventory")).click();
                log.info("Product '{}' added to cart.", productName);
                return;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public int getCartCount() {
        if (!isDisplayed(cartBadge)) return 0;
        return Integer.parseInt(getText(cartBadge));
    }

    public void goToCart() {
        log.info("Navigating to cart.");
        click(cartIcon);
    }
}
