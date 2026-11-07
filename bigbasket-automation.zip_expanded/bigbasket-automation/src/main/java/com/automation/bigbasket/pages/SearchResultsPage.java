package com.automation.bigbasket.pages;

import com.automation.bigbasket.config.ConfigReader;
import com.automation.bigbasket.models.ProductDetails;
import com.automation.bigbasket.utils.TextUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Search results listing: filters, dynamic product selection, detail capture and card-level basket controls.
 * Cards are always re-located by index (never cached) because React re-renders the list frequently.
 */
public class SearchResultsPage extends BasePage {

    // ---- Locators (calibrate against the live DOM; see README) ----
    private static final By PRODUCT_CARD = By.xpath("//li[.//a[contains(@href,'/pd/')]]");
    private static final By CARD_NAME = By.xpath(".//h3");
    private static final By CARD_PRICE_LEAVES = By.xpath(".//*[not(*) and contains(text(),'" + RUPEE + "')]");
    private static final By CARD_DISCOUNT = By.xpath(".//*[not(*) and contains(text(),'OFF')]");
    private static final By CARD_PACK = By.xpath(".//*[not(*)][contains(@class,'ack')]");
    private static final By CARD_ADD = By.xpath(".//button[normalize-space()='Add']");
    private static final By CARD_PLUS = By.xpath(".//button[normalize-space()='+']");
    private static final By CARD_QTY = By.xpath(".//button[normalize-space()='+']/preceding-sibling::*[1]");
    private static final By BRAND_SECTION_HEADER =
            By.xpath("//*[self::button or self::div or self::span][normalize-space()='Brand']");

    private static final Random RANDOM = new Random();

    public SearchResultsPage(WebDriver driver) {
        super(driver);
    }

    // ---------------------------------------------------------------- results
    private List<WebElement> cards() {
        return driver.findElements(PRODUCT_CARD);
    }

    private WebElement card(int index) {
        return cards().get(index);
    }

    /** Waits for at least one product card and returns the count. */
    public int waitForResults() {
        wait.until(d -> !d.findElements(PRODUCT_CARD).isEmpty());
        return cards().size();
    }

    /**
     * After a new search the SPA keeps the previous cards on screen for a moment, so first wait for the URL to
     * reflect the new search term, then for cards to be present.
     */
    public int waitForResultsFor(String term) {
        String needle = term.toLowerCase().replace(" ", "");
        wait.untilQuietly(d -> d.getCurrentUrl().toLowerCase().replace("%20", "").replace("+", "").contains(needle),
                ConfigReader.getInt("explicit.wait"));
        return waitForResults();
    }

    public boolean areResultsDisplayed() {
        return wait.isVisible(PRODUCT_CARD, ConfigReader.getInt("explicit.wait"));
    }

    public List<String> getProductNames() {
        return cards().stream()
                .map(c -> c.findElements(CARD_NAME))
                .filter(l -> !l.isEmpty())
                .map(l -> l.get(0).getText().trim())
                .collect(Collectors.toList());
    }

    private String namesSignature() {
        try {
            return String.join("|", getProductNames());
        } catch (RuntimeException e) {   // stale mid-render
            return "";
        }
    }

    // ---------------------------------------------------------------- filters
    public void applyBrandFilter(String brand) {
        log.info("Applying brand filter '{}'", brand);
        String before = namesSignature();
        By option = By.xpath("(//label[contains(normalize-space(.),'" + brand + "')])[1]");

        if (!wait.isVisible(option, 3) && wait.isVisible(BRAND_SECTION_HEADER, 3)) {
            wait.click(BRAND_SECTION_HEADER);   // expand collapsed section
        }
        WebElement label = wait.visible(option);
        wait.scrollIntoView(label);
        wait.click(label);

        // list must refresh with a different set of products
        wait.untilQuietly(d -> !namesSignature().equals(before), ConfigReader.getInt("explicit.wait"));
        waitForResults();
    }

    /** % of currently displayed products whose name contains the brand. */
    public double brandMatchPercentage(String brand) {
        List<String> names = getProductNames();
        if (names.isEmpty()) return 0;
        long hits = names.stream().filter(n -> n.toLowerCase().contains(brand.toLowerCase())).count();
        return hits * 100.0 / names.size();
    }

    // ---------------------------------------------------------------- selection & capture
    /** Picks a random in-stock product (one that has an Add button) from the top N results. */
    public int pickRandomProductIndex() {
        List<WebElement> all = cards();
        int pool = Math.min(ConfigReader.getInt("random.pick.pool"), all.size());
        List<Integer> candidates = new ArrayList<>();
        for (int i = 0; i < pool; i++) {
            if (!all.get(i).findElements(CARD_ADD).isEmpty()) candidates.add(i);
        }
        if (candidates.isEmpty()) {
            throw new IllegalStateException("No in-stock product with an 'Add' button in the top " + pool + " results");
        }
        int chosen = candidates.get(RANDOM.nextInt(candidates.size()));
        log.info("Randomly selected product index {}", chosen);
        return chosen;
    }

    public ProductDetails captureDetails(int index) {
        WebElement c = card(index);
        wait.scrollIntoView(c);

        ProductDetails p = new ProductDetails();
        p.setName(c.findElement(CARD_NAME).getText().trim());

        // all rupee values on the card: lowest = selling price, highest = MRP
        List<Double> amounts = c.findElements(CARD_PRICE_LEAVES).stream()
                .map(e -> TextUtils.parsePrice(e.getText()))
                .filter(v -> v > 0)
                .collect(Collectors.toList());
        double price = amounts.stream().mapToDouble(Double::doubleValue).min().orElse(0);
        double mrp = amounts.stream().mapToDouble(Double::doubleValue).max().orElse(price);
        p.setPrice(price);
        p.setMrp(mrp);

        List<WebElement> disc = c.findElements(CARD_DISCOUNT);
        if (!disc.isEmpty()) {
            p.setDiscount(disc.get(0).getText().trim());
        } else if (mrp > price) {
            p.setDiscount(Math.round((mrp - price) * 100 / mrp) + "% OFF (computed)");
        } else {
            p.setDiscount("N/A");
        }

        List<WebElement> pack = c.findElements(CARD_PACK);
        p.setPackSize(!pack.isEmpty() && !pack.get(0).getText().isBlank()
                ? pack.get(0).getText().trim()
                : TextUtils.extractPackSize(p.getName()));
        p.setQuantity(1);
        log.info("Captured {}", p);
        return p;
    }

    // ---------------------------------------------------------------- basket controls on the card
    public void addToBasket(int index) {
        WebElement c = card(index);
        wait.scrollIntoView(c);
        wait.click(c.findElement(CARD_ADD));
        wait.until(d -> !card(index).findElements(CARD_PLUS).isEmpty());   // "Add" turns into  - qty +
    }

    public int getCardQuantity(int index) {
        List<WebElement> q = card(index).findElements(CARD_QTY);
        return q.isEmpty() ? 0 : TextUtils.parseInt(q.get(0).getText());
    }

    /** Clicks '+' until the card shows the target quantity, waiting for each increment. */
    public void increaseQuantityTo(int index, int target) {
        int current = getCardQuantity(index);
        while (current < target) {
            final int expected = current + 1;
            wait.click(card(index).findElement(CARD_PLUS));
            wait.until(d -> getCardQuantity(index) == expected);
            current = expected;
        }
    }
}
