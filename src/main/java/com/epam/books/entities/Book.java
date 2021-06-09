package com.epam.books.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



@Entity
@Table(name="book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="BOOK_ID")
	private Long id;


	@Column(name="BOOK_NAME", length=100, nullable=false, unique = true)
	@NotEmpty(message = "Book name is mandatory , please provide bookname")
	private String bookname;

	@Column(name="AUTHOR", length=50, nullable=false)
	@Size(min=2, message="Author Name should have minimum 2 characters")
	private String authorname;

	@Column(name="CATEGORY", length=50, nullable=false)
	private String category;

	@Column(name="DESCRIPTION", length=200, nullable=false)
	private String description;


	@Column(name="PUBLISH_YEAR", length=4, nullable=false)
	private int publishYear;


	public Book() {

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getBookname() {
		return bookname;
	}


	public void setBookname(String bookname) {
		this.bookname = bookname;
	}




	public String getAuthorname() {
		return authorname;
	}


	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getPublishYear() {
		return publishYear;
	}


	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}








}
