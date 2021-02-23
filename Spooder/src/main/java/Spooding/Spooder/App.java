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
		int choice;
		Scanner input = new Scanner(System.in);
		System.out.print("Enter search string:");
		String searchString = input.next();
		url = "https://www.reddit.com/search/?q=" + searchString;

		// instantiate redditCrawler
		RedditCrawler redditCrawl = new RedditCrawler(url);
		// instantiate twitterCrawler
		TwitterCrawler twitterCrawler = new TwitterCrawler(searchString, 100);

		while (proceed) {
			System.out.print(
					"----------MAIN MENU----------\n1. Crawl from twitter\n2. Crawl from reddit\n3. Sentimental analysis with current dataset\n4. Exit\nEnter choice:");
			choice = input.nextInt();
			switch (choice) {
			case 1:
				TwitterCrawler.twitterStart();
				twitterCrawler.crawl();
				break;
			case 2:
				redditCrawl.crawl();
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
