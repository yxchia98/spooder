package Spooding.Spooder;
/**
 * Reddit Post Class. Objects for posts crawled from reddit platform.
 */
public class RedditPost extends PostObject{
	private int votes;
	/**
	 * Constructor
	 */
	public RedditPost() {
		
	}
	/**
	 * Constructor, to initilize titles and votes parsed in
	 * @param title title of post crawled
	 * @param votes votes of post crawled
	 */
	public RedditPost(String title, int votes) {
		super(title);
		this.votes = votes;
	}
	/**
	 * Get Method to return votes variable
	 * @return vote count
	 */
	public int getVotes() {
		return votes;
	}
	/**
	 * Set Method to set votes variable
	 * @param votes vote count
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
}
