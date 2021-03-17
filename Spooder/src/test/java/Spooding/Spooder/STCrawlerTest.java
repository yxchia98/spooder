package Spooding.Spooder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class STCrawlerTest {

	STCrawler test = new STCrawler();

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void getBaseurlTest() {
		assertEquals("https://www.straitstimes.com/tags/budget-2021?page=", test.getBaseUrl());
	}

	@Test
	void setBaseurlTestPass() {
		test.setBaseUrl("http://www.facebook.com");
	}

	@Test
	void setBaseurlTestFail() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setBaseUrl("Straits Times");
		});
	}

	@Test
	void getLimitTest() {
		assertEquals(50, test.getLimit());
	}

	@Test
	void setLimitTestPass() {
		test.setLimit(80);
		assertEquals(80, test.getLimit());
	}

	@Test
	void setLimitTestFailOutofRange() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setLimit(200);
		});
	}

	@Test
	void setLimitTestFailNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			test.setLimit(-7);
		});
	}

}
