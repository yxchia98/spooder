package Spooding.Spooder;
import java.io.IOException;

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
	}
	
	//Method for printing out the query results
	public static void printTweet(QueryResult r) {

		NLP.init();
		for (Status s: r.getTweets()) {
			System.out.printf("At %s, @%-15s :  %s\n",
								  s.getCreatedAt().toString(),
								  s.getUser().getScreenName(),
								  cleanString(s.getText()));
			
			int score = NLP.analyse(cleanString(s.getText()));
			System.out.println("Score: " + score);
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
	
	public void export() {
		
	}
}
