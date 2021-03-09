package Spooding.Spooder;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bson.Document;
import org.openqa.selenium.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.opencsv.CSVWriter;

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
//		Thread.sleep(5000);
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
		exportMongo();
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
		if (redditList.isEmpty()) {
			System.out.println("No reddit data to export");
			return;
		}
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
	
	public void exportMongo() {
		boolean exist = false;
		//connect to mongoDB atlas
		MongoClient mongoClient = MongoClients.create(
				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("spooder");
		//check if specified collection is in database
		for (String name : database.listCollectionNames()){
			if (name.equals("reddit")) {
				exist = true;
			}
		}
		if (!exist) {
			database.createCollection("reddit");
			System.out.println("reddit collection created.");
		}
		MongoCollection<Document> collection = database.getCollection("reddit");
		//first clear all documents in collection, to avoid duplications from multiple crawls
		collection.deleteMany(new Document());
		System.out.println("Connected to MongoDB");
		for (RedditPost post : redditList) {
			Document doc = new Document();
			doc.append("Title", post.getTitle());
			doc.append("Votes", post.getVotes());
			collection.insertOne(doc);
		}
		mongoClient.close();

	}

}
