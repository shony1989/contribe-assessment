package com.contribe.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contribe.domainobject.BasketBooks;

public interface BasketBooksRepository extends JpaRepository<BasketBooks, Long> {
	
	@Query("select bb from BasketBooks bb where bb.book.id = ?1 and bb.basket.id = ?2 and bb.isAdded = true")
	BasketBooks findByBasketAndBook(Long bookId, Long basketId);
	
	@Query("select bb from BasketBooks bb where bb.basket.id = ?1 and bb.isAdded = true")
	List<BasketBooks> findBooksAddedToBasket(Long basketId);

}
