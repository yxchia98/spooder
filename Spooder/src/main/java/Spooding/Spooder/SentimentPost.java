package Spooding.Spooder;


public class SentimentPost {
	private String title;
	private String sentiment;
	private String source;

	public SentimentPost() {

	}

	public SentimentPost(String title, String sentiment, String source) {
		this.title = title;
		this.sentiment = sentiment;
		this.source = source;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSentiment() {
		return sentiment;
	}

	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
