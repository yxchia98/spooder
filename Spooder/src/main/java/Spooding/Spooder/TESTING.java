package Spooding.Spooder;

public class TESTING {
	
	public static void main(String[] args) {
		TwitterCrawler test = new TwitterCrawler("Singapore", 100);
		test.setTopic("ALOHA123");
		System.out.println(test.getTopic());
		test.setCount(50);
		System.out.println(test.getCount());
	}
	
}
