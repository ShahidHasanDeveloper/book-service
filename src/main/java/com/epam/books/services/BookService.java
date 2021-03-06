package com.epam.books.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.epam.books.entities.Book;
import com.epam.books.exceptions.BookExistsException;
import com.epam.books.exceptions.BookNotFoundException;
import com.epam.books.repositories.BookRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class BookService {

	private final Logger logger = LoggerFactory.getLogger(BookService.class);
	@Autowired
	private BookRepository bookRepository;

	@HystrixCommand(fallbackMethod = "buildFallbackGetAllBooks")
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}

	@HystrixCommand
	public Book addBook(Book book) throws BookExistsException{
		Optional<Book>bookFoundByBookname= bookRepository.findByBookname(book.getBookname());
		Optional<Book>bookFoundByAuthor= bookRepository.findByAuthorname(book.getAuthorname());
		if(bookFoundByBookname.isPresent() || bookFoundByAuthor.isPresent()) {
			logger.info("BookService | addBook | Book is already present");
			throw new BookExistsException("Book is already present");
		}
		
		return bookRepository.save(book);
	}
	@HystrixCommand
	public Optional<Book> getBookById(Long id) throws BookNotFoundException {
		Optional<Book> book=  bookRepository.findById(id);
		if(!book.isPresent()) {
			logger.info("BookService | getBookById | book not found");
			throw new BookNotFoundException("book not found");
		}
		return book;

	}
	@HystrixCommand
	public Book updateBookById(Long id, Book book) throws BookNotFoundException{
		Optional<Book> optionalBook=  bookRepository.findById(id);
		if(!optionalBook.isPresent()) {
			logger.info("BookService | updateBookById | Book not found for given book id for update");
			throw new BookNotFoundException("Book not found for given book id for update");
		}
		book.setId(id);
		return bookRepository.save(book);
	}
	@HystrixCommand
	public void deleteBookById(Long id) throws ResponseStatusException {
		Optional<Book> optionalBook=  bookRepository.findById(id);
		if(!optionalBook.isPresent()) {
			logger.info("BookService | deleteBookById | Book not found for given book id for delete");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Book not found for given book id for delete");
		}
		bookRepository.deleteById(id);


	}
	
	
	public List<Book> buildFallbackGetAllBooks(){
		logger.info("BookService | buildFallbackGetAllBooks | serving from fallback implementation");
		Book book = new Book();
		book.setAuthorname("Unknown");
		book.setBookname("Unknown");
		book.setCategory("Unknown");
		book.setDescription("Sorry,  no book information available currently , Please try after sometime");
		return Collections.singletonList(book);
	}
	
}
