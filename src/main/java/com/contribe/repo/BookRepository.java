package com.contribe.repo;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contribe.domainobject.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("select b from Book b where b.title = ?1 or b.author = ?1")
	Book[] findByTitleOrAuthor(String searchString);
	
	Book findByTitleAndAuthorAndPrice(String title, String author, BigDecimal price);
}
