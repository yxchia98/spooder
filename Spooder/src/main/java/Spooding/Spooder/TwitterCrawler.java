package Spooding.Spooder;
import java.io.IOException;

import twitter4j.*;
import twitter4j.auth.AccessToken;

public class TwitterCrawler {
	
	//consumer key, secret consumer key, access token and secret access token of the twitter app
	private final static String CONSUMER_KEY = 
    		"RGabUCmPSveCJ3VfMkfJDLD4f";
    private final static String CONSUMER_KEY_SECRET =  
    		"lJQtpWbDf6JsMJOLGayo7M6WAEuwFbaGKoaHLMAPp903P4t54P";
    private final static String accessToken = 
    		"856759336692965376-YC6tmQ3ZFOFgtOTV8SxJdbeEO4yAsaJ";
	private final static String accessTokenSecret = 
			"oplA82eDLeesZ6scybEgNCjlhPfVKBEXlH0Ey4G61wVnQ";
	
	//Method to set the keys to start using the twitter crawler
	public static void twitterStart(Twitter twitter) {
		
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		
		AccessToken oathAccessToken = new AccessToken(accessToken,
		accessTokenSecret);
		
		twitter.setOAuthAccessToken(oathAccessToken);
	}
	
	//Method for searching tweets with some sample query fields, can add more
	public static QueryResult searchTweet(Twitter twitter, String topic, int count, String resultType, String language)  throws TwitterException, IOException {
		
		//Initialise Query
		//set the search topic
		Query q = new Query(topic);
		//set the number of tweets we want
		q.setCount(count);
		//set the result type
		if (resultType == "recent")
			q.setResultType(Query.RECENT);
		else if (resultType == "mix")
			q.setResultType(Query.MIXED);
		else if (resultType == "popular")
			q.setResultType(Query.POPULAR);
		else {
			System.out.println("Invalid result type, please enter \"recent\", \"mix\" or \"popular\"");
			return null;
		}
		//set tweet's language restriction
		q.setLang(language);
		
		QueryResult r = twitter.search(q);
		
		return r;
	}
	
	//Method for printing out the query results
	public static void printTweet(QueryResult r) {
		
		for (Status s: r.getTweets()) {
			System.out.printf("At %s, @%-15s :  %s\n",
								  s.getCreatedAt().toString(),
								  s.getUser().getScreenName(),
								  cleanString(s.getText()));
		}
	}
	
	//Method for cleaning a string
	public static String cleanString(String s) {	
		//Clean up string
		//remove leading and ending whitespaces, newline and tabs, usernames (with or without RT), links, 
		//non-letter char (including "#" in hashtags), multiple whitespaces to one
		String a = s.trim()
				.replaceAll("[\n\t]", " ")
				.replaceAll("RT ", "")
				.replaceAll("@[\\S]+ ", "")
				.replaceAll("http[s]:[\\S]+", "")
				.replaceAll("[^a-zA-Z ]", "")
				.replaceAll("[\\s]+", " ");
		
		return a;
	}
	
	
}
