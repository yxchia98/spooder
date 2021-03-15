package Spooding.Spooder;
/**
 * Reddit Post Class
 */
public class RedditPost extends PostObject{
	private int votes;
	/**
	 * Method Constructor
	 */
	public RedditPost() {
		
	}
	/**
	 * Method...
	 * @param title
	 * @param votes
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
