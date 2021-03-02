package Spooding.Spooder;

public class TwitterPost extends PostObject{
	//configure twitter post variables
	//post title is already in PostObject class
	private String user;
	
	TwitterPost(){
		
	}
	TwitterPost(String title, String user){
		super(title);
		this.user = user;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}
