package Spooding.Spooder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import com.opencsv.CSVWriter;

import io.github.bonigarcia.wdm.WebDriverManager;
import twitter4j.TwitterException;

public class STCrawler extends Crawler {
	private String baseUrl = "https://www.straitstimes.com/tags/budget-2021";
	private int limit = 50;
	private ArrayList<STPost> postArray = new ArrayList<>();
	private WebDriver driver;

	public STCrawler() {
		this.driver = initWebDriver();
	}

	public STCrawler(int limit) {
		this.limit = limit;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	// override initWebDriver method as javascript should be disabled on straits
	// times.
	protected WebDriver initWebDriver() {
		// Setting system properties of ChromeDriver
//		System.setProperty("webdriver.chrome.driver", "C://WebDriver//bin//chromedriver.exe");	
//		String postText = "";
		WebDriverManager.chromedriver().setup();

		// Creating an object of ChromeDriver
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors",
				"--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage", "--disable-popup-blocking", "--no-sandbox");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		Map<String, Object> chromePrefs = new HashMap<>();
		chromePrefs.put("profile.managed_default_content_settings.javascript", 2);
		options.setExperimentalOption("prefs", chromePrefs);
		// suppress info loggings
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

	public void exportExcel() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Exporting Straits Times articles data to Excel");
		List<String[]> writeList = new ArrayList<>();
		CSVWriter writer = new CSVWriter(new FileWriter("straitstimes.csv"));
		for (STPost post : this.postArray) {
//			System.out.println(this.redditList.indexOf(post) + 1 + ": " + post.getTitle()); //print out post titles
//			System.out.println("Votes: " + post.getVotes()); //print out post votes
			String[] data = { post.getTitle() };
			writeList.add(data);
		}
		writer.writeAll(writeList, false);
		writer.close();
		System.out.println("Exported");
	}

	public void crawl() throws IOException, InterruptedException, TwitterException {
//launching the specified URL
		this.driver = initWebDriver();
		System.out.println("Crawling from straits times...");
		driver.get(getBaseUrl());
		Thread.sleep(1000); 
		System.out.println("Website reached.");
		List<WebElement> list = driver.findElements(By.xpath("//span[@class='story-headline']"));
		for (WebElement listItem : list) {
			System.out.println(listItem.getText());
		}
		List<WebElement> nextList;
		while (list.size() < limit) {
			driver.findElement(By.xpath("//li[@class='pager-next']")).click();
			Thread.sleep(1000); 
			nextList = driver.findElements(By.xpath("//span[@class='story-headline']"));
			for (WebElement listItem : nextList) {
				System.out.println(listItem.getText());
			}
			list.addAll(nextList);
			System.out.println(list.size());
		}
//		exportExcel();
		driver.close();
		driver.quit();
	}

}
