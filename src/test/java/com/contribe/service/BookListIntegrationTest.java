package com.contribe.service;


import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.contribe.domainobject.Book;
import com.contribe.repo.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookListIntegrationTest {
	
	@Autowired
	private BookList service;
	@Autowired
	private BookRepository repo;
	
	@Test
	public void add_book_to_bookstore_test() {
		
		Book book = new Book();
		book.setTitle("Contribe test");
		book.setAuthor("Arpan");
		book.setPrice(new BigDecimal("200.20"));
		
		boolean added = service.add(book, 3);
		assertTrue(added);
	}
	
	@Test
	public void add_Existing_book_to_bookstore_test() {
		
		Book book = new Book();
		book.setTitle("Random Sales");
		book.setAuthor("Cunning Bastard");
		book.setPrice(new BigDecimal("72.00"));
		
		boolean added = service.add(book, 3);
		assertTrue(added);
		
		Book addedBook = 
				repo.findByTitleAndAuthorAndPrice("Random Sales", "Cunning Bastard", new BigDecimal("72.00"));
		
		assertTrue(addedBook.getQuantity() == 22);
	}
	
	@Test
	public void test_status_when_buy() {
		
		Book[] book = new Book[2];
		
 		Book book1 = new Book();
		book1.setTitle("Generic Title");
		book1.setAuthor("First Author");
		book1.setPrice(new BigDecimal("62.00"));
		book1.setQuantity(10);
		book1.setBasketQuantity(8);
		book[0] = book1;
		
		Book book2 = new Book();
		book2.setTitle("Generic Title");
		book2.setAuthor("Second Author");
		book2.setPrice(new BigDecimal("63.00"));
		book2.setQuantity(0);
		book2.setBasketQuantity(2);
		book[1] = book2;
		
		int[] statuses = service.buy(book);
		assertTrue(statuses[0] == 0);
		assertTrue(statuses[1] == 1);
		
	}

}
