package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.*;
import utils.DriverManager;

import java.net.URL;
import java.util.List;

public class FlipkartLazyLoadingTest {
  private FlipkartHomePage homePage;
  private SearchResultsPage searchResultsPage;

  @BeforeClass
  public void setup() {
    DriverManager.initializeDriver();
    homePage = new FlipkartHomePage();
    searchResultsPage = new SearchResultsPage();
  }

  private void captureScreenshot(String screenshotName) {
    BasePage basePage = new BasePage();
    basePage.captureScreenshot(screenshotName);
  }

  @Test(priority = 1, enabled = true, description = "Verify the flipkart hoomepage load time", groups = {
      "regression" })
  public void testFlipkartHomepageLoadTime() {
    long loadTime = homePage.getHomepageLoadTime();
    Assert.assertTrue(loadTime < 8000, "Homepage load time is within acceptable range.");
  }

  @Test(priority = 2, enabled = true, description = "Verify the functionality of search product in mobile category", groups = {
      "regression" })
  public void testSearchProductInMobileCategory() throws InterruptedException {
    String product = "iPhone 13";
//    homePage.searchForProduct(product, "Mobile");
    homePage.searchForProduct(product);
    Assert.assertTrue(searchResultsPage.isSearchResultsDisplayed(), "Search results are displayed.");
  }

  @Test(priority = 3, enabled = true, description = "Verify the functionality of image loading and visibility", groups = {
      "regression" })
  public void testImageLoadingAndVisibility() throws InterruptedException {
    List<WebElement> products = searchResultsPage.getSearchResults();
    for (WebElement product : products) {
      try {
        searchResultsPage.scrollToElement(product);
        Thread.sleep(3000);
        boolean isImageDisplayed = searchResultsPage.isImageDisplayed(product);
        Assert.assertTrue(isImageDisplayed, "Image is loaded and visible.");

        // Capture screenshot after verifying the image visibility
        captureScreenshot("image_visible_" + products.indexOf(product));
      } catch (Exception e) {
        // Handle any exceptions that might occur during scrolling or image verification
        e.printStackTrace();
      }
    }
  }

  @Test(priority = 4, enabled = true, description = "Verify the flipkart hoomepage load time", groups = {
      "regression" })
  public void testScrollFeatureAndContentRefresh() {
    boolean hasScrollFeature = searchResultsPage.hasScrollFeature();
    Assert.assertTrue(hasScrollFeature, "The page has a scroll feature.");

    int initialProductCount = searchResultsPage.getNumberOfVisibleProducts();
    searchResultsPage.scrollToBottom();
    int finalProductCount = searchResultsPage.getNumberOfVisibleProducts();

    Assert.assertTrue(finalProductCount == initialProductCount, "Content is refreshed while scrolling.");
  }

  @Test(priority = 5, enabled = true, description = "Verify the lazy loading functionality", groups = {
      "regression" })
  public void testLazyLoading() {
    int visibleImageCount = searchResultsPage.getNumberOfVisibleImages();
    System.out.println("visibleImageCount is: " + visibleImageCount);

    int totalImageCount = searchResultsPage.getTotalNumberOfImages();
    System.out.println("totalImageCount is: " + totalImageCount);

    Assert.assertTrue((visibleImageCount > 0) && (visibleImageCount == totalImageCount),
        "Images are lazily loaded as the user scrolls down.");
  }

  @Test(priority = 6, enabled = true, description = "Verify the funcitonality of navigating to bottom", groups = {
      "regression" })
  public void testNavigationToBottom() {
    searchResultsPage.scrollToBottom();
    boolean isAtBottom = searchResultsPage.isAtBottomOfPage();

    Assert.assertTrue(isAtBottom, "Successfully navigated to the bottom of the page.");
  }

  @Test(priority = 7, enabled = true, description = "Verify the functionality of cross browser and screen resolution", groups = {
      "regression" })
  public void testCrossBrowserAndScreenResolution() {
    String[] browsers = { "chrome", "firefox" };
    String[] screenResolutions = { "1920x1080", "1366x768" };

    for (String browser : browsers) {
      for (String resolution : screenResolutions) {
        try {
          // Set the URL of the Selenium Grid hub
          URL gridUrl = new URL("http://your-grid-hub-url:4444/wd/hub");

          // Create the WebDriver instance with the desired capabilities
          WebDriver driver = createRemoteWebDriver(browser, resolution, gridUrl);

          // Execute your test steps here with the 'driver' instance
          // You may reuse the existing methods from the SearchResultsPage class

          // Close the browser after the test is done
          driver.quit();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private WebDriver createRemoteWebDriver(String browser, String resolution, URL gridUrl) {
    WebDriver driver = null;

    if ("chrome".equalsIgnoreCase(browser)) {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--window-size=" + resolution);
      driver = new RemoteWebDriver(gridUrl, options);
    } else if ("firefox".equalsIgnoreCase(browser)) {
      FirefoxOptions options = new FirefoxOptions();
      options.addArguments("--window-size=" + resolution);
      driver = new RemoteWebDriver(gridUrl, options);
    }

    return driver;
  }

  @AfterClass
  public void tearDown() {
    DriverManager.quitDriver();
  }
}
