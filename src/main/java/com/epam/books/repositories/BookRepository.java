package com.epam.books.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.books.entities.Book;

public interface BookRepository extends JpaRepository<Book, Long>  {

	public Optional<Book> findByBookname(String bookname);
	public Optional<Book> findByAuthorname(String authorname);
	
}
