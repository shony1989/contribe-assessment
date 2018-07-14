package com.contribe.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.contribe.domainobject.Book;
import com.contribe.domainobject.Status;
import com.contribe.repo.BookRepository;
import com.contribe.service.BookList;

@Component
public class BookListImpl implements BookList {

	private BookRepository repo;

	@Autowired
	public BookListImpl(BookRepository repo) {
		this.repo = repo;
	}

	@Override
	public Book[] list(String searchString) {

		Book[] books = null;

		if (searchString != null) {
			books = repo.findByTitleOrAuthor(searchString);
		} else {
			List<Book> bookList = repo.findAll();
			books = new Book[bookList.size()];
			bookList.toArray(books);
		}
		return books;
	}

	@Override
	public boolean add(Book newBook, int quantity) {

		try {
			Book book = repo.findByTitleAndAuthorAndPrice(newBook.getTitle(), newBook.getAuthor(), newBook.getPrice());

			if (book != null) {
				//increment the quantity if existing book is added
				book.setQuantity(book.getQuantity() + quantity);
			} else {
				book = new Book(newBook.getTitle(), newBook.getAuthor(), newBook.getPrice(), quantity);
			}
			repo.save(book);

		} catch (Exception e) {
			// if any constraint violation will return false
			return false;
		}

		return true;
	}

	@Override
	@Transactional
	public int[] buy(Book... books) {//
		int[] statuses = new int[books.length];
		int i = 0;
		for (Book book : books) {
			if (book.getBasketQuantity() <= book.getQuantity()) {
				book.setQuantity(book.getQuantity() - book.getBasketQuantity());
				statuses[i] = Status.OK.ordinal();
				// Saving with latest quantity
				repo.save(book);
			} else if (book.getBasketQuantity() > book.getQuantity()) {
				statuses[i] = Status.NOT_IN_STOCK.ordinal();
			} else {
				statuses[i] = Status.DOES_NOT_EXIST.ordinal();
			}
			i++;
		}
		return statuses;
	}

	@Override
	public void saveBook(Book book) {
		try {
		repo.save(book);
		} catch (Exception e) {
			
		}
	}

}
