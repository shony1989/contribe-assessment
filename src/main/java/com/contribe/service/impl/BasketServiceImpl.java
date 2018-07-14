package com.contribe.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.contribe.domainobject.Basket;
import com.contribe.domainobject.BasketBooks;
import com.contribe.domainobject.Book;
import com.contribe.repo.BasketBooksRepository;
import com.contribe.repo.BasketRepository;
import com.contribe.repo.BookRepository;
import com.contribe.service.BasketService;

@Component
public class BasketServiceImpl implements BasketService {

	private BasketRepository basketRepo;
	private BasketBooksRepository basketBookRepo;
	private BookRepository bookRepo;
	private BookListImpl bookService;

	@Autowired
	public BasketServiceImpl(BasketRepository basketRepo, BasketBooksRepository basketBookRepo, BookRepository bookRepo,
			BookListImpl bookService) {
		this.basketRepo = basketRepo;
		this.basketBookRepo = basketBookRepo;
		this.bookRepo = bookRepo;
		this.bookService = bookService;
	}

	@Override
	public boolean addBookInBookstore(Long userId, Book book, int quantity) {

		if (quantity == 0) {
			return false;
		}
		try {
			Basket basket = basketRepo.findByUserId(userId);
			BigDecimal totalPrice;
			if (basket != null) {
				Book b = bookRepo.findByTitleAndAuthorAndPrice(book.getTitle(), book.getAuthor(), book.getPrice());

				// Try to add book in Basket if quantity is less than total count or same
				if (b != null && quantity <= b.getQuantity()) {
					BasketBooks bb = basketBookRepo.findByBasketAndBook(b.getId(), basket.getId());
					if (bb != null) {
						bb.setQuantity(bb.getQuantity() + quantity);
					} else {
						bb = new BasketBooks(b, basket, quantity);
					}
					totalPrice = basket.getTotalPrice().add(b.getPrice().multiply(BigDecimal.valueOf(quantity)));

					basket.setTotalPrice(totalPrice);
					basketRepo.save(basket);

					basketBookRepo.save(bb);

				} else {
					return false;
				}
			} else {
				basket = new Basket();
				basket.setUserId(userId);

				BasketBooks bb = null;

				Book b = bookRepo.findByTitleAndAuthorAndPrice(book.getTitle(), book.getAuthor(), book.getPrice());
				if (b != null) {
					bb = new BasketBooks(b, basket, quantity);

				} else {
					return false;
				}

				totalPrice = b.getPrice().multiply(BigDecimal.valueOf(quantity));

				basket.setTotalPrice(totalPrice);

				basket = basketRepo.save(basket);
				bb.setBasket(basket);
				basketBookRepo.save(bb);

			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean removeBookFromBookstore(Long userId, Book book, int quantity) {

		if (quantity == 0) {
			return false;
		}
		try {
			Basket basket = basketRepo.findByUserId(userId);
			BigDecimal totalPrice;
			int qty = 0;
			if (basket != null) {
				Book b = bookRepo.findByTitleAndAuthorAndPrice(book.getTitle(), book.getAuthor(), book.getPrice());
				if (b != null) {
					BasketBooks bb = basketBookRepo.findByBasketAndBook(b.getId(), basket.getId());
					if (bb != null) {
						qty = bb.getQuantity() - quantity;
						if (qty > 0) {
							bb.setQuantity(bb.getQuantity() - quantity);
						} else if (qty < 0) {
							// trying to remove more than available
							return false;
						}
					}
					totalPrice = basket.getTotalPrice().subtract(b.getPrice().multiply(BigDecimal.valueOf(quantity)));

					basket.setTotalPrice(totalPrice);
					basketRepo.save(basket);

					if (qty > 0) {
						basketBookRepo.save(bb);
					} else if (qty == 0) {
						// if qty is zero remove the record from basket
						basketBookRepo.delete(bb);
					}

				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public int[] buy(Long userId) {

		int[] status = new int[1];
		try {
			Basket b = basketRepo.findByUserId(userId);
			if (b != null) {

				List<BasketBooks> totalBooksInBasket = basketBookRepo.findBooksAddedToBasket(b.getId());

				if (!totalBooksInBasket.isEmpty()) {
					Book[] basketList = new Book[totalBooksInBasket.size()];

					int i = 0;

					for (BasketBooks bb : totalBooksInBasket) {
						Book book = bb.getBook();
						book.setBasketQuantity(bb.getQuantity());
						basketList[i] = book;
						i++;
						// its going for buying ,then all shopping list is removed from the basket
						bb.setIsAdded(false);
						basketBookRepo.save(bb);
					}
					status = bookService.buy(basketList);

				} else {
					status[0] = com.contribe.domainobject.Status.DOES_NOT_EXIST.ordinal();
				}
			} else {
				status[0] = com.contribe.domainobject.Status.DOES_NOT_EXIST.ordinal();
			}
		} catch (Exception e) {
			status[0] = com.contribe.domainobject.Status.DOES_NOT_EXIST.ordinal();
		}
		return status;
	}

}
