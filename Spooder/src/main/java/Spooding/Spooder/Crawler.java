package Spooding.Spooder;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import twitter4j.TwitterException;
/**
 * Crawler abstract class. Contains methods to be inherited across all crawlers, enabling a degree of polymorphism.
 */
public abstract class Crawler extends MongoConnect implements Exportable, Runnable{
	/**
	 * Method for initializing crawl function using ChromeDriver
	 * @throws IOException Throws exception is related to Input and Output operations
	 * @throws InterruptedException Throws exception when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
	 * @throws TwitterException Throws exception when TwitterAPI calls are failed
	 */
	public abstract void crawl() throws IOException, InterruptedException, TwitterException;
	/**
	 * Method to start Webdriver
	 * @return driver
	 */
	protected WebDriver initWebDriver() {
		// Setting system properties of ChromeDriver
		WebDriverManager.chromedriver().setup();

		// Creating an object of ChromeDriver
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors",
				"--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--disable-popup-blocking");
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
		//suppress info loggings
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
		WebDriver driver = new ChromeDriver(options);

		//Deleting all the cookies
		driver.manage().deleteAllCookies();

//Specifiying pageLoadTimeout and Implicit wait
//		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		return driver;
	}
}
