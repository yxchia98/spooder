package Spooding.Spooder;

public abstract class PostObject {
	private String title;
	
	public PostObject() {
		
	}
	
	public PostObject(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
