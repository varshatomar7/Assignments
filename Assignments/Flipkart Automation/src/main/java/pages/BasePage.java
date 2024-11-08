package pages;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class BasePage {
	protected WebDriver driver;
	private static AtomicInteger screenshotCounter = new AtomicInteger(0);

	/** * This is the constructor */
	public BasePage() {
		this.driver = DriverManager.getDriver();
	}

	/**
	 * * This method captures a screenshot and saves it with a unique name *
	 * * @param screenshotName The name of the screenshot
	 */
	public void captureScreenshot(String screenshotName) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		Path destination = Paths.get("screenshots",
				"screenshot_" + screenshotCounter.getAndIncrement() + "_" + screenshotName + ".png");
		try {
			Files.createDirectories(destination.getParent());
			Files.copy(source.toPath(), destination);
			System.out.println("Screenshot captured and saved: " + destination);
		} catch (IOException e) {
			System.out.println("Failed to capture screenshot: " + e.getMessage());
		}
	}
}