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
	/**
	 * Test to see if getTopic() function returns the correct value
	 */
	void getTopicTest() {
		test.getTopic();
		assertEquals("Singapore", test.getTopic());
	}

	@Test
	/**
	 * Test to successfully change the topic
	 */
	void setTopicTestPass() {
		test.setTopic("Bangladesh");
		assertEquals("Bangladesh", test.getTopic());
	}

	@Test
	/**
	 * Test to throw exception if topic is entered with anything other than letters and numbers
	 */
	void setTopicTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setTopic("$&^@ you");
		});
	}

	@Test
	/**
	 * Test to see if getCount() function returns the correct value
	 */
	void getCountTest() {
		test.getCount();
		assertEquals(100, test.getCount());
	}

	@Test
	/**
	 * Test to successfully set count to another value
	 */
	void setCountTestPass() {
		test.setCount(50);
		assertEquals(50, test.getCount());
	}

	@Test
	/**
	 * Test to throw exception if a negative number is to be set for count
	 * count should not be a negative value
	 */
	void setCountTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setCount(-100);
		});
	}

}
