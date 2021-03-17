package Spooding.Spooder;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.*;

import com.opencsv.CSVWriter;
/**
 * Reddit Crawler Class
 * Contains methods to crawl individual posts from reddit, afterwards uploading them into MongoDB
 */
public class RedditCrawler extends Crawler {
	private String baseUrl;		//baseurl of reddit link based on search string
	private ArrayList<RedditPost> redditList = new ArrayList<>();	//arraylist to store post objects
	private WebDriver driver;	//selenium webdriver variable
	/**
	 * Get Method to return URL
	 * @return URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * Set Method to modify baseUrl variable
	 * @param baseUrl URL
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	/**
	 * Default Constructor
	 * Method to set default URL if none is provided, default to budget 2021 policies
	 */
	public RedditCrawler() {
		this.baseUrl = "https://www.reddit.com/r/singapore/search/?q=budget%20flair%3ANews%20OR%20flair%3APolitics%20OR%20flair%3AOpinion_Article%20OR%20flair%3ASerious_Discussion&restrict_sr=1";
	}
	
	/**
	 * Specified Constructor
	 * A different reddit url can be specified based on user requirements. 
	 * @param baseUrl
	 */
	public RedditCrawler(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	/**
	 * Initiate crawling from reddit, crawling till a minimum of 50 individual post titles with their respective vote scores will be scraped and 
	 * uploaded into the reddit collection in MongoDB
	 */
	public void crawl() throws InterruptedException, IOException {
		redditList.clear();
		driver = initWebDriver();
		CrawlProgressBar redditBar = new CrawlProgressBar("Crawling Reddit...","crawlReddit");
		System.out.println("Crawling from reddit...");
		driver.get(getBaseUrl());
		System.out.println("Website reached.");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(4000); // pause the browser to let javascript load new posts
		List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class, '_1oQyIsiPHYt6nx7VOmd1sz')]"));
		while(list.size() < 50) {
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			Thread.sleep(4000); // pause the browser to let javascript load new posts
			list = driver.findElements(By.xpath("//div[contains(@class, '_1oQyIsiPHYt6nx7VOmd1sz')]"));
		}
		System.out.println("Retrieved " + list.size() + " posts");
		

		for (WebElement listItem : list) {
			WebElement postTitle = listItem.findElement(By.xpath(".//a[@data-click-id='body']"));
			WebElement postVote = listItem.findElement(By.xpath(".//div[@class='_1rZYMD_4xY3gRcSS3p8ODO _3a2ZHWaih05DgAOtvu6cIo']"));
			String title = postTitle.getText();
			int votes = formatVotes(postVote.getText());
			redditList.add(new RedditPost(title, votes));
		}
		exportRedditMongo(redditList);
		driver.close();
		driver.quit();
		redditBar.close.setEnabled(true);
	}
	
	/**
	 * Method to format the votes
	 * @param votes
	 * @return vote count after formatting
	 */
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
	
	/**
	 * Exports crawled reddit data from MongoDB into Excel CSV
	 * An Excel CSV file named 'reddit' will be generated.
	 */
	public void exportExcel() throws IOException {
		redditList = importRedditMongo();
		if (redditList.isEmpty()) {
			System.out.println("No reddit data to export");
			return;
		}
		System.out.println("Exporting Reddit data to Excel");
		CSVWriter writer = new CSVWriter(new FileWriter("reddit.csv"));
		for (RedditPost post : this.redditList) {
			String[] data = {post.getTitle(), Integer.toString(post.getVotes())};
			writer.writeNext(data, false);
		}
		writer.close();
		System.out.println("Exported");
	}

	/**
	 * Thread runnable method that will be called upon thread.start()
	 */
	public void run() {
		System.out.println("Reddit Crawler");
		try {
			this.crawl();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
