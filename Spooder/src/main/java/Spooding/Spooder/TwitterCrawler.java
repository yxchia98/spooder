package Spooding.Spooder;

import java.awt.Cursor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import edu.stanford.nlp.tagger.io.TaggedFileRecord.Format;
import twitter4j.*;
import twitter4j.auth.AccessToken;

/**
 * Twitter Crawler Class
 */
public class TwitterCrawler extends Crawler {

	// consumer key, secret consumer key, access token and secret access token of
	// the twitter app
	private final static String CONSUMER_KEY = "RGabUCmPSveCJ3VfMkfJDLD4f";
	private final static String CONSUMER_KEY_SECRET = "lJQtpWbDf6JsMJOLGayo7M6WAEuwFbaGKoaHLMAPp903P4t54P";
	private final static String accessToken = "856759336692965376-YC6tmQ3ZFOFgtOTV8SxJdbeEO4yAsaJ";
	private final static String accessTokenSecret = "oplA82eDLeesZ6scybEgNCjlhPfVKBEXlH0Ey4G61wVnQ";

	private static Twitter twitter;
	private String topic;
	private int count;
	private ArrayList<TwitterPost> twitterList = new ArrayList<>();

	/**
	 * Constructor for the TwitterCrawler Class
	 * 
	 * @param topic What topic to search for
	 * @param count How many queries
	 */

	public TwitterCrawler(String topic, int count) {
		this.topic = topic;
		this.count = count;
	}

	/**
	 * Get method to return topic variable
	 * 
	 * @return topic topic to crawl for
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Set method to set topic variable
	 * 
	 * @param topic topic to crawl for
	 */
	public void setTopic(String topic) throws IllegalArgumentException {
		if (!topic.matches("[a-zA-Z0-9]+")) {
			throw new IllegalArgumentException("Topic should only contain letters and numbers");
		}
		this.topic = topic;
	}

	/**
	 * Get method to get count variable
	 * 
	 * @return number of queries to search for
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Set method to modify count variable
	 * 
	 * @param count number of queries to search for
	 */
	public void setCount(int count) throws IllegalArgumentException {
		if (count < 0) {
			throw new IllegalArgumentException("Value of count should be more than 0");
		}
		if (count > 100) {
			throw new IllegalArgumentException("Maximum value of count is 100");
		}
		this.count = count;
	}

	/**
	 * Method to set the keys to start using the twitter crawler
	 */
	public void twitterStart() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

		AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);

		twitter.setOAuthAccessToken(oathAccessToken);

	}

	/**
	 * Method to return size of array list
	 * 
	 * @return size of list
	 */
	public int getListSize() {
		return twitterList.size();
	}

	/**
	 * Method for searching Tweets with some sample query fields, can add more
	 */
	public void crawl() throws IOException, InterruptedException, TwitterException {
		twitterList.clear();
		// set configurations for twitter crawler
		this.twitterStart();
		// Initialise Query
		// set the search topic, filter out retweets and replies
		Query q = new Query(topic.concat(" -filter:retweets -filter:replies"));
		// set the number of tweets we want
		q.setCount(count);
		// set the result type
		q.setResultType(Query.MIXED);
		// set tweet's language restriction
		q.setLang("en");

		QueryResult r = twitter.search(q);

		printTweet(r);
		exportTwitterMongo(twitterList);
	}

	/**
	 * Method for printing out query results
	 * 
	 * @param r variable that stores the Query results
	 * @throws IOException exception regarding input/output
	 */
	public void printTweet(QueryResult r) throws IOException {
		for (Status s : r.getTweets()) {

			System.out.printf("At %s, @%-15s :  %s\n", s.getCreatedAt().toString(), s.getUser().getScreenName(),
					cleanString(s.getText()));

			// instantiate new twitter object
			TwitterPost currentTweet = new TwitterPost(cleanString(s.getText()), s.getUser().getScreenName());
			this.twitterList.add(currentTweet);
		}
	}

	/**
	 * Method for filtering unwanted info from String such as tabs, newlines, etc
	 * 
	 * @param s String to be cleaned/filtered
	 * @return the cleaned up string after filtering
	 */
	public static String cleanString(String s) {
		// Clean up string
		String text = s.trim()
				// remove tabs and newlines
				.replaceAll("[\n\t]", " ")
				// remove retweet "RT" tag
				.replaceAll("RT ", "")
				// remove username tag
				.replaceAll("@[\\S]+ ", "")
				// remove links
				.replaceAll("http[s]:[\\S]+", "")
				// remove non-letter characters
				.replaceAll("[^a-zA-Z ]", "")
				// merge multiple white spaces to a single white space
				.replaceAll("[\\s]+", " ");

		return text;
	}

	/**
	 * Method to export data in to a .csv file type
	 */
	public void exportExcel() throws IOException {
		twitterList = importTwitterMongo();
		if (twitterList.isEmpty()) {
			System.out.println("No twitter data to export");
			return;
		}
		System.out.println("Exporting Twitter data to Excel");
		List<String[]> writeList = new ArrayList<>();
		CSVWriter writer = new CSVWriter(new FileWriter("twitter.csv"));
		for (TwitterPost post : twitterList) {
			String[] data = {post.getUser(), post.getTitle()};
			writeList.add(data);
		}
		writer.writeAll(writeList, false);
		writer.close();
		System.out.println("Exported");
	}

	/**
	 * Thread runnable method that will be called upon thread.start()
	 */
	public void run() {
		System.out.println("Twitter Crawler");
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
