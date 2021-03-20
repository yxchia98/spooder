package Spooding.Spooder;

import java.io.IOException;
import com.opencsv.exceptions.CsvValidationException;
import twitter4j.*;

/**
 * Main Class for the crawler application
 */
public class App {
	
	//Polymorphism methods
	/**
	 * Polymorphism to crawl data based on crawler input
	 * @param crawler crawler type data variable
	 * @throws IOException Throws exception is related to Input and Output operations
	 * @throws InterruptedException Throws exception when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
	 * @throws TwitterException Throws exception when TwitterAPI calls are failed
	 */
	public void crawl(Crawler crawler) throws IOException, InterruptedException, TwitterException {
		crawler.crawl();
	}
	/**
	 * Polymorphism to export data into a .csv file based on crawler input
	 * @param crawler crawler type data variable
	 * @throws IOException Throws exception is related to Input and Output operations
	 */
	public void exportExcel(Crawler crawler) throws IOException {
		crawler.exportExcel();
	}
	
	/**
	 * Main
	 * @param args arguments
	 * @throws IOException Throws exception is related to Input and Output operations
	 * @throws InterruptedException Throws exception when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
	 * @throws TwitterException Throws exception when TwitterAPI calls are failed
	 * @throws CsvValidationException Exception thrown by a LineValidator or LineValidatorAggregator when a single line is invalid.
	 */
	public static void main(String[] args) throws IOException, InterruptedException, TwitterException, CsvValidationException {
		//instantiate App object, enabling polymorphism via App methods
		App crawlerProgram = new App();

		// instantiate redditCrawler
		Crawler redditCrawler = new RedditCrawler(50);
		// instantiate twitterCrawler
		Crawler twitterCrawler = new TwitterCrawler("singapore policy", 50);
//		twitterCrawler.twitterStart();
		//instantiate straits times crawler
		Crawler straitsCrawler = new STCrawler(50);
		
		WordCloudGenerator wordCloud = new WordCloudGenerator();

		new GUI(crawlerProgram, redditCrawler, twitterCrawler, straitsCrawler, wordCloud);
	}
}
