package Spooding.Spooder;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.*;

import com.opencsv.CSVWriter;

/**
 * Reddit Crawler. 
 * Contains methods to crawl individual posts from reddit,
 * afterwards uploading them into MongoDB
 */
public class RedditCrawler extends Crawler {
	private String baseUrl; // baseurl of reddit link based on search string
	private int limit = 50;
	private ArrayList<RedditPost> redditList = new ArrayList<>(); // arraylist to store post objects
	private WebDriver driver; // selenium webdriver variable

	/**
	 * Get Method to return URL
	 * 
	 * @return URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Set Method to modify baseUrl variable
	 * 
	 * @param baseUrl URL
	 */
	public void setBaseUrl(String baseUrl) throws IllegalArgumentException {
		if (!baseUrl.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")) {
			throw new IllegalArgumentException("Not a valid URL or incomplete (missing https)");
		}
		this.baseUrl = baseUrl;
	}

	/**
	 * Get Method to return limit variable
	 * 
	 * @return Limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * Set method to modify limit variable
	 * 
	 * @param limit Limit
	 */
	public void setLimit(int limit) throws IllegalArgumentException {
		if (limit < 0 || limit > 100) {
			throw new IllegalArgumentException("Limit should be between 0-100");
		}
		this.limit = limit;
	}

	/**
	 * Default Constructor Method to set default URL if none is provided, default to
	 * budget 2021 policies
	 */
	public RedditCrawler() {
		this.baseUrl = "https://www.reddit.com/r/singapore/search/?q=budget%20flair%3ANews%20OR%20flair%3APolitics%20OR%20flair%3AOpinion_Article%20OR%20flair%3ASerious_Discussion&restrict_sr=1";
	}

	/**
	 * Specified Constructor A different reddit url can be specified based on user
	 * requirements.
	 * 
	 * @param baseUrl Base URL of Reddit to crawl from
	 */
	public RedditCrawler(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Method to return size of array list
	 * 
	 * @return size of list
	 */
	public int getListSize() {
		return redditList.size();
	}

	/**
	 * Initiate crawling from reddit, crawling till a minimum of specified individual posts, post's
	 * titles with their respective vote scores will be scraped and uploaded into
	 * the reddit collection in MongoDB
	 */
	public void crawl() throws InterruptedException, IOException {
		redditList.clear();
		driver = initWebDriver();
		CrawlProgressBar redditBar = new CrawlProgressBar("Crawling Reddit...", "crawlReddit");
		System.out.println("Crawling from reddit...");
		driver.get(getBaseUrl());
		System.out.println("Website reached.");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(4000); // pause the browser to let javascript load new posts
		List<WebElement> list = driver.findElements(By.xpath("//div[contains(@class, '_1oQyIsiPHYt6nx7VOmd1sz')]"));
		while (list.size() < limit) {
			js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
			Thread.sleep(4000); // pause the browser to let javascript load new posts
			list = driver.findElements(By.xpath("//div[contains(@class, '_1oQyIsiPHYt6nx7VOmd1sz')]"));
		}
		System.out.println("Retrieved " + list.size() + " posts");

		for (WebElement listItem : list) {
			WebElement postTitle = listItem.findElement(By.xpath(".//a[@data-click-id='body']"));
			WebElement postVote = listItem
					.findElement(By.xpath(".//div[@class='_1rZYMD_4xY3gRcSS3p8ODO _3a2ZHWaih05DgAOtvu6cIo']"));
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
	 * 
	 * @param votes
	 * @return vote count after formatting
	 */
	private int formatVotes(String votes) {
		double result;
		String formattedString;
		if (votes.equals("Vote")) {
			return 0;
		}
		formattedString = votes.replaceAll("[^0-9\\.]", "");
		result = Double.parseDouble(formattedString);
		if (votes.contains("k")) {
			result *= 1000;
		} else if (votes.contains("m")) {
			result *= 1000000;
		}
		return (int) result;

	}

	/**
	 * Exports crawled reddit data from MongoDB into Excel CSV An Excel CSV file
	 * named 'reddit' will be generated.
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
			String[] data = { post.getTitle(), Integer.toString(post.getVotes()) };
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
