package Spooding.Spooder;

/**
 * Sentiment post class used to store data to be exported to MongoDB database
 * Creates a mongoDB collection for string sentiment 
 *
 */
public class SentimentPost {
	private String title; // Variable for post title
	private String sentiment; // Variable for posts' sentiment
	private String source; // Variable for post source (Reddit, Twitter, Straitstimes)

	/**
	 * Constructor
	 */
	public SentimentPost() {

	}

	/**
	 * Constructor to create SentimentPost object containing post title, sentiment and source
	 * @param title title of post 
	 * @param sentiment sentiment analysis score of post
	 * @param source source which post came from
	 */
	public SentimentPost(String title, String sentiment, String source) {
		this.title = title;
		this.sentiment = sentiment;
		this.source = source;
	}

	/**
	 * Get Method to return title string
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set Method to modify title string
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get Method to return sentiment string
	 * @return sentiment
	 */
	public String getSentiment() {
		return sentiment;
	}

	/**
	 * Set Method to modify sentiment string
	 * @param sentiment
	 */
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	/**
	 * Get Method to return source string
	 * @return source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Set Method to modify Source string
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
