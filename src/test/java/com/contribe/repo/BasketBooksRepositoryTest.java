package com.contribe.repo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.contribe.domainobject.BasketBooks;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketBooksRepositoryTest {
	
	@Autowired
	private BasketBooksRepository repo;
	
	@Test
	public void test_get_added_books() {
		List<BasketBooks> list = repo.findBooksAddedToBasket(101L);
		assertTrue(list.size() == 2);
	}
	
	@Test
	public void test_get_added_books_failure() {
		List<BasketBooks> list = repo.findBooksAddedToBasket(1L);
		assertFalse(list.size() == 3);
		
	}
	
	@Test
	public void test_get_book_added_in_basket_failure() {
		BasketBooks book = repo.findByBasketAndBook(102L, 101L);
		assertFalse(book != null);
	}
	
	@Test
	public void test_get_book_added_in_basket() {
		BasketBooks book = repo.findByBasketAndBook(103L, 101L);
		assertTrue(book != null);
	}

}
