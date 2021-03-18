package Spooding.Spooder;
/**
 * Twitter Post Class. Objects for posts crawled from twitter platform.
 */
public class TwitterPost extends PostObject{
	//configure twitter post variables
	//post title is already in PostObject class
	private String user;
	/**
	 * Method Constructor
	 */
	public TwitterPost(){
		
	}
	/**
	 * Constructor, to initialize title and user parsed in
	 * @param title
	 * @param user
	 */
	public TwitterPost(String title, String user){
		super(title);
		this.user = user;
	}
	/**
	 * Get Method to return user variable
	 * @return Username of Twitter user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * Set Method to modify user variable
	 * @param user Username of Twitter user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
}
