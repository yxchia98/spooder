package Spooding.Spooder;

import java.io.IOException;
import java.util.Scanner;
import com.opencsv.exceptions.CsvValidationException;

import twitter4j.*;

/**
 * Main Class for the crawler application
 */
public class App {
	
	//Polymorphism methods
	/**
	 * Method to crawl data based on crawler input
	 * @param crawler crawler type data variable
	 * @throws IOException Throws exception is related to Input and Output operations
	 * @throws InterruptedException Throws exception when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
	 * @throws TwitterException Throws exception when TwitterAPI calls are failed
	 */
	public void crawl(Crawler crawler) throws IOException, InterruptedException, TwitterException {
		crawler.crawl();
	}
	/**
	 * Method to export data into a .csv file based on crawler input
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
		Boolean proceed = true;
		String url;
		int choice, subChoice;
		Scanner input = new Scanner(System.in);
//		System.out.print("Enter search string: ");
//		String searchString = input.next();
//		url = "https://www.reddit.com/search/?q=" + searchString;
		//instantiate App object, enabling polymorphism via App methods
		App crawlerProgram = new App();

		// instantiate redditCrawler
		Crawler redditCrawler = new RedditCrawler();
		// instantiate twitterCrawler
		Crawler twitterCrawler = new TwitterCrawler("Singapore", 100);
//		twitterCrawler.twitterStart();
		//instantiate straits times crawler
		Crawler straitsCrawler = new STCrawler(50);
		//GUI gui = new GUI(crawlerProgram, redditCrawler, twitterCrawler, straitsCrawler);
		
		
		SentimentalAnalysis sentimentalAnalysis = new SentimentalAnalysis();
		WordCloudGenerator wordCloud = new WordCloudGenerator();

		while (proceed) {
			System.out.print(
					"\n----------MAIN MENU----------\n1. Crawl from all sources\n2. Crawl from specific source\n3. Sentimental analysis with current dataset\n4. Export Data to Excel\n5. Exit\nEnter choice: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				crawlerProgram.crawl(twitterCrawler);
				crawlerProgram.crawl(redditCrawler);
				crawlerProgram.crawl(straitsCrawler);
				break;
			case 2:
				System.out.print("\n----------Specific crawl----------\n1. Crawl from twitter\n2. Crawl from reddit\n3. Crawl from Straits Times\n4. Return\nEnter Choice: ");
				subChoice = input.nextInt();
				switch(subChoice) {
				case 1:
					crawlerProgram.crawl(twitterCrawler);
					break;
				case 2:
					crawlerProgram.crawl(redditCrawler);
					break;
				case 3:
					crawlerProgram.crawl(straitsCrawler);
					break;
				case 4:
					break;
				default: 
					break;
				}
				break;
			case 3:
				sentimentalAnalysis.Analyze();
				// generate word cloud for each sources
				wordCloud.setSource("twitter");
				wordCloud.generateCloud();
				wordCloud.setSource("reddit");
				wordCloud.generateCloud();
				wordCloud.setSource("straitstimes");
				wordCloud.generateCloud();
				break;
			case 4:
				System.out.print("\n----------Export Data to CSV----------\n1.Raw data\n2.Data after Sentiment Analysis\nEnter Choice:");
				subChoice = input.nextInt();
				switch(subChoice) {
				case 1:
					crawlerProgram.exportExcel(twitterCrawler);
					crawlerProgram.exportExcel(redditCrawler);
					crawlerProgram.exportExcel(straitsCrawler);
					break;
				case 2:
					//method to export all sentimented data to excel
					// work in progress
					break;
				case 3:
					break;
				default:
					break;
				}
				break;
			case 5:
				proceed = false;
				break;
			default:
				proceed = false;
				break;

			}
		}
		input.close();
	}
}
