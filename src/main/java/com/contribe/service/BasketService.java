package com.contribe.service;

import com.contribe.domainobject.Book;

public interface BasketService {

	/**
	 * Method to add book to the basket for the individual user
	 * User id is kept as static purpose for now but when deployed in server expected 
	 * many user can login
	 * 
	 * @param userId
	 * @param book
	 * @param quantity
	 * @return boolean
	 */
	public boolean addBookInBookstore(Long userId, Book book, int quantity);

	/**
	 * Method to remove the book from user basket
	 *  @param userId
	 * @param book
	 * @param quantity
	 * @return boolean
	 */
	public boolean removeBookFromBookstore(Long userId, Book book, int quantity);
	
	/**
	 * buy all book and return status for each individual user
	 * @param user
	 * @return status of book buy
	 */
	public int[] buy(Long userId);

}
