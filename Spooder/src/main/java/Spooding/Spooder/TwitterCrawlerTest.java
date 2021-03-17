package Spooding.Spooder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwitterCrawlerTest {
	TwitterCrawler test = new TwitterCrawler("Singapore", 100);

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getTopicTest() {
		test.getTopic();
		assertEquals("Singapore", test.getTopic());
	}

	@Test
	void setTopicTestPass() {
		test.setTopic("Bangladesh");
		assertEquals("Bangladesh", test.getTopic());
	}

	@Test
	void setTopicTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setTopic("$&^@ you");
		});
	}

	@Test
	void getCountTest() {
		test.getCount();
		assertEquals(100, test.getCount());
	}

	@Test
	void setCountTestPass() {
		test.setCount(50);
		assertEquals(50,test.getCount());
	}

	@Test
	void setCountTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setCount(-100);
		});
	}

}
