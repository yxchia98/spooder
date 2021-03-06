package Spooding.Spooder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import com.opencsv.CSVWriter;

import io.github.bonigarcia.wdm.WebDriverManager;
import twitter4j.TwitterException;

public class STCrawler extends Crawler {
	private String baseUrl = "https://www.straitstimes.com/tags/budget-2021";
	private int limit = 50;
	private ArrayList<STPost> postArray = new ArrayList<>();
	private WebDriver driver;

	public STCrawler() {
		this.driver = initWebDriver();
	}

	public STCrawler(int limit) {
		this.limit = limit;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void exportExcel() throws IOException {
		System.out.println("Exporting Straits Times articles data to Excel");
		CSVWriter writer = new CSVWriter(new FileWriter("straitstimes.csv"));
		for (STPost post : this.postArray) {
//			System.out.println(this.redditList.indexOf(post) + 1 + ": " + post.getTitle()); //print out post titles
//			System.out.println("Votes: " + post.getVotes()); //print out post votes
			String[] data = { post.getTitle() };
			writer.writeNext(data, false);
		}
		writer.close();
		System.out.println("Exported");
	}

	public void crawl() throws IOException, InterruptedException, TwitterException {
//launching the specified URL
		driver = initWebDriver();
		System.out.println("Crawling from straits times...");
		driver.get(getBaseUrl());
		System.out.println("Website reached.");
		Thread.sleep(1000);
		driver.navigate().refresh();
//		driver.switchTo().frame("2668504091318393559_0-frame");
//	    driver.switchTo().defaultContent();
//		System.out.println("Entered iframe");
//		driver.findElement(By.xpath(".//button[@data-lbl='Close Button']")).click();
//		driver.switchTo().defaultContent();
		List<WebElement> list = driver.findElements(By.xpath("//span[@class='story-headline']"));
		for (WebElement listItem : list) {
			STPost currPost = new STPost(listItem.getText());
			postArray.add(currPost);
		}
		List<WebElement> nextList;
		while (list.size() < limit) {
			driver.findElement(By.xpath("//li[@class='pager-next']")).click();
			nextList = driver.findElements(By.xpath("//span[@class='story-headline']"));
			for (WebElement listItem : nextList) {
				STPost currPost = new STPost(listItem.getText());
				postArray.add(currPost);
			}
			list.addAll(nextList);
			System.out.println(list.size());
		}
		exportExcel();
		driver.close();
		driver.quit();
	}

}
