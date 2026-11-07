package com.automation.bigbasket.tests;

import com.automation.bigbasket.base.BaseTest;
import com.automation.bigbasket.components.HeaderComponent;
import com.automation.bigbasket.config.ConfigReader;
import com.automation.bigbasket.data.TestData;
import com.automation.bigbasket.data.TestDataProvider;
import com.automation.bigbasket.models.BasketItem;
import com.automation.bigbasket.models.ProductDetails;
import com.automation.bigbasket.pages.BasketPage;
import com.automation.bigbasket.pages.CheckoutPage;
import com.automation.bigbasket.pages.HomePage;
import com.automation.bigbasket.pages.SearchResultsPage;
import com.automation.bigbasket.utils.TextUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

import static com.automation.bigbasket.listeners.ExtentTestManager.step;

public class BigBasketE2ETest extends BaseTest {

    @Test(dataProvider = "e2eData", dataProviderClass = TestDataProvider.class,
            description = "Search -> filter -> add -> update qty -> second product -> basket validation -> checkout")
    public void groceryShoppingEndToEnd(TestData data) {

        final double tol = ConfigReader.getDouble("price.tolerance");
        final int wait = ConfigReader.getInt("explicit.wait");

        HomePage home = new HomePage(driver);
        HeaderComponent header = new HeaderComponent(driver);
        SearchResultsPage results = new SearchResultsPage(driver);
        BasketPage basket = new BasketPage(driver);
        CheckoutPage checkout = new CheckoutPage(driver);

        // 1 ------------------------------------------------------------------ launch
        step("1. Launch BigBasket");
        home.open();
        home.selectLocation(data.location);
        Assert.assertTrue(home.getTitle().toLowerCase().contains("bigbasket"),
                "Home page title should mention BigBasket but was: " + home.getTitle());

        // 2, 3 ---------------------------------------------------------------- search + verify results
        step("2. Search for '" + data.primaryProduct + "'");
        header.search(data.primaryProduct);

        step("3. Verify search results are displayed");
        int resultCount = results.waitForResultsFor(data.primaryProduct);
        Assert.assertTrue(resultCount > 0, "Expected search results for " + data.primaryProduct);
        step("   " + resultCount + " products displayed");

        // 4, 5 ---------------------------------------------------------------- filter + validate
        step("4. Apply brand filter '" + data.brandFilter + "'");
        results.applyBrandFilter(data.brandFilter);

        step("5. Validate filtered products");
        double matchPct = results.brandMatchPercentage(data.brandFilter);
        step(String.format("   %.0f%% of displayed products contain brand '%s'", matchPct, data.brandFilter));
        Assert.assertTrue(matchPct >= ConfigReader.getDouble("filter.min.match.percent"),
                "Filtered list does not look filtered by brand " + data.brandFilter + " (" + matchPct + "%)");

        // 6, 7 ---------------------------------------------------------------- dynamic pick + capture
        step("6. Select a product dynamically");
        int idx1 = results.pickRandomProductIndex();

        step("7. Capture product details");
        ProductDetails first = results.captureDetails(idx1);
        step("   " + first);
        Assert.assertFalse(first.getName().isEmpty(), "Product name must not be empty");
        Assert.assertTrue(first.getPrice() > 0, "Product price must be > 0");

        // 8 ------------------------------------------------------------------- add to basket
        step("8. Add '" + first.getName() + "' to basket");
        results.addToBasket(idx1);
        Assert.assertEquals(results.getCardQuantity(idx1), 1, "Quantity after first add");
        double amt1 = header.waitForBasketAmount(first.getPrice(), tol, wait);
        Assert.assertEquals(amt1, first.getPrice(), tol, "Basket amount after adding 1 unit");

        // 9 ------------------------------------------------------------------- increase quantity to 2
        step("9. Increase quantity to " + data.targetQuantity);
        results.increaseQuantityTo(idx1, data.targetQuantity);
        first.setQuantity(data.targetQuantity);
        Assert.assertEquals(results.getCardQuantity(idx1), data.targetQuantity, "Card quantity after increase");
        double expectedAfterIncrease = TextUtils.round2(first.getPrice() * data.targetQuantity);
        double amt2 = header.waitForBasketAmount(expectedAfterIncrease, tol, wait);
        step(String.format("   Basket amount expected=%.2f actual=%.2f", expectedAfterIncrease, amt2));
        Assert.assertEquals(amt2, expectedAfterIncrease, tol, "Basket amount after quantity increase");

        // 10 ------------------------------------------------------------------ second product
        step("10. Search for '" + data.secondaryProduct + "' and add it");
        header.search(data.secondaryProduct);
        results.waitForResultsFor(data.secondaryProduct);
        int idx2 = results.pickRandomProductIndex();
        ProductDetails second = results.captureDetails(idx2);
        step("   " + second);
        results.addToBasket(idx2);
        Assert.assertEquals(results.getCardQuantity(idx2), 1, "Quantity of second product");

        // 11 ------------------------------------------------------------------ basket verification
        step("11. Verify both products in basket");
        basket.open();
        basket.waitForItemCount(2);
        List<BasketItem> items = basket.getItems();
        Assert.assertEquals(items.size(), 2, "Basket should contain exactly 2 distinct products");

        for (ProductDetails expected : List.of(first, second)) {
            Optional<BasketItem> row = items.stream()
                    .filter(i -> TextUtils.namesMatch(i.getName(), expected.getName()))
                    .findFirst();
            Assert.assertTrue(row.isPresent(), "Product missing from basket: " + expected.getName());
            Assert.assertEquals(row.get().getQuantity(), expected.getQuantity(),
                    "Quantity mismatch for " + expected.getName());
            Assert.assertEquals(row.get().getUnitPrice(), expected.getPrice(), tol,
                    "Unit price mismatch for " + expected.getName());
            step("   OK: " + row.get());
        }

        // 12 ------------------------------------------------------------------ total calculated in Java
        step("12. Compare Java-calculated total with displayed total");
        double expectedTotal = TextUtils.round2(
                first.getPrice() * first.getQuantity() + second.getPrice() * second.getQuantity());
        double displayedTotal = basket.getDisplayedTotal();
        step(String.format("   Expected total=%.2f, displayed total=%.2f", expectedTotal, displayedTotal));
        Assert.assertEquals(displayedTotal, expectedTotal, tol, "Basket total mismatch");

        // 13 ------------------------------------------------------------------ checkout
        step("13. Proceed to checkout (until login/address page)");
        checkout.proceedToCheckout();
        Assert.assertTrue(checkout.isLoginPromptDisplayed(), "Login prompt should appear for unauthenticated user");

        // 14 ------------------------------------------------------------------ negative validation
        step("14. Validate mandatory-field error for unauthenticated user");
        String error = checkout.submitEmptyAndGetError();
        step("   Error message displayed: '" + error + "'");
        Assert.assertFalse(error.isEmpty(), "An error message should be displayed");
        Assert.assertTrue(error.toLowerCase().contains(data.expectedLoginErrorKeyword.toLowerCase()),
                "Error message should contain '" + data.expectedLoginErrorKeyword + "' but was: " + error);
    }
}
