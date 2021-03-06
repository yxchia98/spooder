package Spooding.Spooder;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import com.opencsv.CSVWriter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RedditCrawler extends Crawler {
	private String baseUrl;		//baseurl of reddit link based on search string
	private ArrayList<RedditPost> redditList = new ArrayList<>();	//arraylist to store post objects
	private WebDriver driver;
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RedditCrawler() {
		this.driver = initWebDriver();
	}
	
	public RedditCrawler(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void crawl() throws InterruptedException, IOException {
		driver = initWebDriver();
//launching the specified URL
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
		exportExcel();
		driver.close();
		driver.quit();
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

	public void exportExcel() throws IOException {
		System.out.println("Exporting Reddit data to Excel");
		CSVWriter writer = new CSVWriter(new FileWriter("reddit.csv"));
		for (RedditPost post : this.redditList) {
//			System.out.println(this.redditList.indexOf(post) + 1 + ": " + post.getTitle()); //print out post titles
//			System.out.println("Votes: " + post.getVotes()); //print out post votes
			String[] data = {post.getTitle(), Integer.toString(post.getVotes())};
			writer.writeNext(data, false);
		}
		writer.close();
		System.out.println("Exported");
	}

}
