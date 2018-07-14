package com.contribe.service;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.contribe.domainobject.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasketServiceIntegrationTest {
	
	@Autowired
	private BasketService service;
	
	
	private final Long userId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
	
	@Test
	public void test_add_book_to_basket() {
		Book book = new Book();
		book.setTitle("Random Sales");
		book.setAuthor("Cunning Bastard");
		book.setPrice(new BigDecimal("72.00"));
		
		assertTrue(service.addBookInBookstore(userId, book, 1));
	}
	
	@Test
	public void test_remove_book_from_basket() {
		
		Book book = new Book();
		book.setTitle("Random Sales");
		book.setAuthor("Cunning Bastard");
		book.setPrice(new BigDecimal("72.00"));
		
		assertTrue(service.addBookInBookstore(userId, book, 1));
	
		assertTrue(service.removeBookFromBookstore(userId, book, 1));
	}
	
	@Test
	public void test_add_existing_book_in_basket() {
		

		Book book = new Book();
		book.setTitle("Generic Title");
		book.setAuthor("First Author");
		book.setPrice(new BigDecimal("62.00"));
		
		assertTrue(service.addBookInBookstore(111L, book, 1));
	
	}


}
