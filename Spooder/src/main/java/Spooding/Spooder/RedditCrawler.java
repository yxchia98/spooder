package Spooding.Spooder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
		String postLink = "";
		WebDriverManager.chromedriver().setup();

		// Creating an object of ChromeDriver
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors",
				"--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
		WebDriver driver = new ChromeDriver(options);
//				WebDriverWait wait = new WebDriverWait(driver, 40);
//		driver.manage().window().maximize();

//Deleting all the cookies
		driver.manage().deleteAllCookies();

//Specifiying pageLoadTimeout and Implicit wait
//		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

//launching the specified URL
		driver.get(getBaseUrl());
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(5000);
		List<WebElement> list = driver.findElements(By.xpath("//a[@data-click-id='body']"));
		
		for (WebElement listItem : list) {
			count++;
			System.out.println(count + ": " + listItem.getText());
			System.out.println(listItem.getAttribute("href"));
			js.executeScript("arguments[0].click();", listItem);
			List<WebElement> postTextList = driver.findElements(By.xpath("//div[@data-click-id='text']"));
			List<WebElement> postLinkList = driver.findElements(By.xpath("/html/body/div[1]/div/div[2]/div[3]/div/div/div/div[2]/div[1]/div[2]/div[1]/div/div[2]/div[1]/div[3]/a"));
			postText = "";
			postLink = "";
			for (WebElement postTextContent : postTextList) {
				postText += postTextContent.getText() + "\n";
			}
			for(WebElement postLinkContent : postLinkList) {
				postLink += postLinkContent.getAttribute("href");
			}
			System.out.println("Links found inside: " + postLink);
			System.out.println(postText);
			js.executeScript("window.history.back();");

		}
		driver.close();
		driver.quit();
	}

}
