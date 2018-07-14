package com.contribe.dto;

import java.math.BigDecimal;

public class BookDTO {

	private String title;

	private String author;

	private BigDecimal price;

	public BookDTO() {

	}

	public BookDTO(String title, String author, BigDecimal price) {
		this.title = title;
		this.author = author;
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {

		return "Book[Title : " + this.title + " ,Author : " + this.author + " ,Price : " + this.price + "]";

	}

}
