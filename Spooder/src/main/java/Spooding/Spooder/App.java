package Spooding.Spooder;

import java.io.IOException;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

import twitter4j.*;

/**
 * Example program to list links from a URL.
 */
public class App {
	public static void main(String[] args) throws IOException, InterruptedException, TwitterException, CsvValidationException {
		Boolean proceed = true;
		String url;
		int choice, subChoice;
		Scanner input = new Scanner(System.in);
		System.out.print("Enter search string: ");
		String searchString = input.next();
		url = "https://www.reddit.com/search/?q=" + searchString;

		// instantiate redditCrawler
		RedditCrawler redditCrawl = new RedditCrawler(url);
		// instantiate twitterCrawler
		TwitterCrawler twitterCrawler = new TwitterCrawler(searchString, 100);
		twitterCrawler.twitterStart();
		
		SentimentalAnalysis sentimentalAnalysis = new SentimentalAnalysis();

		while (proceed) {
			System.out.print(
					"\n----------MAIN MENU----------\n1. Crawl from all sources\n2. Crawl from specific source\n3. Sentimental analysis with current dataset\n4. Exit\nEnter choice: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				redditCrawl.crawl();
				twitterCrawler.crawl();
				break;
			case 2:
				System.out.print("\n----------Specific crawl----------\n1. Crawl from twitter\n2. Crawl from reddit\n3. return\nChoice: ");
				subChoice = input.nextInt();
				switch(subChoice) {
				case 1:
					twitterCrawler.crawl();
					break;
				case 2:
					redditCrawl.crawl();
					break;
				case 3:
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
