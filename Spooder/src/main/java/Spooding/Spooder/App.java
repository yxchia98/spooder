package Spooding.Spooder;

import java.io.IOException;
import java.util.Scanner;
import twitter4j.*;

/**
 * Example program to list links from a URL.
 */
public class App {
	public static void main(String[] args) throws IOException, InterruptedException, TwitterException {
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

		while (proceed) {
			System.out.print(
					"----------MAIN MENU----------\n1. Crawl from all sources\n2. Crawl from specific source\n3. Sentimental analysis with current dataset\n4. Exit\nEnter choice: ");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				redditCrawl.crawl();
				twitterCrawler.crawl();
				break;
			case 2:
				System.out.print("----------Specific crawl----------\n1. Crawl from twitter\n2. Crawl from reddit\n3. return\nChoice: ");
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
