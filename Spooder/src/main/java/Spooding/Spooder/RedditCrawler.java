package Spooding.Spooder;

import java.util.List;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RedditCrawler extends Crawler {
	private String baseUrl;		//baseurl of reddit link based on search string
	private ArrayList<RedditPost> redditList = new ArrayList<>();	//arraylist to store post objects

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
//		String postText = "";
		WebDriverManager.chromedriver().setup();

		// Creating an object of ChromeDriver
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors",
				"--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
		//suppress info loggings
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
		while(list.size() < 50) {
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			Thread.sleep(4000); // pause the browser to let javascript load new posts
			list = driver.findElements(By.xpath("//div[contains(@class, '_1oQyIsiPHYt6nx7VOmd1sz')]"));
		}
		

		for (WebElement listItem : list) {
			WebElement postTitle = listItem.findElement(By.xpath(".//a[@data-click-id='body']"));
			WebElement postVote = listItem.findElement(By.xpath(".//div[@class='_1rZYMD_4xY3gRcSS3p8ODO _3a2ZHWaih05DgAOtvu6cIo']"));
			String title = postTitle.getText();
			int votes = formatVotes(postVote.getText());
//			System.out.println(redditList.size() + ": " + title); //Post Title
//			System.out.println("Votes: " + votes); // Post votes
//			System.out.println("Link: " + postTitle.getAttribute("href")); //Post Link
			RedditPost currentPost = new RedditPost(title, votes);
			redditList.add(currentPost);
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
		exportExcel();
	}
	
	private int formatVotes(String votes) {
		double result;
		String formattedString;
		if(votes.equals("Vote")) {
			return 0;
		}
		formattedString = votes.replaceAll("[^0-9\\.]","");
		result = Double.parseDouble(formattedString);
		if (votes.contains("k")){
			result *= 1000;
		}
		else if (votes.contains("m")) {
			result *= 1000000;
		}
		return (int) result;
		
	}

	public void exportExcel() {
		System.out.println("Exporting Reddit data to Excel");
		for (RedditPost post : this.redditList) {
			System.out.println(this.redditList.indexOf(post) + 1 + ": " + post.getTitle()); //print out post titles
			System.out.println("Votes: " + post.getVotes()); //print out post votes
		}
	}
	public void importExcel() {
		System.out.println("Importing Reddit data from Excel");
	}

}
