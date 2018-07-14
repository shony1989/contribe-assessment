package com.contribe.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.contribe.domainobject.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {

	@Autowired
	private BookRepository repo;
	
	@Test
	public void test_book_full_list() {
		
		List<Book> books = repo.findAll();
		assertEquals(books.size(), 5);
	}
	
	@Test
	public void test_book_With_Title() {
		
		Book[] books = repo.findByTitleOrAuthor("Generic Title");
		assertEquals(books.length, 2);
	}
	
	@Test
	public void test_book_With_Title_Failure() {
		
		Book[] books = repo.findByTitleOrAuthor("Arpan");
		assertNotEquals(books.length, 2);
	}
	
	@Test
	public void test_book_With_Author() {
		
		Book[] books = repo.findByTitleOrAuthor("First Author");
		assertEquals(books.length, 1);
	}
	
	@Test
	public void test_book_With_Author_Failure() {
		
		Book[] books = repo.findByTitleOrAuthor("Arpan");
		assertNotEquals(books.length, 2);
	}
}
