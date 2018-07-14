package com.contribe.service;

import com.contribe.domainobject.Book;

public interface BookList {  

	  public Book[] list(String searchString);

	  public boolean add(Book book, int quantity);

	  public int[] buy(Book... books);
	  
	  public void saveBook(Book book);

	}
