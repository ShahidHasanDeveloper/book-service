package com.epam.books.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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


@RestController
@Validated
public class BookController {

	@Autowired
	private BookService bookService;


	@GetMapping("/books")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}

	@PostMapping("/books")
	public ResponseEntity<Void> addBook(@Valid @RequestBody Book book, UriComponentsBuilder builder) throws BookExistsException {

		Book addBook= bookService.addBook(book);
		HttpHeaders headers= new HttpHeaders();
		headers.setLocation(builder.path("/books/{book_id}").buildAndExpand(addBook.getId()).toUri());
		return  new ResponseEntity<>(headers, HttpStatus.CREATED);

	}

	@GetMapping("/books/{book_id}")
	public Optional<Book> getBookById(@PathVariable("book_id")  @Min(1) Long id){
		try {
			return bookService.getBookById(id);
		} catch(BookNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}

	}


	@PutMapping("/books/{book_id}") 
	public Book updateBookById(@PathVariable("book_id") Long id, @RequestBody Book book) {
		try {
			return bookService.updateBookById(id, book);
		} catch (BookNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@DeleteMapping("/books/{book_id}")
	public void deleteBookById(@PathVariable("book_id") Long id ) {
		bookService.deleteBookById(id);
	}
}
