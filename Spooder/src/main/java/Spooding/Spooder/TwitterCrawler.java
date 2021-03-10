package Spooding.Spooder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.opencsv.CSVWriter;

import twitter4j.*;
import twitter4j.auth.AccessToken;
/**
 * Class for Twitter Crawler
 */
public class TwitterCrawler extends Crawler {
	
	//consumer key, secret consumer key, access token and secret access token of the twitter app
	private final static String CONSUMER_KEY = 
    		"RGabUCmPSveCJ3VfMkfJDLD4f";
    private final static String CONSUMER_KEY_SECRET =  
    		"lJQtpWbDf6JsMJOLGayo7M6WAEuwFbaGKoaHLMAPp903P4t54P";
    private final static String accessToken = 
    		"856759336692965376-YC6tmQ3ZFOFgtOTV8SxJdbeEO4yAsaJ";
	private final static String accessTokenSecret = 
			"oplA82eDLeesZ6scybEgNCjlhPfVKBEXlH0Ey4G61wVnQ";
	
	private static Twitter twitter;
	private String topic;
	private int count;
	private ArrayList<TwitterPost> twitterList = new ArrayList<>();
	
	/**
	 * Constructor for the TwitterCrawler Class
	 * @param topic What topic to search for
	 * @param count How many queries
	 */
	
	public TwitterCrawler(String topic, int count) {
		this.topic = topic;
		this.count = count;
	}
	
	/**
	 * Get method to return topic variable
	 * @return topic topic to crawl for
	 */
	public String getTopic() {
		return topic;
	}
	/**
	 * Set method to set variable topic
	 * @param topic topic to crawl for
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}
	/**
	 * Get method to get variable count
	 * @return number of queries to search for
	 */
	public int getCount() {
		return count;
	}
	/**
	 * Set method to modify variable count
	 * @param count number of queries to search for
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * Method to set the keys to start using the twitter crawler
	 */
	public void twitterStart() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		
		AccessToken oathAccessToken = new AccessToken(accessToken,
		accessTokenSecret);
		
		twitter.setOAuthAccessToken(oathAccessToken);
		
	}
	
	/**
	 * Method for searching Tweets with some sample query fields, can add more
	 */
	public void crawl() throws IOException, InterruptedException, TwitterException {
		//set configurations for twitter crawler
		this.twitterStart();
		//Initialise Query
		//set the search topic, filter out retweets and replies
		Query q = new Query(topic.concat(" -filter:retweets -filter:replies"));
		//set the number of tweets we want
		q.setCount(count);
		//set the result type
		q.setResultType(Query.MIXED);
		//set tweet's language restriction
		q.setLang("en");
		
		QueryResult r = twitter.search(q);
		
		printTweet(r);
		exportMongo();
	}
	
	/**
	 * Method for printing out query results
	 * @param r variable that stores the Query results
	 * @throws IOException exception regarding input/output
	 */
	public void printTweet(QueryResult r) throws IOException {

		for (Status s: r.getTweets()) {
			System.out.printf("At %s, @%-15s :  %s\n",
								  s.getCreatedAt().toString(),
								  s.getUser().getScreenName(),
								  cleanString(s.getText()));
			
			//instantiate new twitterobject
			TwitterPost currentTweet = new TwitterPost(cleanString(s.getText()), s.getUser().getScreenName());
			this.twitterList.add(currentTweet);
		}
	}
	
	/**
	 * Method for filtering unwanted info from String such as tabs, newlines, etc
	 * @param s String to be cleaned/filtered
	 * @return the cleaned up string after filtering
	 */
	public static String cleanString(String s) {	
		//Clean up string
		String text = s.trim()
				// remove tabs and newlines
				.replaceAll("[\n\t]", " ")
				// remove retweet "RT" tag
				.replaceAll("RT ", "")
				// remove usernames
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
		if (twitterList.isEmpty()) {
			System.out.println("No twitter data to export");
			return;
		}
		System.out.println("Exporting Twitter data to Excel");
		List<String[]> writeList = new ArrayList<>();
		CSVWriter writer = new CSVWriter(new FileWriter("twitter.csv"));
		for(TwitterPost post : twitterList) {
//			System.out.print(twitterList.indexOf(post)+1 + ". @" + post.getUser() + ": " + post.getTitle() + "\n");		//print out user handles and tweets
//			String[] data = {post.getUser(), post.getTitle()};
			String[] data = {post.getTitle()};
			writeList.add(data);
		}
		writer.writeAll(writeList, false);
		writer.close();
		System.out.println("Exported");
	}
	/**
	 * Method to export data into MongoDB
	 */
	public void exportMongo() {
		boolean exist = false;
		//connect to mongoDB atlas
		MongoClient mongoClient = MongoClients.create(
				"mongodb+srv://crawlerAdmin:spooder@cluster0.whwla.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
		MongoDatabase database = mongoClient.getDatabase("spooder");
		//check if specified collection is in database
		for (String name : database.listCollectionNames()){
			if (name.equals("twitter")) {
				exist = true;
			}
		}
		if (!exist) {
			database.createCollection("twitter");
			System.out.println("twitter collection created.");
		}
		MongoCollection<Document> collection = database.getCollection("twitter");
		//first clear all documents in collection, to avoid duplications from multiple crawls
		collection.deleteMany(new Document());
		System.out.println("Connected to MongoDB");
		for (TwitterPost post : twitterList) {
			Document doc = new Document();
			doc.append("Title", post.getTitle());
			doc.append("User", post.getUser());
			collection.insertOne(doc);
		}
		mongoClient.close();

	}
}
