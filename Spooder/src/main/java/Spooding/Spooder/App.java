package Spooding.Spooder;

import java.io.IOException;
import java.util.Scanner;
import com.opencsv.exceptions.CsvValidationException;

import twitter4j.*;


public class App {
	
	public void crawl(Crawler crawler) throws IOException, InterruptedException, TwitterException {
		crawler.crawl();
	}
	
	
	public static void main(String[] args) throws IOException, InterruptedException, TwitterException, CsvValidationException {
		Boolean proceed = true;
		String url;
		int choice, subChoice;
		Scanner input = new Scanner(System.in);
		System.out.print("Enter search string: ");
		String searchString = input.next();
		url = "https://www.reddit.com/search/?q=" + searchString;
		//instantiate App object, enabling polymorphism via App methods
		App crawlerProgram = new App();

		// instantiate redditCrawler
		Crawler redditCrawler = new RedditCrawler(url);
		// instantiate twitterCrawler
		Crawler twitterCrawler = new TwitterCrawler(searchString, 100);
//		twitterCrawler.twitterStart();
		//instantiate straits times crawler
		Crawler straitsCrawler = new STCrawler(50);
		
		
		SentimentalAnalysis sentimentalAnalysis = new SentimentalAnalysis();

		while (proceed) {
			System.out.print(
					"\n----------MAIN MENU----------\n1. Crawl from all sources\n2. Crawl from specific source\n3. Sentimental analysis with current dataset\n4. Exit\nEnter choice: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				crawlerProgram.crawl(twitterCrawler);
				crawlerProgram.crawl(redditCrawler);
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
				break;
			case 4:
				proceed = false;
				break;
			default:
				proceed = false;
				break;

			}
		}

	}
}
