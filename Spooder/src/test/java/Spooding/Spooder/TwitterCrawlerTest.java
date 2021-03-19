package Spooding.Spooder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import twitter4j.TwitterException;

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
	 * Test to throw exception if topic is entered with anything other than letters
	 * and numbers
	 */
	void setTopicTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setTopic("$&^@");
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
	 * Test to throw exception if a negative number is to be set for count, count
	 * should not be a negative value
	 */
	void setCountTestFailNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setCount(-100);
		});
	}
	
	@Test
	/**
	 * Test to throw exception when a value >100 is entered
	 */
	void setCountTestFailOutofRange() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setCount(200);
		});
	}

	@Test
	/**
	 * Test to check if the crawler has crawl at least a specified number of posts
	 * defined by the count variable
	 */
	void checkListSize() {
		try {
			test.crawl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("LIST SIZE" + test.getListSize());
		// System.out.println("COUNT SIZE" + test.getCount());
		assertTrue(test.getListSize() >= test.getCount());
	}

}
