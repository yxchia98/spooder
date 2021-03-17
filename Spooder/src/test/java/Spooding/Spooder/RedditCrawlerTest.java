package Spooding.Spooder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RedditCrawlerTest {

	RedditCrawler test = new RedditCrawler();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getBaseurlTest() {
		assertEquals(
				"https://www.reddit.com/r/singapore/search/?q=budget%20flair%3ANews%20OR%20flair%3APolitics%20OR%20flair%3AOpinion_Article%20OR%20flair%3ASerious_Discussion&restrict_sr=1",
				test.getBaseUrl());
	}

	@Test
	void setBaseurlTestPass() {
		test.setBaseUrl("http://www.google.com");
	}

	@Test
	void setBaseurlTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setBaseUrl("Richard");
		});
	}

}
