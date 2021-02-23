package Spooding.Spooder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RedditCrawler extends Crawler {
	private String baseUrl;
	private int count = 0;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RedditCrawler(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public void crawl() throws InterruptedException {
		// Setting system properties of ChromeDriver
//		System.setProperty("webdriver.chrome.driver", "C://WebDriver//bin//chromedriver.exe");	
		String postText = "";
		WebDriverManager.chromedriver().setup();

		// Creating an object of ChromeDriver
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors",
				"--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
		WebDriver driver = new ChromeDriver(options);

//Deleting all the cookies
		driver.manage().deleteAllCookies();

//Specifiying pageLoadTimeout and Implicit wait
//		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

//launching the specified URL
		driver.get(getBaseUrl());
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(4000); // pause the browser to let javascript load new posts
		List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class, '_1oQyIsiPHYt6nx7VOmd1sz')]"));

		for (WebElement listItem : list) {
			count++;
			WebElement postTitle = listItem.findElement(By.xpath(".//a[@data-click-id='body']"));
			WebElement postVote = listItem.findElement(By.xpath(".//div[@class='_1E9mcoVn4MYnuBQSVDt1gC']"));
			System.out.println(count + ": " + postTitle.getText()); //Post Title
			System.out.println("Votes: " + postVote.getText()); // Post votes
			System.out.println("Link: " + postTitle.getAttribute("href")); //Post Link
			//goes into the post and extracts post contents, nullify this code if you want the crawler to run significantly faster.
//			js.executeScript("arguments[0].click();", listItem);
//			List<WebElement> postTextList = driver.findElements(By.xpath("//div[@data-click-id='text']"));
//			postText = "";
//			for (WebElement postTextContent : postTextList) {
//				postText += postTextContent.getText() + "\n";
//			}
//			System.out.println(postText); //post content
//			js.executeScript("window.history.back();");
			//end of post content extraction
		}
		driver.close();
		driver.quit();
	}

	public void export() {

	}

}
