package com.tweetapp.TweetApp;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TweetAppApplicationTests {

	@InjectMocks
	TweetAppApplication TweetAppApplication;
	@Test
	void contextLoads() {
		assertNotNull(TweetAppApplication);
	}

}
