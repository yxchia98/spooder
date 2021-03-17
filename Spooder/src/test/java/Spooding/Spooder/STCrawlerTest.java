package Spooding.Spooder;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import twitter4j.TwitterException;

class STCrawlerTest {

	STCrawler test = new STCrawler();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	/**
	 * Test to see if getBaseUrl() returns the correct default value
	 */
	void getBaseurlTest() {
		assertEquals("https://www.straitstimes.com/tags/budget-2021?page=", test.getBaseUrl());
	}

	@Test
	/**
	 * Test to successfully change the base URL
	 */
	void setBaseurlTestPass() {
		test.setBaseUrl("http://www.facebook.com");
	}

	@Test
	/**
	 * Test to throw exception when an invalid URL is provided
	 */
	void setBaseurlTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setBaseUrl("Straits Times");
		});
	}

	@Test
	/**
	 * Test to see if getLimit() returns the default correct value
	 */
	void getLimitTest() {
		assertEquals(50, test.getLimit());
	}

	@Test
	/**
	 * Test to successfully change the limit value
	 */
	void setLimitTestPass() {
		test.setLimit(80);
		assertEquals(80, test.getLimit());
	}

	@Test
	/**
	 * Test to throw exception when a value >100 is entered
	 */
	void setLimitTestFailOutofRange() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setLimit(200);
		});
	}

	@Test
	/**
	 * Test to throw exception when a negative value is entered
	 */
	void setLimitTestFailNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setLimit(-7);
		});
	}

	@Test
	/**
	 * Test to check if the crawler has crawl at least a specified number of posts
	 * defined by the limit variable
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
		}
		assertTrue(test.getListSize() >= test.getLimit());

	}

}
