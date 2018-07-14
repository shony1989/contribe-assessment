package com.contribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.contribe.domainobject.Book;
import com.contribe.dto.BookDTO;
import com.contribe.mapper.BookMapper;
import com.contribe.service.BasketService;
import com.contribe.service.impl.BookListImpl;

@Component
public class StartBookstore implements CommandLineRunner {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private BookListImpl bookService;

	private BasketService basketService;

	@Value("${download.url}")
	private String downloadPath;

	@Value("${loaddata.filename}")
	private String filename;

	@Value("${load.data}")
	private boolean canLoadData;
	
	// For Each User that generate unique long id,mock as per table ,can be null is
	// its single user always
	private final Long userId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;


	@Autowired
	public StartBookstore(BookListImpl bookService, BasketService basketService) {
		this.bookService = bookService;
		this.basketService = basketService;
	}

	@Override
	public void run(String... args) throws Exception {
		if (canLoadData) {
			// download
			downloadFile();

			// load data in table Book
			loadData();

			Scanner s = new Scanner(System.in);

			int userInput = 0;
			boolean quit = true;

			
			do {
				// display menu to user
				// ask user for his choice and validate it (make sure it is between 1 and 6)
				System.out.println();
				System.out.println("1) List Book by Title/Author or leave the field empty and press Enter");
				System.out.println("2) Add a book to the stock");
				System.out.println("3) Add a book to the Basket");
				System.out.println("4) Remove a book from the stock");
				System.out.println("5) Buy the books");
				System.out.println("6) Exit the bookstore");

				userInput = Integer.valueOf(s.nextLine());

				switch (userInput) {
				case 1:
					System.out.println("Enter a book title/Author");
					System.out.println();

					String in = s.nextLine();

					if (in.isEmpty()) {
						in = null;
					}

					Stream.of(bookService.list(in)).forEach(f -> System.out.println(BookMapper.makeBookDTO(f)));
					break;

				case 2:

					if (bookService.add(BookMapper.makeBook(getBookDTO(s)), getQty(s))) {
						System.out.println("Book Added");
					} else {
						System.out.println("Book cant be added please try again");
					}

					break;

				case 3:

					if (basketService.addBookInBookstore(userId, BookMapper.makeBook(getBookDTO(s)), getQty(s))) {
						System.out.println("Book Added to basket");
					} else {
						System.out.println("Book cant be added please try again");
					}

					break;

				case 4:
					if (basketService.removeBookFromBookstore(userId, BookMapper.makeBook(getBookDTO(s)), getQty(s))) {
						System.out.println("Book removed from basket");
					} else {
						System.out.println("Book cant be removed please try again");
					}

					break;

				case 5:
					int[] buyStatuses = basketService.buy(userId);

					System.out.println("Buy Status of Books");
					for (int i : buyStatuses) {
						System.out.println(i);
					}

					break;

				case 6:
					quit = false;
					System.out.println("Exiting book store");
					break;

				default:
				}

			} while (quit);
			s.close();
		}
	}

	/*
	 * Downloading the file from the URL
	 */
	private void downloadFile() {
		try {
			HttpDownloadUtility.downloadFile(downloadPath);
		} catch (IOException e) {
			System.out.println();
			log.error("", e);
		}
	}

	/*
	 * Loading the data into the table
	 */
	private void loadData() {
		List<String> list = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename), StandardCharsets.ISO_8859_1)) {

			list = br.lines().collect(Collectors.toList());

			list.forEach(f -> {
				String[] parsedString = f.split(";");

				Book b = new Book(parsedString[0], parsedString[1], new BigDecimal(parsedString[2].replaceAll(",", "")),
						Integer.valueOf(parsedString[3]));
				bookService.saveBook(b);
			});

		} catch (IOException e) {
			System.out.println("Exception " + e);
			log.error("", e);
		}
	}

	private BookDTO getBookDTO(Scanner s) {
		System.out.println("Enter a book title");

		String title = s.nextLine();

		System.out.println("Enter a book author");
		String author = s.nextLine();

		System.out.println("Enter a book price");
		BigDecimal price = new BigDecimal(s.nextLine().replaceAll(",", ""));

		BookDTO bookDto = new BookDTO(title, author, price);

		return bookDto;
	}

	private Integer getQty(Scanner s) {
		System.out.println("Enter a book quantity(in numbers)");
		Integer quantity = Integer.valueOf(s.nextLine());

		return quantity;
	}

}
