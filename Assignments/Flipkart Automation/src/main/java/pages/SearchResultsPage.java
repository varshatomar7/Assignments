package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverManager;

import java.util.List;

public class SearchResultsPage {
    private WebDriver driver;
    private final By productResults = By.cssSelector("div[data-id]");
    private final By imageSelector = By.xpath("//span[contains(text(),'results for')]/ancestor::div[4]/div[starts-with(@class,'_1AtVbE')]/descendant::img[@loading='eager']");

    /**
     * This is the constructor of the current page
     */
    public SearchResultsPage() {

        this.driver = DriverManager.getDriver();
    }

    /**
     * This method is used to verify if search results are displayed or not
     *
     * @return !isEmpty - boolean value
     */
    public boolean isSearchResultsDisplayed() {
        boolean isEmpty = driver.findElements(productResults).isEmpty();
        return !isEmpty;
    }

    /**
     * This method is used to get the search results
     *
     * @return productResultsList
     * @throws InterruptedException
     */
    public List<WebElement> getSearchResults() throws InterruptedException {
        List<WebElement> productResultsList = driver.findElements(productResults);
        Thread.sleep(5000);
        return productResultsList;
    }

    /**
     * This method is used to scroll to the element
     *
     * @param element
     * @throws InterruptedException
     */
    public void scrollToElement(WebElement element) throws InterruptedException {
        Thread.sleep(500);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * This method is used to check if the product image is displayed in the search results
     *
     * @param product
     * @return
     * @throws InterruptedException
     */
    public boolean isImageDisplayed(WebElement product) throws InterruptedException {
        Thread.sleep(1000);
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0;",
                product.findElement(imageSelector));
    }

    /**
     * This method is used to check if the page has scroll feature once the results are displayed upon search
     *
     * @return
     */
    public boolean hasScrollFeature() {
        return (boolean) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.scrollHeight > document.documentElement.clientHeight;");
    }

    /**
     * This method is used to scroll to the bottom of the page
     */
    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * This method is used to get the number of visible products
     *
     * @return product size
     */
    public int getNumberOfVisibleProducts() {
        return driver.findElements(productResults).size();
    }

    /**
     * This method is used to get the number of visible images
     *
     * @return images count
     */
    public int getNumberOfVisibleImages() {
        List<WebElement> visibleImages = driver.findElements(imageSelector);
        return (int) visibleImages.stream().filter(WebElement::isDisplayed).count();
    }

    /**
     * This method is used to get the total number of images
     *
     * @return images size
     */
    public int getTotalNumberOfImages() {
        return driver.findElements(imageSelector).size();
    }

    /**
     * This method is used to verify if the cursor at the bottom page
     *
     * @return
     */
    public boolean isAtBottomOfPage() {
        Double scrollY = (Double) ((JavascriptExecutor) driver).executeScript("return window.scrollY;");
        long innerHeight = (Long) ((JavascriptExecutor) driver).executeScript("return window.innerHeight;");
        long scrollHeight = (Long) ((JavascriptExecutor) driver)
                .executeScript("return document.documentElement.scrollHeight;");
        return scrollY + innerHeight >= scrollHeight;
    }

}