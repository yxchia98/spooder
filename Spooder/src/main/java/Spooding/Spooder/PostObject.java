package Spooding.Spooder;
/**
 * PostObject Class
 */
public abstract class PostObject {
	private String title;
	/**
	 * Method Constructor
	 */
	public PostObject() {
		
	}
	/**
	 * Method???
	 * @param title Title of Post
	 */
	public PostObject(String title) {
		this.title = title;
	}
	/**
	 * Get Method to return title variable
	 * @return Title of Post
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Set Method to modify title variable
	 * @param title Title of Post
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
}
