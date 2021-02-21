package Spooding.Spooder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RedditCrawler extends Crawler {
	private int count = 0;

	public RedditCrawler() {
		super();
	}

	public RedditCrawler(String url) {
		super.setBaseUrl(url);
	}

	@Override
	public void crawl() throws InterruptedException {
		// Setting system properties of ChromeDriver
//		System.setProperty("webdriver.chrome.driver", "C://WebDriver//bin//chromedriver.exe");	
		String postText = "";
		WebDriverManager.chromedriver().setup();

		// Creating an object of ChromeDriver
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors",
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
			List<WebElement> postList = driver.findElements(By.xpath("//div[@data-click-id='text']"));
			postText = "";
			for (WebElement postContent : postList) {
				postText += postContent.getText() + "\n";
			}
			System.out.println(postText);
			js.executeScript("window.history.back();");

		}
		driver.close();
	}

}
