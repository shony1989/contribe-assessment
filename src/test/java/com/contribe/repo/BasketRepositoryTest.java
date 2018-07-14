package com.contribe.repo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketRepositoryTest {
	
	@Autowired
	private BasketRepository repo;
	
	@Test
	public void test_get_basket_user() {	
		assertTrue(repo.findByUserId(111L) != null);
	}
	
	@Test
	public void test_get_basket_user_failure() {	
		assertFalse(repo.findByUserId(222L) != null);
	}

}
