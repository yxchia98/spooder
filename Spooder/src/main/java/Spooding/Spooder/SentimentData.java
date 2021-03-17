package Spooding.Spooder;

import java.util.ArrayList;

/**
 * SentimentData class for storing various sentiment data to be used in Sentimental Analysis
 * Imports data from MongoDB
 *
 */
public class SentimentData extends MongoConnect {
	private int dataCount = 0; //Variable to count the number of sentimental analysis
	private int positiveCounter = 0; // Variable to count the number of positive sentiment
	private int negativeCounter = 0; // Variable to count the number of negative sentiment
	private int neutralCounter = 0; // Variable to count the number of neutral sentiment
	private int veryPositiveCounter = 0; // Variable to count the number of very positive sentiment
	private int veryNegativeCounter = 0; // Variable to count the number of very negative sentiment

	private String positiveString = "Positive"; // Positive string variable. Used for string compare for sentiment counter
	private String negativeString = "Negative"; // Negative string variable. Used for string compare for sentiment counter
	private String neutralString = "Neutral"; // Neutral string variable. Used for string compare for sentiment counter
	private String veryPositiveString = "Very positive"; //  Very Positive string variable. Used for string compare for sentiment counter
	private String veryNegativeString = "Very negative"; // Very negative string variable. Used for string compare for sentiment counter

	private String filePath = "src/main/java/Spooding/Spooder/"; // File path for CSV reading/writing

	private ArrayList<SentimentPost> allData = new ArrayList<SentimentPost>(); // Array list to store all data from all sources
	private ArrayList<SentimentPost> redditData = new ArrayList<SentimentPost>(); // Array list to store reddit data
	private ArrayList<SentimentPost> twitterData = new ArrayList<SentimentPost>(); // Array list to store twitter data
	private ArrayList<SentimentPost> STData = new ArrayList<SentimentPost>(); // Arrat list to store Straitstimes data

	private ArrayList<RedditPost> redditPost = importRedditMongo(); // Array list to store reddit posts from MongoDB database
	private ArrayList<TwitterPost> twitterPost = importTwitterMongo(); // Array list to store twitter posts from MongoDB database
	private ArrayList<STPost> STPost = importSTMongo(); // Array list to store straitstimes posts from MongoDB database
	
	/**
	 * Constructor
	 */
	public SentimentData() {

	}
	
	/**
	 * Get method to return dataCount value
	 * @return dataCount
	 */
	public int getDataCount() {
		return dataCount;
	}

	/**
	 * Get Method to return positiveCounter value
	 * @return positiveCounter
	 */
	public int getPositiveCounter() {
		return positiveCounter;
	}

	/**
	 * Set Method to modify positiveCounter variable
	 * @param positiveCounter
	 */
	public void setPositiveCounter(int positiveCounter) {
		this.positiveCounter = positiveCounter;
	}

	/**
	 * Get Method to return negativeCounter value
	 * @return negativeCounter
	 */
	public int getNegativeCounter() {
		return negativeCounter;
	}
	
	/**
	 * Set Method to modify negativeCounter variable
	 * @param negativeCounter
	 */
	public void setNegativeCounter(int negativeCounter) {
		this.negativeCounter = negativeCounter;
	}

	/**
	 * Get Method to return veryPositiveCounter value
	 * @return veryPositiveCounter
	 */
	public int getVeryPositiveCounter() {
		return veryPositiveCounter;
	}
	
	/**
	 * Set Method to modify veryPositiveCounter variable
	 * @param veryPositiveCounter
	 */
	public void setVeryPositiveCounter(int veryPositiveCounter) {
		this.veryPositiveCounter = veryPositiveCounter;
	}

	/**
	 * Get Method to return veryNegativeCounter value
	 * @return veryNegativeCounter
	 */
	public int getVeryNegativeCounter() {
		return veryNegativeCounter;
	}
	
	/**
	 * Set Method to modify veryNegativeCounter variable
	 * @param veryNegativeCounter
	 */
	public void setVeryNegativeCounter(int veryNegativeCounter) {
		this.veryNegativeCounter = veryNegativeCounter;
	}

	/**
	 * Get method to return neutralCounter value
	 * @return neutralCounter
	 */
	public int getNeutralCounter() {
		return neutralCounter;
	}

	/**
	 * Set Method to modify neutralCounter variable
	 * @param neutralCounter
	 */
	public void setNeutralCounter(int neutralCounter) {
		this.neutralCounter = neutralCounter;
	}

	/**
	 * Get Method to return positiveString
	 * @return positiveString
	 */
	public String getPositiveString() {
		return positiveString;
	}

	/**
	 * Get Method to return negativeString
	 * @return negativeString
	 */
	public String getNegativeString() {
		return negativeString;
	}
	
	/**
	 * Get Method to return neutralString
	 * @return neutralString
	 */
	public String getNeutralString() {
		return neutralString;
	}

	/**
	 * Get Method to return veryPositiveString
	 * @return veryPositiveString
	 */
	public String getVeryPositiveString() {
		return veryPositiveString;
	}

	/**
	 * Get Method to return veryNegativeString
	 * @return veryNegativeString
	 */
	public String getVeryNegativeString() {
		return veryNegativeString;
	}

	/**
	 * Get Method to return filePath string
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Set Method to modify filePath variable
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Method that loops through the posts from Reddit, twitter and Straitstimes
	 * and adds the titles/posts to a ArrayList
	 * @return allData ArrayList
	 */
	public ArrayList<SentimentPost> getAllData() {
		for (RedditPost post : redditPost) {
			SentimentPost sentimentPost = new SentimentPost(post.getTitle(), null, "reddit");
			allData.add(sentimentPost);
		}
		for (TwitterPost post : twitterPost) {
			SentimentPost sentimentPost = new SentimentPost(post.getTitle(), null, "twitter");
			allData.add(sentimentPost);
		}
		for (STPost post : STPost) {
			SentimentPost sentimentPost = new SentimentPost(post.getTitle(), null, "straitstimes");
			allData.add(sentimentPost);
		}
		return allData;
	}

	/**
	 * Method that loops through the posts from Reddit
	 * and adds the titles/posts to a ArrayList
	 * @return redditData ArrayList
	 */
	public ArrayList<SentimentPost> getRedditData() {
		for (RedditPost post : redditPost) {
			SentimentPost sentimentPost = new SentimentPost(post.getTitle(), null, "reddit");
			redditData.add(sentimentPost);
		}
		return redditData;
	}

	/**
	 * Method that loops through the posts from Twitter
	 * and adds the titles/posts to a ArrayList
	 * @return twitterData ArrayList
	 */
	public ArrayList<SentimentPost> getTwitterData() {
		for (RedditPost post : redditPost) {
			SentimentPost sentimentPost = new SentimentPost(post.getTitle(), null, "twitter");
			twitterData.add(sentimentPost);
		}
		return twitterData;
	}

	/**
	 * Method that loops through the posts from Straitstimes
	 * and adds the titles/posts to a ArrayList
	 * @return STData ArrayList
	 */
	public ArrayList<SentimentPost> getSTData() {
		for (STPost post : STPost) {
			SentimentPost sentimentPost = new SentimentPost(post.getTitle(), null, "straitstimes");
			STData.add(sentimentPost);
		}
		return STData;
	}

}
