package Spooding.Spooder;

public class RedditPost extends PostObject{
	private int votes;
	
	public RedditPost() {
		
	}
	public RedditPost(String title, int votes) {
		super(title);
		this.votes = votes;
	}
	public int getVotes() {
		return votes;
	}
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
}
