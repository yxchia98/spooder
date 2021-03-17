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
public class STCrawler extends Crawler{
	private String baseUrl = "https://www.straitstimes.com/tags/budget-2021?page=";
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
		int count = 0;
		driver = initWebDriver();
//		WebDriverWait wait = new WebDriverWait(driver, 10);
		CrawlProgressBar straitstimesBar = new CrawlProgressBar("Crawling The Straits Times...","crawlStraitstimes");
		System.out.println("Crawling from straits times...");
		driver.get(getBaseUrl() + String.valueOf(count));
		System.out.println("Website reached.");
//		Thread.sleep(5000);
		driver.navigate().refresh();
		Thread.sleep(3000);
		List<WebElement> list = driver.findElements(By.xpath("//span[@class='story-headline']"));
		for (WebElement listItem : list) {
			postArray.add(new STPost(listItem.getText()));
		}
		List<WebElement> nextList;
		while (list.size() < limit) {
//			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='pager-next']")));
//			element.click();
//			driver.findElement(By.xpath("//li[@class='pager-next']")).click();
			count++;
			driver.get(baseUrl + String.valueOf(count));
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
		straitstimesBar.close.setEnabled(true);
	}
	
	public void exportExcel() throws IOException {
		postArray = importSTMongo();
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
	
	/**
	 * Thread runnable method that will be called upon thread.start()
	 */
	public void run() {
		System.out.println("ST Crawler");
		try {
			this.crawl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
