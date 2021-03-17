package Spooding.Spooder;

import java.io.IOException;

import twitter4j.TwitterException;

public class TESTING {

	public static void main(String[] args) {
		/*
		 * TwitterCrawler test = new TwitterCrawler("Singapore", 100);
		 * test.setTopic("ALOHA123"); System.out.println(test.getTopic());
		 * test.setCount(50); System.out.println(test.getCount());
		 */

		STCrawler test2 = new STCrawler();
		try {
			test2.crawl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("SIZE IS " + test2.getListSize());
	}

}
