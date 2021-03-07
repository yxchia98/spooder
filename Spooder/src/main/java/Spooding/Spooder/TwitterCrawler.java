package Spooding.Spooder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import twitter4j.*;
import twitter4j.auth.AccessToken;

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
	
	public TwitterCrawler(String topic, int count) {
		this.topic = topic;
		this.count = count;
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	//Method to set the keys to start using the twitter crawler
	public void twitterStart() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		
		AccessToken oathAccessToken = new AccessToken(accessToken,
		accessTokenSecret);
		
		twitter.setOAuthAccessToken(oathAccessToken);
		
	}
	
	//Method for searching tweets with some sample query fields, can add more
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
		exportExcel();
	}
	
	//Method for printing out the query results
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
	
	//Method for cleaning a string
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
	
	public void exportExcel() throws IOException {
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
}
