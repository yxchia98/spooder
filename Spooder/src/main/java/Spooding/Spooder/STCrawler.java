package Spooding.Spooder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.CSVWriter;
import twitter4j.TwitterException;
/**
 * Straits Times Crawler Class
 */
public class STCrawler extends Crawler {
	private String baseUrl = "https://www.straitstimes.com/tags/budget-2021";
	private int limit = 50;
	private ArrayList<STPost> postArray = new ArrayList<>();
	private WebDriver driver;
	/**
	 * Method???
	 */
	public STCrawler() {
		this.driver = initWebDriver();
	}
	/**
	 * Method???
	 * @param limit
	 */
	public STCrawler(int limit) {
		this.limit = limit;
	}
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
	 * Get Method to return limit variable
	 * @return Limit
	 */
	public int getLimit() {
		return limit;
	}
	/**
	 * Set method to modify limit variable
	 * @param limit Limit
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void crawl() throws IOException, InterruptedException, TwitterException {
//launching the specified URL
		driver = initWebDriver();
		System.out.println("Crawling from straits times...");
		driver.get(getBaseUrl());
		System.out.println("Website reached.");
//		Thread.sleep(3000);
		driver.navigate().refresh();
		Thread.sleep(3000);
//		driver.switchTo().frame("2668504091318393559_0-frame");
//	    driver.switchTo().defaultContent();
//		System.out.println("Entered iframe");
//		driver.findElement(By.xpath(".//button[@data-lbl='Close Button']")).click();
//		driver.switchTo().defaultContent();
		List<WebElement> list = driver.findElements(By.xpath("//span[@class='story-headline']"));
		for (WebElement listItem : list) {
			postArray.add(new STPost(listItem.getText()));
		}
		List<WebElement> nextList;
		while (list.size() < limit) {
			driver.findElement(By.xpath("//li[@class='pager-next']")).click();
			Thread.sleep(1000);
			nextList = driver.findElements(By.xpath("//span[@class='story-headline']"));
			for (WebElement listItem : nextList) {
				postArray.add(new STPost(listItem.getText()));
			}
			list.addAll(nextList);
			System.out.println(list.size());
		}
		exportSTMongo(postArray);
		driver.close();
		driver.quit();
	}
	
	public void exportExcel() throws IOException {
		if (postArray.isEmpty()) {
			System.out.println("No straitstimes data to export");
			return;
		}
		System.out.println("Exporting Straits Times articles data to Excel");
		CSVWriter writer = new CSVWriter(new FileWriter("straitstimes.csv"));
		for (STPost post : this.postArray) {
			String[] data = { post.getTitle() };
			writer.writeNext(data, false);
		}
		writer.close();
		System.out.println("Exported");
	}

//	public void exportMongo() {
//		boolean exist = false;
//		//connect to mongoDB atlas
//		MongoClient mongoClient = MongoClients.create(
//				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
//		MongoDatabase database = mongoClient.getDatabase("spooder");
//		//check if specified collection is in database
//		for (String name : database.listCollectionNames()){
//			if (name.equals("straitstimes")) {
//				exist = true;
//			}
//		}
//		if (!exist) {
//			database.createCollection("straitstimes");
//			System.out.println("straitstimes collection created.");
//		}
//		MongoCollection<Document> collection = database.getCollection("straitstimes");
//		//first clear all documents in collection, to avoid duplications from multiple crawls
//		collection.deleteMany(new Document());
//		System.out.println("Connected to MongoDB");
//		for (STPost post : postArray) {
//			Document doc = new Document();
//			doc.append("Title", post.getTitle());
//			collection.insertOne(doc);
//		}
//		mongoClient.close();
//
//	}
	

}
