package com.tweetapp.TweetApp;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TweetAppApplicationTests {

	@Mock
	TweetAppApplication TweetAppApplication;

	@Test
	void contextLoads() {
		assertNotNull(TweetAppApplication);
	}

}
