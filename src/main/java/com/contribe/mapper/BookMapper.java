package com.contribe.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.contribe.domainobject.Book;
import com.contribe.dto.BookDTO;

public class BookMapper {

	public static BookDTO makeBookDTO(Book book) {
		BookDTO bookDto = new BookDTO();
		// using reflection to map the Objects
		BeanUtils.copyProperties(book, bookDto);
		return bookDto;
	}

	public static List<BookDTO> makeBookDTOList(Collection<Book> cars) {
		return cars.stream().map(BookMapper::makeBookDTO).collect(Collectors.toList());
	}
	
	public static Book makeBook(BookDTO bookDto) {
		Book book = new Book();
		// using reflection to map the Objects
		BeanUtils.copyProperties(bookDto, book);
		return book;
	}

}
