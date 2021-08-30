package com.epam.books.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.epam.books.entities.Book;
import com.epam.books.exceptions.BookExistsException;
import com.epam.books.exceptions.BookNotFoundException;
import com.epam.books.services.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Book Management RESTful Services", description="Controller for book service")
@RestController
@Validated
public class BookController {

	private final Logger logger = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private BookService bookService;

	@ApiOperation(value = "List of all books")
	@GetMapping(value="/books", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	@ApiOperation(value = "Add a book")
	@PostMapping(value="/books", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addBook(@Valid @RequestBody Book book, UriComponentsBuilder builder) throws BookExistsException {

		Book addBook= bookService.addBook(book);
		HttpHeaders headers= new HttpHeaders();
		headers.setLocation(builder.path("/books/{book_id}").buildAndExpand(addBook.getId()).toUri());
		return  new ResponseEntity<>(headers, HttpStatus.CREATED);

	}

	@ApiOperation(value = "Get book by id")
	@GetMapping(value="/books/{book_id}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Book getBookById(@PathVariable("book_id")  @Min(1) Long id){
		try {
			Optional<Book> optionalBook=bookService.getBookById(id);
			return optionalBook.get();
		} catch(BookNotFoundException e) {
			logger.error("BookController | getBookById | BookNotFoundException exception {} "+ e.getCause());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}

	@ApiOperation(value = "Update a book")
	@PutMapping(value="/books/{book_id}", produces=MediaType.APPLICATION_JSON_VALUE) 
	public Book updateBookById(@PathVariable("book_id") Long id, @RequestBody Book book) {
		try {
			return bookService.updateBookById(id, book);
		} catch (BookNotFoundException e) {
			logger.error("BookController | updateBookById | BookNotFoundException exception {} "+ e.getCause());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@ApiOperation(value = "Delete a book")
	@DeleteMapping(value="/books/{book_id}")
	public void deleteBookById(@PathVariable("book_id") Long id ) {
		bookService.deleteBookById(id);
	}
}
