package Spooding.Spooder;

import java.io.IOException;
import java.util.Scanner;

/**
 * Example program to list links from a URL.
 */
public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
    	Scanner input = new Scanner(System.in);
    	System.out.print("Enter search string:");
    	String searchString = input.next();
    	String url = "https://www.reddit.com/search/?q=" + searchString;
    	RedditCrawler redditCrawl = new RedditCrawler(url);
    	redditCrawl.crawl();
    }
}
